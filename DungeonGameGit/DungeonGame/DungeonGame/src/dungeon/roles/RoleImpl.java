package dungeon.roles;

import dungeon.actions.CounterMonsters;
import dungeon.actions.LevelUp;
import lombok.Data;

import java.util.Map;

@Data
public class RoleImpl implements Role{
	int coreHealth;
	int maxHealth;
	int coreMana;
	int maxMana;
	int attackDmg;
	int healthPotions;
	int manaPotions;
	int potionHeal;
	int potionDropChance;
	int experience;
	int level;
	int levelDivider;
	LevelUp levelUp;
	CounterMonsters counter;
	Spells spells;

	public RoleImpl(int coreHealth, int maxHealth, int coreMana, int maxMana, int attackDmg,
					int healthPotions, int manaPotions, int potionHeal, int potionDropChance,
					LevelUp levelUp, CounterMonsters counter, Spells spells) {
		super();
		this.coreHealth = coreHealth;
		this.maxHealth = maxHealth;
		this.coreMana = coreMana;
		this.maxMana = maxMana;
		this.attackDmg = attackDmg;
		this.healthPotions = healthPotions;
		this.manaPotions = manaPotions;
		this.potionHeal = potionHeal;
		this.potionDropChance = potionDropChance;
		this.levelUp = levelUp;
		this.spells = spells;
		this.counter = counter;
	}

	//UI methods
	@Override
    public String getName() {
        return "Role";
    }

	public String stats() {
		return "Stats: \n" +
				"* Health: " + getMaxHealth() + "/" + getCoreHealth() + "\n" +
				"* Mana: " + getMaxMana() + "/" + getCoreMana() + "\n" +
				"* Maximum attack damage: " + getAttackDmg() + "\n" +
				"* Health potions: " + getHealthPotions() + "\n" +
				"* Mana potions: " + getManaPotions() + "\n" +
				"* Experience: " + getLevelUp().getExperience() + "/" + getLevelUp().getLevelDivider() + "\n" +
				"* Level: " + getLevelUp().getLevel() + "\n";
	}

	public String slayedMonstersCounter() {
		StringBuilder sb = new StringBuilder();
		sb.append("Slayed Monsters:\n");
		for (Map.Entry<String, Integer> entry : counter.getCounterMap().entrySet()) {
			sb.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("/5\n");
		}
		return sb.toString();
	}

	public String spells() {
		return "";
	}

	public String readyWeapon() {
		return "You immediately ready your ";
	}
}
