/**
 * 
 */
package br.com.rads.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import br.com.rads.model.Ground;
import br.com.rads.model.Hell;
import br.com.rads.model.Minion;

/**
 * @author rafael da silva melo
 * 
 *         Classe que renderiza a tela
 * 
 */
public class HellRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private Hell hell;
	private OrthographicCamera camera;

	// debug apenas
	private ShapeRenderer debugRenderer = new ShapeRenderer();

	private SpriteBatch batch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;

	public HellRenderer(Hell hellArea, boolean debug) {
		this.hell = hellArea;
		this.camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT); // inicia
																			// camera
																			// para
																			// pegar
																			// a
																			// tela
																			// toda
																			// em
																			// 10/7
		this.camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0); // posiciona
																			// a
																			// camera
																			// no
																			// meio
																			// da
																			// tela
		this.camera.update();
		this.debug = debug;
		batch = new SpriteBatch();

	}

	public void render() {
		// quando tiver texturas
		// batch.begin();
		// drawGround();
		// drawMinion();
		// batch.end();

		drawCollision();
		drawDebug();
//
		Minion m = hell.getMinion();
		moveCamera(m.getPosition().x, CAMERA_HEIGHT / 2);

	}

	private void drawDebug() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Ground g : hell.getDrawableGround((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {

			Rectangle rect = g.getBounds();
			debugRenderer.setColor(Color.RED);
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		}
		
		Minion m = hell.getMinion();
		
		Rectangle rect = m.getBounds();
		debugRenderer.setColor(Color.GREEN);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		debugRenderer.end();
	}

	private void drawCollision() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : hell.getCollisionRect()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();

	}

	public void moveCamera(float x, float y) {
//		camera.position.x = hell.getMinion().getPosition().x
//				+ hell.getMinion().getBounds().x;
		this.camera.position.set(hell.getMinion().getPosition().x + 4f,hell.getMinion().getPosition().y + 2f,0);
		camera.update();
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.ppuX = (float) width / CAMERA_WIDTH;
		this.ppuY = (float) height / CAMERA_HEIGHT;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
