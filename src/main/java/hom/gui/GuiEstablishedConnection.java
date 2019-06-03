package hom.gui;

import java.io.IOException;

import hom.HandsOnModding;
import hom.blocks.AbstractWirelessBlock.RemoteData;
import hom.network.ConnectionMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class GuiEstablishedConnection extends GuiAbstractRemoteConnection {

	private GuiButton disconnectButton;

	//TODO add title?
	protected String title = "Receiver connection";


	public GuiEstablishedConnection(final GuiScreen guiScreen) {
		super(guiScreen);
	}

	@Override
	public void initGui() {

		super.initGui();

		disconnectButton = new GuiButton(id++, width - 220, height - 30, "Disconnect");
		disconnectButton.setWidth(140);
		buttonList.add(disconnectButton);


		GuiRemoteEntry connection = constructConnectedEntry();
		showConnectionCandidate(connection);
	}

	GuiRemoteEntry constructConnectedEntry() {

		RemoteData data = HandsOnModding.proxy.wireless.getRemoteData();
		GuiRemoteEntry entry = new GuiRemoteEntry(null, data.remoteTag, data.remotePos);
		return entry;
	}

	@Override
	protected void actionPerformed(final GuiButton guiButton) {
		if (guiButton.enabled) {
			if(guiButton.id == disconnectButton.id) {
				onDisconnectMessage();
				mc.displayGuiScreen(parentScreen);

			}
		}
		super.actionPerformed(guiButton);
	}

	@Override
	void onExitMessage() {
		String text1 = localTagField.getText();
		String text2 = candidateTagField.getText();
		if(!(text1.equals("") && text2.equals(""))) {
			IMessage message = new ConnectionMessage(text1, text2, new BlockPos(0,0,0), false, 2);
			HandsOnModding.proxy.packetsToServer.sendToServer(message);
		}

	}

	private void onDisconnectMessage() {

		String tag1 = localTagField.getText();
		String tag2 = candidateTagField.getText();
		BlockPos remotePos = candidate.getPosition();
		boolean connection = false;

		ConnectionMessage message = new ConnectionMessage(tag1, tag2, remotePos, connection, 3);
		HandsOnModding.proxy.packetsToServer.sendToServer(message);

	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseEvent) throws IOException {

		super.mouseClicked(mouseX, mouseY, mouseEvent);


	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {

		super.mouseReleased(mouseX, mouseY, state);


	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char character, final int code) throws IOException {

		super.keyTyped(character, code);

	}


	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void handleMouseInput() throws IOException {

		super.handleMouseInput();

	}



}
