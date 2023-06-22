package net.pitsim.skywars;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import dev.kyro.arcticapi.ArcticAPI;
import dev.kyro.arcticapi.commands.ABaseCommand;
import dev.kyro.arcticapi.data.AConfig;
import dev.kyro.arcticapi.data.AData;
import dev.kyro.arcticapi.hooks.AHook;
import dev.kyro.arcticapi.misc.AOutput;
import me.mrten.mysqlapi.MySQL;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import net.pitsim.skywars.commands.DiscordCommand;
import net.pitsim.skywars.commands.ShowCommand;
import net.pitsim.skywars.commands.StoreCommand;
import net.pitsim.skywars.commands.admin.BaseAdminCommand;
import net.pitsim.skywars.commands.admin.BypassCommand;
import net.pitsim.skywars.commands.admin.ReloadCommand;
import net.pitsim.skywars.controllers.CombatManager;
import net.pitsim.skywars.controllers.*;
import net.pitsim.skywars.controllers.objects.AnticheatManager;
import net.pitsim.skywars.controllers.objects.PitEnchant;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import net.pitsim.skywars.enchants.*;
import net.pitsim.skywars.game.*;
import net.pitsim.skywars.game.skywarsperks.*;
import net.pitsim.skywars.game.sql.TableManager;
import net.pitsim.skywars.misc.PreGameScoreboard;
import net.pitsim.skywars.misc.YummyBread;
import net.pitsim.skywars.perks.NoPerk;
import net.pitsim.skywars.perks.Vampire;
import net.pitsim.skywars.placeholders.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PitSim extends JavaPlugin {

	public static LuckPerms LUCKPERMS;
	public static PitSim INSTANCE;
	public static Economy VAULT = null;
	public static ProtocolManager PROTOCOL_MANAGER = null;

	public static AData playerList;
	public static AnticheatManager anticheat;
	public static MySQL mysql;

	@Override
	public void onEnable() {
		INSTANCE = this;
		getCommand("skywars").setExecutor(new SkywarsCommand());
	}

	public void onInit() {
		loadConfig();
		ArcticAPI.configInit(this, "prefix", "error-prefix");

		if(Bukkit.getPluginManager().getPlugin("GrimAC") != null) hookIntoAnticheat(new GrimManager());
		if(Bukkit.getPluginManager().getPlugin("PolarLoader") != null) hookIntoAnticheat(new PolarManager());

		if(anticheat == null) {
			Bukkit.getLogger().severe("No anticheat found! Shutting down...");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		} else getServer().getPluginManager().registerEvents(anticheat, this);

		mysql = new MySQL();
		mysql.connect(
				AConfig.getString("sw-sql-host"),
				AConfig.getString("sw-sql-port"),
				AConfig.getString("sw-sql-username"),
				AConfig.getString("sw-sql-password"),
				AConfig.getString("sw-sql-database"));

		registerSkywarsPerks();

		TableManager.createPurchasedPerks();
		TableManager.createStatsTable();
		TableManager.createEquippedPerks();


		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		String id = PitSim.INSTANCE.getConfig().getString("server-ID");
		if(id.equalsIgnoreCase("test")) {
			QueueManager.minPlayers = 2;
			QueueManager.timerStartMinutes = 0;
		}

		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if(provider != null) {
			LUCKPERMS = provider.getProvider();
		}

		PreGameScoreboard.INSTANCE = new PreGameScoreboard();
		ScoreboardManager.setScoreboard(PreGameScoreboard.INSTANCE);

		PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

		List<NPC> toRemove = new ArrayList<>();
		CitizensAPI.getNPCRegistry().forEach(toRemove::add);
		while(!toRemove.isEmpty()) {
			toRemove.get(0).destroy();
			toRemove.remove(0);
		}

		if(!setupEconomy()) {
			AOutput.log(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
		EntityDamageEvent.getHandlerList().unregister(essentials);

		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			AOutput.log("Could not find PlaceholderAPI! This plugin is required.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		new ScoreboardManager().register();

		boolean NoteBlockAPI = true;
		if(!Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI")) {
			getLogger().severe("*** NoteBlockAPI is not installed or not enabled. ***");
			NoteBlockAPI = false;
			return;
		}

		MapManager.onPluginStart();

		registerPerks();

		ArcticAPI.setupPlaceholderAPI("skywars");
		AHook.registerPlaceholder(new PrefixPlaceholder());
		AHook.registerPlaceholder(new MapPlaceholder());
		AHook.registerPlaceholder(new ModePlaceholder());
		AHook.registerPlaceholder(new QueuePlayersPlaceholder());
		AHook.registerPlaceholder(new QueueTimePlaceholder());
		AHook.registerPlaceholder(new ServerPlaceholder());
		AHook.registerPlaceholder(new GameTimePlaceholder());
		AHook.registerPlaceholder(new PlayersLeftPlaceholder());
		AHook.registerPlaceholder(new KillsPlaceholder());
		AHook.registerPlaceholder(new LevelPlaceholder()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  );

		AHook.registerPlaceholder(new LinePlaceholder1());
		AHook.registerPlaceholder(new LinePlaceholder2());
		AHook.registerPlaceholder(new LinePlaceholder3());
		AHook.registerPlaceholder(new LinePlaceholder4());
		AHook.registerPlaceholder(new LinePlaceholder5());
		AHook.registerPlaceholder(new LinePlaceholder6());
		AHook.registerPlaceholder(new LinePlaceholder7());
		AHook.registerPlaceholder(new LinePlaceholder8());

		playerList = new AData("player-list", "", false);

		CooldownManager.init();

		registerEnchants();
		registerCommands();
		registerListeners();

		GameManager.init();
	}

	@Override
	public void onDisable() {
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

		getCommand("store").setExecutor(new StoreCommand());
		getCommand("shop").setExecutor(new StoreCommand());
		getCommand("discord").setExecutor(new DiscordCommand());
		getCommand("disc").setExecutor(new DiscordCommand());
		getCommand("show").setExecutor(new ShowCommand());
//		getCommand("oof").setExecutor(new OofCommand());
//		getCommand("togglestereo").setExecutor(new ToggleStereoCommand());
	}

	private void registerSkywarsPerks() {
		SkywarsPerk.registerPerk(new net.pitsim.skywars.game.skywarsperks.NoPerk());
		SkywarsPerk.registerPerk(new net.pitsim.skywars.game.skywarsperks.Vampire());
		SkywarsPerk.registerPerk(new Dirty());
		SkywarsPerk.registerPerk(new Locksmith());
		SkywarsPerk.registerPerk(new ComfortFood());
		SkywarsPerk.registerPerk(new Enderman());
		SkywarsPerk.registerPerk(new VoidLooter());
		SkywarsPerk.registerPerk(new Thick());
		SkywarsPerk.registerPerk(new Aegis());
		SkywarsPerk.registerPerk(new Banker());
		SkywarsPerk.registerPerk(new Tenacity());
		SkywarsPerk.registerPerk(new Gladiator());
		SkywarsPerk.registerPerk(new RefillReady());
		SkywarsPerk.registerPerk(new KillChain());
		SkywarsPerk.registerPerk(new Chicken());
	}

	private void registerListeners() {

		getServer().getPluginManager().registerEvents(new DamageManager(), this);
		getServer().getPluginManager().registerEvents(new PlayerManager(), this);
		getServer().getPluginManager().registerEvents(new DamageIndicator(), this);
		getServer().getPluginManager().registerEvents(new ItemManager(), this);
		getServer().getPluginManager().registerEvents(new SpawnManager(), this);
		getServer().getPluginManager().registerEvents(new AFKManager(), this);
		getServer().getPluginManager().registerEvents(new EnchantManager(), this);
		getServer().getPluginManager().registerEvents(new QueueManager(), this);
		getServer().getPluginManager().registerEvents(new KillManager(), this);
		getServer().getPluginManager().registerEvents(new SpectatorManager(), this);
		getServer().getPluginManager().registerEvents(new YummyBread(), this);
		getServer().getPluginManager().registerEvents(new FeatherManager(), this);
		getServer().getPluginManager().registerEvents(new CombatManager(), this);
		getServer().getPluginManager().registerEvents(new KitPerkChangeManager(), this);
		getServer().getPluginManager().registerEvents(new GoldManager(), this);
	}

	private void loadConfig() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private boolean setupEconomy() {
		if(getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null) {
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
//		EnchantManager.registerEnchant(new Healer());
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
//		EnchantManager.registerEnchant(new Sufferance());
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

//		Resource Enchants
		EnchantManager.registerEnchant(new Moctezuma());
		EnchantManager.registerEnchant(new GoldBump());
		EnchantManager.registerEnchant(new CoinBoost());

		EnchantManager.registerEnchant(new Sweaty());
		EnchantManager.populateMap();
	}

	public void hookIntoAnticheat(AnticheatManager anticheat) {
		if(PitSim.anticheat != null) {
			Bukkit.getLogger().severe("Multiple anticheats found! Shutting down...");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		PitSim.anticheat = anticheat;
	}
}
