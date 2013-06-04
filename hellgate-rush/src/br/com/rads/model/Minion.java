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
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	
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
	
	public void update(float delta)
	{
		position.add(velocity.cpy().scl(delta));
	}
	
	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the acceleration
	 */
	public Vector2 getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	
}
