package hom.tabs;

import hom.HandsOnModding;
import hom.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabEnhancedRedstone extends CreativeTabs {

	public TabEnhancedRedstone() {
		super(HandsOnModding.MODID);
		setBackgroundImageName("item_search.png");
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack((Block) ModBlocks.UNPOWERED_RECEIVER);
	}
	
}