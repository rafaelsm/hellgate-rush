package br.com.rads.screens;

import br.com.rads.screens.Button.ButtonHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements com.badlogic.gdx.Screen {

	private SpriteBatch batch = null;
	private OrthographicCamera camera = null;
	private BitmapFont font = null;
	private Label headingLabel = null;
	private Button playButton = null;
	private Button exitButton = null;
	private int lineHeight = 0;
	
	public MainMenuScreen() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.scale(5f);
		lineHeight = Math.round(2.5f * font.getCapHeight());
		headingLabel = new Label("Screen Manager Demo", font);
		playButton = new Button("Play", font, new ScreenSwitchHandler(Screen.GAME));
		exitButton = new Button("Exit", font, new ButtonHandler() {
			@Override
			public void onClick() {
				Gdx.app.exit();
			}
		});
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		headingLabel.draw(batch);
		playButton.draw(batch, camera);
		exitButton.draw(batch, camera);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		batch.setProjectionMatrix(camera.combined);
		int centerX = width / 2;
		int centerY = height / 2;
		headingLabel.setX(centerX - headingLabel.getWidth() / 2);
		headingLabel.setY(centerY + 2 * lineHeight);
		playButton.setX(centerX - playButton.getWidth() / 2);
		playButton.setY(centerY + lineHeight);
		exitButton.setX(centerX - exitButton.getWidth() / 2);
		exitButton.setY(centerY - lineHeight); 
	}

	@Override
	public void show() {
		
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
		font.dispose();
		batch.dispose();
	}

}
