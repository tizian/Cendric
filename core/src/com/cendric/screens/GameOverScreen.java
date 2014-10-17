package com.cendric.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.cendric.CendricGame;
import com.cendric.Resources;

public class GameOverScreen extends SplashScreen {
	
	public GameOverScreen(CendricGame game) {
		super(game);
	}

	@Override
	public Texture getTexture() {
		return Resources.gameoverScreen;
	}
	
	@Override
	public Screen nextScreen() {
		return new GameScreen(game);
	}

}
