package com.cendric.models;

import com.badlogic.gdx.math.Vector2;
import com.cendric.Resources;
import com.cendric.models.MainCharacter.ActiveSpell;

public class Spell extends GameObject {
	
	public ActiveSpell spell;
	
	public Spell(Vector2 origin, Vector2 direction, ActiveSpell spell) {
		super(origin);
		GRAVITY_ACCELERATION = 0.0f;
		velocity = direction;
		this.spell = spell;
		setCollisionBox(new Vector2(5, 5), 20, 20);
	}
	
	@Override
	public void update(float delta) {
		stateTime += delta;
		position.x += velocity.x * delta;
		position.y += velocity.y * delta;
		
		if (spell.equals(ActiveSpell.FIRE)) {
			textureRegion = Resources.spellFire.getKeyFrame(stateTime, true);
		} else if (spell.equals(ActiveSpell.ICE)) {
			textureRegion = Resources.spellIce.getKeyFrame(stateTime, true);
		}
	}

	
	
}
