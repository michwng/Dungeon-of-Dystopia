/**
 * --------------------------------------------------------------------------
 * File name: StatIncrease.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 10/8/2020
 * Completion Date: 11/19/2020
 * Updated: 09/01/2021
 * @version 1.02
 * --------------------------------------------------------------------------
 */
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * The StatIncrease Class increases the hero's attributes upon level up. This
 * program also keeps track of the Hero's XP.
 *
 * Date created: October 8, 2020
 * 
 * @author Michael Ng, ngmw01@etsu.edu
 */
public class StatIncrease
{
    private Statistics stats;
    private Skills heroSkills;
    private Ally ally;
    //ally2 will not be initialized until the encounter scenario occurs.
    private Ally ally2;

    // Determines the chance of the Hero's Stats increasing.
    private int chanceMaxHP;
    private int chanceConstitution;
    private int chanceAffinity;
    private int chanceArmor;
    private int chanceResistance;
    private int chanceSpeed;

    // Separate growth rate for Zacharias's Stats.
    private int allyChanceMaxHP;
    private int allyChanceConstitution;
    private int allyChanceAffinity;
    private int allyChanceArmor;
    private int allyChanceResistance;
    private int allyChanceSpeed;

    // Separate growth rate for Anthiera's Stats.
    private int ally2ChanceMaxHP;
    private int ally2ChanceConstitution;
    private int ally2ChanceAffinity;
    private int ally2ChanceArmor;
    private int ally2ChanceResistance;
    private int ally2ChanceSpeed;
    //holdStats holds the values of all of Anthiera's level ups until the Encounter scenario. 
    //All values are added to Anthiera's base stats as soon as she becomes an available ally.
    //Holds: maxHP, Constitution, Affinity, Armor, Resistance, and Speed, respectively.
    private int[] holdStats = new int[6];

    // Keeps track of the Hero's (and Ally's) Level and XP.
    private int heroLevel = 1;
    private int heroXP = 0;

    /**
     * Constructor for the StatIncrease class. This method initializes the
     * Statistics object stats, and the probability of increase in all hero
     * attributes.
     * 
     * Statistics stats is needed in the parameter to increase the values of
     * attributes. String heroClass determines the attribute growth probabilities.
     * 
     * Date created: October 8, 2020
     * 
     * @param stats      - Statistics
     * @param heroClass  - String
     * @param heroSkills - Skills
     */
    public StatIncrease(Statistics stats, String heroClass, Skills heroSkills, Ally ally)
    {
        if (heroClass.equalsIgnoreCase("Mage"))
        {
            chanceMaxHP = 60;
            chanceConstitution = 15;
            chanceAffinity = 80;
            chanceArmor = 50;
            chanceResistance = 70;
            chanceSpeed = 70;

            allyChanceMaxHP = 100;
            allyChanceConstitution = 70;
            allyChanceAffinity = 0;
            allyChanceArmor = 70;
            allyChanceResistance = 50;
            allyChanceSpeed = 30;

            ally2ChanceMaxHP = 60;
            ally2ChanceConstitution = 0;
            ally2ChanceAffinity = 90;
            ally2ChanceArmor = 40;
            ally2ChanceResistance = 50;
            ally2ChanceSpeed = 70;
        } 
        else // if(class.equalsIgnoreCase("Warrior"))
        {
            chanceMaxHP = 100;
            chanceConstitution = 90;
            chanceAffinity = 0;
            chanceArmor = 90;
            chanceResistance = 60;
            chanceSpeed = 50;

            allyChanceMaxHP = 50;
            allyChanceConstitution = 0;
            allyChanceAffinity = 80;
            allyChanceArmor = 50;
            allyChanceResistance = 60;
            allyChanceSpeed = 70;

            ally2ChanceMaxHP = 100;
            ally2ChanceConstitution = 85;
            ally2ChanceAffinity = 0;
            ally2ChanceArmor = 75;
            ally2ChanceResistance = 75;
            ally2ChanceSpeed = 40;
        }
        this.stats = stats;
        this.heroSkills = heroSkills;
        this.ally = ally;
    }

    /**
     * Secondary Constructor for the StatIncrease class.
     * Typically used when loading save files.
     * Includes EXP and Hero Level int parameters.
     * 
     * Date created: October 8, 2020
     * 
     * @param stats      - Statistics
     * @param heroClass  - String
     * @param heroSkills - Skills
     */
    public StatIncrease(Statistics stats, String heroClass, Skills heroSkills, Ally ally, int heroXP, int heroLevel)
    {
        if (heroClass.equalsIgnoreCase("Mage"))
        {
            chanceMaxHP = 60;
            chanceConstitution = 15;
            chanceAffinity = 80;
            chanceArmor = 50;
            chanceResistance = 70;
            chanceSpeed = 70;

            allyChanceMaxHP = 100;
            allyChanceConstitution = 70;
            allyChanceAffinity = 0;
            allyChanceArmor = 70;
            allyChanceResistance = 50;
            allyChanceSpeed = 30;

            ally2ChanceMaxHP = 60;
            ally2ChanceConstitution = 0;
            ally2ChanceAffinity = 90;
            ally2ChanceArmor = 40;
            ally2ChanceResistance = 50;
            ally2ChanceSpeed = 70;
        } 
        else // if(class.equalsIgnoreCase("Warrior"))
        {
            chanceMaxHP = 100;
            chanceConstitution = 90;
            chanceAffinity = 0;
            chanceArmor = 90;
            chanceResistance = 60;
            chanceSpeed = 50;

            allyChanceMaxHP = 50;
            allyChanceConstitution = 0;
            allyChanceAffinity = 80;
            allyChanceArmor = 50;
            allyChanceResistance = 60;
            allyChanceSpeed = 70;

            ally2ChanceMaxHP = 100;
            ally2ChanceConstitution = 85;
            ally2ChanceAffinity = 0;
            ally2ChanceArmor = 75;
            ally2ChanceResistance = 75;
            ally2ChanceSpeed = 40;
        }
        this.stats = stats;
        this.heroSkills = heroSkills;
        this.ally = ally;
        this.heroLevel = heroLevel;
        this.heroXP = heroXP;
    }

    /**
     * Checks if the Hero has gained enough XP to level up. This method is called
     * everytime the addHeroXP method is called.
     * 
     * Date created: October 8, 2020
     */
    private void checkLevel() {
        if ((heroXP / 100) >= 1) {
            do {
                levelUp();
                heroXP -= 100;
            } while (heroXP > 100);
        } else {
            System.out.println("You are now " + (100 - heroXP) + "XP away from leveling up.");
        }
    }

    /**
     * Method used to level up the hero. Utilizes the method rollStat to determine
     * if an attribute will increase.
     * 
     * Date created: October 8, 2020
     */
    public void levelUp()
    {
        SimpleAudioPlayer SAP;
        try
        {
            SAP = new SimpleAudioPlayer("17.LevelUp-FireEmblemEchoes-Fanfare(Recruitment).wav", false);
            heroLevel++;
            Random rand = new Random();
            int randomNum = rand.nextInt(2) + 1;
            String allIncreases = stats.getHeroName() + " leveled up! They are now Level " + heroLevel + ".\n___________________________________";
            System.out.println("\nCongratulations! You have Leveled Up! You are now Level " + heroLevel + "!");
            allIncreases += heroSkills.checkHeroLevel(heroLevel);
            

            //Remember, rollStat returns a boolean, allowing it to be a conditional in an if statement.
            if(rollStat(chanceMaxHP))
            {
                randomNum = rand.nextInt(2) + 1; //Max HP can increase by 10 or 20.
                stats.addMaxHP(randomNum * 10);
                System.out.println(" - " + stats.getHeroName() + "'s HP increased by " + (randomNum * 10) + "!");
                allIncreases += "\n - " + stats.getHeroName() + "'s Max HP increased by " + (randomNum * 10) + "!";
            }
            if(rollStat(chanceConstitution))
            {
                randomNum = rand.nextInt(3) + 1; //Constitution can increase by 1-3.
                stats.addConstitution(randomNum);
                System.out.println(" - " + stats.getHeroName() + "'s Constitution increased by " + randomNum + "!");
                allIncreases += "\n - " + stats.getHeroName() + "'s Constitution increased by " + randomNum + "!";
            }
            if(rollStat(chanceAffinity))
            {
                randomNum = rand.nextInt(3) + 1; //Affinity can increase by 1-3.
                stats.addAffinity(randomNum);
                System.out.println(" - " + stats.getHeroName() + "'s Affinity increased by " + randomNum + "!");
                allIncreases += "\n - " + stats.getHeroName() + "'s Affinity increased by " + randomNum + "!";
            }
            if(rollStat(chanceArmor))
            {
                randomNum = rand.nextInt(3) + 1; //Armor can increase by 1-3.
                stats.addArmor(randomNum);
                System.out.println(" - " + stats.getHeroName() + "'s Armor increased by " + randomNum + "!");
                allIncreases += "\n - " + stats.getHeroName() + "'s Armor increased by " + randomNum + "!";
            }
            if(rollStat(chanceResistance))
            {
                randomNum = rand.nextInt(3) + 1; //Resistance can increase by 1-3.
                stats.addResistance(randomNum);
                System.out.println(" - " + stats.getHeroName() + "'s Resistance increased by " + randomNum + "!");
                allIncreases += "\n - " + stats.getHeroName() + "'s Resistance increased by " + randomNum + "!";
            }
            if(rollStat(chanceSpeed))
            {
                randomNum = rand.nextInt(3) + 1; //Speed can increase by 1-3.
                stats.addSpeed(randomNum);
                System.out.println(" - " + stats.getHeroName() + "'s Speed increased by " + randomNum + "!");
                allIncreases += "\n - " + stats.getHeroName() + "'s Speed increased by " + randomNum + "!";
            }

            //Separate growth for the Ally.
            allIncreases += "\n____________________\n Zacharias \n____________________";
            allIncreases += ally.checkAllyLevel(heroLevel);

            //Ally growths are stronger than the Hero's since they cannot increase their stats via shop.
            if(rollStat(allyChanceMaxHP))
            {
                randomNum = rand.nextInt(6) + 1; //Max HP can increase by 10 - 60.
                ally.addMaxHP(randomNum * 10);
                System.out.println("- Zacharias's Max HP increased by " + (randomNum * 10) + "!");
                allIncreases += "\n - Zacharias's Max HP increased by " + (randomNum * 10) + "!";
            }
            if(rollStat(allyChanceConstitution))
            {
                randomNum = rand.nextInt(7) + 1; //Constitution can increase by 1-7.
                ally.addConstitution(randomNum);
                System.out.println("- Zacharias's Constitution increased by " + randomNum + "!");
                allIncreases += "\n - Zacharias's Consitution increased by " + randomNum + "!";
            }
            if(rollStat(allyChanceAffinity))
            {
                randomNum = rand.nextInt(7) + 1; //Affinity can increase by 1-7.
                ally.addAffinity(randomNum);
                System.out.println("- Zacharias's Affinity increased by " + randomNum + "!");
                allIncreases += "\n - Zacharias's Affinity increased by " + randomNum + "!";
            }
            if(rollStat(allyChanceArmor))
            {
                randomNum = rand.nextInt(7) + 1; //Armor can increase by 1-7.
                ally.addArmor(randomNum);
                System.out.println("- Zacharias's Armor increased by " + randomNum + "!");
                allIncreases += "\n - Zacharias's Armor increased by " + randomNum + "!";
            }
            if(rollStat(allyChanceResistance))
            {
                randomNum = rand.nextInt(7) + 1; //Resistance can increase by 1-7.
                ally.addResistance(randomNum);
                System.out.println("- Zacharias's Resistance increased by " + randomNum + "!");
                allIncreases += "\n - Zacharias's Resistance increased by " + randomNum + "!";
            }
            if(rollStat(allyChanceSpeed))
            {
                randomNum = rand.nextInt(7) + 1; //Speed can increase by 1-7.
                ally.addSpeed(randomNum);
                System.out.println("- Zacharias's Speed increased by " + randomNum + "!");
                allIncreases += "\n - Zacharias's Speed increased by " + randomNum + "!";
            }

            //Separate Growth for Anthiera.
            if(ally2 != null)
            {
                allIncreases += "\n____________________\n Anthiera \n____________________";
                allIncreases += ally2.checkAllyLevel(heroLevel);
            }
            if(rollStat(ally2ChanceMaxHP))
            {
                randomNum = rand.nextInt(6) + 1; //Max HP can increase by 10 - 60.
                if(ally2 != null)
                {
                    ally2.addMaxHP(randomNum * 10);
                    System.out.println("- Anthiera's Max HP increased by " + (randomNum * 10) + "!");
                    allIncreases += "\n - Anthiera's Max HP increased by " + (randomNum * 10) + "!";
                }
                else
                {
                    holdStats[0] += randomNum * 10;
                }
            }
            if(rollStat(ally2ChanceConstitution))
            {
                randomNum = rand.nextInt(7) + 1; //Constitution can increase by 1-7.
                if(ally2 != null)
                {
                    ally2.addConstitution(randomNum);
                    System.out.println("- Anthiera's Constitution increased by " + randomNum + "!");
                    allIncreases += "\n - Anthiera's Constitution increased by " + randomNum + "!";
                }
                else
                {
                    holdStats[1] += randomNum;
                }
            }
            if(rollStat(ally2ChanceAffinity))
            {
                randomNum = rand.nextInt(7) + 1; //Affinity can increase by 1-7.
                if(ally2 != null)
                {
                    ally2.addAffinity(randomNum);
                    System.out.println("- Anthiera's Affinity increased by " + randomNum + "!");
                    allIncreases += "\n - Anthiera's Affinity increased by " + randomNum + "!";
                }
                else
                {
                    holdStats[2] += randomNum;
                }
            }
            if(rollStat(ally2ChanceArmor))
            {
                randomNum = rand.nextInt(7) + 1; //Armor can increase by 1-7.
                if(ally2 != null)
                {
                    ally2.addArmor(randomNum);
                    System.out.println("- Anthiera's Armor increased by " + randomNum + "!");
                    allIncreases += "\n - Anthiera's Armor increased by " + randomNum + "!";
                }
                else
                {
                    holdStats[3] += randomNum;
                }
            }
            if(rollStat(ally2ChanceResistance))
            {
                randomNum = rand.nextInt(7) + 1; //Resistance can increase by 1-7.
                if(ally2 != null)
                {
                    ally.addResistance(randomNum);
                    System.out.println("- Anthiera's Resistance increased by " + randomNum + "!");
                    allIncreases += "\n - Anthiera's Resistance increased by " + randomNum + "!";
                }
                else
                {
                    holdStats[4] += randomNum;
                }
            }
            if(rollStat(ally2ChanceSpeed))
            {
                randomNum = rand.nextInt(7) + 1; //Speed can increase by 1-7.
                if(ally2 != null)
                {
                    ally.addAffinity(randomNum);
                    System.out.println("- Anthiera's Speed increased by " + randomNum + "!");
                    allIncreases += "\n - Anthiera's Speed increased by " + randomNum + "!";
                }
                else
                {
                    holdStats[5] += randomNum;
                }
            }

            allIncreases += "\n____________________";
            JOptionPane.showMessageDialog(null, allIncreases, "Level Up!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, stats.listAttributes(), "Hero Attributes", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, ally.listAttributes(), "Zacharias's Attributes", JOptionPane.INFORMATION_MESSAGE);
            if(ally2 != null)
            {
                JOptionPane.showMessageDialog(null, ally2.listAttributes(), "Anthiera's Attributes", JOptionPane.INFORMATION_MESSAGE);
            }
            SAP.stop();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the probability of a stat is less than probability.
     * If yes, it will return boolean true.
     * If no, it will return boolean false.
     * Used to decide if a particular attribute will increase.
     * 
     * Date created: October 8, 2020
     * 
     * @param probability
     * @return true, false
     */
    private boolean rollStat(int probability)
    {
        Random rand = new Random();
        int temp = rand.nextInt(100) + 1; //Returns 1 - 100
        if(temp <= probability)
        {
            return true;
        }
        return false;
    }

    //Accessor methods used for communication outside the class.
    /**
     * returns the probability to increase Max HP.
     * 
     * Date created: October 8, 2020
     * 
     * @return chanceMaxHP - int
     */
    public int getChanceHP()
    {
        return chanceMaxHP;
    }
    /**
     * returns the probability to increase Constitution.
     * 
     * Date created: October 8, 2020
     * 
     * @return chanceConstitution - int
     */
    public int getChanceConstitution()
    {
        return chanceConstitution;
    }
    /**
     * returns the probability to increase Affinity.
     * 
     * Date created: October 8, 2020
     * 
     * @return chanceAffinity - int
     */
    public int getChanceAffinity()
    {
        return chanceAffinity;
    }
    /**
     * returns the probability to increase Armor.
     * 
     * Date created: October 8, 2020
     * 
     * @return chanceArmor - int
     */
    public int getChanceArmor()
    {
        return chanceArmor;
    }
    /**
     * returns the probability to increase Resistance.
     * 
     * Date created: October 8, 2020
     * 
     * @return chanceResistance - int
     */
    public int getChanceResistance()
    {
        return chanceResistance;
    }
    /**
     * returns the probability to increase Speed.
     * 
     * Date created: October 8, 2020
     * 
     * @return chanceSpeed - int
     */
    public int getChanceSpeed()
    {
        return chanceSpeed;
    }
    /**
     * returns the hero's current level.
     * 
     * Date created: October 8, 2020
     * 
     * @return heroLevel - int
     */
    public int getHeroLevel()
    {
        return heroLevel;
    }
    /**
     * returns the hero's current XP.
     * 
     * Date created: October 8, 2020
     * 
     * @return heroXP - int
     */
    public int getHeroXP()
    {
        return heroXP;
    }
    /**
     * Prints all probabilities to each
     * respective attribute.
     * 
     * Date created: October 9, 2020
     */
    public void getProbabilities()
    {
        String message = "";
        message += "\nMax HP: " + chanceMaxHP;
        message += "\nConstitution: " + chanceConstitution;
        message += "\nAffinity: " + chanceAffinity;
        message += "\nArmor: " + chanceArmor;
        message += "\nResistance: " + chanceResistance;
        message += "\nSpeed: " + chanceSpeed;
        message += "\nHero Level: " + heroLevel;
        message += "\nHero XP: " + heroXP;

        System.out.println(message);
    }


    //Mutator methods for the StatIncrease Class
    /**
     * Increases hero experience.
     * Calls on the checkLevel() method
     * afterwards to allow fluidity.
     * 
     * Date created: October 9, 2020
     * 
     * @param amount - int
     */
    public void addHeroXP(int amount)
    {
        heroXP += amount;
        checkLevel();
    }

    /**
     * Sets the hero's level to the specified amount.
     * 
     * @param amount
     */
    public void setHeroLevel(int amount)
    {
        heroLevel = amount;
    }


    /**
     * This method initializes both allies.
     * 
     * Date Created: 11/19/2020
     * Modified: 9/13/2021
     * 
     * @param ally, ally2
     *
    public void initializeAllys(Ally ally, Ally ally2)
    {
        this.ally = ally;
        this.ally2 = ally2;

        ally2.addMaxHP(holdStats[0]);
        ally2.addConstitution(holdStats[1]);
        ally2.addAffinity(holdStats[2]);
        ally2.addArmor(holdStats[3]);
        ally2.addResistance(holdStats[4]);
        ally2.addSpeed(holdStats[5]);
    }*/

    /**
     * This method initializes Zacharias. Typically used after loading a save file.
     * 
     * Date Created: 09/16/2021
     * 
     * @param zacharias
     */
    public void initializeZacharias(Ally zacharias)
    {
        this.ally = zacharias;
    }

    /**
     * This method initializes Anthiera.
     * 
     * Date Created: 09/16/2021
     * 
     * @param anthiera
     */
    public void initializeAnthiera(Ally anthiera)
    {
        ally2 = anthiera;

        ally2.addMaxHP(holdStats[0]);
        ally2.addConstitution(holdStats[1]);
        ally2.addAffinity(holdStats[2]);
        ally2.addArmor(holdStats[3]);
        ally2.addResistance(holdStats[4]);
        ally2.addSpeed(holdStats[5]);
    }
}
