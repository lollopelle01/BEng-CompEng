package utils;

import java.io.IOException;

public class SharedWSSessionManager extends WSSessionManager {
	private static final SharedWSSessionManager instance = new SharedWSSessionManager();

	private SharedWSSessionManager() {
		// Private constructor to prevent instantiation
	}

	public static SharedWSSessionManager getInstance() {
		return instance;
	}
}
