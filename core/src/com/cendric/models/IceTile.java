package com.cendric.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
import com.cendric.models.Spell.SpellId;

/**
 * @author tiz, iro
 * Model class of a tile that behaves like an ice tile
 */
public class IceTile extends DynamicTile {
	
	public IceTile(int column, int row, Level level) {
		super(column, row, level);
	}

	public Rectangle getRect() {
		return rect;
	}
	
	public void hit(SpellId spell) {
		if (spell != SpellId.FIRE) return;
		TiledMapTileLayer layer = (TiledMapTileLayer) level.tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
		Cell cell = layer.getCell(column, row);
		if (cell.getTile().getId() == Constants.TILE_ICE_1_ID) {
			cell.setTile(level.tiledMap.getTileSets().getTile(Constants.TILE_ICE_2_ID));
		} else {
			cell.setTile(null);
			level.computeCollidableTiles();
			level.loadDynamicTiles();
		}
	}

	/**
	 * @return the id of all tiles that have this behaviour
	 */
	public static int[] getIDs() {
		return new int[] {Constants.TILE_ICE_1_ID, Constants.TILE_FROZEN_WATER_ID, Constants.TILE_ICE_2_ID};
	}
}
