/**
 * 
 */
package br.com.rads.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author rafael da silva melo
 *
 */
public class Pancake {

	public static final float SIZE = 0.75f;
	
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	private float stateTime = 0;
	private boolean catched;

	/**
	 * @return the catched
	 */
	public boolean isCatched() {
		return catched;
	}

	/**
	 * @param catched the catched to set
	 */
	public void setCatched(boolean catched) {
		this.catched = catched;
	}

	public Pancake(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x + (SIZE/2));
		this.bounds.setY(position.y + (SIZE/2));
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void update(float delta){
		stateTime += delta;
	}
	
	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
}
