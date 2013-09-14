/**
 * 
 */
package br.com.rads.view;

import br.com.rads.model.Ground;
import br.com.rads.model.Hell;
import br.com.rads.model.Minion;
import br.com.rads.model.Pancake;
import br.com.rads.model.enemy.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author rafael da silva melo
 * 
 *         Classe que renderiza a tela
 * 
 */
public class HellRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private static final float RUNNING_FRAME_DURATION = 0.03f;
	private static final float FIRE_FRAME_DURATION = 0.04f;
	private static final float ENEMY_FRAME_DURATION = 0.04f;

	/**
	 * Textures/animation
	 */
	private TextureRegion minionFrame;
	private Animation running;
	private TextureRegion pancakeFrame;
	private Animation pancakeFire;

	private Texture groundTexture;

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
		this.camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.camera.update();
		this.debug = debug;
		batch = new SpriteBatch();
		loadTextures();

	}

	public void render(float delta) {

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			drawMinion();
			drawPancake(delta);
			//drawEnemy(delta);
			drawGround();
		batch.end();

		drawCollision();
		drawDebug();

		Minion m = hell.getMinion();
		moveCamera(m.getPosition().x + 4f, CAMERA_HEIGHT / 2);

	}

	private void loadTextures() {
		loadMinionTexture();
		loadPancakeTexture();
		loadGroundTexture();

	}

	private void loadPancakeTexture() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/pancake_texture.txt"));
		pancakeFrame = atlas.findRegion("pancake_0");

		TextureRegion[] fireFrames = new TextureRegion[6];
		for (int i = 0; i < 6; i++) {
			fireFrames[i] = atlas.findRegion("pancake_" + i);
		}

		pancakeFire = new Animation(FIRE_FRAME_DURATION, fireFrames);
	}

	private void loadGroundTexture() {
		groundTexture = new Texture(Gdx.files.internal("images/ground.png"));
	}

	private void loadMinionTexture() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/minion.txt"));
		minionFrame = atlas.findRegion("gatinho_0");

		TextureRegion[] walkFrames = new TextureRegion[15];
		for (int i = 0; i < 15; i++) {
			walkFrames[i] = atlas.findRegion("gatinho_" + i);
		}

		running = new Animation(RUNNING_FRAME_DURATION, walkFrames);
	}

	private void drawGround() {
		for (Ground g : hell.getDrawableGround((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {

			float x = g.getBounds().x;
			float y = g.getBounds().y;
			float width = Ground.SIZE;
			float height = Ground.SIZE;

			batch.draw(groundTexture, x, y, width, height);

		}
	}

	private void drawMinion() {
		Minion minion = hell.getMinion();
		minionFrame = running.getKeyFrame(minion.getStateTime(), true);

		float x = minion.getPosition().x;
		float y = minion.getPosition().y;
		float width = Minion.SIZE;
		float height = Minion.SIZE;

		batch.draw(minionFrame, x, y, width, height);
	}

	private void drawPancake(float delta) {
		for (Pancake p : hell.getDrawablePancake((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {
			p.update(delta);
			pancakeFrame = pancakeFire.getKeyFrame(p.getStateTime(), true);

			float x = p.getBounds().x;
			float y = p.getBounds().y;
			float width = Pancake.SIZE;
			float height = Pancake.SIZE;

			batch.draw(pancakeFrame, x, y, width, height);

		}
	}

	private void drawDebug() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);
		
		// DRAW GROUND
		for (Ground g : hell.getDrawableGround((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {

			Rectangle rect = g.getBounds();
			debugRenderer.setColor(Color.RED);
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		}

		
		//DRAW MINION
		Minion m = hell.getMinion();

		Rectangle rect = m.getBounds();
		debugRenderer.setColor(Color.GREEN);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		//DRAW PANCAKE
		for (Pancake p : hell.getDrawablePancake((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {
			Rectangle r = p.getBounds();
			debugRenderer.setColor(Color.YELLOW);
			debugRenderer.rect(r.x, r.y, r.width, r.height);
		}
		
		//DRAW ENEMY
		for (Enemy e : hell.getDrawableEnemy((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {
			Rectangle r = e.getBounds();
			debugRenderer.setColor(Color.MAGENTA);
			debugRenderer.rect(r.x, r.y, r.width, r.height);
		}

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

		Minion minion = hell.getMinion();
		if (minion.getPosition().y > camera.position.y) {
			camera.position.y += 0.05;
		} else if (minion.getPosition().y < camera.position.y - 2.5f) {
			camera.position.y -= 0.05;
		}

		this.camera.position.set(x, camera.position.y, 0);

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
