package com.cendric.ecs;

import java.util.HashMap;
import java.util.Map;

import com.cendric.ecs.components.Component;

public class Entity {
	private static Integer count = 0;
	
	public Integer id;
	public Map<String, Component> components;
	
	public Entity() {
		this.id = count;
		count++;
		this.components = new HashMap<String, Component>();
	}
	
	public void addComponent(Component component) {
		this.components.put(component.getName(), component);
	}
	
	public void removeComponent(String componentName) {
		this.components.remove(componentName);
	}
	
	public String toString() {
		String output = String.format("Entity %d:\n", id);
		for (Component c : components.values()) {
			String cString = c.toString();
			output.concat(cString);
		}
		return output;
	}
}