package dungeon.roles;

public interface Spells {
	
	//Juggernaut Spells:
	public int getEmpowerAttack();
	public int getImmunizingShield();
	public int getImmunized();
	public void setImmunized(int spell);
	
	//SpellInvoker Spells:
	public int getStormBolt();
	public int getFireComet();
	public int getIceShard();
	public int getReducedDamage();
	public int getReducedDamageStack();
	public void setReducedDamageStack(int spell);
	
	//Marksman Spells:
	public int getPoisonArrow();
	public int getPoisonStack();
	public void setPoisonStack(int spell);
	public int getPoisonDmg();
	public int getFollowupArrows();
	public int getCritChance();
	public int getCritArrow();
	
	
}
