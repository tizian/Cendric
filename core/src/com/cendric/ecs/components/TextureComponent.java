package com.cendric.ecs.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent extends Component {
	
	public TextureRegion texture;
	
	@Override
	public ComponentType getType() {
		return ComponentType.Texture;
	}
	
	public TextureComponent(TextureRegion texture) {
		this.texture = texture;
	}
}
