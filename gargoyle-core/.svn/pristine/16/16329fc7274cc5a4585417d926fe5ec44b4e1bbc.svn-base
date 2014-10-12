package com.hackzurich.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.hackzurich.GargoyleGame;
import com.hackzurich.Resources;

public class SuccessScreen extends SplashScreen {

	public SuccessScreen(GargoyleGame game) {
		super(game);
	}

	@Override
	public Texture getTexture() {
		return Resources.successScreen;
	}
	
	@Override
	public Screen nextScreen() {
		if (!game.level2Complete) {
			return new GameScreen(game);
		}
		else {
			return new TitleScreen(game);
		}
	}
}