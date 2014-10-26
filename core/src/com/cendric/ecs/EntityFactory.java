package com.cendric.ecs;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.Resources;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.MassComponent;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.TextureComponent;

public class EntityFactory {
	
	public static Entity createCendric(float x, float y) {
		Entity cendric = new Entity();
		
		cendric.addComponent(new PositionComponent(x, y));
		cendric.addComponent(new BoundingBoxComponent(new Rectangle(x+5, y, 54, 115)));
		cendric.addComponent(new TextureComponent(Resources.cendricIdleRightFrame));
		cendric.addComponent(new MovementComponent());
		cendric.addComponent(new MassComponent());
		cendric.addComponent(new AnimationStateComponent());
		
		return cendric;
	}
}