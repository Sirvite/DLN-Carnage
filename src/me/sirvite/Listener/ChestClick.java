package me.sirvite.Listener;

import java.util.ArrayList;
import me.sirvite.Color.Color;
import me.sirvite.Main.Carnage;
import me.sirvite.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class ChestClick implements Listener {
  Main plugin;
  
  public static boolean killfeststatus;
  
  public static boolean cooldownstatus;
  
  public static boolean cheststatus;
  
  private int cooldowntimer;
  
  int delechest;
  
  public static int Counterdown = 300;
  
  public ChestClick(Main instance) {
    this.plugin = instance;
  }
  
  @EventHandler
  public void onChestOpen(PlayerInteractEvent e) {
    Action action = e.getAction();
    @SuppressWarnings("unused")
	Player player = e.getPlayer();
    Block block = e.getClickedBlock();
    if (action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
      if (block.getType().equals(Material.CHEST)) {
        FileConfiguration config = this.plugin.getConfig();
        Player p = e.getPlayer();
        World w = Bukkit.getWorld(config.getString("chestloc.w"));
        double x = config.getDouble("chestloc.x");
        double y = config.getDouble("chestloc.y");
        double z = config.getDouble("chestloc.z");
        Location cloc = new Location(w, x, y, z);
        if (e.getClickedBlock().getLocation().equals(cloc)) {
          if (killfeststatus == Boolean.TRUE.booleanValue()) {
            if (cheststatus == Boolean.TRUE.booleanValue()) {
              String path1 = config.getString("player-started-countdown");
              path1 = path1.replaceAll("%player%", p.getName());
              path1 = path1.replaceAll("%coords%", "x:" + x + ", y:" + y + ", z:" + z);
              Bukkit.broadcastMessage(Color.format(""));
              Bukkit.broadcastMessage(Color.format(path1));
              Bukkit.broadcastMessage(Color.format(""));
              Counterdown = 300;
              cheststatus = Boolean.FALSE.booleanValue();
              cooldownstatus = Boolean.TRUE.booleanValue();
              cooldown();
              return;
            } 
            if (cooldownstatus == Boolean.TRUE.booleanValue()) {
              p.sendMessage(Color.format(config.getString("already-on-countdown")));
              return;
            } 
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), config.getString("command").replaceAll("%player%", p.getName()));
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "plugman reload DLN-Carnage");
            @SuppressWarnings("unused")
			ArrayList<String> winnerBroadcast = new ArrayList<>();
            Bukkit.broadcastMessage(Color.format(""));
            Bukkit.broadcastMessage(Color.format("&dInformation: "));
            Bukkit.broadcastMessage(Color.format(""));
            Bukkit.broadcastMessage(Color.format("&d&l* &7Winner: &b&n" + p.getName()));
            Bukkit.broadcastMessage(Color.format(""));
            Bukkit.broadcastMessage(Color.format("&d&l* &7The Carnage crate has been claimed. A reward"));
            Bukkit.broadcastMessage(Color.format("&7 of a &d&nMysterious Key&7 has been awarded to the &d&nVictor!"));
            Bukkit.broadcastMessage(Color.format(""));
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "plugman reload DLN-Carnage");
            Carnage.chestHolo1.remove();
            Carnage.chestHolo2.remove();
            Carnage.chestHolo3.remove();
            cooldownstatus = false;
            killfeststatus = false;
            cheststatus = true;
            cloc.getBlock().setType(Material.AIR);
            return;
          } 
          return;
        } 
        return;
      } 
      return;
    } 
  }
  
  public void delechest() {
    FileConfiguration config = this.plugin.getConfig();
    World w = Bukkit.getWorld(config.getString("chestloc.w"));
    double x = config.getDouble("chestloc.x");
    double y = config.getDouble("chestloc.y");
    double z = config.getDouble("chestloc.z");
    final Location cloc = new Location(w, x, y, z);
    this.delechest = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable() {
          int Counterdownd = 3;
          
          public void run() {
            this.Counterdownd -= 0;
            if (this.Counterdownd == 0) {
              cloc.getBlock().setType(Material.AIR);
              Bukkit.getScheduler().cancelTask(ChestClick.this.delechest);
            } 
          }
        },  0L, 20L);
  }
  
  public void cooldown() {
    final FileConfiguration config = this.plugin.getConfig();
    this.cooldowntimer = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable() {
          public void run() {
            if (ChestClick.cooldownstatus) {
              int mins = ChestClick.Counterdown / 60;
              int secs = ChestClick.Counterdown % 60;
              String path1 = config.getString("chesthologram-1");
              path1 = path1.replaceAll("%status%", "Unlocking...");
              path1 = path1.replaceAll("%time%", String.valueOf(mins) + ":" + secs);
              Carnage.chestHolo1.setCustomName(Color.format(path1));
              String path2 = config.getString("chesthologram-2");
              path2 = path2.replaceAll("%status%", "Unlocking...");
              path2 = path2.replaceAll("%time%", String.valueOf(mins) + ":" + secs);
              Carnage.chestHolo2.setCustomName(Color.format(path2));
              String path3 = config.getString("chesthologram-3");
              path3 = path3.replaceAll("%status%", "Unlocking...");
              path3 = path3.replaceAll("%time%", String.valueOf(mins) + ":" + secs);
              Carnage.chestHolo3.setCustomName(Color.format(path3));
              if (ChestClick.Counterdown == 0) {
                String path4 = config.getString("chesthologram-1");
                path4 = path4.replaceAll("%status%", "Collect Prize");
                path4 = path4.replaceAll("%time%", "00:00");
                Carnage.chestHolo3.setCustomName(Color.format(path4));
                String path5 = config.getString("chesthologram-2");
                path5 = path5.replaceAll("%status%", "Collect Prize");
                path5 = path5.replaceAll("%time%", "00:00");
                Carnage.chestHolo3.setCustomName(Color.format(path5));
                String path6 = config.getString("chesthologram-3");
                path6 = path6.replaceAll("%status%", "Collect Prize");
                path6 = path6.replaceAll("%time%", "00:00");
                Carnage.chestHolo3.setCustomName(Color.format(path6));
                Bukkit.getScheduler().cancelTask(ChestClick.this.cooldowntimer);
                ChestClick.cooldownstatus = false;
                Bukkit.broadcastMessage(Color.format(""));
                Bukkit.broadcastMessage(Color.format(""));
                Bukkit.broadcastMessage(Color.format(config.getString("cooldownEnded")));
                Bukkit.broadcastMessage(Color.format(""));
                Bukkit.broadcastMessage(Color.format(""));
              } 
            } else {
              Bukkit.getScheduler().cancelTask(ChestClick.this.cooldowntimer);
            } 
          }
        },0L, 20L);
  }
}
