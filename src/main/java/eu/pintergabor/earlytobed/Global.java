package eu.pintergabor.earlytobed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.util.Identifier;


public final class Global {
	// Used for logging and registration
	public static final String MODID = "earlytobed";
	// This logger is used to write text to the console and the log file.
	@SuppressWarnings("unused")
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	/**
	 * Create a mod specific identifier
	 *
	 * @param path Name, as in lang/*.json files without "*.modid." prefix
	 */
	public static Identifier ModIdentifier(String path) {
		return Identifier.of(MODID, path);
	}
}
