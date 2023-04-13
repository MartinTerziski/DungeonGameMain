package dungeon.actions;

import java.util.Random;

import dungeon.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PotionHandler {

	int healthPotions;
	int manaPotions;
	int potionHeal;
	int potionDropChance;

	public static Random random = new Random();

	public void healthPotionDrop() {
		if(random.nextInt(100) < getPotionDropChance()) {
			setHealthPotions(getHealthPotions() + 1);
			System.out.println("You have found a health potion!");
			if(getHealthPotions() == 1) {
				System.out.println("You now have one health potion at your disposal.");
			} else if(getHealthPotions() <= 5) {
				System.out.println("You gained the potion and now have " + getHealthPotions() + " health potions at your disposal.");
			} else {
				setHealthPotions(getHealthPotions() - 1);
				System.out.println("You cannot take the potion, reached maximum health potions held (5).");
			}
		}
	}

	public void manaPotionDrop() {
		if(random.nextInt(100) < getPotionDropChance()) {
			setManaPotions(getManaPotions() + 1);
			System.out.println("You have found a mana potion!");
			if(getManaPotions() == 1) {
				System.out.println("You now have one mana potion at your disposal.");
			} else if(getManaPotions() <= 5) {
				System.out.println("You gained the potion and now have " + getManaPotions() + " mana potions at your disposal.");
			} else {
				setManaPotions(getManaPotions() - 1);
				System.out.println("You cannot take the potion, reached maximum mana potions held (5).");
			}
		}
	}

	public void usePotion(Role role, String action) {
		if(action.equals("3")) {
			if(role.getPotionHandler().getHealthPotions() <= 0) System.out.println("You ran out of health potions!");
			else {
				double amountToHeal = (double) role.getPotionHandler().getPotionHeal() /100 * role.getCoreHealth();
				role.setMaxHealth((int) Math.min(role.getMaxHealth() + amountToHeal, role.getCoreHealth()));
				role.getPotionHandler().setHealthPotions(role.getPotionHandler().getHealthPotions() - 1);
				if(role.getMaxHealth() > role.getCoreHealth()) role.setMaxHealth(role.getCoreHealth());
				System.out.println("You healed " + ((int) amountToHeal) + " health with your potion.");
				System.out.println("Current health: " + role.getMaxHealth() + "/" + role.getCoreHealth());
				System.out.println("You have " + role.getPotionHandler().getHealthPotions() + " health potion(s) left at your disposal.");
			}
		} else if(action.equals("4")){
			if(role.getPotionHandler().getManaPotions() <= 0) System.out.println("You ran out of mana potions!");
			else {
				double amountToHeal = (double) role.getPotionHandler().getPotionHeal() /100 * role.getCoreMana();
				role.setMaxMana((int) Math.min(role.getMaxMana() + amountToHeal, role.getCoreMana()));
				role.getPotionHandler().setManaPotions(role.getPotionHandler().getManaPotions() - 1);
				if(role.getMaxMana() > role.getCoreMana()) role.setMaxMana(role.getCoreMana());
				System.out.println("You restored " + (int) amountToHeal + " mana with your potion.");
				System.out.println("Current mana: " + role.getMaxMana() + "/" + role.getCoreMana());
				System.out.println("You have " + role.getPotionHandler().getManaPotions() + " mana potion(s) left at your disposal.");
			}
		}
	}
}
