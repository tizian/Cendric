package com.cendric.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Resources;
import com.cendric.models.GameObject;
import com.cendric.models.Level;
import com.cendric.models.MainCharacter;

public class WorldView {
	private Level currentLevel;
	private MainCharacter character;
	private WorldController controller;

	public WorldView(Level level, WorldController controller) {
		currentLevel = level;
		character = level.getMainCharacter();
		this.controller = controller;
	}
	
	public void draw(SpriteBatch batch) {
		// draw character
		batch.draw(currentLevel.getGargoyle().getTextureRegion(), currentLevel
				.getGargoyle().getPosition().x, currentLevel.getGargoyle()
				.getPosition().y);
		batch.draw(character.getTextureRegion(), character.getPosition().x,
				character.getPosition().y);
		batch.draw(character.getStaffEffect(), character.getPosition().x,
				character.getPosition().y);
		// draw spells
		for (GameObject spell : controller.getSpells()) {
			batch.draw(spell.getTextureRegion(), spell.getPosition().x,
					spell.getPosition().y);
		}
		
		// draw aim cursor
		Vector2 mousePosition = controller.getMousPosition();
		batch.draw(Resources.aimCursor, mousePosition.x, mousePosition.y);
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		// TODO [tiz] change the look of this
//		shapeRenderer.setColor(0.8f, 0.0f, 0.0f, 1.0f);
//		shapeRenderer.line(controller.getStaffPosition(), controller.getMousPosition());
	}

}
