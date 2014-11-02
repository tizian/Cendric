package com.cendric;

import java.util.ArrayList;
import java.util.Arrays;

public interface Constants {
	// Screen sizes
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	
	public static final int TILE_SIZE = 64;
	
	// Layers
	public static final int LAYER_BACKGROUND = 0;
	public static final int LAYER_COLLIDABLE = 1;
	public static final int LAYER_EVIL = 2;
	public static final int LAYER_START_POS = 3;
	public static final int LAYER_END_POS = 4;
	public static final int LAYER_LEVERS = 5;
	public static final int LAYER_MIN_COUNT = 5;
	
	// Lever tile
	public static final int TILE_LEVER = 6;
	
	// Dynamic tiles
	public static final int TILE_ICE_1_ID = 2;
	public static final int TILE_ICE_2_ID = 5;
	public static final int TILE_FROZEN_WATER_ID = 3;
	public static final int TILE_WATER_ID = 4;
	
	public static final ArrayList<Integer> DYNAMIC_TILES = new ArrayList<Integer>(
		    Arrays.asList(TILE_ICE_1_ID, TILE_ICE_2_ID, TILE_FROZEN_WATER_ID, TILE_WATER_ID));
}