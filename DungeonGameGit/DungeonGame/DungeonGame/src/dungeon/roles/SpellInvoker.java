package dungeon.roles;

import dungeon.basicmonsters.CounterMonsters;
import lombok.Data;

@Data
public class SpellInvoker extends RoleImpl implements Role, UIInfo{

	public Spells spells;
	
	public SpellInvoker(int coreHealth, int maxHealth,
						int coreMana, int maxMana,
						int attackDmg, int healthPotions, int manaPotions,
						int hpPotionHeal, int hpDropChance,
						int experience, int level, int levelDivider,
						CounterMonsters counterMonsters, Spells spells) {
		super(coreHealth, maxHealth, 
				coreMana, maxMana, 
				attackDmg, healthPotions, manaPotions,
				hpPotionHeal, hpDropChance, 
				experience, level, levelDivider,
				counterMonsters, spells);
		this.spells = spells;
	}
	
	// Implementation of UI methods
    public String getName() {
        return "Spell-Invoker";
    }
    public String getDescription() {
        return "sage with experienced magic skills!";
    }

	public String stats() {
		return super.stats() + "* Mana Power: Storm Bolt [5 Mana cost] - " + 
				"(Deals " + getSpells().getStormBolt() + " damage to an enemy)" +
				"\n" + "* Mana Power: Fire Comet - [20 Mana cost] " + 
				"(Deals massive damage to an enemy for " + getSpells().getFireComet() + " damage)" +
				"\n" + "* Mana Power: Ice Shard - [10 Mana cost] " + 
				"(Deals " + getSpells().getIceShard() + " damage to an enemy and reduces " + getSpells().getReducedDamage() + "% monster damage for their next 2 attacks)\n";
	}
	
	public String spells() {
		return super.spells() + "1. Mana Power: Storm Bolt [5 Mana cost] - " + 
				"(Deals " + getSpells().getStormBolt() + " damage to an enemy)" + "\n" + 
				"2. Mana Power: Fire Comet [20 Mana cost] - " + 
				"(Deals massive damage to an enemy for " + getSpells().getFireComet() + " damage)" + "\n" + 
				"3. Mana Power: Ice Shard [10 Mana cost] - " + 
				"(Deals " + getSpells().getIceShard() + " damage to an enemy and reduces " + getSpells().getReducedDamage() + "% monster damage for their next 2 attacks)\n";
	}
	public String slayedMonstersCounter() {
		return super.slayedMonstersCounter();
	}
	public String readyWeapon() {
		return super.readyWeapon() + "staff!";
	}
}
