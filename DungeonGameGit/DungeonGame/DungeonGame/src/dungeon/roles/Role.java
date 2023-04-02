package dungeon.roles;

public interface Role{
	
	// Methods for UI logic
	default String getName() {
        return "Role";
    }

    default String getDescription() {
        return "Default role";
    }
    
    //Other Methods    
	int getMaxHealth();
	void setMaxHealth(int i);

	int getCoreHealth();
	void setCoreHealth(int i);
	
	int getMaxMana();
	void setMaxMana(int i);
	
	int getCoreMana();
	void setCoreMana(int i);

	int getAttackDmg();
	void setAttackDmg(int i);

	int getHealthPotions();
	void setHealthPotions(int i);

	int getPotionHeal();

	int getExperience();
	void setExperience(int i);

	int getPotionDropChance();
	void setPotionDropChance(int i);

	int getManaPotions();
	void setManaPotions(int i);

	int getLevel();
	void setLevel(int i);
	
	int getLevelDivider();
	void setLevelDivider(int i);
	
	Spells getSpells();
	
	String stats();
	
	String spells();

	String slayedMonstersCounter();
	
	String readyWeapon();
}
