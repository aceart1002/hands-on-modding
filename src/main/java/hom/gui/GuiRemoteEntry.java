package hom.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.util.math.BlockPos;

public class GuiRemoteEntry implements GuiListExtended.IGuiListEntry {

	private final Minecraft client;
	private final GuiRemoteSlots parentSlots;
	BlockPos position;
	String tag;
	private long lastClickTime;


	public GuiRemoteEntry(GuiRemoteSlots slots, String tag, BlockPos pos)
	{
		this.parentSlots = slots;
		this.client = Minecraft.getMinecraft();
		this.tag = tag;
		position = pos;
		
	}

	
	BlockPos getPosition() {
		return position;
	}
	
	String getTag() {
		return tag;
	}
	
	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY,
			boolean isSelected, float partialTicks) {

		String positionString = position.toString();
		client.fontRenderer.drawStringWithShadow(tag, x, y, 421343);
		client.fontRenderer.drawString(positionString, x + 200, y + parentSlots.getSlotHeight()/3, 37577215);

	}

	@Override
	public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
		
		parentSlots.selectEntry(slotIndex);
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
