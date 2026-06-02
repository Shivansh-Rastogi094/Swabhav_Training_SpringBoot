package com.monocept.Exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(long id) {
		super("Requested department not found with id:"+id);
	}
}
