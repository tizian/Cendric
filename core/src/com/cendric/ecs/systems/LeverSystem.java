package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
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
		if (!entity.tag.equals("Lever"))
			return;
		LeverComponent lc = (LeverComponent) entity
				.getComponent(ComponentType.Lever);
		TextureComponent tex = (TextureComponent) entity
				.getComponent(ComponentType.Texture);
		if (lc == null)
			return;

		BoundingBoxComponent bb = (BoundingBoxComponent) entity
				.getComponent(ComponentType.BoundingBox);
		if (bb == null)
			return;

		Rectangle leverBB = bb.boundingBox;

		List<Entity> entities = level.getEntities();
		for (Entity e : entities) {
			if (!e.tag.equals("Spell"))
				continue;

			MovementComponent mov = (MovementComponent) e
					.getComponent(ComponentType.Movement);
			SpellStateComponent sp = (SpellStateComponent) e
					.getComponent(ComponentType.SpellState);
			BoundingBoxComponent bbc = (BoundingBoxComponent) e
					.getComponent(ComponentType.BoundingBox);
			if (mov == null)
				continue;
			if (sp == null || sp.spellType != SpellType.MONEY)
				continue;
			if (bbc == null || !bbc.active)
				continue;

			Rectangle spellBB = new Rectangle(bbc.boundingBox);
			spellBB.x = spellBB.x + mov.vx * dt;
			spellBB.y = spellBB.y + mov.vy * dt;

			if (!spellBB.overlaps(leverBB))
				continue;
			lc.switchActive();
			tex.texture = Resources.getLeverTexture(lc.active);

			// get entities for that
			affectCells(lc.affectedBoxes);
			level.removeEntity(e);
		}
	}

	private void affectCells(List<Entity> boxes) {
		if (boxes.isEmpty())
			return;

		for (Entity b : boxes) {
			TextureComponent lt = (TextureComponent) b
					.getComponent(ComponentType.Texture);
			BoundingBoxComponent bb = (BoundingBoxComponent) b
					.getComponent(ComponentType.BoundingBox);

			if (lt.texture == null) {
				lt.texture = level.getTiledMap().getTileSets()
						.getTile(Constants.TILE_LEVER).getTextureRegion();
			} else {
				lt.texture = null;
			}
			
			bb.active = (lt.texture != null);
		}
	}
}
