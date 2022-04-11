package mq.hidesekken.lightbringer.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import mq.hidesekken.lightbringer.Main;
import mq.hidesekken.lightbringer.States;

public class DamageListeners implements Listener {
	
	private Main main;
	
	public DamageListeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onFallingDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if(event.getCause() == DamageCause.FALL) {
				event.setCancelled(true);
			}
			if(p.getLastDamageCause().getCause() == DamageCause.FALL) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	  public void onPlayerDeath(PlayerDeathEvent e)
	  {
		
	  }
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if(!main.isState(States.PLAYING))
		{
			e.setCancelled(true);
			return;
		}
		
		Entity victim = e.getEntity();
		if(victim instanceof Player)
			{
			
			Player player = (Player) victim;
			if(player.getHealth() <= e.getDamage())
			{
				e.setDamage(0);
				main.eliminate(player);
			}
		}
		
	}
	
  
	
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		
		Entity victim = e.getEntity();
		
		if(!main.isState(States.PLAYING))
		{
			e.setCancelled(true);
			return;
		}
		
		if(victim instanceof Player)
			{
			
			Player player = (Player) victim;
			Entity damager = e.getDamager();
			Player killer = player.getKiller();
			
			
			
			if(player.getHealth() <= e.getDamage())
			{
				if(damager instanceof Player) killer = (Player)damager;

				if(damager instanceof Arrow) 
				{
					Arrow arrow = (Arrow)damager;
					if(arrow.getShooter() instanceof Player)
					{
						killer = (Player) arrow.getShooter();
					}
					
				}
				
				killer.sendMessage("§aVous venez d'éliminer §b" + victim.getCustomName());
				e.setDamage(0);
				main.eliminate(player);
			}
		}
		
	}
	
	

}
