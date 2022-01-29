package com.launium.mcpacket.type;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.LinkedList;

public class VarLong {
    public long value;

    public VarLong(long l) {
        this.value = l;
    }

    public int[] marshal() {
        IntBuffer result = IntBuffer.allocate(10);
        long i = this.value;
        int x = 1;
        while ((i & ~0x7F) != 0) {
            result.put((int) ((i & 0x7F) | 0x80));
            i >>>= 7;
            x += 1;
        }
        result.put((int) i);
        return Arrays.copyOf(result.array(), x);
    }

    public static long readFrom(InputStream in) throws IOException {
        long value = 0;
        int length = 0;
        int currentByte;
        do {
            currentByte = in.read();
            value |= (long) (currentByte & 0x7F) << (length * 7);

            if (++length > 10) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((currentByte & 0x80) == 0x80);
        return value;
    }

    public static long readFrom(LinkedList<Integer> list,int index) {
        long value = 0;
        int length = 0;
        int currentByte;
        do {
            currentByte = index==0?list.removeFirst():list.remove(index);
            value |= (long) (currentByte & 0x7F) << (length * 7);

            if (++length > 10) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((currentByte & 0x80) == 0x80);
        return value;
    }

    public static long readFrom(int[] bytes) {
        long value = 0;
        int length = 0;
        int currentByte;
        do {
            currentByte = bytes[length];
            value |= (long) (currentByte & 0x7F) << (length * 7);

            if (++length > 10) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((currentByte & 0x80) == 0x80);
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(this.value);
    }

    public String toString(int radix) {
        return Long.toString(this.value, radix);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }
}
