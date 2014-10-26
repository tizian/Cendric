package com.cendric.ecs.components;

public class SpellStateComponent extends Component {
	
	public static enum SpellType {
		FIRE, ICE;
	}
	
	public SpellType spellType;
	public int numberReflections;
	
	public SpellStateComponent(SpellType type) {
		spellType = type;
		numberReflections = 3;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.SpellState;
	}
}