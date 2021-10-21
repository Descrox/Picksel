package com.picksel.asset;

import java.util.List;
import java.io.*;

import com.picksel.component.Component;
import com.picksel.scene.Scene;
import com.picksel.util.Data;
import com.picksel.util.exception.*;

/**
 * Represents a generic Asset.
 *
 * @author Noah James Rathman
 */
public class Asset {
	/**
	 * The recommended maximum file size for an asset,
	 * equivalent to {@code 5} megabytes.
	 */
	public static final long MAX_SIZE = (long) (5E6);

	/**
	 * Writes the passed AssetWarning to the console.
	 *
	 * @param warning Warning to be written
	 */
	protected static void warn(AssetWarning warning) {
		warning.printStackTrace();
	}

	/**
	 * Writes Object data to the specified path.<br>
	 *
	 * <b>Note:</b> File etension will always be
	 * {@code .pdata}.
	 *
	 * @param path Path of data file <i>(without extension!)</i>
	 * @param data Object as data
	 */
	public static void writeObjectData(String path, Data data) {
		File dataFile = new File(path + ".pdata");

		try {
			dataFile.createNewFile();

			FileWriter writer = new FileWriter(dataFile);
			List<String> dataList = data.writeData();

			for(int i = 0; i < dataList.size(); i++) {
				String dataEntry = dataList.get(i);

				if(i + 1 < dataList.size()) {
					dataEntry += "\n";
				}

				writer.write(dataEntry);
			}

			writer.close();
		} catch(IOException e) {
			throw new AssetException("Could not write Object data: " + e.getMessage());
		}
	}

	//Class

	/** File this Asset uses. */
	protected File file;

	/**
	 * Creates a new Asset with the assigned file. 
	 * If the passed file doesn't exist, an
	 * {@link com.picksel.util.exception.AssetException}
	 * is thrown.
	 *
	 * @param file File this Asset uses
	 */
	protected Asset(File file) {
		this.file = file;

		if(!file.exists()) {
			throw new AssetException("The file \"" + file + "\" does not exist.");
		} else if(file.length() >= MAX_SIZE) {
			warn(new AssetWarning(
				"\"" + file + "\" is larger than 5 megabytes. " +
				"Using this file may cause high memory usage."
			));
		}
	}

	/**
	 * Gets the file used by this Asset.
	 *
	 * @return File this Asset uses
	 */
	public File getFile() {
		return file;
	}
}