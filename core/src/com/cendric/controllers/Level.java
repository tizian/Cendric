package com.cendric.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.ecs.Entity;
import com.cendric.ecs.EntityFactory;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.LeverComponent;

/**
 * @author tiz, iro Model class for a level
 */
public class Level implements Constants {
	public int ID;

	protected boolean isComplete;

	protected TiledMap tiledMap;

	protected Rectangle levelRect;

	protected List<Rectangle> collidableTiles;
	protected List<Rectangle> evilTiles;
	protected Map<String, Entity> leverMap;

	protected List<Entity> entities;
	protected List<Entity> toAdd;
	protected List<Entity> toDelete;

	public Level(int id) {
		this.ID = id;
		isComplete = false;
		tiledMap = Resources.getMapForLevel(id);
		if (tiledMap.getLayers().getCount() < LAYER_MIN_COUNT) {
			System.err.println("tiledMap for level " + ID
					+ " must have at minimum " + LAYER_MIN_COUNT
					+ " layers!");
		}

		entities = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		toDelete = new ArrayList<Entity>();

		evilTiles = new ArrayList<Rectangle>();
		collidableTiles = new ArrayList<Rectangle>();
		leverMap = new HashMap<String, Entity>();
		
		Vector2 start = computePosition(LAYER_START_POS);
		entities.add(EntityFactory.createCendric(start.x, start.y+5));

		entities.add(EntityFactory.createCursor(start.y, start.x));

		Vector2 end = computePosition(LAYER_END_POS);
		entities.add(EntityFactory.createGargoyle(end.x, end.y));
		
		loadDynamicTiles();
		computeCollidableTiles();
		computeEvilTiles();
		computeLevelRect();
		loadLevers();
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

	public List<Rectangle> getEvilTiles() {
		return evilTiles;
	}

	// TODO Collision can be done more efficiently with a lookup table like
	// getCollidableTile(int x, int y)
	public List<Rectangle> getCollidableTiles() {
		return collidableTiles;
	}

	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * called when the player finishes the level
	 */
	public void finish() {
		isComplete = true;
	}

	public void loadDynamicTiles() {
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
		if (cell == null || cell.getTile() == null)
			return;
		TiledMapTile tile = cell.getTile();
		
		// TODO Only want to know if it is a dynamic tile actually :-/
		if (DYNAMIC_TILES.contains(tile.getId())) {
			entities.add(EntityFactory.createDynamicTile(tile, column * TILE_SIZE, row * TILE_SIZE));
			cell.setTile(null);	// tile not actually part of map, it's an entity now
		}
		
		if (tile.getProperties().containsKey("lever")) {
			Entity e = EntityFactory.createLever(column * TILE_SIZE, row * TILE_SIZE);
			leverMap.put((String) cell.getTile().getProperties().get("lever"), e);
			entities.add(e);
			cell.setTile(null);
		}
	}
	
	public void loadLevers() {
		//check if we have a lever layer
		if (tiledMap.getLayers().getCount() < 6) return;
		
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(
				Constants.LAYER_LEVERS);
		
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
			Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					if (cell.getTile().getProperties().containsKey("lever_id")) {
						String id = (String) cell.getTile().getProperties().get("lever_id");
					Entity e = leverMap.get(id);
						if (e == null) {
							System.err.println("No lever found for id " + id);
						} else {
							LeverComponent l = (LeverComponent) e.getComponent(ComponentType.Lever);
							l.addCell(column, row);
						}
					}
				}
			}
		}
	}

	/**
	 * @param layerID
	 *            the ID of the layer where to search
	 * @return the position of the first tile found in this layer
	 */
	public Vector2 computePosition(int layerID) {
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(
				layerID);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					return new Vector2(column * TILE_SIZE, row
							* TILE_SIZE);
				}
			}
		}
		System.err.println("No tile found in layer " + layerID);
		return new Vector2(0, 0);
	}

	private void computeLevelRect() {
		TiledMapTileLayer levelLayer = (TiledMapTileLayer) tiledMap.getLayers()
				.get(Constants.LAYER_BACKGROUND);
		levelRect = new Rectangle(0, 0, levelLayer.getWidth()
				* Constants.TILE_SIZE, levelLayer.getHeight()
				* Constants.TILE_SIZE);
	}

	private void computeRectsOnLayer(List<Rectangle> rects, int layerID) {
		rects.clear();
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(
				layerID);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					rects.add(new Rectangle(column * Constants.TILE_SIZE, row
							* Constants.TILE_SIZE, Constants.TILE_SIZE,
							Constants.TILE_SIZE));
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
