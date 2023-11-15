/**
 * --------------------------------------------------------------------------
 * File name: Driver.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 10/8/2020
 * Completion Date: 11/16/2020
 * Updated: 09/01/2021 - 09/06/2021, 11/15/2023.
 * @version 1.7
 * --------------------------------------------------------------------------
 */
//imports needed in order to play background music.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * The Driver Class creates objects and accepts user input in order to advance
 * the game.
 *
 * Date created: October 8, 2020
 * 
 * @author Michael Ng, ngmw01@etsu.edu
 */
public class Driver
{
    static String heroClass;
    private static String heroName;
    //These fields are used primarily in the enemy class.
    static String difficulty = "Easy";
    static String multiplier = "x1";
    static boolean postGame = false;

    private static Ally zacharias;
    private static Ally anthiera;
    private static Ally currentAlly;
    private static Statistics stats;
    private static Skills heroSkills;
    private static StatIncrease increaseStat;
    private static Map area;
    private static Battle bat;
    private static SimpleAudioPlayer SAP;
    /**
     * Main method used to run the program. Accept button input from the user and
     * adjusts attributes/classes based on input.
     * 
     * Date created: October 8, 2020
     * 
     * @param args
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        SAP = new SimpleAudioPlayer("18.TitleScreen-Echoes-FireEmblemEchoes.wav", true);
        int menuChoice;

        do
        {
            String[] menuOptions = {"New Game", "Load Game", "Settings", "Credits", "Music"};
            menuChoice = JOptionPane.showOptionDialog(null, "Welcome to The Dungeon of Dystopia!", "Welcome!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, menuOptions, menuOptions[0]);
            
            //Load game
            if(menuChoice == 1)
            {
                //The 2 filepaths to the save files.
                File path = new File("File1.txt");
                File path2 = new File("File2.txt");
                Scanner inputFile = null;
                Scanner inputFile2 = null;
                int pick = 0;

                String filesInformation = "";
                int fileNum = 1;
				try //validate both files.
				{
					inputFile = new Scanner(path);
					fileNum++;
					inputFile2 = new Scanner(path2);
				}
				catch (FileNotFoundException e1) 
				{
					JOptionPane.showMessageDialog (null, "Save file " + fileNum + " was not found.\nPlease select a text file in the following menu.", "Save File not Found.", JOptionPane.ERROR_MESSAGE);
					JFileChooser fileChooser = new JFileChooser("");
					pick = fileChooser.showOpenDialog (null);
					if(pick == JFileChooser.APPROVE_OPTION) 
					{
						File file = fileChooser.getSelectedFile ( );
						try
						{
							Scanner validate = new Scanner(file);
							validate.close();
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				}
				pick = 0;
				
				//Get the summaries of the data in both files. (This one for file 1)
				if(inputFile.hasNext()) 
				{
					String str = inputFile.nextLine();
					String[] values = str.split ("\\|");
					
					//Name
					filesInformation +=  "\n-------------------------\n(File 1) " + values[0];
					filesInformation += "\n--------------------\n";
					//Stages Completed
					filesInformation += "Stages Completed: " + values[1] + "\n";
					//Level
					filesInformation += "Level: " + values[2] + "\n";
					//Experience
					filesInformation += "Experience: " + values[3] + "XP/100XP\n\n";
                    //HP/MaxHP
                    filesInformation += "HP: " + values[5] + "HP/" + values[4] + "HP\n";
                    //Constitution
                    filesInformation += "Constitution: " + values[6] + "\n";
                    //Affinity
                    filesInformation += "Affinity: " + values[7] + "\n";
                    //Armor
                    filesInformation += "Armor: " + values[8] + "\n";
                    //Resistance
                    filesInformation += "Resistance: " + values[9] + "\n";
                    //Speed
                    filesInformation += "Speed: " + values[10] + "\n";
                    //Money
                    filesInformation += "Money: " + values[11] + "\n";
                    //Class
                    filesInformation += "Class: " + values[12] + "\n-------------------------\n";
				}
				else 
				{
					filesInformation += "\n-------------------------\nFile 1 is Empty.\n-------------------------\n";
				}
				//Get the summary of the data in file 2.
				if(inputFile2.hasNext()) 
				{				
					String str = inputFile2.nextLine();
					String[] values = str.split ("\\|");
					
					//Name
					filesInformation +=  "\n\n(File 2) " + values[0];
					filesInformation += "\n--------------------\n";
					//Stages Completed
					filesInformation += "Stages Completed: " + values[1] + "\n";
					//Level
					filesInformation += "Level: " + values[2] + "\n";
					//Experience
					filesInformation += "Experience: " + values[3] + "XP/100XP\n\n";
                    //HP/MaxHP
                    filesInformation += "HP: " + values[5] + "HP/" + values[4] + "HP\n";
                    //Constitution
                    filesInformation += "Constitution: " + values[6] + "\n";
                    //Affinity
                    filesInformation += "Affinity: " + values[7] + "\n";
                    //Armor
                    filesInformation += "Armor: " + values[8] + "\n";
                    //Resistance
                    filesInformation += "Resistance: " + values[9] + "\n";
                    //Speed
                    filesInformation += "Speed: " + values[10] + "\n";
                    //Money
                    filesInformation += "Money: " + values[11] + "\n";
                    //Class
                    filesInformation += "Class: " + values[12] + "\n";
				}
				else 
				{
					filesInformation += "\n-------------------------\nFile 2 is Empty.\n-------------------------\n";
				}
                
                
                //Reset the scanners back to line 1 of the savefile before loading.
                try
                {
                    inputFile = new Scanner(path);
                    inputFile2 = new Scanner(path2);
                }
                catch (FileNotFoundException e1) //Unlikely to happen because it has already been validated. Still, it's put here just in case.
                {
                    JOptionPane.showMessageDialog (null, "A save file was not found.\nPlease select a text file in the following menu.", "Save File not Found.", JOptionPane.ERROR_MESSAGE);
                    JFileChooser fileChooser = new JFileChooser("src\\SaveFiles");
                    pick = fileChooser.showOpenDialog (null);
                    if(pick == JFileChooser.APPROVE_OPTION) 
                    {
                        File file = fileChooser.getSelectedFile ( );
                        try
                        {
                            Scanner validate = new Scanner(file);
                            validate.close();
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        
                    }
                }
                //The user chooses which file to load from.
                String[] options = {"File 1", "File 2"};
                
                //Asks the user which file they would like to load from. Displays the hero summaries of both files. 
                pick = JOptionPane.showOptionDialog (null, "Which file would you like to load from?" + filesInformation, "Save Game", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if(pick == 0) //File 1
                {	
                    //Loads data from File 1.
                    if(inputFile.hasNext ( )) 
                    {
                        String str = inputFile.nextLine();
                        System.out.println(str);
                        loadFile(str, 0);

                        if(inputFile.hasNext())
                        {
                            str = inputFile.nextLine();
                            System.out.println(str);
                            loadFile(str, 1);
                            if(inputFile.hasNext())
                            {
                                str = inputFile.nextLine();
                                System.out.println(str);
                                loadFile(str, 2);
                            }
                            openCommandConsole();
                        }
                        System.out.println("Error: Could not load allies.");
                        JOptionPane.showMessageDialog(null, "Could not find stats for either Zacharias or Anthiera.\nAll stats will be rerolled.", "Reroll", JOptionPane.INFORMATION_MESSAGE);
                        int levelThreshold = increaseStat.getHeroLevel();
                        for(int i = 0; i < levelThreshold; i++)
                        {
                            increaseStat.levelUp();
                        }
                        openCommandConsole();
                    }
                    else //If there is no data. Asks the user to start a new game.
                    {
                        JOptionPane.showMessageDialog (null, "Sorry, this save file has no data associated with it.\nPlease begin a new game.", "No Save Data", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(pick == 1) //File 2
                {
                    //Loads data from File 2.
                    if(inputFile2.hasNext ( )) 
                    {
                        String str = inputFile2.nextLine();
                        //System.out.println(str);

                        //Load the file in a series of if statements. 
                        //The str contains the attributes of the person on that line.
                        loadFile(str, 0);

                        if(inputFile2.hasNext())
                        {
                            str = inputFile2.nextLine();
                            //System.out.println(str);
                            loadFile(str, 1);
                            if(inputFile2.hasNext())
                            {
                                str = inputFile2.nextLine();
                                //System.out.println(str);
                                loadFile(str, 2);
                            }
                            //Opens the Command Console after completing loading.
                            openCommandConsole();
                        }
                        System.out.println("Error: Could not load allies.");
                        JOptionPane.showMessageDialog(null, "Could not find stats for either ally.\nAll stats will be rerolled.", "Reroll", JOptionPane.INFORMATION_MESSAGE);
                        
                        //Level up the hero and allies to the desired amount.
                        int levelThreshold = increaseStat.getHeroLevel();
                        for(int i = 0; i < levelThreshold; i++)
                        {
                            increaseStat.levelUp();
                        }

                        openCommandConsole();
                    }
                    else //If there is no data in the save file that the player chose.
                    {
                        JOptionPane.showMessageDialog (null, "Sorry, this save file has no data associated with it.\nPlease begin a new game.", "No Save Data", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            //Settings
            else if(menuChoice == 2)
            {
                int menuChoice2;
                do
                {
                    String[] settingOptions = {"Change Difficulty", "Change XP Multiplier", "Leave Settings"};
                    menuChoice2 = JOptionPane.showOptionDialog(null, "Difficulty:  " + difficulty + "\nCurrent XP Modifier: " + multiplier + "\n\nYou will not be able to change these in-game.", "Settings", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, settingOptions, settingOptions[0]);
                    if(menuChoice2 == 0)
                    {
                        if(difficulty.equals("Easy"))
                        {
                            difficulty = "Medium";
                            JOptionPane.showMessageDialog(null, "Your difficulty has been set to Medium.\nEnemies now have a 1.5x bonus to their attributes.", "Difficulty is Medium", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(difficulty.equals("Medium"))
                        {
                            difficulty = "Hard";
                            JOptionPane.showMessageDialog(null, "Your difficulty has been set to Hard.\nEnemies now have a 2x bonus to their attributes.", "Difficulty is Hard", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(difficulty.equals("Hard"))
                        {
                            difficulty = "Easy";
                            JOptionPane.showMessageDialog(null, "Your difficulty has been set to Easy.\nEnemies do not have any bonuses to their attributes.", "Difficulty is Easy", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else if(menuChoice2 == 1)
                    {
                        if(multiplier.equals("x1"))
                        {
                            multiplier = "x2";
                            JOptionPane.showMessageDialog(null, "Your XP Multiplier is now set to x2.\nYou will now get double XP from battles.", "XP Multiplier is 2.0", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(multiplier.equals("x2"))
                        {
                            multiplier = "x3";
                            JOptionPane.showMessageDialog(null, "Your XP Multiplier is now set to x3.\nYou will now get triple XP from battles.", "XP Multiplier is 3.0", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(multiplier.equals("x3"))
                        {
                            multiplier = "x1";
                            JOptionPane.showMessageDialog(null, "Your XP Multiplier is now set to x1.\nYou will not recieve any additional XP from battles.", "XP Multiplier is 1.0", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }while(menuChoice2 != 2 && menuChoice2 != -1);
            }
            else if(menuChoice == 3) //Credits
            {
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
            }
            else if(menuChoice == 4) //Music
            {
                String[] musicChoices = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "Exit"};
                int musicPick = 0;
                do 
                {
                    musicPick = JOptionPane.showOptionDialog(null, "Please Choose a Song to Play. (Please see credits for Song Sources)\n1. Serenity\n2. The First Fight\n3. Battle! (Zacharias)" + 
                    "\n4. Next Steps\n5. Battle Theme 1\n6. Battle Theme 2\n7. Battle Theme 3\n8. Battle! (Anthiera)\n9. Battle! (Boss)\n10. Battle! (Leader of the Legion)" + 
                    "\n11. Battle! (Secret Enemy)\n12. The Final Road\n13. Hope\n14. Ending\n15. Victory\n16. Defeat\n17. Level Up\n" + 
                    "18. Title Screen\n19. Aftermath", "Song Player", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, musicChoices, musicChoices[0]);

                    stopAudio();
                    switch(musicPick)
                    {
                        case 0:
                            SAP = new SimpleAudioPlayer("1.IntroSequence-FireEmblemEchoes-Serenity.wav", true);
                            System.out.println("Playing Serenity\n- Credits to: Fire Emblem Echoes - Shadows of Valentia.");
                            break;

                        case 1:
                            SAP = new SimpleAudioPlayer("2.TheFirstFight-FireEmblemPathofRadiance.wav", true);
                            System.out.println("Playing The First Fight\n- Credits to: Fire Emblem - Path of Radiance.");
                            break;

                        case 2:
                            SAP = new SimpleAudioPlayer("3.BattleZacharias-PokemonBlackandWhite2-BattleRival.wav", true);
                            System.out.println("Playing Battle! (Zacharias)\n- Credits to: Pokemon Black/White 2.");
                        break;

                        case 3:
                            SAP = new SimpleAudioPlayer("4.NextStep-FireEmblemAwakening-Destiny.wav", true);
                            System.out.println("Playing Next Step\n- Credits to: Fire Emblem Awakening.");
                        break;

                        case 4:
                            SAP = new SimpleAudioPlayer("5.RegularBattle1-Sonny2Music-InternalConflict.wav", true);
                            System.out.println("Playing Regular Battle 1\n- Credits to: Sonny 2.");
                        break;

                        case 5:
                            SAP = new SimpleAudioPlayer("6.RegularBattle2-Sonny2Music-Outnumbered.wav", true);
                            System.out.println("Playing Regular Battle 2\n- Credits to: Sonny 2.");
                        break;

                        case 6:
                            SAP = new SimpleAudioPlayer("7.RegularBattle3-Sonny2Music-CalltoArms.wav", true);
                            System.out.println("Playing Regular Battle 3\n- Credits to: Sonny 2.");
                        break;

                        case 7:
                            SAP = new SimpleAudioPlayer("8.BattleAnthiera-PokemonORAS-BattleLorekeeperZinnia.wav", true);
                            System.out.println("Playing Battle! (Anthiera)\n- Credits to: Pokemon Omega Ruby/Alpha Sapphire");
                        break;

                        case 8:
                            SAP = new SimpleAudioPlayer("9.BattleBoss-PokemonSwordShield-BattleGymLeader-Rematch.wav", true);
                            System.out.println("Playing Battle! (Boss)\n- Credits to: Pokemon Sword/Shield");
                        break;

                        case 9:
                            SAP = new SimpleAudioPlayer("10.BattleLeaderoftheLegion-FireEmblem3Houses-TheApexoftheWorldRain.wav", true);
                            System.out.println("Playing Battle! (Leader of the Legion)\n- Credits to: Fire Emblem - Three Houses");
                        break;

                        case 10:
                            SAP = new SimpleAudioPlayer("11.BattleSecretEnemy-THEWORLDREVOLVING.wav", true);
                            System.out.println("Playing Battle! (Secret Enemy)\n- Credits to: Deltarune");
                        break;

                        case 11:
                            SAP = new SimpleAudioPlayer("12.TheFinalRoad-PokemonLetsGoPikachu-Eevee.wav", true);
                            System.out.println("Playing The Final Road\n- Credits to: Pokemon Let's Go! Pikachu/Eevee");
                        break;

                        case 12:
                            SAP = new SimpleAudioPlayer("13.Hope-FireEmblemThreeHouses-AWorldforHumanity.wav", true);
                            System.out.println("Playing Hope\n- Credits to: Fire Emblem - Three Houses");
                        break;

                        case 13:
                            SAP = new SimpleAudioPlayer("14.Ending-TheHeritorsofArcadia(Instrumental).wav", true);
                            System.out.println("Playing Ending\n- Credits to: Fire Emblem Echoes - Shadows of Valentia");
                        break;

                        case 14:
                            SAP = new SimpleAudioPlayer("15.Victory-PokemonMysteryDungeonGatestoInfinity-DungeonCleared.wav", false);
                            System.out.println("Playing Victory\n- Credits to: Pokemon Mystery Dungeon - Gates to Infinity");
                        break;

                        case 15:
                            SAP = new SimpleAudioPlayer("16.Defeat-FireEmblemThreeHouses-BrokenWeapon.wav", false);
                            System.out.println("Playing Defeat\n- Credits to: Fire Emblem Three Houses");
                        break;

                        case 16:
                            SAP = new SimpleAudioPlayer("17.LevelUp-FireEmblemEchoes-Fanfare(Recruitment).wav", false);
                            System.out.println("Playing Level Up\n- Credits to: Fire Emblem Echoes - Shadows of Valentia");
                        break;

                        case 17:
                            SAP = new SimpleAudioPlayer("18.TitleScreen-Echoes-FireEmblemEchoes.wav", true);
                            System.out.println("Playing Title Screen\n- Credits to: Fire Emblem Echoes - Shadows of Valentia");
                        break;

                        case 18:
                            SAP = new SimpleAudioPlayer("19.Aftermath-FireEmblemEchoes-WithPrideinYourHeart.wav", true);
                            System.out.println("Playing Aftermath\n- Credits to: Fire Emblem Echoes - Shadows of Valentia");
                        break;

                        default:

                        break;
                    }
                }while(musicPick != 19 && musicPick != -1);
                stopAudio();
                //Resumes Main Menu Music.
                SAP = new SimpleAudioPlayer("18.TitleScreen-Echoes-FireEmblemEchoes.wav", true);
            }
            //'X'
            else if(menuChoice == -1)
            {
                System.exit(0);
            }
            //While the user does not pick "New Game"
        }while(menuChoice != 0);
        
        stopAudio();
        //Story Dialog, with options as dialog choices (it's not really a choice).
        System.out.println("***Welcome to The Dungeon of Dystopia!***");
        System.out.println("*EVENT LOG*\nAll events that have occured will be posted here.");

        //Plays soundtrack number 1 from the file.
        SAP = new SimpleAudioPlayer("1.IntroSequence-FireEmblemEchoes-Serenity.wav", true);
        String[] response1 = {"Zacharias? What happened?"};
        JOptionPane.showOptionDialog(null, "You are shaken awake by your friend Zacharias. \nUpon your waking, he breathes a sigh of relief. \n\nYou appear to be in a cave of some kind.\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response1, response1[0]);

        String[] response2 = {"Is Fendale City alright?"};
        JOptionPane.showOptionDialog(null, "\"We barely made it out of Fendale city\", he says. \"You passed out trying to fight off the Monster Legion\". \nZacharias finishes wiping one of your wounds with an alcohol wipe and sits beside you.\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response2, response2[0]);

        String[] response3 = {"Oh..."};
        JOptionPane.showOptionDialog(null, "Zacharias shakes his head. He looks tired, as if his fatigue has finally set in. \n\n\"I'm afraid not\", he says. \n\"It's such a shame. Fendale city is the last safe haven known to mankind\" \n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response3, response3[0]);

        String[] response4 = {"Perhaps."};
        JOptionPane.showOptionDialog(null, "\"Oh, Indeed. We have nowhere else to go to.\" \nHe looked up at the rock spikes above him. \n\"Perhaps this cave could prove otherwise.\" \n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response4, response4[0]);

        String[] response5 = {"Thank you."};
        JOptionPane.showOptionDialog(null, "Zacharias offers you a hand. \n\"Come on. Let's see what this cave has to offer.\" \n\"Maybe there is someone who can help us.\"\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response5, response5[0]);

        //boolean variable used for do-while loops within the constructor.
        boolean temp = false;
        //Gets the name of the Hero and prints accordingly based on input.
        do
        {
            heroName = JOptionPane.showInputDialog(null, "\nWhat is the name of your Hero?", "Hero Name", JOptionPane.DEFAULT_OPTION);

            if(heroName.length() <= 15)
            {
                if(heroName.isBlank())
                {
                    heroName = "Javar";
                }
                heroName = heroName.trim();
                String[] options = {"Yes", "No"};
                int confirmation = JOptionPane.showOptionDialog(null, "Is " + heroName + " the name of your Hero?", "Hero Name Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                
                if(confirmation == 0)
                {
                    temp = true;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Sorry! Your name cannot be more than 15 characters long.", "Error - Too Many Characters", JOptionPane.ERROR_MESSAGE);
            }
            

        }while(temp == false);
        //Resets the boolean temp.
        temp = false;
        //Asks the user for the Hero's class specialization and responds to input accordingly.
        do
        {
            String[] options = {"Mage", "Warrior"};
            int choice = JOptionPane.showOptionDialog(null, "What class does your Hero specialize in?", "Class Specialization", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if(choice == 0)
            {
                heroClass = "Mage";
            }
            else
            {
                heroClass = "Warrior";
            }


            String[] confirmationOptions = {"Yes", "No"};
            choice = JOptionPane.showOptionDialog(null, "Are you sure you want to specialize as a " + heroClass + "?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions, confirmationOptions[0]);
            if(choice == 0)
            {
                temp = true;
            }

        }while(temp == false);

        //initializes all defined static objects in the driver.
        initialize();

        String[] response6 = {"I agree."};
        JOptionPane.showOptionDialog(null, "\"" + heroName + ", it's good to have you by my side\" Zacharias says. \n\"We've been friends for as long as I can remember.\"\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response6, response6[0]);

        String[] response7 = {"Look out!"};
        JOptionPane.showOptionDialog(null, "Suddenly, a Goblin jumps from out of nowhere.\nYou can recognize the Goblin as part of the Monster Legion.\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, response7, response7[0]);
        
        String[] response8 = {"BEGIN BATTLE"};
        JOptionPane.showOptionDialog(null, "\"Foolish Humans! You will not take one step further into this cave!\" \nZacharias looks at you and nods, knowing what should be done.\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, response8, response8[0]);
        stopAudio();
        Enemy Goblin = new Enemy("Goblin", 0);
        bat = new Battle(stats, heroSkills, currentAlly, Goblin);
        bat.getResult(increaseStat);

        String[] response9 = {"Thanks, Zacharias."};
        JOptionPane.showOptionDialog(null, "\"That was quite the fight,\" Zacharias says. \n\"Good job, " + heroName + ".\"\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response9, response9[0]);

        String[] response10 = {"Noted."};
        JOptionPane.showOptionDialog(null, "\"Let's be more careful.\" Zacharias says. \"We can't let the legion know we are here.\"\n", "Prologue", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, response10, response10[0]);

        openCommandConsole();
    }
    /**
     * Opens the shopping menu. Allows the user to buy a selection of goods.
     * 
     * Date Created: October 11, 2020
     * Last Updated: November 15, 2023
     */
    public static void openShop()
    {
        //moneyAvailable gets the hero's total amount of money.
        boolean completePurchases = false;

        do
        {
            String[] choices = {"MaxHP", "Constitution", "Affinity", "Armor Mod", "Resistance", "Speed", "Leave"};
            int pick = JOptionPane.showOptionDialog(null, "Welcome to the Shop! What will you buy?\nYou have: $" + stats.getMoney() + "\n\nMaxHP Potion: $100 \n - A MaxHP Potion raises the Hero's Max HP by 20.\n\nConstitution Training: $100 \n - Constitution Training raises the Hero's Constitution by 4.\n\nAffinity Honing: $100\n - Affinity Honing raises the Hero's Affinity by 4.\n\nArmor Mod: $100\n - An Armor Modification raises the Hero's Armor by 4. \n\nResistance Training: $100\n - Resistance Training raises the Hero's Resistance by 4.\n\nSpeed Training: $100\n - Speed Training raises the Hero's Speed by 4.\n\n", "Shop", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[0]);

            ArrayList<String> confirmationOptions = new ArrayList<String>();
                confirmationOptions.add("Yes");
                confirmationOptions.add("Buy for Zacharias");
            //Used for button options in the shop class.
            if(anthiera == null) //if Anthiera is not in the party.
            {
                //Regular Arraylist.
                confirmationOptions.add("No");
            }
            else //Anthiera is a member of the party.
            {
                //include the option to buy for Anthiera.
                confirmationOptions.add("Buy for Anthiera");
                confirmationOptions.add("No");
                //Arraylist with the Buy for Anthiera Option.
            }

            String[] YesNoOption = {"Yes", "No"};
            //Buying a MaxHP Potion
            if(pick == 0)
            {
                String message;
                if(anthiera == null) //Anthiera is not in the party.
                {
                    message = "Are you sure you want to buy a MaxHP Potion? (+20HP)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Max HP: " + stats.getMaxHP() +
                                "\nZacharias's Max HP: " + zacharias.getMaxHP();
                }
                else 
                {
                    message = "Are you sure you want to buy a MaxHP Potion? (+20HP)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Max HP: " + stats.getMaxHP() +
                                "\nZacharias's Max HP: " + zacharias.getMaxHP() +
                                "\nAnthiera's Max HP: " + anthiera.getMaxHP();
                }

                int choice = JOptionPane.showOptionDialog(null, message, "Buy MaxHP Potion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions.toArray(), confirmationOptions.get(0));
                if(stats.getMoney() < 100 && !(choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //You didn't have enough money and chose any option except "No".
                {
                    JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money. You only have $" + stats.getMoney() + " out of the $100. \n", "Not Enough Money", JOptionPane.ERROR_MESSAGE);
                }
                else if(choice == 0) //You chose "Yes", buying the potion for yourself.
                {
                    stats.addMaxHP(20);
                    stats.setHP(stats.getMaxHP());
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Thank you for your purchase!\nYour Max HP: " + stats.getMaxHP() + "\n", "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 1) //You chose "Buy for Zacharias".
                {
                    zacharias.addMaxHP(20);
                    zacharias.setHP(zacharias.getMaxHP());
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Zacharias thanks you for buying the potion for him.\nZacharias's Max HP: " + zacharias.getMaxHP(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 2 && anthiera != null) //We choose "Buy for Anthiera", knowing that it is in the list of options.
                {
                    anthiera.addMaxHP(20);
                    anthiera.setHP(anthiera.getMaxHP());
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Anthiera thanks you for buying the potion for her.\nAnthiera's Max HP: " + anthiera.getMaxHP(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if((choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //We choose "No". The left side uses the list of options not including the option to buy for Anthiera. The right side includes that option.
                {
                    //Do nothing and go back.
                }
                else
                {
                    //Also do nothing and go back.
                }
            }

            //Buying a Constitution Potion
            if(pick == 1)
            {
                String message;
                if(anthiera == null) //Anthiera is not in the party.
                {
                    message = "Are you sure you want to buy a Constitution Potion? (+4 Constitution)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Constitution: " + stats.getConstitution() +
                                "\nZacharias's Constitution: " + zacharias.getConstitution();
                }
                else 
                {
                    message = "Are you sure you want to buy a Constitution Potion? (+4 Constitution)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Constitution: " + stats.getConstitution() +
                                "\nZacharias's Constitution: " + zacharias.getConstitution() +
                                "\nAnthiera's Constitution: " + anthiera.getConstitution();
                }
                
                int choice = JOptionPane.showOptionDialog(null, message, "Buy Constitution Potion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions.toArray(), confirmationOptions.get(0));
                if(stats.getMoney() < 100 && !(choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //You didn't have enough money and chose any option except "No".
                {
                    JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money. You only have $" + stats.getMoney() + " out of the $100. \n", "Not Enough Money", JOptionPane.ERROR_MESSAGE);
                }
                else if(choice == 0) //You chose "Yes", buying the potion for yourself.
                {
                    stats.addConstitution(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Thank you for your purchase!\nYour Constitution: " + stats.getConstitution(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 1) //You chose "Buy for Zacharias".
                {
                    zacharias.addConstitution(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Zacharias thanks you for buying the potion for him.\nZacharias's Constitution: " + zacharias.getConstitution(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 2 && anthiera != null) //We choose "Buy for Anthiera", knowing that it is in the list of options.
                {
                    anthiera.addConstitution(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Anthiera thanks you for buying the potion for her.\nAnthiera's Constitution: " + anthiera.getConstitution(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if((choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //We choose "No". The left side uses the list of options not including the option to buy for Anthiera. The right side includes that option.
                {
                    //Do nothing and go back.
                }
                else
                {
                    //Also do nothing and go back.
                }
            }

            //Buying an Affinity Potion
            if(pick == 2)
            {
                String message;
                if(anthiera == null) //Anthiera is not in the party.
                {
                    message = "Are you sure you want to buy an Affinity Potion? (+4 Affinity)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Affinity: " + stats.getAffinity() +
                                "\nZacharias's Affinity: " + zacharias.getAffinity();
                }
                else 
                {
                    message = "Are you sure you want to buy an Affinity Potion? (+4 Affinity)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Affinity: " + stats.getAffinity() +
                                "\nZacharias's Affinity: " + zacharias.getAffinity() +
                                "\nAnthiera's Affinity: " + anthiera.getAffinity();
                }
                
                int choice = JOptionPane.showOptionDialog(null, message, "Buy Affinity Potion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions.toArray(), confirmationOptions.get(0));
                if(stats.getMoney() < 100 && !(choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //You didn't have enough money and chose any option except "No".
                {
                    JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money. You only have $" + stats.getMoney() + " out of the $100. \n", "Not Enough Money", JOptionPane.ERROR_MESSAGE);
                }
                else if(choice == 0) //You chose "Yes", buying the potion for yourself.
                {
                    stats.addAffinity(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Thank you for your purchase!\nYour Affinity: " + stats.getAffinity(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 1) //You chose "Buy for Zacharias".
                {
                    zacharias.addAffinity(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Zacharias thanks you for buying the potion for him.\nZacharias's Affinity: " + zacharias.getAffinity(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 2 && anthiera != null) //We choose "Buy for Anthiera", knowing that it is in the list of options.
                {
                    anthiera.addAffinity(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Anthiera thanks you for buying the potion for her.\nAnthiera's Affinity: " + anthiera.getAffinity(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if((choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //We choose "No". The left side uses the list of options not including the option to buy for Anthiera. The right side includes that option.
                {
                    //Do nothing and go back.
                }
                else
                {
                    //Also do nothing and go back.
                }
            }

            //Buying an Armor Potion
            if(pick == 3)
            {
                String message;
                if(anthiera == null) //Anthiera is not in the party.
                {
                    message = "Are you sure you want to buy an Armor Modification? (+4 Armor)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Armor: " + stats.getArmor() +
                                "\nZacharias's Armor: " + zacharias.getArmor();
                }
                else 
                {
                    message = "Are you sure you want to buy an Armor Modification? (+4 Armor)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Armor: " + stats.getArmor() +
                                "\nZacharias's Armor: " + zacharias.getArmor() +
                                "\nAnthiera's Armor: " + anthiera.getArmor();
                }
                
                int choice = JOptionPane.showOptionDialog(null, message, "Buy Armor Modification", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions.toArray(), confirmationOptions.get(0));
                if(stats.getMoney() < 100 && !(choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //You didn't have enough money and chose any option except "No".
                {
                    JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money. You only have $" + stats.getMoney() + " out of the $100. \n", "Not Enough Money", JOptionPane.ERROR_MESSAGE);
                }
                else if(choice == 0) //You chose "Yes", buying the potion for yourself.
                {
                    stats.addArmor(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Thank you for your purchase!\nYour Armor: " + stats.getArmor(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 1) //You chose "Buy for Zacharias".
                {
                    zacharias.addArmor(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Zacharias thanks you for buying the Armor Modification for him.\nZacharias's Armor: " + zacharias.getArmor(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 2 && anthiera != null) //We choose "Buy for Anthiera", knowing that it is in the list of options.
                {
                    anthiera.addArmor(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Anthiera thanks you for buying the Armor Modification for her.\nAnthiera's Armor: " + anthiera.getArmor(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if((choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //We choose "No". The left side uses the list of options not including the option to buy for Anthiera. The right side includes that option.
                {
                    //Do nothing and go back.
                }
                else
                {
                    //Also do nothing and go back.
                }
            }
            //Buying a Resistance Potion
            if(pick == 4)
            {
                String message;
                if(anthiera == null) //Anthiera is not in the party.
                {
                    message = "Are you sure you want to buy a Resistance Potion? (+4 Resistance)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Resistance: " + stats.getResistance() +
                                "\nZacharias's Resistance: " + zacharias.getResistance();
                }
                else 
                {
                    message = "Are you sure you want to buy a Resistance Potion? (+4 Resistance)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Resistance: " + stats.getResistance() +
                                "\nZacharias's Resistance: " + zacharias.getResistance() +
                                "\nAnthiera's Resistance: " + anthiera.getResistance();
                }
                
                int choice = JOptionPane.showOptionDialog(null, message, "Buy Resistance Potion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions.toArray(), confirmationOptions.get(0));
                if(stats.getMoney() < 100 && !(choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //You didn't have enough money and chose any option except "No".
                {
                    JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money. You only have $" + stats.getMoney() + " out of the $100. \n", "Not Enough Money", JOptionPane.ERROR_MESSAGE);
                }
                else if(choice == 0) //You chose "Yes", buying the potion for yourself.
                {
                    stats.addResistance(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Thank you for your purchase!\nYour Resistance: " + stats.getResistance(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 1) //You chose "Buy for Zacharias".
                {
                    zacharias.addResistance(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Zacharias thanks you for buying the potion for him.\nZacharias's Resistance: " + zacharias.getResistance(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 2 && anthiera != null) //We choose "Buy for Anthiera", knowing that it is in the list of options.
                {
                    anthiera.addResistance(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Anthiera thanks you for buying the potion for her.\nAnthiera's Resistance: " + anthiera.getResistance(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if((choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //We choose "No". The left side uses the list of options not including the option to buy for Anthiera. The right side includes that option.
                {
                    //Do nothing and go back.
                }
                else
                {
                    //Also do nothing and go back.
                }
            }
            //Buying a Speed Potion
            if(pick == 5)
            {
                String message;
                if(anthiera == null) //Anthiera is not in the party.
                {
                    message = "Are you sure you want to buy a Speed Potion? (+4 Speed)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Speed: " + stats.getSpeed() +
                                "\nZacharias's Speed: " + zacharias.getSpeed();
                }
                else 
                {
                    message = "Are you sure you want to buy a Speed Potion? (+4 Speed)\nYour Money: $" + stats.getMoney() + 
                                "\nYour Speed: " + stats.getSpeed() +
                                "\nZacharias's Speed: " + zacharias.getSpeed() +
                                "\nAnthiera's Speed: " + anthiera.getSpeed();
                }
                
                int choice = JOptionPane.showOptionDialog(null, message, "Buy Speed Potion", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmationOptions.toArray(), confirmationOptions.get(0));
                if(stats.getMoney() < 100 && !(choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //You didn't have enough money and chose any option except "No".
                {
                    JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money. You only have $" + stats.getMoney() + " out of the $100. \n", "Not Enough Money", JOptionPane.ERROR_MESSAGE);
                }
                else if(choice == 0) //You chose "Yes", buying the potion for yourself.
                {
                    stats.addSpeed(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Thank you for your purchase!\nYour Affinity: " + stats.getSpeed(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 1) //You chose "Buy for Zacharias".
                {
                    zacharias.addSpeed(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Zacharias thanks you for buying the potion for him.\nZacharias's Speed: " + zacharias.getSpeed(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(choice == 2 && anthiera != null) //We choose "Buy for Anthiera", knowing that it is in the list of options.
                {
                    anthiera.addSpeed(4);
                    stats.addMoney(-100);
                    JOptionPane.showMessageDialog(null, "Anthiera thanks you for buying the potion for her.\nAnthiera's Speed: " + anthiera.getSpeed(), "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                }
                else if((choice == 2 && anthiera == null) || (choice == 3 && anthiera != null)) //We choose "No". The left side uses the list of options not including the option to buy for Anthiera. The right side includes that option.
                {
                    //Do nothing and go back.
                }
                else
                {
                    //Also do nothing and go back.
                }
            }
            //End of Shopping Potions

            //Used to Leave the Shop.
            if(pick == 6)
            {
                int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to leave the shop?", "Leave Shop", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, YesNoOption, YesNoOption[0]);
                if(choice == 0)
                {
                    JOptionPane.showMessageDialog(null, "Thank you for your time! Returning to the Dungeon...\n" , "Returning to Dungeon", JOptionPane.INFORMATION_MESSAGE);
                    completePurchases = true;
                }
                else if(choice == 1)
                {
                    JOptionPane.showMessageDialog(null, "Returning to the Shopping Command Console...", "Returning to Shop", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid input. Returning to the Shopping Command Console...", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }while(completePurchases == false);
    }
    /**
     * Initializes all objects of the Driver class.
     * 
     * Date Created: 11/15/2020
     */
    public static void initialize()
    {
        zacharias = new Ally(heroClass, "Zacharias");
        currentAlly = zacharias;
        stats = new Statistics(heroClass, heroName);
        heroSkills = new Skills(stats, heroClass);
        increaseStat = new StatIncrease(stats, heroClass, heroSkills, zacharias);
        area = new Map(stats, increaseStat, heroSkills, zacharias, heroClass);
    }
    /**
     * A one-time method used to intialize the ally Anthiera.
     * 
     * Date Created: 11/19/2020
     */
    public static void initializeAnthiera()
    {
        anthiera = new Ally(heroClass, "Anthiera");
        JOptionPane.showMessageDialog(null, anthiera.addSkills(increaseStat.getHeroLevel()), "Skills Added", JOptionPane.INFORMATION_MESSAGE);
        increaseStat.initializeAnthiera(anthiera);
    }
    /**
     * Stops the audio. SAP will need to be reinitialized in order to play again.
     * 
     * Date Created: 11/16/2020
     */
    public static void stopAudio()
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
     * Plays the menu's Background music.
     * 
     * Date Created: 11/16/2020
     */
    public static void resumeBGAudio()
    {
        try
        {
            if(area.getArea() < 40)
            {
                //Background audio in Command Console for areas below 40.
                SAP = new SimpleAudioPlayer("4.NextStep-FireEmblemAwakening-Destiny.wav", true);
            }
            else if(area.getArea() >= 40 && area.getArea() <= 60 && !postGame)
            {
                //Plays a new audio track when area is equal to 40 or above.
                SAP = new SimpleAudioPlayer("12.TheFinalRoad-PokemonLetsGoPikachu-Eevee.wav", true);
            }
            else if(postGame || area.getArea() > 60)
            {
                SAP = new SimpleAudioPlayer("19.Aftermath-FireEmblemEchoes-WithPrideinYourHeart.wav", true);
            }
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Saves the game automatically after every floor.
     * 
     * Date Created: 08/31/2021
     */
    public static void saveGame()
    {
        boolean notSaved = true;
		while(notSaved) 
		{
			int pick = JOptionPane.showConfirmDialog (null, "Would you like to save your game?\n" + stats.listAttributes() + "\n", "Save Game", JOptionPane.YES_NO_OPTION);
			
			String filepath = new String("File1.txt");
			String filepath2 = new String("File2.txt");
			String filesInformation = "";
			
			if(pick == 0) //yes, save game.
			{
				//This section previews both save files.
				PrintWriter writer = null;
				
				//filepaths to the save files.
				File path = new File(filepath);
				File path2 = new File(filepath2);
				Scanner inputFile = null;
				Scanner inputFile2 = null;

				
				int fileNum = 1;
				try //validate both files.
				{
					inputFile = new Scanner(path);
					fileNum++;
					inputFile2 = new Scanner(path2);
				}
				catch (FileNotFoundException e1) 
				{
					JOptionPane.showMessageDialog (null, "Save file " + fileNum + " was not found.\nPlease select a text file in the following menu.", "Save File not Found.", JOptionPane.ERROR_MESSAGE);
					JFileChooser fileChooser = new JFileChooser("SemesterProjectFile");
					pick = fileChooser.showOpenDialog (null);
					if(pick == JFileChooser.APPROVE_OPTION) 
					{
						File file = fileChooser.getSelectedFile ( );
						try
						{
							Scanner validate = new Scanner(file);
							validate.close();
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				}
				pick = 0;
				
				//Get the summaries of the data in both files. (This one for file 1)
				if(inputFile.hasNext()) 
				{
					String str = inputFile.nextLine();
					String[] values = str.split ("\\|");
					
					//Name
					filesInformation +=  "\n-------------------------\n(File 1) " + values[0];
					filesInformation += "\n--------------------\n";
					//Stages Completed
					filesInformation += "Stages Completed: " + values[1] + "\n";
					//Level
					filesInformation += "Level: " + values[2] + "\n";
					//Experience
					filesInformation += "Experience: " + values[3] + "XP / 100XP\n\n";
                    //HP/MaxHP
                    filesInformation += "HP: " + values[5] + "HP/" + values[4] + "HP\n";
                    //Constitution
                    filesInformation += "Constitution: " + values[6] + "\n";
                    //Affinity
                    filesInformation += "Affinity: " + values[7] + "\n";
                    //Armor
                    filesInformation += "Armor: " + values[8] + "\n";
                    //Resistance
                    filesInformation += "Resistance: " + values[9] + "\n";
                    //Speed
                    filesInformation += "Speed: " + values[10] + "\n";
                    //Money
                    filesInformation += "Money: " + values[11] + "\n";
                    //Class
                    filesInformation += "Class: " + values[12] + "\n-------------------------\n";
				}
				else 
				{
					filesInformation += "File 1 is Empty.\n\n";
				}
				//Get the summary of the data in file 2.
				if(inputFile2.hasNext()) 
				{				
					String str = inputFile2.nextLine();
					String[] values = str.split ("\\|");
					
					//Name
					filesInformation +=  "\n-------------------------\n(File 2) " + values[0];
					filesInformation += "\n--------------------\n";
					//Stages Completed
					filesInformation += "Stages Completed: " + values[1] + "\n";
					//Level
					filesInformation += "Level: " + values[2] + "\n";
					//Experience
					filesInformation += "Experience: " + values[3] + "XP / 100XP\n\n";
                    //HP/MaxHP
                    filesInformation += "HP: " + values[5] + "HP/" + values[4] + "HP\n";
                    //Constitution
                    filesInformation += "Constitution: " + values[6] + "\n";
                    //Affinity
                    filesInformation += "Affinity: " + values[7] + "\n";
                    //Armor
                    filesInformation += "Armor: " + values[8] + "\n";
                    //Resistance
                    filesInformation += "Resistance: " + values[9] + "\n";
                    //Speed
                    filesInformation += "Speed: " + values[10] + "\n";
                    //Money
                    filesInformation += "Money: " + values[11] + "\n";
                    //Class
                    filesInformation += "Class: " + values[12] + "\n-------------------------\n";
				}
				else 
				{
					filesInformation += "File 2 is Empty.\n\n";
				}
				
				
				//The user chooses which file to save into.
				String[] options = {"File 1", "File 2"};
				
				pick = JOptionPane.showOptionDialog (null, "Which file would you like to save to?" + filesInformation, "Save Game", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				String savedPath = "";
				if(pick == 0) //File 1
				{
					savedPath = filepath;
				}
				else if(pick == 1) //File 2
				{
					savedPath = filepath2;
				}
				else 
				{
					//Do Nothing.
				}
								
                //if the use chose a file to save to.
				if(!savedPath.isBlank ( )) 
				{
					try
					{
						writer = new PrintWriter(savedPath);
					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
					
					writer.println(stats.getHeroName() + "|" +  area.getArea ( ) + "|" + increaseStat.getHeroLevel ( ) + "|" + increaseStat.getHeroXP( ) + "|" 
                    + stats.getMaxHP() + "|" + stats.getHP() + "|" + stats.getConstitution() + "|" + stats.getAffinity() + "|" + stats.getArmor() + "|" 
                    + stats.getResistance() + "|" + stats.getSpeed() + "|" + stats.getMoney() + "|" + heroClass);

                    //Cleverly coded so the algorithm can indicate which is the current ally.
                    //The algorithm determines this by checking the first value. If it's Z, it's Zacharias. If it's A, it's Anthiera.
                    if(currentAlly.equals(zacharias))
                    {
                        writer.println("Z|" + zacharias.getMaxHP() + "|" + zacharias.getHP() + "|" + zacharias.getConstitution() + "|" + zacharias.getAffinity()
                         + "|" + zacharias.getArmor() + "|" + zacharias.getResistance() + "|" + zacharias.getSpeed() + "|" + zacharias.getAllyClass());
                         //Because Anthiera is not the current ally, we don't know if she's joined the team yet.
                         //This if statement checks that possibility.
                         if(anthiera != null)
                         {
                            writer.println("A|" + anthiera.getMaxHP() + "|" + anthiera.getHP() + "|" + anthiera.getConstitution() + "|" + anthiera.getAffinity()
                            + "|" + anthiera.getArmor() + "|" + anthiera.getResistance() + "|" + anthiera.getSpeed() + "|" + anthiera.getAllyClass());
                         }
                    }
                    else //If Anthiera is the current ally
                    {
                        writer.println("A|" + anthiera.getMaxHP() + "|" + anthiera.getHP() + "|" + anthiera.getConstitution() + "|" + anthiera.getAffinity()
                         + "|" + anthiera.getArmor() + "|" + anthiera.getResistance() + "|" + anthiera.getSpeed() + "|" + anthiera.getAllyClass());
                        writer.println("Z|" + zacharias.getMaxHP() + "|" + zacharias.getHP() + "|" + zacharias.getConstitution() + "|" + zacharias.getAffinity()
                         + "|" + zacharias.getArmor() + "|" + zacharias.getResistance() + "|" + zacharias.getSpeed() + "|" + zacharias.getAllyClass());
                    }
					
					notSaved = false;
					JOptionPane.showMessageDialog (null, "Your game has successfully been saved!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
					writer.close();
				}
			}
			else //If the user chose X in the previous choice. (Which file would you like to save to?)
			{
				pick = JOptionPane.showConfirmDialog (null, "Are you sure you do not want to save your game?\nAll progress will be lost when the game is closed.", "Save Game", JOptionPane.YES_NO_OPTION);
				if(pick == 0) //Yes
				{
					notSaved = false;
					break;
				}
			}
		}
    }
    /**
     * loadFile loads a save file from the Title Menu.
     * 
     * Date Created: 09/01/2021 
     */
    private static void loadFile(String str, int loadNum)
    {
        //loadNum being 0 means importing stats for the Hero.
        if(loadNum == 0)
        {
            //Say for example line 1 had:
            //Javar|50|20|10|450|450|80|85|90|90|50|900|Mage|
            //Each cell would represent:
            //Name|Area|Level|Exp|Max HP|HP|Constitution|Affinity|Armor|Resistance|Speed|Money|Class
            String[] values = str.split ("\\|");
            /*for(int i = 0; i < values.length - 1; i++)
            {
                System.out.print(values[i] + "|");
            }*/
            
            //Name: values[0];
            heroName = values[0];
            //Area Number: values[1];
            int areaNum = Integer.parseInt(values[1]);
            //Level: values[2];
            int heroLevel = Integer.parseInt(values[2]);
            //EXP: values[3];
            int heroXP = Integer.parseInt(values[3]);
            //Max HP: values[4];
            int maxHP = Integer.parseInt(values[4]);
            //HP: values[5];
            int HP = Integer.parseInt(values[5]);
            //Constitution: values[6];
            int constitution = Integer.parseInt(values[6]);
            //Affinity: values[7];
            int affinity = Integer.parseInt(values[7]);
            //Armor: values[8];
            int armor = Integer.parseInt(values[8]);
            //Resistance: values[9];
            int resistance = Integer.parseInt(values[9]);
            //Speed: values[10];
            int speed = Integer.parseInt(values[10]);
            //Money: values[11];
            int money = Integer.parseInt(values[11]);
            //Hero Class: values[12];
            heroClass = values[12];

            stats = new Statistics(heroName, money, maxHP, HP, constitution, affinity, armor, resistance, speed);
            heroSkills = new Skills(stats, heroClass, heroLevel);
            //IncreaseStat's Ally has a hypothetical Zacharias at this moment. This will change upon loadNum 1 or 2.
            increaseStat = new StatIncrease(stats, heroClass, heroSkills, new Ally(heroClass, "Zacharias"));
            //area's Ally also has a hypothetical Zacharias. 
            area = new Map(stats, increaseStat, heroSkills, new Ally(heroClass, "Zacharias"), heroClass, areaNum);
            
            //To be used later when currentAlly is initialized.
            increaseStat.setHeroLevel(heroLevel);
            increaseStat.addHeroXP(heroXP);

            //System.out.println("Loaded Hero");
        }
        else if(loadNum == 1)
        {
            //Say for example line 1 had:
            //Z|100|100|35|0|15|5|5|Berserker
            //Each cell would represent:
            //Name|Max HP|HP|Constitution|Affinity|Armor|Resistance|Speed|Money|Class
            String[] values = str.split ("\\|");
            /*for(int i = 0; i < values.length - 1; i++)
            {
                System.out.print(values[i] + "|");
            }*/
             //Max HP: values[1];
             int maxHP = Integer.parseInt(values[1]);
             //HP: values[2];
             int HP = Integer.parseInt(values[2]);
             //Constitution: values[3];
             int constitution = Integer.parseInt(values[3]);
             //Affinity: values[4];
             int affinity = Integer.parseInt(values[4]);
             //Armor: values[5];
             int armor = Integer.parseInt(values[5]);
             //Resistance: values[6];
             int resistance = Integer.parseInt(values[6]);
             //Speed: values[7];
             int speed = Integer.parseInt(values[7]);
             //Class: values[8];
             String allyClass = values[8];

            if(values[0].equals("Z")) //The Z in the first column of the 2nd line stands for Zacharias, letting the algorithm know that it is loading Zacharias.
            {
                zacharias = new Ally("Zacharias", maxHP, HP, constitution, affinity, armor, resistance, speed, allyClass);
                currentAlly = zacharias;
                increaseStat.initializeZacharias(zacharias);
                zacharias.addSkills(increaseStat.getHeroLevel());
                //System.out.println("Loaded Zacharias");
            }
            else //The A in the first column of the 2nd line stands for Anthiera, letting the algorithm know that it is loading Anthiera.
            {
                anthiera = new Ally("Anthiera", maxHP, HP, constitution, affinity, armor, resistance, speed, allyClass);
                currentAlly = anthiera;
                increaseStat.initializeAnthiera(anthiera);
                anthiera.addSkills(increaseStat.getHeroLevel());
                //System.out.println("Loaded Anthiera");
            }   

            int heroXP = increaseStat.getHeroXP();
            int heroLevel = increaseStat.getHeroLevel();
            int areaNum = area.getArea();
            //IncreaseStat's Ally has now become the 'real' zacharias.
            increaseStat = new StatIncrease(stats, heroClass, heroSkills, zacharias, heroXP, heroLevel);
            //area's Ally becomes the current ally.
            area = new Map(stats, increaseStat, heroSkills, currentAlly, heroClass, areaNum);
        }
        else if (loadNum == 2)
        {
            //Say for example line 1 had:
            //Z|100|100|35|0|15|5|5|Berserker
            //Each cell would represent:
            //Name|Max HP|HP|Constitution|Affinity|Armor|Resistance|Speed|Money|Class
            String[] values = str.split ("\\|");
            /*for(int i = 0; i < values.length - 1; i++)
            {
                System.out.print(values[i] + "|");
            }*/
             //Max HP: values[1];
             int maxHP = Integer.parseInt(values[1]);
             //HP: values[2];
             int HP = Integer.parseInt(values[2]);
             //Constitution: values[3];
             int constitution = Integer.parseInt(values[3]);
             //Affinity: values[4];
             int affinity = Integer.parseInt(values[4]);
             //Armor: values[5];
             int armor = Integer.parseInt(values[5]);
             //Resistance: values[6];
             int resistance = Integer.parseInt(values[6]);
             //Speed: values[7];
             int speed = Integer.parseInt(values[7]);
             //Class: values[8];
             String allyClass = values[8];

            if(values[0].equals("A")) //The A in the first column of the 3nd line stands for Anthiera, letting the algorithm know that it is loading Anthiera.
            {
                anthiera = new Ally("Anthiera", maxHP, HP, constitution, affinity, armor, resistance, speed, allyClass);
                increaseStat.initializeAnthiera(anthiera);
                anthiera.addSkills(increaseStat.getHeroLevel());
                //System.out.println("Loaded Anthiera");
            }
            else //If Anthiera was initialized previously and zacharias remains null. Also used as a backup in case Zacharias didn't load previously.
            {
                if(zacharias == null)
                {
                    zacharias = new Ally("Zacharias", maxHP, HP, constitution, affinity, armor, resistance, speed, allyClass);
                    increaseStat.initializeZacharias(zacharias);
                    zacharias.addSkills(increaseStat.getHeroLevel());
                    //System.out.println("Loaded Zacharias");
                }
            }   
        }
    }

    /**
     * Opens the Command Console, where the user can interact with the game.
     * 
     * Date Created: 09/06/2021
     */
    private static void openCommandConsole()
    {
        stopAudio();
        //starts background audio. This background audio changes depending on location.
        resumeBGAudio();
        int choice;
        do
        {
            //MAIN MENU
            String[] consoleOptions = {"Attributes", "Open Shop", "Ally", "New Scenario", "Rush Location", "Practice", "Save Game", "Leave"};
            choice = JOptionPane.showOptionDialog(null, "Welcome to the Command Console! Please Choose an Action. \nYour Group is Level " + increaseStat.getHeroLevel() + ".\n\nLocation\n--------------------\n" + area.getLocation(), "Command Console", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, consoleOptions, consoleOptions[0]);

            //Attribute Description
            switch(choice)
            {
                //Attributes - shows a message dialog containing the hero's attributes.
                //Contains a choice for if the user wants more info about what each attribute does.
                case 0:
                    String[] list = {"OK", "Attribute Info", "Skills"};
                    int listChoice = JOptionPane.showOptionDialog(null, stats.listAttributes(), "Hero Attributes", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, list, list[0]);
                    if(listChoice == 1)
                    {
                        JOptionPane.showMessageDialog(null, stats.attributeDescription(heroClass), "Attribute Descriptions", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(listChoice == 2)
                    {
                        JOptionPane.showMessageDialog(null, heroSkills.listAllSkillDamage(), "Skill Damage", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                //Open Shop - Opens the shop menu.
                case 1:
                    openShop();
                    break;
                //Ally - returns the current Ally's stats.
                case 2:
                    String[] allyList = {"OK", "Attribute Info", "Switch Allies"};
                    listChoice = JOptionPane.showOptionDialog(null, currentAlly.getAllyInformation(), "Ally Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, allyList, allyList[0]);
                    if(listChoice == 1) //OK
                    {
                        JOptionPane.showMessageDialog(null, stats.attributeDescription(heroClass), "Attribute Descriptions", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(listChoice == 2) //Switch Ally - Allows the user to bring a different ally to battle.
                    {
                        if(anthiera != null)
                        {
                            if(currentAlly.equals(zacharias))
                            {
                                currentAlly = anthiera;
                                area.setAlly(anthiera);
                            }
                            else //if(currentAlly.equals(anthiera))
                            {
                                currentAlly = zacharias;
                                area.setAlly(zacharias);
                            }
                            JOptionPane.showMessageDialog(null, "Successfully switched allies! \nYou new ally is " + currentAlly.getAllyName(), "Switched Allies", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Zacharias is your only available ally at the moment.", "No Other Allies", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
                //New Scenario - Gets a random scenario and increments area by 1.
                case 3:
                    area.getScenario();
                    break;
                //rush location - calls getScenario 9 times.
                case 4:
                    String[] options = {"Yes", "No"};
                    int optionChoice = JOptionPane.showOptionDialog(null, "Rush Location will give you 9 different scenarios without stopping.\nAre you sure you want to proceed?\n(Rush Location will always stop on every 10th floor.)", "Rush Location", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
                    if(optionChoice == 0)
                    {
                        area.rushLocation();
                    }
                    break;
                //Practice Battle - allows the user to enter a fight with a random enemy.
                case 5:
                    String[] practiceChoices = {"Practice Battle", "Friendly Duel", "Leave"};
                    int practiceChoice = JOptionPane.showOptionDialog(null, "How will you practice?", "Practice Method", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, practiceChoices, practiceChoices[0]) ;   

                    if(practiceChoice == 0)
                    {
                        stopAudio();
                        bat = new Battle(stats, heroSkills, currentAlly, area.getArea());
                        bat.getResult(increaseStat);
                        resumeBGAudio();
                    }
                    else if(practiceChoice == 1)
                    {
                        //if anthiera has already been unlocked.
                        if(anthiera != null)
                        {
                            String[] allies = {"Zacharias", "Anthiera"};
                            int optionChosen = JOptionPane.showOptionDialog(null, "Challenge Who?", "Who to Challenge", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, allies, allies[0]);

                            if(optionChosen == 0)
                            {
                                JOptionPane.showMessageDialog(null, "You decided to ask Zacharias for a friendly competition.", "Friendly Duel", JOptionPane.INFORMATION_MESSAGE);
                                JOptionPane.showMessageDialog(null, "\"Sure thing, " + heroName + ",\" Zacharias says.\n\"Hold nothing back!\"", "Friendly Duel", JOptionPane.INFORMATION_MESSAGE);
                                stopAudio();
                                //Allies will be unaffected by difficulty.
                                Enemy zach = new Enemy(zacharias); 
                                bat = new Battle(stats, heroSkills, zach);
                                bat.getDuelResults(increaseStat);
                                resumeBGAudio();
                            }
                            if(optionChosen == 1)
                            {
                                JOptionPane.showMessageDialog(null, "You decided to ask Anthiera for a friendly competition.", "Friendly Duel", JOptionPane.INFORMATION_MESSAGE);
                                JOptionPane.showMessageDialog(null, "\"Of course, " + heroName + ",\" Anthiera says.\n\"I'll show you what my Royal Guard training has led to!\"", "Friendly Duel", JOptionPane.INFORMATION_MESSAGE);
                                stopAudio();
                                //Allies will be unaffected by difficulty.
                                Enemy anth = new Enemy(anthiera); 
                                bat = new Battle(stats, heroSkills, anth);
                                bat.getDuelResults(increaseStat);
                                resumeBGAudio();
                            }
                        }
                        else //if anthiera has not been unlocked
                        {
                            JOptionPane.showMessageDialog(null, "You decided to ask Zacharias for a friendly competition.", "Friendly Duel", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "\"Sure thing, " + heroName + ",\" Zacharias says.\n\"Hold nothing back!\"", "Friendly Duel", JOptionPane.INFORMATION_MESSAGE);
                            stopAudio();
                            //Allies will be unaffected by difficulty.
                            Enemy zach = new Enemy(currentAlly); 
                            bat = new Battle(stats, heroSkills, zach);
                            bat.getDuelResults(increaseStat);
                            resumeBGAudio();
                        }
                    }
                    break;
                //Save the Game
                case 6:
                    saveGame();
                    break;
                //Leave
                case 7:
                    saveGame();
                    JOptionPane.showMessageDialog(null, "Leaving Command Console.\nThank you for playing The Dungeon of Dystopia!\n\n", "Leaving Console", JOptionPane.INFORMATION_MESSAGE);
                    break;
                //Pressing the 'X' on the top-right.
                case -1:
                    saveGame();
                    JOptionPane.showMessageDialog(null, "Leaving Command Console.\nThank you for playing The Dungeon of Dystopia!\n\n", "Leaving Console", JOptionPane.INFORMATION_MESSAGE);
                    break;
                //Invalid Command
                default:
                    JOptionPane.showMessageDialog(null, "Invalid command. Please try again.\n", "Invalid Command", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }while(choice != 7 && choice != -1);
        //Exits the program.
        System.exit(0);
    }
}