/**
 * 
 */
package br.com.rads.controller;

import java.util.HashMap;
import java.util.Map;

import br.com.rads.model.Ground;
import br.com.rads.model.HellArea;
import br.com.rads.model.Minion;
import br.com.rads.model.Minion.State;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author rafael da silva melo
 *
 */
public class HellController {

	//Comandos
	enum Keys{JUMP, THROW};
	private static Map<Keys, Boolean> keys = new HashMap<HellController.Keys, Boolean>();
	
	//Variaveis para controlar o pulo
	private static final long LONG_JUMP_PRESS = 150L;//tempo segurando pulo
	private static final float ACCELERATION = 20F;
	private static final float GRAVITY = -20F;
	private static final float MAX_JUMP_SPEED = 7F;
	
	private long jumpPressedTime;
	private boolean jumpingPressed;
	
	private HellArea hellArea;
	private Minion minion;
	private Array<Ground> grounds;
	
	private boolean infinityRun;
	
	public HellController(HellArea area, boolean infinityRun){
		this.hellArea = area;
		this.minion = area.getMinion();
		this.grounds = area.getGround();
		this.infinityRun = infinityRun;
		
		configKeys();
	}
	
	/**
	 * 
	 */
	private void configKeys() {
		keys.put(Keys.JUMP, false);
		keys.put(Keys.THROW, false);
	}

	public void update(float delta){
		
		//pega entradas
		processInput();
		
		minion.getAcceleration().y = GRAVITY;
		minion.getAcceleration().scl(delta);
		minion.getVelocity().add(minion.getAcceleration().x,minion.getAcceleration().y);
		
		minion.update(delta);
		
		if (minion.getPosition().y < 0 ) {
			
			minion.getPosition().y = 0;
			minion.setPosition(minion.getPosition());
			
			if (minion.getState().equals(State.JUMPING)) {
				
				minion.setState(State.RUNNING);
				
			}
			
		}
		
		//atualiza o chao
		updateGround(delta);
	}

	/**
	 * @param delta
	 */
	private void updateGround(float delta) {
		for (Ground g : this.grounds) {
			
			g.getVelocity().x = -Ground.SPEED;
			g.update(delta);
			
			if((g.getPosition().x < -g.getBounds().width) && infinityRun){
				g.setPosition(new Vector2(10, 0));
			}
			
		}
	}
	
	/**
	 * 
	 */
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {
			
			//se nao esta pulando
			if (!minion.getState().equals(State.JUMPING)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				minion.setState(State.JUMPING);
				minion.getVelocity().y = MAX_JUMP_SPEED;
			}
			//esta pulando
			else{
				//se exceder o tempo de pulo, para de pular
				boolean jumpExceedTime = (System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS ? true : false;
				if (jumpingPressed && jumpExceedTime) {
					jumpingPressed = false;
				}
				else
				{
					if (jumpingPressed) {
						minion.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}
		
		return false;
	}

	public void jumpPressed(){
		keys.get(keys.put(Keys.JUMP, true));
	}
	
	public void jumpReleased(){
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = false;
	}
	
	public void throwPressed(){
		keys.get(keys.put(Keys.THROW, true));
	}
	
	public void throwReleased(){
		keys.get(keys.put(Keys.THROW, false));
	}
	
}
