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
	
	private static Random random = new Random();
    
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
			if(action.equals("1")) {
				int dmgDealt = random.nextInt(role.getAttackDmg());
				int dmgTaken;
				//Range characters take 10% less damage
				if(role instanceof SpellInvoker || role instanceof Marksman)
					dmgTaken = (int) (random.nextInt(basicMonster.getBasicMonsterAttack())*(90/100.0f));
				else
					dmgTaken = random.nextInt(basicMonster.getBasicMonsterAttack());
				if(role instanceof Juggernaut) {
					JuggernautSpells.setupImmunized(spells, role, dmgTaken);
				}
				if(role instanceof SpellInvoker) {
					int randomBscMonsterDmg = random.nextInt(basicMonster.getBasicMonsterAttack());
					SpellInvokerSpells.calculateReducedDamage(spells, role, dmgTaken, randomBscMonsterDmg);
				}
				if(role instanceof Marksman) {
					MarksmanSpells.applyPoison(spells, basicMonster, role, false);
				}
				if(calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken)) return true;
			} else if(action.equals("2")) {
				System.out.println("Which spell do you want to use?");
				System.out.println("Current mana: " + role.getMaxMana() + "/" + role.getCoreMana());
				System.out.println(role.spells());
				String spell = input.nextLine();
				if(SpellsHandler.useSpell(spells, role, basicMonster, spell)) return true;				
			} else if(action.equals("3")) {
				PotionHandler.usePotion(role, action);
			} else if(action.equals("4")) {
				PotionHandler.usePotion(role, action);
			} else if(action.equals("5")) {
				System.out.println("You mount your horse and run away from the battle!");
				return true;
			} else {
				System.out.println("Invalid command!");
			}
		}
		if(role.getMaxHealth() < 1) {
			System.out.println("You died in battle...");
			return true;
		}
		
		//Result from fight
		resultFromFight(basicMonster, role, spells, random, input);
		return false;
	}
    
    public static void resultFromFight(BasicMonster basicMonster, Role role, Spells spells, Random random, Scanner input) {
		System.out.println("+++++++++++ =========== +++++++++++");
		role.setExperience(role.getExperience() + basicMonster.getExperienceWeight());
		if(role instanceof SpellInvoker) ((Spells) spells).setReducedDamageStack(0);
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
		if(role.getExperience()/role.getLevelDivider()>=role.getLevel()) {
			System.out.println("+++++++++++ =========== +++++++++++");
			System.out.println("You gained enough experience for an upgrade!");
			System.out.println("What do you want to upgrade?");
			System.out.println("\t1. Attack Damage by 5");
			System.out.println("\t2. Increase Health/Mana Potions drop chance by 5%");
			System.out.println("\t3. Maximum health by 10");
			String upgrade = input.nextLine();
			while(!upgrade.equals("1") && !upgrade.equals("2") && !upgrade.equals("3")) {
				System.out.println("Invalid Command!");
				upgrade = input.nextLine();
			}
			if(upgrade.equals("1")) {
				role.setAttackDmg(role.getAttackDmg() + 5);
				System.out.println("You upgraded your weapon, it now deals " + role.getAttackDmg() + " damage!");
			} else if(upgrade.equals("2")) {
				role.setPotionDropChance(role.getPotionDropChance() + 5);
				System.out.println("You upgraded your health potions drop chance, it now has " + role.getPotionDropChance() + "% drop chance!");
			} else if(upgrade.equals("3")) {
				role.setCoreHealth(role.getCoreHealth() + 10);
				role.setMaxHealth(role.getMaxHealth() + 10);
				System.out.println("You upgraded your maximum health, you now have " + role.getCoreHealth() + " health!");
			}
			int levelAddition = (int)(role.getLevelDivider()*(10/100.0f));
			role.setLevelDivider(role.getLevelDivider() + levelAddition);
			role.setLevel(role.getLevel() + 1);
		}
	}
    
    public static boolean calculateAfterSwing(Role role, BasicMonster basicMonster, int dmgDealt, int dmgTaken) {
    	basicMonster.setMaxBasicMonsterHealth(basicMonster.getMaxBasicMonsterHealth() - dmgDealt);
		role.setMaxHealth(role.getMaxHealth() - dmgTaken);
		System.out.println("You deal " + dmgDealt + " damage to the " + basicMonster.getName() + ".");
		System.out.println(ConsoleColors.RED + "You receive " + dmgTaken + " damage." + ConsoleColors.RESET);
		if(role.getMaxHealth() < 1) {
			System.out.println("You died...");
			return true;
		} else {
			return false;
		}
    }

}
