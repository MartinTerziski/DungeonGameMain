package dungeon.basicmonsters;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BasicMonsterImpl{
	String name;
	int maxBasicMonsterHealth;
	int basicMonsterAttack;
	int experienceWeight;
}
