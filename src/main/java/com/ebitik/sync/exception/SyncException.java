package com.ebitik.sync.exception;

public class SyncException extends RuntimeException {

	private static final long serialVersionUID = 6414322828791793114L;

	public SyncException() {
		super();
	}

	public SyncException(String msg) {
		super(msg);
	}

	public SyncException(Exception e) {
		super(e);
	}

	public SyncException(String msg, Exception e) {
		super(msg, e);
	}
}