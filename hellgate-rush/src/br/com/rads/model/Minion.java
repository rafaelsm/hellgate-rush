/**
 * 
 */
package br.com.rads.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author rafael da silva melo
 *
 * Personagem que irá correr pelo cenário
 *
 */
public class Minion {

	public enum State {
		RUNNING, 
		JUMPING, 
		DYING
	};
	
	private static final float JUMP_VELOCITY = 1f;
	private static final float SIZE = 1f;
	
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	private State state = State.RUNNING;
	private boolean isFirstMinion = false; //pode haver mais de um minion na tela, todos sao controlados ao mesmo tempo

	/**
	 * Construtor
	 */
	public Minion(Vector2 position, boolean firstMinion) {
		this.position = position;
		this.isFirstMinion = firstMinion;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
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
