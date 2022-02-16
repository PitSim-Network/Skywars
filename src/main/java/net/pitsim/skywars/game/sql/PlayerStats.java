package net.pitsim.skywars.game.sql;

import me.mrten.mysqlapi.queries.InsertQuery;
import me.mrten.mysqlapi.queries.Query;
import me.mrten.mysqlapi.queries.SelectQuery;
import net.pitsim.skywars.PitSim;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlayerStats {

	public Player player;
	public int wins = 0;
	public int kills = 0;

	public PlayerStats(Player player) {
		this.player = player;
		SelectQuery selectQuery = new SelectQuery("stats");
		selectQuery.column("*");
		selectQuery.where("uuid = '" + player.getUniqueId().toString() + "'");

		Query query = new Query(PitSim.INSTANCE.mysql, selectQuery.build());

		query.executeQueryAsync((resultSet, e) -> {
			if(e != null) {
				save();
			} else {
				try {
					if (resultSet.next()) {
						wins = resultSet.getInt("ms_wins");
						kills = resultSet.getInt("ms_kills");
					} else {
						System.out.println("No player data! Creating.");
					}

				} catch(SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
	}

	public void save() {
		String sql = new InsertQuery("stats")
				.onDuplicateKeyUpdate()
				.value("uuid")
				.value("username")
				.value("ms_wins")
				.value("ms_kills")
				.build();

		Query query = new Query(PitSim.INSTANCE.mysql, sql + " ms_wins = " + wins + ", ms_kills = " + kills);
		query.setParameter(1, player.getUniqueId().toString());
		query.setParameter(2, player.getName());
		query.setParameter(3, wins);
		query.setParameter(4, kills);
		query.executeUpdateAsync((resultSet, e) -> {
			if (e != null) {
				e.printStackTrace();
			}
		});
	}





}

