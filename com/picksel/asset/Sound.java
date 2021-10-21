package com.picksel.asset;

import java.io.*;
import javax.sound.sampled.*;

import com.picksel.util.exception.AssetException;

/**
 * Asset which uses files as sound clips.
 *
 * @author Noah James Rathman
 */
public final class Sound extends Asset {

	/**
	 * Creates a new Sound with the passed file.
	 * See {@link com.picksel.asset.Asset#Asset(File)} 
	 * for more details.
	 *
	 * @param file File this Sound uses
	 */
	public Sound(File file) {
		super(file);
	}

	/**
	 * Gets this sound as a Clip.
	 *
	 * @return Playable Sound clip
	 */
	public Clip getClip() {
		try {
			Clip clip							= AudioSystem.getClip();
			AudioInputStream ais	= AudioSystem.getAudioInputStream(file);

			clip.open(ais);
			return clip;
		} catch(Exception e) {
			throw new AssetException("Failed to load sound: " + e.getMessage());
		}
	}
}