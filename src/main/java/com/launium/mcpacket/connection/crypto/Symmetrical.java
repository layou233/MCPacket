package com.launium.mcpacket.connection.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Symmetrical {
    public static SecretKey generateMinecraftStyledSharedSecret() {
        try {
            KeyGenerator kenGen = KeyGenerator.getInstance("AES");
            kenGen.init(128);
            return kenGen.generateKey();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return null;
    }

    public static Cipher getCipher(int mode, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(mode, key);
            return cipher;
        } catch (GeneralSecurityException ignored) {
        }
        return null;
    }
}
