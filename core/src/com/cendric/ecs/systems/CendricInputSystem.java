package com.cendric.ecs.systems;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.cendric.controllers.InputController;
import com.cendric.controllers.Key;
import com.cendric.ecs.Entity;
import com.cendric.ecs.EntityFactory;
import com.cendric.ecs.components.AnimationStateComponent;
import com.cendric.ecs.components.BoundingBoxComponent;
import com.cendric.ecs.components.CendricSpellsComponent;
import com.cendric.ecs.components.ComponentType;
import com.cendric.ecs.components.MovementComponent;
import com.cendric.ecs.components.PositionComponent;
import com.cendric.models.Level;

public class CendricInputSystem extends UpdateSystem {
	
	private InputController inputController;
	private Level level;
	
	private boolean castPossible;
	
	public CendricInputSystem(InputController inputController, Level level) {
		this.inputController = inputController;
		this.level = level;
		castPossible = true;
	}

	@Override
	protected void update(Entity entity, float dt) {
		if (!entity.tag.equals("Cendric")) return;
		PositionComponent pos = (PositionComponent) entity.getComponent(ComponentType.Position);
		MovementComponent mov = (MovementComponent) entity.getComponent(ComponentType.Movement);
		CendricSpellsComponent cp = (CendricSpellsComponent) entity.getComponent(ComponentType.CendricSpells);
		AnimationStateComponent as = (AnimationStateComponent) entity.getComponent(ComponentType.AnimationState);
		if (pos == null) return;
		if (mov == null) return;
		if (cp == null) return;
		if (as == null) return;
		
		Entity cursor = level.getEntities().get(1);	// risky (?) assumption: cursor is 2nd entity in list
		PositionComponent cursorPos = (PositionComponent) cursor.getComponent(ComponentType.Position);
		BoundingBoxComponent cursorBB = (BoundingBoxComponent) cursor.getComponent(ComponentType.BoundingBox);
		
		if (inputController.isKeyPressed(Key.LEFT)) {
			mov.vxTarget = -mov.MAX_VELOCITY;
		}
		else if (inputController.isKeyPressed(Key.RIGHT)) {
			mov.vxTarget = mov.MAX_VELOCITY;
		}
		else {
			mov.vxTarget = 0;
		}
		
		if (inputController.isKeyPressed(Key.JUMP) && mov.grounded) {
			mov.vy = mov.JUMP_SPEED;
			mov.grounded = false;
		}
		else if (!inputController.isKeyPressed(Key.JUMP) && !mov.grounded) {
			if (mov.vy > mov.JUMP_SPEED_CUTOFF) {
				mov.vy = mov.JUMP_SPEED_CUTOFF;
			}
		}

		if (mov.grounded) {
			mov.vyTarget = 0;
		}
		else {
			mov.vyTarget = mov.TERMINAL_VELOCITY;
		}
		
		if (inputController.isKeyPressed(Key.NUM_1)) {
			cp.setCurrentSpellIndex(0);
		}
		else if (inputController.isKeyPressed(Key.NUM_2)) {
			cp.setCurrentSpellIndex(1);
		}
		
		int scrollAmount = inputController.getScrollAmount();
		
		if (scrollAmount == 1) {
			cp.nextSpell();
		}
		else if (scrollAmount == -1) {
			cp.previousSpell();
		}
		
		if (inputController.isKeyPressed(Key.CAST) && castPossible) {
			Vector2 staffPosition = new Vector2();
			if (as.facingLeft) {
				staffPosition = new Vector2(pos.x + 14, pos.y + 100);
			}
			else {
				staffPosition = new Vector2(pos.x + 50, pos.y + 100);
			}
			Vector2 mousePosition = new Vector2(cursorPos.x, cursorPos.y);
			Vector2 dir = mousePosition.sub(staffPosition).nor().scl(500);
			level.addEntity(EntityFactory.createSpellCast(cp.activeSpellType(), staffPosition.x, staffPosition.y, dir.x, dir.y));
			castPossible = false;
		}
		else if(!inputController.isKeyPressed(Key.CAST)) {
			castPossible = true;
		}
		
		Vector2 mouseDelta = inputController.getMouseDelta();
		float dx = mouseDelta.x + mov.vx * dt;
		float dy = mouseDelta.y + mov.vy * dt;
		
		cursorBB.boundingBox.x += dx;
		
		if (cursorBB.boundingBox.x < level.getLevelRect().x) {
			cursorBB.boundingBox.x = level.getLevelRect().x;
		}
		else if (cursorBB.boundingBox.x + cursorBB.boundingBox.width > level.getLevelRect().x + level.getLevelRect().width) {
			cursorBB.boundingBox.x = level.getLevelRect().y + level.getLevelRect().width - cursorBB.boundingBox.width;
		}
		
		cursorBB.boundingBox.y += dy;
		
		if (cursorBB.boundingBox.y < level.getLevelRect().y) {
			cursorBB.boundingBox.y = level.getLevelRect().y;
		}
		else if (cursorBB.boundingBox.y + cursorBB.boundingBox.height > level.getLevelRect().y + level.getLevelRect().height) {
			cursorBB.boundingBox.y = level.getLevelRect().y + level.getLevelRect().height - cursorBB.boundingBox.height;
		}
		
		cursorPos.x = cursorBB.boundingBox.x;
		cursorPos.y = cursorBB.boundingBox.y;
	}
}