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
import br.com.rads.model.HellArea;
import br.com.rads.model.Minion;

/**
 * @author rafael da silva melo
 *
 * Classe que renderiza a tela
 *
 */
public class HellRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	
	private HellArea hellArea;
	private Minion minion;
	private OrthographicCamera camera;
	
	private ShapeRenderer debugRenderer = new ShapeRenderer(); //debug apenas
	
	private SpriteBatch batch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;
	
	public HellRenderer(HellArea hellArea, boolean debug){
		this.hellArea = hellArea;
		this.minion = hellArea.getMinion();
		this.camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT); //inicia camera para pegar a tela toda em 10/7
		this.camera.position.set(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f, 0); //posiciona a camera no meio da tela
		this.camera.update();
		this.debug = debug;
		batch = new SpriteBatch();
		
	}
	
	public void render(){
		//quando tiver texturas
//		batch.begin();
//			drawGround();
//			drawMinion();
//		batch.end();
		
		Minion m = hellArea.getMinion();
		moveCamera(m.getPosition().x, CAMERA_HEIGHT / 2);
		
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);
		
		//renderiza chao
		for ( Ground g : hellArea.getDrawableGround((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
			
			Rectangle rect = g.getBounds();
			float x1 = g.getPosition().x + rect.x;
			float y1 = g.getPosition().y + rect.y;
			
			debugRenderer.setColor(Color.RED);
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			
		}
		
		//renderiza minion
		Rectangle rect = m.getBounds();
		float x1 = m.getPosition().x + rect.x;
		float y1 = m.getPosition().y + rect.y;
		debugRenderer.setColor(Color.GREEN);
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		
		debugRenderer.end();
		
		//renderiza colisao
		//drawCollision();
		
	}
	
	public void moveCamera(float x,float y){
		camera.position.x = hellArea.getMinion().getPosition().x + hellArea.getMinion().getBounds().x + 4f;
	    camera.update();
	}

	public void setSize(int width, int height){
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
