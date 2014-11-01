package com.cendric.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.ecs.components.SpellStateComponent.SpellType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Lever extends DynamicTile {
	
	private List<Cell> affectedCells;
	private boolean active;

	public Lever(int column, int row, Level level) {
		super(column, row, level);
		rect = new Rectangle(column * Constants.TILE_SIZE - 8, row * Constants.TILE_SIZE, Constants.TILE_SIZE + 16, Constants.TILE_SIZE * 2);
		affectedCells = new ArrayList<Cell>();
	}

	@Override
	public boolean hit(SpellType spell) {
		if (spell != SpellType.MONEY) return false;
		active = !active;
		System.out.println("affect " + affectedCells.size() + " cells.");
		return true;
		// TODO implement
	}
	
	public void addCell(Cell cell) {
		affectedCells.add(cell);
	}
	
	public Texture getTexture() {
		if (active) {
			return Resources.lever_active;
		} else {
			return Resources.lever_inactive;
		}
	}
	
	
	
	

}
