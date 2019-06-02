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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import tiles.ContainsTile;

@Mod.EventBusSubscriber(modid = HandsOnModding.MODID)
public class Registrator {


	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		for(Registrable unregistered : ModBlocks.ALL_BLOCKS) {
			String customName = unregistered.getCustomRegistryName();

			((Block)unregistered).setUnlocalizedName(customName);
			((Block)unregistered).setRegistryName(customName);
			//((Block)unregistered).setRegistryName(HandsOnModding.MODID + ":" + customName);

		}
		
		IForgeRegistry<Block> registry = event.getRegistry();
		for(Registrable unregistered : ModBlocks.ALL_BLOCKS) {
			registry.register((Block)unregistered);
		}
		
		for(ContainsTile<?> blockWithTile : ModBlocks.BLOCKS_WITH_TILES) {
//			ContainsTile<?> unregistered = (ContainsTile<?>) block;
			GameRegistry.registerTileEntity((blockWithTile).getTileEntityClass(), ((Registrable)blockWithTile).getCustomRegistryName());
		}

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		for(Registrable block : ModBlocks.ALL_BLOCKS) {
			Block unregistered = (Block) block;
			Item itemBlock = new ItemBlock(unregistered).setRegistryName
					(unregistered.getRegistryName());
			registry.register(itemBlock);
		}

	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {

		for(Registrable block : ModBlocks.ALL_BLOCKS) {
			
			Block unregistered = (Block) block;
			Item item = Item.getItemFromBlock(unregistered);
			String id = unregistered.getRegistryName().toString();
			
			
//			String fullVariant = "inventory";
			//if(id.equals("hands_on_modding:unpowered_transmitter")) fullVariant = "facing=north";
			
			ModelLoader.setCustomModelResourceLocation(
					item, 0, new ModelResourceLocation(id, "inventory"));
			//HandsOnModding.MODID + ":" + id
		}
	}

}
