/**
 * 
 */
package br.com.rads.model;

import java.util.ArrayList;
import java.util.List;

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
		groundList = new ArrayList<Ground>();
		loadArea();
	}

	@Override
	public void loadArea() {

		for (int row = 0; row < this.width; row++) {
			Ground g = new Ground( new Vector2(row, 0));
			this.ground[row][0] = g;
			this.groundList.add(g);
		}
		
	}

}
