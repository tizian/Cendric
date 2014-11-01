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
import com.cendric.Resources;

public class ControlsScreen implements Screen {
	
	private CendricGame game;
	
	private Color purple = new Color(114/255f, 87/255f, 126/255f, 1f);
	
	public ControlsScreen(CendricGame game) {
		this.game = game;
		
		// Font
		Resources.font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		Resources.font.setColor(Color.WHITE);
		Resources.font.setScale(5);
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
		Resources.font.draw(game.batch, "CONTROLS", 80, 675);
		
		Resources.font.setScale(5);
		Resources.font.setColor(Color.WHITE);
		Resources.font.drawMultiLine(game.batch, "A / D\nSPACE\nTAB\nNUMBER KEYS\nMOUSE\nMOUSE BUTTON", 80, 550, 500, HAlignment.RIGHT);
		Resources.font.drawMultiLine(game.batch, "-\n-\n-\n-\n-", 580, 550, 80, HAlignment.CENTER);
		Resources.font.drawMultiLine(game.batch, "MOVE\nJUMP\nSPELL SELECTION\nTOGGLE SPELL\nAIM SPELL\nCAST SPELL\n", 660, 550, 500, HAlignment.LEFT);
		
		
		game.batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			game.setScreen(new MainMenuScreen(game, 1));
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
