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

@Mod.EventBusSubscriber(modid = HandsOnModding.MODID)
public class Registrator implements ModBlocks {

	static void setInnerRegistryNames(Registrable[] blocks) {

		for(Registrable block : blocks) {
			String customName = block.getCustomRegistryName();
			Block unregistered = (Block) block;

			unregistered.setUnlocalizedName(customName);
			unregistered.setRegistryName(customName);

		}

	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		setInnerRegistryNames(ModBlocks.BLOCKS);

		IForgeRegistry<Block> registry = event.getRegistry();
		for(Registrable block : ModBlocks.BLOCKS) {
			Block unregistered = (Block) block;
			registry.register(unregistered);
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		for(Registrable block : ModBlocks.BLOCKS) {
			Block unregistered = (Block) block;
			Item itemBlock = new ItemBlock(unregistered).setRegistryName
					(unregistered.getRegistryName());
			registry.register(itemBlock);
		}

	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {

		for(Registrable block : ModBlocks.BLOCKS) {
			Block unregistered = (Block) block;
			Item item = Item.getItemFromBlock(unregistered);
			String id = unregistered.getRegistryName().toString();
			ModelLoader.setCustomModelResourceLocation(
					item, 0, new ModelResourceLocation
					(HandsOnModding.MODID + ":" + id, "inventory"));
		}
	}

}
