/**
 * --------------------------------------------------------------------------
 * File name: Enemy.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 11/7/2020
 * Completion Date: 11/23/2020
 * @version 1.00
 * --------------------------------------------------------------------------
 */
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The Enemy Class creates a random enemy with pre-defined stats when calling
 * the constructor.
 *
 * The Enemy Class also keeps track of the enemy's skills, skill damage, and
 * attributes. As well as defining the xp award and money award for defeating
 * the enemy.
 *
 *
 * Date created: November 7, 2020
 * 
 * @author Michael Ng, ngmw01@etsu.edu
 */
public class Enemy
{
    // Fields for the Enemy Class
    private String enemyClass = "";
    private String enemyName = "";
    private String[] enemySkills = { "", "", "", "", "", "" };
    private String[] enemySkillDescriptions = { "", "", "", "", "", "" };
    //normalSkillDamage is used as a reference when enemySkillDamage is updated to reflect status effect changes.
    private int[] normalSkillDamage = {0, 0, 0, 0, 0, 0};
    private int[] enemySkillDamage = {0, 0, 0, 0, 0, 0};
    // skillStatusEffect Formatted as follows: {"<Proc Chance> <Effect> <Effect Efficacy> <Effect Length>"}
    private String[] skillStatusEffect = { "", "", "", "", "", "" };
    private String enemyDescription = "";

    private int enemyMaxHP;
    private int enemyHP;
    private int enemyConstitution;
    private int enemyAffinity;
    private int enemySpeed;
    private int enemyXPAward;
    private int enemyMoneyAward;

    private SimpleAudioPlayer SAP;

    /**
     * The main constructor for the Enemy Class.
     * Randomly creates an enemy and assigns enemy attributes based on the enemy.
     *
     * Date created: November 7, 2020
     */
    public Enemy(int area)
    {
        boolean customMusic = false;
        Random rand = new Random();
        int randomNum = rand.nextInt(20) + 1; // Gets a number from 1 to 10.

        if (randomNum == 1) {
            enemyClass = "Warrior";
            enemyName = "Orc";
            enemyDescription = "An Orc is a fierce warrior that has high HP, Constitution, and Armor.";
            enemyHP = 125;
            enemyMaxHP = enemyHP;
            enemyConstitution = 15;
            enemyAffinity = 0;
            enemySpeed = 10;
            enemyXPAward = 70;
            enemyMoneyAward = 150;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Fierce Slash";
            enemySkills[1] = "Decimate";
            enemySkillDescriptions[0] = "Fierce Slash: Deals physical damage equal to (Enemy Constitution * 1.2)";
            enemySkillDescriptions[1] = "Decimate: Deals physical damage equal to (Enemy Constitution * 0.9), with a 20% chance of weakening the target, reducing their Constitution by 5 for 2 turns.";
            skillStatusEffect[0] = "00 None 0 0";
            skillStatusEffect[1] = "20 Weakening 5 2";
            enemySkillDamage[0] = (int) (enemyConstitution * 1.2);
            enemySkillDamage[1] = (int) (enemyConstitution * 0.9);

        }
        if (randomNum == 2) {
            enemyClass = "Mage";
            enemyName = "Vampire";
            enemyDescription = "A Vampire is a frightening mage that can heal itself. They have high Affinity, Resistance, and Speed.";
            enemyHP = 100;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 15;
            enemySpeed = 16;
            enemyXPAward = 60;
            enemyMoneyAward = 100;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Enigma";
            enemySkills[1] = "Succ";
            enemySkillDescriptions[0] = "Enigma: Deal magical damage equal to (Enemy Affinity * 1.2).";
            enemySkillDescriptions[1] = "Succ: Deal magical damage equal to (Enemy Affinity * 0.7), healing the user for 70% of the damage done.";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "70 Drain 0 0";
            enemySkillDamage[0] = (int) (enemyAffinity * 1.2);
            enemySkillDamage[1] = (int) (enemyAffinity * 0.7);

        }
        if (randomNum == 3) {
            enemyClass = "Warrior";
            enemyName = "Goblin";
            enemyDescription = "A Goblin is weak, but also a very fast warrior. They have very high Speed.";
            enemyHP = 70;
            enemyMaxHP = enemyHP;
            enemyConstitution = 12;
            enemyAffinity = 0;
            enemySpeed = 35;
            enemyXPAward = 40;
            enemyMoneyAward = 180;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Simple Slash";
            enemySkills[1] = "Cripple";
            enemySkillDescriptions[0] = "Simple Slash: Deal physical damage equal to Enemy Constitution.";
            enemySkillDescriptions[1] = "Cripple: Deal physical damage equal to (Enemy Constitution * 0.8), with a 30% chance of crippling the target.";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Cripple 0 0";
            enemySkillDamage[0] = (int) (enemyConstitution * 1);
            enemySkillDamage[1] = (int) (enemyConstitution * 0.8);

        }

        if (randomNum == 4) {
            enemyClass = "Warrior";
            enemyName = "Jim Slim Pickens";
            enemyDescription = "Secret Enemy: It's difficult to describe what this man is.";
            enemyHP = 30;
            enemyMaxHP = enemyHP;
            enemyConstitution = 50;
            enemyAffinity = 0;
            enemySpeed = 100;
            enemyXPAward = 90;
            enemyMoneyAward = 150;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Vulnerability";
            enemySkills[1] = "Knife";
            enemySkillDescriptions[0] = "Vulnerability: Shoots the target in their vulnerable area. Deals damage equal to EnemyConstitution * 0.5 with a 50% chance to immobilize the target for 2 turns.";
            enemySkillDescriptions[1] = "Knife: Jim Picken says 'Knife to meet you.' deals damage equal to EnemyConstitution.";
            skillStatusEffect[0] = "50 Immobilized 0 2";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = (int) (enemyConstitution * 0.5);
            enemySkillDamage[1] = enemyConstitution;
            customMusic = true;
        }
        if (randomNum == 5) {
            enemyClass = "Mage";
            enemyName = "Jay";
            enemyDescription = "Jay fights to relieve himself of boredom.";
            enemyHP = 300; // 90;
            enemyMaxHP = enemyHP;
            enemyConstitution = 10;
            enemyAffinity = 30;
            enemySpeed = 35;
            enemyXPAward = 60;
            enemyMoneyAward = 200;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Ally";
            enemySkills[1] = "Uh-oh";
            enemySkillDescriptions[0] = "Ally: Direct an ally to attack the target. Deals a random amount of damage from 10-30HP.";
            enemySkillDescriptions[1] = "Uh-oh: Jay grabs a lighter and begins Pyromania. Deals damage equal to Enemy Affinity * 1.3";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "0 None 0 0";
            Random rando = new Random();
            enemySkillDamage[0] = rando.nextInt(21) + 10;
            enemySkillDamage[1] = (int) (enemyAffinity * 1.3);

        }
        if (randomNum == 6) {
            enemyClass = "Warrior";
            enemyName = "Freddy Kruger";
            enemyDescription = "Secret Enemy: Freddy Krugger is a widely known creepypasta, but you survive his attacks and tell the tale?";
            enemyHP = 100;
            enemyMaxHP = enemyHP;
            enemyConstitution = 25;
            enemyAffinity = 20;
            enemySpeed = 60;
            enemyXPAward = 90;
            enemyMoneyAward = 80;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Backstab";
            enemySkills[1] = "Maniacal Laugh";
            enemySkillDescriptions[0] = "Backstab: Freddy Kruger can sneak up behind the target and deal significant damage. Deals damage equal to Enemy Consitution * 2";
            enemySkillDescriptions[1] = "Maniacal Laugh: Freddy Kruger lets out a maniacal laugh, draining the target's sanity. 30% chance to immobilize the target for 1 turn and deals damage equal to Enemy Constitution * 0.3";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Immobilized 50 1";
            enemySkillDamage[0] = (int) (enemyConstitution * 2);
            enemySkillDamage[1] = (int) (enemyConstitution * 0.3);
            customMusic = true;
        }
        if (randomNum == 7) {
            enemyClass = "Warrior";
            enemyName = "Goblin";
            enemyDescription = "A Goblin is weak, but also a very fast warrior. They have very high Speed.";
            enemyHP = 70;
            enemyMaxHP = enemyHP;
            enemyConstitution = 12;
            enemyAffinity = 0;
            enemySpeed = 35;
            enemyXPAward = 40;
            enemyMoneyAward = 180;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Simple Slash";
            enemySkills[1] = "Cripple";
            enemySkillDescriptions[0] = "Simple Slash: Deal physical damage equal to Enemy Constitution.";
            enemySkillDescriptions[1] = "Cripple: Deal physical damage equal to Enemy Constitution * 0.8, with a 30% chance of crippling the target, reducing their SPD by 5 for 3 turns.";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Cripple 5 3";
            enemySkillDamage[0] = (int) (enemyConstitution * 1);
            enemySkillDamage[1] = (int) (enemyConstitution * 0.8);

        }
        if (randomNum == 8) {
            enemyClass = "Mage";
            enemyName = "Apprentice";
            enemyDescription = "The Apprentice can pull off all sorts of magic. Warriors beware!";
            enemyHP = 200;
            enemyMaxHP = enemyHP;
            enemyConstitution = 10;
            enemyAffinity = 20;
            enemySpeed = 5;
            enemyXPAward = 40;
            enemyMoneyAward = 120;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Gift";
            enemySkills[1] = "Fireball";
            enemySkillDescriptions[0] = "Gift: The apprentice will give you a gift, It contains something in it, but is it good?";
            enemySkillDescriptions[1] = "Dark Blade: Summons a dark sword to slash the target. Deal damage equal to Affinity, with a 20% of bleeding the target, dealing 10HP of damage every turn for 3 turns.";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "20 Bleed 10 3";
            enemySkillDamage[0] = (int) (enemyConstitution * 1);
            enemySkillDamage[1] = (int) (enemyAffinity * 1.4);

        }
        if (randomNum == 9) {
            enemyClass = "Mage";
            enemyName = "Bill Nye";
            enemyDescription = "Secret Enemy: Bill Nye is a former scientist. He will use the power of science against you!";
            enemyHP = 150;
            enemyMaxHP = enemyHP;
            enemyConstitution = 10;
            enemyAffinity = 30;
            enemySpeed = 10;
            enemyXPAward = 70;
            enemyMoneyAward = 100;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Vile Vial";
            enemySkills[1] = "Vaccuum";
            enemySkillDescriptions[0] = "Vile Vial: Bill Nye will throw a vial of acid. 70% Chance to poison the target and deal 20HP damage every turn for 3 turns. Deals damage equal to Enemy Affinity * 0.7.";
            enemySkillDescriptions[1] = "Vaccuum: Bill Nye gained the Vampire Ability 'Succ' with Science! Too bad it doesn't heal. Deals damage equal to Enemy Affinity * 1.5.";
            skillStatusEffect[0] = "70 Poison 5 3";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = (int) (enemyConstitution * 0.7);
            enemySkillDamage[1] = enemyAffinity;
            customMusic = true;
        }
        if (randomNum == 10) {
            enemyClass = "Warrior";
            enemyName = "Terminator";
            enemyDescription = "Secret Enemy: Is this a robot?";
            enemyHP = 200;
            enemyMaxHP = enemyHP;
            enemyConstitution = 50;
            enemyAffinity = 0;
            enemySpeed = 5;
            enemyXPAward = 80;
            enemyMoneyAward = 200;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Metal Punch";
            enemySkills[1] = "Intimidate";
            enemySkillDescriptions[0] = "Metal Punch: Socks the target in the face with their metal fist. Deals physical damage equal to enemyConstitution * 0.7";
            enemySkillDescriptions[1] = "Intimidate: The robot stares at the target... menacingly... 50% chance to reduce the target's speed to 0.";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "50 Immobilized 0 0";
            enemySkillDamage[0] = (int) (enemyConstitution * 0.7);
            enemySkillDamage[1] = 0;
        }
        if (randomNum == 11) {
            enemyClass = "Warrior";
            enemyName = "Minotaur";
            enemyDescription = "A frightening Warrior. Deals large amounts of Physical Damage.";
            enemyHP = 70;
            enemyMaxHP = enemyHP;
            enemyConstitution = 30;
            enemyAffinity = 0;
            enemySpeed = 30;
            enemyXPAward = 50;
            enemyMoneyAward = 120;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Slice";
            enemySkills[1] = "Arrow";
            enemySkillDescriptions[0] = "Slice: Slashes the target with a small knife. Deals damage equal to constitution.";
            enemySkillDescriptions[1] = "Arrow: Fires an arrow at the target. Deals damage equal to constitution * 0.7 with a 30% of bleeding the target, dealing 10HP of damage to the target every turn for 3 turns.";
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Bleed 10 3";
            enemySkillDamage[0] = enemyConstitution;
            enemySkillDamage[1] = (int) (enemyConstitution * 0.7);
        }
        if (randomNum == 12) {
            enemyClass = "Warrior";
            enemyName = "Feral Zombie";
            enemyDescription = "Not a resurrected, but a mind controlled human. It's too late to help them now.";
            enemyHP = 100;
            enemyMaxHP = enemyHP;
            enemyConstitution = 20;
            enemyAffinity = 0;
            enemySpeed = 5;
            enemyXPAward = 30;
            enemyMoneyAward = 30;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Bite";
            enemySkills[1] = "Smack";
            enemySkillDescriptions[0] = "Bite: Bites the target out of Hunger. Deals damage equal to constitution * 1.2 with a 20% chance of poisoning the target, reducing their HP by 5 every turn for 3 turns.";
            enemySkillDescriptions[1] = "Smack: Slaps the target with the power of Zed. Deals damage equal to constitution * 2.5";
            skillStatusEffect[0] = "20 Poison 5 3";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = (int) (enemyConstitution * 1.2);
            enemySkillDamage[1] = (int) (enemyConstitution * 2.5);

        }
        if (randomNum == 13) {
            enemyClass = "Warrior";
            enemyName = "Centipede";
            enemyDescription = "A bug with many legs, grown to be half the size of the target on its hindlegs. Ineffective against warriors.";
            enemyHP = 120;
            enemyMaxHP = enemyHP;
            enemyConstitution = 10;
            enemyAffinity = 0;
            enemySpeed = 40;
            enemyXPAward = 50;
            enemyMoneyAward = 50;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Crawl";
            enemySkills[1] = "Poke";
            enemySkillDescriptions[0] = "Crawl: The Centipede crawls closer to the target with all legs, creating a mesmerating sound. 50% chance to stun the target for 2 turns.";
            enemySkillDescriptions[1] = "Poke: The Centipede jabs the target with a leg. Deals damage equal to constitution * 1.5.";
            skillStatusEffect[0] = "50 Stun 0 2";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = 0;
            enemySkillDamage[1] = (int)(enemyConstitution * 1.5);

        }
        if (randomNum == 14) {
            enemyClass = "Warrior";
            enemyName = "Cockroach";
            enemyDescription = "A bug with a very strong backbone. Very durable, but also very weak. Ineffective against warriors.";
            enemyHP = 300;
            enemyMaxHP = enemyHP;
            enemyConstitution = 10;
            enemyAffinity = 0;
            enemySpeed = 40;
            enemyXPAward = 50;
            enemyMoneyAward = 50;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Tickle";
            enemySkills[1] = "Burrow";
            enemySkillDescriptions[0] = "Tickle: The Cockroach crawls on the target, tickling them. 40% chance to Pacify the target for 2 turns.";
            enemySkillDescriptions[1] = "Burrow: The Cockroach attempt to burrow and act as a parasite to the target. Deals damage equal to enemy Consitution and drains HP equal to 100% damage done.";
            skillStatusEffect[0] = "40 Pacify 0 2";
            skillStatusEffect[1] = "100 Drain 0 0";
            enemySkillDamage[0] = 0;
            enemySkillDamage[1] = enemyConstitution;

        }
        if (randomNum == 15) {
            enemyClass = "Warrior";
            enemyName = "Mosquito";
            enemyDescription = "The master of Succ, more reliant than the vampire, but is much weaker. Ineffective against Warriors.";
            enemyHP = 120;
            enemyMaxHP = enemyHP;
            enemyConstitution = 20;
            enemyAffinity = 0;
            enemySpeed = 40;
            enemyXPAward = 50;
            enemyMoneyAward = 50;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Drain Succ";
            enemySkills[1] = "Super Succ";
            enemySkillDescriptions[0] = "Succ Mark S2: The Mosquito drains the target's HP. Deals damage equal to constitution * 1.5, healing the user for 100% of the damage done.";
            enemySkillDescriptions[1] = "Super Succ: The Mosquito uses the power of bio to empower the Succ. Deals damage equal to constitution * 0.5, healing the user for 300% of the damage done.";
            skillStatusEffect[0] = "100 Drain 0 0";
            skillStatusEffect[1] = "300 Drain 0 0";
            enemySkillDamage[0] = (int)(enemyConstitution * 1.5);
            enemySkillDamage[1] = (int)(enemyConstitution * 0.5);

        }
        if (randomNum == 16) {
            enemyClass = "Mage";
            enemyName = "Ogre";
            enemyDescription = "A large and durable enemy. Deals magical damage with questionable methods.";
            enemyHP = 300;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 40;
            enemySpeed = 5;
            enemyXPAward = 70;
            enemyMoneyAward = 180;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Layers";
            enemySkills[1] = "Natural Affinity";
            enemySkillDescriptions[0] = "Layers: The Ogre unleashes a nasty odor at the target. Deals damage to the target equal to affinity, with a 10% chance to poison the target, dealing 2HP of damage every turn for 5 turns.";
            enemySkillDescriptions[1] = "Natural Affinity: The Orge throws an unknown substance at the target. It starts corroding the target's armor. Deals damage equal to affinity * 0.3 with a 30% chance to incur Armor Break on the target, reducing their Armor by 10 for 3 turns.";
            skillStatusEffect[0] = "10 Poison 2 5";
            skillStatusEffect[1] = "30 ArmorBreak 10 3";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 0.3);

        }
        // Start Here
        if (randomNum == 17) {
            enemyClass = "Mage";
            enemyName = "Cardinal";
            enemyDescription = "A sage that can smite those who oppose him.";
            enemyHP = 120;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 30;
            enemySpeed = 5;
            enemyXPAward = 50;
            enemyMoneyAward = 180;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Sonic Boom";
            enemySkills[1] = "Flame Shooter";
            enemySkillDescriptions[0] = "Sonic Boom: The Cardinal smites the target. Deals damage equal to Affinity and has a 20% to incur Submission on the Target.";
            enemySkillDescriptions[1] = "Flame: The Cardinal casts flames at the target, dealing damage equal to Affinity * 1.2";
            skillStatusEffect[0] = "20 Submission 20 2";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 1.2);
        }
        if (randomNum == 18) {
            enemyClass = "Mage";
            enemyName = "Necromancer";
            enemyDescription = "A Necromancer has strong dark magic skills. Defeat them before they summon minions!";
            enemyHP = 100;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 35;
            enemySpeed = 50;
            enemyXPAward = 100;
            enemyMoneyAward = 100;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Resurrection";
            enemySkills[1] = "Miasma X";
            enemySkillDescriptions[0] = "Resurrection: The Necromancer summons a Skeleton to attack the target. Deals damage equal to affinity * 0.6 and has a 10% chance to cause armor break and lower the target's armor by 5 for 3 turns.";
            enemySkillDescriptions[1] = "Miasma X: A very strong dark magic skill. Casts dark spikes that charge towards the target. Deals damage equal to affinity.";
            skillStatusEffect[0] = "10 ArmorBreak 5 3";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = (int) (enemyAffinity * 0.6);
            enemySkillDamage[1] = enemyAffinity;
        }
        if (randomNum == 19) {
            enemyClass = "Mage";
            enemyName = "Darren";
            enemyDescription = "A Snailmancer, capable of casting spells related to snails.";
            enemyHP = 200;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 15;
            enemySpeed = 5;
            enemyXPAward = 50;
            enemyMoneyAward = 100;
            
            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Don't Touch the Snails";
            enemySkills[1] = "Shell Throw";
            enemySkillDescriptions[0] = "Don't touch the Snails: Darren launches a multitude of snails in your direction. Deals damage equal to enemyAffinity and has a 30% chance to cripple the target by 15 SPD for 3 turns.";
            enemySkillDescriptions[1] = "Shell Throw: Darren throws sharp Shells at the target. Deals damage equal to enemyAffinity * 0.8 with a 20% chance to bleed the target, reducing their HP for 5HP every turn for 3 turns.";
            skillStatusEffect[0] = "30 Cripple 15 3";
            skillStatusEffect[1] = "20 Bleed 5 3";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 0.8);
        }
        if (randomNum == 20) {
            enemyClass = "Mage";
            enemyName = "Jarr";
            enemyDescription = "A Mage In Too Much Armor.";
            enemyHP = 300;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 20;
            enemySpeed = 1;
            enemyXPAward = 70;
            enemyMoneyAward = 200;

            balanceDifficulty();
            balanceArea(area);

            enemySkills[0] = "Arrow";
            enemySkills[1] = "Meteor";
            enemySkillDescriptions[0] = "Arrow: Jarr will summon a Arrow hitting the target. Deals damage equal to affinity with a 30% chance to bleed the target, reducing their HP by 5 every turn for 3 turns.";
            enemySkillDescriptions[1] = "Meteor: Jarr summons a meteor to attack the target. Deals damage equal to affinity * 1.5";
            skillStatusEffect[0] = "30 Bleed 5 3";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 1.5);
        }
        normalSkillDamage = enemySkillDamage;

        if(!customMusic)
        {
            randomNum = rand.nextInt(3)+1;
            switch(randomNum)
            {
                case 1:
                    try
                    {
                        SAP = new SimpleAudioPlayer("5.RegularBattle1-Sonny2Music-InternalConflict.wav", true);
                    }
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try
                    {
                        SAP = new SimpleAudioPlayer("6.RegularBattle2-Sonny2Music-Outnumbered.wav", true);
                    }
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try
                    {
                        SAP = new SimpleAudioPlayer("7.RegularBattle3-Sonny2Music-CalltoArms.wav", true);
                    }
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else
        {
            try
            {
                SAP = new SimpleAudioPlayer("11.BattleSecretEnemy-THEWORLDREVOLVING.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
        }
        //balanceDifficulty is called here. 
        //balanceArea is called in the if statements, before setting skill damage. 
        balanceDifficulty();
        // End Here
    }

    /**
     * Secondary Enemy Constructor used to fight a specific enemy.
     * Enemies under this constructor do not change their stats 
     * depending on location.
     * 
     * Date Created: 11/7/2020
     * 
     * @param enemy
     */
    public Enemy(String enemy, int area)
    {
        if(enemy.equals("Anthiera"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("8.BattleAnthiera-PokemonORAS-BattleLorekeeperZinnia.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            if(Driver.heroClass.equals("Warrior"))
            {
                enemyClass = "Beserker";
                enemyName = "Anthiera";
                enemyDescription = "A female Beserker with the intent of defeating us in battle. They appear to be Fendalian, also.";
                enemyHP = 300;
                enemyMaxHP = enemyHP;
                enemyConstitution = 25;
                enemyAffinity = 0;
                enemySpeed = 19;
                enemyXPAward = 60;
                enemyMoneyAward = 100;

                balanceDifficulty();
                balanceArea(area);
                
                enemySkills[0] = "Stun";
                enemySkills[1] = "Sunder";
                enemySkills[2] = "Slash";
                enemySkillDescriptions[0] = "Stun: Bludgeons the target with the hilt of their sword. Deals damage equal to Constitution * 0.8 with a 30% chance to stun the target for 2 turns.";
                enemySkillDescriptions[1] = "Sunder: Jump and perform a devestating downwards slash upon the target. Deals damage equal to Constitution * 1.5, with a 40% chance to Sunder the target for 3 turns.";
                enemySkillDescriptions[2] = "Slash: Attack the target by slashing them with a sword. Deals damage equal to Constitution.";
                //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
                skillStatusEffect[0] = "30 Stun 0 2";
                skillStatusEffect[1] = "40 Sunder 0 3";
                skillStatusEffect[2] = "0 None 0 0";
                enemySkillDamage[0] = (int)(enemyConstitution * 0.8);
                enemySkillDamage[1] = (int)(enemyConstitution * 1.5);
                enemySkillDamage[2] = enemyConstitution;
            }
            else
            {
                enemyClass = "Sage";
                enemyName = "Anthiera";
                enemyDescription = "A female Sage with the intent of defeating us in battle. They appear to be Fendalian, also.";
                enemyHP = 200;
                enemyMaxHP = enemyHP;
                enemyConstitution = 0;
                enemyAffinity = 30;
                enemySpeed = 25;
                enemyXPAward = 60;
                enemyMoneyAward = 100;

                balanceDifficulty();
                balanceArea(area);

                enemySkills[0] = "Soul Grab";
                enemySkills[1] = "Charm";
                enemySkills[2] = "Orb";
                enemySkillDescriptions[0] = "Soul Grab: Attempt to steal the target's soul. Deals damage equal to affinity * 0.8 and 60% chance to reduce the target's HP by 40 for 3 turns.";
                enemySkillDescriptions[1] = "Charm: Cast an illusion spell on the enemy to have Anthiera appear more majestic. Deals damage equal to affinity * 0.7, with a 40% chance to Pacify the target for 3 turns.";
                enemySkillDescriptions[2] = "Orb: Cast a regular magical spell at the target. Deals damage equal to affinity.";
                skillStatusEffect[0] = "60 ReduceHP 40 3";
                skillStatusEffect[1] = "40 Pacify 0 3";
                skillStatusEffect[2] = "0 None 0 0";
                enemySkillDamage[0] = (int)(enemyAffinity * 0.8);
                enemySkillDamage[1] = (int)(enemyAffinity * 0.7);
                enemySkillDamage[2] = enemyAffinity;
            }
        }
        if (enemy.equals("Burny Sandles"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("11.BattleSecretEnemy-THEWORLDREVOLVING.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Mage";
            enemyName = "Burny Sandles";
            enemyDescription = "Secret Enemy: A Highly Charismatic being. Be mindful!";
            enemyHP = 150;
            enemyMaxHP = enemyHP;
            enemyConstitution = 15;
            enemyAffinity = 10;
            enemySpeed = 19;
            enemyXPAward = 80;
            enemyMoneyAward = 500;

            balanceDifficulty();
            balanceArea(area);
            
            enemySkills[0] = "New Way Forward";
            enemySkills[1] = "Social Being";
            enemySkillDescriptions[0] = "New Way Forward: Burny rallies foreign monsters around him to strike the target. Deals damage equal to Enemy Affinity * 5";
            enemySkillDescriptions[1] = "Social Being: Burny charms the Target with a song. 100% chance to cause Submission to the target for 3 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "100 Submission 0 3";
            enemySkillDamage[0] = enemyAffinity * 5;
            enemySkillDamage[1] = 0;
        }
        if(enemy.equals("Goblin"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("2.TheFirstFight-FireEmblemPathofRadiance.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Goblin";
            enemyDescription = "A Goblin is weak, but also a very fast warrior. They have very high Speed.";
            enemyHP = 70;
            enemyMaxHP = enemyHP;
            enemyConstitution = 12;
            enemyAffinity = 0;
            enemySpeed = 35;
            enemyXPAward = 50;
            enemyMoneyAward = 180;

            balanceDifficulty();
            
            enemySkills[0] = "Simple Slash";
            enemySkills[1] = "Cripple";
            enemySkillDescriptions[0] = "Simple Slash: Deal physical damage equal to Enemy Constitution.";
            enemySkillDescriptions[1] = "Cripple: Deal physical damage equal to (Enemy Constitution * 0.8), with a 30% chance of crippling the target, reducing their SPD by 5 for 3 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Cripple 5 3";
            enemySkillDamage[0] = (int)(enemyConstitution * 1);
            enemySkillDamage[1] = (int)(enemyConstitution * 0.8);
        }
        //Boss 1 - Legion Royal Guard
        if(enemy.equals("Legion Royal Guard"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Legion Royal Guard";
            enemyDescription = "A Royal Knight Monster clad in terribly heavy armor. They have an extremely large HP Pool, but cannot attack well.";
            enemyHP = 900;
            enemyMaxHP = enemyHP;
            enemyConstitution = 20;
            enemyAffinity = 0;
            enemySpeed = 1;
            enemyXPAward = 100;
            enemyMoneyAward = 500;

            balanceDifficulty();
            
            enemySkills[0] = "Wieldy";
            enemySkills[1] = "Sunder";
            enemySkillDescriptions[0] = "Wieldy: The Knight swings their lance around in attempt to hit someone. Deals damage equal to Constitution * 2.";
            enemySkillDescriptions[1] = "Sunder: The Knight charges the target and brings their lance down upon them. Deals damage equal to Constitution with a 40% chance to Sunder the target for 3 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "40 Sunder 0 3";
            enemySkillDamage[0] = enemyConstitution * 2;
            enemySkillDamage[1] = enemyConstitution;
        }
        //Boss 2 - Cavalry Squad Leader
        if(enemy.equals("Cavalry Squad Leader"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Cavalry Squad Leader";
            enemyDescription = "A Lieutenant belonging to a Legion's Cavalry Squadron.";
            enemyHP = 800;
            enemyMaxHP = enemyHP;
            enemyConstitution = 70;
            enemyAffinity = 0;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 500;

            balanceDifficulty();
            
            enemySkills[0] = "Buck";
            enemySkills[1] = "Whirlwind Arrow";
            enemySkillDescriptions[0] = "Buck: Charge the target and kick them with strong horse hind legs. Deals damage equal to Constitution.";
            enemySkillDescriptions[1] = "Whirlwind Arrow: 360 No Scope the target from their horse. Deals damage equal to Constitution * 0.7 with a 30% chance to bleed the target, reducing their HP by 5HP every turn for 4 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "30 Bleed 5 4";
            enemySkillDamage[0] = enemyConstitution;
            enemySkillDamage[1] = (int)(enemyConstitution * 0.7);
        }
        //Boss 3 - Reaper
        if(enemy.equals("Reaper"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Reaper";
            enemyDescription = "A Skeleton with a Scythe. Best be careful.";
            enemyHP = 1200;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 0;
            enemySpeed = 10;
            enemyXPAward = 100;
            enemyMoneyAward = 500;

            balanceDifficulty();
            
            enemySkills[0] = "Reap";
            enemySkills[1] = "Haunt";
            enemySkillDescriptions[0] = "Reap: Slice the target with a sharp Scythe. Bleed the target for 50HP every turn for 3 turns.";
            enemySkillDescriptions[1] = "Haunt: Spook the target, rendering them unable to fight. 70% chance to Pacify the target for 1 turn.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "100 Bleed 50 3";
            skillStatusEffect[1] = "70 Pacify 0 1";
            enemySkillDamage[0] = 0;
            enemySkillDamage[1] = 0;
        }
        //Boss 4 - Shadow Ruler
        //Side note: This enemy deals damage only through bleeding.
        if(enemy.equals("Shadow Ruler"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Mage";
            enemyName = "The Shadow Ruler";
            enemyDescription = "Emerging from the Underworld, The Shadow Ruler has come for revenge for his comrade, Reaper.";
            enemyHP = 1500;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 90;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 500;

            balanceDifficulty();
            
            enemySkills[0] = "Rend Soul";
            enemySkills[1] = "Ode of the Fallen";
            enemySkillDescriptions[0] = "Rend Soul: Capture the Target's Soul. Deal damage equal to affinity with a 50% chance to stun the target for 1 turn.";
            enemySkillDescriptions[1] = "Ode of the Fallen: Sing a song that strikes the target's heart. Deal damage equal to affinity * 1.5 with a 70% chance to cause submission to the target for 2 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "50 Stun 0 1";
            skillStatusEffect[1] = "70 Submission 0 2";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 1.5);
        }
        //Boss 5 - 
        if(enemy.equals("Robertz"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Mage";
            enemyName = "Robertz";
            enemyDescription = "The Darkest Priest known to the Legion. He is ready to cast Devestating Judgement upon those who oppose him.";
            enemyHP = 3000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 175;
            enemySpeed = 40;
            enemyXPAward = 100;
            enemyMoneyAward = 800;

            balanceDifficulty();
            
            enemySkills[0] = "Reign of Demise";
            enemySkills[1] = "Taker of Souls";
            enemySkillDescriptions[0] = "Reign of Demise: Robertz, sitting on his throne, demands immediate execution of the target. Deals damage equal to affinity with a 40% chance to cause submission on the target for 2 turns.";
            enemySkillDescriptions[1] = "Taker of Souls: Robertz casts a spell and leeches the target's soul. Deals damage equal to affinity * 0.5 and heals the user for 50% of the damage done.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "40 Submission 0 2";
            skillStatusEffect[1] = "50 Drain 0 0";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 0.5);
        }
        //"Final" Boss
        if(enemy.equals("Leader of the Legion"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("10.BattleLeaderoftheLegion-FireEmblem3Houses-TheApexoftheWorldRain.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Leader of the Legion";
            enemyDescription = "The Leader of the Legion, who wields Thyrfig. Defeat him and liberate the human race from oppression!";
            enemyHP = 8000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 200;
            enemyAffinity = 0;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 2000;

            balanceDifficulty();
            
            enemySkills[0] = "Savage Impale";
            enemySkills[1] = "Decimate";
            enemySkillDescriptions[0] = "Savage Impale: Charge the target, impaling them with Thyrfig. Deals damage equal to Constitution and has a 100% chance to bleed the target for 20HP every turn for 3 turns.";
            enemySkillDescriptions[1] = "Decimate: Brings down Thyrfig upon the target with great might, much like an excecutioner's. Deals damage equal to Constitution with a 40% chance to Sunder the target, reducing their armor to 0 for 3 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "100 Bleed 20 3";
            skillStatusEffect[1] = "40 Sunder 20 3";
            enemySkillDamage[0] = enemyConstitution;
            enemySkillDamage[1] = enemyConstitution;
        }
        if(enemy.equals("Knight of the Council"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Knight of the Council";
            enemyDescription = "A knight clad in futuristic armor. Beware of their attacks!";
            enemyHP = 10000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 300;
            enemyAffinity = 0;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 2000;

            balanceDifficulty();
            
            enemySkills[0] = "Swordcast";
            enemySkills[1] = "Might";
            enemySkillDescriptions[0] = "Swordcast: The Knight sees the liberators of the human race. Commands a magic sword to attack, dealing damage equal to Constitution with a 40% chance to bleed the target for 40HP every turn for 3 turns.";
            enemySkillDescriptions[1] = "Might: Charges the target to deal damage equal to Constitution * 2. Keep your armor in top condition!";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "40 Bleed 40 3";
            skillStatusEffect[1] = "0 None 0 0";
            enemySkillDamage[0] = enemyConstitution;
            enemySkillDamage[1] = enemyConstitution;
        }
        if(enemy.equals("Member of the Council"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Mage";
            enemyName = "Member of the Council";
            enemyDescription = "A heavy mage in heavy cloth clothing. Has a word in the Commandment.";
            enemyHP = 9000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 500;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 4000;

            balanceDifficulty();
            
            enemySkills[0] = "Condemn";
            enemySkills[1] = "Darklight";
            enemySkillDescriptions[0] = "Condemn: The council member condemns their target to shackles. Deals damage equal to Affinity * 0.5 and has an 80% chance of stunning the target for 1 turn.";
            enemySkillDescriptions[1] = "Darklight: Shower the target in cursed light. Deals damage equal to Affinity with a 40% chance of reducing the target's HP by 500HP for 3 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "80 Stun 0 1";
            skillStatusEffect[1] = "40 ReduceHP 500 3";
            enemySkillDamage[0] = (int)(enemyAffinity * 0.5);
            enemySkillDamage[1] = enemyAffinity;
        }
        if(enemy.equals("Council Commander"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Mage";
            enemyName = "Council Commander";
            enemyDescription = "The Commander of the Legion Council. Put a stop to them!";
            enemyHP = 12000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 1000;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 10000;

            balanceDifficulty();
            
            enemySkills[0] = "Allcast";
            enemySkills[1] = "Mind";
            enemySkillDescriptions[0] = "Allcast: The council commander commands all councilmen to cast magic at the target. Deals damage equal to Affinity with a 30% chance of inflicting Silence.";
            enemySkillDescriptions[1] = "Mind: The council commander's words can be heard in the mind. Deals damage equal to Affinity * 0.5 with a 60% chance to inflict Mental Collapse, reducing the target's resistance by 400 for 2 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "80 Stun 0 1";
            skillStatusEffect[1] = "60 MentalCollapse 400 2";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 0.5);
        }
        if(enemy.equals("Dark Guardian"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("10.BattleLeaderoftheLegion-FireEmblem3Houses-TheApexoftheWorldRain.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Warrior";
            enemyName = "Dark Guardian";
            enemyDescription = "A vanguard of darkness. Wields a scarily sharp axe.";
            enemyHP = 15000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 500;
            enemyAffinity = 0;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 50000;

            balanceDifficulty();
        
        enemySkills[0] = "Mangle";
            enemySkills[1] = "Poison Tip";
            enemySkillDescriptions[0] = "Mangle: The Dark Guardian summons sharp objects aimed at the target. Deals damage equal to Constitution with a 100% chance of bleeding the target for 500HP every turn for 1 turn.";
            enemySkillDescriptions[1] = "Poison Tip: The Dark Guardian poisons the tip of their axe and attacks. Deals damage equal to Constitution * 0.5 with a 100% chance of poisoning the target for 1000HP for 1 turn.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "100 Bleed 500 1";
            skillStatusEffect[1] = "100 Poison 1000 1";
            enemySkillDamage[0] = enemyAffinity;
            enemySkillDamage[1] = (int)(enemyAffinity * 0.5);
        }
        if(enemy.equals("Darkness"))
        {
            try
            {
                SAP = new SimpleAudioPlayer("10.BattleLeaderoftheLegion-FireEmblem3Houses-TheApexoftheWorldRain.wav", true);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
            {
                e.printStackTrace();
            }
            enemyClass = "Mage";
            enemyName = "Darkness";
            enemyDescription = "A humanoid shade that commands the endless legions of monsters. Purify it!";
            enemyHP = 20000;
            enemyMaxHP = enemyHP;
            enemyConstitution = 0;
            enemyAffinity = 700;
            enemySpeed = 30;
            enemyXPAward = 100;
            enemyMoneyAward = 100000;

            balanceDifficulty();
            balanceArea(area);
            
            enemySkills[0] = "Void";
            enemySkills[1] = "Nullify";
            enemySkillDescriptions[0] = "Void: The Darkness consumes the target. Deals damage equal to Affinity * 1.5.";
            enemySkillDescriptions[1] = "Nullify: Attempt to erase the target from existence. Deals damage equal to Affinity, with a 50% chance of incurring Mental Collapse, reducing the target's resistance by 500 for 2 turns.";
            //This formatting represents: Proc Chance, Status Effect, Efficacy, Effect Duration
            skillStatusEffect[0] = "0 None 0 0";
            skillStatusEffect[1] = "50 MentalCollapse 500 2";
            enemySkillDamage[0] = (int)(enemyAffinity * 1.5);
            enemySkillDamage[1] = enemyAffinity;
        }
        normalSkillDamage = enemySkillDamage;
    }
    /**
     * Tertiary Enemy constructor used for Friendly Duels.
     * Works with both allies: Zacharias and Anthiera.
     * Allies are not affected by balance difficulty.
     *
     * Date created: November 17, 2020
     * 
     * @param ally
     */
    public Enemy(Ally ally)
    {
        try
        {
            if(ally.getAllyName().equals("Zacharias"))
            {
                SAP = new SimpleAudioPlayer("3.BattleZacharias-PokemonBlackandWhite2-BattleRival.wav", true);
            }
            else
            {
                SAP = new SimpleAudioPlayer("8.BattleAnthiera-PokemonORAS-BattleLorekeeperZinnia.wav", true);
            }
            
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
        enemyClass = ally.getAllyClass();
        enemyName = ally.getAllyName();
        enemyDescription = ally.getAllyDescription();
        enemyHP = ally.getHP();
        enemyMaxHP = enemyHP;
        enemyConstitution = ally.getConstitution();
        enemyAffinity = ally.getAffinity();
        enemySpeed = ally.getSpeed();
        enemyXPAward = 70;
        enemyMoneyAward = 0;

        String[] skillArray = ally.getAttackSkills();
        for(int i = 0; i < skillArray.length; i++)
        {
            if(ally.getAttackSkills()[i] != "")
            {
                enemySkills[i] = ally.getAttackSkills()[i];
                enemySkillDescriptions[i] = ally.getSkillDescription(i);
                skillStatusEffect[i] = ally.getSkillStatusEffect(i);
                enemySkillDamage[i] = ally.getSkillDamage().get(i);
            }
            if(i == 5)
            {
                break;
            }
        }
        normalSkillDamage = enemySkillDamage;
    }
    /**
     * updates the skilldamage for enemies when affected by a status effect.
     * 
     * Date Created: November 23, 2020
     */
    public void updateSkillDamage(String statusEffect, int efficacy)
    {
        if(enemyClass.equals("Mage") || enemyClass.equals("Sage"))
        {
            if(statusEffect.equals("Headache"))
            {
                enemySkillDamage[0] = enemySkillDamage[0] - efficacy;
                if(enemySkillDamage[0] < 0)
                {
                    enemySkillDamage[0] = 0;
                }
                enemySkillDamage[1] = enemySkillDamage[1] - efficacy;
                if(enemySkillDamage[1] < 0)
                {
                    enemySkillDamage[1] = 0;
                }
            }
            else if(statusEffect.equals("Silenced"))
            {
                enemySkillDamage[0] = 0;
                enemySkillDamage[1] = 0;
            }
        }
        else
        {
            if(statusEffect.equals("Weakened"))
            {
                enemySkillDamage[0] = enemySkillDamage[0] - efficacy;
                if(enemySkillDamage[0] < 0)
                {
                    enemySkillDamage[0] = 0;
                }
                enemySkillDamage[1] = enemySkillDamage[1] - efficacy;
                if(enemySkillDamage[1] < 0)
                {
                    enemySkillDamage[1] = 0;
                }
            }
            else if(statusEffect.equals("Pacified"))
            {
                enemySkillDamage[0] = 0;
                enemySkillDamage[1] = 0;
            }
        }
    }

    /**
     * Restores the regular damage done by the enemy (called when the enemy's effect expires.)
     * 
     * Date Created: November 23, 2020
     */
    public void restoreDmg()
    {
        enemySkillDamage = normalSkillDamage;
    }

    //Accessor Methods for the Enemy Class.
    /**
     * returns the enemy's class specialization.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyClass - String
     */
    public String getEnemyClass()
    {
        return enemyClass;
    }
    /**
     * returns the enemy's name.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyName - String
     */
    public String getEnemyName()
    {
        return enemyName;
    }
    /**
     * returns the enemy's description.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyDescription - String
     */
    public String getEnemyDescription()
    {
        return enemyDescription;
    }
    /**
     * returns the enemy's Max HP.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyMaxHP - int
     */
    public int getEnemyMaxHP()
    {
        return enemyMaxHP;
    }
    /**
     * returns the enemy's HP.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyHP - int
     */
    public int getEnemyHP()
    {
        return enemyHP;
    }
    /**
     * returns the enemy's constitution.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyConstitution - int
     */
    public int getEnemyConstitution()
    {
        return enemyConstitution;
    }
    /**
     * returns the enemy's affinity.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyAffinity - int
     */
    public int getEnemyAffinity()
    {
        return enemyAffinity;
    }
    /**
     * returns the enemy's speed.
     *
     * Date created: November 7, 2020
     * 
     * @return enemySpeed - int
     */
    public int getEnemySpeed()
    {
        return enemySpeed;
    }
    /**
     * returns the enemy's XP reward.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyXPAward - int
     */
    public int getEnemyXPAward()
    {
        return enemyXPAward;
    }
    /**
     * returns the enemy's money reward.
     *
     * Date created: November 7, 2020
     * 
     * @return enemyMoneyAward - int
     */
    public int getEnemyMoneyAward()
    {
        return enemyMoneyAward;
    }

    //Mutator Methods for the Enemy Class
    /**
     * Changes the enemy's Max HP by the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param change
     */
    public void addEnemyMaxHP(int change)
    {
        enemyMaxHP += change;
    }
    /**
     * Changes the enemy's HP by the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param change
     */
    public void addEnemyHP(int change)
    {
        enemyHP += change;
    }
    /**
     * Changes the enemy's constitution by the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param change
     */
    public void addEnemyConstitution(int change)
    {
        enemyConstitution += change;
    }
    /**
     * Changes the enemy's Affinity by the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param change
     */
    public void addEnemyAffinity(int change)
    {
        enemyAffinity += change;
    }
    /**
     * Changes the enemy's Speed by the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param change
     */
    public void addEnemySpeed(int change)
    {
        enemySpeed += change;
    }

    /**
     * sets the enemy's Max HP to the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param enemyMaxHP
     */
    public void setEnemyMaxHP(int enemyMaxHP)
    {
        this.enemyMaxHP = enemyMaxHP;
    }
    /**
     * Sets the enemy's HP to the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param enemyHP
     */
    public void setEnemyHP(int enemyHP)
    {
        this.enemyHP = enemyHP;
    }
    /**
     * Sets the enemy's Constitution to the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param enemyConstitution
     */
    public void setEnemyConstitution(int enemyConstitution)
    {
        this.enemyConstitution = enemyConstitution;
    }
    /**
     * Sets the enemy's Affinity to the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param enemyAffinity
     */
    public void setEnemyAffinity(int enemyAffinity)
    {
        this.enemyAffinity = enemyAffinity;
    }
    /**
     * Sets the enemy's Speed to the specified value.
     *
     * Date created: November 7, 2020
     * 
     * @param enemySpeed
     */
    public void setEnemySpeed(int enemySpeed)
    {
        this.enemySpeed = enemySpeed;
    }

    /**
     * returns the enemy's skills
     *
     * Date created: November 7, 2020
     * 
     * @param index - int 
     * @return enemySkills[index] - String
     */
    public String getSkill(int index)
    {
        return enemySkills[index];
    }

    /**
     * returns the description of the specified enemy skill.
     *
     * Date created: November 7, 2020
     * 
     * @param index - int
     * @return enemySkillDescriptions[index] - String
     */
    public String getSkillDescription(int index)
    {
        return enemySkillDescriptions[index];
    }

    /**
     * returns a skill's status effect and its
     * probability of activation.
     *
     * Date created: November 7, 2020
     * 
     * @param index - int
     * @return skillStatusEffect[index] - String
     */
    public String getSkillStatusEffect(int index)
    {
        return skillStatusEffect[index];
    }

    /**
     * returns the skill damage at the specified index.
     *
     * Date created: November 7, 2020
     * 
     * @param index - int
     * @return enemySkillDamage[index] - int
     */
    public int getSkillDamage(int index)
    {
        return enemySkillDamage[index];
    }

    /**
     * returns a detailed description of the enemy and their stats.
     * 
     * Date Created: 11/16/2020
     * 
     * @return message - String
     */
    public String getEnemyInformation()
    {
        String message = "";
        message += "Enemy Name: " + enemyName;
        message += "\n____________________";
        message += "\nEnemy Class: " + enemyClass;
        message += "\nEnemy Description: " + enemyDescription;
        message += "\nEnemy Max HP: " + enemyMaxHP;
        message += "\nEnemy HP: " + enemyHP;
        message += "\nEnemy Constitution: " + enemyConstitution;
        message += "\nEnemy Affinity: " + enemyAffinity;
        message += "\nEnemy Speed: " + enemySpeed + "\n";

        int increment = 0;
        
        do
        {
            //Prints the skills available as well as a description of the skill.
            message += "\nSkill " + (increment + 1) + ": " + enemySkills[increment];
            message += "\nDescription: " + enemySkillDescriptions[increment];
            increment++;
            if(increment == 6)
            {
                //returns the message so index 6 doesn't cause an outofbounds error.
                return message;
            }
        }while(!enemySkills[increment].isEmpty());

        return message;
    }

    /**
     * Return the number of available skills the enemy has. (Int)
     * 
     * Date Created: 09/16/2021
     * 
     * @return num
     */
    public int getAvailableEnemySkills()
    {
        int num = 0;
        for(int i = 0; i < enemySkills.length-1; i++)
        {
            if(!enemySkills[i].equals("")) //if the skill at index i of enemySkills is not blank.
            {
                num++;
            }
        }
        return num;
    }

    /**
     * Stops the enemy's theme.
     * 
     * Date Created: 11/17/2020
     */
    public void stopEnemyTheme()
    {
        try
        {
            SAP.stop();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method balances enemy attributes to match the difficulty.
     * 
     * Date Created: 11/19/2020
     * 
     */
    private void balanceDifficulty()
    {
        //An example of calling a static field from the driver class.
        if(Driver.difficulty.equals("Medium"))
        {
            enemyMaxHP = (int)(enemyMaxHP * 1.5);
            enemyHP = (int)(enemyHP * 1.5);
            enemyConstitution = (int)(enemyConstitution * 1.5);
            enemyAffinity = (int)(enemyAffinity * 1.5);
            enemySpeed = (int)(enemySpeed * 1.5);
        }
        else if(Driver.difficulty.equals("Hard"))
        {
            enemyMaxHP = enemyMaxHP * 2;
            enemyHP = enemyHP * 2;
            enemyConstitution = enemyConstitution * 2;
            enemyAffinity = enemyAffinity * 2;
            enemySpeed = enemySpeed * 2;
        }
        //balanceXP is also called here since balanceDifficulty is called on all constructors (except for the friendly battle constructor).
        balanceXP();
    }

    /**
     * This method adds to the enemy's stat based on area/location.
     * Bosses do not use this method because they have set stats.
     * 
     * Date Created: 11/21/2020
     * 
     * @param area - int
     */
    private void balanceArea(int area)
    {
        enemyMaxHP += (area * 50);
        enemyHP += (area * 50);
        enemyConstitution += (area * 3);
        enemyAffinity += (area * 3);
        enemySpeed += area;
    }

    /**
     * This method changes the amount of XP the enemy provides,
     * depending on the XP Modifier stat.
     * 
     * Date Created: 11/21/2020
     */
    private void balanceXP()
    {
        if(Driver.multiplier.equals("x2"))
        {
            enemyXPAward *= 2;
        }
        else if(Driver.multiplier.equals("x3"))
        {
            enemyXPAward *= 3;
        }
    }
}
