package com.cendric.controllers;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.CendricGame;
import com.cendric.ecs.Entity;
import com.cendric.ecs.systems.GUIRenderSystem;

public class GUIView {
	private Level currentLevel;
	
	GUIRenderSystem guiRenderSystem;
	
	public GUIView(CendricGame game, Level level, InputController input) {
		currentLevel = level;
		guiRenderSystem = new GUIRenderSystem(game, input);
	}
	
	public void draw(SpriteBatch batch, float stateTime) {
		List<Entity> entities = currentLevel.getEntities();
		
		// Run gui render system
		guiRenderSystem.render(entities, batch, stateTime);
	}
}
