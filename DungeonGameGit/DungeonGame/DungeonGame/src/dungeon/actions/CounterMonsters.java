package dungeon.actions;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CounterMonsters {
    private Map<String, Integer> counterMap;

    public CounterMonsters(int imp, int wolf, int ooze, int corruptedVillager, int wraith) {
        counterMap = new HashMap<>();
        counterMap.put("Imp", imp);
        counterMap.put("Wolf", wolf);
        counterMap.put("Ooze", ooze);
        counterMap.put("Corrupted Villager", corruptedVillager);
        counterMap.put("Wraith", wraith);
    }

    public void increment(String monsterName) {
        int forIncrement = counterMap.getOrDefault(monsterName, 0);
        if(forIncrement <= 5) {
            counterMap.put(monsterName, forIncrement + 1);
        }
    }

    public boolean isMaxReached(String monsterName) {
        return counterMap.getOrDefault(monsterName, 0) > 5;
    }
}
