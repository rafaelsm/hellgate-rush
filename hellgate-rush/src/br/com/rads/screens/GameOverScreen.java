package br.com.rads.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameOverScreen implements com.badlogic.gdx.Screen, InputProcessor {

	private SpriteBatch batch = null;
	private Texture background;
	private Texture restartButton;
	private Texture exitButton;
	
	private Rectangle startRect = new Rectangle(246, 100, 289, 129);
	private Rectangle exitRect = new Rectangle(747, 100, 289, 129);
	
	public GameOverScreen() {
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		loadTextures();
	}
	
	private void loadTextures() {
		background = new Texture(
				Gdx.files.internal("images/textures/game_over.png"));
		restartButton = new Texture(
				Gdx.files.internal("images/textures/game_over_restart.png"));
		exitButton = new Texture(
				Gdx.files.internal("images/textures/game_over_exit.png"));
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		drawBackground();
		drawStartButton();
		drawExitButton();
		batch.end();
	}

	private void drawBackground() {
		float x = 0;
		float y = 0;
		float width = 1280;
		float height = 720;
		
		batch.draw(background, x, y, width, height);
	}
	
	private void drawStartButton() {
		batch.draw(restartButton, startRect.x, startRect.y, startRect.width, startRect.height);
	}

	private void drawExitButton() {
		batch.draw(exitButton, exitRect.x, exitRect.y, exitRect.width, exitRect.height);
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		Gdx.app.log("TOUCH", "x");
		
		if (exitRect.contains(screenX, (screenY-720)*-1)) {
			Gdx.app.exit();
		} else if (startRect.contains(screenX, (screenY-720)*-1)) {
			ScreenManager.getInstance().show(Screen.GAME);
		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
