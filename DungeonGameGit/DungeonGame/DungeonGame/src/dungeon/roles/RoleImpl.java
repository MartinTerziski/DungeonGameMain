package dungeon.roles;

import lombok.Data;

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
	Spells spells;
	
	public RoleImpl(int coreHealth, int maxHealth, int coreMana, 
			int maxMana, int attackDmg, int healthPotions, 
			int manaPotions, int potionHeal, int potionDropChance, 
			int experience, int level, int levelDivider,
			Spells spells) {
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
		this.experience = experience;
		this.level = level;
		this.levelDivider = levelDivider;
		this.spells = spells;
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
				"* Experience: " + getExperience() + "/" + getLevelDivider() + "\n" +
				"* Level: " + getLevel() + "\n";
	}
	public String spells() {
		return "";
	}
	public String readyWeapon() {
		return "You immediately ready your ";
	}
}
