package com.cendric.ecs.components;

import java.util.ArrayList;

import com.cendric.ecs.components.SpellStateComponent.SpellType;

public class CendricSpellsComponent extends Component {
	
	private ArrayList<SpellType> knownSpells;
	private int currentSpellIndex;
	
	public SpellType activeSpellType() {
		if (knownSpells.isEmpty()) return null;
		return knownSpells.get(currentSpellIndex);
	}
	
	public void nextSpell() {
		if (knownSpells.isEmpty()) return;
		currentSpellIndex++;
		if (currentSpellIndex > knownSpells.size() - 1) {
			currentSpellIndex = 0;
		}
	}
	
	public void previousSpell() {
		if (knownSpells.isEmpty()) return;
		currentSpellIndex--;
		if (currentSpellIndex < 0) {
			currentSpellIndex = knownSpells.size() - 1;
		}
	}
	
	public void setCurrentSpellIndex(int index) {
		if (index >= 0 && index < knownSpells.size()) {
			currentSpellIndex = index;
		}
	}
	
	public CendricSpellsComponent(ArrayList<SpellType> knownSpells) {
		this.knownSpells = knownSpells;
		currentSpellIndex = 0;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.CendricSpells;
	}

}
