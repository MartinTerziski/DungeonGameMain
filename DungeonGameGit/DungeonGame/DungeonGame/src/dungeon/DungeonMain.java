package dungeon;

import java.util.Scanner;

import dungeon.actions.Combat;
import dungeon.actions.StartContinueAdventure;
import dungeon.basicmonsters.BasicMonster;
import dungeon.basicmonsters.BasicMonsterFactory;
import dungeon.roles.RoleFactory;
import dungeon.roles.Spells;
import dungeon.roles.Role;

public class DungeonMain {

	public static void main(String[] args) {
		
		BasicMonsterFactory basicMonsterFactory = new BasicMonsterFactory();
		RoleFactory roleFactory = new RoleFactory();
		Scanner input = new Scanner(System.in);
		boolean running = true;
		
		//Choosing a starting hero
		Role chosenRole = roleFactory.getCharacter(input);
		Spells spells = chosenRole.getSpells();
		
		//Begin journey
		StartContinueAdventure.beginningJourney(chosenRole);		
		
		//Running the game
		while(running) {
			//Randomize the monster
			BasicMonster basicMonster = basicMonsterFactory.getMonster();
			
			//Actions in battle
			if(Combat.calculateCombat(basicMonster, chosenRole, spells, input)) break;
			
			//Should you go on?
			running = StartContinueAdventure.continueAdventure(chosenRole, input);
			
		}
	}

}
