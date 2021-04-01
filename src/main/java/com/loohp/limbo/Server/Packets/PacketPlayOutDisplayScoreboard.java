package com.loohp.limbo.server.packets;

import com.loohp.limbo.plugins.scoreboard.Scoreboard;
import com.loohp.limbo.utils.DataTypeIO;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketPlayOutDisplayScoreboard extends PacketOut {

    private final Scoreboard scoreboard;

    public PacketPlayOutDisplayScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public byte[] serializePacket() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        DataOutputStream output = new DataOutputStream(buffer);

        output.writeByte(scoreboard.getPosition().getValue());
        DataTypeIO.writeString(output, scoreboard.getName(), StandardCharsets.UTF_8);

        return buffer.toByteArray();
    }
}
