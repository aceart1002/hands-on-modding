package gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import blocks.test.BlockTransmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiListWorldSelectionEntry;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSummary;
import redstone.blocks.TestTransmitter;

public class GuiListReceivers extends GuiListExtended {

	private final GuiConnectToReceiver trConnection;
	
	private final List<GuiListReceiversEntry> receivers = new ArrayList();
	private int selectedIdx = -1;
	
	public GuiListReceivers(GuiConnectToReceiver parentScreen, Minecraft clientIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn)
    {
        super(clientIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        trConnection = parentScreen;
        refreshList();
	}
	
	public void refreshList()
    {
        //sort list here
		
//		List<BlockPos> list = TestTransmitter.getAvailableReceivers();
		List<BlockPos> list = BlockTransmitter.getAvailableReceivers();
		
		for (BlockPos pos : list)
        {
            receivers.add(new GuiListReceiversEntry(this, pos));
        }
    }
	
	 public GuiListReceiversEntry getListEntry(int index)
	    {
	        return this.receivers.get(index);
	    }

	    protected int getSize()
	    {
	        return this.receivers.size();
	    }

	    protected int getScrollBarX()
	    {
	        return super.getScrollBarX() + 20;
	    }
	    
	    public int getListWidth()
	    {
	        return super.getListWidth() + 50;
	    }

	    public void selectReceiver(int idx)
	    {
	        this.selectedIdx = idx;
	        trConnection.selectReceiver(getSelectedReceiver());
	    }
	    
	    
	    protected boolean isSelected(int slotIndex)
	    {
	        return slotIndex == this.selectedIdx;
	    }

	    @Nullable
	    public GuiListReceiversEntry getSelectedReceiver()
	    {
	        return this.selectedIdx >= 0 && this.selectedIdx < this.getSize() ? this.getListEntry(this.selectedIdx) : null;
	    }

	    public GuiConnectToReceiver getParentGui()
	    {
	        return this.trConnection;
	    }

}
