package hom;

import hom.proxy.CommonProxy;
import hom.tabs.TabEnhancedRedstone;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = HandsOnModding.MODID, name = HandsOnModding.NAME, version = HandsOnModding.VERSION)
public class HandsOnModding
{
	public static final String MODID =  "hands_on_modding";
	public static final String NAME =  "Hands on modding";
	public static final String VERSION = "1.0.0";

	@Mod.Instance(HandsOnModding.MODID)
	public static HandsOnModding instance;

	@SidedProxy(clientSide = "hom.proxy.ClientProxy", serverSide = "hom.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static final TabEnhancedRedstone WIRELESS_REDSTONE = 
			new TabEnhancedRedstone();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{

		proxy.preInit(event);

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		proxy.postInit(event);
	}
}
