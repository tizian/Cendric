package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.cendric.CendricGame;
import com.cendric.Constants;

public class AboutScreen implements Screen {
	
	private CendricGame game;
	
	private BitmapFont font;
	private Color purple = new Color(114/255f, 87/255f, 126/255f, 1f);
	
	public AboutScreen(CendricGame game) {
		this.game = game;
		
		// Font
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		font.setColor(Color.WHITE);
		font.setScale(5);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		
		font.setScale(7);
		font.setColor(purple);
		font.draw(game.batch, "ABOUT", 80, 675);
		
		font.setScale(5);
		font.setColor(Color.WHITE);
		font.drawWrapped(game.batch, "CENDRIC IS AWESOME <3", 0, 400, 1280, HAlignment.CENTER);
		
		game.batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
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
		font.dispose();
	}
}
