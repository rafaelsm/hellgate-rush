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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
	private Texture skullTexture;

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

	private ParallaxBackground parallax;
	private Texture pancakeHudTexture;
	
	//font
	private BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public HellRenderer(Hell hellArea, boolean debug) {
		this.hell = hellArea;
		this.camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.camera.zoom = 0.7f;
		this.camera.position.x = this.camera.position.x - 5;
		this.camera.position.y = this.camera.position.y - 1;
		this.camera.update();
		this.debug = debug;
		batch = new SpriteBatch();
		loadTextures();

	}

	public void render(float delta) {
		parallax.render(delta);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawGround();
		drawPancake(delta);
		drawEnemy(delta);
		drawMinion();
		drawPancakeCounter();
		batch.end();

		if (debug) {
			drawCollision();
			drawDebug();
		}

		Minion m = hell.getMinion();
		moveCamera(m.getPosition().x + 3f, CAMERA_HEIGHT / 2);

	}

	private void loadTextures() {
		loadMinionTexture();
		loadPancakeTexture();
		loadPancakeCounterTexture();
		loadGroundTexture();
		loadSkullTexture();
		loadFont();

		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/parallax.txt"));
		parallax = new ParallaxBackground(new ParallaxLayer[] {
				new ParallaxLayer(atlas.findRegion("layer_1"), new Vector2(),
						new Vector2(0, 0)),
				new ParallaxLayer(atlas.findRegion("layer_2"), new Vector2(
						1.0f, 1.0f), new Vector2(0, 500)), }, 1280, 720,
				new Vector2(100, 0));
	}

	private void loadFont() {
//		font = TrueTypeFontFactory.createBitmapFont(
//				Gdx.files.internal("data/caty.ttf"), 
//				FONT_CHARACTERS, 
//				10f, 7f, 1.2f, 
//				Gdx.graphics.getWidth(), 
//				Gdx.graphics.getHeight());
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/caty.ttf"));
		font = generator.generateFont(130, FONT_CHARACTERS, false);
		generator.dispose();
		
		font.setColor(Color.BLACK);
		font.setScale(2 / (float)Gdx.graphics.getWidth(), 2 / (float)Gdx.graphics.getHeight());
	}

	private void loadPancakeCounterTexture() {
		pancakeHudTexture = new Texture(
				Gdx.files.internal("images/textures/pancake_hud.png"));
	}

	private void loadSkullTexture() {
		skullTexture = new Texture(
				Gdx.files.internal("images/textures/skull_sprite.png"));
	}

	private void loadPancakeTexture() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/pancake_texture.txt"));
		pancakeFrame = atlas.findRegion("pancake_0");

		TextureRegion[] fireFrames = new TextureRegion[12];
		for (int i = 0; i < 12; i++) {
			if(i < 6)
				fireFrames[i] = atlas.findRegion("pancake_" + i);
			else
				fireFrames[i] = atlas.findRegion("pancake_" + (11-i));
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

	private void drawEnemy(float delta) {
		for (Enemy e : hell.getDrawableEnemy((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {

			float x = e.getBounds().x;
			float y = e.getBounds().y;
			float width = e.getBounds().width;
			float height = e.getBounds().height;

			batch.draw(skullTexture, x, y, width, height);

		}
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
		
		float offset = 0.5f;
		for (int i = 1; i < minion.getLife(); i++) {
			batch.draw(minionFrame, x - offset, y, width, height);
			offset += 0.1f;
		}
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
	
	private void drawPancakeCounter(){
		
		float hudX = camera.position.x - 0.5f;
		float hudY = camera.position.y + 1.85f;
		float hudWidth = 0.5f;
		float hudHeight = 0.5f;
		
		batch.draw(pancakeHudTexture, hudX, hudY, hudWidth, hudHeight);
		font.draw(batch, String.valueOf(hell.getMinion().getPancakes()), hudX + 0.75f, hudY + 0.175f);
		
//		Minion m = hell.getMinion();
//		
//		float y =camera.position.y -2.25f;
//		float x = camera.position.x + 2.25f;
//		
//		for (int i = 0; i < m.getPancakes(); i++) {
//			float width = 1;
//			float height = 1f;
//
//			batch.draw(pancakeHudTexture, x, y, width, height);
//			y+=0.025f;
//		}
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

		// DRAW MINION
		Minion m = hell.getMinion();

		Rectangle rect = m.getBounds();
		debugRenderer.setColor(Color.GREEN);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

		// DRAW PANCAKE
		for (Pancake p : hell.getDrawablePancake((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {
			Rectangle r = p.getBounds();
			debugRenderer.setColor(Color.YELLOW);
			debugRenderer.rect(r.x, r.y, r.width, r.height);
		}

		// DRAW ENEMY
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
		} else if (minion.getPosition().y < camera.position.y - 1.5f) {
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
