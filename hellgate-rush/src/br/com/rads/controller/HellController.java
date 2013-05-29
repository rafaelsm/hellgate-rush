/**
 * 
 */
package br.com.rads.controller;

import com.badlogic.gdx.utils.Array;

import br.com.rads.model.Ground;
import br.com.rads.model.HellArea;
import br.com.rads.model.Minion;

/**
 * @author rafael da silva melo
 *
 */
public class HellController {

	private HellArea hellArea;
	private Minion minion;
	private Array<Ground> grounds;
	
	public HellController(HellArea area){
		this.hellArea = area;
		this.minion = area.getMinion();
		this.grounds = area.getGround();
	}
	
	public void update(float delta){
		
		for (Ground g : this.grounds) {
			
			g.getVelocity().x = -Ground.SPEED;
			g.update(delta);
			
		}
	}
	
}
