package com.cendric.ecs.systems;

import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;

public class MovementSystem extends UpdateSystem {

	@Override
	protected void update(Entity entity, float dt) {
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		if (pos == null) return;
		if (mov == null) return;
		
		pos.x = pos.x + mov.vx * dt;
		pos.y = pos.y + mov.vy * dt;
		
		
		
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		if (bb == null) return;
		
		bb.boundingBox.x = bb.boundingBox.x + mov.vx * dt;
		bb.boundingBox.y = bb.boundingBox.y + mov.vy * dt;
	}
}