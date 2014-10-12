package com.hackzurich.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.hackzurich.GargoyleGame;
import com.hackzurich.Resources;

public class TitleScreen extends SplashScreen {
	public TitleScreen(GargoyleGame game) {
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
