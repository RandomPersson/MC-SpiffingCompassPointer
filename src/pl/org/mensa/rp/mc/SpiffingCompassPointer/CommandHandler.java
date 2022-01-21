package pl.org.mensa.rp.mc.SpiffingCompassPointer;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	SpiffingCompassPointerPlugin plugin;
	
	public CommandHandler(SpiffingCompassPointerPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String alias, @Nonnull String[] args) {
		if (sender.hasPermission("spiffingcompasspointer.command.compasstimer") || sender.isOp() || !(sender instanceof Player)) {
			if (args.length > 0) {
				long new_timer = -1L;
				
				try {
					new_timer = Long.valueOf(args[0]);
					
					if (new_timer < 1L) {
						sender.sendMessage("§cInvalid value");
						return true;
					}
					
					SpiffingCompassPointerPlugin.timer = new_timer;
					
					plugin.redoTimer();
				} catch (Exception exc) {
					sender.sendMessage("§cInvalid value");
					return true;
				}
			}
			else {
				sender.sendMessage("§e" + SpiffingCompassPointerPlugin.timer);
			}
		}
		else {
			sender.sendMessage("§cNo permission");
		}
		
		return true;
	}
}
