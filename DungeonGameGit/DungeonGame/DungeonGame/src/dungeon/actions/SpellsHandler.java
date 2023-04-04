package dungeon.actions;

import java.util.Random;

import dungeon.basicmonsters.BasicMonster;
import dungeon.roles.Juggernaut;
import dungeon.roles.JuggernautSpells;
import dungeon.roles.Marksman;
import dungeon.roles.MarksmanSpells;
import dungeon.roles.Role;
import dungeon.roles.SpellInvoker;
import dungeon.roles.SpellInvokerSpells;
import dungeon.roles.Spells;

public class SpellsHandler {
	
	public static Random random = new Random();
	
	public static boolean useSpell(Spells spells, Role role, BasicMonster basicMonster, String input) {
		boolean usedSpell = false;
		if(role instanceof Juggernaut) {
			switch (input) {
				case "1" -> usedSpell = JuggernautSpells.resolveEmpowerAttack(5, spells, role, basicMonster, input);
				case "2" -> usedSpell = JuggernautSpells.resolveImmunizingShield(10, spells, role, basicMonster, input);
				case "3" -> System.out.println("Spell canceled...");
				default -> System.out.println("Invalid Command!");
			}
		}
		if(role instanceof SpellInvoker) {
			switch (input) {
				case "1" -> usedSpell = SpellInvokerSpells.resolveStormBolt(5, spells, role, basicMonster, input);
				case "2" -> usedSpell = SpellInvokerSpells.resolveFireComet(20, spells, role, basicMonster, input);
				case "3" -> usedSpell = SpellInvokerSpells.resolveIceShard(10, spells, role, basicMonster, input);
				case "4" -> System.out.println("Spell canceled...");
				default -> System.out.println("Invalid Command!");
			}
		}
		if(role instanceof Marksman) {
			switch (input) {
				case "1" -> usedSpell = MarksmanSpells.resolvePoisonArrow(10, spells, role, basicMonster, input);
				case "2" -> usedSpell = MarksmanSpells.resolveFollowupArrows(5, spells, role, basicMonster, input);
				case "3" -> usedSpell = MarksmanSpells.resolveCriticalArrow(5, spells, role, basicMonster, input);
				case "4" -> System.out.println("Spell canceled...");
				default -> System.out.println("Invalid Command!");
			}
		}
		return usedSpell;
	}
	
	public static boolean notEnoughMana(Role role, int manaWeight) {
		if(role.getMaxMana() < 0) {
			System.out.println("Not enough mana!");
			role.setMaxMana(role.getMaxMana() + manaWeight);
			return true;
		} else {
			return false;
		}
	}
}
