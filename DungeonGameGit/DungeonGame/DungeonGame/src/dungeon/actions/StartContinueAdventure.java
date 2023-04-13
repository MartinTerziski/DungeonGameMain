package dungeon.actions;

import java.util.Scanner;

import dungeon.roles.Role;

public class StartContinueAdventure {
	
	public static void beginningJourney(Role role) {
		System.out.println("+++++++++++ =========== +++++++++++");
		try {
			System.out.println("As you ride your horse, you appear at a strange abandoned village...");
			System.out.println("The streets are so quiet, unless...");
			Thread.sleep(3000);
			System.out.println("A monster attacks you from one of the houses!");
			Thread.sleep(500);
			System.out.println(role.readyWeapon());		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean continueAdventure(Role role, Scanner input) {
		System.out.println("+++++++++++ =========== +++++++++++");
		System.out.println("Continue searching the village?");
		boolean continued = false;
		while(!continued) {
			System.out.println("\t1. Advance through.");
			System.out.println("\t2. See statistics.");
			System.out.println("\t3. Use Health potion. (You have " + role.getPotionHandler().getHealthPotions() + "/5 potions)");
			System.out.println("\t4. Use Mana potion. (You have " + role.getPotionHandler().getManaPotions() + "/5 potions)");
			System.out.println("\t5. See number of slayed monsters.");
			System.out.println("\t6. Leave the village.");
			String nextInput = input.nextLine();
			switch (nextInput) {
				case "1" -> {
					System.out.println("You continue searching the village... After some time...");
					continued = true;
				}
				case "2" -> {
					System.out.println(role.stats());
					System.out.println("+++++++++++ =========== +++++++++++");
				}
				case "3", "4" -> role.getPotionHandler().usePotion(role, nextInput);
				case "5" -> {
					System.out.println(role.slayedMonstersCounter());
					System.out.println("+++++++++++ =========== +++++++++++");
				}
				case "6" -> {
					System.out.println("You are leaving the village...");
					return false;
				}
				default -> {
					System.out.println("Invalid Command: " + nextInput);
					System.out.println("+++++++++++ =========== +++++++++++");
				}
			}
		}
		return continued;
	}
}
