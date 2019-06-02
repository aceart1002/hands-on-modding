package blocks.redstone.wireless;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import gui.GuiAbstractRemoteConnection;
import gui.GuiEstablishedConnection;
import gui.GuiInitiateConnection;
import hands.on.modding.HandsOnModding;
import hands.on.modding.Modes;
import hands.on.modding.TagGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import tiles.TileRemoteConnection;

public abstract class WirelessDiode extends AbstractWirelessBlock {

	private static final Integer SIGNAL_STRENGTH = new Integer(70);
	static protected Map<BlockPos, String> availableConnects;
	private boolean disconnectOnBreakBlock = true;

	World world;
	BlockPos position;

	World getWorld() {
		return world;
	}

	BlockPos getPosition() {
		return position;
	}

	protected WirelessDiode(boolean powered, String registryName) {
		super(powered, registryName);

		setCreativeTab(HandsOnModding.WIRELESS_REDSTONE);
	}

	abstract protected void updateState(World worldIn, BlockPos pos, IBlockState state);

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		world = worldIn;
		position = pos;

		currentData = new BlockData(worldIn, pos, getTag(), isConnectedToRemote());

		HandsOnModding.proxy.setWireless(this);

		if(worldIn.isRemote) {
			Modes mode;
			if (!isConnectedToRemote()) {

				availableConnects = listAvailableConnects(pos, worldIn);

				mode = Modes.NEW_CONNECTION;
			} else {

				remoteData = new RemoteData(getRemoteTag(), 
						getRemotePosition());

				mode = Modes.EXISTING_CONNECTION;
			}
			//try {
			HandsOnModding.proxy.displayScreen(mode);
			//			}catch (Exception e) {
			//				
			//			}
		}
		return true;
	}


	Map<BlockPos, String> listAvailableConnects(BlockPos centralBlock, World worldIn) {

		Map<BlockPos, String> list = new HashMap<>();

		int radius = SIGNAL_STRENGTH;
		BlockPos radiusDistance = new BlockPos(radius,radius,radius);
		BlockPos min = centralBlock.subtract(radiusDistance);
		BlockPos max = centralBlock.add(radiusDistance);

		for(BlockPos blockPos : BlockPos.getAllInBox(min, max)) {
			if (compareWithRemoteType(worldIn.getBlockState(blockPos).getBlock())) {
				TileRemoteConnection remoteTile = getTileEntity(worldIn, blockPos);
				if(!remoteTile.isConnected())
					list.put(blockPos, remoteTile.getLocalTag());
			}
		}

		return list;
	}

	public static Map<BlockPos, String> returnAvailableConnects() {
		return availableConnects;
	}

	protected abstract boolean compareWithRemoteType(Block block);

	abstract void sendUpdateToTransmitter(World world,BlockPos thisPos, BlockPos remotePos);

	abstract void sendUpdateToReceiver(World world, BlockPos thisPos, BlockPos remotePos);

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		
		world = worldIn;
		position = pos;
		
		disconnectOnBreakBlock = false;
		updateDeviceState(worldIn, pos, state);
		disconnectOnBreakBlock = true;

	}

	protected void updateDeviceState(World worldIn, BlockPos pos, IBlockState state) {

		boolean trueNewPower = shouldBePowered(worldIn, pos, state);

		BlockData save = new BlockData(worldIn, getRemotePosition(), 
				getTag(), isConnectedToRemote());
		//saveData(getTileEntity(worldIn,pos));

		IBlockState newState = getNewState(this, trueNewPower, state);
		updateBlock(worldIn, state, newState, pos);

		TileRemoteConnection currentTile = getTileEntity(worldIn, pos);

		writeToTile(currentTile, save);
		currentTile.setPower(trueNewPower);

		updateTile(pos);

	}


	//	public void setTags(String... tags) {
	//		TileRemote localTile = getTileEntity(currentWorld, currentPos);
	//		localTile.setName(tags[0]);
	//
	//		if(isConnectedToRemote(currentWorld, currentPos) && tags.length == 2) {
	//			TileRemote remoteTile = getTileEntity
	//					(currentWorld, getRemotePosition(currentWorld, currentPos)); 
	//
	//			remoteTile.setName(tags[1]);
	//		}
	//
	//	}

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

	//	private BlockData saveData(TileRemoteConnection tile) {
	//		BlockPos remotePosition = tile.getRemotePosition();
	//		return new BlockData(tile.getWorld(), remotePosition, 
	//				tile.getLocalTag(), tile.isConnected());
	//	}

	private void writeToTile(TileRemoteConnection tile, BlockData data) {
		tile.setConnected(data.isConnected);
		tile.setLocalTag(data.tag);
		if(data.position != null)
			tile.setRemotePosition(data.position);
	}

	public void connectToRemote(BlockPos remotePos) {

		setConnection(remotePos, true);

		sendUpdateToTransmitter(getWorld(), getPosition(), remotePos);
		//		updateConnection();
	}

	public void disconnectFromRemote() {

		if(isConnectedToRemote()) {
			BlockPos remotePos = getRemotePosition();
			setConnection(remotePos, false);
			sendUpdateToReceiver(getWorld(), getPosition(), remotePos);
			sendUpdateToTransmitter(getWorld(), getPosition(), remotePos);
		}

	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(!worldIn.isRemote) {

			world = worldIn;
			position = pos;

			TagGenerator generator = new TagGenerator();
			String startName = generator.generateTag();

			//currentData = new BlockData(worldIn, pos, startName, false);

			setTag(startName);
			super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			if(disconnectOnBreakBlock) {
				world = worldIn;
				position = pos;
				disconnectFromRemote();
			}
		}
		super.breakBlock(worldIn, pos, state);
	}


}
