package dungeon.roles;

import dungeon.actions.Combat;
import dungeon.actions.SpellsHandler;
import dungeon.basicmonsters.BasicMonster;
import lombok.Data;

import java.util.Scanner;

@Data
public class JuggernautSpells extends AbstractSpells{
	
	public int empowerAttack;
	public int immunizingShield;
	public int immunized;
	
	public JuggernautSpells(int empowerAttack, int immunizingShield, int immunized) {
		this.empowerAttack = empowerAttack;
		this.immunizingShield = immunizingShield;
		this.immunized = immunized;
	}
	
	@Override
	public int getEmpowerAttack() {
		return empowerAttack;
	}
	
	@Override
	public int getImmunizingShield() {
		return immunizingShield;
	}
	
	@Override
	public int getImmunized() {
		return immunized;
	}
	
	public static boolean resolveImmunizingShield(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
		int immunizingShield = spells.getImmunizingShield();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You immunize yourself from " + immunizingShield + " attacks!***");
		return battleResolveAfterJuggernautSpell(spells, role, basicMonster, input, immunizingShield);
	}
	
	public static boolean resolveEmpowerAttack(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
		int empowerAttack = spells.getEmpowerAttack();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You empower your weapon for " + empowerAttack + " damage!***");
		return battleResolveAfterJuggernautSpell(spells, role, basicMonster, input, empowerAttack);
	}
	
	private static boolean battleResolveAfterJuggernautSpell(Spells spells, Role role, BasicMonster basicMonster, String input, int spell) {
		int dmgDealt;
		int dmgTaken;
		switch (input) {
			case "1" -> {
				dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
				dmgTaken = SpellsHandler.random.nextInt(basicMonster.getBasicMonsterAttack());
				setupImmunized(spells, dmgTaken);
				if (Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, false)) return true;
			}
			case "2" -> spells.setImmunized(spell);
		}
		return false;
	}
	
	public static int setupImmunized(Spells spells, int dmgTaken) {
		if(spells.getImmunized() > 0) {
			spells.setImmunized(spells.getImmunized() - 1);
			return 0;
		} else {
			return dmgTaken;
		}

	}

	public static boolean upgradeJuggernautSpells(Role role, Scanner input) {
		System.out.println("\t1. Empower Attack by 10 damage");
		System.out.println("\t2. Immunizing Shield for one more turn");
		System.out.println("\t3. Return to upgrade menu");
		String spellUpgrade = input.nextLine();
		while (!spellUpgrade.equals("1") && !spellUpgrade.equals("2") && !spellUpgrade.equals("3")) {
			System.out.println("Invalid Command!");
			spellUpgrade = input.nextLine();
		}
		switch (spellUpgrade) {
			case "1" -> {
				// Upgrade Empower Attack
				int newEmpowerAttack = role.getSpells().getEmpowerAttack() + 10;
				role.getSpells().setEmpowerAttack(newEmpowerAttack);
				System.out.println("You upgraded Empower Attack! The next attack is empowered by " + newEmpowerAttack + " damage!");
				return false;
			}
			case "2" -> {
				// Upgrade Immunizing Shield
				int newImmunizingShield = role.getSpells().getImmunizingShield() + 1;
				role.getSpells().setImmunizingShield(newImmunizingShield);
				System.out.println("You upgraded Immunizing Shield! The next " + newImmunizingShield + " attacks are negated!");
				return false;
			}
			case "3" -> {
				System.out.println("Returning to upgrade menu...");
				return true;
			}
		}
		return true;
	}
}
