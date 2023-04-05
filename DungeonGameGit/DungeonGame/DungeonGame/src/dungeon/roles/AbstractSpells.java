package dungeon.roles;

public abstract class AbstractSpells implements Spells {

    // default implementations for some methods
    public int getEmpowerAttack() {
        return 0;
    }
    public void setEmpowerAttack(int spell) {}

    public int getImmunizingShield() {
        return 0;
    }
    public void setImmunizingShield(int spell) {}
    
    public int getImmunized() {
		return 0;
	}
    public void setImmunized(int spell) {}

    public int getStormBolt() {
        return 0;
    }
    public void setStormBolt(int spell) {}

    public int getFireComet() {
        return 0;
    }
    public void setFireComet(int spell) {}

    public int getIceShard() {
        return 0;
    }
    public void setIceShard(int spell) {}

    public int getReducedDamage() {
        return 0;
    }
    public void setReducedDamage(int spell) {}
    
    public int getReducedDamageStack() {
        return 0;
    }
    public void setReducedDamageStack(int spell) {}

    public int getPoisonArrow() {
        return 0;
    }
    public void setPoisonArrow(int spell) {}

    public int getPoisonDmg() {
        return 0;
    }
    public void setPoisonDmg(int spell) {}
    
    public int getPoisonStack() {
		return 0;
	}
    public void setPoisonStack(int spell) {}

    public int getFollowupArrows() {
        return 0;
    }
    public void setFollowupArrows(int spell) {}

    public int getCritChance() {
        return 0;
    }
    public void setCritChance(int spell) {}

    public int getCritArrow() {
        return 0;
    }
    public void setCritArrow(int spell) {}

}