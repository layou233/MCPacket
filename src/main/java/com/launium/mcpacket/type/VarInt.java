package com.launium.mcpacket.type;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.LinkedList;

public class VarInt {
    public int value;

    public VarInt(int i) {
        this.value = i;
    }

    public int[] marshal() {
        IntBuffer result = IntBuffer.allocate(5);
        int i = this.value;
        int x = 1;
        while ((i & ~0x7F) != 0) {
            result.put((i & 0x7F) | 0x80);
            i >>>= 7;
            x += 1;
        }
        result.put(i);
        return Arrays.copyOf(result.array(), x);
    }

    public static int readFrom(InputStream in) throws IOException {
        int value = 0, length = 0;
        int currentByte;
        do {
            currentByte = in.read();
            value |= (currentByte & 0x7F) << (length * 7);

            if (++length > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((currentByte & 0x80) == 0x80);
        return value;
    }

    public static int readFrom(LinkedList<Integer> list,int index) {
        int value = 0, length = 0;
        int currentByte;
        do {
            currentByte = index==0?list.removeFirst():list.remove(index);
            value |= (currentByte & 0x7F) << (length * 7);

            if (++length > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((currentByte & 0x80) == 0x80);
        return value;
    }

    public static int readFrom(int[] bytes) {
        int value = 0, length = 0;
        int currentByte;
        do {
            currentByte = bytes[length];
            value |= (currentByte & 0x7F) << (length * 7);

            if (++length > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((currentByte & 0x80) == 0x80);
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    public String toString(int radix) {
        return Integer.toString(this.value, radix);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }
}
