package proxy;

import blocks.test.WirelessDiode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public static boolean testField = false;

	public static final Minecraft game = Minecraft.getMinecraft();
	private WirelessDiode wireless;

	@Override
	public void setWireless(WirelessDiode wireless) {
		this.wireless = wireless;
	}

	public void connectToRemote(BlockPos remotePos) {
		wireless.connectToRemote(wireless.currentWorld, wireless.currentPos, remotePos);
	}
	
	public void disconnectFromRemote() {
//		wireless.disconnectFromRemote(worldIn, currentPos, state);
	}
	
	public void displayScreen(GuiScreen screen) {
		game.displayGuiScreen(screen);
	}
	
	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

//		MinecraftForge.EVENT_BUS.register(InputHandler.INSTANCE);


	}

	

	
}
