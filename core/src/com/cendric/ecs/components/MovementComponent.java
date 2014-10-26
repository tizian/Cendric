package com.cendric.ecs.components;

public class MovementComponent extends Component {
	
	public final float GRAVITY = -100;
	
	public float MAX_VELOCITY = 200;
	public float TERMINAL_VELOCITY = 100;
	
	public float ACCELERATION = 400;
	
	public float vxTarget;
	public float vyTarget;
	
	public float vx;
	public float vy;

	@Override
	public ComponentType getType() {
		return ComponentType.Movement;
	}
}
