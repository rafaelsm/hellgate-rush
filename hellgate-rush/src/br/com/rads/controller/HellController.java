/**
 * 
 */
package br.com.rads.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.rads.model.Ground;
import br.com.rads.model.HellArea;
import br.com.rads.model.Minion;
import br.com.rads.model.Minion.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
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

	// Variaveis para controlar o pulo
	private static final long LONG_JUMP_PRESS = 150L;// tempo segurando pulo
	private static final float ACCELERATION = 20F;
	private static final float GRAVITY = -20F;
	private static final float MAX_JUMP_SPEED = 7F;

	private long jumpPressedTime;
	private boolean jumpingPressed;
	private boolean grounded;

	private HellArea hellArea;
	private Minion minion;
	private Array<Ground> grounds;

	private boolean infinityRun;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {

		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}

	};

	private Array<Ground> collidable = new Array<Ground>();

	public HellController(HellArea area, boolean infinityRun) {
		this.hellArea = area;
		this.minion = area.getMinion();
		configKeys();
	}

	/**
	 * 
	 */
	private void configKeys() {
		keys.put(Keys.JUMP, false);
		keys.put(Keys.THROW, false);
	}

	public void update(float delta) {

		// pega entradas
		processInput();

		minion.getAcceleration().y = GRAVITY;
		minion.getAcceleration().scl(delta);
		minion.getVelocity().add(minion.getAcceleration().x,
				minion.getAcceleration().y);

		checkCollision(delta);

		if (minion.getPosition().y < 0.5) {

			minion.getPosition().y = 0.5f;
			minion.setPosition(minion.getPosition());

			if (minion.getState().equals(State.JUMPING)) {

				minion.setState(State.RUNNING);

			}

		}
		
		minion.getVelocity().x = 1f;
		minion.update(delta);


		// atualiza o chao
		//updateGround(delta);
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

		startX = endX = (int) Math.floor(minion.getBounds().x
				+ minion.getBounds().width + minion.getVelocity().x);

		populateCollisionGround(startX, startY, endX, endY);

		minionRect.x += minion.getVelocity().x;

		hellArea.getCollisionRec().clear();

		// Verifica colisao!
		for (Ground ground : collidable) {

			if (ground == null)
				continue;

			if (minionRect.overlaps(ground.getBounds())) {
				// fazer esquema de perder minions
				//Gdx.app.log("COLLISION", "horizontal");
				hellArea.getCollisionRec().add(ground.getBounds());
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

		minionRect.y += minion.getVelocity().y;

		// Verifica colisao!
		for (Ground ground : collidable) {

			if (ground == null)
				continue;

			if (minionRect.overlaps(ground.getBounds())) {

				if (minion.getVelocity().y < 0) {
					grounded = true;
				}
				// fazer esquema de perder minions
				Gdx.app.log("COLLISION", "vertical");

				minion.getVelocity().y = 0;
				hellArea.getCollisionRec().add(ground.getBounds());
				break;

			}
		}

		minionRect.y = minion.getPosition().y;
		//Gdx.app.log("Y", "" + minionRect.y);
		
		minion.getPosition().add(minion.getVelocity());
		minion.getBounds().x = minion.getPosition().x;
		minion.getBounds().y = minion.getPosition().y;
		
		minion.getVelocity().scl(1/delta);
		
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
				if (x > 0 && x < hellArea.getArea().getWidth() && y >= 0
						&& y < hellArea.getArea().getHeight()) {
					collidable.add(hellArea.getArea().get(x, y));
				}
			}
		}

	}

	/**
	 * @param delta
	 */
	private void updateGround(float delta) {
		for (Ground g : hellArea.getArea().getGroundList()) {

			g.getVelocity().x = -Ground.SPEED;
			g.update(delta);

			if ((g.getPosition().x < -g.getBounds().width) && infinityRun) {
				g.setPosition(new Vector2(10 * g.SCALE, 0));// da merda...os
															// blocos se
															// sobrepoem
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
