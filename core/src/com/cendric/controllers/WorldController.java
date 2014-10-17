package com.cendric.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.models.GameObject;
import com.cendric.models.IceTile;
import com.cendric.models.Level;
import com.cendric.models.MainCharacter;
import com.cendric.models.MainCharacter.ActiveSpell;
import com.cendric.models.Spell;
import com.cendric.models.WaterTile;
import com.cendric.screens.GameOverScreen;
import com.cendric.screens.SuccessScreen;

public class WorldController {
	
	private CendricGame game;
	
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE, SPELL_FIRE, SPELL_ICE
	}

	private Level currentLevel;
	private MainCharacter character;
	
	private boolean spellFired;
	private ArrayList<Spell> spells;
	
	public Vector2 mousePosition;

	public WorldController(CendricGame game, Level level) {
		this.game = game;
		currentLevel = level;
		character = level.mainCharacter;
		
		spellFired = true;
		spells = new ArrayList<Spell>();
	}

	public void update(float delta) {
		processInput();
		checkPlayerCollisionWithBlocks(delta);
		character.update(delta);

		game.camera.position.x = character.getPosition().x;
		game.camera.position.y = character.getPosition().y;
		if (character.getPosition().x < Constants.WINDOW_WIDTH/2) {
			game.camera.position.x = Constants.WINDOW_WIDTH/2;
		}
		if (character.getPosition().x > currentLevel.levelRect.width - Constants.WINDOW_WIDTH/2) {
			game.camera.position.x = currentLevel.levelRect.width - Constants.WINDOW_WIDTH/2;
		}
		if (character.getPosition().y < Constants.WINDOW_HEIGHT/2) {
			game.camera.position.y = Constants.WINDOW_HEIGHT/2;
		}
		if (character.getPosition().y > currentLevel.levelRect.height - Constants.WINDOW_HEIGHT/2) {
			game.camera.position.y = currentLevel.levelRect.height - Constants.WINDOW_HEIGHT/2;
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
			checkFireSpellCollisionWithIceBlocks(spell);
			checkIceSpellCollisionWithWaterBlocks(spell);
			if (checkSpellCollisionWithBlocks(spell) || !spell.getCollisionBox().overlaps(currentLevel.levelRect)) {
				toDelete.add(spell);
			}
		}
		spells.removeAll(toDelete);
		
		if (playerHasReachedGargoyle()) {
			if (!game.level1Complete) {
				game.level1Complete = true;
			}
			else if (!game.level2Complete) {
				game.level2Complete = true;
			}
			game.setScreen(new SuccessScreen(game));
		}
	}
	
	private boolean checkIsDead() {
		if (character.getPosition().y < -1.5*Constants.TILE_SIZE) return true;
		ArrayList<Rectangle> evilTiles = currentLevel.getEvilTiles();
		for (Rectangle rect : evilTiles) {
			if (character.getCollisionBox().overlaps(rect)) {
				return true;
			}
		}
		return false;
	}
	
	private void checkFireSpellCollisionWithIceBlocks(Spell spell) {
		if (!spell.spell.equals(ActiveSpell.FIRE)) return;
		ArrayList<IceTile> tiles = currentLevel.getIceTiles();
		for (IceTile tile : tiles) {
			if (spell.getCollisionBox().overlaps(tile.getRect())) {
				tile.hit();
			}
		}
	}
	
	private void checkIceSpellCollisionWithWaterBlocks(Spell spell) {
		if (!spell.spell.equals(ActiveSpell.ICE)) return;
		ArrayList<WaterTile> tiles = currentLevel.getWaterTiles();
		for (WaterTile tile : tiles) {
			if (spell.getCollisionBox().overlaps(tile.getRect())) {
				tile.hit();
			}
		}
	}
	
	boolean checkSpellCollisionWithBlocks(Spell spell) {
		ArrayList<Rectangle> collidableTiles = currentLevel.getCollidableTiles();
		for (Rectangle rect : collidableTiles) {
			if (spell.getCollisionBox().overlaps(rect)) {
				return true;
			}
		}
		return false;
	}
	
	boolean playerHasReachedGargoyle() {
		return character.getCollisionBox().overlaps(currentLevel.gargoyle.getCollisionBox());
	}
	
	private void checkPlayerCollisionWithBlocks(float delta) {
		ArrayList<Rectangle> collidableTiles = currentLevel.getCollidableTiles();
		
		Rectangle collisionBox = character.getCollisionBox();
		Vector2 offset = character.getCollisionBoxOffset();
		
		Vector2 nextVelocity = character.nextVelocity(delta);
		Vector2 nextPosition = character.nextPosition(nextVelocity, delta);
		
		Rectangle nextRect = new Rectangle(collisionBox);
		
		// Check x direction
		nextRect.x = nextPosition.x + offset.x;
		
		if (!nextRect.overlaps(currentLevel.levelRect)) {
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
		
		if (!nextRect.overlaps(currentLevel.levelRect)) {
			character.willCollideY();
		}
		
		for (Rectangle rect : collidableTiles) {
			if (nextRect.overlaps(rect)) {
				character.willCollideY();
				break;
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		// draw character
		batch.draw(currentLevel.gargoyle.getTextureRegion(), currentLevel.gargoyle.getPosition().x, currentLevel.gargoyle.getPosition().y);
		batch.draw(character.getTextureRegion(), character.getPosition().x, character.getPosition().y);
		batch.draw(character.getStaffEffect(), character.getPosition().x, character.getPosition().y);
		for (GameObject spell : spells) {
			batch.draw(spell.getTextureRegion(), spell.getPosition().x, spell.getPosition().y);
		}
	}
	
	private Vector2 getStaffPosition() {
		Vector2 staffPosition;
		if (character.isFacingLeft) {
			staffPosition = character.getPosition().cpy().add(14, 100);
		}
		else {
			staffPosition = character.getPosition().cpy().add(50, 100);
		}
		return staffPosition;
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(0.8f, 0.0f, 0.0f, 1.0f);
		shapeRenderer.line(getStaffPosition(), mousePosition);
	}
	
	private void processInput() {
		if (keys.get(Keys.SPELL_FIRE)) {
			character.spell = ActiveSpell.FIRE;
		}
		if (keys.get(Keys.SPELL_ICE)) {
			character.spell = ActiveSpell.ICE;
		}
		if (keys.get(Keys.JUMP)) {
			character.jump();
		}
		if (keys.get(Keys.LEFT)) {
			character.moveLeft();
		}
		else if (keys.get(Keys.RIGHT)) {
			character.moveRight();
		} 
		if (keys.get(Keys.FIRE)) {
			if (spellFired) {
				Vector2 staffPosition = getStaffPosition();
				Vector2 direction = mousePosition.cpy().sub(staffPosition);
				Spell spell = new Spell(staffPosition, direction, character.spell);
				
				spells.add(spell);
				spellFired = false;
			}
			
		}
		else {
			spellFired = true;
		}
	}

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
		keys.put(Keys.SPELL_FIRE, false);
		keys.put(Keys.SPELL_ICE, false);
	};

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}
	
	public void spellFirePressed() {
		keys.get(keys.put(Keys.SPELL_FIRE, true));
	}
	
	public void spellFireReleased() {
		keys.get(keys.put(Keys.SPELL_FIRE, false));
	}
	
	public void spellIcePressed() {
		keys.get(keys.put(Keys.SPELL_ICE, true));
	}
	
	public void spellIceReleased() {
		keys.get(keys.put(Keys.SPELL_ICE, false));
	}
}
