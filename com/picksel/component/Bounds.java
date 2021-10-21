package com.picksel.component;

/**
 * Contains information abound Component boundaries.
 *
 * @author Noah James Rathman
 */
public class Bounds {
	private float x, y, width, height;

	/**
	 * Creates a new bounding box with its position and size set
	 * to {@code 0}.
	 */
	public Bounds() {
		this(0, 0, 0, 0);
	}

	/**
	 * Creates a new bounding box.
	 *
	 * @param x Horizontal position
	 * @param y Vertical position
	 * @param width Horizontal size
	 * @param height Vertical size
	 */
	public Bounds(float x, float y, float width, float height) {
		this.x			= x;
		this.y			= y;
		this.width	= width;
		this.height = height;
	}

	/**
	 * Moves this bounding box by the specified lengths.
	 *
	 * @param x Pixels moved in the X direction
	 * @param y Pixels moved in the Y direction
	 */
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Scales this bounding box by thie specified sizes.
	 *
	 * @param x Amount scaled in the X direction
	 * @param y Amount scaled in the Y direction
	 */
	public void scale(float x, float y) {
		width *= x;
		height *= y;
	}

	/**
	 * Sets the position of this bounding box.
	 *
	 * @param x Horizontal position
	 * @param y Vertical position
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the size of this bounding box.
	 *
	 * @param width Horizontal size
	 * @param height Vertical size
	 */
	public void setSize(float width, float height) {
		this.width	= width;
		this.height = height;
	}

	/**
	 * Tests if this bounding box contains the passed point.
	 *
	 * @param x Point horizontal position
	 * @param y Point vertical position
	 * @return {@code True} if this bounding box contains
	 * this passed point, {@code false} otherwise.
	 */
	public boolean contains(float x, float y) {
		return x >= this.x &&
					 x < this.x + width &&
					 y >= this.y &&
					 y < this.y + height;
	}

	/**
	 * Tests if this bounding box intersects the passed bounding box.
	 *
	 * @param other Other Bounds
	 * @return {@code True} if this bounding box intersects the
	 * passed bounding box, {@code false} otherwise.
	 */
	public boolean intersects(Bounds other) {
		float x1 = other.getX(), x2 = x1 + other.getWidth();
		float y1 = other.getY(), y2 = y1 + other.getHeight();

		return contains(x1, y1) ||
					 contains(x2, y1) ||
					 contains(x1, y2) ||
					 contains(x2, y2);
	}

	/**
	 * Gets the {@code X} position of this Bounds.
	 *
	 * @return Horizontal position
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the {@code Y} position of this Bounds.
	 *
	 * @return Vertical position
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the width of this Bounds.
	 *
	 * @return Horizontal size
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Gets the height of this Bounds.
	 *
	 * @return Vertical size
	 */
	public float getHeight() {
		return height;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + width + ", " + height + ")";
	}
}