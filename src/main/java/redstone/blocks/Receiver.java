package redstone.blocks;

import java.util.Random;

import hands.on.modding.HandsOnModding;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Receiver extends BlockRedstoneRepeater {

	public static final Receiver UNPOWERED = new Receiver(false, "unpowered_receiver");
	public static final Receiver POWERED = new Receiver(true, "powered_receiver");
	
	String name;
	
	protected Receiver(boolean powered, String registryName) {
		super(powered);
		
		this.name = registryName;

		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(HandsOnModding.ENHANCED_REDSTONE);
		
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//		// TODO Auto-generated method stub
//		super.updateTick(worldIn, pos, state, rand);
	}

//	@Override
//	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
////		// TODO Auto-generated method stub
////		super.updateState(worldIn, pos, state);
//	}
	
}
