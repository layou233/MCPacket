package com.launium.mcpacket.connection;

import com.launium.mcpacket.Packet;
import com.launium.mcpacket.packet.CommonPacket;
import com.launium.mcpacket.type.VarInt;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Closeable {
    public Socket socket;
    public InputStream in;
    public OutputStream out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    public Packet readPacket() throws IOException {
        int packetLength = VarInt.readFrom(in);
        Packet packet;
        if (false) { // Compressed
            return null;
        } else { // Uncompressed
            packet = new CommonPacket(VarInt.readFrom(in));
            for (int i = 0; i < packetLength; i++) {
                packet.write(in.read());
            }
        }
        return packet;
    }

    public int[] readBytes(int n) throws IOException {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = in.read();
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }
}
