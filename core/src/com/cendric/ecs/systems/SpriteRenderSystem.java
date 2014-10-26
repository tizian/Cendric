package com.cendric.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.TextureComponent;

public class SpriteRenderSystem extends RenderSystem {

	@Override
	protected void render(Entity entity, SpriteBatch spriteBatch) {
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		if (pos == null) return;
		if (tex == null) return;
		
		spriteBatch.draw(tex.texture, (int)pos.x, (int)pos.y);
	}
}
