/**
 * --------------------------------------------------------------------------
 * File name: Battle.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 10/20/2020
 * Completion Date: 11/19/2020
 * Updated: 09/01/2021
 * @version 1.1
 * --------------------------------------------------------------------------
 */
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * The Battle Class acts as a secondary driver class. The Battle Class simulates
 * a battle by allowing user input and having the hero act based on the user
 * input. This class is the 2nd most important of all classes and the largest class.
 *
 * The Battle Class creates an enemy class and the battle is held in the
 * constructor.
 * 
 * Date created: October 20, 2020
 * 
 * @author Michael Ng, ngmw01@etsu.edu
 */
public class Battle
{
    private Statistics heroStats;
    private Skills skillTree;
    private Enemy enemy;
    private Ally ally;
    private StatusEffect statusEffect;

    private ArrayList<String> tips = new ArrayList<String>();

    // Determines who has the turn.
    private String battleTurn = "Enemy";

    private boolean tutorial = false;
    private boolean friendlyBattle = false;




    // Fields for the Hero.
    private String heroName;

    // Fields for the Enemy.
    private String enemyName = "";
    private int enemyXPAward;
    private int enemyMoneyAward;

    //Fields for the ally.
    private String allyName;

    /***
     * The primary constructor for the Battle Class.
     * 
     * Statistics heroStats is needed to import hero attributes into the battle
     * class. Skills skillTree is needed to have access to the hero's skills and
     * skill damage.
     * 
     * Date created: October 20, 2020
     * 
     * @param heroStats - Statistics
     * @param skillTree - Skills
     */
    // The entirety of the battle is held in the constructor.
    public Battle(Statistics heroStats, Skills skillTree, Ally ally, int area) {
        // gets references to Statistics, Skills, and Ally.
        this.heroStats = heroStats;
        this.skillTree = skillTree;
        this.ally = ally;
        enemy = new Enemy(area);

        getHeroName(heroStats);
        getEnemyDescription(enemy);
        getAllyName(ally);
        initializeTips();

        //Create a new statusEffect to keep track of Status Effects in Battle.
        statusEffect = new StatusEffect(heroStats, enemy, ally, skillTree);

        beginBattle();
    }

    /**
     * Secondary Battle class constructor. Used to begin a battle with a specific
     * enemy.
     * 
     * Statistics heroStats is needed to import hero attributes into the battle
     * class. Skills skillTree is needed to have access to the hero's skills and
     * skill damage. Enemy enemy is needed to have a "custom" enemy.
     * 
     * Date created: October 20, 2020
     * 
     * @param heroStats - Statistics
     * @param skillTree - Skills
     * @param enemy     - Enemy
     */
    public Battle(Statistics heroStats, Skills skillTree, Ally ally, Enemy enemy) {
        //Gets references from Statistics, Skills, Ally, and Enemy.
        this.heroStats = heroStats;
        this.skillTree = skillTree;
        this.enemy = enemy;
        this.ally = ally;
        getHeroName(heroStats);
        getEnemyDescription(enemy);
        getAllyName(ally);
        initializeTips();

        /* checks if the specified enemy has the name goblin.
         * Since there is only 1 goblin in the secondary enemy constructor (used as a tutorial),
         * this if statement can change tutorial to true if the name matches.
         */
        if(enemyName.equals("Goblin"))
        {
            tutorial = true;
        }

        //Create a new statusEffect to keep track of Status Effects in Battle.
        statusEffect = new StatusEffect(heroStats, enemy, ally, skillTree);

        beginBattle();
    }

    /**
     * Tertiary Battle class constructor. Used to begin a battle with an ally.
     * 
     * Statistics heroStats is needed to import hero attributes into the battle
     * class. Skills skillTree is needed to have access to the hero's skills and
     * skill damage. Enemy enemy is needed to have a "custom" enemy.
     * 
     * Date created: November 17, 2020
     * 
     * @param heroStats - Statistics
     * @param skillTree - Skills
     * @param enemy     - Enemy
     */
    public Battle(Statistics heroStats, Skills skillTree, Enemy enemy)
    {
        this.heroStats = heroStats;
        this.skillTree = skillTree;
        this.enemy = enemy;
        getHeroName(heroStats);
        getEnemyDescription(enemy);
        initializeTips();

        //Create a new statusEffect to keep track of Status Effects in Battle.
        statusEffect = new StatusEffect(heroStats, enemy, ally, skillTree);

        beginFriendlyBattle();
    }

    /**
     * Begins Battle. Phases begin with either the player or enemy depending on
     * their speeds.
     */
    private void beginBattle()
    {
        JOptionPane.showMessageDialog(null, enemyName + " appeared! Beginning Battle Phase...",
                "Battle!", JOptionPane.INFORMATION_MESSAGE);

        if(tutorial)
        {
            JOptionPane.showMessageDialog(null, "\"That Goblin must be from the Monster Legion,\" Zacharias says. \n\"Say, do you remember how to fight? You got hit hard back in Fendale.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "\"Let me show you the ropes of battle,\" Zacharias says. \n\"First, let's see who gets to go first.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
        }

        // Speeds are compared to see who goes first in battle.
        if (heroStats.getSpeed() >= enemy.getEnemySpeed()) {
            JOptionPane.showMessageDialog(null, heroName + "'s speed is higher!\n" + heroName + " moves first this battle!",
                    "Hero Moves First", JOptionPane.INFORMATION_MESSAGE);
            battleTurn = "Hero";
        }
        if (battleTurn.equals("Enemy")) {
            JOptionPane.showMessageDialog(null, enemyName + "'s Speed is higher! \nThey move first in this battle.",
                    enemyName + " Moves First", JOptionPane.INFORMATION_MESSAGE);
            enemyPhase();
        }
        //A mini-tutorial for the game. 
        if(tutorial)
        {
            JOptionPane.showMessageDialog(null, "\"Are you alright?\" Zacharias asks. \n\"That goblin's speed was higher, so he hits first.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "\"Keep watch on your HP counter,\" Zacharias says. \n\"You will faint if it reaches 0.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Zacharias turns towards the Goblin. \n\"No one hurts my friends!\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            allyPhase();
            JOptionPane.showMessageDialog(null, "\"I always attack after the enemy's turn,\" he says. \n\"Be mindful about that.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "\"The goblin appears to be a warrior,\" Zacharias says. \n\"Your Armor stat should reduce the amount of damage he deals.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "\"Armor is not a solution to everything,\" Zacharias says. \n\"Enemy mages can bypass Armor. Instead, Resistance reduces damage from mages.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            if(skillTree.getHeroClass().equals("Mage"))
            {
                JOptionPane.showMessageDialog(null, "\"Hey, you're a Mage, aren't you?\" Zacharias asks. \n\"Focus on improving your Affinity.\"\n\"Improving your Constitution will not benefit you.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "\"Hey, you're a Warrior, aren't you?\" Zacharias asks. \n\"Focus on improving your Constitution.\"\n\"Improving your Affinity will not benefit you.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "\"Hey, it's your turn to attack next!\" Zacharias says. \n\"Figure out what you want to do.\"", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Feel free to choose any option under the menu. \nOptions other than Attack or Mend will not cost a turn.", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
        }

        // The Battle begins and loops here.
        while ((heroStats.getHP() > 0 || ally.getHP() > 0) && enemy.getEnemyHP() > 0)
        {
            if (heroStats.getHP() > 0 && battleTurn.equals("Hero"))
            {
                heroPhase();
            }
            if (enemy.getEnemyHP() > 0 && battleTurn.equals("Enemy"))
            {
                enemyPhase();
            }
            if (ally.getHP() > 0 && enemy.getEnemyHP() > 0 && battleTurn.equals("Ally"))
            {
                allyPhase();
            }
        }
    }

    /**
     * Begins Battle with an Ally. 
     * Phases begin with either the player or enemy depending on their speeds.
     * 
     * Date Created: 11/17/2020
     */
    private void beginFriendlyBattle()
    {
        friendlyBattle = true;
        JOptionPane.showMessageDialog(null, "You challenged " + enemyName + " to a friendly duel! \nBeginning Battle Phase...",
                "Battle!", JOptionPane.INFORMATION_MESSAGE);

        // Speeds are compared to see who goes first in battle.
        //Whoever has the higher speed goes first. If speeds are tied, Hero goes first.
        if (heroStats.getSpeed() >= enemy.getEnemySpeed()) {
            JOptionPane.showMessageDialog(null, heroName + "'s speed is higher! \n" + heroName + " moves first this battle!",
                    "Hero Moves First", JOptionPane.INFORMATION_MESSAGE);
            battleTurn = "Hero";
        }
        if (battleTurn.equals("Enemy")) {
            JOptionPane.showMessageDialog(null, enemyName + "'s Speed is higher! \nThey move first in this battle.",
                    enemyName + " Moves First", JOptionPane.INFORMATION_MESSAGE);
            enemyPhase();
        }
        // The Battle begins and loops here.
        while (heroStats.getHP() > 0 && enemy.getEnemyHP() > 0)
        {
            if(heroStats.getHP() > 0 && battleTurn.equals("Hero"))
            {
                heroPhase();
            }
            if(enemy.getEnemyHP() > 0 && battleTurn.equals("Enemy"))
            {
                enemyPhase();
            }
        }
    }

    /**
     * Gets the name of the hero for quick reference.
     * 
     * Date Created: October 20, 2020
     * 
     * @param heroStats - Statisticss
     */
    private void getHeroName(Statistics heroStats)
    {
        heroName = heroStats.getHeroName();
    }

    /**
     * Gets enemy information needed for quick reference.
     * 
     * Date Created: October 20, 2020
     * 
     * @param enemy - Enemy
     */
    private void getEnemyDescription(Enemy enemy)
    {
        enemyName = enemy.getEnemyName();
        enemyXPAward = enemy.getEnemyXPAward();
        enemyMoneyAward = enemy.getEnemyMoneyAward();
    }

    /**
     * Gets the name of the Ally for quick reference.
     * 
     * Date created: 11/15/2020
     * 
     * @param ally
     */
    private void getAllyName(Ally ally)
    {
        allyName = ally.getAllyName();
    }

    /**
     * Begins and iterates the Enemy's Turn. Deals damage to the Hero and can
     * inflict Status Effects.
     * 
     * Date created: October 20, 2020
     * 
     * @param heroStats - Statistics
     * @param enemy     - Enemy
     */
    private void enemyPhase()
    {
        if (statusEffect.getEnemyStatusEffect().equalsIgnoreCase("Frozen") || statusEffect.getEnemyStatusEffect().equalsIgnoreCase("Stunned"))
        {
            JOptionPane.showMessageDialog(null, enemyName + " is " + statusEffect.getEnemyStatusEffect() + "!\nThey cannot move this turn.\n"
            + statusEffect.iterateTurn(enemyName), statusEffect.getEnemyStatusEffect(), JOptionPane.INFORMATION_MESSAGE);

            //If it is not a Friendly Battle (Duel).
            if(!friendlyBattle)
            {
                //Gives the turn to ally if they are not fainted. Otherwise, gives the turn to the hero.
                if(ally.getHP() > 0)
                {
                    battleTurn = "Ally";
                }
                else
                {
                    battleTurn = "Hero";
                }
            }
            else
            {
                battleTurn = "Hero";
            }
            //this return completes the enemy phase without running through the code below.
            return;
        }
        //message stores events that have happened this phase.
        String message = "";

        //This boolean keeps track of if the status effect has been iterated.
        boolean effectIterated = false;

        //the enemy iterates their status effect turn first (if their status effect lasts more than 1 turn) before moving, so status effects can take place before acting.
        if(statusEffect.getEnemyEffectLength() > 1)
        {
            message += statusEffect.iterateTurn(enemyName);
            effectIterated = true;
        }

        Random rand = new Random();
        int randomNum;
        //If statement needed to avoid nullpointerexception for friendly duels.
        if(allyName != null)
        {
            //This series of if statements determines who will be attacked by the enemy: the hero or ally.
            if(heroStats.getHP() != 0 && ally.getHP() != 0)
            {
                //Enemy randomly chooses who to attack. Value 0 attacks Hero, while Value 1 attacks Ally.
                randomNum = rand.nextInt(2);
            }
            //if hero's HP is not 0
            else if(heroStats.getHP() != 0)
            {
                //attacks hero
                randomNum = 0;
            }
            //if ally's HP is not 0
            else //if(ally.getHP() != 0)
            {
                //attacks ally
                randomNum = 1;
            }
            
        }
        else
        {
            //Attack only the hero. This happens only in friendly duels.
            randomNum = 0;
        }

        int randomNum2;
        if(allyName != null) //There is an ally (therefore, it is not a friendly duel)
        {
            randomNum2 = rand.nextInt(2);
        }
        else //There is no ally by your side (it is a friendly duel)
        {
            //Gats a random number from 0 to the available skills the enemy has.
            randomNum2 = rand.nextInt(enemy.getAvailableEnemySkills());
            //System.out.println(randomNum2);
        }

        //Retrieve gets the status effect for the skill at index randomNum2.
        //Skill Status Effect is separated into 4 parts using .split(), which are dissected below.
        String[] retrieve = enemy.getSkillStatusEffect(randomNum2).split(" ");

        String skillUsed = enemy.getSkill(randomNum2);
        //An example is 50 Cripple 10 3.
        //50% Chance to Cripple the Enemy for 10 Speed for 3 turns.
        //The order is as follows: Percent Proc, Status Effect, Effect Strength, Effect Length.
        String skillStatusEffect = retrieve[1];
        //Percent Proc - Chance for an enemy to give a status effect to the target.
        int skillStatusEffectProc = Integer.parseInt(retrieve[0]);
        //Status Efficacy - Strength of a status effect. 
        //Status Effects like Silence or Pacify will not need skillStatusEfficacy.
        int skillStatusEfficacy = Integer.parseInt(retrieve[2]);
        //Status Length - How long a status effect will last.
        int skillStatusLength = Integer.parseInt(retrieve[3]);
        String skillDesc = enemy.getSkillDescription(randomNum2);
        
        int beforeSkillDamage;
        int afterSkillDamage = 0;
        int blockedDamage;
        String target = "";

        //Attack Hero
        if(randomNum == 0)
        {
            //Attack Hero as a Mage/Sage. Uses Hero's Resistance.
            if (enemy.getEnemyClass().equals("Mage") || enemy.getEnemyClass().equals("Sage"))
            {
                //In the case of a friendly battle, if the enemy (ally) uses Restore
                if(skillUsed.equalsIgnoreCase("Restore")) //if ally uses the skill Restore
                {
                    //The enemy heals themself.
                    beforeSkillDamage = enemy.getSkillDamage(randomNum2);
                    if(beforeSkillDamage > (heroStats.getMaxHP() - heroStats.getHP()))
                    {
                        beforeSkillDamage = heroStats.getMaxHP() - heroStats.getHP();
                    }
                    enemy.addEnemyHP(beforeSkillDamage);
                    message += allyName + " used the Skill " + skillUsed + " to heal themself for " + beforeSkillDamage + "HP.\n(" + skillDesc + ")\n\n" + enemyName + "'s HP is now: " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP!";
                    System.out.println(allyName + " used the Skill " + skillUsed + " to heal themself for " + beforeSkillDamage + "HP.");
                }
                else
                {
                    target = "Hero";

                    // Reduces incoming damage by the value of the hero's resistance attribute.
                    beforeSkillDamage = enemy.getSkillDamage(randomNum2);
                    afterSkillDamage = enemy.getSkillDamage(randomNum2);
                    if ((beforeSkillDamage - heroStats.getResistance()) < 0)
                    {
                        afterSkillDamage = 0;
                        blockedDamage = beforeSkillDamage;
                    }
                    else
                    {
                        afterSkillDamage = beforeSkillDamage - heroStats.getResistance();
                        blockedDamage = heroStats.getResistance();
                    }
                    hurtHero(afterSkillDamage);
                    if (heroStats.getHP() < 0)
                    {
                        heroStats.setHP(0);
                    }

                    message += enemy.getEnemyName() + " used the Skill " + skillUsed + " to deal " + beforeSkillDamage
                            + "HP of Damage to " + heroStats.getHeroName() + ". \n(" + skillDesc
                            + ")\n\n" + heroName + "'s Resistance Stat reduced the incoming damage by " + blockedDamage
                            + "HP!\n" + heroStats.getHeroName() + " took " + afterSkillDamage + "HP of damage!\n" 
                            + heroStats.getHeroName() + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP.";
                    System.out.println(enemyName + " used " + skillUsed + " to deal " + afterSkillDamage + "HP of Damage to " + heroName);
                }               
            }
            else //If enemy class is warrior (Attack Hero as a Warrior/Beserker. Uses Hero Armor.)
            {
                //In the case of a friendly battle, if the enemy (ally) uses Restore
                if(skillUsed.equalsIgnoreCase("Courage")) //if ally uses the skill Restore
                {
                    //The enemy heals themself.
                    beforeSkillDamage = enemy.getSkillDamage(randomNum2);
                    if(beforeSkillDamage > (heroStats.getMaxHP() - heroStats.getHP()))
                    {
                        beforeSkillDamage = heroStats.getMaxHP() - heroStats.getHP();
                    }
                    enemy.addEnemyHP(beforeSkillDamage);
                    message += enemyName + " used the Skill " + skillUsed + " to heal themself for " + beforeSkillDamage + "HP.\n(" + skillDesc + ")\n\n" + enemyName + "'s HP is now: " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP!";
                    System.out.println(enemyName + " used the Skill " + skillUsed + " to heal themself for " + beforeSkillDamage + "HP.");
                }
                else
                {
                    target = "Hero";

                    // Reduces incoming damage by the value of the hero's armor attribute.
                    beforeSkillDamage = enemy.getSkillDamage(randomNum2);
                    afterSkillDamage = enemy.getSkillDamage(randomNum2);
                    if ((beforeSkillDamage - heroStats.getArmor()) < 0)
                    {
                        afterSkillDamage = 0;
                        blockedDamage = beforeSkillDamage;
                    }
                    else
                    {
                        afterSkillDamage = beforeSkillDamage - heroStats.getArmor();
                        blockedDamage = heroStats.getArmor();
                    }
                    hurtHero(afterSkillDamage);
                    if (heroStats.getHP() < 0)
                    {
                        heroStats.setHP(0);
                    }
                    if(enemy.getEnemyHP() > enemy.getEnemyMaxHP())
                    {
                        enemy.setEnemyHP(enemy.getEnemyMaxHP());
                    }
                    message += enemy.getEnemyName() + " used the Skill " + skillUsed + " to deal " + beforeSkillDamage
                                    + "HP of Damage to " + heroStats.getHeroName() + ". \n(" + skillDesc
                                    + ")\n\n" + heroName + "'s Armor Stat reduced the incoming damage by " + blockedDamage
                                    + "HP!\n" + heroStats.getHeroName() + " took " + afterSkillDamage + "HP of damage!\n" 
                                    + heroStats.getHeroName() + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP.";
                    System.out.println(enemyName + " used " + skillUsed + " to deal " + afterSkillDamage + "HP of Damage to " + heroName);
                }
            }
        }
        else //Attack Ally
        {
            //Attack the ally as a mage! Reduces damage based on ally's resistance attribute.
            if (enemy.getEnemyClass().equals("Mage") || enemy.getEnemyClass().equals("Sage"))
            {
                target = "Ally";

                // Reduces incoming damage by the value of the ally's resistance attribute.
                beforeSkillDamage = enemy.getSkillDamage(randomNum2);
                afterSkillDamage = enemy.getSkillDamage(randomNum2);
                if ((beforeSkillDamage - ally.getResistance()) < 0)
                {
                    afterSkillDamage = 0;
                    blockedDamage = beforeSkillDamage;
                }
                else
                {
                    afterSkillDamage = beforeSkillDamage - ally.getResistance();
                    blockedDamage = ally.getResistance();
                }
                hurtAlly(afterSkillDamage);
                if (ally.getHP() < 0)
                {
                    ally.setHP(0);
                }
                //Used in case the enemy casts a Drain spell.
                if(enemy.getEnemyHP() > enemy.getEnemyMaxHP())
                {
                    enemy.setEnemyHP(enemy.getEnemyMaxHP());
                }

                message += enemy.getEnemyName() + " used the Skill " + skillUsed + " to deal " + beforeSkillDamage
                        + "HP of Damage to " + ally.getAllyName() + ". \n(" + skillDesc
                        + ")\n\n" + ally.getAllyName() + "'s Resistance Stat reduced the incoming damage by " + blockedDamage
                        + "HP!\n" + ally.getAllyName() + " took " + afterSkillDamage + "HP of damage!\n" 
                        + ally.getAllyName() + "'s HP is now: " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP.";
            }
            else //attack the enemy as a warrior! Reduces damage based on the ally's armor attribute.
            {
                target = "Ally";

                // Reduces incoming damage by the value of the ally's armor attribute.
                beforeSkillDamage = enemy.getSkillDamage(randomNum2);
                afterSkillDamage = enemy.getSkillDamage(randomNum2);
                if ((beforeSkillDamage - ally.getArmor()) < 0)
                {
                    afterSkillDamage = 0;
                    blockedDamage = beforeSkillDamage;
                }
                else
                {
                    afterSkillDamage = beforeSkillDamage - ally.getArmor();
                    blockedDamage = ally.getArmor();
                }
                hurtAlly(afterSkillDamage);
                if (ally.getHP() < 0)
                {
                    ally.setHP(0);
                }
                message += enemy.getEnemyName() + " used the Skill " + skillUsed + " to deal " + beforeSkillDamage
                                + "HP of Damage to " + ally.getAllyName() + ". \n(" + skillDesc
                                + ")\n\n" + ally.getAllyName() + "'s Armor Stat reduced the incoming damage by " + blockedDamage
                                + "HP!\n" + ally.getAllyName() + " took " + afterSkillDamage + "HP of damage!\n" 
                                + ally.getAllyName() + "'s HP is now: " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP.";
            }

            System.out.println(enemyName + " used " + skillUsed + " to deal " + afterSkillDamage + "HP of Damage to " + allyName);
        }

        if(target.equals("Hero"))
        {
            //Proc the status effect of the enemy's skill.
            if (skillStatusEffect.equals("Drain")) 
            {
                int drainAmount = statusEffect.drain(enemyName, afterSkillDamage, skillStatusEffectProc);
                System.out.println(enemyName + " drained " + drainAmount + "HP from " + heroName + "!");
                message += "\n\n" + enemyName + " drained " + drainAmount + "HP from " + heroName + "!";
                message += "\n" + enemyName + " now has " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP.";
            }
            else
            {
                message += "\n" + statusEffectProc(skillStatusEffect, enemyName, heroName, skillStatusEffectProc, skillStatusEfficacy, skillStatusLength);
            }
        }
        else //if target is ally
        {
            //Proc the status effect of the enemy's skill.
            if (skillStatusEffect.equals("Drain")) 
            {
                int drainAmount = statusEffect.drain(enemyName, afterSkillDamage, skillStatusEffectProc);
                System.out.println(enemyName + " drained " + drainAmount + "HP from " + allyName + "!");
                message += "\n\n" + enemyName + " drained " + drainAmount + "HP from " + allyName + "!";
                message += "\n" + enemyName + " now has " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP.";
            }
            else
            {
                message += "\n" + statusEffectProc(skillStatusEffect, enemyName, allyName, skillStatusEffectProc, skillStatusEfficacy, skillStatusLength);
            }
        }

        //if there is only 1 turn left of the status effect.
        if(!effectIterated)
        {
            message += "\n" + statusEffect.iterateTurn(enemyName);
            effectIterated = true;
        }

        JOptionPane.showMessageDialog(null, message, "Enemy Phase Results", JOptionPane.INFORMATION_MESSAGE);
        //This if-statement notifies the user that the Hero/Ally has fainted if either have 0 HP.
        if(randomNum == 0 && heroStats.getHP() == 0)
        {
            JOptionPane.showMessageDialog(null, heroName + "'s HP has dropped to 0! \nThey have fainted and are unable to move the rest of this battle.", heroName + " has fainted!", JOptionPane.WARNING_MESSAGE);
            System.out.println(heroName + "'s HP is 0! " + heroName + " has Fainted and cannot move the rest of the battle!");
            statusEffect.clearStatusEffects("Hero");
            statusEffect.setHeroStatusEffect("Fainted");
            statusEffect.setHeroStatusEffectDescription("Fainted: " + heroName + " was defeated! They can no longer move this battle.");
            statusEffect.setHeroEffectEfficacy(0);
            statusEffect.setHeroEffectLength(0);
        }
        else if(randomNum == 1 && ally.getHP() == 0)
        {
            System.out.println(allyName + "'s HP is 0!\n" + allyName + " has fainted and is unable to move the rest of this battle!");
            JOptionPane.showMessageDialog(null, allyName + " has fainted! They cannot take any more actions this battle.", allyName + " has fainted!", JOptionPane.WARNING_MESSAGE);
            statusEffect.clearStatusEffects("Ally");
            statusEffect.setAllyStatusEffect("Fainted");
            statusEffect.setAllyStatusEffectDescription("Fainted: " + allyName + " was defeated! They can no longer move this battle.");
            statusEffect.setAllyEffectEfficacy(0);
            statusEffect.setAllyEffectLength(0);
        }
        //This if-statement completes the enemyPhase by changing the battleTurn.
        if(!friendlyBattle)
        {
            if(ally.getHP() > 0)
            {
                battleTurn = "Ally";
            }
            else
            {
                battleTurn = "Hero";
            }
        }
        else
        {
            battleTurn = "Hero";
        }
    }

    /**
     * Begins and iterates the hero's turn. Deals damage to the Enemy and can
     * inflict Status Effects.
     * 
     * Date created: October 20, 2020
     * 
     * @param heroStats  - Statistics
     * @param skillTree  - Skills
     * @param enemyStats - Enemy
     */
    private void heroPhase()
    {
        if (statusEffect.getHeroStatusEffect().equalsIgnoreCase("Frozen")
                || statusEffect.getHeroStatusEffect().equalsIgnoreCase("Stunned")) {
            JOptionPane.showMessageDialog(null,
                    heroName + " is " + statusEffect.getHeroStatusEffect() + "!\nThey cannot move this turn.\n"
                    + statusEffect.iterateTurn(heroName), statusEffect.getHeroStatusEffect(), JOptionPane.INFORMATION_MESSAGE);
            battleTurn = "Enemy";
            return;
        }
        //message stores events that have happened this phase.
        String message = "";

        //This boolean keeps track of if the status effect has been iterated.
        boolean effectIterated = false;

        //the hero iterates their status effect turn first (if their status effect lasts more than 1 turn) before moving, so status effects can take place before acting.
        if(statusEffect.getHeroEffectLength() > 1)
        {
            String tempMessage = statusEffect.iterateTurn(heroName);

            //If the hero was poisoned or bleeding, shows the effect before the Hero Phase menu. Else, shows in when attacking.
            if(statusEffect.getHeroStatusEffect().equals("Poisoned") || statusEffect.getHeroStatusEffect().equals("Bleeding"))
            {
                JOptionPane.showMessageDialog(null, tempMessage, statusEffect.getHeroStatusEffect(), JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                message += tempMessage;
            }

            effectIterated = true;
        }

        do
        {
            //Menu options for the Hero Phase.
            String[] commands = {"Hero Attributes", "Skill Damage",
                    "Status Effects", "Enemy Info", "Ally Info", "Mend", "Attack" };
            int optionChosen;
            //Checks if the Hero has an ally.
            if(allyName != null)
            {
                //This optionChosen shows the Ally's HP in the menu.
            optionChosen = JOptionPane.showOptionDialog(null, "What is " + heroStats.getHeroName() + " going to do?\n\n(" + statusEffect.getHeroStatusEffect() + ")   " + heroName + "'s HP:        " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP\n(" + statusEffect.getAllyStatusEffect() + ")   " + allyName + "'s HP:          " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP\n(" + statusEffect.getEnemyStatusEffect() + ")   " + enemyName + "'s HP:    " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP", "Hero Phase", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, commands, commands[6]);
            }
            else //The hero has no ally and this is a Friendly Duel.
            {
                //The difference here is that optionChosen does not show ally HP since it would be null.
            optionChosen = JOptionPane.showOptionDialog(null, "What is " + heroStats.getHeroName() + " going to do?\n\n(" + statusEffect.getHeroStatusEffect() + ")   " + heroName + "'s HP:        " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP\n(" + statusEffect.getEnemyStatusEffect() + ")   " + enemyName + "'s HP:    " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP", "Hero Phase", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, commands, commands[6]);
            }
            

            //Hero Attributes - Show Hero's Current Attributes
            if (optionChosen == 0)
            {
                String[] listChoices = {"OK", "Attribute Descriptions"};
                int choice = JOptionPane.showOptionDialog(null, heroStats.listAttributes(), "Hero Attributes", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, listChoices, listChoices[0]);
                if(choice == 1)
                {
                    JOptionPane.showMessageDialog(null, heroStats.attributeDescription(skillTree.getHeroClass()), "Attribute Description", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //skill damage - returns the damage done by every available skill.
            else if (optionChosen == 1)
            {
                if(ally != null)
                {
                    JOptionPane.showMessageDialog(null, heroName + "\n_________\n" + skillTree.listAllSkillDamage() + "\n__________\n" + allyName + "\n__________\n" + ally.listAllSkillDamage(), "Skill Damage",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, heroName + "\n_________\n" + skillTree.listAllSkillDamage() + "\n__________\n", "Skill Damage",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
            //Status effects - Shows the user status effects of combatants. Also allows the user to ask for more status effect information.
            else if (optionChosen == 2)
            {
                String[] optionChoice = {"OK", "More Information"};
                int optionNum = JOptionPane.showOptionDialog(null, statusEffect.getAllStatusEffects(), "Status Effects", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optionChoice, optionChoice[0]);
                switch(optionNum)
                {
                    case 1:
                        JOptionPane.showMessageDialog(null, statusEffect.getStatusEffectInformation(), "Possible Status Effects", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        break;
                }
            }
            //Enemy Info - Returns the enemy's attributes and skill information.
            else if (optionChosen == 3)
            {
                JOptionPane.showMessageDialog(null, enemy.getEnemyInformation(), "Enemy Information", JOptionPane.INFORMATION_MESSAGE);
            }
            //Ally Info - Returns the ally's attributes and their skill information.
            else if (optionChosen == 4)
            {
                //Not a friendly duel
                if(allyName != null)
                {
                    JOptionPane.showMessageDialog(null, ally.getAllyInformation(), "Ally Information", JOptionPane.INFORMATION_MESSAGE);
                }
                else //friendly duel
                {
                    JOptionPane.showMessageDialog(null, "You currently have no ally by your side.", "No Allies", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
            //Mend - Allows the user to heal the Hero or Ally.
            else if (optionChosen == 5)
            {
                int healAmount = 0;
                //if statement controls healAmount based on heroClass.
                if(skillTree.getHeroClass().equals("Mage"))
                {
                    healAmount = (int)(heroStats.getAffinity() * 0.7);
                }
                else
                {
                    healAmount = (int)(heroStats.getConstitution() * 0.7);
                }
                //Checks if allyName is null, rather than comparing strings.
                if(allyName != null)
                {
                    //The if statement must be nested to avoid a null pointer exception in friendly duels.
                    //whiel the ally is not fainted, allows the user the option to choose who to heal.
                    if(ally.getHP() > 0)
                    {
                        String[] healOption = {heroStats.getHeroName(), ally.getAllyName()};
                        int option = JOptionPane.showOptionDialog(null, "Who will " + heroName + " heal?\n" + heroName + " will heal for: " + healAmount + "HP. \n\n" + heroName + "'s HP:   " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP\n" + allyName + "'s HP:   " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP", "Heal Target", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, healOption, healOption[0]);

                        //heals the hero.
                        if(option == 0)
                        {
                            if(healAmount > (heroStats.getMaxHP() - heroStats.getHP()))
                            {
                                healAmount = heroStats.getMaxHP() - heroStats.getHP();
                            }
                            heroStats.addHP(healAmount);
                            JOptionPane.showMessageDialog(null, heroName + " successfully healed themself for " + healAmount + "HP!\n\n" + heroName + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!\n", "Healed " + heroName + " for " + healAmount + "HP", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println(heroName + " successfully healed themself for " + healAmount + "HP!");
                            battleTurn = "Enemy";
                        }
                        //heals the ally.
                        else if(option == 1)
                        {
                            if(healAmount > (ally.getMaxHP() - ally.getHP()))
                            {
                                healAmount = ally.getMaxHP() - ally.getHP();
                            }
                            ally.addHP(healAmount);
                            JOptionPane.showMessageDialog(null, heroName + " successfully healed " + allyName + " for " + healAmount + "HP!\n\n" + allyName + "'s HP is now: " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP!\n", "Healed " + allyName + " for " + healAmount + "HP", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println(heroName + " successfully healed " + allyName + " for " + healAmount + "HP!");
                            battleTurn = "Enemy";
                        }
                    }
                    else //if ally has fainted, asks for confirmation to heal the hero.
                    {
                        String[] confirmation = {"YES", "NO"};
                        int choice = JOptionPane.showOptionDialog(null, heroName + " will heal themself for " + healAmount + "HP.\n\n" + heroName + "'s Current HP: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!\n", "Heal Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmation, confirmation[0]);
                        if(choice == 0)
                        {
                            if(healAmount > (heroStats.getMaxHP() - heroStats.getHP()))
                            {
                                healAmount = heroStats.getMaxHP() - heroStats.getHP();
                            }
                            heroStats.addHP(healAmount);
                            JOptionPane.showMessageDialog(null, heroName + " successfully healed themself for " + healAmount + "HP!\n\n" + heroName + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!\n", "Healed " + heroName + " for " + healAmount + "HP", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println(heroName + " successfully healed themself for " + healAmount + "HP!");
                            battleTurn = "Enemy";
                        }
                    }
                }
                else //if this is a friendly battle, automatically asks the user for confirmation to heal the hero.
                {
                    String[] confirmation = {"YES", "NO"};
                    int choice = JOptionPane.showOptionDialog(null, heroName + " will heal themself for " + healAmount + "HP.\n\n" + heroName + "'s Current HP: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!\n", "Heal Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmation, confirmation[0]);
                    if(choice == 0)
                    {
                        if(healAmount > (heroStats.getMaxHP() - heroStats.getHP()))
                        {
                            healAmount = heroStats.getMaxHP() - heroStats.getHP();
                        }
                        heroStats.addHP(healAmount);
                        JOptionPane.showMessageDialog(null, heroName + " successfully healed themself for " + healAmount + "HP!\n\n" + heroName + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!\n", "Healed " + heroName + " for " + healAmount + "HP", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(heroName + " successfully healed themself for " + healAmount + "HP!");
                        battleTurn = "Enemy";
                    }
                }
            }
            //Attack - Allows the user to attack the enemy with a skill of their choice.
            else if (optionChosen == 6)
            {
                String[] choices = skillTree.getAttackSkills();
                int skillChoice = JOptionPane.showOptionDialog(null,
                        "Which skill will you use?\n" + skillTree.getHeroSkills(), "Skill to Use",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[2]);
                //This if statement is needed so the code does not crash when the user exits the attack screen.
                if(skillChoice != -1)
                {
                    //Retrieve gets the status effect for the skill at index randomNum2.
                    //Skill Status Effect is separated into 4 parts using .split(), which are dissected below.
                    String[] retrieve = skillTree.getSkillStatusEffect(skillChoice).split(" ");

                    String skillUsed = choices[skillChoice];
                    //An example is 50 Cripple 10 3.
                    //50% Chance to Cripple the Enemy for 10 Speed for 3 turns.
                    //The order is as follows: Percent Proc, Status Effect, Effect Strength, Effect Length.
                    String skillStatusEffect = retrieve[1];
                    //Percent Proc - Chance for an enemy to give a status effect to the target.
                    int skillStatusEffectProc = Integer.parseInt(retrieve[0]);
                    //Status Efficacy - Strength of a status effect. 
                    //Status Effects like Silence or Pacify will not need skillStatusEfficacy.
                    int skillStatusEfficacy = Integer.parseInt(retrieve[2]);
                    //Status Length - How long a status effect will last.
                    int skillStatusLength = Integer.parseInt(retrieve[3]);

                    int skillDamage = skillTree.heroAttack(skillUsed);

                    hurtEnemy(skillDamage);
                    if (enemy.getEnemyHP() < 0) {
                        enemy.setEnemyHP(0);
                    }
                    message += heroName + " used the Skill " + skillUsed + " to deal " + skillDamage
                                    + "HP of Damage to " + enemy.getEnemyName() + "!\n(" + skillTree.getSkillDamageDescription(skillUsed) + ")\n\n" + enemy.getEnemyName() + "'s HP is now: " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP!\n";
                    System.out.println(heroName + " used " + skillUsed + " to deal " + skillDamage + "HP of Damage to " + enemyName);
                    battleTurn = "Enemy";

                    // Status Effect Proc
                    // Method calls ask for: Target, Efficacy, Proc Chance, Length.
                    if (skillStatusEffect.equals("Drain")) {
                        int drainAmount = statusEffect.drain(heroName, skillDamage, skillStatusEffectProc);
                        {
                            System.out.println(heroName + " drained " + drainAmount + "HP from " + enemyName + "!");
                            message += "\n\n" + heroName + " drained " + drainAmount + "HP from " + enemyName + "!";
                            message += "\n" + heroName + " now has " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP.";
                        }
                    }
                    else
                    {
                        message += statusEffectProc(skillStatusEffect, heroName, enemyName, skillStatusEffectProc, skillStatusEfficacy, skillStatusLength);
                    }

                    //if there is only 1 turn left of the status effect.
                    if(!effectIterated)
                    {
                        message += "\n" + statusEffect.iterateTurn(heroName);
                        effectIterated = true;
                    }

                    JOptionPane.showMessageDialog(null, message, "Hero Phase Results", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } while (battleTurn.equals("Hero"));
    }

    /**
     * Allows the ally to perform a random attack on the enemy or heal themself/hero.
     * 
     * Date Created: November 15, 2020
     */
    private void allyPhase()
    {
        if (statusEffect.getAllyStatusEffect().equalsIgnoreCase("Frozen") || statusEffect.getAllyStatusEffect().equalsIgnoreCase("Stunned"))
        {
            JOptionPane.showMessageDialog(null, ally.getAllyName() + " is " + statusEffect.getAllyStatusEffect() + "!\nThey cannot move this turn."
                    + statusEffect.iterateTurn(allyName), statusEffect.getAllyStatusEffect(), JOptionPane.INFORMATION_MESSAGE);
            if(heroStats.getHP() > 0)
            {
                battleTurn = "Hero";
            }
            else
            {
                battleTurn = "Enemy";
            }
            return;
        }
        //message stores events that have happened this phase.
        String message = "";

        //This boolean keeps track of if the status effect has been iterated.
        boolean effectIterated = false;

        //the ally iterates their status effect turn first (if their status effect lasts more than 1 turn) before moving, so status effects can take place before acting.
        if(statusEffect.getAllyEffectLength() > 1)
        {
            message += statusEffect.iterateTurn(allyName);
            effectIterated = true;
        }

        //skills consists of all the Ally's available skills.
        String[] skills = ally.getAttackSkills();

        Random rand = new Random();
        //randomNum decides which skill the ally will use on their turn.
        int randomNum = rand.nextInt(skills.length);

        //Retrieve gets the status effect for the skill at index randomNum2.
        //Skill Status Effect is separated into 4 parts using .split(), which are dissected below.
        String[] retrieve = ally.getSkillStatusEffect(randomNum).split(" ");

        String skillUsed = skills[randomNum];
        //An example is 50 Cripple 10 3.
        //50% Chance to Cripple the Enemy for 10 Speed for 3 turns.
        //The order is as follows: Percent Proc, Status Effect, Effect Strength, Effect Length.
        String skillStatusEffect = retrieve[1];
        //Percent Proc - Chance for an enemy to give a status effect to the target.
        int skillStatusEffectProc = Integer.parseInt(retrieve[0]);
        //Status Efficacy - Strength of a status effect. 
        //Status Effects like Silence or Pacify will not need skillStatusEfficacy.
        int skillStatusEfficacy = Integer.parseInt(retrieve[2]);
        //Status Length - How long a status effect will last.
        int skillStatusLength = Integer.parseInt(retrieve[3]);

        int skillDamage;

        String skillDesc = ally.getSkillDescription(randomNum);

        if (ally.getAllyClass().equals("Sage"))
        {
            if(!skillUsed.equals("Restore"))
            {
                //Attacks the Enemy.
                if(randomNum == 6)
                {
                    randomNum = 5;
                }
                skillDamage = ally.getSkillDamage().get(randomNum);
                hurtEnemy(skillDamage);
                if (enemy.getEnemyHP() < 0)
                {
                    enemy.setEnemyHP(0);
                }
                message += allyName + " used the Skill " + skillUsed + " to deal " + skillDamage + "HP of Damage to " + enemyName + ". \n(" + skillDesc + ")\n\n" + enemyName + "'s HP is now: " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP!";
                System.out.println(allyName + " used the Skill " + skillUsed + " to deal " + skillDamage + "HP of Damage to " + enemyName + ".");
            }
            else //if ally uses the skill Restore
            {
                //Heals the Hero or self, depending on who has lower HP Percentage.
                double heroHPPercentage = heroStats.getHP() / heroStats.getMaxHP();
                double allyHPPercentage = ally.getHP() / ally.getMaxHP();
                //Heals the Hero if true. Otherwise, heal the Ally.
                if(heroHPPercentage < allyHPPercentage && heroStats.getHP() != 0)
                {
                    //Heals the Hero. Cannot heal Hero if they have fainted.
                    skillDamage = ally.getSkillDamage().get(randomNum);
                    if(skillDamage > (heroStats.getMaxHP() - heroStats.getHP()))
                    {
                        skillDamage = heroStats.getMaxHP() - heroStats.getHP();
                    }
                    heroStats.addHP(skillDamage);
                    message += allyName + " used the Skill " + skillUsed + " to heal " + heroName + " for " + skillDamage + "HP.\n(" + skillDesc + ")\n\n" + heroName + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!";
                    System.out.println(allyName + " used the Skill " + skillUsed + " to heal " + heroName + " for " + skillDamage + "HP.");
                }
                else //if heroHPPercentage is greater than allyHPPercentage or hero has fainted.
                {
                    //Heals the Ally.
                    skillDamage = ally.getSkillDamage().get(randomNum);
                    if(skillDamage > (ally.getMaxHP() - ally.getHP()))
                    {
                        skillDamage = ally.getMaxHP() - ally.getHP();
                    }
                    ally.addHP(skillDamage);
                    message += allyName + " used the Skill " + skillUsed + " to heal themself for " + skillDamage + "HP.\n(" + skillDesc + ")\n" + allyName + "'s HP is now: " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP!";
                    System.out.println(allyName + " used the Skill " + skillUsed + " to heal themself for " + skillDamage + "HP.");
                }
            }
        }
        else //if the ally is a warrior
        {
            if(!skillUsed.equals("Courage"))
            {
                // Attacks the Enemy.
                skillDamage = ally.getSkillDamage().get(randomNum);
                hurtEnemy(skillDamage);
                if (enemy.getEnemyHP() < 0)
                {
                    enemy.setEnemyHP(0);
                }
                message +=  allyName + " used the Skill " + skillUsed + " to deal " + skillDamage
                                + "HP of Damage to " + enemy.getEnemyName() + ". \n(" + skillDesc
                                + ")\n\n" + enemy.getEnemyName() + "'s HP is now: " + enemy.getEnemyHP() + "HP / " + enemy.getEnemyMaxHP() + "HP!";
                System.out.println(allyName + " used the Skill " + skillUsed + " to deal " + skillDamage + "HP of Damage to " + enemy.getEnemyName() + ".");
            }
            else
            {
                //Heals the Hero or self, depending on who has lower HP Percentage.
                double heroHPPercentage = heroStats.getHP() / heroStats.getMaxHP();
                double allyHPPercentage = ally.getHP() / ally.getMaxHP();
                //Heals the Hero if true. Otherwise, heal the Ally.
                if(heroHPPercentage < allyHPPercentage && heroStats.getHP() != 0)
                {
                    //Heals the Hero. Cannot heal hero if they have fainted.
                    skillDamage = ally.getSkillDamage().get(randomNum);
                    if(skillDamage > (heroStats.getMaxHP() - heroStats.getHP()))
                    {
                        skillDamage = heroStats.getMaxHP() - heroStats.getHP();
                    }
                    heroStats.addHP(skillDamage);
                    message += allyName + " used the Skill " + skillUsed + " to heal " + heroName + " for " + skillDamage + "HP.\n(" + skillDesc + ")\n" + heroName + "'s HP is now: " + heroStats.getHP() + "HP / " + heroStats.getMaxHP() + "HP!";
                    System.out.println(allyName + " used the Skill " + skillUsed + " to heal " + heroName + " for " + skillDamage + "HP.");
                }
                else
                {
                    //Heals the Ally.
                    skillDamage = ally.getSkillDamage().get(randomNum);
                    if(skillDamage > (ally.getMaxHP() - ally.getHP()))
                    {
                        skillDamage = ally.getMaxHP() - ally.getHP();
                    }
                    ally.addHP(skillDamage);
                    message += allyName + " used the Skill " + skillUsed + " to heal themself for " + skillDamage + "HP.\n(" + skillDesc + ")\n" + allyName + "'s HP is now: " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP!";
                    System.out.println(allyName + " used the Skill " + skillUsed + " to heal themself for " + skillDamage + "HP.");
                }
            }
        }

        if (skillStatusEffect.equals("Drain")) {
            int drainAmount = statusEffect.drain(allyName, skillDamage, skillStatusEffectProc);
            {
                System.out.println(allyName + " drained " + drainAmount + "HP from " + enemyName + "!");
                message += "\n" + allyName + " drained " + drainAmount + "HP from " + enemyName + "!";
                message += "\n" + allyName + " now has " + ally.getHP() + "HP / " + ally.getMaxHP() + "HP.";
            }
        }
        else
        {
            message += "\n" + statusEffectProc(skillStatusEffect, allyName, enemyName, skillStatusEffectProc, skillStatusEfficacy, skillStatusLength);
        }

        //if there is only 1 turn left of the status effect.
        if(!effectIterated)
        {
            message += "\n" + statusEffect.iterateTurn(allyName);
            effectIterated = true;
        }


        JOptionPane.showMessageDialog(null, message, "Ally Phase Results", JOptionPane.INFORMATION_MESSAGE);
        //Completes the Ally Phase by changing battleTurn to either "Hero" or "Enemy".
        if(heroStats.getHP() > 0)
        {
            battleTurn = "Hero";
        }
        else
        {
            battleTurn = "Enemy";
        }
    }

    /**
     * Uses the statusEffect object to incur status effects and their effectiveness and length.
     * 
     * Date Created: November 17, 2020
     * 
     * @param effect - String
     * @param caster - String
     * @param who - String
     * @param proc - int
     * @param efficacy - int
     * @param length - int
     * @return message - String
     */
    private String statusEffectProc(String effect, String caster, String who, int proc, int efficacy, int length)
    {
        String message = "";
        // Status Effect Proc
        // Method calls ask for: Target, Efficacy, Proc Chance, Length.
        if (effect.equals("ReduceHP")) {
            //These methods return true when successfully procced. Otherwise, return false.
            if (!statusEffect.reduceHP(who, efficacy, proc, length)) {
                //If the status effect didn't proc.
                System.out.println(caster + " failed to Reduce " + who + "'s HP!");
                message = "\n" + caster + " failed to Reduce " + who + "'s HP!";
            }
            else
            {
                //The effect procced.
                message = "\n" + caster + " reduced " + who + "'s HP!";
            }
        }
        if (effect.equals("Bleed")) {
            if (!statusEffect.bleed(who, efficacy, proc, length)) {
                System.out.println(caster + " failed to lacerate " + who + "!\n");
                message = "\n" + caster + " failed to lacerate " + who + "!\n";
            }
            else
            {
                message = "\n" + caster + " lacerated " + who + "!\n";
            }
        }
        if (effect.equals("Poison")) {
            if (!statusEffect.poison(who, efficacy, proc, length)) {
                System.out.println(caster + " failed to Poison " + who + "!\n");
                message = "\n" + caster + " failed to Poison " + who + "!\n";
            }
            else
            {
                message = "\n" + caster + " poisoned " + who + "!\n";
            }
        }
        if (effect.equals("Weaken")) {
            if (!statusEffect.weaken(who, efficacy, proc, length)) {
                System.out.println(caster + " failed to Weaken " + who + "!\n");
                message = "\n" + caster + " failed to Weaken " + who + "!\n";
            }
            else
            {
                message = "\n" + caster + " weakened " + who + "!\n";
            }
        }
        if (effect.equals("Pacify")) {
            if (!statusEffect.pacify(who, proc, length)) {
                System.out.println(caster + " failed to Pacify " + who + "!\n");
                message = "\n" + caster + " failed to Pacify " + who + "!\n";
            }
            else
            {
                message = "\n" + caster + " pacified " + who + "!\n";
            }
        }
        if (effect.equals("Headache")) {
            if (!statusEffect.headache(who, efficacy, proc, length)) {
                System.out.println(who + " resisted Headache!");
                message = who + " resisted Headache!";
            }
            else
            {
                message = caster + " gave a headache to " + who + "!\n";
            }
        }
        if (effect.equals("Silence")) {
            if (!statusEffect.silence(who, proc, length)) {
                System.out.println(caster + " failed to Silence " + who + "!\\n" + //
                        "");
                message = caster + " failed to Silence " + who + "!\n";
            }
            else
            {
                message = caster + " silenced " + who + "!\n";
            }
        }
        if (effect.equals("ArmorBreak")) {
            if (!statusEffect.armorBreak(who, efficacy, proc, length)) {
                System.out.println(who + " resisted Armor Break!\n");
                message = who + " resisted Armor Break!\n";
            }
            else
            {
                message = caster + " broke " + who + "'s armor!\n";
            }
        }
        if (effect.equals("Sunder")) {
            if (!statusEffect.sunder(who, proc, length)) {
                System.out.println(caster + " failed to Sunder " + who + "!\n");
                message = caster + " failed to Sunder " + who + "!\n";
            }
            else
            {
                message = caster + " sundered " + who + "!\n";
            }
        }
        if (effect.equals("MentalCollapse")) {
            if (!statusEffect.mentalCollapse(who, efficacy, proc, length)) {
                System.out.println(who + " resisted Mental Collapse!");
                message = who + " resisted Mental Collapse!";
            }
            else
            {
                message = who + " suffered a Mental Collapse!";
            }
        }
        if (effect.equals("Submission")) {
            if (!statusEffect.submission(who, proc, length)) {
                System.out.println(who + " resisted Submission!");
                message = who + " resisted Submission!";
            }
            else
            {
                message = who + " fell under Submission!";
            }
        }
        if (effect.equals("Cripple")) {
            if (!statusEffect.cripple(who, efficacy, proc, length)) {
                System.out.println(caster + " failed to Cripple " + who + "!\n");
                message = caster + " failed to Cripple " + who + "!\n";
            }
            else
            {
                message = caster + " crippled " + who + "!\n";
            }
        }
        if (effect.equals("Immobilized")) {
            if (!statusEffect.immobilize(who, proc, length)) {
                System.out.println(caster + " failed to Immobilize " + who + "!\n");
                message = caster + " failed to Immobilize " + who + "!\n";
            }
            else
            {
                message = caster + " immobilized " + who + "!\n";
            }
        }
        if (effect.equals("Stun")) {
            if (!statusEffect.stun(who, proc, length)) {
                System.out.println(caster + " failed to Stun " + who + "!\n");
                message = caster + " failed to Stun " + who + "!\n";
            }
            else
            {
                message = caster + " stunned " + who + "!\n";
            }
        }
        if (effect.equals("Freeze")) {
            if (!statusEffect.freeze(who, proc, length)) {
                System.out.println(caster + " failed to Freeze " + who + "!\n");
                message = caster + " failed to Freeze " + who + "!\n";
            }
            else
            {
                message = caster + " froze " + who + "!\n";
            }
        }
        return message;
    }

    /**
     * Reduces the Hero's HP by the specified amount.
     * 
     * Date created: October 20, 2020
     * 
     * Date Created: 
     * @param damage - int
     */
    private void hurtHero(int damage)
    {
        heroStats.setHP(heroStats.getHP() - damage);
    }

    /**
     * Reduces the Ally's HP by the specified amount.
     * 
     * Date created: October 20, 2020
     * 
     * @param damage - int
     */
    private void hurtAlly(int damage)
    {
        ally.setHP(ally.getHP() - damage);
    }
    
    /**
     * Reduces the Enemy's HP by the specified amount.
     * 
     * Date created: October 20, 2020
     * 
     * @param damage - int
     */
    private void hurtEnemy(int damage)
    {
        enemy.setEnemyHP(enemy.getEnemyHP() - damage);
    }

    
    /**
     * Returns the result of the battle and awards the Hero with XP and Money based
     * on the enemy they fought.
     * 
     * Date created: October 20, 2020
     * 
     * @param stats        - Statistics
     * @param increaseStat - statIncrease
     * @return true if hero&ally win, and false if enemy wins
     */
    public boolean getResult(StatIncrease increaseStat)
    {
        enemy.stopEnemyTheme();
        if (heroStats.getHP() == 0 && ally.getHP() == 0) {
            SimpleAudioPlayer SAP;
            try
            {
                SAP = new SimpleAudioPlayer("16.Defeat-FireEmblemThreeHouses-BrokenWeapon.wav", false);
                JOptionPane.showMessageDialog(null, heroName + " and " + allyName + " were Defeated in Battle. \nYou awake elsewhere, far away from the battle.", "Defeat", JOptionPane.INFORMATION_MESSAGE);
                System.out.print("Defeat...");
                //restores ally and hero HP.
                heroStats.setHP(heroStats.getMaxHP());
                ally.setHP(ally.getMaxHP());
                statusEffect.clearAllStatusEffects();
                JOptionPane.showMessageDialog(null, getTip(), "After-Battle Tip", JOptionPane.INFORMATION_MESSAGE);
                SAP.stop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            return false;
        }
        else //if(enemy.getEnemyHP() == 0)
        {
            SimpleAudioPlayer SAP;
            try
            {
                SAP = new SimpleAudioPlayer("15.Victory-PokemonMysteryDungeonGatestoInfinity-DungeonCleared.wav", false);
                JOptionPane.showMessageDialog(null, "Congratulations! " + heroName + " and " + allyName + " defeated " + enemyName + "!\n" + enemyName + " had $" + enemyMoneyAward + "!\n\n" + heroName + " gained " + enemyXPAward + "XP!\n", "Victory!", JOptionPane.INFORMATION_MESSAGE);
                //restores ally and hero HP.
                heroStats.setHP(heroStats.getMaxHP());
                ally.setHP(ally.getMaxHP());
                statusEffect.clearAllStatusEffects();
                SAP.stop();
                System.out.print("Victory! ");
                //Rewards Money and XP based on enemyXPAward and enemyMoneyAward.
                heroStats.addMoney(enemyMoneyAward);
                increaseStat.addHeroXP(enemyXPAward);
                JOptionPane.showMessageDialog(null, getTip(), "After-Battle Tip", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * Returns the result of a friendly battle and awards the hero XP.
     * 
     * Date created: October 20, 2020
     * 
     * @param stats        - Statistics
     * @param increaseStat - statIncrease
     * @return true if hero&ally win, and false if enemy wins
     */
    public boolean getDuelResults(StatIncrease increaseStat)
    {
        enemy.stopEnemyTheme();
        if (heroStats.getHP() == 0)
        {
            SimpleAudioPlayer SAP;
            try
            {
                SAP = new SimpleAudioPlayer("16.Defeat-FireEmblemThreeHouses-BrokenWeapon.wav", false);
                if(enemyName.equals("Zacharias"))
                {
                    JOptionPane.showMessageDialog(null, heroStats.getHeroName() + " lost to " + enemyName + "!", "Defeat", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Zacharias twirls his weapon on his finger.\n\"Great effort, " + heroName + ", but it wasn't enough. Better luck next time.\"\n\nThe group gained " + enemyXPAward + "XP!", "Defeat", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, heroStats.getHeroName() + " lost to " + enemyName + "!", "Defeat", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Anthiera holds her hand out to you while you are down.\n\"Great effort, " + heroName + ", but better luck next time.\"\n\nThe group gained " + enemyXPAward + "XP!", "Defeat", JOptionPane.INFORMATION_MESSAGE);
                }
                System.out.print("Defeat...");
                heroStats.setHP(heroStats.getMaxHP());
                statusEffect.clearAllStatusEffects();
                increaseStat.addHeroXP(enemyXPAward);
                JOptionPane.showMessageDialog(null, getTip(), "After-Battle Tip", JOptionPane.INFORMATION_MESSAGE);
                SAP.stop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            return false;
        }
        else //if(enemy.getEnemyHP() = 0)
        {
            SimpleAudioPlayer SAP;
            try
            {
                SAP = new SimpleAudioPlayer("15.Victory-PokemonMysteryDungeonGatestoInfinity-DungeonCleared.wav", false);
                JOptionPane.showMessageDialog(null, "Congratulations! " + heroStats.getHeroName() + " Defeated " + enemyName + "!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
                if(enemyName.equals("Zacharias"))
                {
                    JOptionPane.showMessageDialog(null, "Zacharias smiles and puts his hand on your shoulder.\n\"Great job, " + heroName + ". You're one step closer to becoming stronger.\" \n\nYou and Zacharias gained " + enemyXPAward + "XP!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "\"Great job, " + heroName + ", Anthiera says. \"You're one formidible opponent.\" \n\nYou and Anthiera gained " + enemyXPAward + "XP!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
                }
                System.out.print("Victory! ");
                heroStats.setHP(heroStats.getMaxHP());
                statusEffect.clearAllStatusEffects();
                increaseStat.addHeroXP(enemyXPAward);
                JOptionPane.showMessageDialog(null, getTip(), "After-Battle Tip", JOptionPane.INFORMATION_MESSAGE);
                SAP.stop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            return true;
        }
    }
    /**
     * Initializes the tips arraylist.
     * 
     * Date Created: November 19, 2020
     */
    private void initializeTips()
    {
        tips.add("Tip 1: Different Classes benefit from different attributes. \n - For example, Affinity is useful for Mages, and Constitution is useful for Warriors.");
        tips.add("Tip 2: Status Effects such as Silence and Pacify can nullify certain enemies. \n - For example, Silence will cause enemy mages to deal only 0 damage.");
        tips.add("Tip 3: Bosses appear on every 10th area! Be sure to prepare before then. \nVisit the shop, or do Practice Battles!");
        tips.add("Tip 4: Zacharias is a strong ally! \nKeep him safe and he will devestate the enemy while he lives.");
        tips.add("Tip 5: The shop is the best way to increase a hero's statistics. \nBe sure to buy items there!");
        tips.add("Tip 6: Enemies do not have Armor or Resistance! \nDo not worry about a certain skill doing 0 damage!");
        tips.add("Tip 7: Armor reduces damage from physical attacks, and Resistance reduces damage from magical attacks! \nBe sure to balance both stats.");
        tips.add("Tip 8: Stuck on a boss fight? \nGrind XP by doing practice battles or having friendly duels!");
        tips.add("Tip 9: Using Mend will be a lifesaver in certain situations.\nBe sure to keep your Ally alive by mending them.\nThey cannot fight when fainted.");
        tips.add("Tip 10: Ultimate Skills for both the Hero and Allies unlock at level 15.\nThey deal massive amounts of damage.");
        tips.add("Tip 11: Stun and Freeze are match changers! Utilize them by using 'Shield Bash' or 'Ice Spike'!");
        tips.add("Tip 12: Different Allies provide different skills and status effects.\nUtilize them and learn more about what they can do.");
        tips.add("Tip 13: Status Effects do not stack, but can be replaced or have their length reset! \nBe careful when using other skills that can replace more useful status effects.");
        tips.add("Tip 14: Speed decides who will go first in a battle. \nThose with higher speed will go first.");
        tips.add("Tip 15: Different enemies will have different skills. \nLearn from each skill and plan around their effects!");
        tips.add("Tip 16: Being Pacified or Silenced may reduce your skill damage to 0, as well as mending. \nTry incurring status effects on the enemy when this happens!");
    }
    /**
     * Gets a random tip from the tip arraylist.
     * 
     * Date Created: November 19, 2020
     */
    private String getTip()
    {
        Random rand = new Random();
        int randomNum = rand.nextInt(tips.size());

        return tips.get(randomNum);
    }
}
