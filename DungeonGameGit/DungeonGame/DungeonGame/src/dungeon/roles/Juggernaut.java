package dungeon.roles;

import lombok.Data;

@Data
public class Juggernaut extends RoleImpl implements Role, UIInfo {

	private Spells spells;
	
	public Juggernaut(int coreHealth, int maxHealth, 
			int coreMana, int maxMana, 
			int attackDmg, int healthPotions, int manaPotions, 
			int hpPotionHeal, int hpDropChance, 
			int experience, int level, int levelDivider,
			Spells spells) {
		super(coreHealth, maxHealth, 
				coreMana, maxMana, 
				attackDmg, healthPotions, 
				manaPotions, hpPotionHeal, hpDropChance, 
				experience, level, levelDivider,
				spells);
		this.spells = spells;
	}
	
	// Implementation of UI methods
    public String getName() {
        return "Juggernaut";
    }
    public String getDescription() {
        return "warrior with experienced skill of close combat!";
    }

	public String stats() {
		return super.stats() + "* Mana Power: Empower attack [5 Mana cost] - " + 
				"(The next attack is empowered by " + spells.getEmpowerAttack() + " damage)" + "\n" +
				"* Mana Power: Immunizing Shield [10 Mana cost] - " + 
				(spells.getImmunizingShield()==1 ? "(The next attack from an enemy is negated)"
				: "(The next " + spells.getImmunizingShield() + " attacks are negated)") + "\n";
	}
	
	public String spells() {
		return super.spells() + "1. Mana Power: Empower attack [5 Mana cost] - " + 
				"(The next attack is empowered by " + spells.getEmpowerAttack() + " damage)" + "\n" +
				"2. Mana Power: Immunizing Shield [10 Mana cost] - " + 
				(spells.getImmunizingShield()==1 ? "(The next attack from an enemy is negated)"
				: "(The next " + spells.getImmunizingShield() + " attacks are negated)") + "\n";
	}
	
	public String readyWeapon() {
		return super.readyWeapon() + "sword!";
	}

}
