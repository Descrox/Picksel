package com.picksel.asset;

import java.awt.image.BufferedImage;
import java.io.*;

import com.picksel.renderer.Color;
import com.picksel.util.exception.AssetException;

/**
 * Gives a Texture tiled functionality.
 * TileSheets are structured from top left to bottom right,
 * starting from {@code (0, 0)}.
 *
 * @author Noah James Rathman
 */
public final class TileSheet extends Texture {
	private int tileWidth, tileHeight;
	private Color[][][] cArrays;

	/**
	 * Creates a new TileSheet with the set tile size.
	 * See {@link com.picksel.asset.Texture#Texture(File)} 
	 * for more details.
	 *
	 * @param file File this Texture uses
	 * @param tileWidth Width of each tile
	 * @param tileHeight Height of each tile
	 */
	public TileSheet(File file, int tileWidth, int tileHeight) {
		super(file);

		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;

		createColorArrays();
	}

	private void createColorArrays() {
		int hTiles			= (cArray.length / tileWidth);
		int vTiles			= (cArray[0].length / tileHeight);
		int totalTiles	= hTiles * vTiles;
		cArrays = new Color[totalTiles][tileWidth][tileHeight];

		int tileIdx = 0;
		for(int tY = 0; tY < vTiles; tY++) {
			for(int tX = 0; tX < hTiles; tX++) {
				int xOff = tX * tileWidth;
				int yOff = tY * tileHeight;

				for(int x = 0; x < tileWidth; x++) {
					for(int y = 0; y < tileHeight; y++) {
						cArrays[tileIdx][x][y] = cArray[x + xOff][y + yOff];
					}
				}

				tileIdx++;
			}
		}
	}

	/**
	 * Gets the width of each tile in this TileSheet.
	 *
	 * @return Tile width
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * Gets the height of each tile in this TileSheet.
	 *
	 * @return Tile height
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * Gets the tile at the passed location.
	 *
	 * @param tileIndex Index of tile (from top left to 
	 * bottom right)
	 * @return The tile at the specified position. If the
	 * passed position is out of bounds, this method
	 * returns {@code null}.
	 */
	public Color[][] getTile(int tileIndex) {
		return cArrays[tileIndex];
	}
}