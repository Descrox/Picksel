package com.picksel.component.property;

import com.picksel.component.Component;
import com.picksel.util.Input;

/**
 * Adds extra functionality to Components.
 *
 * @author Noah James Rathman
 */
public abstract class Property {
	/** Component this Property is assigned to. */
	protected final Component parent;

	/**
	 * Creates a new Property with the assigned
	 * parent.
	 *
	 * @param parent Property Component
	 */
	protected Property(Component parent) {
		this.parent = parent;
	}

	/**
	 * Updates this property for the current frame.
	 *
	 * @param dt The amount of time in seconds the last frame
	 * took to finish
	 * @param in User input found this frame
	 */
	public abstract void update(float dt, Input in);

	/**
	 * Gets the parent of this Property.
	 *
	 * @return Property Component
	 */
	public Component getParent() {
		return parent;
	}
}