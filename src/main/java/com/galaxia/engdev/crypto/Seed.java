package com.galaxia.engdev.crypto;

import com.galaxia.engdev.crypto.inc.KISA_SEED_CBC;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seed implements NeptuneCipher {

	private byte[] key;
	private byte[] iv;

	@Override
	public byte[] encrypt(byte[] data) throws Exception {
		byte[] encryptedMessage = KISA_SEED_CBC.SEED_CBC_Encrypt(key, iv, data, 0, data.length);
		return encryptedMessage;
	}

	@Override
	public byte[] decrypt(byte[] data) throws Exception {
		byte[] decryptedMessage = KISA_SEED_CBC.SEED_CBC_Decrypt(key, iv, data, 0, data.length);
		return decryptedMessage;
	}

}
