package pl.org.mensa.rp.mc.SpiffingCompassPointer;

import javax.annotation.Nonnull;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler2 implements CommandExecutor {
	SpiffingCompassPointerPlugin plugin;
	
	public CommandHandler2(SpiffingCompassPointerPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String alias, @Nonnull String[] args) {
		if (sender.hasPermission("spiffingcompasspointer.command.compassteps") || sender.isOp() || !(sender instanceof Player)) {
			if (args.length > 0) {
				int new_steps = -1;
				
				try {
					new_steps = Integer.valueOf(args[0]);
					
					if (new_steps < 1L) {
						sender.sendMessage("§cInvalid value");
						return true;
					}
					
//					CrazyCompass.steps = new_steps;
					
					CrazyCompass.redoSteps(new_steps);
				} catch (Exception exc) {
					sender.sendMessage("§cInvalid value");
					return true;
				}
			}
			else {
				sender.sendMessage("§e" + CrazyCompass.steps);
			}
		}
		else {
			sender.sendMessage("§cNo permission");
		}
		
		return true;
	}
}
