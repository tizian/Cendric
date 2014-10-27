package com.cendric;

import com.badlogic.gdx.Game;
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
	public ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;

	private int currentLevelID;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		Resources.load();

		// Set first Screen.
		currentLevelID = 1;
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
	}

	public int getCurrentLevel() {
		return currentLevelID;
	}

	public void setCurrentLevel(int levelID) {
		if (levelID < 1 || levelID > Resources.levelCount) {
			System.err.println("There is no level with level ID " + levelID
					+ " loaded.");
			return;
		}
		currentLevelID = levelID;
	}
}
