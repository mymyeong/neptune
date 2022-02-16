package com.galaxia.engdev.crypto;

import com.galaxia.engdev.crypto.inc.KISA_SEED_CBC;

public class Seed extends AbstractCipher {

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
