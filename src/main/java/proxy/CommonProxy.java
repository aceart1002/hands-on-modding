package proxy;

import blocks.test.WirelessDiode;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class CommonProxy {
	
	 abstract public void displayScreen(GuiScreen screen);
	abstract public void setWireless(WirelessDiode wireless);
	abstract public void connectToRemote(BlockPos remotePos);
	abstract public void disconnectFromRemote();
	
	 public void preInit(final FMLPreInitializationEvent event) {
	    }
	 
	 public void init(final FMLInitializationEvent event) {

	    }


}
