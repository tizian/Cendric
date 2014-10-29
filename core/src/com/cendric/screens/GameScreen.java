package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.controllers.GUIView;
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
	private GUIView guiView;

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
		worldView = new WorldView(level);
		guiView = new GUIView(game, level, inputController);
	}

	@Override
	public void render(float delta) {
		inputController.update();
		worldController.update(delta);
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new MainMenuScreen(game, 0));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			game.setScreen(new GameScreen(game));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			game.pauseUnpause();
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tiledMapRenderer.setView(game.camera);
		tiledMapRenderer.render();
		
		game.batch.begin();
		worldView.draw(game.batch);
		game.batch.end();
		
		game.shapeRenderer.begin(ShapeType.Line);
		worldView.draw(game.shapeRenderer);
		game.shapeRenderer.end();
		
		game.guiBatch.begin();
		guiView.draw(game.guiBatch);
		game.guiBatch.end();
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
		// TODO this seems bad....
	}
}
