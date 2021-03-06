package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;

public class AboutScreen implements Screen {
	
	private CendricGame game;
	
	private Color purple;
	
	public AboutScreen(CendricGame game) {
		this.game = game;
		
		purple = new Color(114/255f, 87/255f, 126/255f, 1f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		
		Resources.font.setScale(7);
		Resources.font.setColor(purple);
		Resources.font.drawWrapped(game.batch, "ABOUT", Constants.WINDOW_WIDTH/2 - 400, 675, 800, HAlignment.CENTER);
		
		Resources.font.setScale(5);
		Resources.font.setColor(Color.WHITE);
		Resources.font.drawWrapped(game.batch, "CENDRIC IS AWESOME <3", 0, 400, 1280, HAlignment.CENTER);
		
		game.batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new MainMenuScreen(game, 2));
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
