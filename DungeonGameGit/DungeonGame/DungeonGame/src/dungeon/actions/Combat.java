package dungeon.actions;

import java.util.Random;
import java.util.Scanner;

import dungeon.basicmonsters.BasicMonster;
import dungeon.roles.*;
import dungeon.utilites.ConsoleColors;

public class Combat {

	private static final Random random = new Random();

    public static boolean calculateCombat(BasicMonster basicMonster, Role role, Spells spells, Scanner input) {
		System.out.println("+++++++++++ =========== +++++++++++");
		while(basicMonster.getMaxBasicMonsterHealth() > 0) {
			System.out.println("+++++++++++ =========== +++++++++++");
			System.out.println("Health: " + role.getMaxHealth() + "/" + role.getCoreHealth());
			System.out.println(basicMonster.getName() + "'s HP: " + basicMonster.getMaxBasicMonsterHealth());
			System.out.println("Your actions:");
			System.out.println("\t1. Attack!");
			System.out.println("\t2. Use spell!");
			System.out.println("\t3. Drink Health potion!");
			System.out.println("\t4. Drink Mana potion!");
			System.out.println("\t5. Flee!");

			String action = input.nextLine();
			switch (action) {
				case "1" -> {
					int dmgDealt = random.nextInt(role.getAttackDmg());
					if (calculateFightActions(role, spells, basicMonster, dmgDealt, false)) return true;
				}
				case "2" -> {
					System.out.println("Which spell do you want to use?");
					System.out.println("Current mana: " + role.getMaxMana() + "/" + role.getCoreMana());
					System.out.println(role.spells());
					String spell = input.nextLine();
					if (SpellsHandler.useSpell(spells, role, basicMonster, spell)) return true;
				}
				case "3", "4" -> {
					PotionHandler.usePotion(role, action);
					int dmgDealt = 0;
					if (calculateFightActions(role, spells, basicMonster, dmgDealt, true)) return true;
				}
				case "5" -> {
					System.out.println("You mount your horse and run away from the battle!");
					return true;
				}
				default -> System.out.println("Invalid command!");
			}
		}
		if(role.getMaxHealth() < 1) {
			System.out.println("You died in battle...");
			return true;
		}

		//Result from fight
		resultFromFight(basicMonster, role, spells, input);
		return false;
	}

	public static boolean calculateFightActions(Role role, Spells spells, BasicMonster basicMonster, int dmgDealt, boolean isDrinkingPotion) {
		int dmgTaken;
		//Range characters take 10% less damage
		if (role instanceof SpellInvoker || role instanceof Marksman)
			dmgTaken = (int) (random.nextInt(basicMonster.getBasicMonsterAttack()) * (90 / 100.0f));
		else
			dmgTaken = random.nextInt(basicMonster.getBasicMonsterAttack());
		if (role instanceof Juggernaut) {
			dmgTaken = JuggernautSpells.setupImmunized(spells, dmgTaken);
		}
		if (role instanceof SpellInvoker) {
			SpellInvokerSpells.calculateReducedDamage(spells);
		}
		if (role instanceof Marksman) {
			MarksmanSpells.applyPoison(spells, basicMonster, false);
		}
		return calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, isDrinkingPotion);
	}
    
    public static void resultFromFight(BasicMonster basicMonster, Role role, Spells spells, Scanner input) {
		System.out.println("+++++++++++ =========== +++++++++++");
		role.getLevelUp().setExperience(role.getLevelUp().getExperience() + basicMonster.getExperienceWeight());
		if(role instanceof SpellInvoker) spells.setReducedDamageStack(0);
		System.out.println("The " + basicMonster.getName() + " was defeated!");
		System.out.println("Your health: " + role.getMaxHealth() + "/" + role.getCoreHealth());
		System.out.println("You gained " + basicMonster.getExperienceWeight() + " experience.");

		//Chance of gain health/mana potion
		PotionHandler.healthPotionDrop(role);
		PotionHandler.manaPotionDrop(role);

		// Level UP
		if (role.getLevelUp().getExperience() >= role.getLevelUp().getLevelDivider()) {
			role.getLevelUp().levelingUp(role, input);
		}
	}

    public static boolean calculateAfterSwing(Role role, BasicMonster basicMonster, int dmgDealt, int dmgTaken, boolean isDrinkingPotion) {
		basicMonster.setMaxBasicMonsterHealth(basicMonster.getMaxBasicMonsterHealth() - dmgDealt);
		role.setMaxHealth(role.getMaxHealth() - dmgTaken);
		if(!isDrinkingPotion) System.out.println("You deal " + dmgDealt + " damage to the " + basicMonster.getName() + ".");
		System.out.println(ConsoleColors.RED + "You receive " + dmgTaken + " damage." + ConsoleColors.RESET);
		if(role.getMaxHealth() < 1) {
			System.out.println("You died...");
			return true;
		} else {
			return false;
		}
    }

}
