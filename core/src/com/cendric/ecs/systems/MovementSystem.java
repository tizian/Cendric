package com.cendric.ecs.systems;

import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;

public class MovementSystem extends System {

	@Override
	public void update(Entity entity, float dt) {
		PositionComponent p = (PositionComponent) entity.components.get(ComponentType.Position);
		MovementComponent m = (MovementComponent) entity.components.get(ComponentType.Movement);
		if (p == null) return;
		if (m == null) return;
		
		p.x = p.x + m.vx * dt;
		p.y = p.y + m.vy * dt;
	}
}