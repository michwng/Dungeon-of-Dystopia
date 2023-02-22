/**
 * --------------------------------------------------------------------------
 * File name: Statistics.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 10/8/2020
 * Completion Date: 11/3/2020
 * @version 1.00
 * --------------------------------------------------------------------------
 */
 /**
  * The Statistics keeps track of the
  * Hero's statistics and money.
  *
  * Date created: October 8, 2020
  * 
  * @author Michael Ng, ngmw01@etsu.edu
  */
public class Statistics
{
    //Fields to keep track of the hero's attributes.
    //They are instantiated in the constructor, based on the hero's class.
    private String heroName;
    private int maxHP;
    private int HP;
    private int constitution;
    private int affinity;
    private int armor;
    private int resistance;
    private int speed;
    //Non-battle Attributes
    private int money;

    /**
     * The constructor for the Statistics class.
     * 
     * String heroClass is needed here to determine the Hero's starting attributes.
     * 
     * Date created: October 8, 2020
     *  
     * @param heroClass - String
     */
    public Statistics(String heroClass, String heroName)
    {
        //Sets Attributes for a Mage Class Hero.
        if(heroClass.equalsIgnoreCase("Mage"))
        {
            heroClass = "Mage";
            money = 100;
            maxHP = 70;
            HP = 70;
            constitution = 5;
            affinity = 30;
            armor = 0;
            resistance = 10;
            speed = 18;

        }
        else //if (heroClass.equalsIgnoreCase("warrior"));
        {
            heroClass = "Warrior";
            money = 100;
            maxHP = 100;
            HP = 100;
            constitution = 20;
            affinity = 0;
            armor = 15;
            resistance = 10;
            speed = 10;
        }
        this.heroName = heroName;
    }
    /**
     * A constructor for the Statistics class.
     * Typically used when loading a save file.
     * 
     * Date Created: September 1, 2021
     */
    public Statistics(String heroName, int money, int maxHP, int HP, int constitution, int affinity, int armor, int resistance, int speed)
    {
        this.heroName = heroName;
        this.money = money;
        this.maxHP = maxHP;
        this.HP = HP;
        this.constitution = constitution;
        this.affinity = affinity;
        this.armor = armor;
        this.resistance = resistance;
        this.speed = speed;
    }

    //Accessor Methods

    /**
     * returns the hero's name.
     * 
     * @return heroName - String
     */
    public String getHeroName()
    {
        return heroName;
    }

    /**
     * returns the Hero's Max HP.
     * 
     * Date created: October 8, 2020
     * 
     * @return maxHP - int
     */
    public int getMaxHP()
    {
        return maxHP;
    }
    /**
     * returns the Hero's current HP.
     * 
     * Date created: October 8, 2020
     * 
     * @return HP - int
     */
    public int getHP()
    {
        return HP;
    }
    /**
     * returns the Hero's Constitution.
     * 
     * Date created: October 8, 2020
     * 
     * @return constitution - int
     */
    public int getConstitution()
    {
        return constitution;
    }
    /**
     * returns the Hero's Affinity.
     * 
     * Date created: October 8, 2020
     * 
     * @return affinity - int
     */
    public int getAffinity()
    {
        return affinity;
    }
    /**
     * returns the Hero's Armor.
     * 
     * Date created: October 8, 2020
     * 
     * @return armor - int
     */
    public int getArmor()
    {
        return armor;
    }
    /**
     * returns the Hero's Resistance.
     * 
     * Date created: October 8, 2020
     * 
     * @return resistance - int
     */
    public int getResistance()
    {
        return resistance;
    }
    /**
     * returns the Hero's Speed.
     * 
     * Date created: October 8, 2020
     * 
     * @return speed - int
     */
    public int getSpeed()
    {
        return speed;
    }
    /**
     * returns the Hero's Money.
     * 
     * Date created: October 8, 2020
     * 
     * @return money - int
     */
    public int getMoney()
    {
        return money;
    }

    //Mutator Methods
    /**
     * Changes the value of the Hero's maxHP.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addMaxHP(int change)
    {
        maxHP += change;
        HP += change;
    }
    /**
     * Changes the value of the Hero's HP.
     * 
     * Date created: October 8, 2020
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
    }
    /**
     * Changes the value of the Hero's Constitution.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addConstitution(int change)
    {
        constitution += change;
    }
    /**
     * Changes the value of the Hero's Affinity.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addAffinity(int change)
    {
        affinity += change;
    }
    /**
     * Changes the value of the Hero's Armor.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addArmor(int change)
    {
        armor += change;
    }
    /**
     * Changes the value of the Hero's Resistance.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addResistance(int change)
    {
        resistance += change;
    }
    /**
     * Changes the value of the Hero's Speed.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addSpeed(int change)
    {
        speed += change;
    }
    /**
     * Changes the value of the Hero's Money.
     * 
     * Date created: October 8, 2020
     * 
     * @param change
     */
    public void addMoney(int change)
    {
        money += change;
    }

    //Secondary mutator methods
    /**
     * Sets the hero's Max HP to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param maxHP
     */
    public void setMaxHP(int amount)
    {
        maxHP = amount;
    }
    /**
     * Set's the Hero's HP to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setHP(int amount)
    {
        HP = amount;
    }
    /**
     * Set's the Hero's Constitution to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setConstitution(int amount)
    {
        constitution = amount;
    }
    /**
     * Set's the Hero's Affinity to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setAffinity(int amount)
    {
        affinity = amount;
    }
    /**
     * Set's the Hero's Armor to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setArmor(int amount)
    {
        armor = amount;
    }
    /**
     * Set's the Hero's Resistance to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setResistance(int amount)
    {
        resistance = amount;
    }
    /**
     * Set's the Hero's Speed to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setSpeed(int amount)
    {
        speed = amount;
    }

    /**
     * Set's the Hero's Money to the specified value.
     * 
     * Date created: November 3, 2020
     * 
     * @param amount
     */
    public void setMoney(int amount)
    {
        money = amount;
    }






    /**
     * Lists all of the Hero's Current Attributes.
     * Can also be considered the toString() method.
     * 
     * Date created: October 8, 2020
     * 
     * @return allAttributes - String
     */
    public String listAttributes()
    {
        String allAttributes = "Here are your current Attributes. \n";
        allAttributes += "Max HP: " + maxHP + "\n";
        allAttributes += "HP: " + HP + "\n";
        allAttributes += "Constitution: " + constitution + "\n";
        allAttributes += "Affinity: " + affinity + "\n";
        allAttributes += "Armor: " + armor + "\n";
        allAttributes += "Resistance: " + resistance + "\n";
        allAttributes += "Speed: " + speed + "\n";
        allAttributes += "Money: " + money;

        return allAttributes;
    }
    
    /**
     * Describes the usage of each Attribute.
     * 
     * Date created: October 8, 2020
     * 
     * @param heroClass
     * @return attributeDesc - String
     */
    public String attributeDescription(String heroClass)
    {
        String attributeDesc = "Description of Attributes: \n";
        attributeDesc += "Max HP: The maximum amount of HP the Hero can have.\n";
        attributeDesc += "HP: Represents your Hero's Life Force. The game ends when HP reaches 0.\n";
        attributeDesc += "Constitution: Represents the amount of Physical Damage your Hero can deal.\n";
        attributeDesc += "Affinity: Represents the amount of Magical Damage your Hero can deal.\n";
        attributeDesc += "Armor: Reduces the amount of Physical Damage Taken.\n";
        attributeDesc += "Resistance: Reduces the amount of Magical Damage Taken.\n";
        attributeDesc += "Speed: Determines who will go first in Battle. The higher, the better the chance.\n";
        if(heroClass.equalsIgnoreCase("Mage"))
        {
            attributeDesc += "Mage Class: A capable class capable of dishing out and resisting strong magical attacks. However, they are weak to Physical Attacks.\n";
        }
        if(heroClass.equalsIgnoreCase("Warrior"))
        {
            attributeDesc += "Warrior Class: A robust class capable of withstanding many attacks. Minor weakness to Magical attacks.\n";
        }

        return attributeDesc;
    }
}