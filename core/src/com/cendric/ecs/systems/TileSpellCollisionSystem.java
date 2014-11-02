package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
import com.cendric.controllers.Level;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.DynamicTileComponent;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.ecs.components.TextureComponent;
import com.cendric.ecs.components.SpellStateComponent.SpellType;

public class TileSpellCollisionSystem extends UpdateSystem {
	
	private Level level;
	
	public TileSpellCollisionSystem(Level level) {
		this.level = level;
	}

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Tile")) return;
		DynamicTileComponent dtc = (DynamicTileComponent) entity.getComponent(ComponentType.DynamicTile);
		TextureComponent tex = (TextureComponent) entity.getComponent(ComponentType.Texture);
		if (dtc == null) return;
		
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		if (bb == null) return;
		
		Rectangle tileBB = bb.boundingBox;
		
		List<Entity> entities = level.getEntities();
		for (Entity e : entities) {
			if (!e.tag.equals("Spell")) continue;
			
			MovementComponent mov = (MovementComponent) e.getComponent(ComponentType.Movement);
			SpellStateComponent sp = (SpellStateComponent) e.getComponent(ComponentType.SpellState);
			BoundingBoxComponent bbc = (BoundingBoxComponent) e.getComponent(ComponentType.BoundingBox);
			if (mov == null) continue;
			if (sp == null) continue;
			if (bbc == null || !bbc.active) continue;
			
			Rectangle spellBB = new Rectangle(bbc.boundingBox);
			spellBB.x = spellBB.x + mov.vx * dt;
			spellBB.y = spellBB.y + mov.vy * dt;
			
			if (!spellBB.overlaps(tileBB)) continue;
			System.out.println("Hit");
			switch (dtc.tileID) {
			
			case Constants.TILE_ICE_1_ID:
				if (sp.spellType == SpellType.FIRE) {
					tex.texture = level.getTiledMap().getTileSets().getTile(Constants.TILE_ICE_2_ID).getTextureRegion();
					dtc.tileID = Constants.TILE_ICE_2_ID;
					
				}
				break;
				
			case Constants.TILE_ICE_2_ID:
				if (sp.spellType == SpellType.FIRE) {
					tex.texture = null;
					bb.active = false;
					dtc.tileID = -1;		// no better idea atm
				}
				break;
				
			case Constants.TILE_FROZEN_WATER_ID:
				if (sp.spellType == SpellType.FIRE) {
					bb.active = false;
					tex.texture = level.getTiledMap().getTileSets().getTile(Constants.TILE_WATER_ID).getTextureRegion();
					dtc.tileID = Constants.TILE_WATER_ID;
					level.removeEntity(e);
				}
				break;
				
			case Constants.TILE_WATER_ID:
				System.out.println("Hit WATER");
				if (sp.spellType == SpellType.ICE) {
					bb.active = true;
					tex.texture = level.getTiledMap().getTileSets().getTile(Constants.TILE_FROZEN_WATER_ID).getTextureRegion();
					dtc.tileID = Constants.TILE_FROZEN_WATER_ID;
				}

			default:
				break;
			}
		}
	}
}