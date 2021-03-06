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
import com.cendric.ecs.systems.LeverSystem;
import com.cendric.ecs.systems.MovementSystem;
import com.cendric.ecs.systems.SpellAnimationSystem;
import com.cendric.ecs.systems.SpellCollisionSystem;
import com.cendric.ecs.systems.TileSpellCollisionSystem;
public class WorldController {
	
	private CendricGame game;

	private Level currentLevel;
	
	private InputController inputController;
	private CendricInputSystem playerInput;
	private AccelerationSystem acceleration;
	private CendricAnimationSystem cendricAnimation;
	private SpellAnimationSystem spellAnimation;
	private CendricCollisionSystem cendricCollision;
	private SpellCollisionSystem spellCollision;
	private TileSpellCollisionSystem tileSpellCollision;
	private LeverSystem leverSystem;
	private CendricDeathSystem cendricDeath;
	private MovementSystem movement;
	private GargoyleSystem gargoyle;

	public WorldController(CendricGame game, Level level, InputController input) {
		this.game = game;
		currentLevel = level;
		inputController = input;

		playerInput = new CendricInputSystem(input, level);
		acceleration = new AccelerationSystem();
		cendricAnimation = new CendricAnimationSystem();
		spellAnimation = new SpellAnimationSystem();
		
		tileSpellCollision = new TileSpellCollisionSystem(level);
		leverSystem = new LeverSystem(level);
		cendricCollision = new CendricCollisionSystem(level);
		spellCollision = new SpellCollisionSystem(level);
		cendricDeath = new CendricDeathSystem(game, level);
		movement = new MovementSystem();
		gargoyle = new GargoyleSystem(game, level);
	}

	public void update(float delta) {
		inputController.update();
		
		if (game.isPaused()) return;
		
		List<Entity> entities = currentLevel.getEntities();
		
//		System.out.println("# entities: " + entities.size());
		
		// Note: the update order is somewhat important
		
		playerInput.update(entities, delta);
		acceleration.update(entities, delta);
		
		tileSpellCollision.update(entities, delta);
		leverSystem.update(entities, delta);
		spellCollision.update(entities, delta);
		cendricCollision.update(entities, delta);
		
		cendricAnimation.update(entities, delta);
		spellAnimation.update(entities, delta);
		
		cendricDeath.update(entities, delta);
		movement.update(entities, delta);
		gargoyle.update(entities, delta);
		
		currentLevel.updateEntityArray();
		
		// TODO Find better place, maybe separate class.
		Entity player = currentLevel.getPlayer();
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
		game.camera.position.x = Math.round(game.camera.position.x);
		game.camera.position.y = Math.round(game.camera.position.y);
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.shapeRenderer.setProjectionMatrix(game.camera.combined);
	}
}
