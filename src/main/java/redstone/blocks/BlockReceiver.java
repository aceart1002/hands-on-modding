package redstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockReceiver extends WirelessDiode {

	public static final BlockReceiver POWERED = new BlockReceiver(true, "powered_receiver");
	public static final BlockReceiver UNPOWERED = new BlockReceiver(false, "unpowered_receiver");
	
	public BlockReceiver(boolean powered, String registryName) {
		super(powered, registryName);
		// TODO Auto-generated constructor stub
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
	protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
		return remoteConnection.getPower();
	}

	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
		return POWERED.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
		return BlockReceiver.UNPOWERED.getDefaultState().withProperty(FACING, enumfacing);
	}

}
