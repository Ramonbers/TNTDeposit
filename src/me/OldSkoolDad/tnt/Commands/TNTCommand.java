package me.OldSkoolDad.tnt.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TNTCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		TNTHandler tntHandler = new TNTHandler();
		
		if (args.length == 0) {
			tntHandler.showHelp(p);
		} else {
			switch (args[0]) {
				case "deposit":
					tntHandler.depositTNT(p);
					break;
				case "withdraw":
					tntHandler.withdrawTNT(p);
					break;
				case "balance":
					tntHandler.balanceTNT(p);
					break;
				default:
					tntHandler.showHelp(p);
					break;
			}
		}
		return false;
	}
}
