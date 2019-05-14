package blocks.test;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import registry.ModBlocks;
import registry.Registrable;

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
	protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
		if (isConnectedToRemote(worldIn, pos))
			return getRemotePower(worldIn, pos);
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

}
