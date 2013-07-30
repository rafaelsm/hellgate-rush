/**
 * 
 */
package br.com.rads.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author rafael da silva melo
 * 
 *         Classe generica que define uma area/fase/mundo do jogo
 * 
 */
public class Hell {

	private Minion minion;
	private Area area;
	
	private ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>();

	public Hell(Area area) {
		createDemoArea(area);
	}

	private void createDemoArea(Area area) {
		minion = new Minion(new Vector2(1, (float)4), true);
		this.area = area;
	}

	// collision
	public List<Ground> getDrawableGround(int width, int height) {

		//pega posicao do minion
		int x = (int) minion.getPosition().x - width;
		int y = (int) minion.getPosition().y - height;

		if (x < 0)
			x = 0;

		if (y < 0)
			y = 0;

		int x2 = x + 2 * width;
		int y2 = y + 2 * height;

		if (x2 > area.getWidth())
			x2 = area.getWidth() - 1;

		if (y2 > area.getHeight())
			y2 = area.getHeight() - 1;

		List<Ground> grounds = new ArrayList<Ground>();
		Ground ground;

		for (int col = x; col <= x2; col++) {
			for (int row = y; row <= y2; row++) {
				ground = area.getGround()[col][row];
				if (ground != null) {
					grounds.add(ground);
				}
			}
		}

		return grounds;
	}

	// Getters
	public Minion getMinion() {
		return minion;
	}

	public Area getArea() {
		return area;
	}

	public ArrayList<Rectangle> getCollisionRect() {
		return collisionRects;
	}

}
