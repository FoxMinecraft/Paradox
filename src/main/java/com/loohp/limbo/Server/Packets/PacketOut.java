package com.loohp.limbo.server.Packets;

import java.io.IOException;

public abstract class PacketOut extends Packet {

    public abstract byte[] serializePacket() throws IOException;

}
