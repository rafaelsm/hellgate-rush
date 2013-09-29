/**
 * 
 */
package br.com.rads.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author rafael da silva melo
 *
 * Personagem que ir� correr pelo cen�rio
 *
 */
public class Minion {

	public enum State {
		RUNNING, 
		JUMPING,
		DYING
	};
	
	private static final String TAG = "Minion";
	public static final float SIZE = 0.75f;
	
	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	private Rectangle bounds = new Rectangle();
	
	private float stateTime = 0;
	private boolean	longJump = false;
	private State state = State.RUNNING;
	private int life = 7;
	private int pancakes = 0;
	
	/**
	 * Construtor
	 */
	public Minion(Vector2 position, boolean firstMinion) {
		this.position = position;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		Gdx.app.log(TAG, "life: " + this.life);
	}
	
	public void update(float delta)
	{
//		position.add(velocity.cpy().scl(delta));
//		this.bounds.x = position.x;
//		this.bounds.y = position.y;
		stateTime += delta;
		
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
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
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

	/**
	 * @return the stateTime
	 */
	public float getStateTime() {
		return stateTime;
	}

	/**
	 * @param stateTime the stateTime to set
	 */
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	/**
	 * @return the longJump
	 */
	public boolean isLongJump() {
		return longJump;
	}

	/**
	 * @param longJump the longJump to set
	 */
	public void setLongJump(boolean longJump) {
		this.longJump = longJump;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void loseLife(){
		this.setLife(this.getLife() - 1);
		Gdx.app.log(TAG, "life: " + this.life);
	}
	
	public void addLife(){
		this.setLife(this.getLife() + 1);
		Gdx.app.log(TAG, "life: " + this.life);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public void addPancake(){
		this.pancakes++;
	}
	
	public int getPancakes(){
		return this.pancakes;
	}
}
