package com.cendric.ecs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.CendricSpellsComponent;
import com.cendric.ecs.components.DynamicTileComponent;
import com.cendric.ecs.components.LeverComponent;
import com.cendric.ecs.components.MassComponent;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.ecs.components.SpellStateComponent.SpellType;
import com.cendric.ecs.components.TextureComponent;

public class EntityFactory {
	
	public static Entity createCendric(float x, float y) {
		Entity cendric = new Entity("Cendric");
		
		cendric.addComponent(new PositionComponent(x, y));
		cendric.addComponent(new BoundingBoxComponent(new Rectangle(x+5, y, 54, 115)));
		cendric.addComponent(new TextureComponent());
		cendric.addComponent(new MovementComponent());
		cendric.addComponent(new MassComponent());
		cendric.addComponent(new AnimationStateComponent());
		
		CendricSpellsComponent cp = new CendricSpellsComponent();
		cp.learnSpell(SpellType.MONEY);
		cp.learnSpell(SpellType.FIRE);
		cp.learnSpell(SpellType.ICE);
		
		cendric.addComponent(cp);
		
		return cendric;
	}
	
	public static Entity createSpellCast(SpellType type, float x, float y, float dx, float dy) {
		Entity spell = new Entity("Spell");
		
		SpellStateComponent sp = new SpellStateComponent(type);
		if (type == SpellType.ICE) {
			sp.numberReflections = 1;
		}
		
		spell.addComponent(sp);
		spell.addComponent(new BoundingBoxComponent(new Rectangle(x+5, y+5, 20, 20)));
		spell.addComponent(new PositionComponent(x, y));
		spell.addComponent(new TextureComponent());
		spell.addComponent(new AnimationStateComponent());
		
		MassComponent m = new MassComponent();
		m.mass = 0;
		spell.addComponent(m);
		
		MovementComponent mov = new MovementComponent();
		mov.vx = dx;
		mov.vy = dy;
		mov.ACCELERATION = 0;
		spell.addComponent(mov);
		
		return spell;
	}
	
	public static Entity createGargoyle(float x, float y) {
		Entity gargoyle = new Entity("Gargoyle");
		
		gargoyle.addComponent(new PositionComponent(x, y));
		gargoyle.addComponent(new BoundingBoxComponent(new Rectangle(x+8, y, 64, 64)));
		
		TextureComponent tex = new TextureComponent();
		tex.texture = new TextureRegion(Resources.gargoyle);
		gargoyle.addComponent(tex);
		
		return gargoyle;
	}
	
	public static Entity createCursor(float x, float y) {
		Entity cursor = new Entity("Cursor");
		
		cursor.addComponent(new PositionComponent(x, y));
		cursor.addComponent(new BoundingBoxComponent(new Rectangle(x, y, 32, 32)));
		
		TextureComponent tex = new TextureComponent();
		tex.texture = new TextureRegion(Resources.aimCursor);
		cursor.addComponent(tex);
		
		return cursor;
	}

	public static Entity createDynamicTile(TiledMapTile tile, float x, float y) {
		Entity t = new Entity("Tile");
		
		t.addComponent(new PositionComponent(x, y));
		t.addComponent(new DynamicTileComponent(tile.getId()));
		
		TextureComponent tex = new TextureComponent();
		tex.texture = tile.getTextureRegion();
		t.addComponent(tex);
		
		BoundingBoxComponent bb = new BoundingBoxComponent(new Rectangle(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE));
		// TODO Constants.TILE_FROZEN_WATER_ID could have smaller bounding box
		
		if (tile.getId() == Constants.TILE_WATER_ID) {
			bb.active = false;
		}
		t.addComponent(bb);

		return t;
	}
	
	public static Entity createLever(float x, float y) {
		Entity t = new Entity("Lever");
		
		t.addComponent(new PositionComponent(x, y));
		
		TextureComponent tex = new TextureComponent();
		tex.texture = Resources.getLeverTexture(true);
		t.addComponent(tex);
		
		BoundingBoxComponent bb = new BoundingBoxComponent(new Rectangle(x + 10, y, Constants.TILE_SIZE - 20, Constants.TILE_SIZE));
		bb.active = false;
		t.addComponent(bb);
		
		LeverComponent l = new LeverComponent();
		t.addComponent(l);

		return t;
	}
}