package mq.hidesekken.lightbringer.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import mq.hidesekken.lightbringer.Main;
import mq.hidesekken.lightbringer.States;

public class Autostart extends BukkitRunnable {
	

	private Main main;
	private int timer = 10;
	
	

	public Autostart(Main main) {
		this.main = main;
	}



	@Override
	public void run() {
		
		for(Player pls : main.getPlayers()) {
			pls.setLevel(timer);
		}
		
		if(timer == 10 ||
				timer == 9 ||
				timer == 8 ||
				timer == 7 ||
				timer == 6 ||
				timer == 5 ||
				timer == 4 ||
				timer == 3 ||
				timer == 2 ||
				timer == 1 ) {
			Bukkit.broadcastMessage("§eActe I, LuckyBlocks dans : §a" + timer + "§2s");
		}
		
		if(timer == 0)
		{
			Bukkit.broadcastMessage("§6Commencement de l'Acte I, Acte II dans 10 minutes.");
			main.setState(States.MINING);
			
			for(int i = 0; i < main.getPlayers().size(); i++)
			{
				Player player = main.getPlayers().get(i);
				Location spawn = main.getSpawns().get(i);
				player.teleport(spawn);
				player.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
				player.setGameMode(GameMode.SURVIVAL);
			}
			
			GameCycle cycle = new GameCycle(main);
			cycle.runTaskTimer(main, 0, 20);
			
			cancel();
		}

		timer--;
		
	}

}
