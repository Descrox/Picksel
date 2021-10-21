package com.picksel.util;

import com.picksel.component.Component;

/**
 * Viewport for each Picksel Game. The camera
 * focuses on an assigned Component.
 *
 * @author Noah James Rathman
 */
public final class Camera {
	private static int SCREEN_WIDTH, SCREEN_HEIGHT;

	/**
	 * Initializes the Camera with the passed screen
	 * width and height.
	 *
	 * @param sWidth Width of Game screen
	 * @param sHeight Height of Game screen
	 */
	public static void init(int sWidth, int sHeight) {
		SCREEN_WIDTH = sWidth;
		SCREEN_HEIGHT = sHeight;
	}

	//Class
	private Component focus;
	private int x, y, offX, offY;

	/**
	 * Creates a new Camera.
	 */
	public Camera() {
		focus = null;
		x = 0;
		y = 0;
		offX = 0;
		offY = 0;
	}

	/**
	 * Centers the focus of the Camera.
	 */
	public void update() {
		if(focus != null) {
			offX	= (int) (focus.bounds().getX() - (SCREEN_WIDTH / 2) + (focus.bounds().getWidth() / 2));
			offY	= (int) (focus.bounds().getY() - (SCREEN_HEIGHT / 2) + (focus.bounds().getHeight() / 2));
			x			= (int) (focus.bounds().getX() + (focus.bounds().getWidth() / 2));
			y			= (int) (focus.bounds().getY() + (focus.bounds().getHeight() / 2));
		}
	}

	/**
	 * Sets the focus of this Camera to the passed Component.
	 *
	 * @param focus New Camera focus
	 */
	public void setFocus(Component focus) {
		this.focus = focus;
	}

	/**
	 * Gets this Camera's horizontal offset.
	 *
	 * @return Camera X offset
	 */
	public int getX() {
		return offX;
	}

	/**
	 * Gets this Camera's vertical offset.
	 *
	 * @return Camera Y offset
	 */
	public int getY() {
		return offY;
	}

	/**
	 * Gets the center horizontal position of this Camera's
	 * focus.
	 *
	 * @return Camrera focus X position
	 */
	public int getXIgnoreOffset() {
		return x;
	}

	/**
	 * Gets the center vertical position of this Camera's
	 * focus.
	 *
	 * @return Camrera focus Y position
	 */
	public int getYIgnoreOffset() {
		return y;
	}
}