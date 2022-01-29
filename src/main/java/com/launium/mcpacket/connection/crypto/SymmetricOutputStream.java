package com.launium.mcpacket.connection.crypto;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.OutputStream;

public class SymmetricOutputStream extends OutputStream {
    public OutputStream origin;
    public Cipher encrypt;

    public SymmetricOutputStream(OutputStream origin, Cipher encrypt) {
        super();
        this.origin = origin;
        this.encrypt = encrypt;
    }

    @Override
    public void write(int b) throws IOException {
        origin.write(encrypt.update(new byte[]{(byte) b}));
    }

    @Override
    public void write(byte[] b) throws IOException {
        origin.write(encrypt.update(b));
    }

    @Override
    public void close() throws IOException {
        super.close();
        origin.close();
    }
}
