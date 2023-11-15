/**
 * --------------------------------------------------------------------------
 * File name: Map.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 10/12/2020
 * Completion Date: 11/18/2020
 * Updated: 09/01/2021
 * @version 1.02
 * --------------------------------------------------------------------------
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * The Map Class allows the program to randomly pick a scenario and have the
 * Hero navigate through each scenario. The Map Class also keeps track of the
 * Hero's location.
 *
 * Date created: October 12, 2020
 * 
 * @author Michael Ng, ngmw01@etsu.edu
 */
public class Map {
    // Fields for the Map Class
    private Statistics heroStats;
    private Skills heroSkills;
    private StatIncrease increaseStat;
    private Ally ally;

    private ArrayList<String> events = new ArrayList<String>();
    private int area = 1;
    private String zone = "The Eastern Cave - Entrance";

    /**
     * Primary constructor for the Map class.
     * 
     * Statistics heroStats is needed to have access to the hero's attributes.
     * StatIncrease increaseStat is needed to add experience with Experience
     * Encounters. Skills heroSkills is needed to be able to set up a battle in an
     * Enemy Encounter. String heroName is needed to be able to incorporate the
     * Hero's name in various scenarios.
     * fd
     * Date created: October 12, 2020
     * 
     * @param heroStats    - Statistics
     * @param increaseStat - StatIncrease
     * @param heroSkills   - Skills
     * @param heroName     - String
     */
    public Map(Statistics heroStats, StatIncrease increaseStat, Skills heroSkills, Ally ally, String heroClass) {
        events.add("Potion");
        events.add("Poison Potion");
        events.add("Enemy Encounter");
        events.add("Experience Encounter");
        events.add("Loose Money");
        events.add("Random Event");
        events.add("Encounter");

        this.heroStats = heroStats;
        this.heroSkills = heroSkills;
        this.increaseStat = increaseStat;
        this.ally = ally;
    }

    /**
     * Secondary constructor for the Map class.
     * Mostly similar to primary constructor, but accepts an area parameter to specify area location.
     * Mostly used when loading a save file.
     * 
     * Date created: September 1, 2021
     * 
     * @param heroStats    - Statistics
     * @param increaseStat - StatIncrease
     * @param heroSkills   - Skills
     * @param heroName     - String
     */
    public Map(Statistics heroStats, StatIncrease increaseStat, Skills heroSkills, Ally ally, String heroClass, int area) {
        events.add("Potion");
        events.add("Poison Potion");
        events.add("Enemy Encounter");
        events.add("Experience Encounter");
        events.add("Loose Money");
        events.add("Random Event");

        this.heroStats = heroStats;
        this.heroSkills = heroSkills;
        this.increaseStat = increaseStat;
        this.ally = ally;
        this.area = area;
        checkLocation();
    }

    /**
     * This method returns a list of events that are in the events ArrayList.
     * 
     * Date created: October 12, 2020
     * 
     * @return temp - String;
     */
    public String getPossibleEvents() {
        String temp = "\n";
        for (int i = 0; i < events.size(); i++) {
            temp += " - " + events.get(i) + "\n";
        }
        return temp;
    }

    /**
     * Rushes the current area by calling getScenario() 9 times.
     * Allows the user to quickly access the boss of the floor.
     * 
     * Date Created: 11/16/2020
     */
    public void rushLocation() {
        for (int i = 0; i < 9; i++) {
            
            if(area % 10 == 0)
            {
                String[] listChoices = {"OK"};
                JOptionPane.showOptionDialog(null, "You've arrived at a Boss Floor. \nTo proceed, please select New Scenario.", "Rush Location Disabled", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, listChoices, listChoices[0]);
                System.out.println("You've arrived at a Boss Floor. \nTo proceed, please select New Scenario.");
                break;
            }
            else 
            {
                getScenario();
            }
        }
    }

    /**
     * Randomly chooses a Scenario from the events Arraylist and advances them using
     * the advanceScenario() method.
     * 
     * Date created: October 12, 2020
     */
    public void getScenario()
    {
        //If area is not divisible by 10 and is not the post game.
        if (((area % 10) != 0))
        {
            Random rand = new Random();
            //Gets a random event from the events ArrayList. Gets any event from index 0 to arraylist.size().
            advanceScenario(events.get(rand.nextInt(events.size())));
        }
        else //area is divisible by 10.
        {
            advanceScenario("Boss Battle");
        }
    }

    /**
     * advanceScenario plays out a scenario.
     * This is separated from the getScenario() method.
     * 
     * Date created: October 12, 2020
     * 
     * @param scenario - String
     */
    //Scenarios that are passed are based off of String values in the "events" ArrayList.
    private void advanceScenario(String scenario)
    {
        System.out.println("\n***" + scenario + "***");

        if (scenario.equals("Boss Battle"))
        {
            Battle bat;
            Driver.stopAudio();
            if (area == 10) {
                JOptionPane.showMessageDialog(null, "Upon advancing the 10th floor, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " encounter a large knight encased in armor. \nThey appear to be guarding the entrance to the next area.", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);

                //The original options allows the user to choose whether or not to fight the boss.
                //Options that allow the user to decide have been commented out.
                //String[] options = {"YES", "NO!"};
                //int optionChosen = JOptionPane.showOptionDialog(null, "\"Looks like we'll have to fight this guy in order to get through,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                String[] options = {"YES"};
                JOptionPane.showOptionDialog(null, "\"Looks like we'll have to fight this guy in order to get through,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                //if(optionChosen == 0)
                //{
                    JOptionPane.showMessageDialog(null, "\"Alright,\" " + ally.getAllyName() + " says. \n\"Here goes nothing.\"", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "You and " + ally.getAllyName() + " ambush the Knight and begin battle.", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    //Creates a new Enemy and allows the user to battle the enemy.
                    Enemy knight = new Enemy("Legion Royal Guard", getArea());
                    bat = new Battle(heroStats, heroSkills, ally, knight);
                    //If the hero loses.
                    if(!bat.getResult(increaseStat))
                    {
                        area--;
                    }
                //}
                //else
                //{
                //    JOptionPane.showMessageDialog(null, "You decided not to engage the knight.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //    JOptionPane.showMessageDialog(null, "\"Perhaps a little more preparation can help our chances,\" " + ally.getAllyName() + " says.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //    area--;
                //}
                
        }
        else if (area == 20)
        {
                JOptionPane.showMessageDialog(null, "Upon advancing the 20th floor, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " encounter a strong cavalier. \nThey appear to be a cavalier leader, judging by their insignia.", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);

                //The original options allows the user to choose whether or not to fight the boss.
                //Options that allow the user to decide have been commented out.
                //String[] options = {"YES", "NO!"};
                //int optionChosen = JOptionPane.showOptionDialog(null, "\"Fighting this guy will demoralize their troops and improve out chances here,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                String[] options = {"YES"};
                JOptionPane.showOptionDialog(null, "\"Fighting this guy will demoralize their troops and improve out chances here,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                
                //if(optionChosen == 0)
                //{
                    JOptionPane.showMessageDialog(null, "\"Alright,\" " + ally.getAllyName() + " says. \n\"Here we go!\"", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "You and " + ally.getAllyName() + " ambush the Cavalier and begin battle.", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    //Creates a new Enemy and allows the user to battle the enemy.
                    Enemy bowmen = new Enemy("Cavalry Squad Leader", getArea());
                    bat = new Battle(heroStats, heroSkills, ally, bowmen);
                    //If the hero loses.
                    if(!bat.getResult(increaseStat))
                    {
                        area--;
                    }
                    
                //}
                //else
                //{
                //    JOptionPane.showMessageDialog(null, "You decided not to engage the Cavalier.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //    JOptionPane.showMessageDialog(null, "\"Perhaps a little more preparation can help our chances,\" " + ally.getAllyName() + " says.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //    area--;
                //}
            }
            else if (area == 30)
            {
                JOptionPane.showMessageDialog(null, "Upon advancing the 30th floor, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " encounter a menacing skeleton with a scythe, guarding the entrance of a portal.", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);

                //The original options allows the user to choose whether or not to fight the boss.
                //Options that allow the user to decide have been commented out.
                //String[] options = {"YES", "NO!"};
                String[] options = {"YES"};
                //int optionChosen = JOptionPane.showOptionDialog(null, "\"We will have to fight this skeleton in order to pass through,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                JOptionPane.showOptionDialog(null, "\"We will have to fight this skeleton in order to pass through,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                // if(optionChosen == 0)
                // {
                    JOptionPane.showMessageDialog(null, "\"Alright,\" " + ally.getAllyName() + " says. \n\"Let's go!\"", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "You and " + ally.getAllyName() + " ambush the Skeleton and begin battle.", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    //Creates a new Enemy and allows the user to battle the enemy.
                    Enemy reaper = new Enemy("Reaper", getArea());
                    bat = new Battle(heroStats, heroSkills, ally, reaper);
                    //If the hero loses.
                    if(!bat.getResult(increaseStat))
                    {
                        area--;
                    }
                // }
                // else
                // {
                // JOptionPane.showMessageDialog(null, "You decided not to engage the Skeleton.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                // JOptionPane.showMessageDialog(null, "\"Perhaps a little more preparation can help our chances,\" " + ally.getAllyName() + " says.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                // area--;
                // }
            }
            else if (area == 40)
            {
                JOptionPane.showMessageDialog(null, "Upon advancing the 40th floor, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " are suddenly ambushed by several shadows.", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "\"You have done well to make it this far, humans.\" an unknown voice calls. \n\"I want revenge for my fallen comrade.\"", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);

                //String[] options = {"YES", "FLEE!"};
                String[] options = {"YES"};
                //int optionChosen = JOptionPane.showOptionDialog(null, "\"Looks like this guy won't let us through without a fight,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                JOptionPane.showOptionDialog(null, "\"Looks like this guy won't let us through without a fight,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                //if(optionChosen == 0)
                //{
                    JOptionPane.showMessageDialog(null, "\"Alright,\" " + ally.getAllyName() + " says. \n\"Let's go!\"", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "You and " + ally.getAllyName() + " attack the Shadow and begin battle.", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    //Creates a new Enemy and allows the user to battle the enemy.
                    Enemy shadow = new Enemy("Shadow Ruler", getArea());
                    bat = new Battle(heroStats, heroSkills, ally, shadow);
                    //If the hero loses.
                    if(!bat.getResult(increaseStat))
                    {
                        area--;
                    }
                //}
                //else
                //{
                //JOptionPane.showMessageDialog(null, "You decided not to engage the Shadow and flee alongside " + ally.getAllyName() + ".", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(null, "\"Perhaps a little more preparation can help our chances,\" " + ally.getAllyName() + " says.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //area--;
                //}
            }
            else if (area == 50)
            {
                //Note: It is possible to reach the 50th floor without ever discovering Anthiera, but it is highly unlikely.
                JOptionPane.showMessageDialog(null, "Upon advancing the 50th floor, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " are stopped by a magical barrier.", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "\"Welcome to the Leader's Lair.\" an unknown voice calls. \nThey appear directly in front of them not soon after. \n\"You have caused too much of a stir here,\" he says. \"Let it be known that your life ends here under my hands. Any last words?\"", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Anthiera steps up. \n\"I'd recognize that voice from anywhere,\" she says. \n\"Robertz, I will avenge my fallen comrades by taking your life!\"", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "\"Oh, how amusing,\" Robertz says. \n\"The last member of the Royal Guard, who ran away while I was decimating your army. Pity. Your life will end here, whether you like it or not.\"", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Anthiera grits her teeth. \n\"You'll live to regret saying that...'\" she says.", "Pre-Boss Battle", JOptionPane.INFORMATION_MESSAGE);

                //The original options allows the user to choose whether or not to fight the boss.
                //Options that allow the user to decide have been commented out.
                //String[] options = {"YES", "FLEE!"};
                String[] options = {"YES"};
                //int optionChosen = JOptionPane.showOptionDialog(null, "\"I will never submit to the Monster Legion.\"\n" + ally.getAllyName() + " Looks at you with determination. This guy won't let us through without a fight,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                JOptionPane.showOptionDialog(null, ally.getAllyName() + " looks at you with determination. \n\"This guy won't let us through without a fight,\" " + ally.getAllyName() + " says. \n\"Are you ready?\"", "Pre-Boss Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                //if(optionChosen == 0)
                //{
                    JOptionPane.showMessageDialog(null, "\"Of Course,\" " + ally.getAllyName() + " says. \n\"Let's go!\"", "Initiated Boss Battle", JOptionPane.INFORMATION_MESSAGE);
                    //Creates a new Enemy and allows the user to battle the enemy.
                    Enemy robertz = new Enemy("Robertz", getArea());
                    bat = new Battle(heroStats, heroSkills, ally, robertz);
                    //If the hero loses.
                    if(!bat.getResult(increaseStat))
                    {
                        area--;
                    }
                    else //The hero/ally wins
                    {
                        JOptionPane.showMessageDialog(null, "Immediately, as Robertz falls to his knees, Anthiera rushes to grab him by the throat. \n\"You monster! It's time for you to feel the wrath of my comrades!\" \nShe unsheathes a knife as she finishes.", "Post Boss-Battle", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, "Zacharias pulls on " + heroStats.getHeroName() + "'s sleeve. \n\"This won't be pretty,\" he says. \"Let's go outside and leave her be.\"", "Post Boss-Battle", JOptionPane.INFORMATION_MESSAGE);

                        String[] options1 = {"Leader's Lair?"};
                        JOptionPane.showOptionDialog(null, "Zacharias leads " + heroStats.getHeroName() + " outside the room.\n\"Something during that fight still bothers me,\" he says, while walking. \"Robertz said, \'Welcome to the Leader's Lair.\'\"", "Post Boss-Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options1, options1[0]);
                        String[] options2 = {"We can't rule it out..."};
                        JOptionPane.showOptionDialog(null, "Zacharias and " + heroStats.getHeroName() + " sit down.\n\"Are we really that close to him?\" he says. \"The Leader is the strongest and toughest monster to date.\"", "Post Boss-Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);
                        String[] options3 = {"Got it."};
                        JOptionPane.showOptionDialog(null, "He looks down. \"We'll need to prepare more than ever in order to defeat him.\" he says. \"He'll be the last obstacle we'll have to face this journey.\"", "Post Boss-Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options3, options3[0]);
                        String[] options4 = {"Thank you."};
                        JOptionPane.showOptionDialog(null, "Anthiera walk up behind the two. \n\"Thank you for letting me take care of Robertz,\" she says. \"Next up, the Leader. I can show you guys where his room is.\"", "Post Boss-Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);
                    }
                //}
                //else
                //{
                //JOptionPane.showMessageDialog(null, "You decided not to engage the Mage and flee alongside " + ally.getAllyName() + ".", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(null, "\"Perhaps a little more preparation can help our chances,\" " + ally.getAllyName() + " says.", "Flee", JOptionPane.INFORMATION_MESSAGE);
                //area--;
                //}
            }
            else if(area == 60)
            {
                SimpleAudioPlayer SAP;
                try
                {
                    SAP = new SimpleAudioPlayer("13.Hope-FireEmblemThreeHouses-AWorldforHumanity.wav", true);
                
                    JOptionPane.showMessageDialog(null, "The group reach the last room of the Headquarters.\nThey are unscathed, ready to fight.", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);

                    String[] options1 = {"It has been a long journey."};
                    JOptionPane.showOptionDialog(null, "Zacharias sighs and puts his hand on your shoulder.\n \"I never thought we would make it this far,\" he says.\n\"Standing before us is the last door of our adventure.\"\n", "Before the Final Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options1, options1[0]);
                    JOptionPane.showMessageDialog(null, "Anthiera claps and rubs her hands together.\n \"I can't wait to defeat this guy,\" she says.\n\"It's time to put a stop to his oppression!\"\n", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Zacharias chuckles. \"And after all this, we can finally rebuild Fendale in peace.\" \n\"Fendale City may once again live another day.\"", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);
                    //The original options2 allows the user to choose whether or not to fight the boss.
                    //Options that allow the user to decide have been commented out.
                    //String[] options2 = {"For the Glory of Fendale!", "I need more time..."};
                    String[] options2 = {"For the Glory of Fendale!"};
                    //int choice = JOptionPane.showOptionDialog(null, "\"Indeed it has.\" he says. \"And after this, we can finally rebuild peacefully.\" \n \"Fendale City may once again live another day\"", "Before the Final Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);
                    JOptionPane.showOptionDialog(null, "\"It's about time! For the Glory of Fendale!!\" Anthiera chants.", "Before the Final Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);

                    //if(choice == 0)
                    //{
                        String[] options3 = {"-Enter the Room-"};
                        JOptionPane.showOptionDialog(null, "Zacharias smirks and raises his weapon.\n \"Of course,\" he says.\n\"For the Glory of Fendale!!\"\n", "Before the Final Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options3, options3[0]);

                        JOptionPane.showMessageDialog(null, heroStats.getHeroName() + " and " + ally.getAllyName() + " bust into the room.\n", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, "\"Welcome, humans,\" the Leader says, \"Your persistence to travel this cave is honorable.\"\n\"But, it was all for naught. I will put an end to your lives and end humanity once and for all.\"", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, ally.getAllyName() + " grits his teeth. \n\"I'll never let that happen! I'll stop your treacherous rule before you even think about it again!\"\n", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, "\"How amusing that a weak mortal like you can defeat the Leader of the world-renowned Monster Legion.\"\n\"Let us see who will prevail.\"", "Before the Final Battle", JOptionPane.INFORMATION_MESSAGE);

                        String[] options4 = {"BEGIN FINAL BATTLE"};
                        JOptionPane.showOptionDialog(null, "\'The Fate of Humanity rests in this one battle.\" an unknown voice calls, \n\"You must fight and put an end to the war!\"", "Before the Final Battle", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);
                        try
                        {
                            SAP.stop();
                        }
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
                        {
                            e.printStackTrace();
                        }
                        //Creates a new Enemy and allows the user to battle the enemy.
                        Enemy leaderLegion = new Enemy("Leader of the Legion", getArea());
                        bat = new Battle(heroStats, heroSkills, ally, leaderLegion);
                        //If the hero loses.
                        if(!(bat.getResult(increaseStat)))
                        {
                            area--;
                            JOptionPane.showMessageDialog(null, "\"Looks like we were not prepared,\" " + ally.getAllyName() + " says. \n\"Let us do so before fighting him again\".", "Defeated", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            //Plays ending theme.
                            SAP = new SimpleAudioPlayer("14.Ending-TheHeritorsofArcadia(Instrumental).wav", true);
                            JOptionPane.showMessageDialog(null, "The Leader is knocked back, and falls onto the ground upon defeat.\nIt was a difficult fight, but the group emerged victorious.", "Epilogue", JOptionPane.INFORMATION_MESSAGE);

                            String[] option1 = {"We did this together."};
                            JOptionPane.showOptionDialog(null, "Zacharias thrusts his sword into the ground. \"We've won... the war is over.\" \nHe turns back. \"I couldn't have done it without you two.\"", "Epilogue", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option1, option1[0]);

                            JOptionPane.showMessageDialog(null, "\"I never thought we could make it this far,\" Anthiera says, \"what should we do now?\"", "Epilogue", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "\"With the Leader out of the way,\" Zacharias begins, \"I suppose we can start anew and rebuild Fendale.\"", "Epilogue", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "\"There will be a lot of work ahead of us,\" he admits, \"but I want nothing more than to live with friends at Fendale.\"", "Epilogue", JOptionPane.INFORMATION_MESSAGE);

                            
                            JOptionPane.showMessageDialog(null, "\"After that, we still have much to rebuild.\" Anthiera says, \"Once we secure Fendale, let's set our goals outwards.\"", "Epilogue", JOptionPane.INFORMATION_MESSAGE);
                            String[] option2 = {"Of Course!"};
                            JOptionPane.showOptionDialog(null, "\"Let's head out then,\" Zacharias says. \n\"We have a civilization to rebuild.\"", "Epilogue", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option2, option2[0]);

                            //Credits
                            JOptionPane.showMessageDialog(null, "Thank you for playing Dungeon of Dystopia!\n\nCredits:\nCoding: Michael Ng\nWriting: Michael Ng\nDesign: Michael Ng\nPlanning: Michael Ng\nDirector: Michael Ng\n", "Game Credits", JOptionPane.DEFAULT_OPTION);
                            //Music Credits
                            JOptionPane.showMessageDialog(null, 
                            "\nMusic Credits:"
                            + "\n) Fire Emblem Echoes: Shadows of Valentia \n - (Serenity [Intro Sequence], The Heritors of Arcadia (Instrumental) [Ending], Fanfare(Recruitment) [Level Up], \nEchoes [Title Screen], With Pride in Your Heart [Aftermath])"
                            + "\n) Fire Emblem: Path of Radiance - (The First Fight)"
                            + "\n) Fire Emblem: Awakening - (Destiny [Next Step])"
                            + "\n) Fire Emblem: Three Houses \n- (The Apex of the World (Rain) [BattleLeaderoftheLegion], A World for Humanity [Hope], Broken Weapon [Defeat])"
                            + "\n) Pokemon Black and White 2 - (Battle! (Rival) [BattleZacharias])"
                            + "\n) Pokemon Omega Ruby & Alpha Sapphire (ORAS) - (Battle! (Lorekeeper Zinnia) [BattleAnthiera])"
                            + "\n) Pokemon Let's Go! Pikachu/Eevee - (The Final Road)"
                            + "\n) Pokemon Sword and Shield - (Battle! (Gym Leader Rematch) [BattleBoss])"
                            + "\n) Pokemon Mystery Dungeon: Gates to Infinity - (Dungeon Cleared! [Victory])"
                            + "\n) Deltarune - (THE WORLD REVOLVING [BattleSecretEnemy])"
                            + "\n) Sonny 2 - (Internal Conflict [RegularBattle1], Outnumbered [RegularBattle2], Call to Arms [RegularBattle3])", "Music Credits", JOptionPane.DEFAULT_OPTION);

                            JOptionPane.showMessageDialog(null, "You have reached the end of the game! \nYou can continue playing the game from this point if you desire!\n\nThanks again for playing!");
                            SAP.stop();
                            Driver.postGame = true;
                        }
                    ///}
                    //else
                    //{
                    //    JOptionPane.showMessageDialog(null, "\"You're right,\" " + ally.getAllyName() + " says. \"It would be ill-advised to go in unprepared. Let us prepare some more.\"", "Flee", JOptionPane.INFORMATION_MESSAGE);
                    //    area--;
                    //}
                }
                catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
                {
                    e.printStackTrace();
                }
                    
            }
            else if(area == 70)
            {
                Enemy councilKnight = new Enemy("Knight of the Council", getArea());
                bat = new Battle(heroStats, heroSkills, ally, councilKnight);
                //If the hero loses.
                if(!bat.getResult(increaseStat))
                {
                    area--;
                }
            }
            else if(area == 80)
            {
                Enemy councilMember = new Enemy("Member of the Council", getArea());
                bat = new Battle(heroStats, heroSkills, ally, councilMember);
                //If the hero loses.
                if(!bat.getResult(increaseStat))
                {
                    area--;
                }
            }
            else if(area == 90)
            {
                Enemy councilCommander = new Enemy("Council Commander", getArea());
                bat = new Battle(heroStats, heroSkills, ally, councilCommander);
                //If the hero loses.
                if(!bat.getResult(increaseStat))
                {
                    area--;
                }
            }
            else if(area == 100)
            {
                Enemy darkGuardian = new Enemy("Dark Guardian", getArea());
                bat = new Battle(heroStats, heroSkills, ally, darkGuardian);
                //If the hero loses.
                if(!bat.getResult(increaseStat))
                {
                    area--;
                }
            }
            else if(area >= 110)
            {
                Enemy darkness = new Enemy("Darkness", getArea());
                bat = new Battle(heroStats, heroSkills, ally, darkness);
                //If the hero loses.
                if(!bat.getResult(increaseStat))
                {
                    area--;
                }
            }
            Driver.resumeBGAudio();
        }
        if(scenario.equals("Potion"))
        {
            //Drink a good potion. Since the coder (Michael Ng) cannot have user input in this class, the Hero is forced to drink it by impulse.
            JOptionPane.showMessageDialog(null, heroStats.getHeroName() + " found a Potion! They decided to drink it. \nThe Potion was good! Max HP has increased and HP is restored!\n", "Good Potion!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(heroStats.getHeroName() + " found a Potion! They decided to drink it.");
            System.out.println("The Potion was good! Max HP has increased and HP is restored!");
            heroStats.addMaxHP(10);
            heroStats.setHP(heroStats.getMaxHP());
        }
        if(scenario.equals("Poison Potion"))
        {
            //Drink a bad potion. Since the coder (Michael Ng) cannot have user input in this class, the Hero is forced to drink it by impulse.
            JOptionPane.showMessageDialog(null, heroStats.getHeroName() + " found a Potion! They decided to drink it. \nThat Potion was not good! HP decreased by 10!\n", "Bad Potion!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(heroStats.getHeroName() + " found a Potion! They decided to drink it.");
            System.out.println("That Potion was not good! HP decreased by 10!");
            //This carries on to the next battle.
            heroStats.addHP(-10);
        }
        if(scenario.equals("Random Event"))
        {
            //Randomly chooses a scenario out of 5.
            Random rand = new Random();
            int randomNum = rand.nextInt(5)+1;
            //Hero Interaction with Meme Man
            if(randomNum == 1)
            {
                String message = "";
                //This is intentionally misspelled.
                message += "While walking through the dungeon, " + heroStats.getHeroName() + " sees a man in a suit.";
                message += "\nHe notices you, and walks closer.\n";
                message += "\n\"Helo, Strngr.\", he says, \"My naem is Meme Man. Nis wether wi r havng tuday.\"";
                message += "\n" + heroStats.getHeroName() + " feels like they have heard this man before, but they do not recall.";
                message += "\n" + heroStats.getHeroName() + " had a long and thoughtful conversation with Meme Man.";
                message += "\n\nAffinity increased by 1!";

                JOptionPane.showMessageDialog(null, message, "Interaction", JOptionPane.INFORMATION_MESSAGE);
                heroStats.addAffinity(1);
            }
            //All-Star Encounter
            if(randomNum == 2)
            {
                //Lyrics altered.
                String message = "";
                message += "While navigating the dungeon, " + heroStats.getHeroName() + " hears strange music. They decide to investigate.";
                message += "\nUpon further inspection, " + heroStats.getHeroName() + " sees a musician wearing black sunglasses.";
                message += "\n\"Years begin coming and they do not stop coming.\"";
                message += "\n\"You're an all-star, get your show on!\"";
                message += "\n" + heroStats.getHeroName() + " listened to the entirety of their performance. Invigorating!!";
                message += "\n\nConstitution increased by 1! Resistance increased by 2!";

                JOptionPane.showMessageDialog(null, message, "Musical Performance", JOptionPane.INFORMATION_MESSAGE);
                heroStats.addConstitution(1);
                heroStats.addResistance(2);
            }
            //Ultra-Instinct Shaggy Encounter
            if(randomNum == 3)
            {
                String message = "";
                message += "While walking through the dungeon, " + heroStats.getHeroName() + " notices a strange light emanating from a room.";
                message += "\nUpon further inspection, " + heroStats.getHeroName() + " sees a floating person in a green shirt with blue flames circling around their body.";
                message += "\nOn the other side of the room, there is a living skeleton evading all their attacks.";
                message += "\nThe floating persons says, \"not bad. I'm only using up 2% of my power!\"";
                message += "\n" + heroStats.getHeroName() + " decided to leave before either person notices him.";
                message += "\n\nSpeed increased by 2!";

                JOptionPane.showMessageDialog(null, message, "Ultra-Instinct Encounter", JOptionPane.INFORMATION_MESSAGE);
                heroStats.addSpeed(2);
            }
            //Are ya winning son? Encounter
            if(randomNum == 4)
            {
                String message = "";
                message += "While navigating the dungeon, " + heroStats.getHeroName() + " notices a person walking into a room.";
                message += "\n" + heroStats.getHeroName() + " hears the person saying \"Are you winning, son?\" inside the room";
                message += "\n" + heroStats.getHeroName() + " hears a reply from inside. \"Yes, Dad\"!";
                message += "\n" + heroStats.getHeroName() + " leaves with an emboldened sense of courage.";
                message += "\n\nConstitution and Affinity increased by 2!\n";

                JOptionPane.showMessageDialog(null, message, "Dad and Son", JOptionPane.INFORMATION_MESSAGE);
                heroStats.addConstitution(2);
                heroStats.addAffinity(2);
            }
            //Financial Support Encounter
            if(randomNum == 5)
            {
                String message = "";
                message += "While navigating the dungeon, " + heroStats.getHeroName() + " hears a public speaker.";
                message += "\nlistening closely, the speaker appears to be speaking on the other side of a wall.";
                message += "\n" + heroStats.getHeroName() + " listens. They can hear \"I am asking once again for Financial Support.\"\n";
                message += "\n It appears they want $200 to fund their 'campaign'.\n\n";
                
                if(heroStats.getMoney() >= 200)
                {
                    message += heroStats.getHeroName() + " notices a coin slot. They slip in some money and walk away.";
                    message += "\nmaxHP increased by 150 at the cost of $200!";

                    JOptionPane.showMessageDialog(null, message, "Financial Support", JOptionPane.INFORMATION_MESSAGE);
                    heroStats.addMaxHP(150);
                    heroStats.addMoney(-200);
                }
                else
                {
                    message += heroStats.getHeroName() + " could not afford to give $200 of financial support.";
                    message += "\nAs " + heroStats.getHeroName() + " turns away, the wall suddenly breaks down, revealing a man wearing simple clothes, with socks, sandals, mittens, and white hair.";
                    message += "\n\"I... need my financial support...\" he says, drooling a puddle on the floor.";
                    message += "\nThe man lunges at " + heroStats.getHeroName() + ", who dodges the attack. It's safe to assume that this man wants to do harm.";

                    String[] battleOption = {"BEGIN BATTLE"};
                    JOptionPane.showOptionDialog(null, message, "No Financial Support", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, battleOption, battleOption[0]);

                    Driver.stopAudio();
                    Enemy sandles = new Enemy("Burny Sandles", getArea());
                    Battle bat = new Battle(heroStats, heroSkills, ally, sandles);
                    bat.getResult(increaseStat);
                }
            }
        }
        if(scenario.equals("Experience Encounter"))
        {
            //randomly chooses a scenario of of the 3.
            Random rand = new Random();
            int randomNum = rand.nextInt(3)+1;
            //Obstruction
            if(randomNum == 1)
            {
                JOptionPane.showMessageDialog(null, "While navigating the dungeon, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " walks up to an obstruction that blocks the way.\n"
                                                    + heroStats.getHeroName() + " looks around, but the obstruction appears to be the only way forward.\n" 
                                                    + heroStats.getHeroName() + " & " + ally.getAllyName() + " spend time clearing the obstruction, gaining XP as they continue.\n" 
                                                    + heroStats.getHeroName() + " & " + ally.getAllyName() + " gained 30XP!\n", "Cleared Obstruction", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("While navigating the dungeon, " + heroStats.getHeroName() + " walks up to an obstruction that blocks the way.");
                System.out.println(heroStats.getHeroName() + " looks around, but the obstruction appears to be the only way forward.");
                System.out.println(heroStats.getHeroName() + " spends time clearing the obstruction, gaining XP as they continue.");
                System.out.println(heroStats.getHeroName() + " gained 30XP!");
                increaseStat.addHeroXP(30);
            }
            //Adventurer
            if(randomNum == 2)
            {
                JOptionPane.showMessageDialog(null, "While navigating the dungeon, " + heroStats.getHeroName() + " notices another adventurer and greet them.\n"
                                                    + heroStats.getHeroName() + " and the adventurer practice their skills with each other, to prepare for future battles.\n" 
                                                    + heroStats.getHeroName() + " & " + ally.getAllyName() + " wish the adventurer safe travels as they conclude their training session.\n"
                                                    + heroStats.getHeroName() + " gained 70XP!\n", "Practice with an Adventurer", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("While navigating the dungeon, " + heroStats.getHeroName() + " notices another adventurer and greet them.");
                System.out.println(heroStats.getHeroName() + " and the adventurer practice their skills with each other, to prepare for future battles.");
                System.out.println(heroStats.getHeroName() + " wishes the adventurer safe travels as they conclude their training session.");
                System.out.println(heroStats.getHeroName() + " gained 70XP!");
                increaseStat.addHeroXP(70);
            }
            //Armory
            if(randomNum == 3)
            {
                JOptionPane.showMessageDialog(null, "While navigating the dungeon, " + heroStats.getHeroName() + " finds an armory.\n"
                                                    + heroStats.getHeroName() + " & " + ally.getAllyName() + " make use of the weapons and books laying around the armory to practice on training dummies.\n"
                                                    + heroStats.getHeroName() + " & " + ally.getAllyName() + " spend time practicing new techniques in the armory and leaves with more experience.\n"
                                                    + heroStats.getHeroName() + " & " + ally.getAllyName() + " gained 100XP!\n", "Armory Practice", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("While navigating the dungeon, " + heroStats.getHeroName() + " finds an armory.\n");
                System.out.println(heroStats.getHeroName() + " makes use of the weapons and books laying around the armory to practice on training dummies.\n");
                System.out.println(heroStats.getHeroName() + " spends time practicing new techniques in the armory and leaves with more experience.\n");
                System.out.println(heroStats.getHeroName() + " gained 100XP!\n");
                increaseStat.addHeroXP(50);
            }
        }
        if(scenario.equals("Loose Money"))
        {
            JOptionPane.showMessageDialog(null, "Whilst adventuring, " + heroStats.getHeroName() + " and " + ally.getAllyName() + " find loose change on the floor. \n\nYou got $150!", "Loose Change", JOptionPane.INFORMATION_MESSAGE);
            heroStats.addMoney(150);
        }
        if(scenario.equals("Enemy Encounter"))
        {
            //stops the background audio in the driver from playing music.
            Driver.stopAudio();
            JOptionPane.showMessageDialog(null, "While " + heroStats.getHeroName() + " is navigating, he encounters an enemy. \nThe enemy lunges, and a Battle commences.", "Battle Commencement", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("While " + heroStats.getHeroName() + " is navigating, he encounters an enemy.");
            System.out.println("The enemy lunges, and a Battle commences.");
            Battle bat = new Battle(heroStats, heroSkills, ally, getArea());
            if(!bat.getResult(increaseStat))
            {
                area--;
            }
            //resumes background music.
            Driver.resumeBGAudio();
        }
        if(scenario.equals("Encounter"))
        {
            String[] options1 = {"Are you alright?"};
            JOptionPane.showOptionDialog(null, "You and Zacharias find a good place to rest.\nWhile you are relaxing, you notice that Zacharias appears worried. \nIt seems like him to do so.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options1, options1[0]);

            String[] options2 = {"I'm going through that as well."};
            JOptionPane.showOptionDialog(null, "\"Yeah,\" Zacharias repies. \"I'm still trying to fight the shock when the Monster Legion attacked Fendale City that day.\"\n\"I can't get over how quickly our last safe haven fell.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);

            String[] options3 = {"..."};
            JOptionPane.showOptionDialog(null, "\"I wonder if we are the only two survivors.\" He remarks.\n\"I didn't see anyone else flee the city other than us.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options3, options3[0]);

            String[] options4 = {"Uhh... Zacharias?"};
            JOptionPane.showOptionDialog(null, "Out of the corner of your eye, you see someone approch you with their weapon drawn. \nYou turn your head, and your vision has not betrayed you.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);

            String[] options5 = {"..."};
            JOptionPane.showOptionDialog(null, "The person suddenly begins sprinting at you, ready to swing. \nZacharias quickly stands up and parries their attack that was about to hit you.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "\"Who do you think you are, trying to attack my friend?\" Zacharias says, as the person recovers from the parry.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "The person keeps their sword ready, in a stance ready to swing. \nZacharias takes a defensive stance in front of you.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "\"How funny,\" the person remarks, \"I never knew Monsters had friendships.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "Zacharias does not seem pleased. \n\"Monsters?\" he says, \"What are you talking about? We are in no way related to them.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "\"That's what Robertz, the Underground Dark Priest, said \nbefore he began secretly backstabbing every single member of the Royal Guard,\" says the person.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "Zacharias seems to realize that this person may not be after us. \nHe begins adopting a less defensive stance to show that he is willing to negotiate.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "\"Let's put down our weapons,\" Zacharias suggests, still in a defensive stance.\n\"We are not your enemies. We could be after the same goal.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showOptionDialog(null, "\"I can't do that,\" the person replies. \"you'll have to beat me in battle, first, so I know you're human.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);

            String[] options6 = {"Are we going to fight this person?"};
            JOptionPane.showOptionDialog(null, "Zacharias sighs, and offers me a hand.\n", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options6, options6[0]);

            String[] options7 = {"Alright"};
            JOptionPane.showOptionDialog(null, "\"It looks like we have no other choice. Let's prove we're not monsters.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options7, options7[0]);

            String[] options8 = {"BEGIN BATTLE"};
            JOptionPane.showOptionDialog(null, "The other person looks prepared.\n\"It will be I, Anthiera, who will prevail in this battle!\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options8, options8[0]);

            Driver.stopAudio();
            Enemy anthiera = new Enemy("Anthiera", getArea());
            Battle bat = new Battle(heroStats, heroSkills, ally, anthiera);

            //if the hero + zacharias wins.
            if(bat.getResult(increaseStat))
            {
                //Messages proceeding a victory
                JOptionPane.showOptionDialog(null, "Anthiera backs away slowly and kneels down, relying on her weapon to keep her from laying on the floor.\n\"I...I concede\" she says \"You win.\".", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "Zacharias sheathes his blade and walks closer to Anthiera.\n\"To make this clear, we are not monsters.\" he says, \"Do you read me?\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "Anthiera nods weakly. \n\"I'm sorry... I never thought anyone lived through the conflict. It was rude of me to assume that.\"\nShe looks up to see Zacharias lending her a hand.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "\"Neither did we,\" Zacharias says. \"Join us in our expedition of this cave.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            }
            else
            {
                //Messages proceeding a defeat.
                JOptionPane.showOptionDialog(null, heroStats.getHeroName() + " and Zacharias back away from battle and withdraw their weapons.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "\"Nice job. You did well,\" Anthiera said. \"I'm sure you would have beaten me, had you not held back.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "\"I'm not cut out for fighting a survivor,\" Zacaharias says, \"Let alone create a rivalry in the situation we're in.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "\"I appreciate the sentiment,\" Anthiera said. \"Count me in your group.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                JOptionPane.showOptionDialog(null, "Zacharias shrugs. \"Why not? Welcome to our group.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            }
            //Anthiera joins the team regardless of whether or not the Hero and Zacharias won the battle.
            String[] options9 = {"We don't?"};
            JOptionPane.showOptionDialog(null, "\"Thank you.\" Anthiera says. \n\"By the sound of it, you two don't seem to know what's in this cave.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options9, options9[0]);
            JOptionPane.showOptionDialog(null, "\"This cave is home to the monster legion,\" Anthiera says. \"You two are lucky to have lived thus far.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            String[] options10 = {"Neither would I"};
            JOptionPane.showOptionDialog(null, "Zacharias looks down, suprised. \"I would never have suspected as much.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options10, options10[0]);

            String[] options11 = {"It's worth a try"};
            JOptionPane.showOptionDialog(null, "\"I came here to seek revenge for my fallen comrades,\" Anthiera says. \n\"But our survival is paramount, compared to revenge.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options11, options11[0]);
            JOptionPane.showOptionDialog(null, "\"I agree with " + heroStats.getHeroName() + ",\" Zacharias says. \n\"If we can't defeat the monsters, we can't rebuild safely.\"", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
                
            String[] options12 = {"Let's Go!"};
            JOptionPane.showOptionDialog(null, "\"Awesome. Let us proceed, shall we?\" Anthiera says.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options12, options12[0]);
            JOptionPane.showOptionDialog(null, heroStats.getHeroName() + " and Zacharias nod. They all begin to traverse the dungeon together.", "Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options5, options5[0]);
            JOptionPane.showMessageDialog(null, "Congratulations! Anthiera is now a usable ally.", "New Ally!", JOptionPane.INFORMATION_MESSAGE);
            Driver.initializeAnthiera();
            Driver.resumeBGAudio();
            //removes the event from the arraylist, now that Anthiera is a part of the group.
            events.remove(6);
        }
        System.out.println();
        //Increments area.
        area++;
        checkLocation();
    }
    /**
     * updates the location by checking the area.
     * 
     * Date created: November 15, 2020
     */
    private void checkLocation()
    {
        if(area <= 10)
        {
            zone = "The Eastern Cave - Entrance";
        }
        else if(area > 10 && area <= 20)
        {
            zone = "The Eastern Cave - Depths";
        }
        else if(area > 20 && area <= 30)
        {
            zone = "The Land of the Fallen";
        }
        else if(area > 30 && area <= 40)
        {
            zone = "Mack's Horror Mansion";
        }
        else if(area > 40 && area <= 50)
        {
            zone = "The Factory of the Forgotten";
        }
        else if(area > 50 && area <= 60)
        {
            zone = "Legion's Headquarters";
        }
        else //Zone is greater than 60
        {
            zone = "Post-Game - Saviors of Humanity";
        }
    }
    /**
     * returns the value of area.
     * This will be used in the driver class to decide menu background music.
     * 
     * Date created: October 12, 2020
     * 
     * @return area - int
     */
    public int getArea()
    {
        return area;
    }
    /**
     * returns the location and area of the hero as a toString() method.
     * 
     * Date created: October 12, 2020
     * 
     * @return message - String
     */
    public String getLocation()
    {
        String message = "";
        message += "Zone: " + zone;
        message += "\nArea: " + area;

        return message;
    }

    /**
     * changes the reference of Ally if the user uses the Switch Allies option.
     * 
     * Date Created: 11/18/2020
     * 
     * @param ally
     */
    public void setAlly(Ally ally)
    {
        this.ally = ally;
    }
}
