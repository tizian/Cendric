package com.cendric.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.cendric.CendricGame;
import com.cendric.Resources;

public class SuccessScreen extends SplashScreen {

	public SuccessScreen(CendricGame game) {
		super(game);
	}

	@Override
	public Texture getTexture() {
		return Resources.successScreen;
	}

	@Override
	public Screen nextScreen() {
		if (game.getCurrentLevel() > Resources.levelCount) {
			game.setCurrentLevel(1);
			return new TitleScreen(game);
		} else {
			return new GameScreen(game);
		}
	}
}