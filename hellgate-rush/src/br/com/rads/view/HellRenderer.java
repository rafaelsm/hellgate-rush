/**
 * 
 */
package br.com.rads.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	private OrthographicCamera camera;
	
	private ShapeRenderer debugRenderer = new ShapeRenderer(); //debug apenas
	
	//tamanhos de tela
	private int height;
	private int width;
	private float ppuX;
	private float ppuY;
	
	public HellRenderer(HellArea hellArea){
		this.hellArea = hellArea;
		this.camera = new OrthographicCamera(10, 7); //inicia camera para pegar a tela toda em 10/7
		this.camera.position.set(5f, 3.5f, 0); //oposiciona a camera no meio da tela
		this.camera.update();
	}
	
	public void render(){
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
		Minion m = hellArea.getMinion();
		Rectangle rect = m.getBounds();
		float x1 = m.getPosition().x + rect.x;
		float y1 = m.getPosition().y + rect.y;
		debugRenderer.setColor(Color.GREEN);
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		
		debugRenderer.end();
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		this.ppuX = (float) width / CAMERA_WIDTH;
		this.ppuY = (float) height / CAMERA_HEIGHT;
	}
	
}
