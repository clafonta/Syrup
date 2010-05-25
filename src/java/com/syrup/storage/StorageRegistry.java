package com.syrup.storage;

public class StorageRegistry {
	public static final IStorage SyrupStorage = InMemoryStorage.getInstance();
}
