/**
 * 
 */
package br.com.rads.model;


/**
 * @author rafael da silva melo
 *
 */
public abstract class Area {
	
	protected int width;
	protected int height;
	protected Ground[][] ground;
	
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
	}

	public abstract void loadArea();

	public Ground get(int x, int y){
		return ground[x][y];
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Ground[][] getGround() {
		return ground;
	}

	public void setGround(Ground[][] ground) {
		this.ground = ground;
	}
	
}
