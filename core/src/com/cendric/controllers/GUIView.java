package com.cendric.controllers;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.CendricGame;
import com.cendric.ecs.Entity;
import com.cendric.ecs.systems.GUIRenderSystem;
import com.cendric.models.Level;

public class GUIView {
	private Level currentLevel;
	
	GUIRenderSystem guiRenderSystem;
	
	public GUIView(CendricGame game, Level level) {
		currentLevel = level;
		guiRenderSystem = new GUIRenderSystem(game);
	}
	
	public void draw(SpriteBatch batch) {
		List<Entity> entities = currentLevel.getEntities();
		
		// Run gui render system
		guiRenderSystem.render(entities, batch);
	}
}
