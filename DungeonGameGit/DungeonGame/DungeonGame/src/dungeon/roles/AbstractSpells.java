package dungeon.roles;

public abstract class AbstractSpells implements Spells {

    // default implementations for some methods
    public int getEmpowerAttack() {
        return 0;
    }

    public int getImmunizingShield() {
        return 0;
    }
    
    public int getImmunized() {
		return 0;
	}
    
    public void setImmunized(int spell) {}

    public int getStormBolt() {
        return 0;
    }

    public int getFireComet() {
        return 0;
    }

    public int getIceShard() {
        return 0;
    }

    public int getReducedDamage() {
        return 0;
    }
    
    public int getReducedDamageStack() {
        return 0;
    }
    
    public void setReducedDamageStack(int spell) {}

    public int getPoisonArrow() {
        return 0;
    }

    public int getPoisonDmg() {
        return 0;
    }
    
    public int getPoisonStack() {
		return 0;
	}
    
    public void setPoisonStack(int spell) {}

    public int getFollowupArrows() {
        return 0;
    }

    public int getCritChance() {
        return 0;
    }

    public int getCritArrow() {
        return 0;
    }

}