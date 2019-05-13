package redstone.blocks;

import hands.on.modding.HandsOnModding;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestLever extends BlockLever {
	
	String name;
	
	boolean pushed;
	
	public TestLever(String newRegistryName) {
		super();
		
		this.name = newRegistryName;

		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(HandsOnModding.ENHANCED_REDSTONE);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		 if (worldIn.isRemote)
	        {
	            return true;
	        }
	        else
	        {
	            state = state.cycleProperty(POWERED);
	            worldIn.setBlockState(pos, state, 3);
	            pushed = (Boolean)state.getValue(POWERED);
	            float f = ((Boolean)state.getValue(POWERED)).booleanValue() ? 0.6F : 0.5F;
	            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
	            worldIn.notifyNeighborsOfStateChange(pos, this, false);
	            EnumFacing enumfacing = ((BlockLever.EnumOrientation)state.getValue(FACING)).getFacing();
	            worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this, false);
	          
	            HandsOnModding.proxy.testField = pushed;
	            return true;
	        }
		 }
}
