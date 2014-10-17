package com.cendric.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;

public class IceTile {
	public static final int MAX_HIT_COUNT = 4;
	public static final int ID = 2;
	public static final int ICED_ID = 9;
	public int hitCount;
	private Rectangle rect;
	private Cell cell;
	private Level level;

	public IceTile(Cell cell, int column, int row, Level level) {
		hitCount = 0;
		this.cell = cell;
		this.level = level;
		rect = new Rectangle(column * Constants.TILE_SIZE, row * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void hit() {
		hitCount++;
		if (hitCount == MAX_HIT_COUNT) {
			cell.setTile(null);
			level.computeCollidableTiles();
		}
	}
}
