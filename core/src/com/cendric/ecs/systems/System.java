package com.cendric.ecs.systems;

import java.util.List;

import com.cendric.ecs.Entity;

public abstract class System {
	public abstract void update(List<Entity> entities, float dt);
}