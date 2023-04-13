package dungeon.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import dungeon.basicmonsters.*;
import dungeon.roles.*;
import dungeon.utilites.ConsoleColors;

public class Combat {

	private static final Map<String, Integer> stolenPotions = new HashMap<>();
	private static int increasedVillagerDamage = 0;
	private static int decreasedOozeDamage = 0;
	private static int overtimeWolfDamage = 0;
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
			System.out.println("\t3. Drink Health potion! (You have " + role.getPotionHandler().getHealthPotions() + "/5 potions)");
			System.out.println("\t4. Drink Mana potion! (You have " + role.getPotionHandler().getManaPotions() + "/5 potions)");
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
					role.getPotionHandler().usePotion(role, action);
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

		//After defeated monster
		switch (basicMonster.getName()) {
			case "Imp" -> {
				if(!stolenPotions.isEmpty()) {
					int healthPotions = 0;
					int manaPotions = 0;
					if(stolenPotions.get("health")!=null) healthPotions = stolenPotions.get("health");
					if(stolenPotions.get("mana")!=null) manaPotions = stolenPotions.get("mana");
					if(healthPotions!=0) {
						role.getPotionHandler().setHealthPotions(role.getPotionHandler().getHealthPotions() + healthPotions);
						System.out.println("+++++++++++ =========== +++++++++++");
						System.out.println("You retrieved " + healthPotions + " health potions from the Imp!");
						System.out.println("+++++++++++ =========== +++++++++++");
					}
					if(manaPotions!=0) {
						role.getPotionHandler().setManaPotions(role.getPotionHandler().getManaPotions() + manaPotions);
						System.out.println("+++++++++++ =========== +++++++++++");
						System.out.println("You retrieved " + manaPotions + " mana potions from the Imp!");
						System.out.println("+++++++++++ =========== +++++++++++");
					}
					stolenPotions.clear();
				}
			}
			case "Ooze" -> {
				if(decreasedOozeDamage > 0) {
					role.setAttackDmg(role.getAttackDmg() + decreasedOozeDamage);
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("You cleaned up your weapon after the fight and strengthen it for " + decreasedOozeDamage + " damage!");
					System.out.println("+++++++++++ =========== +++++++++++");
					decreasedOozeDamage = 0;
				}
			}
			case "Wolf" -> {
				if(overtimeWolfDamage > 0) {
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("You patched your wounds up and you do not bleed anymore for " + overtimeWolfDamage + " damage!");
					System.out.println("+++++++++++ =========== +++++++++++");
					overtimeWolfDamage = 0;
				}
			}
		}

		//Chance of gain health/mana potion
		role.getPotionHandler().healthPotionDrop();
		role.getPotionHandler().manaPotionDrop();

		// Level UP
		if (role.getLevelUp().getExperience() >= role.getLevelUp().getLevelDivider()) {
			role.getLevelUp().levelingUp(role, input);
		}
	}

    public static boolean calculateAfterSwing(Role role, BasicMonster basicMonster, int dmgDealt, int dmgTaken, boolean isDrinkingPotion) {
		basicMonster.setMaxBasicMonsterHealth(basicMonster.getMaxBasicMonsterHealth() - dmgDealt);
		boolean willMonsterCastSpell = random.nextInt(100) < 20;
		if(willMonsterCastSpell) {
			usedMonsterSpell(basicMonster, role, dmgTaken);
			switch (basicMonster.getName()) {
				case "Corrupted Villager" -> {
					resolveAfterAttack(role, basicMonster, increasedVillagerDamage, dmgDealt, isDrinkingPotion);
					increasedVillagerDamage = 0;
				}
				case "Wolf" -> {
					int damageWithBite = dmgTaken + overtimeWolfDamage;
					role.setMaxHealth(role.getMaxHealth() - damageWithBite);
					System.out.println(ConsoleColors.RED + "You receive " + dmgTaken + " damage " +
							"with additional bite damage for " + overtimeWolfDamage + "." + ConsoleColors.RESET);
					if(!isDrinkingPotion) System.out.println("You deal " + dmgDealt + " damage to the " + basicMonster.getName() + ".");
				}
				case "Ooze", "Imp", "Wraith" -> resolveAfterAttack(role, basicMonster, dmgTaken, dmgDealt, isDrinkingPotion);
			}
		} else {
			resolveAfterAttack(role, basicMonster, dmgTaken, dmgDealt, isDrinkingPotion);
		}

		if(role.getMaxHealth() < 1) {
			System.out.println("You died...");
			return true;
		} else {
			return false;
		}
    }

	public static void resolveAfterAttack(Role role, BasicMonster basicMonster, int dmgTaken, int dmgDealt, boolean isDrinkingPotion) {
		role.setMaxHealth(role.getMaxHealth() - dmgTaken);
		System.out.println(ConsoleColors.RED + "You receive " + dmgTaken + " damage." + ConsoleColors.RESET);
		if(!isDrinkingPotion) System.out.println("You deal " + dmgDealt + " damage to the " + basicMonster.getName() + ".");
	}

	public static void usedMonsterSpell(BasicMonster basicMonster, Role role, int dmgTaken) {
		Random random = new Random();
		switch (basicMonster.getName()) {
			case "Corrupted Villager" -> {
				increasedVillagerDamage = dmgTaken + ((CorruptedVillager) basicMonster).getSpell();
				System.out.println("+++++++++++ =========== +++++++++++");
				System.out.println("Corrupted Villager casts 'Rage Of The People'! " +
						"It deals " + ((CorruptedVillager) basicMonster).getSpell() + " additional damage to you!");
				System.out.println("+++++++++++ =========== +++++++++++");
			}
			case "Imp" -> {
				String namePotion = "none";
				if (random.nextInt(100) < 50 && role.getPotionHandler().getHealthPotions() > 0) {
					role.getPotionHandler().setHealthPotions(role.getPotionHandler().getHealthPotions() - 1);
					namePotion = "health";
					stolenPotions.merge(namePotion, 1, Integer::sum);
				} else if(role.getPotionHandler().getManaPotions() > 0){
					role.getPotionHandler().setManaPotions(role.getPotionHandler().getManaPotions() - 1);
					namePotion = "mana";
					stolenPotions.merge(namePotion, 1, Integer::sum);
				}
				if(namePotion.equals("none")) {
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("Imp casts 'Ritual Robbing'! " +
							"It does not steal a potion, since you do not posses one of that type!");
					System.out.println("+++++++++++ =========== +++++++++++");
				} else {
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("Imp casts 'Ritual Robbing'! " +
							"It steals a " + namePotion + " potion from you!");
					System.out.println("+++++++++++ =========== +++++++++++");
				}
			}
			case "Ooze" -> {
				int localReducedDamage = ((Ooze) basicMonster).getSpell();
				if(role.getAttackDmg() > localReducedDamage) {
					role.setAttackDmg(role.getAttackDmg() - localReducedDamage);
					decreasedOozeDamage += localReducedDamage;
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("Ooze casts 'Sticky Weapon'! " +
							"It reduces your maximum attack damage by " + localReducedDamage + "!");
					System.out.println("+++++++++++ =========== +++++++++++");
				} else {
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("Ooze casts 'Sticky Weapon'! " +
							"It does not reduce your maximum attack damage, since it reached its minimum, " + role.getAttackDmg() + "!");
					System.out.println("+++++++++++ =========== +++++++++++");
				}

			}
			case "Wolf" -> {
				overtimeWolfDamage += ((Wolf) basicMonster).getSpell();
				System.out.println("+++++++++++ =========== +++++++++++");
				System.out.println("Wolf casts 'Bite'! " +
						"It is doing overtime damage by " + ((Wolf) basicMonster).getSpell() + "!" +
						" (Stacked up to " + overtimeWolfDamage + " damage)");
				System.out.println("+++++++++++ =========== +++++++++++");
			}
			case "Wraith" -> {
				int reducedMana = ((Wraith) basicMonster).getSpell();
				int reducedManaResult;
				if(role.getMaxMana() > 0) {
					if(role.getMaxMana() - reducedMana >= 0) {
						reducedManaResult = role.getMaxMana() - reducedMana;
						role.setMaxMana(reducedManaResult);
						reducedManaResult = reducedMana;
					} else {
						reducedManaResult = role.getMaxMana();
						role.setMaxMana(0);
					}
					System.out.println("+++++++++++ =========== +++++++++++");
					System.out.println("Wraith casts 'Thirst For Power'! " +
							"It reduces " + reducedManaResult + " points from your mana!");
					System.out.println("+++++++++++ =========== +++++++++++");
				}
			}
		}
	}
}