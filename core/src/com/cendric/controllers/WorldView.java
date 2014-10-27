package com.cendric.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cendric.ecs.systems.SpriteRenderSystem;
import com.cendric.models.Level;

public class WorldView {
	private Level currentLevel;
	
	SpriteRenderSystem spriteSystem;
	
	public WorldView(Level level) {
		currentLevel = level;
		
		spriteSystem = new SpriteRenderSystem();
	}
	
	public void draw(SpriteBatch batch) {
		// Run render system
		spriteSystem.render(currentLevel.getEntities(), batch);
		
		// draw aim cursor
//		Vector2 mousePosition = input.getMousePosition();
//		batch.draw(Resources.aimCursor, mousePosition.x, mousePosition.y);
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		// TODO could be used for a debug renderer (draw bounding boxes only)
	}

}
