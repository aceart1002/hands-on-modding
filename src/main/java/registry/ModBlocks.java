package registry;

import hands.on.modding.HandsOnModding;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import redstone.blocks.WirelessDiode;
import redstone.blocks.TestTransmitter;
import redstone.blocks.TestLever;
import redstone.blocks.BlockReceiver;
import redstone.blocks.BlockTransmitter;


public interface ModBlocks {
	
	Block WIRELESS = new BlockTransmitter(true, "redstone_transmitter");
	
	Registrable TRANSMITTER = BlockTransmitter.UNPOWERED;
	Registrable RECEIVER = BlockReceiver.UNPOWERED;
	
	Registrable test = new BlockReceiver(true, "test");
	
//	Block POWERED_REPEATER = new TestTransmitter(true, "different_repeater");
	
	Block LEVER = new TestLever("test_lever");
	
	Registrable[] BLOCKS  = {TRANSMITTER, RECEIVER, test};

	
}
