package me.OldSkoolDad.tnt;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Item {
	
	public static ItemStack Create(Material m, int amount, int damage, String dname, String... string) {
		ItemStack i = new ItemStack(m, amount, (short) damage);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', dname));
		ArrayList<String> Lore = new ArrayList<String>();
		for (String lores : string) {
			Lore.add(ChatColor.translateAlternateColorCodes('&', lores));
		}
		im.setLore(Lore);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack Create(Material m, int amount, int damage, String dname) {
		ItemStack i = new ItemStack(m, amount, (short) damage);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', dname));
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack Create(Material m, int damage) {
		ItemStack i = new ItemStack(m, 1, (short) damage);
		return i;
	}
	
	public static ItemStack Skull(String SkullOwner, int amount, int damage, String dname, String... lore) {
		ItemStack i = new ItemStack(Material.SKULL_ITEM, amount, (short) damage);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', dname));
		ArrayList<String> Lore = new ArrayList<String>();
		for (String lores : lore) {
			Lore.add(ChatColor.translateAlternateColorCodes('&', lores));
		}
		im.setLore(Lore);
		((SkullMeta) im).setOwner(SkullOwner);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack Skull(String SkullOwner, int amount, int damage, String dname) {
		ItemStack i = new ItemStack(Material.SKULL_ITEM, amount, (short) damage);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', dname));
		((SkullMeta) im).setOwner(SkullOwner);
		i.setItemMeta(im);
		return i;
	}
	
}
