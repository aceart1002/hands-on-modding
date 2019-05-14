package registry;

import blocks.test.BlockReceiver;
import blocks.test.BlockTransmitter;
import net.minecraft.block.Block;


public class ModBlocks {
	
//	public static Block WIRELESS = new BlockTransmitter(true, "redstone_transmitter");
	
//	public static Registrable TRANSMITTER = BlockTransmitter.UNPOWERED;
//	public static Registrable RECEIVER = BlockReceiver.UNPOWERED;
	
	public static Registrable test = new BlockReceiver(true, "test");
	
//	public static Registrable POWERED_RECEIVER = new BlockReceiver(true, "powered_receiver");
//	public static Registrable UNPOWERED_RECEIVER = new BlockReceiver(false, "unpowered_receiver");
	
//	public static Registrable POWERED_RECEIVER = BlockReceiver.POWERED_RECEIVER;
	public static Registrable UNPOWERED_RECEIVER = BlockReceiver.UNPOWERED;
	public static Registrable UNPOWERED_TRANSMITTER = BlockTransmitter.UNPOWERED;
	
	public static Registrable POWERED_RECEIVER = BlockReceiver.POWERED;
	public static Registrable POWERED_TRANSMITTER = BlockTransmitter.POWERED;
	
//	Block POWERED_REPEATER = new TestTransmitter(true, "different_repeater");
	
//	Block LEVER = new TestLever("test_lever");
	
	public static Registrable[] BLOCKS  = {UNPOWERED_TRANSMITTER, UNPOWERED_RECEIVER,
			POWERED_RECEIVER, POWERED_TRANSMITTER, test};

	
}
