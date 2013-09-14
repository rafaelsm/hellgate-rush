package br.com.rads.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.IntMap;

public final class ScreenManager {

	private Game game;
	private IntMap<com.badlogic.gdx.Screen> screens;
	
	private static ScreenManager instance;
	private ScreenManager(){
		screens = new IntMap<com.badlogic.gdx.Screen>();
	};
	
	
	public static ScreenManager getInstance(){
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}
	
	public void initialize(Game game){
		this.game = game;
	}
	
	public void show(Screen screen){
		if(game == null) return;
		
		if(!screens.containsKey(screen.ordinal())){
			screens.put(screen.ordinal(), screen.getScreenInstance());
		}
		
		game.setScreen( screens.get(screen.ordinal()));
	}
	
	public void dispose(Screen screen){
		if(!screens.containsKey(screen.ordinal())) return;
		
		screens.remove(screen.ordinal()).dispose();
	}
	
	public void dispose(){
		for (com.badlogic.gdx.Screen screen : screens.values()) {
			screen.dispose();
		}
		screens.clear();
		instance = null;
	}
}
