package com.launium.mcpacket.connection.crypto;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;

public class SymmetricInputStream extends InputStream {
    public InputStream origin;
    public Cipher decrypt;

    public SymmetricInputStream(InputStream origin, Cipher decrypt) {
        super();
        this.origin = origin;
        this.decrypt = decrypt;
    }

    @Override
    public int read() throws IOException {
        return decrypt.update(new byte[]{(byte) origin.read()})[0];
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return decrypt.update(origin.readNBytes(len));
    }

    @Override
    public void close() throws IOException {
        super.close();
        origin.close();
    }
}
