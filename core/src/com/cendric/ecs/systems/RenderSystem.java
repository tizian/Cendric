package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.ecs.Entity;

public abstract class RenderSystem {
	
	public void render(List<Entity> entities, SpriteBatch spriteBatch) {
		for (Entity e : entities) {
			render(e, spriteBatch);
		}
	}
	
	protected abstract void render(Entity entity, SpriteBatch spriteBatch);
}