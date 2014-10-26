package com.cendric.ecs;

import java.util.HashMap;
import java.util.Map;

import com.cendric.ecs.components.Component;
import com.cendric.ecs.components.ComponentType;

public class Entity {
	private static Integer count = 0;
	
	public String tag;
	public Integer id;
	private Map<ComponentType, Component> components;
	
	public Entity(String tag) {
		this.tag = tag;
		this.id = count;
		count++;
		this.components = new HashMap<ComponentType, Component>();
	}
	
	public Component getComponent(ComponentType type) {
		return this.components.get(type);
	}
	
	public void addComponent(Component component) {
		this.components.put(component.getType(), component);
	}
	
	public void removeComponent(ComponentType type) {
		this.components.remove(type);
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