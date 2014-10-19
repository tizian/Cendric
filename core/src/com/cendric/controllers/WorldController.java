package com.cendric.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.models.DynamicTile;
import com.cendric.models.Level;
import com.cendric.models.MainCharacter;
import com.cendric.models.Spell;
import com.cendric.models.Spell.SpellId;
import com.cendric.screens.GameOverScreen;
import com.cendric.screens.SuccessScreen;

public class WorldController {

	private CendricGame game;

	public enum Key {
		LEFT, RIGHT, JUMP, FIRE, SPELL_FIRE, SPELL_ICE
	}

	private Level currentLevel;
	private MainCharacter character;

	private ArrayList<Spell> spells;

	private Vector2 mousePosition;

	public WorldController(CendricGame game, Level level) {
		this.game = game;
		currentLevel = level;
		character = level.getMainCharacter();

		spells = new ArrayList<Spell>();
	}

	public void update(float delta) {
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
	}

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

	private void checkSpellCollisions(Spell spell) {
		List<DynamicTile> tiles = currentLevel.getDynamicTiles();
		for (DynamicTile tile : tiles) {
			if (spell.getCollisionBox().overlaps(tile.getRect())) {
				tile.hit(spell.spell);
			}
		}
	}

	boolean checkSpellCollisionWithBlocks(Spell spell) {
		List<Rectangle> collidableTiles = currentLevel.getCollidableTiles();
		for (Rectangle rect : collidableTiles) {
			if (spell.getCollisionBox().overlaps(rect)) {
				return true;
			}
		}
		return false;
	}
	
	boolean playerHasReachedGargoyle() {
		return character.getCollisionBox().overlaps(
				currentLevel.getGargoyle().getCollisionBox());
	}

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
	
	// The following methods are used by the world view
	public Vector2 getMousPosition() {
		return mousePosition;
	}

	public Vector2 getStaffPosition() {
		Vector2 staffPosition;
		if (character.isFacingLeft) {
			staffPosition = character.getPosition().cpy().add(14, 100);
		} else {
			staffPosition = character.getPosition().cpy().add(50, 100);
		}
		return staffPosition;
	}
	
	public List<Spell> getSpells() {
		return spells;
	}

	// The following methods are input handling
	public void setMousePosition(Vector2 mousePos) {
		mousePosition = mousePos;
	}
	
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

	private static Map<Key, Boolean> keys = new HashMap<WorldController.Key, Boolean>();
	static {
		keys.put(Key.LEFT, false);
		keys.put(Key.RIGHT, false);
		keys.put(Key.JUMP, false);
		keys.put(Key.FIRE, false);
		keys.put(Key.SPELL_FIRE, false);
		keys.put(Key.SPELL_ICE, false);
	};
	
	public void keyPressed(Key key, boolean pressed) {
		keys.get(keys.put(key, pressed));
	}
}
