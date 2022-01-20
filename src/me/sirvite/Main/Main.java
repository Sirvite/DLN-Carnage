package me.sirvite.Main;

import java.util.ArrayList;
import java.util.List;

import me.sirvite.Color.Color;
import me.sirvite.Listener.ChestClick;
import me.sirvite.Listener.ConfigListener;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	@SuppressWarnings("unused")
	private Carnage commands = new Carnage(null);
  public static long serverStartTime = System.currentTimeMillis();
  
  public static ArmorStand infoholo1;
  
  public static ArmorStand infoholo2;
  
  public static ArmorStand infoholo3;
  
  public static int Cooldown = 3600;
  
  public static int StartCooldown = 300;
  
  @SuppressWarnings("unused")
private int autostartt;
  
  public static int placeholder;
  
  
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
    getServer().getPluginManager().registerEvents(this, (Plugin)this);
    getCommand("carnage").setExecutor(new Carnage(this));
    
    registerListeners();
    Bukkit.getConsoleSender().sendMessage(Color.format("&8------------------------------------------"));
    Bukkit.getConsoleSender().sendMessage(Color.format("&8"));
    Bukkit.getConsoleSender().sendMessage(Color.format("&8[&aDLN-Carnage&8] &a&nEnabled&a!"));
    Bukkit.getConsoleSender().sendMessage(Color.format("&8[&aDLN-Carnage&8] &aAny Errors? Contact Sirvite"));
    Bukkit.getConsoleSender().sendMessage(Color.format("&8"));
    Bukkit.getConsoleSender().sendMessage(Color.format("&8------------------------------------------"));
    if (getConfig().getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
      World w = Bukkit.getWorld(getConfig().getString("hologram.loc.w"));
      double x = getConfig().getDouble("hologram.loc.x");
      double y = getConfig().getDouble("hologram.loc.y");
      double z = getConfig().getDouble("hologram.loc.z");
      Location tloc = new Location(w, x - 0.59D, y, z);
      x -= -1.12D;
      y -= 2.0D;
      z -= -0.5D;
      tloc = new Location(w, x - 0.59D, y, z);
      infoholo1 = (ArmorStand)Bukkit.getWorld(getConfig().getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
      infoholo1.setGravity(false);
      infoholo1.setVisible(false);
      infoholo1.setCustomNameVisible(true);
      infoholo1.setCustomName(Color.format("&4&k|||&f &c&lRestart The Server &4&k|||"));
      y -= 0.3D;
      tloc = new Location(w, x - 0.59D, y, z);
      infoholo2 = (ArmorStand)Bukkit.getWorld(getConfig().getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
      infoholo2.setGravity(false);
      infoholo2.setVisible(false);
      infoholo2.setCustomNameVisible(true);
      infoholo2.setCustomName(Color.format("[Error]"));
      y -= 0.3D;
      tloc = new Location(w, x - 0.59D, y, z);
      infoholo3 = (ArmorStand)Bukkit.getWorld(getConfig().getString("hologram.loc.w")).spawnEntity(tloc, EntityType.ARMOR_STAND);
      infoholo3.setGravity(false);
      infoholo3.setVisible(false);
      infoholo3.setCustomNameVisible(true);
      infoholo3.setCustomName(Color.format("[Error]"));
      placeholders();
    } 
  }
  
  public void onDisable() {
	    if (getConfig().getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
	      infoholo1.remove();
	      infoholo2.remove();
	      infoholo3.remove();
	    } 
	    if (ChestClick.killfeststatus == Boolean.TRUE.booleanValue() || ChestClick.killfeststatus == Boolean.FALSE.booleanValue()) {
	      Carnage.chestHolo1.remove();
	      Carnage.chestHolo2.remove();
	      Carnage.chestHolo3.remove();
	      World w = Bukkit.getWorld(getConfig().getString("chestloc.w"));
	      double x = getConfig().getDouble("chestloc.x");
	      double y = getConfig().getDouble("chestloc.y");
	      double z = getConfig().getDouble("chestloc.z");
	      Location cloc = new Location(w, x, y, z);
	      cloc.getBlock().setType(Material.AIR);
	      ChestClick.cooldownstatus = false;
	      ChestClick.killfeststatus = false;
	      ChestClick.cheststatus = true;
	    } 
	    Bukkit.getConsoleSender().sendMessage(Color.format("&8[&dDLN-Carnage&8] &d&nDisabled&c!"));
	    saveConfig();
	  }
  
  public void registerListeners() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents((Listener)new ConfigListener(this), (Plugin)this);
    pm.registerEvents((Listener)new ChestClick(this), (Plugin)this);
  }
 
  
  @EventHandler
  public void onClickItem1(PlayerInteractEvent event) {
   final Player player = event.getPlayer();
   if (player.hasPermission("carnage.use")){
 	  if (event.hasItem() && event.getPlayer().getItemInHand().getType().equals(Material.SLIME_BALL))
 		     if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
 		       player.performCommand("carnage mad");
 		       
 		     ItemStack summoneritem = new ItemStack(Material.FIREWORK_CHARGE, 1);
 		     ItemMeta expiredmeta = summoneritem.getItemMeta();
 		     List<String> summonlore = new ArrayList<String>();
 		    
 		     expiredmeta.setDisplayName(cl("&d&ki&f &lCarnage Summoner&d &ki&7 &o(Expired)"));
 		     summonlore.add(cl("&7This can no longer be used to start the"));
 		     summonlore.add(cl("&f&lCarnage Crate&7 in the warzone."));
 		     summonlore.add(cl("&7"));
 		     
 		     expiredmeta.setLore(summonlore);
 		     summoneritem.setItemMeta(expiredmeta);
 		     
 		       event.getPlayer().getItemInHand().setType(Material.FIREWORK_CHARGE);
 		      event.getPlayer().getItemInHand().setItemMeta(expiredmeta);
 		       
 		     	}
   	}
   
  }

   public static String cl(String text) {
		  return ChatColor.translateAlternateColorCodes('&', text);
		 }
//   Autostart Feature Temporarily Disabled
  /* 
  public void autostart() {
    final FileConfiguration config = getConfig();
    this.autostartt = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable() {
          public void run() {
            Main.Cooldown--;
            if (Main.Cooldown <= 0)
              if (!ChestClick.killfeststatus) {
                World w = Bukkit.getWorld(config.getString("chestloc.w"));
                double x = config.getDouble("chestloc.x");
                double y = config.getDouble("chestloc.y");
                double z = config.getDouble("chestloc.z");
                Location cloc = new Location(w, x, y, z);
                cloc.getBlock().setType(Material.CHEST);
                Chest chest = (Chest)cloc.getBlock().getState();
                String path1 = config.getString("chesthologram-1");
                path1 = path1.replaceAll("%status%", "Left Click");
                path1 = path1.replaceAll("%time%", "");
                y += 0.3D;
                x += 0.5D;
                z += 0.6D;
                Location hloc = new Location(w, x, y - 0.3D, z);
                Carnage.chestHolo1 = (ArmorStand)Bukkit.getWorld(config.getString("chestloc.w")).spawnEntity(hloc, EntityType.ARMOR_STAND);
                Carnage.chestHolo1.setGravity(false);
                Carnage.chestHolo1.setVisible(false);
                Carnage.chestHolo1.setCustomNameVisible(true);
                Carnage.chestHolo1.setCustomName(Color.format(path1));
                String path2 = config.getString("chesthologram-2");
                path2 = path2.replaceAll("%status%", "Left Click");
                path2 = path2.replaceAll("%time%", "");
                y -= 0.3D;
                hloc = new Location(w, x, y - 0.3D, z);
                Carnage.chestHolo2 = (ArmorStand)Bukkit.getWorld(config.getString("chestloc.w")).spawnEntity(hloc, EntityType.ARMOR_STAND);
                Carnage.chestHolo2.setGravity(false);
                Carnage.chestHolo2.setVisible(false);
                Carnage.chestHolo2.setCustomNameVisible(true);
                Carnage.chestHolo2.setCustomName(Color.format(path2));
                String path3 = config.getString("chesthologram-3");
                path3 = path3.replaceAll("%status%", "Left Click");
                path3 = path3.replaceAll("%time%", "");
                hloc = new Location(w, x, y - 0.6D, z);
                Carnage.chestHolo3 = (ArmorStand)Bukkit.getWorld(config.getString("chestloc.w")).spawnEntity(hloc, EntityType.ARMOR_STAND);
                Carnage.chestHolo3.setGravity(false);
                Carnage.chestHolo3.setVisible(false);
                Carnage.chestHolo3.setCustomNameVisible(true);
                Carnage.chestHolo3.setCustomName(Color.format(path3));
                ChestClick.killfeststatus = true;
                ChestClick.cooldownstatus = false;
                ChestClick.cheststatus = true;
                ArrayList<String> startedkillfest = new ArrayList<>();
                for (String startedk : config.getStringList("started-carnage.lore")) {
                  startedkillfest.add(Color.format(startedk));
                  Bukkit.broadcastMessage(Color.format(startedk));
                } 
                Main.Cooldown = 3600;
              }  
          }
        },0L, 20L);
  }
  */
  
  public void placeholders() {
    final FileConfiguration config = getConfig();
    placeholder = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable() {
          public void run() {
            if (Main.this.getConfig().getBoolean("hologram.DontEDIT") == Boolean.TRUE.booleanValue()) {
              String path1 = config.getString("infohologram-1");
              String path2 = config.getString("infohologram-2");
              String path3 = config.getString("infohologram-3");
              if (ChestClick.cheststatus == Boolean.TRUE.booleanValue()) {
                path1 = path1.replaceAll("%status%", "Resetting");
                path1 = path1.replaceAll("%time%", "");
                path2 = path2.replaceAll("%status%", "Resetting");
                path2 = path2.replaceAll("%time%", "");
                path3 = path3.replaceAll("%status%", "Resetting");
                path3 = path3.replaceAll("%time%", "");
                Main.infoholo1.setCustomName(Color.format(path1));
              } 
              if (ChestClick.cooldownstatus == Boolean.TRUE.booleanValue()) {
                ChestClick.Counterdown--;
                int mins = ChestClick.Counterdown / 60;
                int secs = ChestClick.Counterdown % 60;
                path1 = path1.replaceAll("%status%", "Unlocking...");
                path1 = path1.replaceAll("%time%", String.valueOf(mins) + ":" + secs);
                path2 = path2.replaceAll("%status%", "Unlocking...");
                path2 = path2.replaceAll("%time%", String.valueOf(mins) + ":" + secs);
                path3 = path3.replaceAll("%status%", "Unlocking...");
                path3 = path3.replaceAll("%time%", String.valueOf(mins) + ":" + secs);
                Main.infoholo1.setCustomName(Color.format(path1));
                Main.infoholo2.setCustomName(Color.format(path2));
                Main.infoholo3.setCustomName(Color.format(path3));
              } 
              if (ChestClick.cheststatus == Boolean.FALSE.booleanValue() && ChestClick.cooldownstatus == Boolean.FALSE.booleanValue() && ChestClick.killfeststatus == Boolean.TRUE.booleanValue()) {
                path1 = path1.replaceAll("%status%", "Claim Prize");
                path1 = path1.replaceAll("%time%", "00:00");
                path2 = path2.replaceAll("%status%", "Claim Prize");
                path2 = path2.replaceAll("%time%", "00:00");
                path3 = path3.replaceAll("%status%", "Claim Prize");
                path3 = path3.replaceAll("%time%", "00:00");
                Main.infoholo1.setCustomName(Color.format(path1));
                Main.infoholo2.setCustomName(Color.format(path2));
                Main.infoholo3.setCustomName(Color.format(path3));
              } 
              if (ChestClick.cheststatus == Boolean.FALSE.booleanValue()) {
                int hours1 = Main.Cooldown / 3600;
                int A1 = Main.Cooldown / 60;
                int minutes = A1 % 60;
                @SuppressWarnings("unused")
				int hours = hours1 % 24;
                int seconds = Main.Cooldown % 60;
                
                path1 = path1.replaceAll("%status%", "Cooldown");
                path1 = path1.replaceAll("%time%", String.valueOf(minutes) + " Minutes" + " " + seconds + " Seconds");
                path2 = path2.replaceAll("%status%", "Cooldown");
                path2 = path2.replaceAll("%time%", String.valueOf(minutes) + " Minutes" + " " + seconds + " Seconds");
                path3 = path3.replaceAll("%status%", "Cooldown");
                path3 = path3.replaceAll("%time%", String.valueOf(minutes) + " Minutes" + " " + seconds + " Seconds");
                Main.infoholo1.setCustomName(Color.format(path1));
                Main.infoholo2.setCustomName(Color.format(path2));
                Main.infoholo3.setCustomName(Color.format(path3));
              } 
            } 
          }
        },0L, 20L);
  }
}
