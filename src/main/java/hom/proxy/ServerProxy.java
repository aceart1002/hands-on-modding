package hom.proxy;

import hom.Modes;
import hom.blocks.WirelessDiode;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

	@Override
	public void displayScreen(Modes mode) {
	}

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postInit(final FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}
