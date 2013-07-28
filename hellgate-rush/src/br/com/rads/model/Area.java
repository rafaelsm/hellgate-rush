/**
 * 
 */
package br.com.rads.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rafael da silva melo
 *
 */
public abstract class Area {
	
	protected int width;
	protected int height;
	protected Ground[][] ground;
	protected List<Ground> groundList;
	
	/**
	 * @param width
	 * @param height
	 * @param ground
	 */
	public Area(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.ground = new Ground[width][height];
		this.groundList = new ArrayList<Ground>();
	}

	public Ground get(int x, int y){
		return ground[x][y];
	}

	public abstract void loadArea();
	

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the ground
	 */
	public Ground[][] getGround() {
		return ground;
	}

	/**
	 * @param ground the ground to set
	 */
	public void setGround(Ground[][] ground) {
		this.ground = ground;
	}
	
	public List<Ground> getGroundList(){
		return this.groundList;
	}
}
