package com.cendric.ecs.components;

public class DynamicTileComponent extends Component {

	public int tileID;
	
	public DynamicTileComponent(int tileID) {
		this.tileID = tileID;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.DynamicTile;
	}
}
