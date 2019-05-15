package blocks.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.GuiConnectToReceiver;
import hands.on.modding.HandsOnModding;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import tiles.TileRemote;

public abstract class WirelessDiode extends 
AbstractWithTileRegistrableContainsProperties<TileRemote> {

	
	private static final Integer SIGNAL_STRENGTH = new Integer(100);
	
	static protected List<BlockPos> availableConnects;
	
	public World currentWorld;
	public BlockPos currentPos;
	
	protected WirelessDiode(boolean powered, String registryName) {
		super(powered, registryName);
		
		setCreativeTab(HandsOnModding.ENHANCED_REDSTONE);
	}
	
	protected static List<BlockPos> returnAvailableConnects() {
		return availableConnects;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	if(!worldIn.isRemote) {
//		remoteConnection = getTileEntity(worldIn, pos);
		currentPos = pos;
		currentWorld = worldIn;
		HandsOnModding.proxy.setWireless(this);
//		Minecraft game = HandsOnModding.proxy.game;
		Minecraft game = Minecraft.getMinecraft();
		if (!isConnectedToRemote(worldIn, pos)) {
			availableConnects = findAvailableConnects(pos, worldIn);
//			openWithSlots
//			game.displayGuiScreen(new GuiWorldSelection(game.currentScreen));
//			game.displayGuiScreen(new GuiConnectToReceiver(game.currentScreen));
			HandsOnModding.proxy.displayScreen(new GuiConnectToReceiver(game.currentScreen));
		} else {
//			openWithoutSlots
		}
	}
		return true;
	}
	
	List<BlockPos> findAvailableConnects(BlockPos centralBlock, World worldIn) {
		
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
            
			//boolean flag = this.shouldBePowered(worldIn, pos, state);

//            if (this.isRepeaterPowered && !flag)
//            {
            	
            	//worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
//            }
//            else if (!this.isRepeaterPowered)
//            {
			TileRemote tile1 = getTileEntity(worldIn, pos);
			TileRemote tile2 = getTileEntity(worldIn, tile1.getPosition());
			
			BlockPos remotePos = tile1.getPosition();
			BlockPos currentPos = tile2.getPosition();
			
			BlockPos pos3 = getRemotePosition(worldIn, pos);
			
            	updateConnection(worldIn, pos, state);
                //worldIn.setBlockState(pos, this.getPoweredState(state), 2);

//                if (!flag)
//                {
                    //worldIn.updateBlockTick(pos, this.getPoweredState(state).getBlock(), this.getTickDelay(state), -1);
//                }
//            }
        }
		
	}

	IBlockState getNewState(WirelessDiode block, boolean currentPower,
			IBlockState oldState) {
		IBlockState newState = null;
		if(currentPower) {
			newState = block.getPoweredState(oldState);
		} else if(!currentPower) {
			newState = block.getUnpoweredState(oldState);
		}
		
		return newState;
	}
	
	private void updateConnection(World worldIn, BlockPos pos, IBlockState state) {
		
		boolean connectionPower = shouldBePowered(worldIn, pos, state);
//		setPower(remoteConnection, connectionIsPowered);

		boolean runRemote = isConnectedToRemote(worldIn, pos);
		BlockPos remotePos = null;
		if(runRemote) {
			remotePos = getRemotePosition(worldIn, pos);
		}
		
		//update this block
		IBlockState newState = getNewState(this, connectionPower, state);
		updateBlock(worldIn, state, newState, pos);
		
		//update remote block
		if(runRemote) {
			
//			setPower(getTileEntity(worldIn, remotePos), connectionIsPowered);
			updateBlock(worldIn, remotePos, connectionPower);

			setConnection(worldIn, true, pos, remotePos);
			refreshPower(worldIn, connectionPower, pos, remotePos);
			
//			worldIn.updateBlockTick(remotePos, 
//					worldIn.getBlockState(remotePos).getBlock(), 
//					this.getDelay(worldIn.getBlockState(remotePos)), -1);
			
		}
	}
	
	@Override
	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
//		boolean flag = this.shouldBePowered(worldIn, pos, state);

		worldIn.updateBlockTick(pos, this, this.getDelay(state), -1);
        
	}
	
	
	
	void refreshPower(World worldIn, boolean connectionPower,
			BlockPos currentPos, BlockPos remotePos) {
		
		TileRemote localTile = getTileEntity(worldIn, currentPos);
		TileRemote remoteTile = getTileEntity(worldIn, remotePos);
		
		remoteTile.setPower(connectionPower);
		localTile.setPower(connectionPower);
	}
	
//	void setConnectionPower(World worldIn, BlockPos currentPos, boolean currentPower) {
//		if(connectedToRemote(worldIn, currentPos)) {
//			remoteConnection.setPower(currentPower);
//		
//		
//			TileRemote remoteConnectionTile = getTileEntity(worldIn, getRemotePosition());
//			remoteConnectionTile.setPower(currentPower);
//		}
//	}
	
//	void refreshTiles(World worldIn, BlockPos currentPos, BlockPos remotePos) {
//		localTile = getTileEntity(worldIn, currentPos);
//		remoteTile = getTileEntity(worldIn, remotePos);
//	}
	
//	updateTileEntity(World worldIn, BlockPos tilePos, BlockPos storePos) {
//		TileRemote tile = getTileEntity(worldIn, tilePos);
//		tile.setPosition(storePos);
//		
//	}
	
	private void setConnection(World worldIn, boolean connected,
			BlockPos currentPos, BlockPos remotePos) {
		
		TileRemote localTile = getTileEntity(worldIn, currentPos);
		TileRemote remoteTile = getTileEntity(worldIn, remotePos);
//		refreshTiles(worldIn, currentPos, remotePos);
		remoteTile.setConnected(connected);
		localTile.setConnected(connected);
		
		remoteTile.setPosition(currentPos);
		localTile.setPosition(remotePos);
		
	}
	
	public void connectToRemote(World worldIn, BlockPos currentPos, BlockPos remotePos) {
		
		setConnection(worldIn, true, currentPos, remotePos);
		
		IBlockState state = worldIn.getBlockState(currentPos);
		
		worldIn.updateBlockTick(currentPos, this, this.getDelay(state), -1);
//		updateConnection();
	}
	
	public void disconnectFromRemote(World worldIn, BlockPos currentPos, IBlockState state) {
		
		if(isConnectedToRemote(worldIn, currentPos)) {
			setConnection(worldIn, false, currentPos,
					getRemotePosition(worldIn, currentPos));
		}

		worldIn.updateBlockTick(currentPos, this, this.getDelay(state), -1);
		
	}
	
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
//			disconnectFromRemote(worldIn, pos, state);
		}
//		super.breakBlock(worldIn, pos, state);
	}

	void updateBlock(World worldIn, BlockPos pos, boolean connectionPower) {
		 
		IBlockState state = worldIn.getBlockState(pos);
		WirelessDiode block = (WirelessDiode) state.getBlock();
		IBlockState newState = getNewState(block, connectionPower, state);
		
		updateBlock(worldIn, state, newState, pos);
	}
	
	void updateBlock(World worldIn, IBlockState oldState, IBlockState newState,
			BlockPos pos) {
		
		if(newState != oldState)
			worldIn.setBlockState(pos, newState);
		
	}
	
	BlockPos getRemotePosition(World worldIn, BlockPos pos) {
		TileRemote localTile = getTileEntity(worldIn, pos);
		return localTile.getPosition();
	}
	
	 boolean getRemotePower(World worldIn, BlockPos pos) {
		 TileRemote localTile = getTileEntity(worldIn, pos);
		return localTile.getPower();
	}
	 
	 boolean isConnectedToRemote(World worldIn, BlockPos position) {
		 
		 TileRemote localTile = getTileEntity(worldIn, position);
//			 if (localTile != null)
				 return localTile.isConnected();
//			 else
//				 return false;
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
