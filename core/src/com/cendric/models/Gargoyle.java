package com.cendric.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.Resources;

public class Gargoyle {
	
	protected Vector2 position;
	protected TextureRegion textureRegion;
	
	private Rectangle collisionBox;
	private Vector2 offset;
	
	public Gargoyle(Vector2 startPosition) {
		position = new Vector2(startPosition.x - 8, startPosition.y);
		textureRegion = new TextureRegion(Resources.gargoyle);
	}
	
	public void setCollisionBox(Vector2 offset, float width, float height) {
		this.offset = offset;
		collisionBox = new Rectangle(offset.x, offset.y, width, height);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	public Rectangle getCollisionBox() {
		collisionBox.x = position.x + offset.x;
		collisionBox.y = position.y + offset.y;
		return collisionBox;
	}

}
