package com.cendric.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.cendric.Constants;
import com.cendric.models.Spell.SpellId;

/**
 * @author tiz, iro
 * Model class of a water tile
 */
public class WaterTile extends DynamicTile {
	
	private final int maxHitCount = 1;

	public WaterTile(int column, int row, Level level) {
		super(column, row, level);
	}
	
	public void hit(SpellId spell) {
		if (spell != SpellId.ICE) return;
		hitCount++;
		if (hitCount == maxHitCount) {
			TiledMapTileLayer layer = (TiledMapTileLayer) level.tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
			if (layer.getCell(column, row) == null) {
				layer.setCell(column, row, new Cell());
			}
			layer.getCell(column, row).setTile(level.tiledMap.getTileSets().getTile(Constants.TILE_FROZEN_WATER_ID));
			level.computeCollidableTiles();
			level.loadDynamicTiles();
		}
	}

	/**
	 * @return the id of all tiles that have this behaviour
	 */
	public static int[] getIDs() {
		return new int[] {Constants.TILE_WATER_ID};
	}
}
