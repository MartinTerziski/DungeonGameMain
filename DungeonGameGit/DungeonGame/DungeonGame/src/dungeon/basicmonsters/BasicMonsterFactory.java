package dungeon.basicmonsters;

import dungeon.actions.CounterMonsters;
import java.util.Random;

public class BasicMonsterFactory {
	private final CounterMonsters counterMonsters;

	public BasicMonsterFactory(CounterMonsters counterMonsters) {
		this.counterMonsters = counterMonsters;
	}

	public BasicMonster getMonster() {
		boolean isNotDefeated = true;
		boolean[] isNotDefeatedArray = { false, false, false, false, false };
		Random random = new Random();
		BasicMonster basicMonster = null;
		System.out.println("+++++++++++ =========== +++++++++++");
		while (isNotDefeated) {
			switch (random.nextInt(5)) {
				case 0 -> {
					counterMonsters.increment("Imp");
					if (!counterMonsters.isMaxReached("Imp")) {
						basicMonster = new Imp("Imp",
								30,
								15,
								12,
								1);
					} else {
						isNotDefeatedArray[0] = true;
						isNotDefeated = areAllDefeated(isNotDefeatedArray);
					}
				}
				case 1 -> {
					counterMonsters.increment("Wolf");
					if (!counterMonsters.isMaxReached("Wolf")) {
						basicMonster = new Wolf("Wolf",
								25,
								15,
								10,
								2);
					} else {
						isNotDefeatedArray[1] = true;
						isNotDefeated = areAllDefeated(isNotDefeatedArray);
					}
				}
				case 2 -> {
					counterMonsters.increment("Ooze");
					if (!counterMonsters.isMaxReached("Ooze")) {
						basicMonster = new Ooze(
								"Ooze",
								20,
								15,
								8,
								5);
					} else {
						isNotDefeatedArray[2] = true;
						isNotDefeated = areAllDefeated(isNotDefeatedArray);
					}
				}
				case 3 -> {
					counterMonsters.increment("Corrupted Villager");
					if (!counterMonsters.isMaxReached("Corrupted Villager")) {
						basicMonster = new CorruptedVillager(
								"Corrupted Villager",
								35,
								15,
								15,
								8);
					} else {
						isNotDefeatedArray[3] = true;
						isNotDefeated = areAllDefeated(isNotDefeatedArray);
					}
				}
				case 4 -> {
					counterMonsters.increment("Wraith");
					if (!counterMonsters.isMaxReached("Wraith")) {
						basicMonster = new Wraith("Wraith",
								40,
								20,
								20,
								4);
					} else {
						isNotDefeatedArray[4] = true;
						isNotDefeated = areAllDefeated(isNotDefeatedArray);
					}
				}
			}
			if (basicMonster != null) {
				if ("AEIOUY".indexOf(basicMonster.getName().charAt(0))>=0) {
					System.out.print("An ");
				} else {
					System.out.print("A ");
				}
				System.out.println(basicMonster.getName() + " is facing you!");
				return basicMonster;
			}
		}

		if(basicMonster==null) {
			//todo code for spawning boss
		}

		return basicMonster;
	}

	public static boolean areAllDefeated(boolean[] isNotDefeatedArray) {
		for (boolean isNotDefeated : isNotDefeatedArray) {
			if (!isNotDefeated) {
				return true;
			}
		}
		return false;
	}
}
