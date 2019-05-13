package gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiConnectToReceiver extends GuiScreen {

    private GuiButton connect = null;
    GuiListReceivers receiversSlots;
    GuiTextField transmitterTag;

    protected String title = "Receiver connection";
   

    public GuiConnectToReceiver(final GuiScreen guiScreen) {
         
    }

    @Override
    public void initGui() {
        int id = 0;
        int i = 0;
    
        receiversSlots = new GuiListReceivers(this, mc, width, height, 32, height-64, 36);
        
        //renderer?
        transmitterTag = new GuiTextField(i++, this.fontRenderer, 20, 40, 150, 20);
        
        this.connect = new GuiButton(id++, width - 200, height - 40, "Connect");
        buttonList.add(connect);

    }


    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
         
        	if(guiButton.id == connect.id) {
        		
        	}
        	
            
        }
    }
    
    public void selectReceiver(GuiListReceiversEntry entry) {
    	
    }
    

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseEvent) throws IOException {
        
        super.mouseClicked(mouseX, mouseY, mouseEvent);
        receiversSlots.mouseClicked(mouseX, mouseY, mouseEvent);
        transmitterTag.mouseClicked(mouseX, mouseY, mouseEvent);
        
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
    	
    	super.mouseReleased(mouseX, mouseY, state);
    	receiversSlots.mouseReleased(mouseX, mouseY, state);

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
        transmitterTag.textboxKeyTyped(character, code);   
        
    }


    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
      
    	receiversSlots.drawScreen(mouseX, mouseY, partialTicks);
    	
    	super.drawScreen(mouseX, mouseY, partialTicks);

        transmitterTag.drawTextBox();
                 
    }
    
    @Override
    public void handleMouseInput() throws IOException {

    	super.handleMouseInput();
    	receiversSlots.handleMouseInput();
    }
   
}
