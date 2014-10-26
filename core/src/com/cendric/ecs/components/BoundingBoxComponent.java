package com.cendric.ecs.components;

import com.badlogic.gdx.math.Rectangle;

public class BoundingBoxComponent extends Component {
	
	public Rectangle boundingBox;
	
	public BoundingBoxComponent(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.BoundingBox;
	}
}
