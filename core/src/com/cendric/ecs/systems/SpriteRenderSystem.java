package com.cendric.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.TextureComponent;

public class SpriteRenderSystem extends RenderSystem {

	@Override
	protected void render(Entity entity, SpriteBatch spriteBatch, float stateTime) {
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		if (pos == null) return;
		if (tex == null) return;
		
		if (tex.texture != null) {
			spriteBatch.draw(tex.texture, pos.x, pos.y);
		}
		if (tex.textureOverlay != null) {
			spriteBatch.draw(tex.textureOverlay, pos.x, pos.y);
		}
	}
}
