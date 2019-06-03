package hom.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import scala.annotation.varargs;

public class ConnectionMessage implements IMessage {

	String localTag;
	String remoteTag;
	
	boolean connect;
	
	Vec3d remotePos;
	
	int processCase;

	BlockPos getRemotePos() {
		return new BlockPos(remotePos);
	}

	public ConnectionMessage() {}

	public ConnectionMessage(String tag, String secondTag, BlockPos remotePos, boolean connect,
			int processCase) {
		localTag = tag;
		remoteTag = secondTag;

		this.connect = connect;

		int x = remotePos.getX();
		int y = remotePos.getY();
		int z = remotePos.getZ();
		
		this.remotePos = new Vec3d(x,y,z);
		
		this.processCase = processCase;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		
		localTag = ByteBufUtils.readUTF8String(buf);
		remoteTag = ByteBufUtils.readUTF8String(buf);

		double x = buf.readDouble();
		double y = buf.readDouble();
		double z = buf.readDouble();
		remotePos = new Vec3d(x,y,z);
		
		connect = buf.readBoolean();
		
		processCase = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		ByteBufUtils.writeUTF8String(buf, localTag);
		ByteBufUtils.writeUTF8String(buf, remoteTag);
		
		buf.writeDouble(remotePos.x);
		buf.writeDouble(remotePos.y);
		buf.writeDouble(remotePos.z);
		
		buf.writeBoolean(connect);
		
		buf.writeInt(processCase);
	}



}
