package com.cendric.ecs.systems;

import java.util.List;

import com.cendric.ecs.Entity;

public abstract class System {
	public void update(List<Entity> entities, float dt) {
		for (Entity e : entities) {
			update(e, dt);
		}
	}
	
	public abstract void update(Entity entity, float dt);
}