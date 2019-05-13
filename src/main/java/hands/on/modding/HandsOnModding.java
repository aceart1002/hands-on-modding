package hands.on.modding;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import proxy.CommonProxy;
import tabs.TabEnhancedRedstone;

@Mod(modid = HandsOnModding.MODID, name = HandsOnModding.NAME, version = HandsOnModding.VERSION)
public class HandsOnModding
{
    public static final String MODID = "HOM";
    public static final String NAME = "Hands On Modding";
    public static final String VERSION = "1.0.0.1";

    @SidedProxy(serverSide = "proxy.CommonProxy", clientSide = "proxy.ClientProxy")
    public static CommonProxy proxy;
    
    public static final TabEnhancedRedstone ENHANCED_REDSTONE = 
    		new TabEnhancedRedstone();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
