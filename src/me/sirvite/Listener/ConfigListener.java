package me.sirvite.Listener;

import me.sirvite.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConfigListener implements Listener {
  Main plugin;
  
  public ConfigListener(Main instance) {
    this.plugin = instance;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {}
}
