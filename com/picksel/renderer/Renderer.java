package com.picksel.renderer;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.picksel.util.Camera;

/**
 * Manages all rendering for a Picksel Game.
 *
 * @author Noah James Rathman
 */
public final class Renderer {
	private final Color[][] pixels;
	private final BufferedImage image;
	private final int width, height;

	/**
	 * Creates a new Renderer.
	 *
	 * @param width Horizontal pixels per vertical pixel
	 * @param height Vertical pixels per horizontal pixel
	 * @param scale Scale of each pixel
	 */
	public Renderer(int width, int height, float scale) {
		pixels = new Color[width][height];
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		this.width = width;
		this.height = height;

		clear();
	}

	/**
	 * Resets all pixels to {@link Color#BLACK}.
	 */
	public void clear() {
		long color = 0L;

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x][y] = Color.BLACK;
			}	
		}
	}

	/**
	 * Tests if the passed coordinate is inside the screen.
	 *
	 * @param x Coordinate X position
	 * @param y Coordinate Y position
	 * @return {@code True} if in bounds, {@code false}
	 * otherwise.
	 */
	public boolean inBounds(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	/**
	 * Sets the color of a pixel in game.
	 *
	 * @param x Horizontal location
	 * @param y Vertical location
	 * @param color Draw color
	 */
	public void setPixel(int x, int y, Color color) {
		if(inBounds(x, y)) pixels[x][y] = pixels[x][y].blend(color);
	}

	/**
	 * Draws a 2D array of colors.
	 *
	 * @param xOff Horizontal offset
	 * @param yOff Vertical offset
	 * @param colors Array of colors drawn
	 */
	public void drawColorArray(int xOff, int yOff, Color[][] colors) {
		int w = colors.length;
		int h = colors[0].length;

		for(int y = 0; y < h; y++) {
			if(y >= height) break;
			for(int x = 0; x < w; x++) {
				if(x >= width) break;
				Color col = colors[x][y];

				if(inBounds(x + xOff, y + yOff)) {
					pixels[x + xOff][y + yOff] = pixels[x + xOff][y + yOff].blend(col);
				}
			}	
		}
	}

	/**
	 * Draws the passed Renderable with the passed Camera
	 * offset.
	 *
	 * @param r Renderable being drawn
	 * @param camera Camera offset for current frame
	 */
	void drawRenderable(Renderable r, Camera camera) {
		r.render(this, camera);
	}

	/**
	 * Draws the outline of a rectangle.
	 *
	 * @param xOff Horizontal offset
	 * @param yOff Vertical offset
	 * @param xSize Horizontal size
	 * @param ySize Vertical size
	 * @param color Draw color
	 */
	public void drawRect(int xOff, int yOff, int xSize, int ySize, Color color) {
		int x1 = xOff, x2 = xOff + xSize - 1;
		int y1 = yOff, y2 = yOff + ySize - 1;

		for(int x = x1; x < x2 + 1; x++) {
			if(inBounds(x, y1)) pixels[x][y1] = pixels[x][y1].blend(color);
			if(inBounds(x, y2)) pixels[x][y2] = pixels[x][y2].blend(color);
		}

		for(int y = y1 + 1; y < y2; y++) {
			if(inBounds(x1, y)) pixels[x1][y] = pixels[x1][y].blend(color);
			if(inBounds(x2, y)) pixels[x2][y] = pixels[x2][y].blend(color);
		}
	}

	/**
	 * Draws a rectangle.
	 *
	 * @param xOff Horizontal offset
	 * @param yOff Vertical offset
	 * @param xSize Horizontal size
	 * @param ySize Vertical size
	 * @param color Draw color
	 */
	public void fillRect(int xOff, int yOff, int xSize, int ySize, Color color) {
		for(int x = xOff; x < xOff + xSize; x++) {
			if(x >= width) break;
			for(int y = yOff; y < yOff + ySize; y++) {
				if(y >= height) break;
				if(inBounds(x, y)) pixels[x][y] = pixels[x][y].blend(color);
			}
		}
	}

	/**
	 * Converts this Renderer to a BufferedImage.
	 *
	 * @return Renderer as BufferedImage, scaled by previously defined
	 * {@code scale} value.
	 */
	public BufferedImage asImage() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				image.setRGB(x, y, pixels[x][y].intValue());
			}
		}

		return image;
	}
}