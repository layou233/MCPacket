package com.launium.mcpacket.type;

import java.util.Arrays;

public class VarLongTest {
    public static void main(String[] args) {
        VarLong i = new VarLong(9223372036854775807L);
        System.out.println(Arrays.toString(i.marshal()));
        System.out.println(VarLong.readFrom(i.marshal()));

        i = new VarLong(-9223372036854775808L);
        System.out.println(Arrays.toString(i.marshal()));
        System.out.println(VarLong.readFrom(i.marshal()));

        i = new VarLong(-1L);
        System.out.println(Arrays.toString(i.marshal()));
        System.out.println(VarLong.readFrom(i.marshal()));
    }
}
