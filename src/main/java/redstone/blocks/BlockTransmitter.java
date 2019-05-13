package redstone.blocks;

import javax.sound.midi.Transmitter;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockTransmitter extends WirelessDiode {

	public static final BlockTransmitter POWERED = new BlockTransmitter(false, "powered_transmitter");
	public static final BlockTransmitter UNPOWERED = new BlockTransmitter(false, "unpowered_transmitter");

	public BlockTransmitter(boolean powered, String registryName) {
		super(powered, registryName);
		//		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DELAY, Integer.valueOf(1)).withProperty(LOCKED, Boolean.valueOf(false)));

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

}
