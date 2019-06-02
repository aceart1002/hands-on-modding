package proxy;

import blocks.redstone.wireless.WirelessDiode;
import hands.on.modding.HandsOnModding;
import hands.on.modding.Modes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import network.ConnectionMessage;
import network.ServerMessageProcessor;

public abstract class CommonProxy {

	public WirelessDiode wireless;

	public static  SimpleNetworkWrapper packetsToServer;
	private static final byte MESSAGE_ID = 50;
	
	abstract public void displayScreen(Modes operationMode);

	public void setWireless(WirelessDiode wireless) {
		this.wireless = wireless;
	}

	public void preInit(final FMLPreInitializationEvent event) {


	}
	public void init(final FMLInitializationEvent event) {
		packetsToServer = NetworkRegistry.INSTANCE.newSimpleChannel(HandsOnModding.MODID +
				HandsOnModding.VERSION);
		packetsToServer.registerMessage(ServerMessageProcessor.class, ConnectionMessage.class, 
				MESSAGE_ID, Side.SERVER);
		
	}
	public void postInit(final FMLPostInitializationEvent event) {

	}

}
