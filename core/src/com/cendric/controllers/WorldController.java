package com.cendric.controllers;

import java.util.List;

import com.cendric.CendricGame;
import com.cendric.Constants;
import com.cendric.ecs.Entity;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.ecs.systems.AccelerationSystem;
import com.cendric.ecs.systems.CendricAnimationSystem;
import com.cendric.ecs.systems.CendricCollisionSystem;
import com.cendric.ecs.systems.CendricDeathSystem;
import com.cendric.ecs.systems.CendricInputSystem;
import com.cendric.ecs.systems.GargoyleSystem;
import com.cendric.ecs.systems.MovementSystem;
import com.cendric.ecs.systems.SpellAnimationSystem;
import com.cendric.ecs.systems.SpellCollisionSystem;
import com.cendric.models.Level;
public class WorldController {
	
	private CendricGame game;

	private Level currentLevel;
	
	private CendricInputSystem playerInput;
	private AccelerationSystem acceleration;
	private CendricAnimationSystem cendricAnimation;
	private SpellAnimationSystem spellAnimation;
	private CendricCollisionSystem cendricCollision;
	private SpellCollisionSystem spellCollision;
	private CendricDeathSystem cendricDeath;
	private MovementSystem movement;
	private GargoyleSystem gargoyle;

	public WorldController(CendricGame game, Level level, InputController input) {
		this.game = game;
		currentLevel = level;

		playerInput = new CendricInputSystem(input, level);
		acceleration = new AccelerationSystem();
		cendricAnimation = new CendricAnimationSystem();
		spellAnimation = new SpellAnimationSystem();
		
		cendricCollision = new CendricCollisionSystem(level);
		spellCollision = new SpellCollisionSystem(level);
		cendricDeath = new CendricDeathSystem(game, level);
		movement = new MovementSystem();
		gargoyle = new GargoyleSystem(game, level);
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
		
		cendricDeath.update(entities, delta);
		movement.update(entities, delta);
		gargoyle.update(entities, delta);
		
		currentLevel.updateEntityArray();
		
		// TODO Find better place, maybe separate class.
		Entity player = currentLevel.getEntities().get(0);	// risky (?) assumption: Cendric is 1st entity in list
		PositionComponent pos = (PositionComponent) player.getComponent(ComponentType.Position);
		
		game.camera.position.x = pos.x;
		game.camera.position.y = pos.y;
		if (pos.x < Constants.WINDOW_WIDTH / 2) {
			game.camera.position.x = Constants.WINDOW_WIDTH / 2;
		}
		if (pos.x > currentLevel.getLevelRect().width - Constants.WINDOW_WIDTH / 2) {
			game.camera.position.x = currentLevel.getLevelRect().width - Constants.WINDOW_WIDTH / 2;
		}
		if (pos.y < Constants.WINDOW_HEIGHT / 2) {
			game.camera.position.y = Constants.WINDOW_HEIGHT / 2;
		}
		if (pos.y > currentLevel.getLevelRect().height - Constants.WINDOW_HEIGHT / 2) {
			game.camera.position.y = currentLevel.getLevelRect().height - Constants.WINDOW_HEIGHT / 2;
		}
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.shapeRenderer.setProjectionMatrix(game.camera.combined);
	}
}
