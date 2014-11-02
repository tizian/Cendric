package com.cendric.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.ecs.components.SpellStateComponent.SpellType;

public class LevelSelectionScreen implements Screen {
	
	private CendricGame game;
	private Color purple;
	
	private Texture level1;
	private Texture level2;
	private Texture level3;
	
	private int selection;
	private float stateTime;
	
	public LevelSelectionScreen(CendricGame game) {
		this.game = game;
		
		purple = new Color(114/255f, 87/255f, 126/255f, 1f);
		
		level1 = new Texture(Gdx.files.internal("menu/level1.png"));
		level2 = new Texture(Gdx.files.internal("menu/level2.png"));
		level3 = new Texture(Gdx.files.internal("menu/level3.png"));
		
		selection = game.getCurrentLevel();
		stateTime = 0.0f;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.camera.setToOrtho(false,Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        
        stateTime += delta;
		
		game.batch.begin();
		
		Vector2 center = new Vector2(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2 + 60);
		Vector2 left = center.cpy().sub(Constants.WINDOW_WIDTH/3.5f, 0);
		Vector2 right = center.cpy().add(Constants.WINDOW_WIDTH/3.5f, 0);
		
		drawCircle(game.batch, level1, left.x, left.y, 100);
		drawCircle(game.batch, Resources.levelSlot, left.x, left.y, 150);
		
		drawCircle(game.batch, level2, center.x, center.y, 100);
		drawCircle(game.batch, Resources.levelSlot, center.x, center.y, 150);
		
		drawCircle(game.batch, level3, right.x, right.y, 100);
		drawCircle(game.batch, Resources.levelSlot, right.x, right.y, 150);
		
		Resources.font.drawWrapped(game.batch, "FOREST", left.x - 250, left.y - 150, 500, HAlignment.CENTER);
		Resources.font.drawWrapped(game.batch, "VEGAS", center.x - 250, center.y - 150, 500, HAlignment.CENTER);
		Resources.font.drawWrapped(game.batch, "CASTLE", right.x - 250, right.y - 150, 500, HAlignment.CENTER);
		
		if (selection == 1) {
			drawCircle(game.batch, Resources.levelSlotSelected, left.x, left.y, 150);
			game.batch.draw(Resources.cendricWalkingRight.getKeyFrame(stateTime, true), left.x - 32, left.y - 350);
			game.batch.draw(Resources.getStaffEffectTexture(SpellType.FIRE, stateTime, false).getTexture(), left.x - 32, left.y - 350);
		}
		else if (selection == 2) {
			drawCircle(game.batch, Resources.levelSlotSelected, center.x, center.y, 150);
			game.batch.draw(Resources.cendricWalkingRight.getKeyFrame(stateTime, true), center.x - 32, center.y - 350);
			game.batch.draw(Resources.getStaffEffectTexture(SpellType.FIRE, stateTime, false).getTexture(), center.x - 32, center.y - 350);
		}
		else if (selection == 3) {
			drawCircle(game.batch, Resources.levelSlotSelected, right.x, right.y, 150);
			game.batch.draw(Resources.cendricWalkingRight.getKeyFrame(stateTime, true), right.x - 32, right.y - 350);
			game.batch.draw(Resources.getStaffEffectTexture(SpellType.FIRE, stateTime, false).getTexture(), right.x - 32, right.y - 350);
		}
		
		Resources.font.setScale(7);
		Resources.font.setColor(purple);
		Resources.font.drawWrapped(game.batch, "LEVEL SELECTION", Constants.WINDOW_WIDTH/2 - 500, 675, 1000, HAlignment.CENTER);
		
		Resources.font.setScale(5);
		Resources.font.setColor(Color.WHITE);
		
		game.batch.end();
		
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
        	selection++;
        	if (selection > 3) selection = 1;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
        	selection--;
        	if (selection < 1) selection = 3;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        	game.setCurrentLevel(selection);
    		game.setScreen(new GameScreen(game));
        }
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new MainMenuScreen(game, 0));
		}
	}

	private void drawCircle(SpriteBatch batch, Texture tex, float x, float y, float r) {
		batch.draw(tex, x-r, y-r, 2*r, 2*r);
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
		level1.dispose();
		level2.dispose();
		level3.dispose();
	}

}
