package dungeon.roles;

import dungeon.actions.Combat;
import dungeon.actions.SpellsHandler;
import dungeon.basicmonsters.BasicMonster;
import lombok.Data;

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
		manaWeight = 10;
		int immunizingShield = ((Spells) spells).getImmunizingShield();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You immunize yourself from " + immunizingShield + " attacks!***");
		return battleResolveAfterJuggernautSpell(spells, role, basicMonster, input, immunizingShield);
	}
	
	public static boolean resolveEmpowerAttack(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
		manaWeight = 5;
		int empowerAttack = ((Spells) spells).getEmpowerAttack();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You empower your weapon for " + empowerAttack + " damage!***");
		return battleResolveAfterJuggernautSpell(spells, role, basicMonster, input, empowerAttack);
	}
	
	private static boolean battleResolveAfterJuggernautSpell(Spells spells, Role role, BasicMonster basicMonster, String input, int spell) {
		int dmgDealt;
		int dmgTaken;
		switch(input) {
		case "1":
			dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
			dmgTaken = SpellsHandler.random.nextInt(basicMonster.getBasicMonsterAttack());
			setupImmunized(spells, role, dmgTaken);
			if(Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken)) return true;
			break;
		case "2":
			((Spells) spells).setImmunized(spell);
		}
		return false;
	}
	
	public static void setupImmunized(Spells spells, Role role, int dmgTaken) {
		if(((Spells) spells).getImmunized() > 0) {
			dmgTaken = 0;
			((Spells) spells).setImmunized(((Spells) spells).getImmunized() - 1);
		}
	}
}
