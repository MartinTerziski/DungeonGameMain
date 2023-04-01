package dungeon.actions;

import java.util.Random;
import java.util.Scanner;

import dungeon.basicmonsters.BasicMonster;
import dungeon.roles.Juggernaut;
import dungeon.roles.JuggernautSpells;
import dungeon.roles.Marksman;
import dungeon.roles.MarksmanSpells;
import dungeon.roles.Role;
import dungeon.roles.SpellInvoker;
import dungeon.roles.SpellInvokerSpells;
import dungeon.roles.Spells;
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
			JuggernautSpells.setupImmunized(spells, dmgTaken);
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
		role.setExperience(role.getExperience() + basicMonster.getExperienceWeight());
		if(role instanceof SpellInvoker) spells.setReducedDamageStack(0);
		System.out.println("The " + basicMonster.getName() + " was defeated!");
		System.out.println("Your health: " + role.getMaxHealth() + "/" + role.getCoreHealth());
		System.out.println("You gained " + basicMonster.getExperienceWeight() + " experience.");
		
		//Chance of gain health/mana potion
		PotionHandler.healthPotionDrop(role);
		PotionHandler.manaPotionDrop(role);
		
		// Level UP
		levelingUp(role, input);
	}

	public static void levelingUp(Role role, Scanner input) {
		int currentLevel = role.getLevel();
		int currentExp = role.getExperience();
		int expRequired = role.getLevelDivider() * currentLevel;

		if (currentExp >= role.getLevelDivider()) {
			// Level up
			role.setLevel(currentLevel + 1);

			// Increase level divider by 30%
			int newLevelDivider = (int) (role.getLevelDivider() * 1.3f);
			role.setLevelDivider(newLevelDivider);

			// Reset remaining experience to zero or add it to next level's requirements
			int remainingExp = currentExp - expRequired;
			role.setExperience(Math.max(remainingExp, 0));

			// Ask user to choose an upgrade
			System.out.println("+++++++++++ =========== +++++++++++");
			System.out.println("You gained enough experience for an upgrade!");
			System.out.println("What do you want to upgrade?");
			System.out.println("\t1. Increase maximum attack damage by 5");
			System.out.println("\t2. Increase health/mana potions drop chance by 5%");
			System.out.println("\t3. Increase maximum health by 10");
			String upgrade = input.nextLine();
			while (!upgrade.equals("1") && !upgrade.equals("2") && !upgrade.equals("3")) {
				System.out.println("Invalid Command!");
				upgrade = input.nextLine();
			}
			switch (upgrade) {
				case "1" -> {
					role.setAttackDmg(role.getAttackDmg() + 5);
					System.out.println("You upgraded your weapon, it now deals additional " + role.getAttackDmg() + " damage!");
				}
				case "2" -> {
					role.setPotionDropChance(role.getPotionDropChance() + 5);
					System.out.println("You upgraded your health potions drop chance, it now has " + role.getPotionDropChance() + "% drop chance!");
				}
				case "3" -> {
					role.setCoreHealth(role.getCoreHealth() + 10);
					role.setMaxHealth(role.getMaxHealth() + 10);
					System.out.println("You upgraded your maximum health, you now have " + role.getCoreHealth() + " health!");
				}
			}
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
