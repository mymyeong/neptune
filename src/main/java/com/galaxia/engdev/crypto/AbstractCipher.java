package com.galaxia.engdev.crypto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractCipher {

	@Getter
	@Setter
	protected byte[] key;

	@Getter
	@Setter
	protected byte[] iv;

	public abstract byte[] encrypt(byte[] data) throws Exception;

	public abstract byte[] decrypt(byte[] data) throws Exception;
}