package gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.util.math.BlockPos;

public class GuiListReceiversEntry implements GuiListExtended.IGuiListEntry {

	private final Minecraft client;
	private final GuiConnectToReceiver worldSelScreen;
	private final GuiListReceivers containingListIn;
	BlockPos position;
	private long lastClickTime;


	public GuiListReceiversEntry(GuiListReceivers listIn, BlockPos pos)
	{
		this.containingListIn = listIn;
		this.worldSelScreen = listIn.getParentGui();
		this.client = Minecraft.getMinecraft();
		position = pos;
		
	}

	

	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY,
			boolean isSelected, float partialTicks) {

		String info = position.toString();
		client.fontRenderer.drawString(info, x+32, y+32, 37577215);

	}

	@Override
	public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
		
		containingListIn.selectReceiver(slotIndex);
		if (relativeX <= 32 && relativeX < 32)
        {
			return true;
        }
		//double click
        else if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
        {
          return true;
        }
        else
        {
            this.lastClickTime = Minecraft.getSystemTime();
            return false;
        }

	}

	@Override
	public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePosition(int slotIndex, int x, int y, float partialTicks) {
	}
	
}
