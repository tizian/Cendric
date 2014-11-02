package com.cendric.ecs.systems;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.controllers.Level;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.screens.GameOverScreen;

public class CendricDeathSystem extends UpdateSystem {
	
	private CendricGame game;
	private Level level;
	
	public CendricDeathSystem(CendricGame game, Level level) {
		this.game = game;
		this.level = level;
	}
	
	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Cendric")) return;
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		BoundingBoxComponent bb = (BoundingBoxComponent) entity.getComponent(ComponentType.BoundingBox);
		if (pos == null) return;
		if (bb == null) return;
		
		boolean dead = false;
		
		if (pos.y < -5 * Constants.TILE_SIZE) {
			dead = true;
		}

		List<Rectangle> evilTiles = level.getEvilTiles();
		for (Rectangle rect : evilTiles) {
			if (bb.boundingBox.overlaps(rect)) {
				dead = true;
			}
		}
		
		if (dead) {
			game.setScreen(new GameOverScreen(game));
		}
	}

}
