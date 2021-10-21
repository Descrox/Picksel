package com.picksel.util.exception;

/**
 * Exception created when an Asset is too large in some
 * way.
 *
 * @author Noah James Rathman
 */
public final class AssetWarning extends Exception {
	/**
	 * Creates a new AssetWarning.
	 *
	 * @param message Message displayed with warning
	 */
	public AssetWarning(String message) {
		super(message);
	}
}