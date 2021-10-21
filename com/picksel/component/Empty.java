package com.picksel.component;

import com.picksel.component.property.Property;
import com.picksel.util.Input;

/**
 * A component which can't have properties or be
 * rendered, but holds a position on screen.
 *
 * @author Noah James Rathman
 */
public final class Empty implements Component {
	private final String ID;
	private Bounds bounds = new Bounds() {
		/**
		 * @param x Amount scaled in the X direction
	 	 * @param y Amount scaled in the Y direction
		 * @deprecated Empties should not have a scale.
		 */
		@Deprecated
		@Override
		public void scale(float x, float y) {}

		/**
		 * @param width Horizontal size
	 	 * @param height Vertical size
		 * @deprecated Empties should not have a scale.
		 */
		@Deprecated
		@Override
		public void setSize(float width, float height) {}
	};

	/**
	 * Creates a new Empty.<br>
	 *
	 * <b>Note:</b> Emptys have special defined Bounds which
	 * deprecate their size.
	 *
	 * @param id Identifier for this Component
	 * @param x Horizontal position
	 * @param y Vertical position
	 */
	public Empty(String id, int x, int y) {
		ID = id;
		bounds.setPosition(x, y);
	}

	/**
	 * @param p New Component Property
	 * @deprecated Empties currently have no use for
	 * Properties.
	 */
	@Deprecated
	public void addProperty(Property p) {}

	/**
	 * <b>Note:</b> Emptys will always do nothing when
	 * updating.
	 *
	 * @param dt The amount of time in seconds the last frame
	 * took to finish
	 * @param in User input found this frame
	 */
	public void update(float dt, Input in) {}

	public String id() {
		return ID;
	}

	public Bounds bounds() {
		return bounds;
	}
}