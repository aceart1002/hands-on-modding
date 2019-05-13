package redstone.blocks;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class Transmitter extends WirelessDiode {

	public static final PropertyBool LOCKED = PropertyBool.create("locked");
    public static final PropertyInteger DELAY = PropertyInteger.create("delay", 1, 4);

	public Transmitter(boolean powered, String registryName) {
		super(powered, registryName);
//		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DELAY, Integer.valueOf(1)).withProperty(LOCKED, Boolean.valueOf(false)));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getDelay(IBlockState state) {
		return ((Integer)state.getValue(DELAY)).intValue() * 2;
	}

	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		// TODO Auto-generated method stub
		return null;
	}

}
