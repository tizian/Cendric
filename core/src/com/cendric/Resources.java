package com.cendric;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Resources {
	
	// splash screens
	public static Texture titleScreen;
	public static Texture gameoverScreen;
	public static Texture successScreen;
	public static Texture tutorialScreen;
	public static Texture helpOverlay;

	// spells
	public static Texture spellFire1;
	public static Texture spellFire2;
	private static final float SPELL_FIRE_ANIMATION_DURATION = 0.15f;
	
	public static Texture spellIce1;
	public static Texture spellIce2;
	private static final float SPELL_ICE_ANIMATION_DURATION = 0.15f;
	
	public static Animation spellFire;
	public static Animation spellIce;
	
	// cendric
	public static Texture cendricIdleLeft;
	public static Texture cendricIdleRight;
	public static Texture cendricWalking1;
	public static Texture cendricWalking2;
	
	public static TextureRegion cendricIdleLeftFrame;
	public static TextureRegion cendricIdleRightFrame;
	
	public static TextureRegion cendricJumpingRightFrame;
	public static TextureRegion cendricJumpingLeftFrame;
	
	public static Animation cendricWalkingLeft;
	public static Animation cendricWalkingRight;
	private static final float CENDRIC_WALKING_ANIMATION_DURATION = 0.15f;
	
	// staff overlay
	public static Texture staffFire1;
	public static Texture staffFire2;
	public static Texture staffIce1;
	public static Texture staffIce2;
	
	public static Animation staffFireLeft;
	public static Animation staffFireRight;
	private static final float STAFF_FIRE_ANIMATION_DURATION = 0.15f;
	
	public static Animation staffIceLeft;
	public static Animation staffIceRight;
	private static final float STAFF_ICE_ANIMATION_DURATION = 0.15f;
	
	// tiled maps
	public static Map<Integer, TiledMap> levelMap;
	public static TiledMap tiledMapLevel1;
	public static TiledMap tiledMapLevel2;
	public static int levelCount;
	
	public static void loadTiledMaps() {
		// load tiled maps
		tiledMapLevel1 = new TmxMapLoader().load("tilemaps/tilemap_level1.tmx");
		tiledMapLevel2 = new TmxMapLoader().load("tilemaps/tilemap_level2.tmx");
		
		levelMap = new HashMap<Integer, TiledMap>();
		levelMap.put(1, tiledMapLevel1);
		levelMap.put(2, tiledMapLevel2);
		levelCount = levelMap.size();
	}
	
	// Gargoyle
	public static Texture gargoyle;
	
	public static void load() {
		
		// load splash screens
		titleScreen = new Texture(Gdx.files.internal("titleScreen.png"));
		gameoverScreen = new Texture(Gdx.files.internal("gameoverScreen.png"));
		successScreen = new Texture(Gdx.files.internal("successScreen.png"));
		tutorialScreen = new Texture(Gdx.files.internal("tutorialScreen.png"));
		helpOverlay = new Texture(Gdx.files.internal("helpOverlay.png"));
		
		// load textures spells
		spellFire1 = new Texture(Gdx.files.internal("sprites/spells/spell_fire_1.png"));
		spellFire2 = new Texture(Gdx.files.internal("sprites/spells/spell_fire_2.png"));
		spellIce1 = new Texture(Gdx.files.internal("sprites/spells/spell_ice_1.png"));
		spellIce2 = new Texture(Gdx.files.internal("sprites/spells/spell_ice_2.png"));
		
		TextureRegion[] spellFireRegion = new TextureRegion[2];
		spellFireRegion[0] = new TextureRegion(spellFire1);
		spellFireRegion[1] = new TextureRegion(spellFire2);
		spellFire = new Animation(SPELL_FIRE_ANIMATION_DURATION, spellFireRegion);
		
		TextureRegion[] spellIceRegion = new TextureRegion[2];
		spellIceRegion[0] = new TextureRegion(spellIce1);
		spellIceRegion[1] = new TextureRegion(spellIce2);
		spellIce = new Animation(SPELL_ICE_ANIMATION_DURATION, spellIceRegion);
		
		// load textures cendric
		cendricIdleRight = new Texture(Gdx.files.internal("sprites/cendric/cendric_idle.png"));
		cendricWalking1 = new Texture(Gdx.files.internal("sprites/cendric/cendric_walking_1.png"));
		cendricWalking2 = new Texture(Gdx.files.internal("sprites/cendric/cendric_walking_2.png"));
		TextureRegion[] walkingLeftRegion = new TextureRegion[4];
		walkingLeftRegion[0] = new TextureRegion(cendricIdleRight);
		walkingLeftRegion[1] = new TextureRegion(cendricWalking1);
		walkingLeftRegion[2] = new TextureRegion(cendricIdleRight);
		walkingLeftRegion[3] = new TextureRegion(cendricWalking2);
		for (TextureRegion r : walkingLeftRegion) {
			r.flip(true, false);
		}
		cendricIdleLeftFrame = walkingLeftRegion[0];
		cendricJumpingLeftFrame = walkingLeftRegion[3];
		cendricWalkingLeft = new Animation(CENDRIC_WALKING_ANIMATION_DURATION, walkingLeftRegion);
		
		TextureRegion[] walkingRightRegion = new TextureRegion[4];
		walkingRightRegion[0] = new TextureRegion(cendricIdleRight);
		walkingRightRegion[1] = new TextureRegion(cendricWalking1);
		walkingRightRegion[2] = new TextureRegion(cendricIdleRight);
		walkingRightRegion[3] = new TextureRegion(cendricWalking2);
		cendricIdleRightFrame = walkingRightRegion[0];
		cendricJumpingRightFrame = walkingRightRegion[3];
		cendricWalkingRight = new Animation(CENDRIC_WALKING_ANIMATION_DURATION, walkingRightRegion);
		
		// load textures staff
		staffFire1 = new Texture(Gdx.files.internal("sprites/cendric/staff_fire_1.png"));
		staffFire2 = new Texture(Gdx.files.internal("sprites/cendric/staff_fire_2.png"));
		staffIce1 = new Texture(Gdx.files.internal("sprites/cendric/staff_ice_1.png"));
		staffIce2 = new Texture(Gdx.files.internal("sprites/cendric/staff_ice_2.png"));
		
		TextureRegion[] staffFireLeftRegion = new TextureRegion[2];
		staffFireLeftRegion[0] = new TextureRegion(staffFire1);
		staffFireLeftRegion[1] = new TextureRegion(staffFire2);
		for (TextureRegion r : staffFireLeftRegion) {
			r.flip(true, false);
		}
		staffFireLeft = new Animation(STAFF_FIRE_ANIMATION_DURATION, staffFireLeftRegion);
		
		TextureRegion[] staffFireRightRegion = new TextureRegion[2];
		staffFireRightRegion[0] = new TextureRegion(staffFire1);
		staffFireRightRegion[1] = new TextureRegion(staffFire2);
		staffFireRight = new Animation(STAFF_FIRE_ANIMATION_DURATION, staffFireRightRegion);
		
		TextureRegion[] staffIceLeftRegion = new TextureRegion[2];
		staffIceLeftRegion[0] = new TextureRegion(staffIce1);
		staffIceLeftRegion[1] = new TextureRegion(staffIce2);
		for (TextureRegion r : staffIceLeftRegion) {
			r.flip(true, false);
		}
		staffIceLeft = new Animation(STAFF_ICE_ANIMATION_DURATION, staffIceLeftRegion);
		
		TextureRegion[] staffIceRightRegion = new TextureRegion[2];
		staffIceRightRegion[0] = new TextureRegion(staffIce1);
		staffIceRightRegion[1] = new TextureRegion(staffIce2);
		staffIceRight = new Animation(STAFF_ICE_ANIMATION_DURATION, staffIceRightRegion);
		
		// load gargoyle
		gargoyle = new Texture(Gdx.files.internal("sprites/gargoyle.png"));
	}
	
	public static TiledMap getMapForLevel(int levelID) {
		return levelMap.get(levelID);
	}

	public static void dispose() {
		
		cendricIdleRight.dispose(); 
		cendricWalking1.dispose(); 
		cendricWalking2.dispose();
		
		staffFire1.dispose();
		staffFire2.dispose();
		staffIce1.dispose();
		staffIce2.dispose();
		
		spellFire1.dispose();
		spellFire2.dispose();
		spellIce1.dispose();
		spellIce2.dispose();
		
		gargoyle.dispose();
		
		if (tiledMapLevel1 != null) {
			tiledMapLevel1.dispose();
		}
		if (tiledMapLevel2 != null) {
			tiledMapLevel2.dispose();
		}
	}
}
