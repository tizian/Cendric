package com.hackzurich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.hackzurich.Constants;
import com.hackzurich.GargoyleGame;
import com.hackzurich.Resources;
import com.hackzurich.controllers.InputController;
import com.hackzurich.controllers.WorldController;
import com.hackzurich.models.Level;

public class GameScreen implements Screen {
	
	private GargoyleGame game;
	private TiledMapRenderer tiledMapRenderer;
	private WorldController worldController;
	private InputController inputController;
	
	private Texture helpOverlay;

	public GameScreen(GargoyleGame game) {
		this.game = game;
        
		Resources.loadTiledMaps();
		
		Level level = null;
		if (!game.level1Complete) {
			level = new Level(Resources.tiledMapLevel1);
		}
		else if (!game.level2Complete) {
			level = new Level(Resources.tiledMapLevel2);
		}
		else {
			game.level1Complete = false;
			game.level2Complete = false;
			level = new Level(Resources.tiledMapLevel1);
		}
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.tiledMap);
		
        game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
		
		worldController = new WorldController(game, level);
		inputController = new InputController(worldController);
		
		helpOverlay = Resources.helpOverlay;
	}

	@Override
	public void render(float delta) {
		inputController.update();
		worldController.update(delta);
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
			game.setScreen(new TutorialScreen(game, this));
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tiledMapRenderer.setView(game.camera);
		tiledMapRenderer.render();
		
		game.batch.begin();
		game.batch.draw(helpOverlay, game.camera.position.x - Constants.WINDOW_WIDTH/2, game.camera.position.y - Constants.WINDOW_HEIGHT/2);
		worldController.draw(game.batch);
		game.batch.end();
		
		game.shapeRenderer.begin(ShapeType.Line);
		worldController.draw(game.shapeRenderer);
		game.shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
