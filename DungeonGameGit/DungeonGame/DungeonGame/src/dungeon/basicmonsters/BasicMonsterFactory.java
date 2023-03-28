package dungeon.basicmonsters;

import java.util.Random;

public class BasicMonsterFactory {
	
	public BasicMonster getMonster() {
		Random random = new Random();
		BasicMonster basicMonster = null;
		System.out.println("+++++++++++ =========== +++++++++++");
		while (true) {
			switch (random.nextInt(5)) {
			case 0:
				basicMonster = new Imp("Imp", 30, 15, 10);
				break;
			case 1:
				basicMonster = new Wolf("Wolf", 25, 15, 10);
				break;
			case 2:
				basicMonster = new Ooze("Ooze", 20, 15, 10);
				break;
			case 3:
				basicMonster = new CorruptedVillager("Corrupted Villager", 35, 15, 15);
				break;
			case 4:
				basicMonster = new Wraith("Wraith", 40, 20, 20);
				break;
			}
			
			if ("AEIOUY".indexOf(basicMonster.getName().charAt(0))>=0) {
				System.out.println("An " + basicMonster.getName() + " is facing you!");
			} else {
				System.out.println("A " + basicMonster.getName() + " is facing you!");
			}
			return basicMonster;
		}
	}
}
