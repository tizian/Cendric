package com.cendric;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cendric.screens.MainMenuScreen;

/**
 * @author tiz, iro
 * @since 0.1.0 Main class for the game "Cendric" The core application starts
 *        from here
 */
public class CendricGame extends Game {

	// One SpriteBatch for the whole game. Passed on to all Screens.
	public SpriteBatch batch;
	public SpriteBatch guiBatch;
	public ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;

	private int currentLevelID;
	
	private boolean paused;
	
	public void pauseUnpause() {
		paused = !paused;
	}
	
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		guiBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		Resources.load();
		Resources.loadTiledMaps();
		
		paused = false;

		// Set first Screen.
		currentLevelID = 1;
		Gdx.input.setCursorCatched(true);
		this.setScreen(new MainMenuScreen(this, 0));
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

	public void finishCurrentLevel() {
		currentLevelID++;
		if (currentLevelID > Resources.levelMap.size()) {
			currentLevelID = 1;
		}
	}

	public int getCurrentLevel() {
		return currentLevelID;
	}

	public void setCurrentLevel(int levelID) {
		if (levelID < 1 || levelID > Resources.levelMap.size()) {
			System.err.println("There is no level with level ID " + levelID
					+ " loaded.");
			return;
		}
		currentLevelID = levelID;
	}
}
