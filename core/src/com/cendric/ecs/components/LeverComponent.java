package com.cendric.ecs.components;

import java.util.ArrayList;
import java.util.List;

import com.cendric.ecs.Entity;

public class LeverComponent extends Component {
	
	public String id;
	public boolean active;
	public List<Entity> affectedBoxes;
	
	public LeverComponent(String id) {
		this.id = id;
		active = true;
		affectedBoxes = new ArrayList<Entity>();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.Lever;
	}

	public void switchActive() {
		active = !active;
	}
	
	public void addBox(Entity e) {
		if (!e.tag.equals("LeverBox")) return;
		affectedBoxes.add(e);
	}

}
