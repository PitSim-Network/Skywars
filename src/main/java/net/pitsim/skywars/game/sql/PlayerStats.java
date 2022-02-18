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
	public int level = 1;
	public double xp = 0;
	public double coins = 0;

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
					if(resultSet.next()) {
						wins = resultSet.getInt("ms_wins");
						kills = resultSet.getInt("ms_kills");
						level = resultSet.getInt("level");
						xp = resultSet.getInt("level_xp");
						coins = resultSet.getInt("coins");
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
				.value("level")
				.value("level_xp")
				.value("coins")
				.build();

		Query query = new Query(PitSim.INSTANCE.mysql, sql + " ms_wins = " + wins + ", ms_kills = " + kills
				+ ", level = " + level + ", level_xp = " + xp + ", coins = " + coins);
		query.setParameter(1, player.getUniqueId().toString());
		query.setParameter(2, player.getName());
		query.setParameter(3, wins);
		query.setParameter(4, kills);
		query.setParameter(5, level);
		query.setParameter(6, xp);
		query.setParameter(7, coins);
		query.executeUpdateAsync((resultSet, e) -> {
			if(e != null) {
				e.printStackTrace();
			}
		});
	}


}

