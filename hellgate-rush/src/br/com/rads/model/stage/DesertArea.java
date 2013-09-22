/**
 * 
 */
package br.com.rads.model.stage;

import br.com.rads.model.Ground;
import br.com.rads.model.Pancake;
import br.com.rads.model.enemy.Enemy;
import br.com.rads.model.enemy.Enemy.Type;
import br.com.rads.model.enemy.Skull;

import com.badlogic.gdx.math.Vector2;

/**
 * @author rafael da silva melo
 * 
 */
public class DesertArea extends Area {

	/**
	 * @param width
	 * @param height
	 * @param ground
	 */
	public DesertArea(int width, int height) {
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

		// loadFirstTest();
		loadDesertGround();

	}

	/**
	 * Primeiro teste de conceito
	 */
	private void loadFirstTest() {
		for (int col = 0; col < this.width; col++) {

			if (col < 15 || col > 160) {
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
			} else if (col > 60 && col < 75) {
				Ground g = new Ground(new Vector2(col, 4));
				this.ground[col][4] = g;
			} else if (col > 75 && col < 90) {
				Ground g = new Ground(new Vector2(col, 5));
				this.ground[col][5] = g;
			} else if (col > 90 && col < 105) {
				Ground g = new Ground(new Vector2(col, 0));
				this.ground[col][0] = g;
			} else {
				Ground g = new Ground(new Vector2(col, 0));
				this.ground[col][0] = g;
			}

		}

		for (int col = 0; col < this.width; col++) {

			Pancake pan = null;
			float fCol = col;

			if (col < 15 || col > 60) {
				pan = new Pancake(new Vector2(fCol, 2));
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

	private void loadDesertGround() {

		for (int col = 0; col < this.width; col++) {

			int screen = col / 10;

			switch (screen) {
			case 0:
				addTile(col, 0);
				break;
			case 1:
				if (col == 10 || col == 19) {
					addTile(col, 1);
					addPancake(col, 2);
					addEnemy(Type.SKULL, col, 3);
				} else {
					addTile(col, 0);
				}

				if (col > 11 && col < 19) {
					addPancake(col, 1);
				}

				break;
			case 2:

				if (col == 20 || col == 29) {
					addTile(col, 1);
				} else {
					addTile(col, 0);
				}

				if (col < 21) {
					addPancake(col, 2);
					addEnemy(Type.SKULL, col, 3);
				} else if (col > 21 && col < 28) {
					addPancake(col, 1);
				}

				break;

			case 3:

				if (col <= 32) {
					addTile(col, 1);
					addPancake(col, 2);
				} else {
					addTile(col, 0);

					if (col > 33)
						addPancake(col, 1);
				}

				break;

			case 4:

				if (col >= 40 && col < 49) {
					addTile(col, 1);

					if (col > 40 && col < 46)
						addPancake(col, 2);
				}

				break;

			case 5:

				if (col >= 50 && col < 58) {
					addTile(col, 0);

					if (col > 50 && col < 58)
						addPancake(col, 1);
				}

				break;
			case 6:

				if (col >= 60 && col < 67) {
					addTile(col, 0);
				} else {
					addTile(col, 1);
				}

				if (col < 65) {
					addPancake(col, 1);
				} else if (col < 68) {
					addPancake(col, 2);
				}

				break;
			case 7:

				if (col >= 70 && col <= 79) {
					addTile(col, 2);
				}

				if (col < 74 || col > 76) {
					addPancake(col, 3);
				} else if (col == 74 || col == 76) {
					addPancake(col, 4);
				} else {
					addPancake(col, 5);
				}

				break;
			case 8:
				if (col >= 80 && col <= 86) {
					addTile(col, 0);

					if (col > 80 && col < 85)
						addPancake(col, 1);
					else if (col == 85)
						addPancake(col, 2);

				} else if (col > 86 && col < 89) {
					addTile(col, 1);
				} else {
					addTile(col, 2);
				}

				if (col == 86 || col == 89)
					addPancake(col, 3);

				break;
			case 9:
				if (col == 90) {
					addTile(col, 2);
					addPancake(col, 4);
				} else {
					addTile(col, 0);

					if (col == 91)
						addPancake(col, 5);
					else if (col > 92 && col < 99)
						addPancake(col, 1);
				}

				break;

			case 10:

				if (col <= 101) {
					addTile(col, 1);
				} else if (col > 101 && col <= 103) {
					addTile(col, 0);
					if (col == 103)
						addPancake(col, 2);
				} else {
					addTile(col, 0);
					addTile(col, 2);
					addPancake(col, 1);
				}
				break;
			case 11:

				if (col <= 111) {
					addTile(col, 0);
				} else if (col > 111 && col <= 113) {
					addTile(col, 1);
					addTile(col, 3);
				} else if (col > 113 && col <= 117) {
					addTile(col, 3);
					addTile(col, 0);
				} else {
					addTile(col, 0);
				}

				if (col > 112 && col < 117) {
					addPancake(col, 4);
				}

				if (col > 114)
					addPancake(col, 1);

				if (col >= 118)
					addPancake(col, 5);

				break;
			case 12:
				//parei aqui
				if (col == 120) {
					addTile(col, 0);
					addTile(col, 3);
				} else if (col <= 122) {
					addTile(col, 3);
				} else if (col <= 125) {
					addTile(col, 3);
					addTile(col, 0);
				} else {
					addTile(col, 4);
					addTile(col, 0);
				}

				break;
			case 13:
				if (col == 130) {
					addTile(col, 4);
				} else if (col >= 131 && col <= 134) {
					addTile(col, 4);
					addTile(col, 1);
				} else if (col >= 135 && col <= 138) {
					addTile(col, 4);
					addTile(col, 0);
				} else {
					addTile(col, 0);
					addTile(col, 1);
					addTile(col, 4);
					addTile(col, 5);
				}

				break;
			case 14:

				if (col >= 141) {
					addTile(col, 5);
					addTile(col, 0);
				}

				break;
			case 15:

				if (col <= 153) {
					addTile(col, 1);
				} else if (col <= 157) {
					addTile(col, 2);
				} else if (col <= 159) {
					addTile(col, 3);
				}

				break;
			case 16:

				if (col == 160) {
					addTile(col, 3);
				} else {
					addTile(col, 0);
				}

				break;

			case 17:

				if (col <= 172) {
					addTile(col, 1);
				} else if (col <= 175) {
					addTile(col, 0);
				} else if (col <= 178) {
					addTile(col, 1);
				} else {
					addTile(col, 0);
				}

				break;
			case 18:

				if (col <= 181) {
					addTile(col, 0);
				} else if (col <= 184) {
					addTile(col, 1);
				} else if (col <= 186) {
					addTile(col, 0);
				} else {
					addTile(col, 2);
					addTile(col, 0);
				}

				break;
			case 19:

				if (col <= 193) {
					addTile(col, 2);
				}
				addTile(col, 0);

				break;

			case 20:
				if (col <= 202) {
					addTile(col, 0);
				} else if (col <= 205) {
					addTile(col, 1);
				} else if (col <= 208) {
					addTile(col, 2);
				} else {
					addTile(col, 0);
				}
				break;
			case 21:

				if (col == 210) {
					addTile(col, 0);
				} else if (col <= 213) {
					addTile(col, 0);
					addTile(col, 3);
				} else if (col == 214) {
					addTile(col, 0);
				} else if (col > 214 && col <= 217) {
					addTile(col, 4);
				} else if (col > 217) {
					addTile(col, 0);
					addTile(col, 5);
				}

				break;
			case 22:
				addTile(col, 5);
				addTile(col, 0);

				if (col == 223 || col == 229) {
					addTile(col, 2);
				}

				break;
			case 23:
				addTile(col, 0);

				if (col >= 233 && col <= 235) {
					addTile(col, 0);
				} else if (col >= 238) {
					addTile(col, 5);
					addTile(col, 0);
				}

				if (col == 237) {
					addTile(col, 2);
				}

				break;
			case 24:

				if (col == 240) {
					addTile(col, 5);
					addTile(col, 0);
				} else if (col == 241) {
					addTile(col, 0);
				} else if (col < 249) {
					addTile(col, 1);
				} else {
					addTile(col, 2);
					addTile(col, 1);
				}

				break;
			case 25:

				if (col > 251) {
					addTile(col, 0);

					if (col <= 234) {
						addTile(col, 3);
					} else if (col > 235 && col <= 238) {
						addTile(col, 4);
						addTile(col, 1);
					}

				}

				break;
			case 26:

				if (col <= 262) {
					addTile(col, 0);
				} else if (col <= 267) {
					addTile(col, 1);
				}

				break;
			case 27:

				if (col <= 277) {
					addTile(col, 2);
				}

				break;
			case 28:
				if (col <= 287) {
					addTile(col, 3);
				}
				break;
			case 29:
				addTile(col, 4);
				break;

			case 30:
				addTile(col, 0);

				if (col == 302) {
					addTile(col, 5);
					addTile(col, 6);
				}

				break;
			case 31:

				if (col <= 312) {
					addTile(col, 1);
				} else if (col <= 315) {
					addTile(col, 2);
				} else if (col > 315) {
					addTile(col, 0);
				}

				if (col == 319) {
					addTile(col, 2);
				}

				break;
			case 32:

				if (col == 320 || col == 323 || col == 324 || col == 327
						|| col == 328) {
					addTile(col, 2);
				}
				addTile(col, 0);

				break;
			case 33:

				if (col <= 336) {
					addTile(col, 0);
				}

				if (col > 330 && col < 339) {
					addTile(col, 3);
				} else {
					addTile(col, 3);
					addTile(col, 4);
				}

				break;
			case 34:

				if (col <= 346) {
					addTile(col, 0);
				}

				if (col > 342) {
					addTile(col, 4);
				}

				break;
			case 35:

				if (col <= 351) {
					addTile(col, 0);
				} else if (col <= 353) {
					addTile(col, 3);
				} else if (col <= 355) {
					addTile(col, 0);
				} else if (col <= 357) {
					addTile(col, 3);
				}

				break;
			case 36:

				if (col <= 364) {
					addTile(col, 0);
				} else if (col <= 368) {
					addTile(col, 1);
				}

				break;
			case 37:

				if (col < 379) {
					addTile(col, 2);
				}

				break;
			case 38:

				if (col < 389) {
					addTile(col, 3);
				}

				break;
			case 39:

				if (col < 399) {
					addTile(col, 4);
				}

				break;

			case 40:
				addTile(col, 5);
				break;
			case 41:
				addTile(col, 0);
				break;
			case 42:
				if (col <= 422) {
					addTile(col, 0);
				} else if (col <= 426) {
					addTile(col, 1);
				} else if (col <= 428) {
					addTile(col, 0);
				} else {
					addTile(col, 0);
					addTile(col, 2);
				}
				break;
			case 43:

				if (col <= 431) {
					addTile(col, 0);
					addTile(col, 2);
				} else if (col <= 434) {
					addTile(col, 0);
				} else if (col <= 437) {
					addTile(col, 1);
				} else {
					addTile(col, 2);
				}

				break;
			case 44:

				if (col == 440) {
					addTile(col, 2);
				} else if (col <= 443) {
					addTile(col, 3);
				} else if (col <= 446) {
					addTile(col, 4);
				} else {
					addTile(col, 5);
				}

				break;
			case 45:

				if (col <= 452) {
					addTile(col, 4);
				} else if (col <= 455) {
					addTile(col, 3);
				} else if (col <= 458) {
					addTile(col, 2);
				} else {
					addTile(col, 1);
				}

				break;
			case 46:

				if (col <= 461) {
					addTile(col, 1);
				} else {
					addTile(col, 0);
				}

				break;
			case 47:

				if (col > 471 && col < 475) {
					addTile(col, 0);
				} else if (col > 476) {
					addTile(col, 0);
				}

				break;
			case 48:

				if (col > 481 && col < 485) {
					addTile(col, 0);
				} else if (col > 486) {
					addTile(col, 0);
				}

				break;
			case 49:
				if (col > 491 && col < 495) {
					addTile(col, 1);
				} else if (col > 496) {
					addTile(col, 1);
				}
				break;

			case 50:

				if (col > 501 && col < 505) {
					addTile(col, 0);
				} else if (col > 506) {
					addTile(col, 1);
				}

				break;
			case 51:

				if (col <= 512) {
					addTile(col, 2);
				} else if (col > 514 && col < 518) {
					addTile(col, 2);
				} else {
					addTile(col, 1);
				}

				break;
			case 52:

				addTile(col, 0);

				if (col == 529) {
					addTile(col, 1);
				}

				break;
			case 53:

				if (col == 533 || col == 537) {
					addTile(col, 1);
				}

				if (col >= 537) {
					addTile(col, 0);
				}

				break;
			case 54:

				addTile(col, 0);
				addTile(col, 1);

				if (col == 549) {
					addTile(col, 2);
				}

				break;
			case 55:

				addTile(col, 0);

				if (col > 552) {
					addTile(col, 2);
				}

				if (col == 559) {
					addTile(col, 3);
				}
				break;
			case 56:

				addTile(col, 0);

				if (col == 562) {
					addTile(col, 1);
					addTile(col, 4);
				} else if (col > 562 && col < 569) {
					addTile(col, 4);
				} else {
					addTile(col, 5);
				}

				break;
			case 57:

				if (col < 577) {
					addTile(col, 0);
				}

				if (col > 573) {
					addTile(col, 5);
				}

				break;
			case 58:
				addTile(col, 0);
				break;
			case 59:
				addTile(col, 0);
				break;
			}

		}
	}

	private void addEnemy(Type enemyType, int col, int atHeight) {
		
		Enemy e = null;
		Vector2 pos = new Vector2(col, atHeight);
		
		switch (Type.SKULL) {
		case SKULL:
			e = new Skull(pos);
			break;
		case HANDS:
			break;
		default:
			break;
		}
		
		this.enemy[col][atHeight] = e;
	}

	/**
	 * 
	 * Adiciona um ground em x e y
	 * 
	 * @param col
	 *            , atHeight
	 */
	private void addTile(int col, int atHeight) {
		Ground g = new Ground(new Vector2(col, atHeight));
		this.ground[col][atHeight] = g;
	}

	private void addPancake(int col, int atHeight) {
		Pancake p = new Pancake(new Vector2(col, atHeight));
		this.pancake[col][atHeight] = p;
	}

}
