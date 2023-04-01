package dungeon.roles;

import dungeon.actions.Combat;
import dungeon.actions.SpellsHandler;
import dungeon.basicmonsters.BasicMonster;
import lombok.Data;

@Data
public class MarksmanSpells extends AbstractSpells{
	
	public int poisonArrow;
	public int poisonDmg;
	public int poisonStack;
	public int followupArrows;
	public int critArrow;
	public int critChance;
	
	public MarksmanSpells(int poisonArrow, int poisonDmg, int poisonStack,
			int followupArrows, int critArrow,
			int critChance) {
		this.poisonArrow = poisonArrow;
		this.poisonDmg = poisonDmg;
		this.poisonStack = poisonStack;
		this.followupArrows = followupArrows;
		this.critArrow = critArrow;
		this.critChance = critChance;
	}
	
	@Override
    public int getPoisonArrow() {
        return poisonArrow;
    }

    @Override
    public int getPoisonDmg() {
        return poisonDmg;
    }

    @Override
    public int getPoisonStack() {
        return poisonStack;
    }
    
    @Override
    public int getFollowupArrows() {
        return followupArrows;
    }

    @Override
    public int getCritArrow() {
        return critArrow;
    }
    
    @Override
    public int getCritChance() {
        return critChance;
    }
    
    public static boolean resolvePoisonArrow(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
    	int poisonArrow = spells.getPoisonArrow();
		int poisonDamage = spells.getPoisonDmg();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You use a posioned arrow!***");
		return battleResolveAfterMarksmanSpell(spells, role, basicMonster, input, poisonArrow, poisonDamage);
    }
    
    public static boolean resolveFollowupArrows(int manaWeight, Spells spells, Role role,
    		BasicMonster basicMonster, String input) {
    	int followupArrows = spells.getFollowupArrows();
    	role.setMaxMana(role.getMaxMana() - manaWeight);
    	if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
    	System.out.println("***You fire consecutive shots!***");
    	return battleResolveAfterMarksmanSpell(spells, role, basicMonster, input, followupArrows, 0);
    }
    
    public static boolean resolveCriticalArrow(int manaWeight, Spells spells, Role role,
    		BasicMonster basicMonster, String input) {
    	int critArrow = spells.getCritArrow();
		int critChance = spells.getCritChance();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You fire an arrow to a weak spot!***");
		return battleResolveAfterMarksmanSpell(spells, role, basicMonster, input, critArrow, critChance);    	
    }
    
    private static boolean battleResolveAfterMarksmanSpell(Spells spells, Role role, BasicMonster basicMonster, String input, int spellPart1, int spellPart2) {
		int dmgDealt;
		int dmgTaken;
		int randomBscMonsterDmg = SpellsHandler.random.nextInt(basicMonster.getBasicMonsterAttack());
		switch (input) {
			case "1" -> {
				dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spellPart1;
				//Range characters take 10% less damage
				dmgTaken = (int) (randomBscMonsterDmg * (90 / 100.0f));
				if (Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, false)) return true;
				applyPoison(spells, basicMonster, true);
			}
			case "2" -> {
				dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) * spellPart1;
				//Range characters take 10% less damage
				dmgTaken = (int) (randomBscMonsterDmg * (90 / 100.0f));
				if (Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, false)) return true;
				applyPoison(spells, basicMonster, false);
			}
			case "3" -> {
				dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg());
				//Range characters take 10% less damage
				dmgTaken = (int) (randomBscMonsterDmg * (90 / 100.0f));
				if (spellPart2 < SpellsHandler.random.nextInt(100)) {
					System.out.println("*+*+*You hit the weak spot!*+*+*");
					dmgDealt += spellPart1 * role.getAttackDmg();
				}
				if (Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, false)) return true;
				applyPoison(spells, basicMonster, false);
			}
		}
		return false;
	}
    
    public static void applyPoison(Spells spells, BasicMonster basicMonster, boolean isInitialShot) {
		if(basicMonster.getMaxBasicMonsterHealth() > 0) {
			if(isInitialShot) spells.setPoisonStack(spells.getPoisonStack() + 2);
			if(spells.getPoisonStack() > 0) {
				System.out.println("The monster receives " + spells.getPoisonDmg() + " additional damage!");
				basicMonster.setMaxBasicMonsterHealth(basicMonster.getMaxBasicMonsterHealth() - spells.getPoisonDmg());
				spells.setPoisonStack(spells.getPoisonStack() - 1);
			}
		}
	}
}
