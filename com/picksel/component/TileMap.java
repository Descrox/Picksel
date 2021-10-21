package com.picksel.component;

import com.picksel.asset.*;
import com.picksel.component.property.Property;
import com.picksel.renderer.*;
import com.picksel.util.Input;
import com.picksel.util.Camera;

/**
 * A map of tiled images.
 *
 * @author Noah James Rathman
 */
public final class TileMap implements Component, Renderable {
	private final String ID;
	private TileSheet sheet;
	private Sprite[][] tiles;
	private Map indices;
	private Bounds bounds;
	private int layer, drawType;
	private boolean visible;

	/**
	 * Creates a new TileMap.
	 *
	 * @param id Identifier for this Component
	 * @param sheet TileSheet used for this TileMap
	 * @param indices Map indices for this TileMap
	 * @param bounds Bounding box of this Component
	 */
	public TileMap(String id, TileSheet sheet, Map indices, Bounds bounds) {
		this.sheet		= sheet;
		this.indices	= indices;
		this.bounds		= bounds;
		ID						= id;
		layer					= 0;
		visible				= true;
		drawType			= Renderable.STATIC_DRAW;

		createTiles();
	}

	private void createTiles() {
		final String TILE_ID = ID + "_tile_";
		final int TILE_W = sheet.getTileWidth();
		final int TILE_H = sheet.getTileHeight();

		int[][] iArray = indices.getIndexArray();
		tiles = new Sprite[iArray.length][iArray[0].length];
		bounds.setSize(iArray.length * TILE_W, iArray[0].length * TILE_H);

		int tNum = 0;
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				String tID = TILE_ID + tNum;
				Bounds tBounds = new Bounds(x * TILE_W, y * TILE_H, TILE_W, TILE_H);
				Color[][] cArray = sheet.getTile(iArray[x][y]);

				tiles[x][y] = new Sprite(tID, cArray, tBounds);
				tNum++;
			}
		}
	}

	/**
	 * @param p New Component Property
	 * @deprecated TileMaps currently have no use for
	 * Properties.
	 */
	@Deprecated
	public void addProperty(Property p) {}

	/**
	 * <b>Note:</b> TileMaps will always do nothing when
	 * updating.
	 *
	 * @param dt The amount of time in seconds the last frame
	 * took to finish
	 * @param in User input found this frame
	 */
	public void update(float dt, Input in) {}

	public void render(Renderer renderer, Camera camera) {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				Sprite tile = tiles[x][y];
				int x1 = (int) tile.bounds().getX(), x2 = (int) (x1 + tile.bounds().getWidth());
				int y1 = (int) tile.bounds().getY(), y2 = (int) (y1 + tile.bounds().getHeight());

				if(renderer.inBounds(x1, y1) || renderer.inBounds(x1, y2) ||
					 renderer.inBounds(x2, y1) || renderer.inBounds(x2, y2)) {
					tile.render(renderer, camera);
				}
			}
		}
	}

	public String id() {
		return ID;
	}

	public int layer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public boolean visible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int drawType() {
		return drawType;
	}

	public void setDrawType(int drawType) {
		this.drawType = drawType;
	}

	public Bounds bounds() {
		return bounds;
	}
}