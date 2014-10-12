package com.hackzurich.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.hackzurich.Resources;


public class MainCharacter extends GameObject {
	
	public State state;
	public ActiveSpell spell;
	
	private TextureRegion staffEffect;

	public static enum ActiveSpell {
		FIRE, ICE;
	}
	
	public static enum State {
		IDLE, WALKING, JUMPING
	}
	
	public TextureRegion getStaffEffect() {
		return staffEffect;
	}
	
	public MainCharacter(Vector2 startPos) {
		super(startPos);
		this.state = State.IDLE;
		this.spell = ActiveSpell.FIRE;
	}
	
	@Override
	public void update(float delta) {
		stateTime += delta;
		if (state.equals(State.IDLE)) {
			textureRegion = isFacingLeft ? Resources.cendricIdleLeftFrame : Resources.cendricIdleRightFrame;
		}
		else if (state.equals(State.WALKING)) {
			textureRegion = isFacingLeft ? Resources.cendricWalkingLeft.getKeyFrame(stateTime, true) : Resources.cendricWalkingRight.getKeyFrame(stateTime, true);
		} else if (state.equals(State.JUMPING)) {
			textureRegion = isFacingLeft ? Resources.cendricJumpingLeftFrame : Resources.cendricJumpingRightFrame;
		}
		
		if (spell.equals(ActiveSpell.FIRE)) {
			staffEffect = isFacingLeft ? Resources.staffFireLeft.getKeyFrame(stateTime, true) : Resources.staffFireRight.getKeyFrame(stateTime, true);
		} else if (spell.equals(ActiveSpell.ICE)) {
			staffEffect = isFacingLeft ? Resources.staffIceLeft.getKeyFrame(stateTime, true) : Resources.staffIceRight.getKeyFrame(stateTime, true);
		}
		
		
		

		velocity = nextVelocity(delta);
		if (Math.abs(velocity.x) < 20) state = State.IDLE;
		if (!grounded && Math.abs(velocity.y) > 10) state = State.JUMPING;
		position = nextPosition(velocity, delta);
		
		willCollide = false;

		// Default value
		acceleration.x = 0;
	}
	
	@Override
	public void moveLeft() {
		super.moveLeft();
		if (grounded)
		state = State.WALKING;
	}

	@Override
	public void moveRight() {
		super.moveRight();
		if (grounded)
		state = State.WALKING;
	}
}
