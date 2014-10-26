package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.controllers.InputController;
import com.cendric.controllers.WorldController;
import com.cendric.controllers.WorldView;
import com.cendric.models.Level;

public class GameScreen implements Screen {
	
	private CendricGame game;
	private TiledMapRenderer tiledMapRenderer;
	private WorldController worldController;
	private InputController inputController;
	private WorldView worldView;
	
	private Texture helpOverlay;

	public GameScreen(CendricGame game) {
		this.game = game;
        
		Resources.loadTiledMaps();
		Level level = new Level(game.getCurrentLevel());
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(level.getTiledMap());
		
        game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        // TODO [tiz] change tile size to 1x1....or maybe not
        game.camera.update();
		
		inputController = new InputController(worldController);
		worldController = new WorldController(game, level, inputController);
		worldView = new WorldView(level, inputController);
		
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			game.setScreen(new GameScreen(game));
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tiledMapRenderer.setView(game.camera);
		tiledMapRenderer.render();
		
		game.batch.begin();
		game.batch.draw(helpOverlay, game.camera.position.x - Constants.WINDOW_WIDTH/2, game.camera.position.y - Constants.WINDOW_HEIGHT/2);
		worldView.draw(game.batch);
		game.batch.end();
		
		game.shapeRenderer.begin(ShapeType.Line);
		worldView.draw(game.shapeRenderer);
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
