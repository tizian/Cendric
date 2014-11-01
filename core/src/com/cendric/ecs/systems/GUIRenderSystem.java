package com.cendric.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.controllers.InputController;
import com.cendric.controllers.Key;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.CendricSpellsComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.SpellStateComponent.SpellType;

public class GUIRenderSystem extends RenderSystem {
	
	private CendricGame game;
	private InputController input;
	
	public GUIRenderSystem(CendricGame game, InputController input) {
		this.game = game;
		this.input = input;
	}

	@Override
	protected void render(Entity entity, SpriteBatch batch, float stateTime) {
		if (!entity.tag.equals("Cendric")) return;
		CendricSpellsComponent cp = (CendricSpellsComponent) entity.getComponent(ComponentType.CendricSpells);
		if (cp == null) return;
		
		if (game.isPaused()) {
			batch.draw(Resources.blackOverlay, 0, 0);
			
			Resources.font.setScale(6);
			Resources.font.drawWrapped(game.guiBatch, "SPELL SELECTION", 0, 700, Constants.WINDOW_WIDTH, HAlignment.CENTER);
			
			// TODO DEBUG MODE
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
				cp.learnSpell(SpellType.FIRE);
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
				cp.unlearnSpell();
			}
			
			float slotRadius = Resources.spellSlot.getWidth() * 1.7f;
			float circleR;
			
			if (cp.numberOfKnownSpells() == 1) {
				circleR = 0;
			}
			else {
				circleR = Constants.WINDOW_HEIGHT / 8 + cp.numberOfKnownSpells() * 12;
			}
			
			float startTheta = (float) (Math.PI / 2);
			float deltaTheta = (float) (2*Math.PI / cp.numberOfKnownSpells());
			Vector2 center = new Vector2(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
			
			Vector2 direction = input.getMousePosition().cpy().sub(center);
			if (direction.len() > 50) {
				direction.nor().scl(20);
				input.setMousePosition(center.cpy().add(direction));
				direction = input.getMousePosition().cpy().sub(center);
			}
			
			float angle = (direction.angle() - 90.0f);
			if (angle < 0) angle += 360.0f;
			
			int selectedIndex = round(angle, 360.0f/cp.numberOfKnownSpells());
			
			cp.setCurrentSpellIndex(selectedIndex);
			
			for (int i = 0; i < cp.numberOfKnownSpells(); i++) {
				Vector2 pos = new Vector2(center.x + (float)Math.cos(startTheta + i * deltaTheta) * circleR, center.y + (float)Math.sin(startTheta + i *deltaTheta) * circleR);
				
				if (i == cp.activeSpellIndex()) {
					drawCircle(batch, Resources.spellSlotSelected, pos.x, pos.y, slotRadius);
				}
				else {
					drawCircle(batch, Resources.spellSlot, pos.x, pos.y, slotRadius);
				}
				
				drawCircle(batch, Resources.getSpellAnimation(cp.spellTypeForIndex(i)).getKeyFrame(stateTime, true).getTexture(), pos.x, pos.y, 20);
				
				Resources.font.setScale(1.8f);
				Resources.font.drawWrapped(game.guiBatch, cp.spellTypeForIndex(i).name(), pos.x - 50, pos.y - 2.6f*18, 100, HAlignment.CENTER);
				String number = String.valueOf(i+1);
				if (i == 9) number = "0";
				Resources.font.drawWrapped(game.guiBatch, number, pos.x-50.5f, pos.y + slotRadius - 5, 100, HAlignment.CENTER);
			}
			
				input.getScrollAmount();	// resets internal scroll amount
			
			if (input.isKeyPressed(Key.CAST) || input.isKeyPressed(Key.ESC)) {
				game.pauseUnpause();
			}
		}
	}
	
	private void drawCircle(SpriteBatch batch, Texture tex, float x, float y, float r) {
		batch.draw(tex, x-r, y-r, 2*r, 2*r);
	}
	
	int round(double i, float v){
	    return (int) (Math.round(i/v));
	}
}
