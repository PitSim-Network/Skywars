package net.pitsim.skywars.game.sql;

import me.mrten.mysqlapi.queries.CreateTableQuery;
import me.mrten.mysqlapi.queries.Query;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;

public class TableManager {
	public static void createStatsTable() {
		String sql = new CreateTableQuery("stats")
				.ifNotExists()
				.column("uuid", "VARCHAR(255) NOT NULL UNIQUE")
				.column("username", "VARCHAR(255) NOT NULL")
				.column("ms_wins", "INT(11)")
				.column("ms_kills", "INT(11)")
				.column("level", "INT(11)")
				.column("level_xp", "INT(11)")
				.column("coins", "INT(11)")

				.primaryKey("uuid")
				.build();

		Query query = new Query(PitSim.INSTANCE.mysql, sql);
		query.executeUpdateAsync();
	}

	public static void createPurchasedPerks() {
		CreateTableQuery sql = new CreateTableQuery("purchased_perks");
		sql.ifNotExists();
		sql.column("uuid", "VARCHAR(255) NOT NULL UNIQUE");
		for(SkywarsPerk perk : SkywarsPerk.perks) {
			sql.column(perk.refName, "INT(11)");
		}
		sql.primaryKey("uuid");

		Query query = new Query(PitSim.INSTANCE.mysql, sql.build());
		query.executeUpdateAsync();
	}

	public static void createEquippedPerks() {
		String sql = new CreateTableQuery("equipped_perks")
				.ifNotExists()
				.column("uuid", "VARCHAR(255) NOT NULL UNIQUE")
				.column("perk1", "VARCHAR(255)")
				.column("perk2", "VARCHAR(255)")
				.column("perk3", "VARCHAR(255)")
				.column("perk4", "VARCHAR(255)")

				.primaryKey("uuid")
				.build();

		Query query = new Query(PitSim.INSTANCE.mysql, sql);
		query.executeUpdateAsync();
	}
}
