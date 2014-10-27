package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
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
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		SpellStateComponent sp = (SpellStateComponent) entity.getComponent(ComponentType.SpellState);
		if (mov == null) return;
		if (bb == null) return;
		if (sp == null) return;

		Rectangle nextRect = new Rectangle(bb.boundingBox);
		
		nextRect.x = nextRect.x + mov.vx * dt;
		nextRect.y = nextRect.y + mov.vy * dt;
		
		List<DynamicTile> tiles = level.getDynamicTiles();
		
		for (DynamicTile tile : tiles) {
			if (nextRect.overlaps(tile.getRect())) {
				tile.hit(sp.spellType);
			}
		}
		
		nextRect.x = bb.boundingBox.x;
		nextRect.y = bb.boundingBox.y;
		
		List<Rectangle> collidableTiles = level.getCollidableTiles();

		// Check x direction
		nextRect.x = nextRect.x + mov.vx * dt;

		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				mov.vx = -mov.vx;
				sp.numberReflections--;
				if (sp.numberReflections < 0) {
					level.removeEntity(entity);
				}
				break;
			}
		}
		
		nextRect.x = bb.boundingBox.x;

		// Check y direction
		nextRect.y = nextRect.y + mov.vy * dt;

		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				mov.vy = -mov.vy;
				sp.numberReflections--;
				if (sp.numberReflections < 0) {
					level.removeEntity(entity);
				}
				break;
			}
		}
		
		if (!bb.boundingBox.overlaps(level.getLevelRect())) {
			level.removeEntity(entity);
		}
	}
}
