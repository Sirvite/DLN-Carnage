package me.sirvite.Main;

import java.util.ArrayList;
import java.util.List;

import me.sirvite.Color.Color;
import me.sirvite.Listener.ChestClick;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Carnage extends CommandExecute implements CommandExecutor, Listener {
  Main plugin;
  
  public static ArmorStand IH1;
  public static ArmorStand IH2;
  public static ArmorStand IH3;
  public static ArmorStand IH4;
  public static ArmorStand IH5;
  public static ArmorStand chestHolo1;
  public static ArmorStand chestHolo2;
  public static ArmorStand chestHolo3;
  
  
  
  public Carnage(Main instance) {
    this.plugin = instance;
  }
  
  
  @SuppressWarnings("null")
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("carnage")) {
      if (!(sender instanceof Player)) {
        Bukkit.getConsoleSender().sendMessage(Color.format(this.plugin.getConfig().getString("only-players")));
        return true;
      } 
      FileConfiguration config = this.plugin.getConfig();
      Player p = (Player)sender;
      if (args.length == 0)
        KillfestHelpMSG(p); 
      if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
        if (p.hasPermission("killfest.reload")) {
          this.plugin.reloadConfig();
          this.plugin.reloadConfig();
          Bukkit.getConsoleSender().sendMessage(Color.format(config.getString("config-reloaded")));
          p.sendMessage(Color.format(config.getString("config-reloaded")));
        } else {
          p.sendMessage(Color.format(config.getString("no-permission")));
        }  
      if (args.length == 1 && args[0].equalsIgnoreCase("timer")) {
        int hours1 = Main.Cooldown / 3600;
        int A1 = Main.Cooldown / 60;
        int minutes = A1 % 60;
        int hours = hours1 % 24;
        int seconds = Main.Cooldown % 60;
        String msg = String.valueOf(hours) + " hours " + minutes + " minutes and " + seconds + " seconds";
        if (ChestClick.killfeststatus) {
          p.sendMessage(Color.format(config.getString("already-running")));
        } else {
          String path1 = config.getString("next-carnage");
          path1 = path1.replaceAll("%time%", msg);
          p.sendMessage(Color.format(path1));
        } 
      }
      
      
      //
      

      if (args.length == 2 && args[0].equalsIgnoreCase("summoner"))
        if (p.hasPermission("carnage.summoner")) {
        	
        	Player target = Bukkit.getServer().getPlayer(args[1]);
            if (target == null) {
              sender.sendMessage(ChatColor.RED + "Could Not Find Player " + target.getName());
              return true;
            }
        	
        	ItemStack summoneritem = new ItemStack(Material.SLIME_BALL, 1);
            ItemMeta summonermeta = summoneritem.getItemMeta();
            List<String> summonlore = new ArrayList<String>();
            
            summonermeta.setDisplayName(cl("&d&ki&f &lCarnage Summoner&d &ki"));
            summonlore.add(cl("&7Clicking this summoner spawns the"));
            summonlore.add(cl("&f&lCarnage Crate&7 in the warzone."));
            summonlore.add(cl("&7"));
            summonlore.add(cl("&4&lWarning:&7 This can either be activated with"));
            summonlore.add(cl("&7&oleft-clicking &7or &oright-clicking"));
            summonermeta.setLore(summonlore);
            summoneritem.setItemMeta(summonermeta);
           
            if (sender instanceof Player) {
                    @SuppressWarnings("unused")
					Player pl = (Player)sender;
                    target.getInventory().addItem(summoneritem);
                    target.updateInventory();
            
            return false;
            }
           
           
    }
      
      if (args.length == 1 && args[0].equalsIgnoreCase("hologram"))
        if (p.hasPermission("carnage.spawnhologram")) {
          if (config.getBoolean("hologram.DontEDIT") == Boolean.FALSE.booleanValue()) {
            Location loc = p.getLocation();
            String ww = p.getWorld().getName();
            double x = loc.getBlockX();
            double y = loc.getBlockY();
            double z = loc.getBlockZ();
            this.plugin.getConfig().set("hologram.loc.w", ww);
            this.plugin.getConfig().set("hologram.loc.x", Double.valueOf(x));
            this.plugin.getConfig().set("hologram.loc.y", Double.valueOf(y));
            this.plugin.getConfig().set("hologram.loc.z", Double.valueOf(z));
            this.plugin.getConfig().set("hologram.DontEDIT", Boolean.TRUE);
            this.plugin.saveConfig();
            World w = Bukkit.getWorld(config.getString("hologram.loc.w"));
            Location tloc = new Location(w, x, y - 0.3D, z);
            Main.infoholo1 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            Main.infoholo1.setGravity(false);
            Main.infoholo1.setVisible(false);
            Main.infoholo1.setCustomNameVisible(true);
            Main.infoholo1.setCustomName(Color.format("&4&k|||&f &c&lKill Fest test &4&k|||"));
            y -= 0.5D;
            x -= 0.5D;
            z -= 0.5D;
            tloc = new Location(w, x, y - 0.3D, z);
            Main.infoholo2 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            Main.infoholo2.setGravity(false);
            Main.infoholo2.setVisible(false);
            Main.infoholo2.setCustomNameVisible(true);
            Main.infoholo2.setCustomName(Color.format("[Reload]"));
            y -= 0.5D;
            x -= 0.5D;
            z -= 0.5D;
            tloc = new Location(w, x, y - 0.3D, z);
            Main.infoholo3 = (ArmorStand)Bukkit.getWorld(config.getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
            Main.infoholo3.setGravity(false);
            Main.infoholo3.setVisible(false);
            Main.infoholo3.setCustomNameVisible(true);
            Main.infoholo3.setCustomName(Color.format("[Reload]"));
            p.sendMessage(Color.format(String.valueOf(config.getString("hologram-spawned")) + " &8[&7&oreload for hologram to work&8]"));
            return true;
          } 
          p.sendMessage(Color.format(config.getString("hologram-already-spawned")));
        } else {
          p.sendMessage(Color.format(config.getString("no-permission")));
        }  
      if (args.length == 1 && args[0].equalsIgnoreCase("rh"))
        if (p.hasPermission("carnage.spawnholo")) {
          if (config.getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
            Main.infoholo1.remove();
            Main.infoholo2.remove();
            Main.infoholo3.remove();
            this.plugin.getConfig().set("hologram.DontEDIT", Boolean.FALSE);
            this.plugin.saveConfig();
            p.sendMessage(Color.format(config.getString("hologram-deleted")));
            return true;
          } 
          p.sendMessage(Color.format(config.getString("no-hologram-to-delete")));
        } else {
          p.sendMessage(Color.format(config.getString("no-permission")));
        }  
      if (args.length == 1 && args[0].equalsIgnoreCase("setLocation"))
        if (p.hasPermission("carnage.setlootloc")) {
          Location loc = p.getLocation();
          String w = p.getWorld().getName();
          double x = loc.getBlockX();
          double y = loc.getBlockY();
          double z = loc.getBlockZ();
          this.plugin.getConfig().set("chestloc.w", w);
          this.plugin.getConfig().set("chestloc.x", Double.valueOf(x));
          this.plugin.getConfig().set("chestloc.y", Double.valueOf(y));
          this.plugin.getConfig().set("chestloc.z", Double.valueOf(z));
          this.plugin.saveConfig();
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("location-set")));
        } else {
          p.sendMessage(Color.format(config.getString("no-permission")));
        } 

      if (p.hasPermission(config.getString("carnage.start")) && args.length == 1 && args[0].equalsIgnoreCase("start")) {
        if (ChestClick.killfeststatus == Boolean.TRUE.booleanValue()) {
          p.sendMessage(Color.format(config.getString("already-running")));
          return true;
        } 
        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);
        cloc.getBlock().setType(Material.CHEST);
        @SuppressWarnings("unused")
		Chest chest = (Chest)cloc.getBlock().getState();
        y += 0.5D;
        x += 0.5D;
        z += 0.5D;
        String path1 = config.getString("chesthologram-1");
        path1 = path1.replaceAll("%status%", "Left Click");
        path1 = path1.replaceAll("%time%", "");
        Location hloc = new Location(w, x, y - 0.3D, z);
        chestHolo1 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo1.setGravity(false);
        chestHolo1.setVisible(false);
        chestHolo1.setCustomNameVisible(true);
        chestHolo1.setCustomName(Color.format(path1));
        String path2 = config.getString("chesthologram-2");
        path2 = path2.replaceAll("%status%", "Left Click");
        path2 = path2.replaceAll("%time%", "");
        y -= 0.3D;
        hloc = new Location(w, x, y - 0.3D, z);
        chestHolo2 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo2.setGravity(false);
        chestHolo2.setVisible(false);
        chestHolo2.setCustomNameVisible(true);
        chestHolo2.setCustomName(Color.format(path2));
        String path3 = config.getString("chesthologram-3");
        path3 = path3.replaceAll("%status%", "Left Click");
        path3 = path3.replaceAll("%time%", "");
        hloc = new Location(w, x, y - 0.6D, z);
        chestHolo3 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo3.setGravity(false);
        chestHolo3.setVisible(false);
        chestHolo3.setCustomNameVisible(true);
        chestHolo3.setCustomName(Color.format(path3));
        ChestClick.killfeststatus = true;
        ChestClick.cooldownstatus = false;
        ChestClick.cheststatus = true;
        p.sendMessage(Color.format(config.getString("carnage-force-start")));
        ArrayList<String> startedkillfest = new ArrayList<>();
        for (String startedk : config.getStringList("started-carnage.lore")) {
          startedkillfest.add(Color.format(startedk));
          Bukkit.broadcastMessage(Color.format(startedk));
        } 
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "tm bc " + config.getString("message-event"));
        return true;
      } 
      if (p.hasPermission(config.getString("carnage.mad")) && args.length == 1 && args[0].equalsIgnoreCase("mad")) {
        if (ChestClick.killfeststatus == Boolean.TRUE.booleanValue()) {
          p.sendMessage(Color.format(config.getString("already-running")));
          return true;
        } 
        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);
        cloc.getBlock().setType(Material.CHEST);
        @SuppressWarnings("unused")
		Chest chest = (Chest)cloc.getBlock().getState();
        y += 0.5D;
        x += 0.5D;
        z += 0.5D;
        String path1 = config.getString("chesthologram-1");
        path1 = path1.replaceAll("%status%", "Left Click");
        path1 = path1.replaceAll("%time%", "");
        Location hloc = new Location(w, x, y - 0.3D, z);
        chestHolo1 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo1.setGravity(false);
        chestHolo1.setVisible(false);
        chestHolo1.setCustomNameVisible(true);
        chestHolo1.setCustomName(Color.format(path1));
        String path2 = config.getString("chesthologram-2");
        path2 = path2.replaceAll("%status%", "Left Click");
        path2 = path2.replaceAll("%time%", "");
        y -= 0.3D;
        hloc = new Location(w, x, y - 0.3D, z);
        chestHolo2 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo2.setGravity(false);
        chestHolo2.setVisible(false);
        chestHolo2.setCustomNameVisible(true);
        chestHolo2.setCustomName(Color.format(path2));
        String path3 = config.getString("chesthologram-3");
        path3 = path3.replaceAll("%status%", "Left Click");
        path3 = path3.replaceAll("%time%", "");
        hloc = new Location(w, x, y - 0.6D, z);
        chestHolo3 = (ArmorStand)p.getWorld().spawnEntity(hloc, EntityType.ARMOR_STAND);
        chestHolo3.setGravity(false);
        chestHolo3.setVisible(false);
        chestHolo3.setCustomNameVisible(true);
        chestHolo3.setCustomName(Color.format(path3));
        ChestClick.killfeststatus = true;
        ChestClick.cooldownstatus = false;
        ChestClick.cheststatus = true;
        p.chat("/carnage mad");
        p.sendMessage(Color.format(config.getString("carnage-force-start")));
        ArrayList<String> startedkillfest = new ArrayList<>();
        for (String startedk : config.getStringList("started-carnage.lore")) {
          startedkillfest.add(Color.format(startedk));
          Bukkit.broadcastMessage(Color.format(startedk));
        }
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "tm bc " + config.getString("message-event"));
        return true;
        
      } 
      if (p.hasPermission(config.getString("carnage.stop")) && args.length == 1 && args[0].equalsIgnoreCase("stop")) {
        if (ChestClick.killfeststatus == Boolean.FALSE.booleanValue()) {
          p.sendMessage(Color.format(config.getString("no-carnage-stop")));
          return true;
        } 
        chestHolo1.remove();
        chestHolo2.remove();
        chestHolo3.remove();
        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);
        cloc.getBlock().setType(Material.AIR);
        ArrayList<String> stopkillfest = new ArrayList<>();
        for (String stopk : config.getStringList("stop-carnage.lore")) {
          stopkillfest.add(Color.format(stopk));
          Bukkit.broadcastMessage(Color.format(stopk));
        } 
        ChestClick.cooldownstatus = false;
        ChestClick.killfeststatus = false;
        ChestClick.cheststatus = true;
        return true;
      } 
    } 
    return true;
  }
  
  public static String cl(String text) {
	  return ChatColor.translateAlternateColorCodes('&', text);
	 }
  
  public void KillfestHelpMSG(Player p) {
    FileConfiguration config = this.plugin.getConfig();
    p.sendMessage(Color.format("&d&l* &f&lDLN Carnage&d:"));
    p.sendMessage(Color.format("&7 &d- &7Made by:&d Sirvite"));
    p.sendMessage(Color.format(""));
    if (p.hasPermission(config.getString("carnage.start"))) {
      p.sendMessage(Color.format("&d&l* &7/carnage start &8- &dForcefully starts a Carnage Event."));
      if (p.hasPermission(config.getString("carnage.stop"))) {
        p.sendMessage(Color.format("&d&l* &7/carnage stop &8- &dForcefully stops a Carnage Event."));
        if (p.hasPermission(config.getString("carnage.reload"))) {
          p.sendMessage(Color.format("&d&l* &7/carnage reload &8- &dReloads the configuration."));
          if (p.hasPermission(config.getString("carnage.setlootloc"))) {
            p.sendMessage(Color.format("&d&l* &7/carnage setlocation &8- &dSets the Carnage crate location."));
                p.sendMessage(Color.format(""));
              } else {
                p.sendMessage(Color.format(config.getString("no-permission")));
              } 
            } 
          } 
        } 
      } 
    } 
