package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;

public class TutorialScreen implements Screen {
	
	private CendricGame game;
	private Screen lastScreen;
	private Texture tutorialTexture;
	private BitmapFont font;
	
	public TutorialScreen(CendricGame game, Screen lastScreen) {
		this.game = game;
		this.lastScreen = lastScreen;
		tutorialTexture = Resources.tutorialScreen;
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		font.setColor(Color.WHITE);
		font.setScale(4);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		game.batch.draw(tutorialTexture, 0, 0);
		
		font.draw(game.batch, "BITMAP FONT TEST BLA BLA", 10, Constants.WINDOW_HEIGHT - 30);
		
		game.batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
			game.setScreen(lastScreen);
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
		font.dispose();
	}
}
