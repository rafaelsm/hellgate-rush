/**
 * 
 */
package br.com.rads.screens;

import br.com.rads.controller.HellController;
import br.com.rads.model.HellArea;
import br.com.rads.view.HellRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author rafael da silva melo
 *
 */
public class GameScreen implements Screen {

	private HellArea hellArea;
	private HellRenderer renderer;
	private HellController controller;
	
	/**
	 * Inicia o mundo e o render
	 */
	@Override
	public void show() {
		hellArea = new HellArea();
		renderer = new HellRenderer(hellArea);
		controller = new HellController(hellArea);
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
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
