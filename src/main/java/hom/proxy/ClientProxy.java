package hom.proxy;

import hom.Modes;
import hom.blocks.WirelessDiode;
import hom.gui.GuiEstablishedConnection;
import hom.gui.GuiInitiateConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public static final Minecraft game = Minecraft.getMinecraft();

//	public void disconnectFromRemote() {
//		World world = wireless.currentData.world;
//		BlockPos pos = wireless.currentData.position;
//		wireless.disconnectFromRemote(world, pos);
//	}


//	@Override
//	public void updateTag(String name, BlockPos pos) {
//		World world = wireless.currentData.world;
//		wireless.setTag(name, pos, world);
//	}

	@Override
	public void displayScreen(Modes mode) {
		try {
			switch(mode) {
			case NEW_CONNECTION: 
				game.displayGuiScreen(
						new GuiInitiateConnection(game.currentScreen));
				break;
			case EXISTING_CONNECTION: 
				game.displayGuiScreen(
						new GuiEstablishedConnection(game.currentScreen));
				break;
			}
		} catch (Exception e) {
			System.out.println("Screen render exception");
		}
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
