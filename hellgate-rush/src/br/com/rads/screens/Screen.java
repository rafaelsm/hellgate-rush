package br.com.rads.screens;


public enum Screen {

	MAIN_MENU{
		@Override
		protected com.badlogic.gdx.Screen getScreenInstance(){
			return new MainMenuScreen();
		}
	},
	GAME{
		@Override
		protected com.badlogic.gdx.Screen getScreenInstance(){
			return new GameScreen();
		}
	},
	GAME_OVER{
		@Override
		protected com.badlogic.gdx.Screen getScreenInstance() {
			return new GameOverScreen();
		}
	};

	protected abstract com.badlogic.gdx.Screen getScreenInstance();
}
