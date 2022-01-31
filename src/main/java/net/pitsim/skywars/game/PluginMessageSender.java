package net.pitsim.skywars.game;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pitsim.skywars.PitSim;
import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessageSender {

	public static void sendStart() {
		String id = PitSim.INSTANCE.getConfig().getString("server-ID");
		if(id == null) return;


		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("Skywars");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(id);
			msgout.writeUTF("GAME_START");
			msgout.writeUTF("");
			msgout.writeUTF("");
		} catch (IOException exception){
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		Bukkit.getServer().sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}

	public static void sendEnd() {
		String id = PitSim.INSTANCE.getConfig().getString("server-ID");
		if(id == null) return;


		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("Skywars");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(id);
			msgout.writeUTF("GAME_END");
			msgout.writeUTF("");
			msgout.writeUTF("");
		} catch (IOException exception){
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		Bukkit.getServer().sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}
}
