package com.cendric.ecs.components;

public class AnimationStateComponent extends Component {
	
	public float animationTime;	// TODO Animation abstraction class that includes this. Goal: no specific XAnimationSystem classes for each entity type...
	public boolean facingLeft;
	public State state;
	
	public static enum State {
		IDLE, WALKING, JUMPING
	}
	
	public AnimationStateComponent() {
		animationTime = 0;
		facingLeft = false;
		state = State.IDLE;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.AnimationState;
	}

}
