package com.launium.mcpacket.exception;

public class PacketSizeOutOfBoundsException extends ArrayIndexOutOfBoundsException{
    public PacketSizeOutOfBoundsException(int size) {
        super("Packet size out of range: " + size);
    }
}
