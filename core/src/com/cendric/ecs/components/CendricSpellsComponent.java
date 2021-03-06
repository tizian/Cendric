package com.cendric.ecs.components;

import java.util.ArrayList;

import com.cendric.ecs.components.SpellStateComponent.SpellType;

public class CendricSpellsComponent extends Component {
	
	private ArrayList<SpellType> knownSpells;
	private int currentSpellIndex;
	
	public int numberOfKnownSpells() {
		return knownSpells.size();
	}
	
	public int activeSpellIndex() {
		return currentSpellIndex;
	}
	
	public SpellType spellTypeForIndex(int index) {
		return knownSpells.get(index);
	}
	
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
	
	public void learnSpell(SpellType spellType) {
		knownSpells.add(spellType);
	}
	
	public void unlearnSpell() {
		knownSpells.remove(knownSpells.size()-1);
	}
	
	public CendricSpellsComponent() {
		knownSpells = new ArrayList<SpellType>();
		currentSpellIndex = 0;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.CendricSpells;
	}

}
