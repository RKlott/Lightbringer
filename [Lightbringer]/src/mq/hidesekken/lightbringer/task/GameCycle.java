package mq.hidesekken.lightbringer.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import mq.hidesekken.lightbringer.Main;
import mq.hidesekken.lightbringer.States;

public class GameCycle extends BukkitRunnable{
	
	private Main main;
	static int timer = 300;
	
	public GameCycle(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		if(timer == 10 ||
				timer == 5 ||
				timer == 4 ||
				timer == 3 ||
				timer == 2 ||
				timer == 1 ) 
		{
			Bukkit.broadcastMessage("§eActe II dans : §a" + timer + "§2s, phase d'affrontement");
		}
		
		if(timer == 0)
		{
			for(Player pls : main.getPlayers())
			{
				pls.teleport(new Location(Bukkit.getWorld("world"), 923.602, 29.0, 103.657, 123.0f, 4.4f)); //Zone d'affrontement
			}
			cancel();
			Bukkit.broadcastMessage("§6Lancement de l'Acte II, Affrontement. ");
			main.setState(States.PLAYING);
		}
		
		
		timer --;
		
	}

}

