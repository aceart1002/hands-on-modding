package blocks.redstone.wireless;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTransmitter extends WirelessDiode {

	public static final BlockTransmitter POWERED = new BlockTransmitter(false, "powered_transmitter");
	public static final BlockTransmitter UNPOWERED = new BlockTransmitter(false, "unpowered_transmitter");

	//	private static Map<String, BlockPos> availableReceivers = availableConnects;

	public BlockTransmitter(boolean powered, String registryName) {
		super(powered, registryName);

	}

	@Override
	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {

		if(!worldIn.isRemote)
			world = worldIn;
		position = pos;
		sendUpdateToTransmitter(worldIn, pos, getRemotePosition());
	}

	@Override
	protected int getDelay(IBlockState state) {
		return 2;
	}

	@Override
	protected boolean compareWithRemoteType(Block block) {
		return block instanceof BlockReceiver;
	}

	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {

		EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
		return POWERED.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
		return UNPOWERED.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return false;
	}

	@Override
	void sendUpdateToTransmitter(World world, BlockPos thisPos, BlockPos remotePos) {

		IBlockState state = world.getBlockState(thisPos);
		world.updateBlockTick(thisPos,this, this.getDelay(state), -1);
	}

	@Override
	protected void updateDeviceState(World worldIn, BlockPos pos, IBlockState state) {
		super.updateDeviceState(worldIn, pos, state);
		if(isConnectedToRemote()) {
			sendUpdateToReceiver(worldIn, pos, getRemotePosition());
		}
	}

	@Override
	void sendUpdateToReceiver(World world, BlockPos thisPos, BlockPos receiverPos) {
		IBlockState state = world.getBlockState(receiverPos);
		BlockReceiver receiver = (BlockReceiver) state.getBlock();
		world.updateBlockTick(receiverPos, receiver, receiver.getDelay(state), -1);

	}
}
