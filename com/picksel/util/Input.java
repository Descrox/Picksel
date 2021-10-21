package com.picksel.util;

import java.awt.event.*;

/**
 * Manages keyboard and mouse input for Picksel Games.
 *
 * @author Noah James Rathman
 */
public final class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	/** Amount of keys Input supports. */
	public static final int KEY_SIZE = 128;
	/** Amount of buttons Input supports. */
	public static final int BTN_SIZE = 6;

	//Class
	private final float SCALE;
	private boolean[] keys, keysLast, btns, btnsLast;
	private int mX, mY, mXL, mYL, dX, dY, scroll;
	private boolean dragging;

	/**
	 * Creates a new Input.
	 *
	 * @param scale Scale of screen
	 */
	public Input(float scale) {
		SCALE = scale;

		keys			= new boolean[KEY_SIZE];
		keysLast	= new boolean[KEY_SIZE];
		btns			= new boolean[BTN_SIZE];
		btnsLast	= new boolean[BTN_SIZE];

		mX				= 0;			mY				= 0;
		mXL				= 0;			mYL				= 0;
		dX				= 0;			dY				= 0;

		scroll		= 0;
		dragging	= false;
	}

	/**
	 * Places all current-frame input into previous-frame
	 * storage, and resets change values.
	 */
	public void update() {
		for(int i = 0; i < KEY_SIZE; i++) {
			keysLast[i] = keys[i];

			if(i < BTN_SIZE) {
				btnsLast[i] = btns[i];
			}
		}

		mXL = mX;
		mYL = mY;

		scroll		= 0;
		dX				= 0;
		dY				= 0;
	}

	//Input Controls

	/**
	 * Tests if the key associated with the passed code is
	 * being pressed.
	 *
	 * @param code Integer code for tested key
	 * @return {@code True} if the key is being pressed,
	 * {@code false} otherwise.
	 */
	public boolean isKey(int code) {
		return keys[code];
	}

	/**
	 * Tests if the button associated with the passed code
	 * is being pressed.<br>
	 * <b>Note:</b> button code {@code 0} is reserved for
	 * unrecognized button presses.
	 *
	 * @param code Integer code for tested button
	 * @return {@code True} if the button is being pressed,
	 * {@code false} otherwise.
	 */
	public boolean isButton(int code) {
		return btns[code];
	}

	/**
	 * Gets the X axis position of the mouse.
	 *
	 * @return Mouse X position
	 */
	public int getX() {
		return mX;
	}

	/**
	 * Gets the Y axis position of the mouse.
	 *
	 * @return Mouse Y position
	 */
	public int getY() {
		return mY;
	}

	/**
	 * Gets the change in mouse position on the X axis.
	 *
	 * @return Mouse X position change
	 */
	public int getDeltaX() {
		return dX;
	}

	/**
	 * Gets the change in mouse position on the Y axis.
	 *
	 * @return Mouse Y position change
	 */
	public int getDeltaY() {
		return dY;
	}

	/**
	 * Gets the mouse scroll speed.
	 *
	 * @return Mouse scroll
	 */
	public int getScroll() {
		return scroll;
	}

	/**
	 * Tests if the mouse is currently dragging.
	 *
	 * @return {@code True} if dragging, {@code false}
	 * otherwise.
	 */
	public boolean isDragging() {
		return dragging;
	}

	//Key Input
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	//Button Input
	public void mousePressed(MouseEvent e) {
		btns[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		btns[e.getButton()] = false;
	}

	//Motion Input
	public void mouseMoved(MouseEvent e) {
		mX = (int) (e.getX() / SCALE);
		mY = (int) (e.getY() / SCALE);

		dX = mX - mXL;
		dY = mY - mYL;

		dragging = false;
	}

	public void mouseDragged(MouseEvent e) {
		mX = (int) (e.getX() / SCALE);
		mY = (int) (e.getY() / SCALE);

		dX = mX - mXL;
		dY = mY - mYL;

		dragging = true;
	}

	//Wheel Input
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getScrollAmount();
	}

	//Unused Input Methods

	/**
	 * @deprecated Input does not use this method.
	 */
	@Deprecated
	public void keyTyped(KeyEvent e) {}

	/**
	 * @deprecated Input does not use this method.
	 */
	@Deprecated
	public void mouseClicked(MouseEvent e) {}

	/**
	 * @deprecated Input does not use this method.
	 */
	@Deprecated
	public void mouseEntered(MouseEvent e) {}

	/**
	 * @deprecated Input does not use this method.
	 */
	@Deprecated
	public void mouseExited(MouseEvent e) {}
}