package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.models.DynamicTile;
import com.cendric.models.Level;

public class SpellCollisionSystem extends UpdateSystem {
	
	private Level level;
	
	public SpellCollisionSystem(Level level) {
		this.level = level;
	}

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Spell")) return;
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		SpellStateComponent sp = (SpellStateComponent) entity.getComponent(ComponentType.SpellState);
		if (bb == null) return;
		if (sp == null) return;
		
		List<DynamicTile> tiles = level.getDynamicTiles();
		for (DynamicTile tile : tiles) {
			if (bb.boundingBox.overlaps(tile.getRect())) {
				tile.hit(sp.spellType);
			}
		}
		
		List<Rectangle> collidableTiles = level.getCollidableTiles();
		for (Rectangle rect : collidableTiles) {
			if (bb.boundingBox.overlaps(rect)) {
				level.removeEntity(entity);
			}
		}
		
		if (!bb.boundingBox.overlaps(level.getLevelRect())) {
			level.removeEntity(entity);
		}
	}

}
