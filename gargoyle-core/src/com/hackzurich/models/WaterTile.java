package com.hackzurich.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.hackzurich.Constants;

public class WaterTile {
	public static final int MAX_HIT_COUNT = 1;
	public static final int ID = 10;
	public int hitCount;
	private int column;
	private int row;
	private Rectangle rect;
	private Level level;

	public WaterTile(int column, int row, Level level) {
		hitCount = 0;
		this.column = column;
		this.row = row;
		this.level = level;
		rect = new Rectangle(column * Constants.TILE_SIZE, row * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void hit() {
		hitCount++;
		if (hitCount == MAX_HIT_COUNT) {
			// cell.setTile(null);
			TiledMapTileLayer layer = (TiledMapTileLayer) level.tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
			if (layer.getCell(column, row) == null) {
				layer.setCell(column, row, new Cell());
			}
			layer.getCell(column, row).setTile(level.tiledMap.getTileSets().getTile(Constants.FROZEN_WATER_ID));
			level.computeCollidableTiles();
			level.loadIceTiles();
			level.loadWaterTiles();
		}
	}
}
