package com.cendric.ecs.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.controllers.InputController;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.CendricSpellsComponent;
import com.cendric.ecs.components.ComponentType;

public class GUIRenderSystem extends RenderSystem {
	
	private CendricGame game;
	private InputController input;
	
	public GUIRenderSystem(CendricGame game, InputController input) {
		this.game = game;
		this.input = input;
	}

	@Override
	protected void render(Entity entity, SpriteBatch batch) {
		if (!entity.tag.equals("Cendric")) return;
		CendricSpellsComponent cp = (CendricSpellsComponent) entity.getComponent(ComponentType.CendricSpells);
		if (cp == null) return;
		
		float r = Resources.spellSlot.getWidth() * 1.2f;
		
		if (game.isPaused()) {
			batch.draw(Resources.blackOverlay, 0, 0);

			float circleR = Constants.WINDOW_HEIGHT / 4;
			float startTheta = (float) (Math.PI / 2);
			float deltaTheta = (float) (2*Math.PI / cp.numberOfKnownSpells());
			Vector2 center = new Vector2(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
			
			Vector2 direction = input.getMousePosition().cpy().sub(center);
			float angle = direction.angle() - 90.0f;
			
			int selectedIndex = round(angle, (int) (360.0f/cp.numberOfKnownSpells()));
			
			System.out.println(input.getMousePosition().x + ", " + input.getMousePosition().y + ", " + selectedIndex);
			cp.setCurrentSpellIndex(selectedIndex);
			
			
			for (int i = 0; i < cp.numberOfKnownSpells(); i++) {
				Vector2 pos = new Vector2(center.x + (float)Math.cos(startTheta + i * deltaTheta) * circleR, center.y + (float)Math.sin(startTheta + i *deltaTheta) * circleR);
				
				if (i == cp.activeSpellIndex()) {
					drawCircle(batch, Resources.spellSlotSelected, pos.x, pos.y, 1.2f*r);
				}
				else {
					drawCircle(batch, Resources.spellSlot, pos.x, pos.y, 1.2f*r);
				}
				
				if (i % 2 == 0) {
					drawCircle(batch, Resources.spellFire1, pos.x, pos.y, 1.2f*18);
				}
				else if (i % 2 == 1) {
					drawCircle(batch, Resources.spellIce1, pos.x, pos.y, 1.2f*18);
				}
			}
		}
	}
	
	private void drawCircle(SpriteBatch batch, Texture tex, float x, float y, float r) {
		batch.draw(tex, x-r, y-r, 2*r, 2*r);
	}
	
	int round(double i, int v){
	    return (int) (Math.round(i/v));
	}
}
