package mq.hidesekken.lightbringer;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import fr.hytoria.api.Player.ApiPlayer;
import mq.hidesekken.lightbringer.listener.DamageListeners;
import mq.hidesekken.lightbringer.listener.PlayerListeners;
import mq.hidesekken.lightbringer.task.WorldUtils;

public class Main extends JavaPlugin {
	
	private List<Player> players = new ArrayList<>();
	
	public WorldUtils worldUtils = new WorldUtils();
	
	private List<Location> spawns = new ArrayList<>();

	private States state;
	
	@Override
	public void onEnable() {
		System.out.println("Le plugin est pret");
		setState(States.WAITING);
		World world = Bukkit.getWorld("world");
		spawns.add(new Location(world, 902.374/*X*/, 23.0/*Y*/, -60.855/*Z*/, 23.6f/*YAW*/, -4.8f/*PITCH*/)); //les endroits de tp au luckyrush world
		spawns.add(new Location(world, 924.500/*X*/, 29.0/*Y*/, -12.500/*Z*/, 71.4f/*YAW*/, 6.0f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 1026.500/*X*/, 25.0/*Y*/, 48.000/*Z*/, -123.4f/*YAW*/, -13.1f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 1052.847/*X*/, 26.0/*Y*/, 36.464/*Z*/, 111.3f/*YAW*/, -8.1f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 1074.294/*X*/, 33.0/*Y*/, 42.500/*Z*/, 132.5f/*YAW*/, -4.1f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 766.868/*X*/, 28.0/*Y*/, 49.424/*Z*/, -94.2f/*YAW*/, -13.8f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 1052.300/*X*/, 33.0/*Y*/, 26.024/*Z*/, -62.2f/*YAW*/, 0.4f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 908.500/*X*/, 28.0/*Y*/, 31.500/*Z*/, 112.7f/*YAW*/, -7.1f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 867.435/*X*/, 30.0/*Y*/, 5.741/*Z*/, -179.4f/*YAW*/, 15.9f/*PITCH*/)); //pareil
		spawns.add(new Location(world, 855.26/*X*/, 27.0/*Y*/, -64.237/*Z*/, 2.7f/*YAW*/, 5.5f/*PITCH*/)); //pareil
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListeners(this), this);
		pm.registerEvents(new DamageListeners(this), this);
	}
	
	public void onDisable() {
		
			if(this.isState(States.FINISH)){
				
				World world = Bukkit.getWorld("world");
				World copy = Bukkit.getWorld("saveWorld");
				Bukkit.unloadWorld(world, false);
				File worldFile = new File(world.getName());
				File worldCopyFile = new File(copy.getName());
				worldUtils.deleteWorld(worldFile);
				
				
				try {
					worldUtils.copyFolder(worldCopyFile, worldFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			
			
		}
	

	public void setState(States state) {
		this.state = state;
	}
	
	public boolean isState(States state) {
		return this.state == state;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Location> getSpawns(){
		return spawns;
	}

	public void eliminate(Player player) {
		if(players.contains(player)) players.remove(player);
		player.setGameMode(GameMode.SPECTATOR);
		player.sendMessage("Vous avez succombé.");
		checkWin();
		
	}

	public void checkWin() {
		if(players.size() == 1 )
		{
			Player winner = players.get(0);
			for(Player players : Bukkit.getOnlinePlayers())
			{
				players.sendMessage(winner.getName()+" Sort vainqueur de l'Acte II.");
		
				ApiPlayer apiplayers = new ApiPlayer((CraftPlayer)players);
	              apiplayers.sendTo("HubMJ");
	              Bukkit.spigot().restart();
				}
				
			}
			
		
		
	}

	
	
	
}
