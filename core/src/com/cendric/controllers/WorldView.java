package com.cendric.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Resources;
import com.cendric.ecs.systems.SpriteRenderSystem;
import com.cendric.models.Level;

public class WorldView {
	private Level currentLevel;
	private InputController input;
	
	SpriteRenderSystem spriteSystem;
	
	public WorldView(Level level, InputController input) {
		currentLevel = level;
		this.input = input;
		
		spriteSystem = new SpriteRenderSystem();
	}
	
	public void draw(SpriteBatch batch) {
		// Run render system
		spriteSystem.render(currentLevel.getEntities(), batch);
		
		// draw aim cursor
		Vector2 mousePosition = input.getMousePosition();
		batch.draw(Resources.aimCursor, mousePosition.x, mousePosition.y);
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		// TODO could be used for a debug renderer (draw bounding boxes only)
	}

}
