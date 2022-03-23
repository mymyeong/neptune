package com.galaxia.engdev.crypto;

public interface NeptuneCipher {

	public abstract byte[] encrypt(byte[] data) throws Exception;

	public abstract byte[] decrypt(byte[] data) throws Exception;
}