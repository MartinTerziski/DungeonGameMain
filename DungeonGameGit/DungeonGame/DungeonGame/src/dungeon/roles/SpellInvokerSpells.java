package dungeon.roles;

import dungeon.actions.Combat;
import dungeon.actions.SpellsHandler;
import dungeon.basicmonsters.BasicMonster;

public class SpellInvokerSpells extends AbstractSpells {

    // implementation specific to SpellInvokerSpells
    public int stormBolt;
    public int fireComet;
    public int iceShard;
    public int reducedDamage;
    public int reducedDamageStack;

    public SpellInvokerSpells(int stormBolt, int fireComet, int iceShard, 
            int reducedDamage, int reducedDamageStack) {
        this.stormBolt = stormBolt;
        this.fireComet = fireComet;
        this.iceShard = iceShard;
        this.reducedDamage = reducedDamage;
        this.reducedDamageStack = reducedDamageStack;
    }

    // override some methods if needed
    @Override
    public int getStormBolt() {
        return stormBolt;
    }

    @Override
    public int getFireComet() {
        return fireComet;
    }

    @Override
    public int getIceShard() {
        return iceShard;
    }

    @Override
    public int getReducedDamage() {
        return reducedDamage;
    }
    
    @Override
    public int getReducedDamageStack() {
        return reducedDamageStack;
    }
    
    public static boolean resolveStormBolt(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
    	int stormBolt = ((Spells) spells).getStormBolt();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You use the power of the storms!***");
		return battleResolveAfterSpellInvokerSpell(spells, role, basicMonster, input, stormBolt);
    }
    
    public static boolean resolveFireComet(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
    	int fireComet = ((Spells) spells).getFireComet();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You use the power of the fire!***");
		return battleResolveAfterSpellInvokerSpell(spells, role, basicMonster, input, fireComet);
    }
    
    public static boolean resolveIceShard(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
    	int iceShard = ((Spells) spells).getIceShard();
		role.setMaxMana(role.getMaxMana() - 10);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You use the power of the ice!***");
		return battleResolveAfterSpellInvokerSpell(spells, role, basicMonster, input, iceShard);
    }
    
    private static boolean battleResolveAfterSpellInvokerSpell(Spells spells, Role role, BasicMonster basicMonster, String input, int spell) {
		int dmgDealt;
		int dmgTaken;
		int randomBscMonsterDmg = SpellsHandler.random.nextInt(basicMonster.getBasicMonsterAttack());
		switch(input) {
		case "1":
			dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
			//Range characters take 10% less damage
			dmgTaken = (int) (randomBscMonsterDmg*(90/100.0f));
			calculateReducedDamage(spells, role, dmgTaken, randomBscMonsterDmg);
			if(Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken)) return true;
			break;
		case "2":
			dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
			//Range characters take 10% less damage
			dmgTaken = (int) (randomBscMonsterDmg*(90/100.0f));
			calculateReducedDamage(spells, role, dmgTaken, randomBscMonsterDmg);
			if(Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken)) return true;
			break;
		case "3":
			dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
			((Spells) spells).setReducedDamageStack(((Spells) spells).getReducedDamageStack() + 2);
			//Range characters take 10% less damage
			dmgTaken = (int) (randomBscMonsterDmg*(90/100.0f));
			calculateReducedDamage(spells, role, dmgTaken, randomBscMonsterDmg);	
			if(Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken)) return true;
			break;
		}
		return false;
	}
    
    public static void calculateReducedDamage(Spells spells, Role role, int dmgTaken, int randomBscMonsterDmg) {
		if(((Spells) spells).getReducedDamageStack() > 0) {
			dmgTaken -= randomBscMonsterDmg*(((Spells) spells).getReducedDamage()/100.0f);
			((Spells) spells).setReducedDamageStack(((Spells) spells).getReducedDamageStack() - 1);
		}
	}
}
