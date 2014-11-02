package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Resources;
import com.cendric.controllers.Level;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.LeverComponent;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.ecs.components.SpellStateComponent.SpellType;
import com.cendric.ecs.components.TextureComponent;

public class LeverSystem extends UpdateSystem {
	
	private Level level;
	
	public LeverSystem(Level level) {
		this.level = level;
	}

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Lever")) return;
		LeverComponent lc = (LeverComponent) entity.getComponent(ComponentType.Lever);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		if (lc == null) return;
		
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		if (bb == null) return;
		
		Rectangle leverBB = bb.boundingBox;
		
		List<Entity> entities = level.getEntities();
		for (Entity e : entities) {
			if (!e.tag.equals("Spell")) continue;
			
			MovementComponent mov = (MovementComponent) e.getComponent(ComponentType.Movement);
			SpellStateComponent sp = (SpellStateComponent) e.getComponent(ComponentType.SpellState);
			BoundingBoxComponent bbc = (BoundingBoxComponent) e.getComponent(ComponentType.BoundingBox);
			if (mov == null) continue;
			if (sp == null || sp.spellType != SpellType.MONEY) continue;
			if (bbc == null || !bbc.active) continue;
			
			Rectangle spellBB = new Rectangle(bbc.boundingBox);
			spellBB.x = spellBB.x + mov.vx * dt;
			spellBB.y = spellBB.y + mov.vy * dt;
			
			if (!spellBB.overlaps(leverBB)) continue;
			System.out.println("Hit Lever");
			lc.switchActive();
			tex.texture = Resources.getLeverTexture(lc.active);
			affectCells(lc.affectedCells, lc.active);
			level.removeEntity(e);
		}
	}
	
	private void affectCells(List<Vector2> cells, boolean activate) {
		
	}

}
