package com.cendric.ecs.components;

import com.badlogic.gdx.math.Rectangle;

public class BoundingBoxComponent extends Component {
	
	public Rectangle boundingBox;
	public boolean active;
	
	public BoundingBoxComponent(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
		active = true;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.BoundingBox;
	}
}
