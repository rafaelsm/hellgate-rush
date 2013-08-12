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

			if (col < 15 || col > 60) {
				Ground g = new Ground(new Vector2(col, 0));
				this.ground[col][0] = g;
			} else if (col > 15 && col < 30) {
				Ground g = new Ground(new Vector2(col, 1));
				this.ground[col][1] = g;
			} else if (col > 30 && col < 45) {
				Ground g = new Ground(new Vector2(col, 2));
				this.ground[col][2] = g;
			} else if (col > 45 && col < 60) {
				Ground g = new Ground(new Vector2(col, 3));
				this.ground[col][3] = g;
			}

		}
		
		for (int col = 0; col < this.width; col++) {

			Pancake pan = null;
			float fCol = col;
			
			if (col < 15 || col > 60) {
				pan = new Pancake( new Vector2(fCol, 2));
				this.pancake[col][1] = pan;
			} else if (col > 15 && col < 30) {
				pan = new Pancake(new Vector2(fCol, 3));
				this.pancake[col][2] = pan;
			} else if (col > 30 && col < 45) {
				pan = new Pancake(new Vector2(fCol, 4));
				this.pancake[col][3] = pan;
			} else if (col > 45 && col < 60) {
				pan = new Pancake(new Vector2(fCol, 5));
				this.pancake[col][4] = pan;
			}

		}
	}

}
