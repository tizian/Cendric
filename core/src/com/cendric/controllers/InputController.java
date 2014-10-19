package com.cendric.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Constants;
import com.cendric.controllers.WorldController.Key;

public class InputController {
	
	private WorldController worldController;
	private boolean firingSpell;
	
	public InputController(WorldController worldController) {
		this.worldController = worldController;
		Gdx.input.setCursorCatched(true);
		firingSpell = false;
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			worldController.keyPressed(Key.LEFT, true);
	    }
		else {
			worldController.keyPressed(Key.LEFT, false);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			worldController.keyPressed(Key.RIGHT, true);
	    }
		else {
			worldController.keyPressed(Key.RIGHT, false);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			worldController.keyPressed(Key.JUMP, true);
		}
		else {
			worldController.keyPressed(Key.JUMP, false);
		}
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (!firingSpell) {
				worldController.keyPressed(Key.FIRE, true);
				firingSpell = true;
			} else {
				worldController.keyPressed(Key.FIRE, false);
			}
		}
		else {
			worldController.keyPressed(Key.FIRE, false);
			firingSpell = false;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			worldController.keyPressed(Key.SPELL_FIRE, true);
		}
		else {
			worldController.keyPressed(Key.SPELL_FIRE, false);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			worldController.keyPressed(Key.SPELL_ICE, true);
		}
		else {
			worldController.keyPressed(Key.SPELL_ICE, false);
		}
		
		Vector2 mousePosition = new Vector2(Gdx.input.getX(), Constants.WINDOW_HEIGHT - Gdx.input.getY());
		worldController.setMousePosition(mousePosition);
	}  
}
