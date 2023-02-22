/**
 * --------------------------------------------------------------------------
 * File name: Ally.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 11/3/2020
 * Completion Date: 11/23/2020
 * @version 
 * --------------------------------------------------------------------------
 */

import java.util.ArrayList;
/**
  * The Ally class keeps track of
  * Allied Attributes and Stats. 
  * As well as their skills.
  *
  * This class is used in the Battle class.
  *
  * Date created: November 3, 2020
  * 
  * @author Michael Ng, ngmw01@etsu.edu
  */
public class Ally
{
    private String allyName;
    private String allyClass;
    private String allyDescription;

    private ArrayList<String> allySkills = new ArrayList<String>();
    private ArrayList<String> allySkillDescription = new ArrayList<String>();
    private ArrayList<String> allySkillStatusEffect = new ArrayList<String>();
    private ArrayList<Integer> allySkillDamage = new ArrayList<Integer>();

    private int maxHP;
    private int HP;
    private int constitution;
    private int affinity;
    private int armor;
    private int resistance;
    private int speed;

    /**
     * Zacharias assumes the opposite class of the Hero.
     * Anthiera assumes the same class as the Hero.
     * They also account for weaknesses that the hero has.
     * 
     * Formatted similarly to both the Statistics and Enemy class constructors.
     * 
     * Date Created: November 3, 2020
     * 
     * @param allyClass
     */
    public Ally(String heroClass, String allyName)
    {
        if(allyName.equals("Zacharias"))
        {
            allyDescription = "Zacharias is your long-time friend. He is remarkably strong, but also quite fragile.";
            if(heroClass.equals("Mage"))
            {
                allyClass = "Berserker";
                maxHP = 100;
                HP = 100;
                constitution = 35;
                affinity = 0;
                armor = 15;
                resistance = 5;
                speed = 5;
                
                allySkills.add("Strike");
                allySkills.add("Disarm");
                allySkills.add("Slash");
                allySkillDescription.add("Strike: Aim for an enemy's vitals. Deals damage equal to Constitution * 1.2 with a 30% chance to bleed the enemy for 5HP every turn for 3 turns.");
                allySkillDescription.add("Disarm: Attempt to disarm the enemy by slashing at their weapon. Deals damage equal to constitution with a 40% chance to Silence the enemy for 3 turns.");
                allySkillDescription.add("Slash: Slash the enemy with a sword. Deals damage equal to constitution * 1.1.");
                allySkillStatusEffect.add("30 Bleed 5 3");
                allySkillStatusEffect.add("40 Silence 0 3");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(constitution * 1.2));
                allySkillDamage.add(constitution);
                allySkillDamage.add((int)(constitution * 1.1));
            }
            else
            {
                allyClass = "Sage";
                maxHP = 80;
                HP = 80;
                constitution = 0;
                affinity = 30;
                armor = 0;
                resistance = 10;
                speed = 18;

                allySkills.add("Tome of Draining");
                allySkills.add("Boulder");
                allySkills.add("Orb");
                allySkillDescription.add("Tome of Draining: Cast a draining spell on the enemy. Deals damage equal to affinity * 1.2, healing the user for 50% of the damage done.");
                allySkillDescription.add("Boulder: Summon a Boulder and cast it towards the enemy. Deals damage equal to affinity * 1.7");
                allySkillDescription.add("Orb: Cast a regular magical spell at the target. Deals damage equal to affinity.");
                allySkillStatusEffect.add("50 Drain 0 0");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(affinity * 1.2));
                allySkillDamage.add((int)(affinity * 1.7));
                allySkillDamage.add(affinity);
            }
        }
        else //For ally Anthiera
        {
            //if hero is a mage
            if(heroClass.equals("Mage"))
            {
                allyClass = "Sage";
                allyDescription = "Anthiera is a formidable Sage from the Fendalian Royal Guard. \nShe deals large amounts of Magical damage.";
                maxHP = 80;
                HP = 80;
                constitution = 0;
                affinity = 30;
                armor = 0;
                resistance = 10;
                speed = 18;

                allySkills.add("Soul Grab");
                allySkills.add("Charm");
                allySkills.add("Orb");
                allySkillDescription.add("Soul Grab: Attempt to steal the target's soul. Deals damage equal to affinity and 60% chance to reduce the target's HP by 40 for 3 turns.");
                allySkillDescription.add("Charm: Cast an illusion spell on the enemy to have Anthiera appear more majestic. Deals damage equal to affinity * 0.7, with a 40% chance to Pacify the target for 3 turns.");
                allySkillDescription.add("Orb: Cast a regular magical spell at the target. Deals damage equal to affinity.");
                allySkillStatusEffect.add("60 ReduceHP 40 3");
                allySkillStatusEffect.add("40 Pacify 0 3");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add(affinity);
                allySkillDamage.add((int)(affinity * 0.7));
                allySkillDamage.add(affinity);
            }
            else // ally is a beserker
            {
                allyClass = "Berserker";
                allyDescription = "Anthiera is a formidable Beserker from the Fendalian Royal Guard. \nShe deals large amounts of Physical damage.";
                maxHP = 100;
                HP = 100;
                constitution = 35;
                affinity = 0;
                armor = 15;
                resistance = 5;
                speed = 5;
                
                allySkills.add("Stun");
                allySkills.add("Sunder");
                allySkills.add("Slash");
                allySkillDescription.add("Stun: Bludgeons the enemy with the hilt of their sword. Deals damage equal to Constitution * 0.8 with a 30% chance to stun the enemy for 2 turns.");
                allySkillDescription.add("Sunder: Jump and perform a devestating downwards slash upon the target. Deals damage equal to Constitution * 1.5.");
                allySkillDescription.add("Slash: Slash the enemy with a sword. Deals damage equal to constitution.");
                allySkillStatusEffect.add("30 Stun 0 2");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(constitution * 0.8));
                allySkillDamage.add((int)(constitution * 1.5));
                allySkillDamage.add(constitution);
            }
        }
        this.allyName = allyName;
    }

    /**
     * Secondary constructor. 
     * Used when laoding from a save file.
     * 
     * Date Created: September 1, 2021
     * 
     * @param allyClass
     */
    public Ally(String allyName, int maxHP, int HP, int constitution, int affinity, int armor, int resistance, int speed, String allyClass)
    {
        if(allyName.equals("Zacharias"))
        {
            allyDescription = "Zacharias is your long-time friend. He is remarkably strong, but also quite fragile.";
            if(allyClass.equals("Berserker"))
            {
                this.allyClass = "Berserker";
                this.maxHP = maxHP;
                this.HP = HP;
                this.constitution = constitution;
                this.affinity = affinity;
                this.armor = armor;
                this.resistance = resistance;
                this.speed = speed;
                
                allySkills.add("Strike");
                allySkills.add("Disarm");
                allySkills.add("Slash");
                allySkillDescription.add("Strike: Aim for an enemy's vitals. Deals damage equal to Constitution * 1.2 with a 30% chance to bleed the enemy for 5HP every turn for 3 turns.");
                allySkillDescription.add("Disarm: Attempt to disarm the enemy by slashing at their weapon. Deals damage equal to constitution with a 40% chance to Silence the enemy for 3 turns.");
                allySkillDescription.add("Slash: Slash the enemy with a sword. Deals damage equal to constitution * 1.1.");
                allySkillStatusEffect.add("30 Bleed 5 3");
                allySkillStatusEffect.add("40 Silence 0 3");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(constitution * 1.2));
                allySkillDamage.add(constitution);
                allySkillDamage.add((int)(constitution * 1.1));
            }
            else
            {
                this.allyClass = "Sage";
                this.maxHP = maxHP;
                this.HP = HP;
                this.constitution = constitution;
                this.affinity = affinity;
                this.armor = armor;
                this.resistance = resistance;
                this.speed = speed;

                allySkills.add("Tome of Draining");
                allySkills.add("Boulder");
                allySkills.add("Orb");
                allySkillDescription.add("Tome of Draining: Cast a draining spell on the enemy. Deals damage equal to affinity * 1.2, healing the user for 50% of the damage done.");
                allySkillDescription.add("Boulder: Summon a Boulder and cast it towards the enemy. Deals damage equal to affinity * 1.7");
                allySkillDescription.add("Orb: Cast a regular magical spell at the target. Deals damage equal to affinity.");
                allySkillStatusEffect.add("50 Drain 0 0");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(affinity * 1.2));
                allySkillDamage.add((int)(affinity * 1.7));
                allySkillDamage.add(affinity);
            }
        }
        else //For ally Anthiera
        {
            //if hero is a mage
            if(allyClass.equals("Sage"))
            {
                this.allyClass = "Sage";
                allyDescription = "Anthiera is a formidable Sage from the Fendalian Royal Guard. \nShe deals large amounts of Magical damage.";
                this.maxHP = maxHP;
                this.HP = HP;
                this.constitution = constitution;
                this.affinity = affinity;
                this.armor = armor;
                this.resistance = resistance;
                this.speed = speed;

                allySkills.add("Soul Grab");
                allySkills.add("Charm");
                allySkills.add("Orb");
                allySkillDescription.add("Soul Grab: Attempt to steal the target's soul. Deals damage equal to affinity and 60% chance to reduce the target's HP by 40 for 3 turns.");
                allySkillDescription.add("Charm: Cast an illusion spell on the enemy to have Anthiera appear more majestic. Deals damage equal to affinity * 0.7, with a 40% chance to Pacify the target for 3 turns.");
                allySkillDescription.add("Orb: Cast a regular magical spell at the target. Deals damage equal to affinity.");
                allySkillStatusEffect.add("60 ReduceHP 40 3");
                allySkillStatusEffect.add("40 Pacify 0 3");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add(affinity);
                allySkillDamage.add((int)(affinity * 0.7));
                allySkillDamage.add(affinity);
            }
            else // ally is a beserker
            {
                this.allyClass = "Berserker";
                allyDescription = "Anthiera is a formidable Beserker from the Fendalian Royal Guard. \nShe deals large amounts of Physical damage.";
                this.maxHP = maxHP;
                this.HP = HP;
                this.constitution = constitution;
                this.affinity = affinity;
                this.armor = armor;
                this.resistance = resistance;
                this.speed = speed;
                
                allySkills.add("Stun");
                allySkills.add("Sunder");
                allySkills.add("Slash");
                allySkillDescription.add("Stun: Bludgeons the enemy with the hilt of their sword. Deals damage equal to Constitution * 0.8 with a 30% chance to stun the enemy for 2 turns.");
                allySkillDescription.add("Sunder: Jump and perform a devestating downwards slash upon the target. Deals damage equal to Constitution * 1.5.");
                allySkillDescription.add("Slash: Slash the enemy with a sword. Deals damage equal to constitution.");
                allySkillStatusEffect.add("30 Stun 0 2");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(constitution * 0.8));
                allySkillDamage.add((int)(constitution * 1.5));
                allySkillDamage.add(constitution);
            }
        }
        this.allyName = allyName;
    }

    //Accessor Methods
    /**
     * returns the Ally's Max HP.
     * 
     * Date created: November 4, 2020
     * 
     * @return maxHP - int
     */
    public int getMaxHP()
    {
        return maxHP;
    }
    /**
     * returns the Ally's current HP.
     * 
     * Date created: November 4, 2020
     * 
     * @return HP - int
     */
    public int getHP()
    {
        return HP;
    }
    /**
     * returns the Ally's Constitution.
     * 
     * Date created: November 4, 2020
     * 
     * @return constitution - int
     */
    public int getConstitution()
    {
        return constitution;
    }
    /**
     * returns the Ally's Affinity.
     * 
     * Date created: November 4, 2020
     * 
     * @return affinity - int
     */
    public int getAffinity()
    {
        return affinity;
    }
    /**
     * returns the Ally's Armor.
     * 
     * Date created: November 4, 2020
     * 
     * @return armor - int
     */
    public int getArmor()
    {
        return armor;
    }
    /**
     * returns the Ally's Resistance.
     * 
     * Date created: November 4, 2020
     * 
     * @return resistance - int
     */
    public int getResistance()
    {
        return resistance;
    }
    /**
     * returns the Ally's Speed.
     * 
     * Date created: November 4, 2020
     * 
     * @return speed - int
     */
    public int getSpeed()
    {
        return speed;
    }

    public String getAllyName()
    {
        return allyName;
    }

    /**
     * returns the ally's class.
     * 
     * Date created: November 4, 2020
     * 
     * @return allyClass - String
     */
    public String getAllyClass()
    {
        return allyClass;
    }

    /**
     * Returns an array of skill strings.
     * Used in the battle class when getting Hero skills.
     * 
     * Date Created: November 15, 2020
     * 
     * @return skills - String[]
     */
    public String[] getAttackSkills()
    {
        String[] skills = new String[allySkills.size()];
        for(int i = 0; i < allySkills.size(); i++)
        {
            skills[i] = allySkills.get(i);
        }
        return skills;
    }

    public String getAllyDescription()
    {
        return allyDescription;
    }

    //Mutator Methods
    /**
     * Changes the value of the Ally's maxHP.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addMaxHP(int change)
    {
        maxHP += change;
        HP += change;
    }
    /**
     * Changes the value of the Ally's HP.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addHP(int change)
    {
        HP += change;
        //To prevent HP from going over maxHP.
        if(HP > maxHP)
        {
            HP = maxHP;
        }
    }/**
     * Changes the value of the Ally's Constitution.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addConstitution(int change)
    {
        constitution += change;
    }
    /**
     * Changes the value of the Ally's Affinity.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addAffinity(int change)
    {
        affinity += change;
    }
    /**
     * Changes the value of the Ally's Armor.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addArmor(int change)
    {
        armor += change;
    }
    /**
     * Changes the value of the Ally's Resistance.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addResistance(int change)
    {
        resistance += change;
    }
    /**
     * Changes the value of the Ally's Speed.
     * 
     * Date created: November 4, 2020
     * 
     * @param change
     */
    public void addSpeed(int change)
    {
        speed += change;
    }

    //Secondary mutator methods
    /**
     * Sets the ally's Max HP to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param maxHP
     */
    public void setMaxHP(int amount)
    {
        maxHP = amount;
    }
    /**
     * Set's the Ally's HP to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param amount
     */
    public void setHP(int amount)
    {
        HP = amount;
    }
    /**
     * Set's the Ally's Constitution to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param amount
     */
    public void setConstitution(int amount)
    {
        constitution = amount;
    }
    /**
     * Set's the Ally's Affinity to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param amount
     */
    public void setAffinity(int amount)
    {
        affinity = amount;
    }
    /**
     * Set's the Ally's Armor to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param amount
     */
    public void setArmor(int amount)
    {
        armor = amount;
    }
    /**
     * Set's the Ally's Resistance to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param amount
     */
    public void setResistance(int amount)
    {
        resistance = amount;
    }
    /**
     * Set's the Ally's Speed to the specified value.
     * 
     * Date created: November 4, 2020
     * 
     * @param amount
     */
    public void setSpeed(int amount)
    {
        speed = amount;
    }

    /**
     * Lists all of the Ally's Current Attributes.
     * Can also be considered the toString() method.
     * 
     * Date created: November 4, 2020
     * 
     * @return allAttributes - String
     */
    public String listAttributes()
    {
        String allAttributes = "Here are " + allyName + "'s current Attributes. \n";
        allAttributes += "Max HP: " + maxHP + "\n";
        allAttributes += "HP: " + HP + "\n";
        allAttributes += "Constitution: " + constitution + "\n";
        allAttributes += "Affinity: " + affinity + "\n";
        allAttributes += "Armor: " + armor + "\n";
        allAttributes += "Resistance: " + resistance + "\n";
        allAttributes += "Speed: " + speed;

        return allAttributes;
    }

    /**
     * Lists all of the Ally's Attributes and 
     * skill information.
     * 
     * Date created: November 16, 2020
     * 
     * @return allAttributes - String
     */
    public String getAllyInformation()
    {
        String message = allyName + "\n____________________\n";
        message += "Ally Class: " + allyClass + "\n";
        message += "Ally Class Description: A " + allyClass + " is capable of dishing out massive amounts of damage. \n - However, they are weak against both physical and magical damage.\n";
        message += "Max HP: " + maxHP + "\n";
        message += "HP: " + HP + "\n";
        message += "Constitution: " + constitution + "\n";
        message += "Affinity: " + affinity + "\n";
        message += "Armor: " + armor + "\n";
        message += "Resistance: " + resistance + "\n";
        message += "Speed: " + speed;
        message += "\n\n";

        for(int i = 0; i < allySkills.size(); i++)
        {
            message += "Skill " + (i + 1) + ": " + allySkills.get(i) + "\n";
            message += allySkillDescription.get(i) + "\n";
            message += getSkillDamageDescription(allySkills.get(i)) + "\n";
        }

        return message;
    }

    /**
     * returns a list of the ally's skills.
     * 
     * Date created: November 4, 2020
     * 
     * @return temp - String
     */
    public String getAllySkills()
    {
        String temp = "Your Ally's current skills are...\n";
        for(int i = 0; i < allySkills.size(); i++)
        {
            temp += " - " + allySkills.get(i) + "\n";
        }
        return temp;
    }

    
    /**
     * returns an array that contains the value of all skill damages.
     * 
     * Date created: November 4, 2020
     * 
     * @return allySkillDamage - ArrayList<Integer>
     */
    public ArrayList<Integer> getSkillDamage()
    {
        return allySkillDamage;
    }
    /**
     * returns an ally's skill description at the specified index.
     * 
     * Date Created: November 23, 2020
     * 
     * @param index
     * @return allySkillDescription.get(index)
     */
    public String getSkillDescription(int index)
    {
        return allySkillDescription.get(index);
    }
    /**
     * returns a description of the skill's damage as well as its side effects.
     * 
     * Date created: November 4, 2020
     * 
     * @param skill
     * @return String
     */
    private String getSkillDamageDescription(String skill)
    {
        updateSkillDamage();
        if(allyClass.equals("Sage"))
        {
            if(skill.equals("Tome of Draining")) //Zacharias Beginner Skill
            {
                return skill + " deals " + allySkillDamage.get(0) + " Damage, healing the user for 50% of the damage done.";
            }
            if(skill.equals("Boulder")) //Zacharias Beginner Skill
            {
                return skill + " deals " + allySkillDamage.get(1) + " Damage.";
            }
            if(skill.equals("Soul Grab")) //Anthiera Beginner Skill
            {
                return skill + " deals " + allySkillDamage.get(0) + " Damage, with a 60% chance to reduce the target's HP by 40 for 3 turns.";
            }
            if(skill.equals("Charm")) // Anthiera Beginner Skill
            {
                return skill + " deals " + allySkillDamage.get(1) + " Damage, but has a 40% chance to Pacify the target for 3 turns.";
            }
            if(skill.equals("Orb")) //Shared Skill
            {
                return skill + " deals " + allySkillDamage.get(2) + " Damage.";
            }
            //Future moves for the Sage Class. Shared by both allies.
            if(skill.equals("Restore"))
            {
                return skill + " heals the user or an ally for " + allySkillDamage.get(3) + "HP, depending on who has lower HP Percentage.";
            }
            if(skill.equals("Enlightening"))
            {
                return skill + " deals " + allySkillDamage.get(4) + " Damage.";
            }
            if(skill.equals("Spear of Light"))
            {
                return skill + " deals " + allySkillDamage.get(5) + " Damage, with a 40% chance to Bleed the target for 15HP every turn for 5 turns.";
            }
        }
        if(allyClass.equals("Berserker"))
        {
            if(skill.equals("Strike")) //Zacharias Beginning Skill
            {
                return skill + " deals " + allySkillDamage.get(0) + " Damage, with a 30% chance to bleed the enemy for 5HP every turn for 3 turns.";
            }
            if(skill.equals("Disarm")) //Zacharias Beginning Skill
            {
                return skill + " deals " + allySkillDamage.get(1) + " Damage, with a 40% chance to Silence the enemy for 3 turns.";
            }
            if(skill.equals("Stun")) //Anthiera Beginning Skill 
            {
                return skill + " deals " + allySkillDamage.get(0) + " Damage, with a 30% chance to stun the enemy for 2 turns.";
            }
            if(skill.equals("Sunder")) //Anthiera Beginning Skill
            {
                return skill + " deals " + allySkillDamage.get(1) + " Damage.";
            }
            if(skill.equals("Slash")) // Shared Skill
            {
                return skill + " deals " + allySkillDamage.get(2) + " Damage.";
            }
            //Future moves for the Berserker Class. Shared by both allies.
            if(skill.equals("Courage"))
            {
                return skill + " heals the user or an ally for " + allySkillDamage.get(3) + "HP, depending on who has lower HP Percentage.";
            }
            if(skill.equals("Weaken"))
            {
                return skill + " deals " + allySkillDamage.get(4) + " Damage, with a 40% chance of reducing the target's ATK by 15 for 4 turns.";
            }
            if(skill.equals("Banesmark"))
            {
                return skill + " deals " + allySkillDamage.get(5) + " Damage, with a 50% chance of Bleeding the target for 10HP every turn for 5 turns.";
            }
        }
        return "error: " + skill + " is not a valid skill for your current class.";
    }
    /**
     * Lists the description of skills based on ally constitution and affinity.
     * 
     * Date created: November 4, 2020
     * 
     * @return temp - String
     */
    public String listAllSkillDamage()
    {
        updateSkillDamage();
        String temp = "";
        for(int i = 0; i < allySkills.size(); i++)
        {
            temp += getSkillDamageDescription(allySkills.get(i)) + "\n";
        }
        return temp;
    }

    /**
     * Returns the skill's proc chance, status effect, efficacy, and length in a String.
     * 
     * Date created: November 5, 2020
     * 
     * @param index
     * @return skillStatusEffect[index] - String;
     */
    public String getSkillStatusEffect(int index)
    {
        return allySkillStatusEffect.get(index);
    }

    /**
     * updates affinity and constitution as well
     * as skill damages in the skillDamage array.
     * 
     * Date created: November 18, 2020
     * 
     */
    public void updateSkillDamage()
    {
        allySkillDamage = new ArrayList<Integer>();
        if(allyName.equals("Zacharias"))
        {
            if(allyClass.equals("Sage"))
            {
                allySkillDamage.add((int)(affinity * 1.2));
                allySkillDamage.add((int)(affinity * 1.7));
                allySkillDamage.add(affinity);
                allySkillDamage.add((int)(affinity * 0.8));
                allySkillDamage.add(affinity * 2);
                allySkillDamage.add((int)(affinity * 2.3));
            }
            else //if(allyClass.equals("Warrior"))
            {
                allySkillDamage.add((int)(constitution * 1.2));
                allySkillDamage.add(constitution);
                allySkillDamage.add((int)(constitution * 1.1));
                allySkillDamage.add((int)(constitution * 0.6));
                allySkillDamage.add(constitution);
                allySkillDamage.add((int)(constitution * 2.3));
            }
        }
        else //if(allyName.equals("Anthiera"))
        {
            if(allyClass.equals("Sage"))
            {
                allySkillDamage.add(affinity);
                allySkillDamage.add((int)(affinity * 0.7));
                allySkillDamage.add(affinity);
                allySkillDamage.add(affinity);
                allySkillDamage.add(affinity * 2);
                allySkillDamage.add((int)(affinity * 2.3));
            }
            else //if(allyClass.equals("Warrior"))
            {
                allySkillDamage.add((int)(constitution * 0.8));
                allySkillDamage.add((int)(constitution * 1.5));
                allySkillDamage.add(constitution);
                allySkillDamage.add((int)(constitution * 0.5));
                allySkillDamage.add(constitution);
                allySkillDamage.add((int)(constitution * 2.3));
            }
        }
    }

    /**
     * returns a string informing the user about the ally gaining a new skill
     * if the Hero reaches a certain level. If not, returns an empty string.
     * Ally Level grows alongside the Hero's level.
     * 
     * Date created: November 4, 2020
     * 
     * @param allyLevel - int
     * @return String - return depends on the heroLevel.
     */
    public String checkAllyLevel(int heroLevel)
    {
        updateSkillDamage();
        if(allyClass.equals("Sage"))
        {
            if(heroLevel == 3)
            {
                allySkills.add("Restore");
                allySkillDescription.add("Restore: Heal the user or an ally with an amount equal to Affinity * 0.8, depending on who has lower HP percentage.");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(affinity * 0.8));
                return "\nThe Ally has gained a new skill!: Restore - Heal the user or an ally with an amount equal to Affinity * 0.8, depending on who has lower HP percentage.";
            }
            if(heroLevel == 5)
            {
                allySkills.add("Enlightening");
                allySkillDescription.add("Enlightening: Shower the Enemy in Holy Light. Deals damage equal to Affinity * 2.");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add(affinity * 2);
                return "\nThe Ally has gained a new skill!: Enlightening - Deal damage to the target with an amount equal to Affinity * 2.";
            }
            if(heroLevel == 15)
            {
                allySkills.add("Spear of Light");
                allySkillDescription.add("Spear of Light: Summon a Spear of Light to eviscerate the enemy. Deals damage equal to Affinity * 2.3 with a 40% chance to Bleed the target for 15HP over 5 turns.");
                allySkillStatusEffect.add("40 Bleed 15 5");
                allySkillDamage.add((int)(affinity * 2.3));
                return "\nThe Ally has gained a new skill!: Spear of Light - Deal damage to the target with an amount equal to Affinity * 2.3 with a 40% chance to Bleed the target for 15HP over 5 turns.";
            }
        }
        else
        {
            if(heroLevel == 3)
            {
                allySkills.add("Courage");
                allySkillDescription.add("Courage: Heal the user or an ally with an amount equal to Constitution * 0.6, depending on who has lower HP percentage.");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(constitution * 0.6));
                return "\nThe Ally has gained a new skill!: Courage - Heal the user or an ally with an amount equal to Constitution * 0.6, depending on who has lower HP percentage.";
            }
            if(heroLevel == 5)
            {
                allySkills.add("Weaken");
                allySkillDescription.add("Weaken: 40% chance to weaken the enemy for 15 constitution for 4 turns. Deals damage equal to constitution.");
                allySkillStatusEffect.add("40 Weaken 15 4");
                allySkillDamage.add((int)(constitution));
                return "\nThe Ally has gained a new skill!: Weaken: 40% chance to weaken the enemy for 15 constitution for 4 turns. Deals damage equal to constitution.";
            }
            if(heroLevel == 15)
            {
                allySkills.add("Banesmark");
                allySkillDescription.add("Banesmark: Unsheathe the Legendary Banesmark and perform a flurry of attacks on the enemy. Deals damage equal to constitution * 2.3 with a 50% chance of bleeding the target for 10HP every turn for 5 turns.");
                allySkillStatusEffect.add("50 Bleed 10 5");
                allySkillDamage.add(constitution);
                return "\nThe Ally has gained a new skill!: Banesmark - Unsheathe the Legendary Banesmark and perform a flurry of attacks on the enemy. Deals damage equal to constitution * 2.3 with a 50% chance of bleeding the target for 10HP every turn for 5 turns.";
            }
        }
        return "";
    }

    /**
     * Used mainly for constructing Anthiera. 
     * Restores lost skills in previous level ups.
     * 
     * Date created: November 19, 2020
     * 
     * @param heroLevel
     * @return String
     */
    public String addSkills(int heroLevel)
    {
        String message = "";
        updateSkillDamage();
        if(allyClass.equals("Sage"))
        {
            if(heroLevel >= 3)
            {
                allySkills.add("Restore");
                allySkillDescription.add("Restore: Heal the user or an ally with an amount equal to Affinity * 0.8, depending on who has lower HP percentage.");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(affinity * 0.8));
                message += "\nThe Ally has gained a new skill!: Restore - Heal the user or an ally with an amount equal to Affinity * 0.8, depending on who has lower HP percentage.";
            }
            if(heroLevel >= 5)
            {
                allySkills.add("Enlightening");
                allySkillDescription.add("Enlightening: Shower the Enemy in Holy Light. Deals damage equal to Affinity * 2.");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add(affinity * 2);
                message += "\nThe Ally has gained a new skill!: Enlightening - Deal damage to the target with an amount equal to Affinity * 2.";
            }
            if(heroLevel >= 15)
            {
                allySkills.add("Spear of Light");
                allySkillDescription.add("Spear of Light: Summon a Spear of Light to eviscerate the enemy. Deals damage equal to Affinity * 2.3 with a 40% chance to Bleed the target for 15HP over 5 turns.");
                allySkillStatusEffect.add("40 Bleed 15 5");
                allySkillDamage.add((int)(affinity * 2.3));
                message += "\nThe Ally has gained a new skill!: Spear of Light - Deal damage to the target with an amount equal to Affinity * 2.3 with a 40% chance to Bleed the target for 15HP over 5 turns.";
            }
        }
        else
        {
            if(heroLevel >= 3)
            {
                allySkills.add("Courage");
                allySkillDescription.add("Courage: Heal the user or an ally with an amount equal to Constitution * 0.6, depending on who has lower HP percentage.");
                allySkillStatusEffect.add("0 None 0 0");
                allySkillDamage.add((int)(constitution * 0.6));
                message += "\nThe Ally has gained a new skill!: Courage - Heal the user or an ally with an amount equal to Constitution * 0.6, depending on who has lower HP percentage.";
                
            }
            if(heroLevel >= 5)
            {
                allySkills.add("Weaken");
                allySkillDescription.add("Weaken: 40% chance to weaken the enemy for 15 constitution for 4 turns. Deals damage equal to constitution.");
                allySkillStatusEffect.add("40 Weaken 15 4");
                allySkillDamage.add((int)(constitution));
                message += "\nThe Ally has gained a new skill!: Weaken - 40% chance to weaken the enemy for 15 constitution for 4 turns. Deals damage equal to constitution.";
            }
            if(heroLevel >= 15)
            {
                allySkills.add("Banesmark");
                allySkillDescription.add("Banesmark: Unsheathe the Legendary Banesmark and perform a flurry of attacks on the enemy. Deals damage equal to constitution * 2.3 with a 50% chance of bleeding the target for 10HP every turn for 5 turns.");
                allySkillStatusEffect.add("50 Bleed 10 5");
                allySkillDamage.add((int)(constitution * 2.3));
                message += "\nThe Ally has gained a new skill!: Banesmark - Unsheathe the Legendary Banesmark and perform a flurry of attacks on the enemy. Deals damage equal to constitution * 2.3 with a 50% chance of bleeding the target for 10HP every turn for 5 turns.";
            }
        }
        return message;
    }
}
