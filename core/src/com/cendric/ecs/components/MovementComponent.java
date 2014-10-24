package com.cendric.ecs.components;

public class MovementComponent extends Component {
	
	public final float MAX_VELOCITY = 2;
	public final float TERMINAL_VELOCITY = 3;
	
	public final float ACCELERATION = 1;
	public final float GRAVITY = 2;
	
	public float vxTarget;
	public float vyTarget;
	
	public float vx;
	public float vy;

	@Override
	public ComponentType getType() {
		return ComponentType.Movement;
	}

}
