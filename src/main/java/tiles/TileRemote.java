package tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRemote extends TileEntity {
	
	private boolean isConnected = false;

	private boolean isPowered;
	
	private int posX;
	private int posY;
	private int posZ;
	
//	@Override
//	public void setPos(BlockPos posIn) {
//		if(pos != getPosition()) {
//			super.setPos(posIn);
//		}
//		
//	}
	
	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean connection) {
		this.isConnected = connection;

		markDirty();
	}
	
	public void setPower(boolean powered) {
		isPowered = powered;

		markDirty();
	}
	
	public boolean getPower() {
		return isPowered;
	}
	
	public BlockPos getPosition() {
//		readFromNBT(new NBTTagCompound());
		return new BlockPos(posX,posY,posZ);
	}
	
	public void setPosition(BlockPos pos) {
		posX = pos.getX();
		posY = pos.getY();
		posZ = pos.getZ();
	
//		writeToNBT(new NBTTagCompound());
		markDirty();
	}
	
	private void updateTile() {
		NBTTagCompound tagUpdate = writeToNBT(new NBTTagCompound());
		readFromNBT(tagUpdate);
	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {

		tagCompound.setBoolean("isConnected", isConnected);
		
        tagCompound.setInteger("posX", posX);
        tagCompound.setInteger("posY", posY);
        tagCompound.setInteger("posZ", posZ);
        
        tagCompound.setBoolean("powered", isPowered);

        return super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {

    	isConnected = tagCompound.getBoolean("isConnected");
    	
        posX = tagCompound.getInteger("posX");
        posY = tagCompound.getInteger("posY");
        posZ = tagCompound.getInteger("posZ");
        
        tagCompound.getBoolean("powered");

        super.readFromNBT(tagCompound);
    }
    
//    @Override
//	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
//	{
//	    return (oldState.getBlock() != newSate.getBlock());
//	}
}
