/**
 * 
 */
package br.com.rads.model;

import com.badlogic.gdx.math.Vector2;

/**
 * @author rafael da silva melo
 * 
 */
public class FirstArea extends Area {

	/**
	 * @param width
	 * @param height
	 * @param ground
	 */
	public FirstArea(int width, int height) {
		super(width, height);
		loadArea();
	}

	@Override
	public void loadArea() {
		
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				this.ground[col][row] = null;
			}
		}

		for (int col = 0; col < this.width; col++) {
			if (col % 7 == 1) {
				Ground g = new Ground(new Vector2(col, 1));
				this.ground[col][1] = g;
			}

			Ground g = new Ground(new Vector2(col, 0));
			this.ground[col][0] = g;

		}
	}

}
