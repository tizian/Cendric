package com.cendric.ecs.components;

public class SpellStateComponent extends Component {
	
	public static enum SpellType {
		FIRE, ICE;
	}
	
	public SpellType spellType;
	
	public SpellStateComponent(SpellType type) {
		spellType = type;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.SpellState;
	}
}
