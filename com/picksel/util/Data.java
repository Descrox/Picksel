package com.picksel.util;

import java.util.List;

/**
 * Allows Objects to be written as a {@code .pdata}
 * file. Functions very similarly to Serialization,
 * however Data has no conversion to bytecode.
 *
 * @author Noah James Rathman
 */
public interface Data {
	/**
	 * Writes the data of this Object into a List of
	 * Strings.
	 *
	 * @return Object data
	 */
	List<String> writeData();

	/**
	 * Reads the passed List of String data.
	 *
	 * @param data Object data
	 */
	void readData(List<String> data);
}