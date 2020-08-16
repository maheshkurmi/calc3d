package com.calc3d.app.resources;
import java.util.ResourceBundle;

/**
 * Helper class used to get text resources from the properties files.
 * <p>
 * This class is used by all classes to get the appropriate resources.

 */
public final class Messages {
	// change this value to messages_test.properties to test the text translation
	
	/** The resource bundle containing the text resources */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("com.calc3d.app.resources.messages");
	
	/**
	 * Hidden constructor.
	 */
	private Messages() {}
	
	/**
	 * Returns the value of the given key.
	 * @param key the key
	 * @return String the value
	 */
	public static final String getString(String key) {
		return BUNDLE.getString(key);
	}
}
