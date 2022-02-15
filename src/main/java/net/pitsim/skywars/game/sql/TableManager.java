package net.pitsim.skywars.game.sql;

import me.mrten.mysqlapi.queries.CreateTableQuery;
import me.mrten.mysqlapi.queries.Query;
import net.pitsim.skywars.PitSim;

public class TableManager {
	public static void createTable() {
		// Create a new SQL query
		String sql = new CreateTableQuery("stats")
				.ifNotExists()
				.column("uuid", "VARCHAR(255) NOT NULL UNIQUE")
				.column("username", "VARCHAR(255) NOT NULL")
				.column("ms_wins", "INT(11)")
				.column("ms_kills", "INT(11)")

				.primaryKey("uuid")
				.build();

		Query query = new Query(PitSim.INSTANCE.mysql, sql);
		// Execute it asynchronously
		query.executeUpdateAsync();
	}
}
