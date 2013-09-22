package br.com.rads.model.enemy;

import com.badlogic.gdx.math.Vector2;

public class Skull extends Enemy {

	public Skull(Vector2 position) {
		super(position);
		this.getBounds().width = 0.5f;
	}

}
