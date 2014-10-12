package com.hackzurich.models;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.hackzurich.Constants;

public class Level {
	
	public TiledMap tiledMap;
	
	public MainCharacter mainCharacter;
	public Gargoyle gargoyle;
	
	public Rectangle levelRect;
	
	private ArrayList<Rectangle> collidableTiles;
	private ArrayList<Rectangle> evilTiles;
	
	public ArrayList<IceTile> iceTiles; 
	public ArrayList<WaterTile> waterTiles; 
	
	
	public Level(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
		if (tiledMap.getLayers().getCount() != Constants.LAYER_MIN_COUNT) 
		{
			System.err.println("tiledMap for level must have at minimum "+ Constants.LAYER_MIN_COUNT + " layers!");
		}
		collidableTiles = new ArrayList<Rectangle>();
		evilTiles = new ArrayList<Rectangle>();
		computeCollidableTiles();
		computeEvilTiles();
		computeLevelRect();
		loadIceTiles();
		loadWaterTiles();
		
		mainCharacter = new MainCharacter(computeStartPosition());
		mainCharacter.setCollisionBox(new Vector2(5, 0), 54, 100);
		
		gargoyle = new Gargoyle(computeEndPosition());
		gargoyle.setCollisionBox(new Vector2(8, 0), 64, 64);
	}
	
	public ArrayList<IceTile> getIceTiles() {
		return iceTiles;
	}
	
	public ArrayList<WaterTile> getWaterTiles() {
		return waterTiles;
	}
	
	public ArrayList<Rectangle> getEvilTiles() {
		return evilTiles;
	}
	
	public void loadIceTiles() {
		iceTiles = new ArrayList<IceTile>();
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null && (cell.getTile().getId() == IceTile.ID || cell.getTile().getId() == IceTile.ICED_ID)) {
					iceTiles.add(new IceTile(cell, column, row, this));
				}
			}
		}
	}
	
	public void loadWaterTiles() {
		waterTiles = new ArrayList<WaterTile>();
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_BACKGROUND);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null && cell.getTile().getId() == WaterTile.ID) {
					waterTiles.add(new WaterTile(column, row, this));
				}
			}
		}
	}
	
	public Vector2 computeStartPosition() {
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_START_POS);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					return new Vector2 (column * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
				}
			}
		}
		System.err.println("No start position in level found.");
		return new Vector2 (0, 0);
	}
	
	public Vector2 computeEndPosition() {
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_END_POS);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					return new Vector2 (column * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
				}
			}
		}
		System.err.println("No end position in level found.");
		return new Vector2 (0, 0);
	}

	public ArrayList<Rectangle> getCollidableTiles() {
		return collidableTiles;
	}
	
	private void computeLevelRect() {
		TiledMapTileLayer levelLayer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_BACKGROUND);
		levelRect = new Rectangle(0, 0, levelLayer.getWidth() * Constants.TILE_SIZE, levelLayer.getHeight() * Constants.TILE_SIZE);
	}
	
	public void computeCollidableTiles() {
		collidableTiles.clear();
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_COLLIDABLE);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					collidableTiles.add(new Rectangle(column * Constants.TILE_SIZE, row * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE));
				}
			}
		}
	}
	
	public void computeEvilTiles() {
		evilTiles.clear();
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(Constants.LAYER_EVIL);
		for (int column = 0; column < layer.getWidth(); column++) {
			for (int row = 0; row < layer.getHeight(); row++) {
				Cell cell = layer.getCell(column, row);
				if (cell != null && cell.getTile() != null) {
					evilTiles.add(new Rectangle(column * Constants.TILE_SIZE, row * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE));
				}
			}
		}
	}
}
