package com.cendric.controllers;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cendric.ecs.Entity;
import com.cendric.ecs.systems.SpriteRenderSystem;

public class WorldView {
	private Level currentLevel;
	private SpriteRenderSystem spriteSystem;
	
	public WorldView(Level level) {
		currentLevel = level;
		spriteSystem = new SpriteRenderSystem();
	}
	
	public void draw(SpriteBatch batch, float stateTime) {
		List<Entity> entities = currentLevel.getEntities();
		
		// Run render system
		spriteSystem.render(entities, batch, stateTime);
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		// TODO could be used for a debug renderer (draw bounding boxes only)
	}

}
