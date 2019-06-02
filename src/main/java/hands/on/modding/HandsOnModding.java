package hands.on.modding;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import network.ConnectionMessage;
import network.ServerMessageProcessor;
import proxy.CommonProxy;
import tabs.TabEnhancedRedstone;

@Mod(modid = HandsOnModding.MODID, name = HandsOnModding.NAME, version = HandsOnModding.VERSION)
public class HandsOnModding
{
	public static final String MODID =  "hands_on_modding";
	public static final String NAME =  "Hands on modding";
	public static final String VERSION = "1.0.0";

	@Mod.Instance(HandsOnModding.MODID)
	public static HandsOnModding instance;

	@SidedProxy(clientSide = "proxy.ClientProxy", serverSide = "proxy.ServerProxy")
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
