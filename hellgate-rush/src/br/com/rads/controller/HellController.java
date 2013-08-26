/**
 * 
 */
package br.com.rads.controller;

import java.util.HashMap;
import java.util.Map;

import br.com.rads.model.Ground;
import br.com.rads.model.Hell;
import br.com.rads.model.Minion;
import br.com.rads.model.Minion.State;
import br.com.rads.model.Pancake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * @author rafael da silva melo
 * 
 */
public class HellController {

	// Comandos
	enum Keys {
		JUMP, THROW
	};

	private static Map<Keys, Boolean> keys = new HashMap<HellController.Keys, Boolean>();
	static{
		
	}

	// Variaveis para controlar o pulo
	private static final long LONG_JUMP_PRESS = 150L;// tempo segurando pulo
	private static final float ACCELERATION = 20F;
	private static final float GRAVITY = -20F;
	private static final float MAX_JUMP_SPEED = 7F;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
//	private static final float VELOCITY = 5f;
	private static final float VELOCITY = 5f;

	private Hell hellArea;
	private Minion minion;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private boolean grounded = false;
	
	private int i = 0;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	private Array<Ground> collidable = new Array<Ground>();
	private Array<Pancake> collidablePancake = new Array<Pancake>();

	public HellController(Hell area) {
		this.hellArea = area;
		this.minion = area.getMinion();
		configKeys();
	}

	private void configKeys() {
		keys.put(Keys.JUMP, false);
		keys.put(Keys.THROW, false);
	}

	public void update(float delta) {

		// pega entradas
		processInput();

		if (grounded && minion.getState().equals(State.JUMPING)) {
			minion.setState(State.RUNNING);
		}

		minion.getAcceleration().y = GRAVITY;
		minion.getAcceleration().scl(delta);
		minion.getVelocity().add(minion.getAcceleration().x,
				minion.getAcceleration().y);

		checkCollision(delta);

		minion.getVelocity().x *= DAMP;

		if (minion.getVelocity().x > MAX_VEL) {
			minion.getVelocity().x = MAX_VEL;
		}
		if (minion.getVelocity().x < -MAX_VEL) {
			minion.getVelocity().x = -MAX_VEL;
		}

		minion.getVelocity().x = VELOCITY;;
		minion.update(delta);
	}

	/**
	 * @param delta
	 */
	private void checkCollision(float delta) {

		minion.getVelocity().scl(delta);

		Rectangle minionRect = rectPool.obtain();
		minionRect.set(minion.getBounds().x, minion.getBounds().y,
				minion.getBounds().width, minion.getBounds().height);

		int startX, endX;
		int startY = (int) minion.getBounds().y;
		int endY = (int) (minion.getBounds().y + minion.getBounds().height);

		if (minion.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(minion.getBounds().x
					+ minion.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(minion.getBounds().x
					+ minion.getBounds().width + minion.getVelocity().x);
		}
		
		populateCollisionGround(startX, startY, endX, endY);
		populateCollisionPancake(startX, startY, endX, endY);

		minionRect.x += minion.getVelocity().x;

		hellArea.getCollisionRect().clear();

		// Verifica colisao!
		for (Ground ground : collidable) {

			if (ground == null)
				continue;

			if (minionRect.overlaps(ground.getBounds())) {
				minion.getVelocity().x = 0;
				hellArea.getCollisionRect().add(ground.getBounds());
				break;
			}
		}
		
		for (Pancake pancake : collidablePancake) {
			if (pancake == null)
				continue;
			
			if (minionRect.overlaps(pancake.getBounds())) {
				i++;
				Gdx.app.log("Colision", "pancake-"+i);
				hellArea.getCollisionRect().add(pancake.getBounds());
				pancake.setBounds(new Rectangle());
				break;
			}
		}

		minionRect.x = minion.getPosition().x;

		// o mesmo para colisao do outro eixo
		startX = (int) minion.getBounds().x;
		endX = (int) (minion.getBounds().x + minion.getBounds().width);

		if (minion.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(minion.getBounds().y
					+ minion.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(minion.getBounds().y
					+ minion.getBounds().height + minion.getVelocity().y);
		}
		
		populateCollisionGround(startX, startY, endX, endY);
		populateCollisionPancake(startX, startY, endX, endY);

		minionRect.y += minion.getVelocity().y;

		// Verifica colisao!
		for (Ground ground : collidable) {

			if (ground == null)
				continue;

			if (minionRect.overlaps(ground.getBounds())) {

				if (minion.getVelocity().y < 0) {
					grounded = true;
				}

				minion.getVelocity().y = 0;
				hellArea.getCollisionRect().add(ground.getBounds());
				break;

			}
		}
		
		for (Pancake pancake : collidablePancake) {
			if (pancake == null)
				continue;
			
			if (minionRect.overlaps(pancake.getBounds())) {
				i++;
				Gdx.app.log("Colision", "pancake-"+i);
				hellArea.getCollisionRect().add(pancake.getBounds());
				pancake.setBounds( new Rectangle());
				break;
			}
		}

		minionRect.y = minion.getPosition().y;

		minion.getPosition().add(minion.getVelocity());
		minion.getBounds().x = minion.getPosition().x;
		minion.getBounds().y = minion.getPosition().y;

		minion.getVelocity().scl(1 / delta);

	}

	private void populateCollisionPancake(int startX, int startY, int endX,
			int endY) {

		collidablePancake.clear();

		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < hellArea.getArea().getWidth() && y >= 0
						&& y < hellArea.getArea().getHeight()) {
					collidablePancake.add(hellArea.getArea().getPancakeAt(x, y)); 
				}
			}
		}
		
	}

	/**
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 */
	private void populateCollisionGround(int startX, int startY, int endX,
			int endY) {

		collidable.clear();

		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < hellArea.getArea().getWidth() && y >= 0
						&& y < hellArea.getArea().getHeight()) {
					collidable.add(hellArea.getArea().get(x, y)); 
				}
			}
		}

	}

	/**
	 * 
	 */
	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {

			// se nao esta pulando
			if (!minion.getState().equals(State.JUMPING)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();
				minion.setState(State.JUMPING);
				minion.getVelocity().y = MAX_JUMP_SPEED;
				grounded = false;
			}
			// esta pulando
			else {
				// se exceder o tempo de pulo, para de pular
				boolean jumpExceedTime = (System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS ? true
						: false;
				if (jumpingPressed && jumpExceedTime) {
					jumpingPressed = false;
				} else {
					if (jumpingPressed) {
						minion.getVelocity().y = MAX_JUMP_SPEED;
					}
				}
			}
		}

		return false;
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = false;
	}

	public void throwPressed() {
		keys.get(keys.put(Keys.THROW, true));
	}

	public void throwReleased() {
		keys.get(keys.put(Keys.THROW, false));
	}

}
