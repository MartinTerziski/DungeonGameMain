package dungeon.roles;

import dungeon.actions.Combat;
import dungeon.actions.SpellsHandler;
import dungeon.basicmonsters.BasicMonster;

import java.util.Scanner;


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
    	int stormBolt = spells.getStormBolt();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You use the power of the storms!***");
		return battleResolveAfterSpellInvokerSpell(spells, role, basicMonster, input, stormBolt);
    }
    
    public static boolean resolveFireComet(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
    	int fireComet = spells.getFireComet();
		role.setMaxMana(role.getMaxMana() - manaWeight);
		if(SpellsHandler.notEnoughMana(role, manaWeight)) return false;
		System.out.println("***You use the power of the fire!***");
		return battleResolveAfterSpellInvokerSpell(spells, role, basicMonster, input, fireComet);
    }
    
    public static boolean resolveIceShard(int manaWeight, Spells spells, Role role,
			BasicMonster basicMonster, String input) {
    	int iceShard = spells.getIceShard();
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
			case "1", "2" -> {
				dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
				//Range characters take 10% less damage
				dmgTaken = (int) (randomBscMonsterDmg*(90/100.0f));
				calculateReducedDamage(spells);
				if(Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, false)) return true;
			}
		//Range characters take 10% less damage
			case "3" -> {
				dmgDealt = SpellsHandler.random.nextInt(role.getAttackDmg()) + spell;
				spells.setReducedDamageStack(spells.getReducedDamageStack() + 2);
				//Range characters take 10% less damage
				dmgTaken = (int) (randomBscMonsterDmg*(90/100.0f));
				calculateReducedDamage(spells);
				if(Combat.calculateAfterSwing(role, basicMonster, dmgDealt, dmgTaken, false)) return true;
			}
		}
		return false;
	}
    
    public static void calculateReducedDamage(Spells spells) {
		if(spells.getReducedDamageStack() > 0) {
			spells.setReducedDamageStack(spells.getReducedDamageStack() - 1);
		}
	}

    public static boolean upgradeSpellInvokerSpells(Role role, Scanner input) {
        System.out.println("\t1. Empower Storm Bolt's damage by 10");
        System.out.println("\t2. Empower Fire Comet's damage by 20");
        System.out.println("\t3. Empower Ice Shard's initial damage by 10 and reduced damage by 5%");
        System.out.println("\t4. Return to upgrade menu");
        String spellUpgrade = input.nextLine();
        while (!spellUpgrade.equals("1") && !spellUpgrade.equals("2")
                && !spellUpgrade.equals("3") && !spellUpgrade.equals("4")) {
            System.out.println("Invalid Command!");
            spellUpgrade = input.nextLine();
        }
        switch (spellUpgrade) {
            case "1" -> {
                // Upgrade Storm Bolt
                int newStormBolt = role.getSpells().getStormBolt() + 10;
                role.getSpells().setStormBolt(newStormBolt);
                System.out.println("You upgraded Storm Bolt!" +
                        " It now deals " + newStormBolt + " damage!");
                return false;
            }
            case "2" -> {
                // Upgrade Fire Comet
                int newFireComet = role.getSpells().getFireComet() + 1;
                role.getSpells().setFireComet(newFireComet);
                System.out.println("You upgraded Fire Comet! " +
                        "It now deals " + newFireComet + " damage!");
                return false;
            }
            case "3" -> {
                // Upgrade Ice Shard
                int newIceShard = role.getSpells().getIceShard() + 10;
                int newReducedDamage = role.getSpells().getReducedDamage() + 5;
                if(newReducedDamage > 100) {
                    System.out.println("Maximum reduced damage from Ice Shard is reached!" +
                            "Please choose another upgrade");
                    return true;
                }
                role.getSpells().setIceShard(newIceShard);
                role.getSpells().setReducedDamage(newReducedDamage);
                System.out.println("You upgraded Ice Shard! " +
                        "It now deals " + newIceShard + " damage with reduction of the next 2 attacks for " + newReducedDamage +"%!");
                return false;
            }
            case "4" -> {
                System.out.println("Returning to upgrade menu...");
                return true;
            }
        }
        return true;
    }
}
