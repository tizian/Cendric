package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;

public class GameOverScreen implements Screen {
	
	private CendricGame game;
	private Texture titleText;
	
	private Color red;
	
	public GameOverScreen(CendricGame game) {
		this.game = game;
		
		// Textures
		titleText = new Texture(Gdx.files.internal("menu/GameOverTitle.png"));
		
		red = new Color(75/255f, 35/255f, 35/255f, 1f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(red.r, red.g, red.b, red.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        
        game.batch.begin();
        
        // Draw title text
        float width = titleText.getWidth() * 7f;
        float height = titleText.getHeight() * 7f;
        game.batch.draw(titleText, (Constants.WINDOW_WIDTH - width)/2, 250, width, height);
        

        // Text
        Resources.font.setScale(5);
        Resources.font.setColor(Color.WHITE);
        
        Resources.font.drawWrapped(game.batch, "- PRESS SPACE TO CONTINUE -", 0, 200, Constants.WINDOW_WIDTH, HAlignment.CENTER);
        
        
        game.batch.end();
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        	game.setScreen(new GameScreen(game));
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
