package br.com.rads.model.enemy;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	public enum Type{
		SKULL,
		HANDS
	};
	
	public static final float SIZE = 1f;

	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();

	private float stateTime = 0;

	public Enemy(Vector2 position) {

		this.position = position;
		this.bounds.setX(position.x + (SIZE / 2));
		this.bounds.setY(position.y + (SIZE / 2));
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;

	}
	
	public void update(float delta){
		stateTime += delta;
	}

	/**
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
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
	 * @param bounds
	 *            the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	/**
	 * @return the stateTime
	 */
	public float getStateTime() {
		return stateTime;
	}

	/**
	 * @param stateTime
	 *            the stateTime to set
	 */
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

}
