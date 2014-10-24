package com.cendric.ecs.systems;

import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;

public class AccelerationSystem extends System {

	@Override
	public void update(Entity entity, float dt) {
		MovementComponent m = (MovementComponent) entity.components.get(ComponentType.Movement);
		if (m == null) return;
		
		float dirx = Math.signum(m.vxTarget - m.vx);
		float diry = Math.signum(m.vyTarget - m.vy);
		
		m.vx = m.vx + dirx * m.ACCELERATION * dt;
		m.vy = m.vy + diry * m.GRAVITY * dt;
		
		if (Math.signum(m.vxTarget - m.vx) != dirx) {
			m.vx = m.vxTarget;
		}
		if (Math.signum(m.vyTarget - m.vy) != diry) {
			m.vy = m.vyTarget;
		}
	}
}