package registry;

import hands.on.modding.HandsOnModding;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class Registrator implements ModBlocks {
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		IForgeRegistry<Block> registry = event.getRegistry();
		
		for(Block block : ModBlocks.BLOCKS)
			registry.register(block);
		
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		for(Block block : ModBlocks.BLOCKS) {
			Item itemBlock = new ItemBlock(block).setRegistryName(block.getRegistryName());
			registry.register(itemBlock);
		}

	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
		for(Block block : ModBlocks.BLOCKS) {
		Item item = Item.getItemFromBlock(block);
		String id = block.getRegistryName().toString();
		ModelLoader.setCustomModelResourceLocation(
				item, 0, new ModelResourceLocation
				(HandsOnModding.MODID + ":" + id, "inventory"));
		}
	}



	
}
