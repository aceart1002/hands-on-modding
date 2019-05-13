package registry;

import hands.on.modding.HandsOnModding;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import redstone.blocks.WirelessDiode;
import redstone.blocks.TestTransmitter;
import redstone.blocks.TestLever;
import redstone.blocks.Receiver;
import redstone.blocks.Transmitter;


public interface ModBlocks {
	
	Block WIRELESS = new Transmitter(true, "redstone_transmitter");
	
	Block TRANSMITTER = new TestTransmitter(false, "unpowered_transmitter");
	Block RECEIVER = Receiver.UNPOWERED;
	
	
//	Block POWERED_REPEATER = new TestTransmitter(true, "different_repeater");
	
	Block LEVER = new TestLever("test_lever");
	
	Block[] BLOCKS  = {WIRELESS, LEVER, TRANSMITTER, RECEIVER};

	
}
