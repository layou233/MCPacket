package com.launium.mcpacket.packet;

import com.launium.mcpacket.Packet;
import com.launium.mcpacket.exception.PacketSizeOutOfBoundsException;
import com.launium.mcpacket.type.VarInt;
import com.launium.mcpacket.type.VarLong;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.UUID;

public class CommonPacket implements Packet {
    public int id;
    private final LinkedList<Integer> data;

    public CommonPacket(int id) {
        this.id = id;
        this.data = new LinkedList<>();
    }

    @Override
    public int read() {
        return data.remove();
    }

    @Override
    public int[] readUnsignedBytes(int n) throws PacketSizeOutOfBoundsException {
        if (data.size() < n) {
            throw new PacketSizeOutOfBoundsException(n);
        }
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = data.remove();
        }
        return result;
    }

    public byte[] readBytes(int n) {
        if (data.size() < n) {
            throw new PacketSizeOutOfBoundsException(n);
        }
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = (byte) data.remove().intValue();
        }
        return result;
    }

    @Override
    public int readInt() {
        return read();
    }

    @Override
    public int readVarInt() {
        return VarInt.readFrom(data, 0);
    }

    @Override
    public long readLong() {
        byte[] read = readBytes(8);
        return ((long) read[0] << 56)
                + ((long) (read[1] & 255) << 48)
                + ((long) (read[2] & 255) << 40)
                + ((long) (read[3] & 255) << 32)
                + ((long) (read[4] & 255) << 24)
                + ((read[5] & 255) << 16)
                + ((read[6] & 255) << 8)
                + ((read[7] & 255));
    }

    @Override
    public long readVarLong() {
        return VarLong.readFrom(data, 0);
    }

    @Override
    public short readShort() {
        return (short) read();
    }

    @Override
    public boolean readBoolean() {
        return read() == 1;
    }

    @Override
    public int readUnsignedShort() {
        int ch1 = read();
        int ch2 = read();
        return (ch1 << 8) + (ch2);
    }

    @Override
    public String readString() {
        int length = readVarInt();
        return new String(readBytes(length), StandardCharsets.UTF_8);
    }

    @Override
    public UUID readUUID() {
        long mostSignificantBits = readLong();
        long leastSignificantBits = readLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    @Override
    public void write(int i) {
        data.add(i);
    }

    @Override
    public void write(VarInt i) {
        writeBytes(i.marshal());
    }

    @Override
    public void write(byte b) {
        data.add((int) b);
    }

    @Override
    public void write(byte[] bytes) {
        for (byte aByte : bytes) {
            write(aByte);
        }
    }

    @Override
    public void write(boolean b) {
        write(b ? 1 : 0);
    }

    @Override
    public void write(short s) {
        write((int) s);
    }

    @Override
    public void writeUnsignedShort(int s) {
        write((byte) ((s >>> 8) & 0xFF));
        write((byte) ((s) & 0xFF));
    }

    @Override
    public void write(long l) {
        write((byte) (l >>> 56));
        write((byte) (l >>> 48));
        write((byte) (l >>> 40));
        write((byte) (l >>> 32));
        write((byte) (l >>> 24));
        write((byte) (l >>> 16));
        write((byte) (l >>> 8));
        write((byte) (l));
    }

    @Override
    public void write(VarLong l) {
        writeBytes(l.marshal());
    }

    @Override
    public void write(String str) throws IllegalArgumentException, IOException {
        if (str == null) {
            throw new IllegalArgumentException("String cannot be null!");
        }
        byte[] bytes=str.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 32767) {
            throw new IOException("String too big (was " + str.length() + " bytes encoded, max " + 32767 + ")");
        }
        write(new VarInt(str.length()));
        write(bytes);
    }

    @Override
    public void writeBytes(int... bytes) {
        for (int aByte : bytes) {
            data.add(aByte);
        }
    }

    @Override
    public void write(UUID uuid) {
        write(uuid.getMostSignificantBits());
        write(uuid.getLeastSignificantBits());
    }
}
