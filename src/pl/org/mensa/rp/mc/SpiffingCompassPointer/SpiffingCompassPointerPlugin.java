package pl.org.mensa.rp.mc.SpiffingCompassPointer;

import org.bukkit.plugin.java.JavaPlugin;

public class SpiffingCompassPointerPlugin extends JavaPlugin {
	public static long timer = 10L;
	
	final CrazyCompass crazy_compass = new CrazyCompass(this);
	
	@Override
	public void onEnable() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, crazy_compass, 1L, 1L);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CompassSetter(crazy_compass), timer, timer);
		this.getCommand("compasstimer").setExecutor(new CommandHandler(this));
		this.getCommand("compassteps").setExecutor(new CommandHandler2(this));
	}
	@Override
	public void onDisable() {
		this.getServer().getScheduler().cancelTasks(this);
	}
	
	public void redoTimer() {
		this.getServer().getScheduler().cancelTasks(this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, crazy_compass, timer, timer);
	}
}
