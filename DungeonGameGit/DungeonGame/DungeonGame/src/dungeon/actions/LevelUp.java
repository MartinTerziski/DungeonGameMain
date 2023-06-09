package dungeon.actions;

import dungeon.roles.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Scanner;

@Data
@AllArgsConstructor
public class LevelUp {

    public int experience;
    public int level;
    public int levelDivider;

    public void levelingUp(Role role, Scanner input) {
        boolean canContinue = true;
        int currentLevel = getLevel();
        int currentExp = getExperience();
        int expRequired = getLevelDivider();

        // Level up
        setLevel(currentLevel + 1);

        // Increase level divider by 30%
        int newLevelDivider = (int) (getLevelDivider() * 1.2f);
        setLevelDivider(newLevelDivider);

        // Reset remaining experience to zero or add it to next level's requirements
        int remainingExp = currentExp - expRequired;
        role.getLevelUp().setExperience(Math.max(remainingExp, 0));

        System.out.println("+++++++++++ =========== +++++++++++");
        System.out.println("You gained enough experience for an upgrade!");

        while (canContinue) {
            // Ask user to choose an upgrade
            System.out.println("+++++++++++ =========== +++++++++++");
            System.out.println("What do you want to upgrade?");
            System.out.println("\t1. Increase maximum attack damage by 5");
            System.out.println("\t2. Increase maximum health by 10 and mana by 5");
            System.out.println("\t3. Increase health/mana potions drop chance by 5%");
            System.out.println("\t4. Increase health/mana potion restoring percent by 10%");
            System.out.println("\t5. Upgrade spells");

            String upgrade = input.nextLine();
            while (!upgrade.equals("1") && !upgrade.equals("2") && !upgrade.equals("3")
                    && !upgrade.equals("4") && !upgrade.equals("5")) {
                System.out.println("Invalid Command!");
                upgrade = input.nextLine();
            }

            switch (upgrade) {
                case "1" -> {
                    role.setAttackDmg(role.getAttackDmg() + 5);
                    System.out.println("You upgraded your weapon, it now deals additional " + role.getAttackDmg() + " damage!");
                    canContinue = false;
                }
                case "2" -> {
                    role.setCoreHealth(role.getCoreHealth() + 10);
                    role.setCoreMana(role.getCoreMana() + 5);
                    role.setMaxHealth(role.getMaxHealth() + 10);
                    role.setMaxMana(role.getMaxMana() + 5);
                    System.out.println("You upgraded your maximum health and mana," +
                            " you now have " + role.getCoreHealth() + " health and " + role.getCoreMana() + " mana!");
                    canContinue = false;
                }
                case "3" -> {
                    role.getPotionHandler().setPotionDropChance(role.getPotionHandler().getPotionDropChance() + 5);
                    System.out.println("You upgraded your health potions drop chance, it now has " + role.getPotionHandler().getPotionDropChance() + "% drop chance!");
                    canContinue = false;
                }
                case "4" -> {
                    role.getPotionHandler().setPotionHeal(role.getPotionHandler().getPotionHeal() + 10);
                    if (role.getPotionHandler().getPotionHeal() > 100) {
                        System.out.println("Maximum restoring effect reached! Please choose another upgrade...");
                        role.getPotionHandler().setPotionHeal(role.getPotionHandler().getPotionHeal() - 10);
                    } else {
                        System.out.println("You upgraded your potions restoring percent to " + role.getPotionHandler().getPotionHeal() + "!");
                        canContinue = false;
                    }
                }
                case "5" -> {
                    System.out.println("Which spell do you want to upgrade?");
                    if (role instanceof Juggernaut) {
                        canContinue = JuggernautSpells.upgradeJuggernautSpells(role, input);
                    } else if (role instanceof SpellInvoker) {
                        canContinue = SpellInvokerSpells.upgradeSpellInvokerSpells(role, input);
                    } else if (role instanceof Marksman) {
                        canContinue = MarksmanSpells.upgradeMarksmanSpells(role, input);
                    }

                }
            }
        }
    }
}