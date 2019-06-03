package hom.network;

import hom.HandsOnModding;
import hom.tiles.TileRemoteConnection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerMessageProcessor implements IMessageHandler<ConnectionMessage, IMessage>{

	@Override
	public IMessage onMessage(ConnectionMessage message, MessageContext ctx) {

		EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;

		final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
		playerWorldServer.addScheduledTask(new Runnable() {
			public void run() {
				processMessage(message);
				
			}
		});

		return null;
	}
	
	public void processMessage(ConnectionMessage message) {
		
		if(message.processCase > 2) 
				if(message.connect == true)
					HandsOnModding.proxy.wireless.connectToRemote(message.getRemotePos());
				else if(message.connect == false) {
					HandsOnModding.proxy.wireless.disconnectFromRemote();	
					return;
				}
		
		if(message.processCase > 1)
			HandsOnModding.proxy.wireless.setRemoteTag(message.remoteTag);
		
		HandsOnModding.proxy.wireless.setTag(message.localTag);
	}
	
}
