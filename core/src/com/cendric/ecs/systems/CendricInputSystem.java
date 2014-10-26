package com.cendric.ecs.systems;

import com.badlogic.gdx.math.Vector2;
import com.cendric.controllers.InputController;
import com.cendric.controllers.Key;
import com.cendric.ecs.Entity;
import com.cendric.ecs.EntityFactory;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.components.SpellStateComponent;
import com.cendric.ecs.components.SpellStateComponent.SpellType;
import com.cendric.models.Level;

public class CendricInputSystem extends UpdateSystem {
	
	private InputController inputController;
	private Level level;
	
	private boolean castPossible;
	
	public CendricInputSystem(InputController inputController, Level level) {
		this.inputController = inputController;
		this.level = level;
		castPossible = true;
	}

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Cendric")) return;
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		SpellStateComponent sp = (SpellStateComponent) entity.getComponent(ComponentType.SpellState);
		AnimationStateComponent as = (AnimationStateComponent) entity.getComponent(ComponentType.AnimationState);
		if (pos == null) return;
		if (mov == null) return;
		if (sp == null) return;
		if (as == null) return;
		
		if (inputController.isKeyPressed(Key.LEFT)) {
			mov.vxTarget = -mov.MAX_VELOCITY;
		}
		else if (inputController.isKeyPressed(Key.RIGHT)) {
			mov.vxTarget = mov.MAX_VELOCITY;
		}
		else {
			mov.vxTarget = 0;
		}
		
		if (inputController.isKeyPressed(Key.JUMP) && mov.grounded) {
			mov.vy = mov.JUMP_SPEED;
			mov.grounded = false;
		}
		else if (!inputController.isKeyPressed(Key.JUMP) && !mov.grounded) {
			if (mov.vy > mov.JUMP_SPEED_CUTOFF) {
				mov.vy = mov.JUMP_SPEED_CUTOFF;
			}
		}

		if (mov.grounded) {
			mov.vyTarget = 0;
		}
		else {
			mov.vyTarget = mov.TERMINAL_VELOCITY;
		}
		
		if (inputController.isKeyPressed(Key.NUM_1)) {
			sp.spellType = SpellType.FIRE;
		}
		else if (inputController.isKeyPressed(Key.NUM_2)) {
			sp.spellType = SpellType.ICE;
		}
		
		if (inputController.isKeyPressed(Key.CAST) && castPossible) {
			Vector2 staffPosition = new Vector2();
			if (as.facingLeft) {
				staffPosition = new Vector2(pos.x + 14, pos.y + 100);
			}
			else {
				staffPosition = new Vector2(pos.x + 50, pos.y + 100);
			}
			Vector2 mousePosition = inputController.getMousePosition();
			Vector2 dir = mousePosition.sub(staffPosition).nor().scl(500);
			level.addEntity(EntityFactory.createSpellCast(sp.spellType, staffPosition.x, staffPosition.y, dir.x, dir.y));
			castPossible = false;
		}
		else if(!inputController.isKeyPressed(Key.CAST)) {
			castPossible = true;
		}
	}
}