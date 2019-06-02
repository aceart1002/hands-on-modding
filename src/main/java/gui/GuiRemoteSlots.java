package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import blocks.redstone.wireless.BlockTransmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;

public class GuiRemoteSlots extends GuiListExtended {

	private final GuiAbstractRemoteConnection parentScreen;
	
	private final List<GuiRemoteEntry> remoteList = new ArrayList();
	private int selectedIndex = -1;
	
	public GuiRemoteSlots(GuiAbstractRemoteConnection parentScreen, Minecraft clientIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn)
    {
        super(clientIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.parentScreen = parentScreen;
        refreshList();
	}
	
	public void refreshList()
    {
        //TODO sort list here
		
//		List<BlockPos> list = getRemoteConnects; //
		Map<BlockPos, String> tagPositionPairs = BlockTransmitter.returnAvailableConnects();
		
		for(BlockPos position : tagPositionPairs.keySet()) {
			String tag = tagPositionPairs.get(position);
			remoteList.add(new GuiRemoteEntry(this, tag, position));
		}
//		for (BlockPos pos : tagPositionPairs)
//        {
//            remoteList.add(new GuiRemoteEntry(this, pos));
//        }
    }
	
	 public GuiRemoteEntry getListEntry(int index)
	    {
	        return remoteList.get(index);
	    }

	    protected int getSize()
	    {
	        return remoteList.size();
	    }

	    protected int getScrollBarX()
	    {
	        return super.getScrollBarX() + 80;
	    }
	    
	    public int getListWidth()
	    {
	        return super.getListWidth() + 150;
	    }

	    public void selectEntry(int idx)
	    {
	        selectedIndex = idx;
	        parentScreen.showConnectionCandidate(getSelectedCandidate());
	    }
	    
	    
	    protected boolean isSelected(int slotIndex)
	    {
	        return slotIndex == this.selectedIndex;
	    }

	    @Nullable
	    public GuiRemoteEntry getSelectedCandidate()
	    {
	        return this.selectedIndex >= 0 && this.selectedIndex < this.getSize() ? getListEntry(selectedIndex) : null;
	    }

	    public GuiAbstractRemoteConnection getParentGui()
	    {
	        return this.parentScreen;
	    }

	    @Override
	    protected void drawContainerBackground(final Tessellator tessellator) {
	    }
	    
	    @Override
	    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha)
	    {
	    }
}
