package com.cendric.ecs.systems;

import com.cendric.Resources;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.ecs.components.SpellStateComponent.SpellType;
import com.cendric.ecs.components.TextureComponent;
import com.cendric.ecs.components.AnimationStateComponent.State;

public class CendricAnimationSystem extends UpdateSystem {

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Cendric")) return;
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		AnimationStateComponent as = (AnimationStateComponent) entity.getComponent(ComponentType.AnimationState);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		SpellStateComponent sp = (SpellStateComponent) entity.getComponent(ComponentType.SpellState);
		if (mov == null) return;
		if (as == null) return;
		if (tex == null) return;
		if (sp == null) return;
		
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
		
		if (sp.spellType == SpellType.FIRE) {
			tex.textureOverlay = as.facingLeft ? Resources.staffFireLeft.getKeyFrame(as.animationTime, true) : Resources.staffFireRight.getKeyFrame(as.animationTime, true);
		} else if (sp.spellType == SpellType.ICE) {
			tex.textureOverlay = as.facingLeft ? Resources.staffIceLeft.getKeyFrame(as.animationTime, true) : Resources.staffIceRight.getKeyFrame(as.animationTime, true);
		}
	}

}
