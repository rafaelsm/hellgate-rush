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
	public static final float SPEED = 4f;
	
	private Vector2 position;
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();

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
	
	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * @return the acceleration
	 */
	public Vector2 getAcceleration() {
		return acceleration;
	}
	
	public void update(float delta){
		position.add(velocity.cpy().scl(delta));
	}
	
}
