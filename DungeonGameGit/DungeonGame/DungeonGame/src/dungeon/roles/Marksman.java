package dungeon.roles;

import dungeon.actions.CounterMonsters;
import dungeon.actions.LevelUp;
import dungeon.actions.PotionHandler;
import lombok.Data;

@Data
public class Marksman extends RoleImpl implements Role {
	
	private Spells spells;
	
	public Marksman(int coreHealth, int maxHealth, int coreMana, int maxMana, int attackDmg,
					PotionHandler potionHandler, LevelUp levelUp, CounterMonsters counter, Spells spells) {
		super(coreHealth, maxHealth, coreMana, maxMana, attackDmg,
				potionHandler, levelUp, counter, spells);
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
				"to deal " + getSpells().getCritArrow() + " times from base attack damage)" +
				"4. Cancel using a spell" + "\n";
	}

	public String slayedMonstersCounter() {
		return super.slayedMonstersCounter();
	}

	public String readyWeapon() {
		return super.readyWeapon() + "bow!";
	}

}
