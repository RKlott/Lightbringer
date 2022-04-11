package mq.hidesekken.lightbringer.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mq.hidesekken.lightbringer.Main;
import mq.hidesekken.lightbringer.States;
import mq.hidesekken.lightbringer.task.Autostart;

public class PlayerListeners implements Listener {
	
	private Main main;
	String prefix = "§7[§bLUCKYRUSH§7] §b";

	public PlayerListeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		Location lobby = new Location(Bukkit.getWorld("world"), 642.241, 39.0, 40.827, -174.4f, -12.9f); //a definir la salle d'attente
		player.teleport(lobby);
		player.getInventory().clear();
		player.setFoodLevel(20);
		player.setMaxHealth(40.0D);
		player.setHealth(20.0D);
		
		if(!main.isState(States.WAITING))
		{
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("§7Une partie est en cours !"); //a changer
			e.setJoinMessage(null);
			return;
		}
		if(!main.getPlayers().contains(player))
			main.getPlayers().add(player);
		player.setGameMode(GameMode.ADVENTURE);
		e.setJoinMessage(prefix+player.getName()+" §aRejoint le lobby. §e[§6"+main.getPlayers().size()+"/10§e]");
		
		if(main.isState(States.WAITING) && main.getPlayers().size() == 2)
		{
			main.setState(States.STARTING); //supprimer si ya un prob
			
			Autostart start = new Autostart(main);
			start.runTaskTimer(main, 0, 20);
			main.setState(States.MINING);
			
		}
			
		}
		
		
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player player = e.getPlayer();
		if(main.getPlayers().contains(player)) 
		{
			main.getPlayers().remove(player);
		}
		
		e.setQuitMessage(prefix+player.getName()+" §cest parti.");
		main.checkWin();
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(!main.isState(States.MINING)) {
		
		
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(!main.isState(States.MINING) && e.getBlock().getType() != Material.EMERALD_BLOCK) {
			e.setCancelled(true);
			return;
		}
				
			}
		
	
	
	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		
	}
	

}
