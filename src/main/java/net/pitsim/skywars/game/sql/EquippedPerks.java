package net.pitsim.skywars.game.sql;

import me.mrten.mysqlapi.queries.InsertQuery;
import me.mrten.mysqlapi.queries.Query;
import me.mrten.mysqlapi.queries.SelectQuery;
import net.pitsim.skywars.PitSim;
import net.pitsim.skywars.controllers.objects.SkywarsPerk;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class EquippedPerks {

	public Player player;

	public SkywarsPerk[] perks = {SkywarsPerk.getPerk("no_perk"), SkywarsPerk.getPerk("no_perk"),
			SkywarsPerk.getPerk("no_perk"), SkywarsPerk.getPerk("no_perk")};

	public EquippedPerks(Player player) {
		this.player = player;
		SelectQuery selectQuery = new SelectQuery("equipped_perks");
		selectQuery.column("*");
		selectQuery.where("uuid = '" + player.getUniqueId().toString() + "'");

		Query query = new Query(PitSim.INSTANCE.mysql, selectQuery.build());

		query.executeQueryAsync((resultSet, e) -> {
			if(e != null) {
				save();
			} else {
				try {
					if(resultSet.next()) {
						perks[0] = SkywarsPerk.getPerk(resultSet.getString("perk1"));
						perks[1] = SkywarsPerk.getPerk(resultSet.getString("perk2"));
						perks[2] = SkywarsPerk.getPerk(resultSet.getString("perk3"));
						perks[3] = SkywarsPerk.getPerk(resultSet.getString("perk4"));
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
		System.out.println("Saving!");
		String sql = new InsertQuery("equipped_perks")
				.onDuplicateKeyUpdate()
				.value("uuid")
				.value("perk1")
				.value("perk2")
				.value("perk3")
				.value("perk4")
				.build();

		Query query = new Query(PitSim.INSTANCE.mysql, sql + " perk1 = '" + perks[0].refName + "', perk2 = '" + perks[1].refName
				+ "', perk3 = '" + perks[2].refName + "', perk4 = '" + perks[3].refName + "'");

		query.setParameter(1, player.getUniqueId().toString());
		query.setParameter(2, perks[0].refName);
		query.setParameter(3, perks[1].refName);
		query.setParameter(4, perks[2].refName);
		query.setParameter(5, perks[3].refName);

		query.executeUpdateAsync((resultSet, e) -> {
			if(e != null) {
				e.printStackTrace();
			}
		});
	}


}

