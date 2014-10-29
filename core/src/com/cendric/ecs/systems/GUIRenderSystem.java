package com.cendric.ecs.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.Resources;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.CendricSpellsComponent;
import com.cendric.ecs.components.ComponentType;

public class GUIRenderSystem extends RenderSystem {
	
	private CendricGame game;
	
	public GUIRenderSystem(CendricGame game) {
		this.game = game;
	}

	@Override
	protected void render(Entity entity, SpriteBatch batch) {
		if (!entity.tag.equals("Cendric")) return;
		CendricSpellsComponent cp = (CendricSpellsComponent) entity.getComponent(ComponentType.CendricSpells);
		if (cp == null) return;
		
		int NUM_TEST_SPELLS = 10;
		
		float r = Resources.spellSlot.getWidth() * 1.2f;
		
		for (int i = 0; i < NUM_TEST_SPELLS; i++) {
			if (i == cp.activeSpellIndex()) {
				drawCircle(batch, Resources.spellSlotSelected, 60+i*2.5f*r, 700 - r, r);
			}
			else {
//				batch.draw(Resources.spellSlot, 20 + i*(20+size), 700 - size, size, size);
				drawCircle(batch, Resources.spellSlot, 60+i*2.5f*r, 700 - r, r);
			}
			
			if (i == 0) {
				drawCircle(batch, Resources.spellFire1, 60+i*2.5f*r, 700 - r, 18);
			}
			else if (i == 1) {
				drawCircle(batch, Resources.spellIce1, 60+i*2.5f*r, 700 - r, 18);
			}
			
		}
		
		if (game.isPaused()) {
			batch.draw(Resources.blackOverlay, 0, 0);

			float circleR = Constants.WINDOW_HEIGHT / 4;
			float startTheta = (float) (Math.PI / 2);
			float deltaTheta = (float) (2*Math.PI / NUM_TEST_SPELLS);
			Vector2 center = new Vector2(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
			
			for (int i = 0; i < NUM_TEST_SPELLS; i++) {
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

}
