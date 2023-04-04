package dungeon.actions;

import java.util.Random;

import dungeon.roles.Role;

public class PotionHandler {

	public static Random random = new Random();

	public static void healthPotionDrop(Role role) {
		if(random.nextInt(100) < role.getPotionDropChance()) {
			role.setHealthPotions(role.getHealthPotions() + 1);
			System.out.println("You have found a health potion!");
			if(role.getHealthPotions() == 1) {
				System.out.println("You now have one health potion at your disposal.");
			} else if(role.getHealthPotions() <= 5) {
				System.out.println("You gained the potion and now have " + role.getHealthPotions() + " health potions at your disposal.");
			} else {
				role.setHealthPotions(role.getHealthPotions() - 1);
				System.out.println("You cannot take the potion, reached maximum health potions held (5).");
			}
		}
	}

	public static void manaPotionDrop(Role role) {
		if(random.nextInt(100) < role.getPotionDropChance()) {
			role.setManaPotions(role.getManaPotions() + 1);
			System.out.println("You have found a mana potion!");
			if(role.getManaPotions() == 1) {
				System.out.println("You now have one mana potion at your disposal.");
			} else if(role.getManaPotions() <= 5) {
				System.out.println("You gained the potion and now have " + role.getManaPotions() + " mana potions at your disposal.");
			} else {
				role.setManaPotions(role.getManaPotions() - 1);
				System.out.println("You cannot take the potion, reached maximum mana potions held (5).");
			}
		}
	}

	public static void usePotion(Role role, String action) {
		if(action.equals("3")) {
			if(role.getHealthPotions() <= 0) System.out.println("You ran out of health potions!");
			else {
				double amountToHeal = (double) role.getPotionHeal() /100 * role.getCoreHealth();
				role.setMaxHealth((int) Math.min(role.getMaxHealth() + amountToHeal, role.getCoreHealth()));
				role.setHealthPotions(role.getHealthPotions() - 1);
				if(role.getMaxHealth() > role.getCoreHealth()) role.setMaxHealth(role.getCoreHealth());
				System.out.println("You healed " + ((int) amountToHeal) + " health with your potion.");
				System.out.println("Current health: " + role.getMaxHealth() + "/" + role.getCoreHealth());
				System.out.println("You have " + role.getHealthPotions() + " health potion(s) left at your disposal.");
			}
		} else if(action.equals("4")){
			if(role.getManaPotions() <= 0) System.out.println("You ran out of mana potions!");
			else {
				double amountToHeal = (double) role.getPotionHeal() /100 * role.getCoreMana();
				role.setMaxMana((int) Math.min(role.getMaxMana() + amountToHeal, role.getCoreMana()));
				role.setManaPotions(role.getManaPotions() - 1);
				if(role.getMaxMana() > role.getCoreMana()) role.setMaxMana(role.getCoreMana());
				System.out.println("You restored " + (int) amountToHeal + " mana with your potion.");
				System.out.println("Current mana: " + role.getMaxMana() + "/" + role.getCoreMana());
				System.out.println("You have " + role.getManaPotions() + " mana potion(s) left at your disposal.");
			}
		}
	}
}
