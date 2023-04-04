package dungeon.basicmonsters;

import dungeon.actions.CounterMonsters;

import java.util.Random;

public class BasicMonsterFactory {
	private CounterMonsters counterMonsters;

	public BasicMonsterFactory(CounterMonsters counterMonsters) {
		this.counterMonsters = counterMonsters;
	}

	public BasicMonster getMonster() {
		Random random = new Random();
		BasicMonster basicMonster = null;
		System.out.println("+++++++++++ =========== +++++++++++");
		while (true) {
			switch (random.nextInt(5)) {
				case 0 -> {
					if (!counterMonsters.isMaxReached("Imp")) {
						basicMonster = new Imp("Imp", 30, 15, 12);
					}
				}
				case 1 -> {
					if (!counterMonsters.isMaxReached("Wolf")) {
						basicMonster = new Wolf("Wolf", 25, 15, 10);
					}
				}
				case 2 -> {
					if (!counterMonsters.isMaxReached("Ooze")) {
						basicMonster = new Ooze("Ooze", 20, 15, 8);
					}
				}
				case 3 -> {
					if (!counterMonsters.isMaxReached("Corrupted Villager")) {
						basicMonster = new CorruptedVillager("Corrupted Villager", 35, 15, 15);
					}
				}
				case 4 -> {
					if (!counterMonsters.isMaxReached("Wraith")) {
						basicMonster = new Wraith("Wraith", 40, 20, 20);
					}
				}
			}
			if (basicMonster != null) {
				counterMonsters.increment(basicMonster.getName());
				if ("AEIOUY".indexOf(basicMonster.getName().charAt(0))>=0) {
					System.out.print("An ");
				} else {
					System.out.print("A ");
				}
				System.out.println(basicMonster.getName() + " is facing you!");
				return basicMonster;
			}
		}
	}
}
