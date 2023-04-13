package dungeon.roles;

import dungeon.actions.CounterMonsters;
import dungeon.actions.LevelUp;
import dungeon.actions.PotionHandler;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class RoleImpl implements Role{
	int coreHealth;
	int maxHealth;
	int coreMana;
	int maxMana;
	int attackDmg;
	PotionHandler potionHandler;
	LevelUp levelUp;
	CounterMonsters counter;
	Spells spells;

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
				"* Health potions: " + getPotionHandler().getHealthPotions() + "\n" +
				"* Mana potions: " + getPotionHandler().getManaPotions() + "\n" +
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
