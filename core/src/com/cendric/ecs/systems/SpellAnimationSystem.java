package com.cendric.ecs.systems;

import com.cendric.Resources;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.ecs.components.TextureComponent;

public class SpellAnimationSystem extends UpdateSystem {

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Spell")) return;
		AnimationStateComponent as = (AnimationStateComponent) entity.getComponent(ComponentType.AnimationState);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		SpellStateComponent sp = (SpellStateComponent) entity.getComponent(ComponentType.SpellState);
		if (as == null) return;
		if (tex == null) return;
		if (sp == null) return;
		
		as.animationTime += dt;
		
		switch (sp.spellType) {
		case FIRE:
			tex.texture = Resources.spellFire.getKeyFrame(as.animationTime, true);
			break;
			
		case ICE:
			tex.texture = Resources.spellIce.getKeyFrame(as.animationTime, true);

		default:
			break;
		}
	}

}
