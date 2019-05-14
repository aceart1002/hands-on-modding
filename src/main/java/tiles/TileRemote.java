package tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRemote extends TileEntity {
	
	private boolean isConnected = false;

	private boolean isPowered;
	
	private int x;
	private int y;
	private int z;
	
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
		return new BlockPos(x,y,z);
	}
	
	public void setPosition(BlockPos pos) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		
		markDirty();
	}
	
	private void updateTile() {
		NBTTagCompound tagUpdate = writeToNBT(new NBTTagCompound());
		readFromNBT(tagUpdate);
	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {

		tagCompound.setBoolean("isConnected", isConnected);
		
        tagCompound.setInteger("x", x);
        tagCompound.setInteger("y", y);
        tagCompound.setInteger("z", z);
        
        tagCompound.setBoolean("powered", isPowered);

        return super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {

    	isConnected = tagCompound.getBoolean("isConnected");
    	
        x = tagCompound.getInteger("x");
        y = tagCompound.getInteger("y");
        z = tagCompound.getInteger("z");
        
        tagCompound.getBoolean("powered");

        super.readFromNBT(tagCompound);
    }
    
    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
	    return (oldState.getBlock() != newSate.getBlock());
	}
}
