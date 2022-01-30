package net.pitsim.skywars;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.commands.ABaseCommand;
import dev.kyro.arcticapi.data.AData;
import dev.kyro.arcticapi.hooks.AHook;
import dev.kyro.arcticapi.misc.AOutput;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.enchants.GoldBoost;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import net.pitsim.skywars.commands.*;
import net.pitsim.skywars.commands.admin.BaseAdminCommand;
import net.pitsim.skywars.commands.admin.BypassCommand;
import net.pitsim.skywars.commands.admin.LockdownCommand;
import net.pitsim.skywars.commands.admin.ReloadCommand;
import net.pitsim.skywars.controllers.*;
import net.pitsim.skywars.enchants.*;
import net.pitsim.skywars.game.KillManager;
import net.pitsim.skywars.game.QueueManager;
import net.pitsim.skywars.game.SpectatorManager;
import net.pitsim.skywars.misc.SpawnNPCs;
import net.pitsim.skywars.perks.NoPerk;
import net.pitsim.skywars.perks.Vampire;
import net.pitsim.skywars.placeholders.PrefixPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class PitSim extends JavaPlugin {

	public static double version = 2.0;

	public static LuckPerms LUCKPERMS;
	public static PitSim INSTANCE;
	public static Economy VAULT = null;
	public static ProtocolManager PROTOCOL_MANAGER = null;

	public static AData playerList;
//	private BukkitAudiences adventure;
//
//	public BukkitAudiences adventure() {
//		if(this.adventure == null) {
//			throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
//		}
//		return this.adventure;
//	}

	@Override
	public void onEnable() {

		INSTANCE = this;

		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider != null) {
			LUCKPERMS = provider.getProvider();
		}

		PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

		List<NPC> toRemove = new ArrayList<>();
		CitizensAPI.getNPCRegistry().forEach(toRemove::add);
		while(!toRemove.isEmpty()) {
			toRemove.get(0).destroy();
			toRemove.remove(0);
		}

		SpawnNPCs.createNPCs();

		if (!setupEconomy()) {
			AOutput.log(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
		EntityDamageEvent.getHandlerList().unregister(essentials);
		for(RegisteredListener listener : EntityDamageEvent.getHandlerList().getRegisteredListeners()) {
		}

		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
		} else {
			AOutput.log(String.format("Could not find PlaceholderAPI! This plugin is required."));
			Bukkit.getPluginManager().disablePlugin(this);
		}

		boolean NoteBlockAPI = true;
		if (!Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI")){
			getLogger().severe("*** NoteBlockAPI is not installed or not enabled. ***");
			NoteBlockAPI = false;
			return;
		}

		registerPerks();

		ArcticAPI.setupPlaceholderAPI("sync");
		AHook.registerPlaceholder(new PrefixPlaceholder());

		loadConfig();

		ArcticAPI.configInit(this, "prefix", "error-prefix");
		playerList = new AData("player-list", "", false);

		CooldownManager.init();

		registerEnchants();
		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {

		SpawnNPCs.removeNPCs();




		for(PitEnchant pitEnchant : EnchantManager.pitEnchants) pitEnchant.onDisable();

		Iterator<Map.Entry<Player, EntitySongPlayer>> it = StereoManager.playerMusic.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Player, EntitySongPlayer> pair = it.next();
			EntitySongPlayer esp = pair.getValue();
			esp.destroy();
			it.remove();
		}

	}

	private void registerPerks() {
		PerkManager.registerUpgrade(new NoPerk());
		PerkManager.registerUpgrade(new Vampire());
	}

	private void registerCommands() {

		ABaseCommand adminCommand = new BaseAdminCommand("pitsim");
		getCommand("ps").setExecutor(adminCommand);
		adminCommand.registerCommand(new ReloadCommand("reload"));
		adminCommand.registerCommand(new BypassCommand("bypass"));
		adminCommand.registerCommand(new LockdownCommand("lockdown"));

		getCommand("atest").setExecutor(new ATestCommand());

		getCommand("oof").setExecutor(new OofCommand());
		getCommand("enchant").setExecutor(new EnchantCommand());
		getCommand("fresh").setExecutor(new FreshCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("store").setExecutor(new StoreCommand());
		getCommand("shop").setExecutor(new StoreCommand());
		getCommand("discord").setExecutor(new DiscordCommand());
		getCommand("disc").setExecutor(new DiscordCommand());
		getCommand("captcha").setExecutor(new CaptchaCommand());
//		getCommand("togglestereo").setExecutor(new ToggleStereoCommand());
	}

	private void registerListeners() {

		getServer().getPluginManager().registerEvents(new DamageManager(), this);
		getServer().getPluginManager().registerEvents(new PlayerManager(), this);
		getServer().getPluginManager().registerEvents(new DamageIndicator(), this);
		getServer().getPluginManager().registerEvents(new ItemManager(), this);
		getServer().getPluginManager().registerEvents(new SpawnManager(), this);
		getServer().getPluginManager().registerEvents(new AFKManager(), this);
		getServer().getPluginManager().registerEvents(new EnchantManager(), this);
		getServer().getPluginManager().registerEvents(new SpawnNPCs(), this);
		getServer().getPluginManager().registerEvents(new QueueManager(), this);
		getServer().getPluginManager().registerEvents(new KillManager(), this);
		getServer().getPluginManager().registerEvents(new SpectatorManager(), this);
	}

	private void loadConfig() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		VAULT = rsp.getProvider();
		return VAULT != null;
	}

	private void registerEnchants() {
		EnchantManager.registerEnchant(new ComboVenom());
//		EnchantManager.registerEnchant(new aCPLEnchant());
//		EnchantManager.registerEnchant(new SelfCheckout());
		EnchantManager.registerEnchant(new aEntanglement());
		EnchantManager.registerEnchant(new aRetroGravityMinikloon());

		EnchantManager.registerEnchant(new Billionaire());
		EnchantManager.registerEnchant(new ComboPerun());
		EnchantManager.registerEnchant(new Executioner());
		EnchantManager.registerEnchant(new Gamble());
		EnchantManager.registerEnchant(new ComboStun());
		EnchantManager.registerEnchant(new SpeedyHit());
		EnchantManager.registerEnchant(new Healer());
		EnchantManager.registerEnchant(new Lifesteal());
		EnchantManager.registerEnchant(new ComboHeal());

		EnchantManager.registerEnchant(new Shark());
		EnchantManager.registerEnchant(new PainFocus());
		EnchantManager.registerEnchant(new DiamondStomp());
		EnchantManager.registerEnchant(new ComboDamage());
		EnchantManager.registerEnchant(new KingBuster());
		EnchantManager.registerEnchant(new Sharp());
		EnchantManager.registerEnchant(new Punisher());
		EnchantManager.registerEnchant(new BeatTheSpammers());
		EnchantManager.registerEnchant(new GoldAndBoosted());

		EnchantManager.registerEnchant(new ComboSwift());
		EnchantManager.registerEnchant(new BulletTime());
		EnchantManager.registerEnchant(new Guts());
		EnchantManager.registerEnchant(new Crush());

		EnchantManager.registerEnchant(new MegaLongBow());
		EnchantManager.registerEnchant(new Robinhood());
		EnchantManager.registerEnchant(new Volley());
		EnchantManager.registerEnchant(new Telebow());
		EnchantManager.registerEnchant(new Pullbow());
		EnchantManager.registerEnchant(new Explosive());
		EnchantManager.registerEnchant(new LuckyShot());

		EnchantManager.registerEnchant(new SprintDrain());
		EnchantManager.registerEnchant(new Wasp());
		EnchantManager.registerEnchant(new PinDown());
		EnchantManager.registerEnchant(new FasterThanTheirShadow());
		EnchantManager.registerEnchant(new PushComesToShove());
		EnchantManager.registerEnchant(new Parasite());
		EnchantManager.registerEnchant(new Chipping());
		EnchantManager.registerEnchant(new Fletching());
//		EnchantManager.registerEnchant(new BottomlessQuiver());

		EnchantManager.registerEnchant(new RetroGravityMicrocosm());
		EnchantManager.registerEnchant(new Regularity());
		EnchantManager.registerEnchant(new Solitude());

		EnchantManager.registerEnchant(new Mirror());
		EnchantManager.registerEnchant(new Sufferance());
		EnchantManager.registerEnchant(new CriticallyFunky());
		EnchantManager.registerEnchant(new FractionalReserve());
		EnchantManager.registerEnchant(new NotGladiator());
		EnchantManager.registerEnchant(new Protection());
		EnchantManager.registerEnchant(new RingArmor());

		EnchantManager.registerEnchant(new Peroxide());
		EnchantManager.registerEnchant(new Booboo());
		EnchantManager.registerEnchant(new ReallyToxic());
		EnchantManager.registerEnchant(new NewDeal());
		EnchantManager.registerEnchant(new HeighHo());

		EnchantManager.registerEnchant(new GoldenHeart());
		EnchantManager.registerEnchant(new Hearts());
		EnchantManager.registerEnchant(new Prick());
		EnchantManager.registerEnchant(new Electrolytes());
		EnchantManager.registerEnchant(new GottaGoFast());
		EnchantManager.registerEnchant(new CounterOffensive());
		EnchantManager.registerEnchant(new LastStand());
		EnchantManager.registerEnchant(new Stereo());
//		EnchantManager.registerEnchant(new DiamondAllergy());
//		EnchantManager.registerEnchant(new PitBlob());
//		EnchantManager.registerEnchant(new WolfPack());

//		Resource Enchants
		EnchantManager.registerEnchant(new Moctezuma());
		EnchantManager.registerEnchant(new GoldBump());
		EnchantManager.registerEnchant(new GoldBoost());

//		EnchantManager.registerEnchant(new Sweaty());
		EnchantManager.registerEnchant(new XpBump());
	}
}
