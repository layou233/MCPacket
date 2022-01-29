package com.launium.mcpacket.type;

import java.util.Arrays;

public class VarIntTest {
    public static void main(String[] args) {
        VarInt i = new VarInt(2147483647);
        System.out.println(Arrays.toString(i.marshal()));
        System.out.println(VarInt.readFrom(i.marshal()));

        i = new VarInt(-2147483647);
        System.out.println(Arrays.toString(i.marshal()));
        System.out.println(VarInt.readFrom(i.marshal()));

        i = new VarInt(25565);
        System.out.println(Arrays.toString(i.marshal()));
        System.out.println(VarInt.readFrom(i.marshal()));
    }
}
