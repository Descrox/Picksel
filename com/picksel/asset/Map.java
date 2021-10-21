package com.picksel.asset;

import java.util.Scanner;
import java.io.*;

import com.picksel.util.exception.*;

/**
 * Parses files that hold TileMap information.
 *
 * @author Noah James Rathman
 */
public final class Map extends Asset {
	private int[][] iArray;

	/**
	 * Creates a new Map.
	 * See {@link com.picksel.asset.Asset#Asset(File)} 
	 * for more details.
	 *
	 * @param file File this Asset uses
	 */
	public Map(File file) {
		super(file);

		if(!file.getPath().endsWith(".pmap")) {
			warn(new AssetWarning(
				"The file \"" + file + "\" should have the extension \".pmap\"."
			));
		}

		generateIndexArray();
	}

	private void generateIndexArray() {
		try {
			FileInputStream fStream = new FileInputStream(file);
			Scanner scn = new Scanner(fStream);

			int tWidth = scn.nextInt();
			int tHeight = scn.nextInt();
			iArray = new int[tWidth][tHeight];

			for(int y = 0; y < tHeight; y++) {
				for(int x = 0; x < tWidth; x++) {
					iArray[x][y] = scn.nextInt();
				}
			}

			scn.close();
		} catch(IOException e) {
			throw new AssetException("Failed to load map: " + e.getMessage());
		}
	}

	/**
	 * Gets the generated index array.
	 *
	 * @return Array of TileMap indices
	 */
	public int[][] getIndexArray() {
		return iArray;
	}
}