package com.cendric.ecs.components;

public class MovementComponent extends Component {
	
	public final float GRAVITY = 1500;
	
	public float MAX_VELOCITY = 300;
	public float TERMINAL_VELOCITY = -1000;
	
	public float JUMP_SPEED = 920;
	public float JUMP_SPEED_CUTOFF = 400;
	
	public float ACCELERATION = 1200;
	
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
