package redstone.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.GuiConnectToReceiver;
import hands.on.modding.HandsOnModding;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestTransmitter extends BlockRedstoneRepeater {

	String name;

	private static final int SIGNAL_STRENGTH = 100;
	
	private static List<BlockPos> availableReceivers;
	
	private static BlockPos receiverPos = new BlockPos(0,0,0);
	
	public static BlockPos getReceiverPos() {
		return receiverPos;
	}

	public static void setReceiverPos(BlockPos receiverPos) {
		TestTransmitter.receiverPos = receiverPos;
	}

	BlockPos referencedRepeaterPos = new BlockPos(500, 56, -700);
	
	public void setReference() {
		referencedRepeaterPos = new BlockPos(500, 56, -700);
	}

	public TestTransmitter(boolean powered, String newRegistryName) {
		super(powered);

		this.name = newRegistryName;

		setRegistryName(name);
		setUnlocalizedName(name);

		setCreativeTab(HandsOnModding.ENHANCED_REDSTONE);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		boolean flag = this.shouldBePowered(worldIn, pos, state);
		pos = receiverPos;
        if (!flag)
        {
            worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
        }
        else
        {
            worldIn.setBlockState(pos, this.getPoweredState(state), 2);

            if (!flag)
            {
//                worldIn.updateBlockTick(pos, this.getPoweredState(state).getBlock(), this.getTickDelay(state), -1);
            }
        }
	}
	
	
	public static List<BlockPos> getAvailableReceivers() {
		return availableReceivers;
	}
	
//	@Override
//	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
//		 if (!this.isLocked(worldIn, pos, state))
//	        {
//	            boolean flag = this.shouldBePowered(worldIn, pos, state);
//
//	            if (!worldIn.isBlockTickPending(pos, this))
//	            {
//	                int i = -1;
//
//	                if (this.isFacingTowardsRepeater(worldIn, pos, state))
//	                {
//	                    i = -3;
//	                }
//	                else if (this.isRepeaterPowered)
//	                {
//	                    i = -2;
//	                }
//
//	                worldIn.updateBlockTick(pos, this, this.getDelay(state), i);
//	            }
//	        }
//	}
	
	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		Integer integer = (Integer)unpoweredState.getValue(DELAY);
		Boolean obool = (Boolean)unpoweredState.getValue(LOCKED);
		EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
		return BlockReceiver.POWERED.getDefaultState().withProperty(FACING, enumfacing).withProperty(DELAY, integer).withProperty(LOCKED, obool);
	}

	
	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		   Integer integer = (Integer)poweredState.getValue(DELAY);
	       Boolean obool = (Boolean)poweredState.getValue(LOCKED);
	       EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
	       return BlockReceiver.UNPOWERED.getDefaultState().withProperty(FACING, enumfacing).withProperty(DELAY, integer).withProperty(LOCKED, obool);
	   
	}
	
//	@Override
//	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
//		// TODO Auto-generated method stub
//		super.updateTick(worldIn, pos, state, rand);
//	}
//	
//	@Override
//	protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
//		if(Redstone.proxy.testField) {
//			return true; 
//			}
//		else
//			return false;
//	}
	
//	public static boolean isDiode(IBlockState state)
//    {
//        return true;
//    }
	
	
//	@Override
//	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
//			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//		// TODO Auto-generated method stub
////		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
//		
//		return Redstone.testField = Redstone.testField == true ? false : true;
//	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		availableReceivers = getAvailableReceivers(pos, worldIn);
		
		 HandsOnModding.proxy.game.displayGuiScreen(new GuiConnectToReceiver(HandsOnModding.proxy.game.currentScreen));
		 return true;
	}
	
	
	public List<BlockPos> getAvailableReceivers(BlockPos centralBlock, World world) {
		
		List<BlockPos> receivers = new ArrayList<>();
		
		int radius = SIGNAL_STRENGTH;
		BlockPos radiusDistance = new BlockPos(radius,radius,radius);
		BlockPos min = centralBlock.subtract(radiusDistance);
		BlockPos max = centralBlock.add(radiusDistance);
		
		for(BlockPos blockPos : BlockPos.getAllInBox(min, max)) {
		
			if (world.getBlockState(blockPos).getBlock() instanceof BlockReceiver) {
				receivers.add(blockPos);
			}
			
//			if (world.getBlockState(blockPos).getBlock() == ) {
//				receivers.add(blockPos);
//			}
		}
		
		return receivers;
	}
	
}
