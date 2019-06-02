package tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileRemoteConnection extends TileEntity {
	
	private String localTag = "";
	
	private boolean isConnected = false;

	private boolean isPowered;
	
	private int remotePosX;
	private int remotePosY;
	private int remotePosZ;
	
	public String getLocalTag() {
		return localTag;
	}

	public void setLocalTag(String name) {
		localTag = name;
		markDirty();
	}
	
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
	
	public BlockPos getRemotePosition() {
//		readFromNBT(new NBTTagCompound());
		return new BlockPos(remotePosX,remotePosY,remotePosZ);
	}
	
	public void setRemotePosition(BlockPos pos) {
		remotePosX = pos.getX();
		remotePosY = pos.getY();
		remotePosZ = pos.getZ();
	
//		writeToNBT(new NBTTagCompound());
		markDirty();
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();//getUpdateTag();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
//	private void updateTile() {
//		NBTTagCompound tagUpdate = writeToNBT(new NBTTagCompound());
//		readFromNBT(tagUpdate);
//	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {

		tagCompound.setString("name", localTag);
		
		tagCompound.setBoolean("isConnected", isConnected);
		
        tagCompound.setInteger("posX", remotePosX);
        tagCompound.setInteger("posY", remotePosY);
        tagCompound.setInteger("posZ", remotePosZ);
        
        tagCompound.setBoolean("powered", isPowered);

        return super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {

    	localTag = tagCompound.getString("name");
    	
    	isConnected = tagCompound.getBoolean("isConnected");
    	
        remotePosX = tagCompound.getInteger("posX");
        remotePosY = tagCompound.getInteger("posY");
        remotePosZ = tagCompound.getInteger("posZ");
        
        isPowered = tagCompound.getBoolean("powered");

        super.readFromNBT(tagCompound);
    }
    
//    @Override
//	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
//	{
//	    return (oldState.getBlock() != newSate.getBlock());
//	}
}
