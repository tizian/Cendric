package com.cendric.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Constants;

public class InputController {
	
	private Map<Key, Boolean> pressedKeys;
	private Vector2 lastMousePosition;
	private Vector2 mousePosition;
	private int scrollAmount;
	
	public InputController(WorldController worldController) {
		pressedKeys = new HashMap<Key, Boolean>();
		mousePosition = new Vector2();
		lastMousePosition = new Vector2();
		
		scrollAmount = 0;
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean scrolled(int amount) {
				scrollAmount = amount;
				return true;
			}
		});
	}
	
	public Boolean isKeyPressed(Key key) {
		return pressedKeys.get(key);
	}
	
	public Vector2 getMousePosition() {
		return mousePosition;
	}
	
	public Vector2 getMouseDelta() {
		return mousePosition.cpy().sub(lastMousePosition);
	}
	
	public int getScrollAmount() {
		int ret = scrollAmount;
		scrollAmount = 0;
		return ret;
	}
	
	public void update() {
		lastMousePosition = mousePosition.cpy();
		mousePosition = new Vector2(Gdx.input.getX(), Constants.WINDOW_HEIGHT - Gdx.input.getY());
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			pressedKeys.put(Key.LEFT, true);
	    }
		else {
			pressedKeys.put(Key.LEFT, false);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			pressedKeys.put(Key.RIGHT, true);
	    }
		else {
			pressedKeys.put(Key.RIGHT, false);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			pressedKeys.put(Key.JUMP, true);
		}
		else {
			pressedKeys.put(Key.JUMP, false);
		}
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			pressedKeys.put(Key.CAST, true);
		}
		else {
			pressedKeys.put(Key.CAST, false);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			pressedKeys.put(Key.NUM_1, true);
		}
		else {
			pressedKeys.put(Key.NUM_1, false);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			pressedKeys.put(Key.NUM_2, true);
		}
		else {
			pressedKeys.put(Key.NUM_2, false);
		}
	}
}