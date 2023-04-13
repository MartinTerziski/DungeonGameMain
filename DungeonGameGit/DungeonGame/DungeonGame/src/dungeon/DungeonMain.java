package dungeon;

import java.util.Scanner;

import dungeon.actions.Combat;
import dungeon.actions.StartContinueAdventure;
import dungeon.basicmonsters.BasicMonster;
import dungeon.basicmonsters.BasicMonsterFactory;
import dungeon.actions.CounterMonsters;
import dungeon.roles.RoleFactory;
import dungeon.roles.Spells;
import dungeon.roles.Role;

public class DungeonMain {

	public static void main(String[] args) {

		CounterMonsters counterMonsters = new CounterMonsters(0, 0, 0, 0, 0);
		BasicMonsterFactory basicMonsterFactory = new BasicMonsterFactory(counterMonsters);
		RoleFactory roleFactory = new RoleFactory();

		Scanner input = new Scanner(System.in);
		boolean running = true;
		
		//Choosing a starting hero
		Role chosenRole = roleFactory.getCharacter(input, counterMonsters);
		Spells spells = chosenRole.getSpells();
		
		//Begin journey
		StartContinueAdventure.beginningJourney(chosenRole);

		Combat combat = new Combat(chosenRole, spells, input);
		
		//Running the game
		while(running) {
			//Randomize the monster
			BasicMonster basicMonster;

			do {
				basicMonster = basicMonsterFactory.getMonster();
			} while (counterMonsters.isMaxReached(basicMonster.getName()));
			
			//Actions in battle
			if(combat.runCombat(basicMonster, chosenRole, spells, input)) break;
			
			//Should you go on?
			running = StartContinueAdventure.continueAdventure(chosenRole, input);
			
		}
	}

}
