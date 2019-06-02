package registry;

import blocks.redstone.wireless.BlockReceiver;
import blocks.redstone.wireless.BlockTransmitter;
import tiles.ContainsTile;


public class ModBlocks {
	
	//redstone
	public static final Registrable UNPOWERED_RECEIVER = BlockReceiver.UNPOWERED;
	public static final Registrable UNPOWERED_TRANSMITTER = BlockTransmitter.UNPOWERED;
	
	public static final Registrable POWERED_RECEIVER = BlockReceiver.POWERED;
	public static final Registrable POWERED_TRANSMITTER = BlockTransmitter.POWERED;
	
	public static final ContainsTile WIRELESS_TILE = (ContainsTile) UNPOWERED_TRANSMITTER;
	
	
	public static final Registrable[] ALL_BLOCKS  = {UNPOWERED_TRANSMITTER, UNPOWERED_RECEIVER,
			POWERED_RECEIVER, POWERED_TRANSMITTER};

	
	public static final ContainsTile[] BLOCKS_WITH_TILES = {WIRELESS_TILE};
}
