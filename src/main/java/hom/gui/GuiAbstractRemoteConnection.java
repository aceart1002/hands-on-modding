package hom.gui;

import java.io.IOException;

import hom.HandsOnModding;
import hom.blocks.AbstractWirelessBlock.BlockData;
import hom.network.ConnectionMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class GuiAbstractRemoteConnection extends GuiScreen {

	//GuiLabel localTagLabel;

	GuiTextField localTagField;
	GuiTextField localPositionField;
	GuiTextField candidateTagField;
	GuiTextField candidatePositionField;

	GuiRemoteEntry candidate;

	int id = 0;

	private GuiButton exitButton;

	BlockPos localPosition;

	protected GuiScreen parentScreen;
	//TODO add title?
	protected String title = "Receiver connection";

	public GuiAbstractRemoteConnection(final GuiScreen guiScreen) {
		parentScreen = guiScreen;
	}

	public void showConnectionCandidate(GuiRemoteEntry candidate) {

		this.candidate = candidate;

		String tag = candidate.getTag();
		String position = candidate.getPosition().toString();

		candidateTagField.setText(tag);
		candidatePositionField.setText(position);

	}

	@Override
	public void initGui() {
		//        localTagLabel = new GuiLabel(fontRenderer, id++, 5, 5, 150, 20, 234234);

		localTagField = new GuiTextField(id++, this.fontRenderer, 10, 10, 150, 20);
		localPositionField = new GuiTextField(id++, this.fontRenderer, 200, 10, 150, 20);
		displayLocalTagAndPosition();

		candidateTagField = new GuiTextField(id++, this.fontRenderer, 10, 50, 150, 20);
		candidatePositionField = new GuiTextField(id++, this.fontRenderer, 200, 50, 150, 20);

		exitButton = new GuiButton(id++, width - 420, height - 30, "Exit");
		exitButton.setWidth(140);
		buttonList.add(exitButton);

	}

	void displayLocalTagAndPosition() {

		BlockData data = HandsOnModding.proxy.wireless.getBlockData();

		localPosition = data.position;

		localTagField.setText(data.tag);
		localPositionField.setText(localPosition.toString());
	}


	@Override
	protected void actionPerformed(final GuiButton guiButton) {
		if (guiButton.enabled) {

			if(guiButton.id == exitButton.id) {
				onExitMessage();
				mc.displayGuiScreen(parentScreen);
			}
		}
	}
	
	void onExitMessage() {
		String text = localTagField.getText();
		if(!text.equals("")) {
			IMessage message = new ConnectionMessage(localTagField.getText(), "", new BlockPos(0,0,0), false, 1);
			HandsOnModding.proxy.packetsToServer.sendToServer(message);
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseEvent) throws IOException {

		super.mouseClicked(mouseX, mouseY, mouseEvent);

		localTagField.mouseClicked(mouseX, mouseY, mouseEvent);
		candidateTagField.mouseClicked(mouseX, mouseY, mouseEvent);

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
		//        if (code == Keyboard.KEY_ESCAPE) {
		//            this.mc.displayGuiScreen(this);
		//            return;
		//        }
		super.keyTyped(character, code);
		localTagField.textboxKeyTyped(character, code);
		candidateTagField.textboxKeyTyped(character, code);

	}


	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		try {
			localTagField.drawTextBox();
			localPositionField.drawTextBox();
			candidateTagField.drawTextBox();
			candidatePositionField.drawTextBox();

			super.drawScreen(mouseX, mouseY, partialTicks);
		} catch (Exception e) {
			System.out.println("draw screen exception");
		}
	}

	@Override
	public void handleMouseInput() throws IOException {

		super.handleMouseInput();
	}

}
