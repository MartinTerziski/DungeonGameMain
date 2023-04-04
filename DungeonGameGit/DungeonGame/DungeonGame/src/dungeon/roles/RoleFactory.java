package dungeon.roles;

import dungeon.actions.CounterMonsters;
import dungeon.actions.LevelUp;

import java.util.Scanner;

public class RoleFactory {

	public Role getCharacter(Scanner input, CounterMonsters counterMonsters) {
	    System.out.println("+++++++++++ =========== +++++++++++");
	    System.out.println("Welcome, chosen champion.");

	    boolean exitChoosing = false;
	    Role role = null;
		LevelUp levelUp = new LevelUp(0, 1, 20);

	    while(!exitChoosing) {
	        // Display the available characters to choose from
	        displayAvailableCharacters();

	        String userChoice = input.nextLine();
	        String confirm;
			switch (userChoice) {
				case "1" ->
					// Get the Juggernaut character stats
						role = getJuggernautCharacter(levelUp, counterMonsters);
				case "2" ->
					// Get the Spell-Invoker character stats
						role = getSpellInvokerCharacter(levelUp, counterMonsters);
				case "3" ->
					// Get the Marksman character stats
						role = getMarksmanCharacter(levelUp, counterMonsters);
				case "4" -> {
					// Exit the game
					System.out.println("Exiting game...");
					System.exit(0);
				}
				default -> System.out.println("Invalid command!");
			}

	        if (role != null) {
	            // Confirm character selection
	            confirm = confirmCharacterSelection(input, role);

	            if (confirm.equals("1")) {
	                System.out.println("You have chosen " + role.getName() + ", a " + role.getDescription());
	                exitChoosing = true;
	            }
	        }
	    }

	    return role;
	}

	private void displayAvailableCharacters() {
	    System.out.println("Choose your starting hero: ");
	    System.out.println("\t1. Juggernaut");
	    System.out.println("\t2. Spell-Invoker");
	    System.out.println("\t3. Marksman");
	    System.out.println("\t4. Exit game");
	}

	private Role getJuggernautCharacter(LevelUp levelUp, CounterMonsters counterMonsters) {
		Spells spells = new JuggernautSpells(20, 1, 0);
	    return new Juggernaut(100, 100, 10, 10, 30,
				1, 1, 30, 30,
				levelUp, counterMonsters, spells);
	}

	private Role getSpellInvokerCharacter(LevelUp levelUp, CounterMonsters counterMonsters) {
		Spells spells = new SpellInvokerSpells(30, 60, 10, 20, 0);
	    return new SpellInvoker(80, 80, 50, 50, 5,
				1, 1, 30, 30,
				levelUp, counterMonsters, spells);
	}

	private Role getMarksmanCharacter(LevelUp levelUp, CounterMonsters counterMonsters) {
		Spells spells = new MarksmanSpells(20, 5, 0, 2, 3, 20);
	    return new Marksman(90, 90, 30, 30, 15,
				1, 1, 30, 30,
				levelUp, counterMonsters, spells);
	}

	private String confirmCharacterSelection(Scanner input, Role role) {
	    System.out.println(role.stats());
	    System.out.println("Do you wish to continue with this character?");
	    System.out.println("\t1. Yes");
	    System.out.println("\t2. No");
	    return input.nextLine();
	}
}
