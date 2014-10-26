package com.cendric.ecs;

import com.cendric.Resources;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.TextureComponent;

public class EntityFactory {
	
	public static Entity createCendric(float x, float y) {
		Entity cendric = new Entity();
		
		cendric.addComponent(new PositionComponent(x, y));
		cendric.addComponent(new TextureComponent(Resources.cendricIdleRightFrame));
		cendric.addComponent(new MovementComponent());
//		cendric.addComponent(new MassComponent());
		
		return cendric;
	}
}
