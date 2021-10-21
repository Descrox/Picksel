package com.picksel.component;

import com.picksel.component.property.Property;
import com.picksel.util.Input;

/**
 * Allows Objects to define how they are updated each frame.
 *
 * @author Noah James Rathman
 */
public interface Component {

	/**
	 * Adds the passed property to this Component.
	 *
	 * @param p New Component Property
	 */
	void addProperty(Property p);

	/**
	 * Gets the ID of this Component.
	 *
	 * @return Component String ID
	 */
	String id();

	/**
	 * Gets the bounding box of this Component.
	 *
	 * @return Component bounding box
	 */
	Bounds bounds();

	/**
	 * Updates this Component for the current frame.
	 *
	 * @param dt The amount of time in seconds the last frame
	 * took to finish
	 * @param in User input found this frame
	 */
	void update(float dt, Input in);
}