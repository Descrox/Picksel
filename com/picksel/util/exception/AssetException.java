package com.picksel.util.exception;

import com.picksel.asset.Asset;

/**
 * Exception thrown when an exception occurs creating
 * a game asset.
 *
 * @author Noah James Rathman
 */
public final class AssetException extends RuntimeException {

	/**
	 * Creates a new AssetException with the passed message.
	 *
	 * @param message Message displayed with Exception
	 */
	public AssetException(String message) {
		super(message);
	}
}