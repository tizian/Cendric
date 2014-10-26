package com.cendric.ecs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cendric.Resources;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.BoundingBoxComponent;
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
		cendric.addComponent(new SpellStateComponent(SpellType.FIRE));
		
		return cendric;
	}
	
	public static Entity createSpellCast(SpellType type, float x, float y, float dx, float dy) {
		Entity fire = new Entity("Spell");
		
		fire.addComponent(new SpellStateComponent(type));
		fire.addComponent(new BoundingBoxComponent(new Rectangle(x+5, y+5, 20, 20)));
		fire.addComponent(new PositionComponent(x, y));
		fire.addComponent(new TextureComponent());
		fire.addComponent(new AnimationStateComponent());
		
		MassComponent m = new MassComponent();
		m.mass = 0;
		fire.addComponent(m);
		
		MovementComponent mov = new MovementComponent();
		mov.vx = dx;
		mov.vy = dy;
		mov.ACCELERATION = 0;
		fire.addComponent(mov);
		
		return fire;
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
}