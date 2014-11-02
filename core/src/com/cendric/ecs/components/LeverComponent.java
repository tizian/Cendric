package com.cendric.ecs.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class LeverComponent extends Component {

	public List<Vector2> affectedCells;
	public boolean active;
	
	public LeverComponent() {
		affectedCells = new ArrayList<Vector2>();
		active = true;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.Lever;
	}
	
	public void addCell(float col, float row) {
		affectedCells.add(new Vector2(col, row));
	}
	
	public void switchActive() {
		active = !active;
	}

}