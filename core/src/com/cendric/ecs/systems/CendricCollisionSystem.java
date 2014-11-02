package com.cendric.ecs.systems;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.controllers.Level;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;

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
		
		List<Rectangle> collidables = new ArrayList<Rectangle>();
				
		collidables.addAll(level.getCollidableTiles());
		
		List<Entity> entities = level.getEntities();
		for (Entity e : entities) {
			if (e.equals(entity)) continue;
			if (e.tag.equals("Spell")) continue;	// TODO better handling for what collides with what
			if (e.tag.equals("Gargoyle")) continue;
			BoundingBoxComponent bbc = (BoundingBoxComponent) e.getComponent(ComponentType.BoundingBox);
			if (bbc != null && bbc.active) {
				collidables.add(bbc.boundingBox);
			}
		}
		
		for (Rectangle rect : collidables) {
			if (bb.boundingBox.overlaps(rect)) {
				mov.vx = 0;
				mov.vy = 0;
			}
		}

		Rectangle nextRect = new Rectangle(bb.boundingBox);

		// Check x direction
		nextRect.x = nextRect.x + mov.vx * dt;
		
		if (!level.getLevelRect().contains(nextRect)) {		//  && nextRect.y <= level.getLevelRect().y + level.getLevelRect().height && nextRect.y >= level.getLevelRect().y
			if (nextRect.x + nextRect.width > level.getLevelRect().x + level.getLevelRect().width) {
				bb.boundingBox.x = level.getLevelRect().x + level.getLevelRect().width - bb.boundingBox.width;
				pos.x = bb.boundingBox.x - 5;	// TODO hardcoded offset to BB (also in other places)
				mov.vx = 0;
			}
			else if (nextRect.x < level.getLevelRect().x) {
				bb.boundingBox.x = level.getLevelRect().x;
				pos.x = bb.boundingBox.x - 5;
				mov.vx = 0;
			}
		}

		for (Rectangle rect : collidables) {
			if (nextRect.overlaps(rect)) {
				if (mov.vx > 0) {
					bb.boundingBox.x = rect.x - bb.boundingBox.width;
					pos.x = bb.boundingBox.x - 5;
				}
				else if (mov.vx < 0) {
					bb.boundingBox.x = rect.x + rect.width;
					pos.x = bb.boundingBox.x - 5;
				}
				mov.vx = 0;
				break;
			}
		}
		
		nextRect.x = bb.boundingBox.x;

		// Check y direction
		nextRect.y = nextRect.y + mov.vy * dt;
		
		if (!level.getLevelRect().contains(nextRect)) {
			if (nextRect.y + nextRect.height > level.getLevelRect().y + level.getLevelRect().height) {
				bb.boundingBox.y = level.getLevelRect().y + level.getLevelRect().height - bb.boundingBox.height;
				pos.y = bb.boundingBox.y;
				mov.vy = 0;
			}
		}

		for (Rectangle rect : collidables) {
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
	}
}
