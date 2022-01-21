package pl.org.mensa.rp.mc.SpiffingCompassPointer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CompassSetter implements Runnable {
	CrazyCompass crazy_compass;
	
	public CompassSetter(CrazyCompass crazy_compass) {
		this.crazy_compass = crazy_compass;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		List<Player> targets = Bukkit.getOnlinePlayers().stream().filter(p -> p != null).filter(p -> p.isOnline()).filter(p -> p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() == Material.GOLDEN_HELMET).collect(Collectors.toList());
		
		Bukkit.getOnlinePlayers().stream().filter(p -> p != null).filter(p -> p.isOnline()).forEach(p -> {
			Location target_location = null;
			
			if (targets.contains(p)) {
				targets.remove(p);
				target_location = getNearestTarget(p.getLocation(), targets);
				targets.add(p);
			}
			else {
				final List<UUID> team_uuids = new ArrayList<UUID>();
				if (p.getScoreboard().getPlayerTeam(p) != null) {
					team_uuids.addAll(p.getScoreboard().getPlayerTeam(p).getPlayers().stream().map(team_p -> team_p.getUniqueId()).collect(Collectors.toList()));
				}				
				List<Player> team_targets = targets.stream().filter(target -> team_uuids.contains(target.getUniqueId())).collect(Collectors.toList());
				
				targets.removeAll(team_targets);
				target_location = getNearestTarget(p.getLocation(), targets);
				targets.addAll(team_targets);
			}
			
			if (target_location == null) p.setCompassTarget(crazy_compass.nextPosition(p.getLocation()));
			else p.setCompassTarget(target_location);
		});
	}
	
	private Location getNearestTarget(Location player_location, List<Player> players) {
		double closest_distance = 7_200_000_000_000_000.0D;
		Location closest_target = null;
		
		for (Player p : players) {
			if (p == null) continue;
			
			double dist = player_location.distanceSquared(p.getLocation());
			
			if (dist < closest_distance) {
				closest_distance = dist;
				closest_target = p.getLocation();
			}
		}
		
		return closest_target;
	}
}
