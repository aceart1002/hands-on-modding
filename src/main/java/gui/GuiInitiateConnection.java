package gui;

import java.io.IOException;

import hands.on.modding.HandsOnModding;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import network.ConnectionMessage;

public class GuiInitiateConnection extends GuiAbstractRemoteConnection {

    GuiRemoteSlots remoteSlots;
    
    private GuiButton connectButton;
   
    //TODO add title?
    protected String title = "Receiver connection";
   

    public GuiInitiateConnection(final GuiScreen guiScreen) {
    	super(guiScreen);
    }

    @Override
    public void initGui() {

    	super.initGui();
    	
        remoteSlots = new GuiRemoteSlots(this, mc, width, height, 90, height - 40, 25);
        remoteSlots.setShowSelectionBox(true);
        
        connectButton = new GuiButton(id++, width - 220, height - 30, "Connect");
        connectButton.setWidth(140);
        connectButton.enabled = false;
        buttonList.add(connectButton);

    }

    @Override
    public void showConnectionCandidate(GuiRemoteEntry candidate) {
    	super.showConnectionCandidate(candidate);
    	switchConnectButton();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
        	if(guiButton.id == connectButton.id) {
        		
        		onConnectMessage();

        		mc.displayGuiScreen(parentScreen);
        	}
        }
        super.actionPerformed(guiButton);
    }

    private void onConnectMessage() {
    	String tag1 = localTagField.getText();
    	String tag2 = candidateTagField.getText();
    	BlockPos remotePos = candidate.getPosition();
    	boolean connection = true;
    	ConnectionMessage message = new ConnectionMessage(tag1, tag2, remotePos, connection, 3);
    	HandsOnModding.proxy.packetsToServer.sendToServer(message);
    }


    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseEvent) throws IOException {
        
        super.mouseClicked(mouseX, mouseY, mouseEvent);
        remoteSlots.mouseClicked(mouseX, mouseY, mouseEvent);
        
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
    	
    	super.mouseReleased(mouseX, mouseY, state);
    	remoteSlots.mouseReleased(mouseX, mouseY, state);

    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    @Override
    protected void keyTyped(final char character, final int code) throws IOException {

        super.keyTyped(character, code);
        switchConnectButton();
    }

    private void switchConnectButton() {
    	connectButton.enabled =
    			!candidateTagField.getText().equals("")
         		&& !localTagField.getText().equals("")
         		&& candidate != null;
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    	try {
    	remoteSlots.drawScreen(mouseX, mouseY, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
    	} catch (Exception e) {
    		System.out.println("draw slots exception");
    	}
    }
    
    @Override
    public void handleMouseInput() throws IOException {

    	super.handleMouseInput();
    	remoteSlots.handleMouseInput();
    }

   
}
