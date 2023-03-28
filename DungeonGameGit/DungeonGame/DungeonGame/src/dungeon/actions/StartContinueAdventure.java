package dungeon.actions;

import java.util.Scanner;

import dungeon.roles.Role;

public class StartContinueAdventure {
	
	public static void beginningJourney(Role role) {
		System.out.println("+++++++++++ =========== +++++++++++");
		try {
			System.out.println("As you ride your horse, you appear at a strange abandonded village...");
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
			System.out.println("\t3. Use Health potion.");
			System.out.println("\t4. Use Mana potion.");
			System.out.println("\t5. Leave the village.");
			String nextInput = input.nextLine();
			while(!nextInput.equals("1") && !nextInput.equals("2") && !nextInput.equals("3")
					&& !nextInput.equals("4") && !nextInput.equals("5")) {
				System.out.println("Invalid Command!");
				break;
			}
			if(nextInput.equals("1")) {
				System.out.println("You continue searching the village... After some time...");
				continued = true;
			} else if(nextInput.equals("2")) {
				System.out.println(role.stats());
			} else if(nextInput.equals("3")) {
				PotionHandler.usePotion(role, nextInput);
			} else if(nextInput.equals("4")) {
				PotionHandler.usePotion(role, nextInput);
			} else if(nextInput.equals("5")) {
				System.out.println("You are leaving the village...");
				break;
			}
		}
		return continued;
	}
}
