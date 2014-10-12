package com.hackzurich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.hackzurich.Constants;
import com.hackzurich.GargoyleGame;

public abstract class SplashScreen implements Screen {
	
	protected GargoyleGame game;
	
	public SplashScreen(GargoyleGame game) {
		this.game = game;
	}
	
	public abstract Texture getTexture();
	
	public abstract Screen nextScreen();

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		game.batch.draw(getTexture(), 0, 0);
		game.batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			game.setScreen(nextScreen());
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
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
