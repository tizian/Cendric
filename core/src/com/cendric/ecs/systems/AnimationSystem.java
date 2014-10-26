package com.cendric.ecs.systems;

import com.cendric.Resources;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.TextureComponent;
import com.cendric.ecs.components.AnimationStateComponent.State;

public class AnimationSystem extends UpdateSystem {

	@Override
	protected void update(Entity entity, float dt) {
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		AnimationStateComponent as = (AnimationStateComponent) entity.getComponent(ComponentType.AnimationState);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		if (mov == null) return;
		if (as == null) return;
		if (tex == null) return;
		
		if (mov.vx < -20) {
			as.state = State.WALKING;
			as.facingLeft = true;
		}
		else if (mov.vx > 20) {
			as.state = State.WALKING;
			as.facingLeft = false;
		}
		else {
			as.state = State.IDLE;
		}
		
		if (mov.vy != 0) as.state = State.JUMPING;
		
		as.animationTime += dt;
		
		if (as.state.equals(State.IDLE)) {
			tex.texture = as.facingLeft ? Resources.cendricIdleLeftFrame : Resources.cendricIdleRightFrame;
		}
		else if (as.state.equals(State.WALKING)) {
			tex.texture = as.facingLeft ? Resources.cendricWalkingLeft.getKeyFrame(as.animationTime, true) : Resources.cendricWalkingRight.getKeyFrame(as.animationTime, true);
		} else if (as.state.equals(State.JUMPING)) {
			tex.texture = as.facingLeft ? Resources.cendricJumpingLeftFrame : Resources.cendricJumpingRightFrame;
		}
		
//		if (spell.equals(SpellId.FIRE)) {
//			staffEffect = isFacingLeft ? Resources.staffFireLeft.getKeyFrame(stateTime, true) : Resources.staffFireRight.getKeyFrame(stateTime, true);
//		} else if (spell.equals(SpellId.ICE)) {
//			staffEffect = isFacingLeft ? Resources.staffIceLeft.getKeyFrame(stateTime, true) : Resources.staffIceRight.getKeyFrame(stateTime, true);
//		}
	}

}
