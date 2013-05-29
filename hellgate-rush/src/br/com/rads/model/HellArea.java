/**
 * 
 */
package br.com.rads.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author rafael da silva melo
 *
 * Classe generica que define uma area/fase/mundo do jogo
 *
 */
public class HellArea {

	/**
	 * Array com todos espaços que compoem o chao
	 */
	private Array<Ground> ground = new Array<Ground>();
	
	/**
	 * Personagem
	 */
	private Minion minion;
	
	/**
	 * Inicia um mundo no jogo
	 */
	public HellArea() {
		createDemoArea();
	}

	/**
	 * 
	 */
	private void createDemoArea() {
		minion = new Minion(new Vector2(1,1), true);
		
		for (int i = 0; i < 30; i++) {
			Ground g = new Ground( new Vector2(i, 0));
			ground.add(g);
		}
		
	}

	/**
	 * @return the ground
	 */
	public Array<Ground> getGround() {
		return ground;
	}

	/**
	 * @return the minion
	 */
	public Minion getMinion() {
		return minion;
	}
	
	
}
