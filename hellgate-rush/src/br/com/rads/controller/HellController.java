/**
 * 
 */
package br.com.rads.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.Position;

import br.com.rads.model.Ground;
import br.com.rads.model.Hell;
import br.com.rads.model.Minion;
import br.com.rads.model.Minion.State;
import br.com.rads.model.Pancake;
import br.com.rads.model.enemy.Enemy;
import br.com.rads.screens.Screen;
import br.com.rads.screens.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	private static final float VELOCITY = 5f;

	private Hell hellArea;
	private Minion minion;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private boolean grounded = false;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	private Array<Ground> collidable = new Array<Ground>();
	private Array<Pancake> collidablePancake = new Array<Pancake>();
	private Array<Enemy> collidableEnemy = new Array<Enemy>();

	private Resources resources;

	public HellController(Hell area) {
		this.hellArea = area;
		this.minion = area.getFirstMinion();
		configKeys();
		resources = Resources.getInstance();
	}

	private void configKeys() {
		keys.put(Keys.JUMP, false);
		keys.put(Keys.THROW, false);
	}

	public void update(float delta) {

		if(minion.getPosition().x < 20f && minion.getPosition().y < 0){
			minion.setPosition( new Vector2(minion.getPosition().x, 8f));
			for (int i = 1; i < hellArea.getMinions().size(); i++) {
				Minion m = hellArea.getMinions().get(i);
				m.setPosition( new Vector2(m.getPosition().x, 8f));
			}
		}
		
		// pega entradas
		processInput();

		if (grounded && minion.getState().equals(State.JUMPING)) {
			minion.setState(State.RUNNING);
			updateAllMinionsState(State.RUNNING);
		}

		minion.getAcceleration().y = GRAVITY;

		minion.getAcceleration().scl(delta);
		minion.getVelocity().add(minion.getAcceleration().x,
				minion.getAcceleration().y);

		// bugzao
		final float deltaTemp = delta;
//		Timer.schedule(new Task() {
//			@Override
//			public void run() {
				updateAllMinionsGravity(deltaTemp);
//			}
//		}, 0);

		checkCollisionForAllMinions(delta);

		minion.getVelocity().x *= DAMP;

		if (minion.getVelocity().x > MAX_VEL) {
			minion.getVelocity().x = MAX_VEL;
		}
		if (minion.getVelocity().x < -MAX_VEL) {
			minion.getVelocity().x = -MAX_VEL;
		}

		minion.getVelocity().x = VELOCITY;
		minion.update(delta);

		updateAllMinions(delta);

		// perdeu...
		if (minion.getPosition().y < -20 || minion.getLife() <= 0) {
			ScreenManager.getInstance().show(Screen.GAME_OVER);
			resources.stopMusic();
			resources.stopSounds();

		}
		
		if (minion.getPosition().x > 580 && minion.getLife() > 0) {
			ScreenManager.getInstance().show(Screen.WIN);
			resources.stopMusic();
			resources.stopSounds();
		}

	}


	private void updateAllMinionsState(State state) {
		List<Minion> minions = hellArea.getMinions();

		for (int i = 1; i < minions.size(); i++) {
			Minion m = minions.get(i);
			m.setState(state);
		}
	}

	private void updateAllMinionsGravity(float delta) {
		List<Minion> minions = hellArea.getMinions();

		for (int i = 1; i < minions.size(); i++) {
			Minion m = minions.get(i);
			m.getAcceleration().y = GRAVITY;
			m.getAcceleration().scl(delta);
			m.getVelocity().add(m.getAcceleration().x, m.getAcceleration().y);
		}
	}

	private void updateAllMinions(float delta) {
		List<Minion> minions = hellArea.getMinions();

		for (int i = 1; i < minions.size(); i++) {
			Minion m = minions.get(i);
			m.getVelocity().x = VELOCITY;
			m.update(delta);
		}
	}

	private void pauseAllMinions(boolean xAxis) {
		List<Minion> minions = hellArea.getMinions();

		for (int i = 1; i < minions.size(); i++) {
			Minion m = minions.get(i);

			if (xAxis)
				m.getVelocity().x = 0;
			else
				m.getVelocity().y = 0;
		}
	}

	private void updateAllMinionsVelocity(float delta) {
		List<Minion> minions = hellArea.getMinions();

		for (int i = 1; i < minions.size(); i++) {
			Minion m = minions.get(i);
			m.getVelocity().scl(delta);
		}
	}

	private void updateAllMinionsPosition(float delta) {

		List<Minion> minions = hellArea.getMinions();

		for (int i = 1; i < minions.size(); i++) {

			Minion m = minions.get(i);
			m.getPosition().add(m.getVelocity());
			m.getBounds().x = m.getPosition().x;
			m.getBounds().y = m.getPosition().y;
			m.getVelocity().scl(1 / delta);
		}
	}

	/**
	 * @param delta
	 */
	private void checkCollision(float delta) {

		minion.getVelocity().scl(delta);
		updateAllMinionsVelocity(delta);

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
		populateCollisionEnemy(startX, startY, endX, endY);

		minionRect.x += minion.getVelocity().x;

		hellArea.getCollisionRect().clear();

		// Verifica colisao!
		for (Ground ground : collidable) {

			if (ground == null)
				continue;

			if (minionRect.overlaps(ground.getBounds())) {
				
				minion.getVelocity().x = 0;
				pauseAllMinions(true);
				hellArea.getCollisionRect().add(ground.getBounds());
				break;
			}
		}

		for (Pancake pancake : collidablePancake) {
			if (pancake == null)
				continue;

			if (minionRect.overlaps(pancake.getBounds())) {
				hellArea.getCollisionRect().add(pancake.getBounds());
				pancake.setBounds(new Rectangle());
				minion.addPancake();
				break;
			}
		}

		for (Enemy enemy : collidableEnemy) {
			if (enemy == null)
				continue;

			if (minionRect.overlaps(enemy.getBounds()) && !enemy.isDidDamage()) {
				hellArea.getCollisionRect().add(enemy.getBounds());
				enemy.setDidDamage(true);
				minion.loseLife();
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
		populateCollisionEnemy(startX, startY, endX, endY);

		minionRect.y += minion.getVelocity().y;

		// Verifica colisao!
		for (Minion m : hellArea.getMinions()) {

			float oldY = minionRect.y;
			minionRect.y += m.getVelocity().y;

			for (Ground ground : collidable) {

				if (ground == null)
					continue;

				if (minionRect.overlaps(ground.getBounds())) {

					if (m.isFirstMinion() && minion.getVelocity().y < 0) {
						grounded = true;
					}

					m.getVelocity().y = 0;
					pauseAllMinions(false);
					hellArea.getCollisionRect().add(ground.getBounds());
					break;

				}
			}

			minionRect.y = oldY;
		}

		for (Pancake pancake : collidablePancake) {
			if (pancake == null)
				continue;

			if (minionRect.overlaps(pancake.getBounds())) {
				hellArea.getCollisionRect().add(pancake.getBounds());
				pancake.setBounds(new Rectangle());
				minion.addPancake();
				break;
			}
		}

		for (Enemy enemy : collidableEnemy) {
			if (enemy == null)
				continue;

			if (minionRect.overlaps(enemy.getBounds()) && !enemy.isDidDamage()) {
				hellArea.getCollisionRect().add(enemy.getBounds());
				enemy.setDidDamage(true);
				minion.loseLife();
				break;
			}
		}

		minionRect.y = minion.getPosition().y;

		minion.getPosition().add(minion.getVelocity());
		minion.getBounds().x = minion.getPosition().x;
		minion.getBounds().y = minion.getPosition().y;

		minion.getVelocity().scl(1 / delta);

		updateAllMinionsPosition(delta);

	}

	private void checkCollisionForAllMinions(float delta) {

		List<Minion> minions = hellArea.getMinions();

		for (Minion m : minions) {

			m.getVelocity().scl(delta);

			Rectangle minionRect = rectPool.obtain();
			minionRect.set(m.getBounds().x, m.getBounds().y,
					m.getBounds().width, m.getBounds().height);

			int startX, endX;
			int startY = (int) m.getBounds().y;
			int endY = (int) (m.getBounds().y + m.getBounds().height);

			if (m.getVelocity().x < 0) {
				startX = endX = (int) Math.floor(m.getBounds().x
						+ m.getVelocity().x);
			} else {
				startX = endX = (int) Math.floor(m.getBounds().x
						+ m.getBounds().width + m.getVelocity().x);
			}

			populateCollisionGround(startX, startY, endX, endY);
			if (m.isFirstMinion()) {
				populateCollisionPancake(startX, startY, endX, endY);
				populateCollisionEnemy(startX, startY, endX, endY);
			}

			minionRect.x += m.getVelocity().x;
			hellArea.getCollisionRect().clear();

			if (m.isFirstMinion()) {
				for (Ground ground : collidable) {

					if (ground == null)
						continue;

					if (minionRect.overlaps(ground.getBounds())) {
						Gdx.app.log("Colision", "" + ground.getPosition().x);
						m.getVelocity().x = 0;
						pauseAllMinions(true);
						hellArea.getCollisionRect().add(ground.getBounds());
						break;
					}
				}

				for (Pancake pancake : collidablePancake) {
					if (pancake == null)
						continue;

					if (minionRect.overlaps(pancake.getBounds())) {
						hellArea.getCollisionRect().add(pancake.getBounds());
						pancake.setCatched(true);
						m.addPancake();
						resources.playSound();
						break;
					}
				}

				for (Enemy enemy : collidableEnemy) {
					if (enemy == null)
						continue;

					if (minionRect.overlaps(enemy.getBounds())
							&& !enemy.isDidDamage()) {
						hellArea.getCollisionRect().add(enemy.getBounds());
						enemy.setDidDamage(true);
						m.loseLife();
						break;
					}
				}
			}

			minionRect.x = m.getPosition().x;

			// o mesmo para colisao do outro eixo
			startX = (int) m.getBounds().x;
			endX = (int) (m.getBounds().x + m.getBounds().width);

			if (m.getVelocity().y < 0) {
				startY = endY = (int) Math.floor(m.getBounds().y
						+ m.getVelocity().y);
			} else {
				startY = endY = (int) Math.floor(m.getBounds().y
						+ m.getBounds().height + m.getVelocity().y);
			}

			populateCollisionGround(startX, startY, endX, endY);
			if (m.isFirstMinion()) {
				populateCollisionPancake(startX, startY, endX, endY);
				populateCollisionEnemy(startX, startY, endX, endY);
			}

			minionRect.y += m.getVelocity().y;

			// Verifica colisao!

			for (Ground ground : collidable) {

				if (ground == null)
					continue;

				if (minionRect.overlaps(ground.getBounds())) {

					if (m.isFirstMinion() && m.getVelocity().y < 0) {
						grounded = true;
					}

					m.getVelocity().y = 0;
					hellArea.getCollisionRect().add(ground.getBounds());
					break;

				}
			}

			if (m.isFirstMinion()) {
				for (Pancake pancake : collidablePancake) {
					if (pancake == null)
						continue;

					if (minionRect.overlaps(pancake.getBounds())) {
						hellArea.getCollisionRect().add(pancake.getBounds());
						pancake.setCatched(true);
						m.addPancake();
						resources.playSound();
						break;
					}
				}

				for (Enemy enemy : collidableEnemy) {
					if (enemy == null)
						continue;

					if (minionRect.overlaps(enemy.getBounds())
							&& !enemy.isDidDamage()) {
						hellArea.getCollisionRect().add(enemy.getBounds());
						enemy.setDidDamage(true);
						m.loseLife();
						break;
					}
				}
			}

			minionRect.y = m.getPosition().y;

			m.getPosition().add(m.getVelocity());
			m.getBounds().x = m.getPosition().x;
			m.getBounds().y = m.getPosition().y;

			m.getVelocity().scl(1 / delta);
		}
	}

	private void populateCollisionEnemy(int startX, int startY, int endX,
			int endY) {

		collidableEnemy.clear();

		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < hellArea.getArea().getWidth() && y >= 0
						&& y < hellArea.getArea().getHeight()) {
					collidableEnemy.add(hellArea.getArea().getEnemyAt(x, y));
				}
			}
		}

	}

	private void populateCollisionPancake(int startX, int startY, int endX,
			int endY) {

		collidablePancake.clear();

		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < hellArea.getArea().getWidth() && y >= 0
						&& y < hellArea.getArea().getHeight()) {
					collidablePancake
							.add(hellArea.getArea().getPancakeAt(x, y));
				}
			}
		}

	}

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

	private boolean processInput() {
		if (keys.get(Keys.JUMP)) {

			// se nao esta pulando
			if (!minion.getState().equals(State.JUMPING)) {
				jumpingPressed = true;
				jumpPressedTime = System.currentTimeMillis();

				float i = 0.025f;
				for (Minion m : hellArea.getMinions()) {

					if (m.isFirstMinion()) {
						m.setState(State.JUMPING);
						m.getVelocity().y = MAX_JUMP_SPEED;
					} else {
						jump(m, i, State.JUMPING);
						i += 0.025f;
					}
				}
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

						float i = 0.025f;
						for (Minion m : hellArea.getMinions()) {

							if (m.isFirstMinion()) {
								m.getVelocity().y = MAX_JUMP_SPEED;
							} else {
								jump(m, i, null);
								i += 0.025f;
							}

						}
					}
				}
			}
		}

		return false;
	}

	public void jump(final Minion m, float delay, final State state) {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				if (state != null){
					m.setState(state);
				}
				m.getVelocity().y = MAX_JUMP_SPEED;
			}
		}, delay);
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
