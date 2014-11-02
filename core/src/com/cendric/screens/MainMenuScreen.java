package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;

public class MainMenuScreen implements Screen {
	
	private CendricGame game;
	
	private Texture titleText;
	private Texture titleCendric;
	private Animation casino;
	
	private Color purple;
	
	private int selection;
	private float stateTime;
	private final float ANIMATION_TIME = 0.3f;
	
	public MainMenuScreen(CendricGame game, int selection) {
		this.game = game;
		
		// Textures
		titleText = new Texture(Gdx.files.internal("menu/CendricTitle.png"));
		titleCendric = new Texture(Gdx.files.internal("menu/CendricHero.png"));
		
		// Animation
		Texture casino1 = new Texture(Gdx.files.internal("menu/CasinoEdition1.png"));
		Texture casino2 = new Texture(Gdx.files.internal("menu/CasinoEdition2.png"));
		TextureRegion[] casinoRegion = new TextureRegion[2];
		casinoRegion[0] = new TextureRegion(casino1);
		casinoRegion[1] = new TextureRegion(casino2);
		casino = new Animation(ANIMATION_TIME, casinoRegion);
		
		purple = new Color(114/255f, 87/255f, 126/255f, 1f);
		
		this.selection = selection;
		stateTime = 0.0f;
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
//        game.batch.draw(titleText, 110, 375, 749, 315);
        game.batch.draw(titleText, (1280-titleText.getWidth()*7)/2 - 80, 375, titleText.getWidth()*7, titleText.getHeight()*7);
        
        // Draw casino edition animation
        stateTime += delta;
        
        TextureRegion casinoFrame = casino.getKeyFrame(stateTime, true);
        game.batch.draw(casinoFrame, (1280-casinoFrame.getRegionWidth()/4)/2, 320, casinoFrame.getRegionWidth()/4, casinoFrame.getRegionHeight()/4);
        
        // Draw Cendric
        game.batch.draw(titleCendric, 820, -50, titleCendric.getWidth()/3, titleCendric.getHeight()/3);
        
        // Text
        Resources.font.setScale(5);
        Resources.font.setColor(Color.WHITE);
        
//        float startHeight = 390;
        float startHeight = 310;
        float deltaHeight = 60;
        
        Resources.font.draw(game.batch, "START GAME", 280, startHeight);
        Resources.font.draw(game.batch, "CONTROLS", 360, startHeight-deltaHeight);
        Resources.font.draw(game.batch, "ABOUT", 480, startHeight-2*deltaHeight);
        Resources.font.draw(game.batch, "EXIT", 520, startHeight-3*deltaHeight);
        
        Resources.font.setColor(purple);
        
        if (selection == 0) {
        	Resources.font.draw(game.batch, "START GAME", 280, startHeight);
        	Resources.font.draw(game.batch, "<", 700, startHeight);
        }
        if (selection == 1) {
        	Resources.font.draw(game.batch, "CONTROLS", 360, startHeight-deltaHeight);
        	Resources.font.draw(game.batch, "<", 700, startHeight-deltaHeight);
        }
        if (selection == 2) {
        	Resources.font.draw(game.batch, "ABOUT", 480, startHeight-2*deltaHeight);
        	Resources.font.draw(game.batch, "<", 700, startHeight-2*deltaHeight);
        }
        if (selection == 3) {
        	Resources.font.draw(game.batch, "EXIT", 520, startHeight-3*deltaHeight);
        	Resources.font.draw(game.batch, "<", 700, startHeight-3*deltaHeight);
        }
        
        Resources.font.setScale(2.4f);
        Resources.font.setColor(Color.WHITE);
        
        Resources.font.drawWrapped(game.batch, "& 2014 - ISABELLE ROESCH - TIZIAN ZELTNER", 0, 50, 1280, HAlignment.CENTER);
        
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
        		game.setScreen(new LevelSelectionScreen(game));
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
	}
}
