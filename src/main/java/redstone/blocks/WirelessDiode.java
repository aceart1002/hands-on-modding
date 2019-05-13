package redstone.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.GuiConnectToReceiver;
import hands.on.modding.HandsOnModding;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import registry.Registrable;
import tiles.ContainsTile;
import tiles.TileRemote;

public abstract class WirelessDiode extends BlockRedstoneDiode 
implements Registrable, ContainsTile<TileRemote> {

	private String registryName;
	protected TileRemote remoteConnection;
	private static final Integer SIGNAL_STRENGTH = new Integer(100);
	boolean connectionIsPowered;
	BlockPos currentPos;
	
	protected WirelessDiode(boolean powered, String registryName) {
		super(powered);
		setCustomRegistryName(registryName);
		
		setCreativeTab(HandsOnModding.ENHANCED_REDSTONE);
	}
	
	@Override
	public void setCustomRegistryName(String newName) {
		registryName = newName;
		
	}
	
	@Override
	public String getCustomRegistryName() {
		return registryName;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	
		remoteConnection = getTileEntity(worldIn, pos);
		
		Minecraft game = HandsOnModding.proxy.game;
		if (getRemotePosition() != null) {
			List<BlockPos> availableConnects = getAvailableConnects(pos, worldIn);
//			openWithSlots
			game.displayGuiScreen(new GuiConnectToReceiver(game.currentScreen));
		} else {
//			openWithoutSlots
		}
		return true;
	}
	
	List<BlockPos> getAvailableConnects(BlockPos centralBlock, World worldIn) {
		
		List<BlockPos> list = new ArrayList<>();
		
		int radius = SIGNAL_STRENGTH;
		BlockPos radiusDistance = new BlockPos(radius,radius,radius);
		BlockPos min = centralBlock.subtract(radiusDistance);
		BlockPos max = centralBlock.add(radiusDistance);
		
		for(BlockPos blockPos : BlockPos.getAllInBox(min, max)) {
			if (compareWithRemoteType(worldIn.getBlockState(blockPos).getBlock())) {
				list.add(blockPos);
			}
			
//			if (world.getBlockState(blockPos).getBlock() == ) {
//				receivers.add(blockPos);
//			}
		}
		
		return list;
	}
	
	protected abstract boolean compareWithRemoteType(Block block);

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!this.isLocked(worldIn, pos, state))
        {
            
			if (getRemotePosition() != null) {
				
			}
			//boolean flag = this.shouldBePowered(worldIn, pos, state);

//            if (this.isRepeaterPowered && !flag)
//            {
            	
            	//worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
//            }
//            else if (!this.isRepeaterPowered)
//            {
            	updateConnection(worldIn, pos, state);
                //worldIn.setBlockState(pos, this.getPoweredState(state), 2);

//                if (!flag)
//                {
                    //worldIn.updateBlockTick(pos, this.getPoweredState(state).getBlock(), this.getTickDelay(state), -1);
//                }
//            }
        }
		
	}

	void updateBlock(World worldIn, BlockPos pos) {
		
		IBlockState state = worldIn.getBlockState(pos);
		WirelessDiode block = (WirelessDiode) state.getBlock();
		IBlockState newState = getNewState(block, state);
		
		updateBlock(worldIn, state, newState, pos);
	}
	
	void updateBlock(World worldIn, IBlockState oldState, IBlockState newState,
			BlockPos pos) {
		
		if(newState != oldState)
			worldIn.setBlockState(pos, newState);
	
	}
	
	IBlockState getNewState(WirelessDiode block, IBlockState oldState) {
		IBlockState newState = null;
		if(connectionIsPowered) {
			newState = block.getPoweredState(oldState);
		} else if(!connectionIsPowered) {
			newState = block.getUnpoweredState(oldState);
		}
		
		return newState;
	}
	
	private void updateConnection(World worldIn, BlockPos pos, IBlockState state) {
		
		connectionIsPowered = shouldBePowered(worldIn, pos, state);
		setConnectionPower(worldIn, connectionIsPowered);

		//update this block
		IBlockState newState = getNewState(this, state);
		updateBlock(worldIn, state, newState, pos);
		
		//update remote block
		BlockPos remotePos = remoteConnection.getPos();
		updateBlock(worldIn, remotePos);

	}
	
	 BlockPos getRemotePosition() {
//		remoteConnection = getTileEntity(currentPos);
		return remoteConnection.getPosition();
	}
	
	 boolean getRemotePower() {
//		remoteConnection = getTileEntity(currentPos);
		return remoteConnection.getPower();
	}
	
	void setConnectionPositions(World worldIn, BlockPos currentPos, BlockPos remotePos) {
		TileRemote remoteConnectionTile = getTileEntity(worldIn, remoteConnection.getPosition());
		
		remoteConnectionTile.setPosition(currentPos);
		remoteConnection.setPosition(remotePos);
	}
	
	void setConnectionPower(World worldIn, boolean currentPower) {
		TileRemote remoteConnectionTile = getTileEntity(worldIn, remoteConnection.getPosition());
		
		remoteConnectionTile.setPower(currentPower);
		remoteConnection.setPower(currentPower);
	}
	
	void connectToRemote(World worldIn, BlockPos currentPos, BlockPos remotePos) {
		
		setConnectionPositions(worldIn, currentPos, remotePos);
		
		IBlockState state = worldIn.getBlockState(currentPos);
		
		worldIn.updateBlockTick(currentPos, this, this.getDelay(state), -1);
//		updateConnection();
	}
	
	void disconnectFromRemote(World worldIn) {
		
		if(remoteConnection.getPos() != null) {
			setConnectionPositions(worldIn, null, null);
		}
		
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		
		remoteConnection = getTileEntity(worldIn, pos);
		disconnectFromRemote(worldIn);
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		
		remoteConnection = getTileEntity(worldIn, pos);
		disconnectFromRemote(worldIn);
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		remoteConnection = getTileEntity(worldIn, pos);
		disconnectFromRemote(worldIn);
		super.breakBlock(worldIn, pos, state);
	}
	
	
	@Override
    public Class<TileRemote> getTileEntityClass() {

        return TileRemote.class;
    }

    @Override
    public TileRemote createTileEntity(World world, IBlockState blockState) {

        return new TileRemote();
    }
	
}
