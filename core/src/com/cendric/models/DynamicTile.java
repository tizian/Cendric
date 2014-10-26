package com.cendric.models;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
import com.cendric.ecs.components.SpellStateComponent.SpellType;
import com.cendric.models.Spell.SpellId;

/**
 * 
 * @author tiz, iro
 * Abstract model class for dynamic tiles
 * dynamic tiles are tiles with which the player can interact
 * by casting spells
 */
public abstract class DynamicTile {

	// location
	protected int column;
	protected int row;
	protected Level level;
	
	/**
	 * the collider rectangle for this tile.
	 * It is set in the constructor
	 */
	protected Rectangle rect;
	
	/**
	 * super constructor
	 * creates a tile with a
	 * collider rect thats the size of
	 * the tile itself
	 * 
	 * when overriding the constructor
	 * make sure the collider rect is set
	 */
	public DynamicTile(int column, int row, Level level) {
		this.column = column;
		this.row = row;
		this.level = level;
		rect = new Rectangle(column * Constants.TILE_SIZE, row * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
	}
	
	/**
	 * called by a controller when the tile gets hit with a certain spell
	 * @param spell the id of the spell the tile gets 
	 */
	public abstract void hit(SpellType spell);
	
	public Rectangle getRect() {
		return rect;
	}
	
	

}
