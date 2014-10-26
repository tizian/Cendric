package com.cendric.ecs.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent extends Component {
	
	public TextureRegion texture;
	public TextureRegion textureOverlay;
	
	@Override
	public ComponentType getType() {
		return ComponentType.Texture;
	}
}
