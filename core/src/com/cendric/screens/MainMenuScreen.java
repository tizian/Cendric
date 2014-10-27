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

public class MainMenuScreen implements Screen {
	
	private CendricGame game;
	
	private Texture titleText;
	private Texture titleCendric;
	
	private BitmapFont font;
	private Color purple = new Color(114/255f, 87/255f, 126/255f, 1f);
	
	private int selection;
	
	public MainMenuScreen(CendricGame game, int selection) {
		this.game = game;
		
		// Textures
		titleText = new Texture(Gdx.files.internal("sprites/CendricTitle.png"));
		titleCendric = new Texture(Gdx.files.internal("sprites/CendricHero.png"));
		
		// Font
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
		font.setColor(Color.WHITE);
		font.setScale(5);
		
		this.selection = selection;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        
        game.batch.begin();
        
        // Draw title text
        game.batch.draw(titleText, 110, 320);
        
        // Draw Cendric
        game.batch.draw(titleCendric, 860, 25);
        
        // Text
        font.setColor(Color.WHITE);
        
        font.draw(game.batch, "START GAME", 280, 320);
        font.draw(game.batch, "CONTROLS", 360, 245);
        font.draw(game.batch, "ABOUT", 480, 170);
        font.draw(game.batch, "EXIT", 520, 95);
        
        font.setColor(purple);
        
        if (selection == 0) {
        	font.draw(game.batch, "START GAME", 280, 320);
        	font.draw(game.batch, "<", 700, 320);
        }
        if (selection == 1) {
        	font.draw(game.batch, "CONTROLS", 360, 245);
        	font.draw(game.batch, "<", 700, 245);
        }
        if (selection == 2) {
        	font.draw(game.batch, "ABOUT", 480, 170);
        	font.draw(game.batch, "<", 700, 170);
        }
        if (selection == 3) {
        	font.draw(game.batch, "EXIT", 520, 95);
        	font.draw(game.batch, "<", 700, 95);
        }
        
        game.batch.end();
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
        	selection++;
        	if (selection > 3) selection = 0;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
        	selection--;
        	if (selection < 0) selection = 3;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	if (selection == 0) {
        		game.setScreen(new GameScreen(game));
        	}
        	else if (selection == 1) {
        		game.setScreen(new ControlsScreen(game));
        	}
			else if (selection == 2) {
				game.setScreen(new AboutScreen(game));
			}
			else if (selection == 3) {
				Gdx.app.exit();
			}
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
		titleText.dispose();
		titleCendric.dispose();
		font.dispose();
	}
}
