package com.cendric;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.cendric.ecs.components.SpellStateComponent.SpellType;

public class Resources {

	// Font
	public static BitmapFont font;
	
	// GUI
	public static Texture spellSlot;
	public static Texture spellSlotSelected;
	public static Texture blackOverlay;
	public static Texture levelSlot;
	public static Texture levelSlotSelected;

	// spells
	private static Map<SpellType, Animation> spells;
	private static Map<SpellType, Animation> staffEffects;
	
	private static Animation spellFire;
	private static Texture spellFire1;
	private static Texture spellFire2;
	private static final float SPELL_FIRE_ANIMATION_DURATION = 0.15f;
	
	private static Animation spellIce;
	private static Texture spellIce1;
	private static Texture spellIce2;
	private static final float SPELL_ICE_ANIMATION_DURATION = 0.15f;
	
	private static Animation spellMoney;
	private static Texture spellMoney1;
	private static Texture spellMoney2;
	private static final float SPELL_MONEY_ANIMATION_DURATION = 0.15f;
	
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
	
	// Cursor
	public static Texture aimCursor;
	
	// staff overlay
	private static Texture staffFire1;
	private static Texture staffFire2;
	private static Texture staffIce1;
	private static Texture staffIce2;
	private static Texture staffMoney1;
	private static Texture staffMoney2;
	
	private static Animation staffFire;
	private static final float STAFF_FIRE_ANIMATION_DURATION = 0.15f;
	
	private static Animation staffIce;
	private static final float STAFF_ICE_ANIMATION_DURATION = 0.15f;
	
	private static Animation staffMoney;
	private static final float STAFF_MONEY_ANIMATION_DURATION = 0.15f;
	
	// tiled maps
	public static Map<Integer, TiledMap> levelMap;
	private static TiledMap tiledMapLevel1;
	private static TiledMap tiledMapLevel2;
	private static TiledMap tiledMapLevel3;
	
	public static void loadTiledMaps() {
		// load tiled maps
		tiledMapLevel1 = new TmxMapLoader().load("tilemaps/tilemap_level1.tmx");
		tiledMapLevel2 = new TmxMapLoader().load("tilemaps/tilemap_level2.tmx");
		tiledMapLevel3 = new TmxMapLoader().load("tilemaps/tilemap_level3.tmx");
		
		levelMap = new HashMap<Integer, TiledMap>();
		levelMap.put(1, tiledMapLevel1);
		levelMap.put(3, tiledMapLevel2);
		levelMap.put(2, tiledMapLevel3);
	}
	
	// Gargoyle
	public static Texture gargoyle;
	
	// Lever
	public static Texture lever_active;
	public static Texture lever_inactive;
	
	public static void load() {
		
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		
		// GUI
		spellSlot = new Texture(Gdx.files.internal("gui/SpellSlot.png"));
		spellSlotSelected = new Texture(Gdx.files.internal("gui/SpellSlotSelected.png"));
		blackOverlay = new Texture(Gdx.files.internal("gui/Overlay.png"));
		levelSlot = new Texture(Gdx.files.internal("gui/LevelSlot.png"));
		levelSlotSelected = new Texture(Gdx.files.internal("gui/LevelSlotSelected.png"));
		
		// load spells
		
		spells = new HashMap<SpellType, Animation>();
		staffEffects = new HashMap<SpellType, Animation>();
		
		spellFire1 = new Texture(Gdx.files.internal("sprites/spells/spell_fire_1.png"));
		spellFire2 = new Texture(Gdx.files.internal("sprites/spells/spell_fire_2.png"));
		spellIce1 = new Texture(Gdx.files.internal("sprites/spells/spell_ice_1.png"));
		spellIce2 = new Texture(Gdx.files.internal("sprites/spells/spell_ice_2.png"));
		spellMoney1 = new Texture(Gdx.files.internal("sprites/spells/spell_money_1.png"));
		spellMoney2 = new Texture(Gdx.files.internal("sprites/spells/spell_money_2.png"));
		
		TextureRegion[] spellFireRegion = new TextureRegion[2];
		spellFireRegion[0] = new TextureRegion(spellFire1);
		spellFireRegion[1] = new TextureRegion(spellFire2);
		spellFire = new Animation(SPELL_FIRE_ANIMATION_DURATION, spellFireRegion);
		spells.put(SpellType.FIRE, spellFire);
		
		TextureRegion[] spellIceRegion = new TextureRegion[2];
		spellIceRegion[0] = new TextureRegion(spellIce1);
		spellIceRegion[1] = new TextureRegion(spellIce2);
		spellIce = new Animation(SPELL_ICE_ANIMATION_DURATION, spellIceRegion);
		spells.put(SpellType.ICE, spellIce);
		
		TextureRegion[] spellMoneyRegion = new TextureRegion[2];
		spellMoneyRegion[0] = new TextureRegion(spellMoney1);
		spellMoneyRegion[1] = new TextureRegion(spellMoney2);
		spellMoney = new Animation(SPELL_MONEY_ANIMATION_DURATION, spellMoneyRegion);
		spells.put(SpellType.MONEY, spellMoney);
		
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
		
		// load aim cursor
		aimCursor = new Texture(Gdx.files.internal("cursor.png"));
		
		// load textures staff
		staffFire1 = new Texture(Gdx.files.internal("sprites/cendric/staff_fire_1.png"));
		staffFire2 = new Texture(Gdx.files.internal("sprites/cendric/staff_fire_2.png"));
		staffIce1 = new Texture(Gdx.files.internal("sprites/cendric/staff_ice_1.png"));
		staffIce2 = new Texture(Gdx.files.internal("sprites/cendric/staff_ice_2.png"));
		staffMoney1 = new Texture(Gdx.files.internal("sprites/cendric/staff_money_1.png"));
		staffMoney2 = new Texture(Gdx.files.internal("sprites/cendric/staff_money_2.png"));

		TextureRegion[] staffFireRightRegion = new TextureRegion[2];
		staffFireRightRegion[0] = new TextureRegion(staffFire1);
		staffFireRightRegion[1] = new TextureRegion(staffFire2);
		staffFire = new Animation(STAFF_FIRE_ANIMATION_DURATION, staffFireRightRegion);
		
		TextureRegion[] staffIceRightRegion = new TextureRegion[2];
		staffIceRightRegion[0] = new TextureRegion(staffIce1);
		staffIceRightRegion[1] = new TextureRegion(staffIce2);
		staffIce = new Animation(STAFF_ICE_ANIMATION_DURATION, staffIceRightRegion);
		
		TextureRegion[] staffMoneyRightRegion = new TextureRegion[2];
		staffMoneyRightRegion[0] = new TextureRegion(staffMoney1);
		staffMoneyRightRegion[1] = new TextureRegion(staffMoney2);
		staffMoney = new Animation(STAFF_MONEY_ANIMATION_DURATION, staffMoneyRightRegion);
		
		staffEffects.put(SpellType.FIRE, staffFire);
		staffEffects.put(SpellType.ICE, staffIce);
		staffEffects.put(SpellType.MONEY, staffMoney);
		
		// load gargoyle
		gargoyle = new Texture(Gdx.files.internal("sprites/gargoyle.png"));
		
		// load lever
		lever_inactive = new Texture(Gdx.files.internal("sprites/lever/bouncer_1.png"));
		lever_active = new Texture(Gdx.files.internal("sprites/lever/bouncer_2.png"));
	}
	
	public static TiledMap getMapForLevel(int levelID) {
		return levelMap.get(levelID);
	}
	
	public static TextureRegion getLeverTexture(boolean active) {
		if (active) {
			return new TextureRegion(lever_active);
		} else {
			return new TextureRegion(lever_inactive);
		}
	}
	
	public static TextureRegion getSpellTexture(SpellType spellType, float stateTime) {
		return spells.get(spellType).getKeyFrame(stateTime, true);
	}
	
	public static TextureRegion getStaffEffectTexture(SpellType spellType, float stateTime, boolean facingLeft) {
		TextureRegion keyFrame = staffEffects.get(spellType).getKeyFrame(stateTime, true);
		TextureRegion ret = new TextureRegion(keyFrame);
		ret.flip(facingLeft, false);
		return ret;
	}

	public static void dispose() {
		
		font.dispose();
		
		spellSlot.dispose();
		spellSlotSelected.dispose();
		blackOverlay.dispose();
		levelSlot.dispose();
		levelSlotSelected.dispose();
		
		cendricIdleRight.dispose(); 
		cendricWalking1.dispose(); 
		cendricWalking2.dispose();
		
		staffFire1.dispose();
		staffFire2.dispose();
		staffIce1.dispose();
		staffIce2.dispose();
		staffMoney1.dispose();
		staffMoney2.dispose();
		
		spellFire1.dispose();
		spellFire2.dispose();
		spellIce1.dispose();
		spellIce2.dispose();
		spellMoney1.dispose();
		spellMoney2.dispose();
		
		gargoyle.dispose();
		
		lever_active.dispose();
		lever_inactive.dispose();
		
		if (levelMap != null) {
			for (TiledMap tm : levelMap.values()) {
				if (tm != null) tm.dispose();
			}
		}
	}
}
