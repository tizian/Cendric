package com.hackzurich.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	protected float stateTime;
	
	public boolean isFacingLeft;

	protected float MAX_VELOCITY_X = 10000.0f;
	protected float MAX_VELOCITY_Y = 1000.0f;

	protected float WALK_ACCELERATION = 5000.0f;
	protected float GRAVITY_ACCELERATION = -800.0f;
	
	protected float JUMP_BOOST = 700.0f;

	protected float DAMPING = 0.7f;

	protected boolean willCollide = false;
	protected boolean grounded = false;

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;

	protected TextureRegion textureRegion;
	private Rectangle collisionBox;
	private Vector2 offset;

	public GameObject(Vector2 startPosition) {
		stateTime = 0f;
		position = startPosition.cpy();
		velocity = new Vector2();
		acceleration = new Vector2();
		
		acceleration.y = GRAVITY_ACCELERATION;
	}

	public GameObject() {
		position = new Vector2();
		velocity = new Vector2();
		acceleration = new Vector2();
		
		acceleration.y = GRAVITY_ACCELERATION;
	}

	public void jump() {
		if (grounded && velocity.y >= 0) {
			grounded = false;
			velocity.y = JUMP_BOOST;
		}
	}

	public void moveLeft() {
		isFacingLeft = true;
		acceleration.x = -WALK_ACCELERATION;
	}

	public void moveRight() {
		isFacingLeft = false;
		acceleration.x = WALK_ACCELERATION;
	}

	public void willCollideX() {
		velocity.x = 0;
		acceleration.x = 0;
	}

	public void willCollideY() {
		willCollide = true;
		if (velocity.y < 0) {
			grounded = true;
		}
		velocity.y = 0;
	}

	public void update(float delta) {
		stateTime += delta;
		velocity = nextVelocity(delta);
		position = nextPosition(velocity, delta);
		
		willCollide = false;

		// Default value
		acceleration.x = 0;
	}
	
	public Vector2 nextVelocity(float delta) {
		Vector2 nextVelocity = new Vector2();
		
		nextVelocity.x = (velocity.x + acceleration.x * delta) * DAMPING;
		
		if (!willCollide) {
			nextVelocity.y = velocity.y + acceleration.y * delta;
		}
		if (velocity.y < -MAX_VELOCITY_Y) {
			nextVelocity.y = -MAX_VELOCITY_Y;
		}
		if (velocity.y > MAX_VELOCITY_Y) {
			nextVelocity.y = MAX_VELOCITY_Y;
		}
		if (velocity.x < -MAX_VELOCITY_X) {
			nextVelocity.x = -MAX_VELOCITY_X;
		}
		if (velocity.x > MAX_VELOCITY_X) {
			nextVelocity.x = MAX_VELOCITY_X;
		}
		
		return nextVelocity;
	}
	
	public Vector2 nextPosition(Vector2 nextVelocity, float delta) {
		Vector2 nextPosition = new Vector2();
		
		nextPosition.x = position.x + nextVelocity.x * delta;
		nextPosition.y = position.y + nextVelocity.y * delta;
		nextPosition.x = Math.round(nextPosition.x);
		nextPosition.y = Math.round(nextPosition.y);
		
		return nextPosition;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
		this.offset = new Vector2(0, 0);
		this.collisionBox = new Rectangle(position.x, position.y,
				textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}

	public void setCollisionBox(Vector2 offset, float width, float height) {
		this.offset = offset;
		collisionBox = new Rectangle(offset.x, offset.y, width, height);
	}

	public Rectangle getCollisionBox() {
		collisionBox.x = position.x + offset.x;
		collisionBox.y = position.y + offset.y;
		return collisionBox;
	}
	
	public Vector2 getCollisionBoxOffset() {
		return offset;
	}
}
