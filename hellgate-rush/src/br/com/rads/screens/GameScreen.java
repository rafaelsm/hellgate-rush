/**
 * 
 */
package br.com.rads.screens;

import br.com.rads.controller.HellController;
import br.com.rads.model.FirstArea;
import br.com.rads.model.HellArea;
import br.com.rads.view.HellRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author rafael da silva melo
 *
 */
public class GameScreen implements Screen, InputProcessor{

	private HellArea hellArea;
	private HellRenderer renderer;
	private HellController controller;
	
	private int width;
	private int height;
	
	/**
	 * Inicia o mundo e o render
	 */
	@Override
	public void show() {
		hellArea = new HellArea(new FirstArea(100, 7));
		renderer = new HellRenderer(hellArea);
		controller = new HellController(hellArea,false); //change here for infinity run
	}
	
	/**
	 * Método que desenha na tela
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		controller.update(delta);
		renderer.render();
		
		Gdx.input.setInputProcessor(this);
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
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (screenX < width / 2 && screenY < height / 2) {
			controller.jumpPressed();
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		if (screenX < width / 2 && screenY < height / 2) {
			controller.jumpReleased();
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
