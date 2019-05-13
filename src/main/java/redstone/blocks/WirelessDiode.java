package redstone.blocks;

import hands.on.modding.HandsOnModding;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.creativetab.CreativeTabs;
import registry.Registrable;

public abstract class WirelessDiode extends BlockRedstoneDiode implements Registrable {

	private String registryName;
	
	protected WirelessDiode(boolean powered, String registryName) {
		super(powered);
		setCustomRegistryName(registryName);
		
		setCreativeTab(HandsOnModding.ENHANCED_REDSTONE);
	}
	
	@Override
	public void setCustomRegistryName(String newName) {
		registryName = newName;
		
	}
	
	@Override
	public String getCustomRegistryName() {
		return registryName;
	}
	
	
	

}
