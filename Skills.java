/**
 * --------------------------------------------------------------------------
 * File name: Skills.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 10/9/2020
 * Completion Date: 11/14/2020
 * @version 1.02
 * --------------------------------------------------------------------------
 */
import java.util.ArrayList;
/**
  * The Skills Class keeps track of the hero's
  * skills, skill damage, and class.
  *
  * Date created: October 9, 2020
  * 
  * @author Michael Ng, ngmw01@etsu.edu
  */
public class Skills
{
    //Fields used in the Skills Class.
    private Statistics heroStats;
    private ArrayList<String> heroSkills = new ArrayList<String>();
    private int[] skillDamage = {0, 0, 0, 0, 0, 0};
    private String[] skillStatusEffect = {"", "", "", "", "", ""};
    private int constitution;
    private int affinity;
    private String heroClass;

    /**
     * Main constructor for the Skills class.
     * Statistics heroStats is needed in order to calculate skill damage.
     * StatIncrease increaseStats allows Skills to communicate with StatIncrease to determine the Hero's level.
     * String heroClass determines the Hero's skillset.
     * 
     * Date created: October 9, 2020
     * 
     * @param heroStats - Statistics
     * @param heroClass - String
     */
    public Skills(Statistics heroStats, String heroClass)
    {
        this.heroStats = heroStats;
        this.heroClass = heroClass;
        this.constitution = heroStats.getConstitution();
        this.affinity = heroStats.getAffinity();

        if(heroClass.equalsIgnoreCase("Mage"))
        {
            this.heroClass = "Mage";
            heroSkills.add("Fireball");
            heroSkills.add("Ice Spike");
            heroSkills.add("Orb");

            skillDamage[0] = (int)(affinity * 1.1);
            skillDamage[1] = (int)(affinity * 0.8);
            skillDamage[2] = affinity;

            skillStatusEffect[0] = "10 ReduceHP 20 3";
            skillStatusEffect[1] = "30 Freeze 0 2";
            skillStatusEffect[2] = "0 None 0 0";

            getHeroSkills();
        } 
        else if(heroClass.equalsIgnoreCase("Warrior"))
        {
            this.heroClass = "Warrior";
            heroSkills.add("Heavy Slash");
            heroSkills.add("Shield Bash");
            heroSkills.add("Slash");

            skillDamage[0] = (int)(constitution * 1.5);
            skillDamage[1] = (int)(constitution * 0.7);
            skillDamage[2] = (constitution);

            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Stun 0 1";
            skillStatusEffect[2] = "0 None 0 0";

            getHeroSkills();
        }
    }

    /**
     * Secondary Constructor for the Skills class.
     * Used when loading save files.
     * 
     * Date created: September 1, 2021
     * 
     * @param heroStats - Statistics
     * @param heroClass - String
     */
    public Skills(Statistics heroStats, String heroClass, int heroLevel)
    {
        this.heroStats = heroStats;
        this.heroClass = heroClass;
        this.constitution = heroStats.getConstitution();
        this.affinity = heroStats.getAffinity();

        if(heroClass.equalsIgnoreCase("Mage"))
        {
            this.heroClass = "Mage";
            heroSkills.add("Fireball");
            heroSkills.add("Ice Spike");
            heroSkills.add("Orb");

            skillDamage[0] = (int)(affinity * 1.1);
            skillDamage[1] = (int)(affinity * 0.8);
            skillDamage[2] = affinity;

            skillStatusEffect[0] = "10 ReduceHP 20 3";
            skillStatusEffect[1] = "30 Freeze 0 2";
            skillStatusEffect[2] = "0 None 0 0";

            getHeroSkills();
        } 
        else if(heroClass.equalsIgnoreCase("Warrior"))
        {
            this.heroClass = "Warrior";
            heroSkills.add("Heavy Slash");
            heroSkills.add("Shield Bash");
            heroSkills.add("Slash");

            skillDamage[0] = (int)(constitution * 1.5);
            skillDamage[1] = (int)(constitution * 0.7);
            skillDamage[2] = (constitution);

            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Stun 0 1";
            skillStatusEffect[2] = "0 None 0 0";

            getHeroSkills();
        }
        restoreSkills(heroLevel);
    }

    /**
     * returns a list of the hero's skills.
     * 
     * Date created: October 9, 2020
     * 
     * @return temp - String
     */
    public String getHeroSkills()
    {
        String temp = "Your current skills are...\n";
        for(int i = 0; i < heroSkills.size(); i++)
        {
            temp += " - " + heroSkills.get(i) + "\n";
        }
        return temp;
    }

    /**
     * Returns an array of skill strings.
     * Used in the battle class when getting Hero skills.
     * 
     * Date Created: November 14, 2020
     * 
     * @return skills - String[]
     */
    public String[] getAttackSkills()
    {
        String[] skills = new String[heroSkills.size()];
        for(int i = 0; i < heroSkills.size(); i++)
        {
            skills[i] = heroSkills.get(i);
        }
        return skills;
    }
    /**
     * returns an array that contains the value of all skill damages.
     * 
     * Date created: October 9, 2020
     * 
     * @return skillDamage - int[]
     */
    public int[] getSkillDamage()
    {
        updateSkillDamage();
        return skillDamage;
    }

    /**
     * returns a description of the skill's damage as well as its side effects.
     * 
     * Date created: October 9, 2020
     * 
     * @param skill
     * @return String
     */
    public String getSkillDamageDescription(String skill)
    {
        updateSkillDamage();
        if(this.heroClass.equals("Mage"))
        {
            if(skill.equals("Fireball"))
            {
                return skill + " deals " + (int)(affinity * 1.1) + " Damage, with a 10% chance of Reducing the target's HP by 20 for 3 turns.";
            }
            if(skill.equals("Ice Spike"))
            {
                return skill + " deals " + (int)(affinity * 0.8) + " Damage, with a 30% chance to freeze the target for 1 turn.";
            }
            if(skill.equals("Orb"))
            {
                return skill + " deals " + (affinity) + " Damage.";
            }
            //Future moves for the Mage Class
            if(skill.equals("Rune of Despair"))
            {
                return skill + " deals " + (int)(affinity * 1.2) + " Damage, with a 30% of causing headache to the target, reducing their affinity by 10 for 3 turns.";
            }
            if(skill.equals("Enlightening"))
            {
                return skill + " deals " + (affinity * 2) + " Damage.";
            }
            if(skill.equals("Sigil of Smite"))
            {
                return skill + " deals " + (int)(affinity * 2.5) + " Damage, with a 50% chance of reducing the target's HP by 30 for 2 turns.";
            }
        }
        if(this.heroClass.equals("Warrior"))
        {
            if(skill.equals("Heavy Slash"))
            {
                return skill + " deals " + (int)(constitution * 1.5) + " Damage.";

            }
            if(skill.equals("Shield Bash"))
            {
                return skill + " deals " + (int)(constitution * 0.7) + " Damage, with a 30% chance to stun the target for 1 turn.";
            }
            if(skill.equals("Slash"))
            {
                return skill + " deals " + constitution + " Damage.";
            }
            //Future moves for the Warrior Class
            if(skill.equals("Charge"))
            {
                return skill + " deals " + (int)(constitution * 2) + " Damage.";
            }
            if(skill.equals("Weaken"))
            {
                return skill + " deals " + ((int)(constitution * 1.2)) + " Damage, with a 30% chance of reducing the target's constitution by 15 for 3 turns.";
            }
            if(skill.equals("Draining Strike"))
            {
                return skill + " deals " + (int)(constitution * 1.7) + " Damage, healing the user for 30% damage done.";
            }
        }
        return "error: " + skill + " is not a valid skill for your current class.";
    }
    /**
     * Lists the description of skills based on hero constitution and affinity.
     * 
     * Date created: October 9, 2020
     * 
     * @return temp - String
     */
    public String listAllSkillDamage()
    {
        String temp = "";
        for(int i = 0; i < heroSkills.size(); i++)
        {
            temp += getSkillDamageDescription(heroSkills.get(i)) + "\n";
        }
        return temp;
    }

    /**
     * Returns the damage done by a particular skill.
     * 
     * Date created: October 9, 2020
     * 
     * @param skill - String
     * @return skillDamage[];
     */
    public int heroAttack(String skill)
    {
        updateSkillDamage();
        if(heroClass.equals("Mage"))
        {
            if(skill.equalsIgnoreCase("Fireball"))
            {
                return skillDamage[0];
            }
            else if(skill.equalsIgnoreCase("Ice Spike"))
            {
                return skillDamage[1];
            }
            else if(skill.equalsIgnoreCase("Orb"))
            {
                return skillDamage[2];
            }
            //Future moves for the Mage Class
            else if(skill.equalsIgnoreCase("Rune of Despair"))
            {
                return skillDamage[3]; //Healing Skill!!
            }
            else if(skill.equalsIgnoreCase("Enlightening"))
            {
                return skillDamage[4];
            }
            else if(skill.equalsIgnoreCase("Sigil of Smite"))
            {
                return skillDamage[5];
            }
        }
        if(heroClass.equals("Warrior"))
        {
            if(skill.equalsIgnoreCase("Heavy Slash"))
            {
                return skillDamage[0];
            }
            else if(skill.equalsIgnoreCase("Shield Bash"))
            {
                return skillDamage[1];
            }
            else if(skill.equalsIgnoreCase("Slash"))
            {
                return skillDamage[2];
            }
            //Future moves for the Warrior Class
            else if(skill.equalsIgnoreCase("Charge"))
            {
                return skillDamage[3]; //Healing Skill!!
            }
            else if(skill.equalsIgnoreCase("Weaken"))
            {
                return skillDamage[4];
            }
            else if(skill.equalsIgnoreCase("Draining Strike"))
            {
                return skillDamage[5];
            }
        }
        return 0;
    }

    /**
     * updates affinity and constitution as well
     * as skill damages in the skillDamage array.
     * 
     * Date created: October 9, 2020
     * 
     */
    public void updateSkillDamage()
    {
        affinity = heroStats.getAffinity();
        constitution = heroStats.getConstitution();
        if(heroClass.equals("Mage"))
        {
            skillDamage[0] = (int)(affinity * 1.1);
            skillDamage[1] = (int)(affinity * 0.8);
            skillDamage[2] = affinity;
            skillDamage[3] = (int)(affinity * 1.2);
            skillDamage[4] = affinity * 2;
            skillDamage[5] = (int)(affinity * 2.5);
        }
        if(heroClass.equals("Warrior"))
        {
            skillDamage[0] = (int)(constitution * 1.5);
            skillDamage[1] = (int)(constitution * 0.7);
            skillDamage[2] = constitution;
            skillDamage[3] = constitution * 2;
            skillDamage[4] = (int)(constitution * 1.2);
            skillDamage[5] = (int)(constitution * 1.7);
        }
        
    }
    /**
     * returns the hero's class.
     * 
     * Date created: October 9, 2020
     * 
     * @return heroClass - String
     */
    public String getHeroClass()
    {
        return heroClass;
    }

    /**
     * returns a string informing the user about the hero gaining a new skill
     * if the Hero reaches a certain level. If not, returns an empty string.
     * 
     * Date created: October 9, 2020
     * 
     * @param heroLevel
     * @return String
     */
    public String checkHeroLevel(int heroLevel)
    {
        if(heroClass.equals("Mage"))
        {
            if(heroLevel == 3)
            {
                heroSkills.add("Rune of Despair");
                skillDamage[3] = (int)(affinity * 1.2);
                skillStatusEffect[3] = "30 Headache 10 3";

                return "\nThe Hero has gained a new skill!: Rune of Despair - Damage the target with an amount equal to affinity * 1.2 with a 30% of causing headache to the target, reducing their affinity by 10 for 3 turns.";
            }
            if(heroLevel == 5)
            {
                heroSkills.add("Enlightening");
                skillDamage[4] = affinity * 2;
                skillStatusEffect[4] = "0 None 0 0";

                return "\nThe Hero has gained a new skill!: Enlightening - Deal damage to the target with an amount equal to Affinity * 2.";
            }
            if(heroLevel == 15)
            {
                heroSkills.add("Sigil of Smite");
                skillDamage[5] = (int)(affinity * 2.5);
                skillStatusEffect[5] = "50 ReduceHP 30 2";

                return "\nThe Hero has gained a new skill!: Sigil of Smite - Smite the target with an amount equal to Affinity * 2.5, with a 50% chance of reducing the target's HP by 30 for 2 turns.";
            }
        }
        else
        {
            if(heroLevel == 3)
            {
                heroSkills.add("Charge");
                skillDamage[3] = constitution * 2;
                skillStatusEffect[3] = "0 None 0 0";
                return "\nThe Hero has gained a new skill!: Charge - Deal damage equal to Constitution * 2.";
            }
            if(heroLevel == 5)
            {
                heroSkills.add("Weaken");
                skillDamage[4] = (int)(constitution * 1.2);
                skillStatusEffect[4] = "30 Weaken 15 3";
                return "\nThe Hero has gained a new skill!: Weaken - Deal damage equal to constitution * 1.2, with a 30% chance of reducing the target's constitution by 15 for 3 turns.";
            }
            if(heroLevel == 15)
            {
                heroSkills.add("Draining Strike");
                skillDamage[5] = (int)(constitution * 1.7);
                skillStatusEffect[5] = "30 Drain 0 0";
                return "\nThe Hero has gained a new skill!: Draining Strike - Deal damage equal to constitution * 1.7, healing the user for 30% damage done.";
            }
        }
        return "";
    }

    /**
     * Returns the skill's proc chance, status effect, efficacy, and length in a String.
     * 
     * Date Created: October 9, 2020
     * 
     * @param index
     * @return skillStatusEffect[index] - String;
     */
    public String getSkillStatusEffect(int index)
    {
        return skillStatusEffect[index];
    }

    /**
     * Restores the Hero's skills after loading a save game.
     * 
     * Date created: September 1, 2021
     * 
     * @param heroLevel
     * @return String
     */
    public String restoreSkills(int heroLevel)
    {
        String message = "";
        if(heroClass.equals("Mage"))
        {
            if(heroLevel >= 3)
            {
                heroSkills.add("Rune of Despair");
                skillDamage[3] = (int)(affinity * 1.2);
                skillStatusEffect[3] = "30 Headache 10 3";

                message += "\nThe Hero has gained a new skill!: Rune of Despair - Damage the target with an amount equal to affinity * 1.2 with a 30% of causing headache to the target, reducing their affinity by 10 for 3 turns.";
            }
            if(heroLevel >= 5)
            {
                heroSkills.add("Enlightening");
                skillDamage[4] = affinity * 2;
                skillStatusEffect[4] = "0 None 0 0";

                message += "\nThe Hero has gained a new skill!: Enlightening - Deal damage to the target with an amount equal to Affinity * 2.";
            }
            if(heroLevel >= 15)
            {
                heroSkills.add("Sigil of Smite");
                skillDamage[5] = (int)(affinity * 2.5);
                skillStatusEffect[5] = "50 ReduceHP 30 2";

                message += "\nThe Hero has gained a new skill!: Sigil of Smite - Smite the target with an amount equal to Affinity * 2.5, with a 50% chance of reducing the target's HP by 30 for 2 turns.";
            }
        }
        else
        {
            if(heroLevel >= 3)
            {
                heroSkills.add("Charge");
                skillDamage[3] = constitution * 2;
                skillStatusEffect[3] = "0 None 0 0";
                message += "\nThe Hero has gained a new skill!: Charge - Deal damage equal to Constitution * 2.";
            }
            if(heroLevel >= 5)
            {
                heroSkills.add("Weaken");
                skillDamage[4] = (int)(constitution * 1.2);
                skillStatusEffect[4] = "30 Weaken 15 3";
                message += "\nThe Hero has gained a new skill!: Weaken - Deal damage equal to constitution * 1.2, with a 30% chance of reducing the target's constitution by 15 for 3 turns.";
            }
            if(heroLevel >= 15)
            {
                heroSkills.add("Draining Strike");
                skillDamage[5] = (int)(constitution * 1.7);
                skillStatusEffect[5] = "30 Drain 0 0";
                message += "\nThe Hero has gained a new skill!: Draining Strike - Deal damage equal to constitution * 1.7, healing the user for 30% damage done.";
            }
        }
        return message;
    }
}
