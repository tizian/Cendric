package com.cendric.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.cendric.CendricGame;
import com.cendric.Resources;

public class TitleScreen extends SplashScreen {
	public TitleScreen(CendricGame game) {
		super(game);
	}

	@Override
	public Texture getTexture() {
		return Resources.titleScreen;
	}
	
	@Override
	public Screen nextScreen() {
		return new GameScreen(game);
	}
}
