package com.cendric.ecs.components;

import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends Component {
	public String getName() {
		return "Position";
	}
	
	public Vector2 p;
	
	public PositionComponent() {
		this.p = new Vector2();
	}
	
	public PositionComponent(Vector2 p) {
		this.p = p;
	}
	
	public PositionComponent(float x, float y) {
		this.p = new Vector2(x, y);
	}
}