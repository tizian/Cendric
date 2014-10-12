package com.hackzurich;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hackzurich.screens.TitleScreen;

public class GargoyleGame extends Game {
	
	// One SpriteBatch for the whole game. Passed on to all Screens.
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;
	
	public boolean level1Complete;
	public boolean level2Complete;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		Resources.load();
		
		// Set first Screen.
		this.setScreen(new TitleScreen(this));
		
		level1Complete = false;
		level2Complete = false;
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		Resources.dispose();
	}
}
