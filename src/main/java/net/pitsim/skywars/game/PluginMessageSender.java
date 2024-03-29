package net.pitsim.skywars.game;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pitsim.skywars.PitSim;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
		} catch(IOException exception) {
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		assert p != null;
		p.sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
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
		} catch(IOException exception) {
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		assert p != null;
		p.sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}

	public static void sendPassQuest() {
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
			msgout.writeUTF("PASS_QUEST");

			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < GameManager.questQualified.size(); i++) {
				builder.append(GameManager.questQualified.get(i));
				if(i + 1 != GameManager.questQualified.size()) builder.append(",");
			}
			msgout.writeUTF(builder.toString());

			msgout.writeUTF("");
		} catch(IOException exception) {
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		assert p != null;
		p.sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}

	public static void sendToLobby() {

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect"); // So BungeeCord knows to forward it
		out.writeUTF("skywars");

		Bukkit.getServer().sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}

	public static void sendQueue(Player player) {


		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("SkywarsQueue");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(player.getName());
		} catch (IOException exception){
			exception.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		assert p != null;
		p.sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}

	public static void sendToLobby(Player player) {

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect"); // So BungeeCord knows to forward it
		out.writeUTF("skywars");

		player .sendPluginMessage(PitSim.INSTANCE, "BungeeCord", out.toByteArray());
	}
}
