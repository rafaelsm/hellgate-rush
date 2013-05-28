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

	private static final float SIZE = 1f;
	private Vector2 position;

	private Rectangle bounds;

	/**
	 * Construtor
	 */
	public Ground(Vector2 position) {
		this.position = new Vector2(position.x, position.y);
		
		this.bounds = new Rectangle();
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		
	}

	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
}
