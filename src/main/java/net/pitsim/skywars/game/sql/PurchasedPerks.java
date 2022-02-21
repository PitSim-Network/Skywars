package net.pitsim.skywars.game.sql;

import me.mrten.mysqlapi.queries.InsertQuery;
import me.mrten.mysqlapi.queries.Query;
import me.mrten.mysqlapi.queries.SelectQuery;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PurchasedPerks {

	public Player player;
	public Map<String, Integer> perkTiers = new HashMap<>();

	public PurchasedPerks(Player player) {
		this.player = player;

		for(SkywarsPerk perk : SkywarsPerk.perks) {
			if(perk.refName.equals("no_perk")) continue;
			perkTiers.put(perk.refName, 0);
		}

		SelectQuery selectQuery = new SelectQuery("purchased_perks");
		selectQuery.column("*");
		selectQuery.where("uuid = '" + player.getUniqueId().toString() + "'");

		Query query = new Query(PitSim.INSTANCE.mysql, selectQuery.build());

		query.executeQueryAsync((resultSet, e) -> {
			if(e != null) {
				save();
			} else {
				try {
					if(resultSet.next()) {
						perkTiers.clear();
						for(SkywarsPerk perk : SkywarsPerk.perks) {
							if(perk.refName.equals("no_perk")) continue;
							perkTiers.put(perk.refName, resultSet.getInt(perk.refName));
						}
					} else {
						System.out.println("No player data! Creating.");
						save();
					}

				} catch(SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
	}

	public void save() {
		InsertQuery sql = new InsertQuery("purchased_perks");
		sql.onDuplicateKeyUpdate();
		sql.value("uuid");
		for(String s : perkTiers.keySet()) {
			sql.value(s);
		}
		StringBuilder sqlString = new StringBuilder(sql.build());
		int count = 0;
		for(Map.Entry<String, Integer> stringIntegerEntry : perkTiers.entrySet()) {
			if(count != 0) sqlString.append(",");
			sqlString.append(" ").append(stringIntegerEntry.getKey()).append(" = ").append(stringIntegerEntry.getValue());

			count++;
		}

		Query query = new Query(PitSim.INSTANCE.mysql, sqlString.toString());
		query.setParameter(1, player.getUniqueId().toString());
		int paramCount = 2;
		for(Map.Entry<String, Integer> stringIntegerEntry : perkTiers.entrySet()) {
			query.setParameter(paramCount, stringIntegerEntry.getValue());
			paramCount++;
		}
		query.executeUpdateAsync((resultSet, e) -> {
			if(e != null) {
				e.printStackTrace();
			}
		});
	}
}
