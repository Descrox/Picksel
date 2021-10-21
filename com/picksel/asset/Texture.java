package com.picksel.asset;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import com.picksel.renderer.Color;
import com.picksel.util.exception.*;

/**
 * Represents an image texture.
 *
 * @author Noah James Rathman
 */
public class Texture extends Asset {
	/**
	 * Returns a flipped 2D Color array from the passed Texture.<br>
	 * 
	 * <b>Note:</b> {@code dir} when {@code true} flips on the
	 * X axis and {@code false} flips on the Y axis.
	 * 
	 * @param dir Direction flipped
	 * @param tex Texture to be flipped
	 * @return Flipped Texture
	 */
	public static Color[][] flip(boolean dir, Texture tex) {
		Color[][] array = tex.getColorArray();
		Color[][] nArray = new Color[array.length][array[0].length];

		int idx = 0;
		if(dir) {
			for(int x = array.length - 1; x >= 0; x--) {
				for(int y = 0; y < array[0].length; y++) {
					nArray[x][y] = array[idx][y];
					idx++;
				}
			}
		} else {
			for(int x = 0; x < array.length; x++) {
				for(int y = array[0].length - 1; y >= 0; y--) {
					nArray[x][y] = array[x][idx];
					idx++;
				}
			}
		}

		return nArray;
	}

	//Class
	/**
	 * The recommended maximum width and height of a texture.
	 */
	public static final int MAX_LENGTH = 512;

	/** 2D Color array representation of Image. */
	protected Color[][] cArray;

	/**
	 * Creates a new Texture with the passed file.
	 * See {@link com.picksel.asset.Asset#Asset(File)} 
	 * for more details.
	 *
	 * @param file File this Texture uses
	 */
	public Texture(File file) {
		super(file);

		try {
			BufferedImage image = ImageIO.read(file);

			if(image.getWidth() > MAX_LENGTH || image.getHeight() > MAX_LENGTH) {
				warn(new AssetWarning(
					"The texture \"" + file + "\" exceeds the recommended maximum " +
					"width or height. This may cause high memory usage."
				));
			}

			cArray = Color.toColorArray(image);
			image.flush();
		} catch(IOException e) {
			throw new AssetException("Failed to load image: " + e.getMessage());
		}
	}

	/**
	 * Gets the 2D Color array generated from this Texture.
	 *
	 * @return Texture as 2D Color array
	 */
	public Color[][] getColorArray() {
		return cArray;
	}
}