package com.picksel.util;

import java.lang.annotation.*;

/**
 * Allows easy documentation of additions and
 * revisions to Picksel capabilities.
 *
 * @author Noah James Rathman
 */
@Documented
public @interface VersionInfo {

	/**
	 * Current version when this revision was
	 * written.
	 *
	 * @return Current version
	 */
	String version();

	/**
	 * Date this revision was written.
	 *
	 * @return Date of revision
	 */
	String releaseDate();

	/**
	 * First version this class or function was
	 * introduced.
	 *
	 * @return First introduction of
	 * functionality
	 */
	String since();

	/**
	 * All contributors to this revision.
	 *
	 * @return All revision contributors
	 */
	String[] contributors();
}