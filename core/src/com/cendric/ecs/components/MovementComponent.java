package com.cendric.ecs.components;

public class MovementComponent extends Component {
	
	public final float GRAVITY = 800;
	
	public float MAX_VELOCITY = 200;
	public float TERMINAL_VELOCITY = -1000;
	
	public float JUMP_SPEED = 700;
	public float JUMP_SPEED_CUTOFF = 400;
	
	public float ACCELERATION = 400;
	
	public float vxTarget;
	public float vyTarget;
	
	public float vx;
	public float vy;
	
	public boolean grounded;

	@Override
	public ComponentType getType() {
		return ComponentType.Movement;
	}
}
