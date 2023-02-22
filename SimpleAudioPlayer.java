/**
 * --------------------------------------------------------------------------
 * File name: SimpleAudioPlayer.java
 * Project name: Semester Project
 * --------------------------------------------------------------------------
 * Creator's name and email: GeeksforGeeks, https://www.geeksforgeeks.org/
 * Modified by: Michael Ng, ngmw01@etsu.edu
 * Course: CSCI 1250-942
 * Creation Date: 11/15/2020
 * Completion Date: 11/15/2020
 * @version 1.01
 * --------------------------------------------------------------------------
 */
//Original Project: https://www.geeksforgeeks.org/play-audio-file-using-java/
/**
 * The modified version of this audioplayer removes
 * static references and alters the constructor.
 * Many methods were removed since they did not function correctly.
 * 
 * Note: Audio Files are pulled from the same folder as Semester Project.
 * Most files have been compressed in .wav format so the size is roughly 152 MB.
 * 
 * Music Files are pulled from a multitude of video games.
 * None of these soundtracks are made by Michael Ng.
 * 
 * Games include: 
 * Fire Emblem Echoes: Shadows of Valentia, 
 * Fire Emblem: Path of Radiance,
 * Fire Emblem: Awakening,
 * Fire Emblem: Three Houses,
 * Pokemon Black and White 2,
 * Pokemon Omega Ruby & Alpha Sapphire (ORAS),
 * Pokemon Let's Go! Pikachu/Eevee,
 * Pokemon Sword and Shield,
 * Pokemon Mystery Dungeon: Gates to Infinity,
 * Deltarune,
 * Sonny 2.
 * 
 * All credit for the original SimpleAudioPlayer goes to GeeksForGeeks.
 */

// Java program to play an Audio 
// file using Clip Object 
import java.io.File; 
import java.io.IOException; 

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
/**
 * SimpleAudioPlayer allows the interface to play background
 * music while code is running. Music can be stopped and changed.
 * 
 * The use of errors or the try{} and catch{} method
 * is required for the implementation of audio.
 * 
 * Created: March 24, 2018
 * Date Modified: November 15, 2020
 */
public class SimpleAudioPlayer 
{ 
	// to store current position 
	Long currentFrame; 
	Clip clip; 
	
	// current status of clip 
	String status; 
	
	AudioInputStream audioInputStream; 
	static String filePath; 

	/**
	 * constructor to initialize streams and clip 
	 * altered to add the option to loop music files and
	 * now calls on the beginPlaying method.
	 * 
	 * Date Modified: 11/15/2020
	 * 
	 * @param fileName
	 * @param loop
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public SimpleAudioPlayer(String fileName, boolean loop) 
		throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
	{ 
		// create AudioInputStream object 
		audioInputStream = AudioSystem.getAudioInputStream(new File(fileName)); 
		
		// create clip reference 
		clip = AudioSystem.getClip(); 
		
		// open audioInputStream to the clip 
		clip.open(audioInputStream); 
		
		if(loop)
		{
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
		}

		beginPlaying(fileName);
	} 

	/**
	 * begins playing the sound file located at fileName.
	 * 
	 * Date Modified: 11/15/2020
	 * 
	 * @param fileName
	 */
	public void beginPlaying(String fileName)
	{ 
		try
		{ 
			filePath = fileName; 
			play(); 
		} 
		
		catch (Exception ex) 
		{ 
			System.out.println("Error with playing sound."); 
			ex.printStackTrace(); 
		} 
	} 
	
	/**
	 * Method to play the audio.
	 * 
	 * Date Modified: 11/15/2020
	 */
	public void play() 
	{ 
		//start the clip 
		clip.start(); 
		
		status = "play"; 
	} 
	
	/**
	 * Method used to stop the audio.
	 * 
	 * Date Modified: 11/15/2020
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void stop() throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException 
	{ 
		currentFrame = 0L; 
		clip.stop(); 
		clip.close(); 
	} 
} 