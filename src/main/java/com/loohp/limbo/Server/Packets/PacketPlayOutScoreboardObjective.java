package com.loohp.limbo.server.packets;

import com.loohp.limbo.plugins.scoreboard.Objective;
import com.loohp.limbo.utils.DataTypeIO;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.TextComponentSerializer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketPlayOutScoreboardObjective extends PacketOut {

    private final Objective objective;
    private final Mode mode;

    public PacketPlayOutScoreboardObjective(Objective objective, Mode mode) {
        this.objective = objective;
        this.mode = mode;
    }

    @Override
    public byte[] serializePacket() throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        DataOutputStream output = new DataOutputStream(buffer);

        DataTypeIO.writeString(output, objective.getName(), StandardCharsets.UTF_8);

        int mode = this.mode.getValue();

        output.writeByte(mode);

        if (mode == 1)
            return buffer.toByteArray();

        DataTypeIO.writeString(output, chat(objective.getValue()), StandardCharsets.UTF_8);
        DataTypeIO.writeVarInt(output, objective.getType().getValue());

        return buffer.toByteArray();
    }

    private String chat(String input) {
        TextComponentSerializer serializer = new TextComponentSerializer();

        TextComponent component = new TextComponent();

        for (BaseComponent component1 : TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', input)))
            component.addExtra(component1);

        return serializer.serialize(component, String.class, null).getAsString();
    }

    public enum Mode {
        CREATE_SCOREBOARD(0),
        REMOVE_SCOREBOARD(1),
        UPDATE_TEXT(2);

        private final int value;

        Mode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
