package com.cendric.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
import com.cendric.models.Spell.SpellId;

/**
 * @author tiz, iro
 * Model class of a tile that behaves like an ice tile
 */
public class IceTile extends DynamicTile {
	
	private final int maxHitCount = 1;
	
	public IceTile(int column, int row, Level level) {
		super(column, row, level);
	}

	public Rectangle getRect() {
		return rect;
	}
	
	public void hit(SpellId spell) {
		if (spell != SpellId.FIRE) return;
		hitCount++;
		if (hitCount == maxHitCount) {
			TiledMapTileLayer layer = (TiledMapTileLayer) level.tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
			layer.getCell(column, row).setTile(null);
			level.computeCollidableTiles();
		}
	}

	/**
	 * @return the id of all tiles that have this behaviour
	 */
	public static int[] getIDs() {
		return new int[] {Constants.TILE_ICE_ID, Constants.TILE_FROZEN_WATER_ID};
	}
}
