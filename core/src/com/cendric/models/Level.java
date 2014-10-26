package com.cendric.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.ecs.Entity;
import com.cendric.ecs.EntityFactory;

/**
 * @author tiz, iro
 * Model class for a level
 */
public class Level {
	public int ID;
	
	protected boolean isComplete;
	
	protected TiledMap tiledMap;
	
	protected Rectangle levelRect;
	
	protected List<Rectangle> collidableTiles;
	protected List<Rectangle> evilTiles;
	protected List<DynamicTile> dynamicTiles;
	
	protected List<Entity> entities;
	protected List<Entity> toAdd;
	protected List<Entity> toDelete;
	
	public Level(int id) {
		this.ID = id;
		isComplete = false;
		tiledMap = Resources.getMapForLevel(id);
		if (tiledMap.getLayers().getCount() != Constants.LAYER_MIN_COUNT) 
		{
			System.err.println("tiledMap for level " + ID + " must have at minimum "+ Constants.LAYER_MIN_COUNT + " layers!");
		}
		
		entities = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		toDelete = new ArrayList<Entity>();
		
		evilTiles = new ArrayList<Rectangle>();
		collidableTiles = new ArrayList<Rectangle>();
		computeCollidableTiles();
		computeEvilTiles();
		computeLevelRect();
		loadDynamicTiles();
		
		Vector2 start = computePosition(Constants.LAYER_START_POS);
		entities.add(EntityFactory.createCendric(start.x, start.y));

		Vector2 end = computePosition(Constants.LAYER_END_POS);
		entities.add(EntityFactory.createGargoyle(end.x, end.y));
	}
	
	public void updateEntityArray() {
		entities.addAll(toAdd);
		entities.removeAll(toDelete);
		toAdd.clear();
		toDelete.clear();
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(Entity e) {
		toAdd.add(e);
	}
	
	public void removeEntity(Entity e) {
		toDelete.add(e);
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}
	
	public Rectangle getLevelRect() {
		return levelRect;
	}
	
	public List<DynamicTile> getDynamicTiles() {
		return dynamicTiles;
	}
	
	public List<Rectangle> getEvilTiles() {
		return evilTiles;
	}
	
	// TODO Collision can be done more efficiently with a lookup table like getCollidableTile(int x, int y)
	public List<Rectangle> getCollidableTiles() {
		return collidableTiles;
	}
	
	public boolean isComplete() {
		return isComplete;
	}
	
	/**
	 * called when the player finishes
	 * the level
	 */
	public void finish() {
		isComplete = true;
	}
	
	// TODO [iro] refactor this ugly shit
	public void loadDynamicTiles() {
		dynamicTiles = new ArrayList<DynamicTile>();
		for (MapLayer layer_ : tiledMap.getLayers()) {
			TiledMapTileLayer layer = (TiledMapTileLayer) layer_;
			for (int column = 0; column < layer.getWidth(); column++) {
				for (int row = 0; row < layer.getHeight(); row++) {
					Cell cell = layer.getCell(column, row);
					addDynamicTile(cell, column, row);
				}
			}
		}
	}
	
	public void addDynamicTile(Cell cell, int column, int row) {
		if (cell == null || cell.getTile() == null) return;
		for (int i : IceTile.getIDs()) {
			if (i == cell.getTile().getId()) {
				dynamicTiles.add(new IceTile(column, row, this));
			}
		}
		for (int i : WaterTile.getIDs()) {
			if (i == cell.getTile().getId()) {
				dynamicTiles.add(new WaterTile(column, row, this));
			}
		}
	}
	
	/**
	 * @param layerID the ID of the layer where to search
	 * @return the position of the first tile found in this layer
	 */
	public Vector2 computePosition(int layerID) {
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(layerID);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					return new Vector2 (column * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
				}
			}
		}
		System.err.println("No tile found in layer " + layerID);
		return new Vector2 (0, 0);
	}
	
	private void computeLevelRect() {
		TiledMapTileLayer levelLayer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_BACKGROUND);
		levelRect = new Rectangle(0, 0, levelLayer.getWidth() * Constants.TILE_SIZE, levelLayer.getHeight() * Constants.TILE_SIZE);
	}
	
	private void computeRectsOnLayer(List<Rectangle> rects, int layerID) {
		rects.clear();
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(layerID);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					rects.add(new Rectangle(column * Constants.TILE_SIZE, row * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE));
				}
			}
		}
	}
	
	public void computeCollidableTiles() {
		computeRectsOnLayer(collidableTiles, Constants.LAYER_COLLIDABLE);
	}
	
	public void computeEvilTiles() {
		computeRectsOnLayer(evilTiles, Constants.LAYER_EVIL);
	}
}
