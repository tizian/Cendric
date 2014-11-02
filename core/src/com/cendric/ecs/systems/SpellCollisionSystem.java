package com.cendric.ecs.systems;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.controllers.Level;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.SpellStateComponent;

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
		
		nextRect.x = bb.boundingBox.x;
		nextRect.y = bb.boundingBox.y;
		
		List<Rectangle> collidables = new ArrayList<Rectangle>();
		collidables.addAll(level.getCollidableTiles());
		
		List<Entity> entities = level.getEntities();
		for (Entity e : entities) {
			if (e.equals(entity)) continue;
			if (e.tag.equals("Spell")) continue;	// TODO better handling for what collides with what
			if (e.tag.equals("Cendric")) continue;
			if (e.tag.equals("Cursor")) continue;
			BoundingBoxComponent bbc = (BoundingBoxComponent) e.getComponent(ComponentType.BoundingBox);
			if (bbc != null && bbc.active) {
				collidables.add(bbc.boundingBox);
			}
		}
		
		// Check x direction
		nextRect.x = nextRect.x + mov.vx * dt;

		for (Rectangle rect : collidables) {
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

		for (Rectangle rect : collidables) {
			if (nextRect.overlaps(rect)) {
				mov.vy = -mov.vy;
				sp.numberReflections--;
				if (sp.numberReflections < 0) {
					level.removeEntity(entity);
				}
				break;
			}
		}
		
		// TODO foreach Entity e: if e has bounding box, do the same
		
		if (!bb.boundingBox.overlaps(level.getLevelRect())) {
			level.removeEntity(entity);
		}
	}
}
