package br.com.rads.screens;

import br.com.rads.screens.Button.ButtonHandler;

public class ScreenSwitchHandler implements ButtonHandler {
	
	private Screen screen = null;
	
	public ScreenSwitchHandler(Screen screen) {
		this.screen = screen;
	}

	@Override
	public void onClick() {
		/* easily implemented screen switching thanks to singleton pattern */
		ScreenManager.getInstance().show(screen);
	}

}
