package com.cendric.ecs.systems;

import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MassComponent;
import com.cendric.ecs.components.MovementComponent;

public class AccelerationSystem extends UpdateSystem {

	@Override
	protected void update(Entity entity, float dt) {
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		MassComponent m = (MassComponent) entity.getComponent(ComponentType.Mass);
		if (mov == null) return;
		
		float dirx = Math.signum(mov.vxTarget - mov.vx);
		float diry = Math.signum(mov.vyTarget - mov.vy);
		
		mov.vx = mov.vx + dirx * mov.ACCELERATION * dt;
		
		if (m != null) {
			mov.vy = mov.vy + diry * mov.GRAVITY * m.mass * dt;
		}
		
		if (Math.signum(mov.vxTarget - mov.vx) != dirx) {
			mov.vx = mov.vxTarget;
		}
		if (Math.signum(mov.vyTarget - mov.vy) != diry) {
			mov.vy = mov.vyTarget;
		}
	}
}