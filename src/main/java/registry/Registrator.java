package registry;

import blocks.test.BlockReceiver;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import tiles.ContainsTile;

//@Mod.EventBusSubscriber(modid = HandsOnModding.MODID)
public class Registrator {

	static void setInnerRegistryNames(Registrable[] blocks) {
		
		int i = 1; //crutch!
		
		for(Registrable block : blocks) {
			String customName = block.getCustomRegistryName();
			Block unregistered = (Block) block;

			unregistered.setUnlocalizedName(customName);
			unregistered.setRegistryName(customName);

			//TODO temp crutch
			if (i == 1) {
//				registerTile(unregistered, customName);
				i++;
			}
		}
	}
	
	static void registerTile(Block block, String registryName) {
		if(block instanceof ContainsTile<?>) {
			ContainsTile<?> unregistered = (ContainsTile<?>) block;
			GameRegistry.registerTileEntity((unregistered).getTileEntityClass(), registryName);
		}
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		setInnerRegistryNames(ModBlocks.BLOCKS);
		
		//TODO GOVNO
		BlockReceiver block1 = (BlockReceiver) ( ModBlocks.UNPOWERED_RECEIVER);
		GameRegistry.registerTileEntity((block1).getTileEntityClass(), 
				block1.getRegistryName().toString());

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
