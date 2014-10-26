package com.cendric.controllers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.ecs.Entity;
import com.cendric.ecs.systems.AccelerationSystem;
import com.cendric.ecs.systems.CendricAnimationSystem;
import com.cendric.ecs.systems.CendricCollisionSystem;
import com.cendric.ecs.systems.CendricInputSystem;
import com.cendric.ecs.systems.MovementSystem;
import com.cendric.ecs.systems.SpellAnimationSystem;
import com.cendric.ecs.systems.SpellCollisionSystem;
import com.cendric.models.Level;
import com.cendric.models.Spell;
public class WorldController {
	
	// TODO [tiz] lots of rewriting...

	private CendricGame game;

	private Level currentLevel;

	private ArrayList<Spell> spells;

	private Vector2 mousePosition;
	
	private InputController input;
	
	private CendricInputSystem playerInput;
	private AccelerationSystem acceleration;
	private CendricAnimationSystem cendricAnimation;
	private SpellAnimationSystem spellAnimation;
	private CendricCollisionSystem cendricCollision;
	private SpellCollisionSystem spellCollision;
	private MovementSystem movement;

	public WorldController(CendricGame game, Level level, InputController input) {
		this.game = game;
		this.input = input;
		currentLevel = level;
		
		// TODO: maybe find a better place for this?
//		Vector3 mouseStartWorld = new Vector3(character.getPosition().x + 2*Constants.TILE_SIZE, character.getPosition().y + Constants.TILE_SIZE, 0);
//		System.out.println(mouseStartWorld);
//		Vector3 mousStartScreen = game.camera.unproject(mouseStartWorld);
//		Gdx.input.setCursorPosition(Math.round(mousStartScreen.x), Math.round(mousStartScreen.y));

		spells = new ArrayList<Spell>();
		
		playerInput = new CendricInputSystem(input, level);
		acceleration = new AccelerationSystem();
		cendricAnimation = new CendricAnimationSystem();
		spellAnimation = new SpellAnimationSystem();
		
		cendricCollision = new CendricCollisionSystem(level);
		spellCollision = new SpellCollisionSystem(level);
		movement = new MovementSystem();
		
	}

	public void update(float delta) {
		List<Entity> entities = currentLevel.getEntities();
		
		// Note: the update order is somewhat important
		
		playerInput.update(entities, delta);
		acceleration.update(entities, delta);
		
		spellCollision.update(entities, delta);
		cendricCollision.update(entities, delta);
		
		cendricAnimation.update(entities, delta);
		spellAnimation.update(entities, delta);
		
		movement.update(entities, delta);
		
		currentLevel.updateEntityArray();
		
		/*
		processInput();
		checkPlayerCollisionWithBlocks(delta);
		character.update(delta);

		game.camera.position.x = character.getPosition().x;
		game.camera.position.y = character.getPosition().y;
		if (character.getPosition().x < Constants.WINDOW_WIDTH / 2) {
			game.camera.position.x = Constants.WINDOW_WIDTH / 2;
		}
		if (character.getPosition().x > currentLevel.getLevelRect().width
				- Constants.WINDOW_WIDTH / 2) {
			game.camera.position.x = currentLevel.getLevelRect().width
					- Constants.WINDOW_WIDTH / 2;
		}
		if (character.getPosition().y < Constants.WINDOW_HEIGHT / 2) {
			game.camera.position.y = Constants.WINDOW_HEIGHT / 2;
		}
		if (character.getPosition().y > currentLevel.getLevelRect().height
				- Constants.WINDOW_HEIGHT / 2) {
			game.camera.position.y = currentLevel.getLevelRect().height
					- Constants.WINDOW_HEIGHT / 2;
		}
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.shapeRenderer.setProjectionMatrix(game.camera.combined);

		if (checkIsDead()) {
			game.setScreen(new GameOverScreen(game));
		}

		ArrayList<Spell> toDelete = new ArrayList<Spell>();
		for (Spell spell : spells) {
			spell.update(delta);
			checkSpellCollisions(spell);
			if (checkSpellCollisionWithBlocks(spell)
					|| !spell.getCollisionBox().overlaps(
							currentLevel.getLevelRect())) {
				toDelete.add(spell);
			}
		}
		spells.removeAll(toDelete);

		if (playerHasReachedGargoyle()) {
			currentLevel.finish();
			game.finishCurrentLevel();
			game.setScreen(new SuccessScreen(game));
		}
		*/
	}

	/*
	private boolean checkIsDead() {
		if (character.getPosition().y < -1.5 * Constants.TILE_SIZE)
			return true;
		List<Rectangle> evilTiles = currentLevel.getEvilTiles();
		for (Rectangle rect : evilTiles) {
			if (character.getCollisionBox().overlaps(rect)) {
				return true;
			}
		}
		return false;
	}
	*/

//	private void checkSpellCollisions(Spell spell) {
//		List<DynamicTile> tiles = currentLevel.getDynamicTiles();
//		for (DynamicTile tile : tiles) {
//			if (spell.getCollisionBox().overlaps(tile.getRect())) {
//				tile.hit(spell.spell);
//			}
//		}
//	}

	boolean checkSpellCollisionWithBlocks(Spell spell) {
		List<Rectangle> collidableTiles = currentLevel.getCollidableTiles();
		for (Rectangle rect : collidableTiles) {
			if (spell.getCollisionBox().overlaps(rect)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	boolean playerHasReachedGargoyle() {
		return character.getCollisionBox().overlaps(
				currentLevel.getGargoyle().getCollisionBox());
	}
	*/

	/*
	private void checkPlayerCollisionWithBlocks(float delta) {
		List<Rectangle> collidableTiles = currentLevel.getCollidableTiles();

		Rectangle collisionBox = character.getCollisionBox();
		Vector2 offset = character.getCollisionBoxOffset();

		Vector2 nextVelocity = character.nextVelocity(delta);
		Vector2 nextPosition = character.nextPosition(nextVelocity, delta);

		Rectangle nextRect = new Rectangle(collisionBox);

		// Check x direction
		nextRect.x = nextPosition.x + offset.x;

		if (!nextRect.overlaps(currentLevel.getLevelRect())) {
			character.willCollideX();
		}

		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				character.willCollideX();
				break;
			}
		}

		nextRect.x = collisionBox.x;

		// Check y direction
		nextRect.y = nextPosition.y + offset.y;

		if (!nextRect.overlaps(currentLevel.getLevelRect())) {
			character.willCollideY();
		}

		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				character.willCollideY();
				break;
			}
		}
	}
	*/
	
	// The following methods are used by the world view
	public Vector2 getMousPosition() {
		return mousePosition;
	}

	/*
	public Vector2 getStaffPosition() {
		Vector2 staffPosition;
		if (character.isFacingLeft) {
			staffPosition = character.getPosition().cpy().add(14, 100);
		} else {
			staffPosition = character.getPosition().cpy().add(50, 100);
		}
		return staffPosition;
	}
	*/
	
	public List<Spell> getSpells() {
		return spells;
	}

	// The following methods are input handling
	public void setMousePosition(Vector2 mousePos) {
		mousePosition = mousePos;
	}
	
	/*
	private void processInput() {
		if (keys.get(Key.SPELL_FIRE)) {
			character.spell = SpellId.FIRE;
		}
		if (keys.get(Key.SPELL_ICE)) {
			character.spell = SpellId.ICE;
		}
		if (keys.get(Key.JUMP)) {
			character.jump();
		}
		if (keys.get(Key.LEFT)) {
			character.moveLeft();
		} else if (keys.get(Key.RIGHT)) {
			character.moveRight();
		}
		if (keys.get(Key.FIRE)) {
			Vector2 staffPosition = getStaffPosition();
			Vector2 direction = mousePosition.cpy().sub(staffPosition);
			Spell spell = new Spell(staffPosition, direction, character.spell);
			spells.add(spell);
		}
	}
	*/
}
