package dungeon.roles;

public interface Spells {
	
	//Juggernaut Spells:
	int getEmpowerAttack();
	void setEmpowerAttack(int spell);

	int getImmunizingShield();
	void setImmunizingShield(int spell);

	int getImmunized();
	void setImmunized(int spell);
	
	//SpellInvoker Spells:
	int getStormBolt();
	void setStormBolt(int spell);

	int getFireComet();
	void setFireComet(int spell);

	int getIceShard();
	void setIceShard(int spell);

	int getReducedDamage();
	void setReducedDamage(int spell);

	int getReducedDamageStack();
	void setReducedDamageStack(int spell);
	
	//Marksman Spells:
	int getPoisonArrow();
	void setPoisonArrow(int spell);

	int getPoisonStack();
	void setPoisonStack(int spell);

	int getPoisonDmg();
	void setPoisonDmg(int spell);

	int getFollowupArrows();
	void setFollowupArrows(int spell);

	int getCritChance();
	void setCritChance(int spell);

	int getCritArrow();
	void setCritArrow(int spell);
	
	
}
