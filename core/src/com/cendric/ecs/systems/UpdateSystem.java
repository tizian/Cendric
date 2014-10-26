package com.cendric.ecs.systems;

import java.util.List;

import com.cendric.ecs.Entity;

public abstract class UpdateSystem {
	public void update(List<Entity> entities, float dt) {
		for (Entity e : entities) {
			update(e, dt);
		}
	}
	
	protected abstract void update(Entity entity, float dt);
}