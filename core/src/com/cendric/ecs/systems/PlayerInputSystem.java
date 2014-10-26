package com.cendric.ecs.systems;

import com.cendric.controllers.InputController;
import com.cendric.controllers.Key;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;

public class PlayerInputSystem extends UpdateSystem {
	
	private InputController inputController;
	
	public PlayerInputSystem(InputController inputController) {
		this.inputController = inputController;
	}

	@Override
	protected void update(Entity entity, float dt) {
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		if (mov == null) return;
		
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
	}
}