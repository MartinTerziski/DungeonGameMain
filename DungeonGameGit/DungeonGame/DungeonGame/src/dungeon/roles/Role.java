package dungeon.roles;

import dungeon.actions.LevelUp;
import dungeon.actions.PotionHandler;

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

	PotionHandler getPotionHandler();

	LevelUp getLevelUp();
	
	Spells getSpells();
	
	String stats();
	
	String spells();

	String slayedMonstersCounter();
	
	String readyWeapon();
}
