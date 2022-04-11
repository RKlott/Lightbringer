package mq.hidesekken.lightbringer.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreBoard implements Listener{
	
	
	public Map<Player, ScoreboardSign> boards = new HashMap<>();

	public ScoreBoard (ScoreboardSign sc) {
		
		
	}
	
	public ScoreBoard (GameCycle gc) {
	}



@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
			sign.getValue().setLine(4, "" + Bukkit.getOnlinePlayers().size());
		}
		
		ScoreboardSign scoreboard = new ScoreboardSign(player, "§e§lLUCKYRUSH");
		scoreboard.create();
		scoreboard.setLine(0, "§7Affrontement dans :");
		scoreboard.setLine(1, "§e"+ GameCycle.timer );
		scoreboard.setLine(2, "§a ");
		scoreboard.setLine(3, "§aJoueurs connectés :");
		scoreboard.setLine(4, "" + Bukkit.getOnlinePlayers().size());
		scoreboard.setLine(5, "§d ");
		scoreboard.setLine(6, "§eOriginsMC.fr");
		boards.put(player, scoreboard);
		
		
	
}
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		
		for(Entry<Player, ScoreboardSign> sign : boards.entrySet()){
			sign.getValue().setLine(4, "" + (Bukkit.getOnlinePlayers().size() - 1));
		}
		
		if(boards.containsKey(player)){
			boards.get(player).destroy();
		}
		
	}

}
