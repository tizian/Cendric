package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.models.Level;

public class CendricCollisionSystem extends UpdateSystem {
	
	private Level level;
	
	public CendricCollisionSystem(Level level) {
		this.level = level;
	}

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Cendric")) return;
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		if (pos == null) return;
		if (mov == null) return;
		if (bb == null) return;
		
		mov.grounded = false;
		
		List<Rectangle> collidableTiles = level.getCollidableTiles();

		Rectangle nextRect = new Rectangle(bb.boundingBox);

		// Check x direction
		nextRect.x = nextRect.x + mov.vx * dt;

		if (!nextRect.overlaps(level.getLevelRect())) {
			mov.vx = 0;
		}

		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				if (mov.vx > 0) {
					bb.boundingBox.x = rect.x - bb.boundingBox.width;
					pos.x = bb.boundingBox.x - 5;
				}
				else if (mov.vy < 0) {
					bb.boundingBox.x = rect.x + rect.width;
					pos.x = bb.boundingBox.x + 5;
				}
				mov.vx = 0;
				break;
			}
		}
		
		nextRect.x = bb.boundingBox.x;

		// Check y direction
		nextRect.y = nextRect.y + mov.vy * dt;

		if (!nextRect.overlaps(level.getLevelRect())) {
//			mov.vy = 0;
//			mov.grounded = true;
		}

		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				if (mov.vy < 0) {
					mov.grounded = true;
					bb.boundingBox.y = rect.y + rect.height;
					pos.y = bb.boundingBox.y;
				}
				else if (mov.vy > 0) {
					bb.boundingBox.y = rect.y - bb.boundingBox.height;
					pos.y = bb.boundingBox.y;
				}
				mov.vy = 0;
				break;
			}
		}
		
		// TODO in future, check all entities with collision component as well
	}
}
