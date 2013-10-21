/**
 * 
 */
package br.com.rads.screens;

import br.com.rads.controller.HellController;
import br.com.rads.controller.Resources;
import br.com.rads.model.Hell;
import br.com.rads.model.stage.DesertArea;
import br.com.rads.view.HellRenderer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author rafael da silva melo
 * 
 */
public class GameScreen implements Screen, InputProcessor {

	private Hell hell;
	private HellRenderer renderer;
	private HellController controller;

	private int width;
	private int height;

	/**
	 * Inicia o mundo e o render
	 */
	@Override
	public void show() {
		Resources resources = Resources.getInstance();
		if (resources.getMusic() != null) {
			try{
			if (!resources.getMusic().isPlaying()) {
				resources.getMusic().play();
				resources.getMusic().setLooping(true);
			}
			}catch(Exception e){
				resources.restart();
				resources.playMusic();
			}
		}

		hell = new Hell(new DesertArea(600, 7));
		renderer = new HellRenderer(hell, true);
		controller = new HellController(hell);
		Gdx.input.setInputProcessor(this);

	}

	/**
	 * Método que desenha na tela
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		renderer.render(delta);

	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		// music.dispose();
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
		// music.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.SPACE) {
			controller.jumpPressed();
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.SPACE) {
			controller.jumpReleased();
		}

		if (keycode == Keys.D)
			renderer.setDebug(!renderer.isDebug());

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		controller.jumpPressed();

		if (pointer >= 1)
			renderer.setDebug(!renderer.isDebug());

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		controller.jumpReleased();

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
