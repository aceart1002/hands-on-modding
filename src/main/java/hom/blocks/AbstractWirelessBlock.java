package hom.blocks;

import hom.tiles.TileRemoteConnection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractWirelessBlock extends AbstractDiodeBlock<TileRemoteConnection> {
	
	public BlockData currentData;
	public RemoteData remoteData;
	
	protected AbstractWirelessBlock(boolean powered, String registryName) {
		super(powered, registryName);
	}

	public void updateTile(BlockPos tilePos) {
		IBlockState state = getWorld().getBlockState(tilePos); 
		getWorld().notifyBlockUpdate(tilePos, state, state, 3);
	}
	
	protected void setConnection(BlockPos remotePos, boolean connected) {

		World worldIn = getWorld();
		BlockPos currentPos = getPosition();

		TileRemoteConnection localTile = getTileEntity(worldIn, currentPos);
		TileRemoteConnection remoteTile = getTileEntity(worldIn, remotePos);

		if(localTile != null) {
			localTile.setConnected(connected);
			localTile.setRemotePosition(remotePos);
			updateTile(currentPos);
		}

		if (remoteTile != null) {
			remoteTile.setConnected(connected);
			remoteTile.setRemotePosition(currentPos);
			updateTile(remotePos);
		}
		
		
	}

	BlockPos getRemotePosition() {

		if(isConnectedToRemote()) {
			TileRemoteConnection localTile = getTileEntity(getWorld(), getPosition());
			return localTile.getRemotePosition();
		} else { 
			return null;
		}

	}

	boolean getRemotePower() {
		TileRemoteConnection localTile = getTileEntity(getWorld(), getRemotePosition());
		return localTile.getPower();
	}

	String getRemoteTag() {
	
		TileRemoteConnection remoteTile = getTileEntity(getWorld(), getRemotePosition());
		
		return remoteTile.getLocalTag();
	}
	
	String getTag() {
		
		TileRemoteConnection localTile = getTileEntity(getWorld(), getPosition());
		if(localTile != null)
			return localTile.getLocalTag();
		else
			return "";
	}

	public void setRemoteTag(String name) {
		TileRemoteConnection tile = getTileEntity(getWorld(), getRemotePosition());
		tile.setLocalTag(name);
		
		updateTile(getRemotePosition());
	}
	
	public void setTag(String name) {
		TileRemoteConnection tile = getTileEntity(getWorld(), getPosition());
		tile.setLocalTag(name);
		
		updateTile(getPosition());
		
	}
	
	boolean isConnectedToRemote() {

		TileRemoteConnection localTile = getTileEntity(getWorld(), 
				getPosition());

		if (localTile != null)
			return localTile.isConnected();
		else
			return false;
	}
	
	@Override
	public Class<TileRemoteConnection> getTileEntityClass() {

		return TileRemoteConnection.class;
	}

	@Override
	public TileRemoteConnection createTileEntity(World world, IBlockState blockState) {

		return new TileRemoteConnection();
	}
	
	public class RemoteData {

		public final BlockPos remotePos;
		public final String remoteTag;

		public RemoteData(String name, BlockPos remote) {
			remotePos = remote;
			remoteTag = name;
		}
	

	}
	public class BlockData {

		public final World world;
		public final BlockPos position;
		public final String tag;
		public final boolean isConnected;

		BlockData(World world, BlockPos position, String tag, boolean connected) {
			this.world = world;
			this.position = position;
			this.tag = tag;
			this.isConnected = connected;
		}

	}
	public BlockData getBlockData() {
		return currentData;
	}

	public RemoteData getRemoteData() {
		return remoteData;
	}
	
	abstract World getWorld();
	abstract BlockPos getPosition();
}
