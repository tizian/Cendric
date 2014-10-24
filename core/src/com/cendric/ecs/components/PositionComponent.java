package com.cendric.ecs.components;

public class PositionComponent extends Component {
	
	public float x;
	public float y;
	
	@Override
	public ComponentType getType() {
		return ComponentType.Position;
	}
	
	public PositionComponent() {
		this.x = 0;
		this.y = 0;
	}

	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
}