package br.com.rads;

import br.com.rads.screens.GameScreen;

import com.badlogic.gdx.Game;

public class HellGateRush extends Game{

	@Override
	public void create() {
		setScreen( new GameScreen() );
	}
	
}
