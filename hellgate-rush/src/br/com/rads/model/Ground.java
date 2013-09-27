/**
 * 
 */
package br.com.rads.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author rafael da silva melo
 * 
 *         Caminho por onde o personagem correrá
 * 
 */
public class Ground {

	public static final float SIZE = 1f;
	public static final float SCALE = 1f;//change here for width, adjustments...
	
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();

	public Ground(Vector2 position) {
		this.position = position;
		
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
}
