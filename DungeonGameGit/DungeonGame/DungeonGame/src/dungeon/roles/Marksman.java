package dungeon.roles;

import dungeon.basicmonsters.CounterMonsters;
import lombok.Data;

@Data
public class Marksman extends RoleImpl implements Role {
	
	private Spells spells;
	
	public Marksman(int coreHealth, int maxHealth,
					int coreMana, int maxMana,
					int attackDmg, int healthPotions, int manaPotions,
					int hpPotionHeal, int hpDropChance,
					int experience, int level, int levelDivider,
					CounterMonsters counter, Spells spells) {
		super(coreHealth, maxHealth, 
				coreMana, maxMana, 
				attackDmg, healthPotions, manaPotions,
				hpPotionHeal, hpDropChance, 
				experience, level, levelDivider,
				counter, spells);
		this.spells = spells;
	}
	
	// Implementation of UI methods
    public String getName() {
        return "Marksman";
    }
    public String getDescription() {
        return "bow expert with ranged skills!";
    }

	public String stats() {
		return super.stats() + "* Mana Power: Poison Arrow [10 Mana cost] - " + 
				"(Deals initial damage for " +  getSpells().getPoisonArrow() + 
				" and poisons the target for " + getSpells().getPoisonDmg() + " for 2 turns)" +
				"\n" + "* Mana Power: Follow-up Arrows [5 Mana cost] - " +
				"(Fires " + getSpells().getFollowupArrows() + " consecutive shots, in the size of your attack damage)" +
				"\n" + "* Mana Power: Critical Arrow [5 Mana cost] - " +
				"(Fires an arrow to a weak spot that has a " + getSpells().getCritChance() + "% chance " +
				"to deal " + getSpells().getCritArrow() + " times from base attack damage)";
	}
	
	public String spells() {
		return super.spells() + "1. Mana Power: Poison Arrow [10 Mana cost] - " + 
				"(Deals initial damage for " + getSpells().getPoisonArrow() + 
				" and poisons the target for " + getSpells().getPoisonDmg() + " for 2 turns)" +	"\n" + 
				"2. Mana Power: Follow-up Arrows [5 Mana cost] - " +
				"(Fires " + getSpells().getFollowupArrows() + " consecutive shots, in the size of your attack damage)" + "\n" + 
				"3. Mana Power: Critical Arrow [5 Mana cost] - " +
				"(Fires an arrow to a weak spot that has a " + getSpells().getCritChance() + "% chance " +
				"to deal " + getSpells().getCritArrow() + " times from base attack damage)";
	}

	public String slayedMonstersCounter() {
		return super.slayedMonstersCounter();
	}

	public String readyWeapon() {
		return super.readyWeapon() + "bow!";
	}

}
