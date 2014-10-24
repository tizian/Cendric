package com.cendric.ecs.systems;

import com.cendric.controllers.InputController;
import com.cendric.controllers.Key;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;

public class PlayerInputSystem extends System {
	
	private InputController inputController;
	
	public PlayerInputSystem(InputController inputController) {
		this.inputController = inputController;
	}

	@Override
	public void update(Entity entity, float dt) {
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

		// TODO [tiz] detect on platform somehow and store somewhere...
//		if (player on platform) {
//			m.vyTarget = 0;
//		}
//		else {
//			m.vyTarget = m.TERMINAL_VELOCITY;
//		}
	}
}