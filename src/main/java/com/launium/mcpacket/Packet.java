package com.launium.mcpacket;

import com.launium.mcpacket.exception.PacketSizeOutOfBoundsException;
import com.launium.mcpacket.type.VarInt;
import com.launium.mcpacket.type.VarLong;

import java.io.IOException;
import java.util.UUID;

public interface Packet {
    int id = 0;

    int read();

    int[] readUnsignedBytes(int n);

    byte[] readBytes(int n) throws PacketSizeOutOfBoundsException;

    @Deprecated
    int readInt();

    int readVarInt();

    long readLong();

    long readVarLong();

    short readShort();

    boolean readBoolean();

    int readUnsignedShort();

    String readString();

    UUID readUUID();

    void write(int i);

    void write(VarInt i);

    void write(byte b);

    void write(byte[] bytes);

    void write(boolean b);

    void write(short s);

    void write(long l);

    void write(VarLong l);

    void write(UUID uuid);

    void write(String str) throws IllegalArgumentException,IOException;

    void writeUnsignedShort(int s);

    void writeBytes(int... bytes);
}
