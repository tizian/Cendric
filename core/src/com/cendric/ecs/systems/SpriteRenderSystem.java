package com.cendric.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.TextureComponent;

public class SpriteRenderSystem extends RenderSystem {

	@Override
	public void render(Entity entity, SpriteBatch spriteBatch) {
		PositionComponent p = (PositionComponent) entity.components.get("Position");
		TextureComponent t = (TextureComponent) entity.components.get("Texture");
		if (p == null) return;
		if (t == null) return;
		
		spriteBatch.draw(t.texture, p.x, p.y);
	}

	@Override
	public void update(Entity entity, float dt) {
	}
}
