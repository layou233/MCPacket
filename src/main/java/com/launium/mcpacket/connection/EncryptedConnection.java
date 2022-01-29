package com.launium.mcpacket.connection;

import com.launium.mcpacket.connection.crypto.SymmetricInputStream;
import com.launium.mcpacket.connection.crypto.SymmetricOutputStream;
import com.launium.mcpacket.connection.crypto.Symmetrical;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.Socket;

public class EncryptedConnection extends Connection {
    public SecretKey key;

    public EncryptedConnection(Socket socket, SecretKey key) throws IOException {
        super(socket);
        this.key = key;
        this.in = new SymmetricInputStream(this.in,
                Symmetrical.getCipher(Cipher.ENCRYPT_MODE, this.key));
        this.out = new SymmetricOutputStream(this.out,
                Symmetrical.getCipher(Cipher.DECRYPT_MODE, this.key));
    }
}
