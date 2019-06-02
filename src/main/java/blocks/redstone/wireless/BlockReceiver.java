package blocks.redstone.wireless;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockReceiver extends WirelessDiode {

	public static final BlockReceiver POWERED 
	= new BlockReceiver(true, "powered_receiver");
	
	public static final BlockReceiver UNPOWERED
	= new BlockReceiver(false, "unpowered_receiver");
	
	public BlockReceiver(boolean powered, String registryName) {
		super(powered, registryName);
		
	}

	@Override
	protected boolean compareWithRemoteType(Block block) {
		return block instanceof BlockTransmitter;
	}

	@Override
	protected int getDelay(IBlockState state) {
		return 2;
	}
	
	@Override
	void sendUpdateToTransmitter(World world, BlockPos thisPos, BlockPos transmitterPos) {
		
		IBlockState state = world.getBlockState(transmitterPos);
		BlockTransmitter transmitter = (BlockTransmitter) state.getBlock();
		world.updateBlockTick(transmitterPos, transmitter, transmitter.getDelay(state), -1);
	}
	
	@Override
	protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
		if (isConnectedToRemote())
			return getRemotePower();
		else 
			return false;
	}
	
	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
		return BlockReceiver.POWERED.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
		return UNPOWERED.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	void sendUpdateToReceiver(World world, BlockPos thisPos, BlockPos remotePos) {
		
		IBlockState state = world.getBlockState(thisPos);
		world.updateBlockTick(thisPos, this, this.getDelay(state), -1);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		if(!worldIn.isRemote) {
			this.notifyNeighbors(worldIn, pos, state);
		}
	}
	
	@Override
	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
	}

}
