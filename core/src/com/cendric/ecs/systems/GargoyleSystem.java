package com.cendric.ecs.systems;

import com.cendric.CendricGame;
import com.cendric.controllers.Level;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.screens.SuccessScreen;

public class GargoyleSystem extends UpdateSystem {
	
	private CendricGame game;
	private Level level;

	public GargoyleSystem(CendricGame game, Level level) {
		this.game = game;
		this.level = level;
	}
	
	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Gargoyle")) return;
		BoundingBoxComponent gargoyleBB = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		if (gargoyleBB == null) return;
		
		Entity player = level.getPlayer();
		BoundingBoxComponent playerBB = (BoundingBoxComponent) player.getComponent(ComponentType.BoundingBox);
		
		if (playerBB.boundingBox.overlaps(gargoyleBB.boundingBox)) {
			level.finish();
			game.finishCurrentLevel();
			game.setScreen(new SuccessScreen(game));
		}
	}

}
