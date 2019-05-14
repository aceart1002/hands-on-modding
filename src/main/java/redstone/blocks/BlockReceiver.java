package redstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import registry.ModBlocks;
import registry.Registrable;

public class BlockReceiver extends WirelessDiode {

	public static final BlockReceiver POWERED_RECEIVER 
	= new BlockReceiver(true, "powered_receiver");
	
	public static final BlockReceiver UNPOWERED_RECEIVER
	= new BlockReceiver(false, "unpowered_receiver");
	
	public BlockReceiver(boolean powered, String registryName) {
		super(powered, registryName);
		
//		setRegistryName(getCustomRegistryName());
//		setUnlocalizedName(getCustomRegistryName());

//		 this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
		if (remoteConnection != null)
			return remoteConnection.getPower();
		else 
			return false;
	}

	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
//		return POWERED.getDefaultState().withProperty(FACING, enumfacing);
		return BlockReceiver.POWERED_RECEIVER.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
//		return BlockReceiver.UNPOWERED.getDefaultState().withProperty(FACING, enumfacing);
		return UNPOWERED_RECEIVER.getDefaultState().withProperty(FACING, enumfacing);
	}

}
