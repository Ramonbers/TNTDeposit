package me.OldSkoolDad.tnt.Commands;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.entity.MPlayer;

import me.OldSkoolDad.tnt.C;
import me.OldSkoolDad.tnt.Item;

public class TNTHandler {
	
	public static HashMap<String, Integer> FactionTNTList = new HashMap<>();
	public static HashMap<String, Integer> FactionTNTUpdateList = new HashMap<>();
	
	public void showHelp(Player p) {
		p.sendMessage(C.TAC(" "));
		p.sendMessage(C.TAC("&6TNTDeposit &7(1.0)"));
		p.sendMessage(C.TAC(" &e- &7/tnt &edeposit"));
		p.sendMessage(C.TAC(" &e- &7/tnt &ewithdraw"));
		p.sendMessage(C.TAC(" &e- &7/tnt &ebalance"));
		p.sendMessage(C.TAC(" "));
	}
	
	public void depositTNT(Player p) {
		Inventory inv = p.getInventory();
		MPlayer mplayer = MPlayer.get(p.getUniqueId());
		String factionId = mplayer.getFaction().getId();
		
		int tntCount = 0;
		
		for (int i = 0; i <= inv.getSize(); i++) {
			if (inv.getItem(i) != null) {
				if (inv.getItem(i).getType().equals(Material.TNT)) {
					tntCount += inv.getItem(i).getAmount();
				}
			}
		}
		
		if (tntCount > 0) {
			if (!FactionTNTList.containsKey(factionId)) {
				FactionTNTList.put(factionId, 0);
			}
			int tntAmount = FactionTNTList.get(factionId) + tntCount;
			FactionTNTList.put(factionId, tntAmount);
			FactionTNTUpdateList.put(factionId, tntAmount);
			inv.remove(new ItemStack(Material.TNT, tntCount));
			p.sendMessage(C.TAC("&aSuccessfully deposited &2" + tntCount + " TNT &ato your faction!"));
		} else {
			p.sendMessage(C.TAC("&c&l! &CYou have no &4TNT&c in your inventory."));
		}
	}
	
	public void balanceTNT(Player p) {
		MPlayer mplayer = MPlayer.get(p.getUniqueId());
		String factionId = mplayer.getFaction().getId();
		if (mplayer.getFactionName().equalsIgnoreCase("Wilderness") || mplayer.getFactionName().equalsIgnoreCase("None") || mplayer.getFactionName().equalsIgnoreCase("")
				|| mplayer.getFactionName() == null) {
			p.sendMessage(C.TAC("&c&l! &cYou're not in a faction!"));
		} else {
			if (FactionTNTList.get(factionId) == null) {
				p.sendMessage(C.TAC("&c&l! &cThere is no TNT balance in your faction."));
			} else {
				int tntAmount = FactionTNTList.get(factionId);
				p.sendMessage(C.TAC("&7Faction's TNT balance: &e" + tntAmount));
			}
		}
	}
	
	public void withdrawTNT(Player p) {
		MPlayer mplayer = MPlayer.get(p.getUniqueId());
		String factionId = mplayer.getFaction().getId();
		int amount = 0;
		for (int i = 0; i < 2304; i += 64) {
			if (!invFull(p)) {
				amount += 64;
				if ((FactionTNTList.get(factionId) - amount) >= 0) {
					p.getInventory().addItem(Item.Create(Material.TNT, 64, 0, ""));
				}
			}
		}
		int tntAmount = FactionTNTList.get(factionId) - amount;
		FactionTNTList.put(factionId, tntAmount);
		FactionTNTUpdateList.put(factionId, tntAmount);
		p.sendMessage(C.TAC("&cYou have withdrawn &4" + amount + " &cof tnt from your faction."));
	}
	
	public boolean invFull(Player p) {
		if (p.getInventory().firstEmpty() == -1) {
			return true;
		} else {
			return false;
		}
	}
}
