package com.cendric.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Constants;

public class InputController {
	
	private WorldController worldController;
	
	public InputController(WorldController worldController) {
		this.worldController = worldController;
		Gdx.input.setCursorCatched(true);
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			worldController.leftPressed();
	    }
		else {
			worldController.leftReleased();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			worldController.rightPressed();
	    }
		else {
			worldController.rightReleased();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			worldController.jumpPressed();
		}
		else {
			worldController.jumpReleased();
		}
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			worldController.firePressed();
		}
		else {
			worldController.fireReleased();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			worldController.spellFirePressed();
		}
		else {
			worldController.spellFireReleased();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			worldController.spellIcePressed();
		}
		else {
			worldController.spellIceReleased();
		}
		
		Vector2 mousePosition = new Vector2(Gdx.input.getX(), Constants.WINDOW_HEIGHT - Gdx.input.getY());
		worldController.mousePosition = mousePosition;
	}  
}
