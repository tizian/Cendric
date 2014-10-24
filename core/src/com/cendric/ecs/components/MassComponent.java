package com.cendric.ecs.components;

public class MassComponent extends Component {

	public float mass = 1;
	
	@Override
	public ComponentType getType() {
		return ComponentType.Mass;
	}

}
