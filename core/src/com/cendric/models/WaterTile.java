package com.cendric.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.cendric.Constants;
import com.cendric.ecs.components.SpellStateComponent.SpellType;

/**
 * @author tiz, iro
 * Model class of a water tile
 */
public class WaterTile extends DynamicTile {
	
	public WaterTile(int column, int row, Level level) {
		super(column, row, level);
	}
	
	public void hit(SpellType spell) {
		if (spell != SpellType.ICE) return;
		TiledMapTileLayer layer = (TiledMapTileLayer) level.tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
		if (layer.getCell(column, row) == null) {
				layer.setCell(column, row, new Cell());
		}
		layer.getCell(column, row).setTile(level.tiledMap.getTileSets().getTile(Constants.TILE_FROZEN_WATER_ID));
		level.computeCollidableTiles();
		level.loadDynamicTiles();
	}

	/**
	 * @return the id of all tiles that have this behaviour
	 */
	public static int[] getIDs() {
		return new int[] {Constants.TILE_WATER_ID};
	}
}
