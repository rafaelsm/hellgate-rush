/**
 * 
 */
package br.com.rads.view;

import br.com.rads.model.Ground;
import br.com.rads.model.Hell;
import br.com.rads.model.Minion;
import br.com.rads.model.Pancake;

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
import com.badlogic.gdx.utils.Logger;

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

	/**
	 * Textures/animation
	 */
	private TextureRegion minionFrame;
	private Animation running;
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

	public void render() {

		batch.begin();
			drawMinion();
//		 	drawGround();
		batch.end();

		drawCollision();
		drawDebug();

		Minion m = hell.getMinion();
		moveCamera(m.getPosition().x + 4f, CAMERA_HEIGHT / 2);

	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas("images/textures/minion.txt");
		minionFrame = atlas.findRegion("gatinho_0");

		TextureRegion[] walkFrames = new TextureRegion[15];
		for (int i = 0; i < 15; i++) {
			walkFrames[i] = atlas.findRegion("gatinho_" + i);
		}

		running = new Animation(RUNNING_FRAME_DURATION, walkFrames);
		
		groundTexture = new Texture(Gdx.files.internal("images/ground.png"));

	}
	
	private void drawGround(){
		for (Ground g :  hell.getDrawableGround((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {
			batch.draw(groundTexture, 
					g.getPosition().x, 
					g.getPosition().y * ppuY, 
					Ground.SIZE * ppuX, 
					Ground.SIZE * ppuY);
		}
	}

	private void drawMinion() {
		Minion minion = hell.getMinion();
		minionFrame = running.getKeyFrame(minion.getStateTime(), true);
			float x = 130;
			
			float y = minion.getPosition().y * 100f;
			if(minion.getPosition().y > camera.position.y){
				y = (camera.position.y - minion.getPosition().y) * 100f;
			}
			
			float width = Minion.SIZE * ppuX;
			float height = Minion.SIZE * ppuY;
			
			batch.draw(minionFrame, x, y, width, height);
			Gdx.app.log("spriteY", "sy="+y+"  miniony="+minion.getPosition().y + "  cameray=" + camera.position.y);
			
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

		for (Pancake p : hell.getDrawablePancake((int) CAMERA_WIDTH,
				(int) CAMERA_HEIGHT)) {
			Rectangle r = p.getBounds();
			debugRenderer.setColor(Color.YELLOW);
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
