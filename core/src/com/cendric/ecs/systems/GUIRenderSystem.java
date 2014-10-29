package com.cendric.ecs.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cendric.CendricGame;
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
		
		if (game.isPaused()) {
			// Stuff
		}
		
		float r = Resources.spellSlot.getWidth() * 1.6f;
		
		for (int i = 0; i < cp.numberOfKnownSpells(); i++) {
			if (i == cp.activeSpellIndex()) {
				drawCircle(batch, Resources.spellSlotSelected, 80+i*2.5f*r, 700 - r, r);
			}
			else {
//				batch.draw(Resources.spellSlot, 20 + i*(20+size), 700 - size, size, size);
				drawCircle(batch, Resources.spellSlot, 80+i*2.5f*r, 700 - r, r);
			}
			
			if (i == 0) {
				drawCircle(batch, Resources.spellFire1, 80+i*2.5f*r, 700 - r, 20);
			}
			else if (i == 1) {
				drawCircle(batch, Resources.spellIce1, 80+i*2.5f*r, 700 - r, 20);
			}
			
		}
		
	}
	
	private void drawCircle(SpriteBatch batch, Texture tex, float x, float y, float r) {
		batch.draw(tex, x-r, y-r, 2*r, 2*r);
	}

}
