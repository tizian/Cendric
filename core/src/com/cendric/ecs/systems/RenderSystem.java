package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.ecs.Entity;

public abstract class RenderSystem {
	
	public void render(List<Entity> entities, SpriteBatch spriteBatch, float stateTime) {
//		for (Entity e : entities) {
//			render(e, spriteBatch, stateTime);
//		}
		for (int i = entities.size() - 1; i >= 0; i--) {
			render(entities.get(i), spriteBatch, stateTime);
		}
	}
	
	protected abstract void render(Entity entity, SpriteBatch spriteBatch, float stateTime);
}