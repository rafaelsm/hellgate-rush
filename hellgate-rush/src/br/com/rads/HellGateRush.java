package br.com.rads;

import br.com.rads.screens.Screen;
import br.com.rads.screens.ScreenManager;

import com.badlogic.gdx.Game;

public class HellGateRush extends Game{

	@Override
	public void create() {
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().show(Screen.MAIN_MENU);
//		setScreen( new GameScreen() );
	}
	
	@Override
	public void dispose() {
		super.dispose();
		ScreenManager.getInstance().dispose();
	}
	
}
