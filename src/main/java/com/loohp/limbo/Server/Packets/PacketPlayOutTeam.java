package com.loohp.limbo.server.packets;

import com.loohp.limbo.entity.Entity;
import com.loohp.limbo.player.Player;
import com.loohp.limbo.plugins.scoreboard.Team;
import com.loohp.limbo.utils.DataTypeIO;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.TextComponentSerializer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PacketPlayOutTeam extends PacketOut {

    private final Team team;
    private final Mode mode;
    private List<Entity> changed;

    public PacketPlayOutTeam(Team team, Mode mode) {
        this.team = team;
        this.mode = mode;
    }

    public PacketPlayOutTeam(Team team, Mode mode, List<Entity> changed) {
        this.team = team;
        this.mode = mode;
        this.changed = changed;
    }

    @Override
    public byte[] serializePacket() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        DataOutputStream output = new DataOutputStream(buffer);

        DataTypeIO.writeString(output, team.getName(), StandardCharsets.UTF_8);

        byte id = mode.id;

        output.writeByte(id);

        if (id == 1) // Remove team
            return buffer.toByteArray();

        if (id == 0) {
            writeData(output);
            writeEntityData(output);
        }

        if (id == 2) {
            writeData(output);
        }

        if (id == 3 || id == 4) {
            writeEntityData(output, changed);
        }


        return buffer.toByteArray();
    }

    private void writeEntityData(DataOutputStream output, List<Entity> list) throws IOException {
        DataTypeIO.writeVarInt(output, list.size());

        for (Entity e : list) {
            if (e instanceof Player)
                DataTypeIO.writeString(output, ((Player) e).getName(), StandardCharsets.UTF_8);
            else
                DataTypeIO.writeString(output, e.getUniqueId().toString(), StandardCharsets.UTF_8);
        }
    }

    private void writeEntityData(DataOutputStream output) throws IOException {
        writeEntityData(output, team.getEntities());
    }

    private void writeData(DataOutputStream output) throws IOException {
        DataTypeIO.writeString(output, chat(team.getDisplayName()), StandardCharsets.UTF_8);
        output.writeByte(getFriendlyFlags());
        DataTypeIO.writeString(output, team.getNameTagVisibilityOption().getValue(), StandardCharsets.UTF_8);
        DataTypeIO.writeString(output, team.getCollisionRuleOption().getValue(), StandardCharsets.UTF_8);
        DataTypeIO.writeVarInt(output, team.getColor().getValue());
        DataTypeIO.writeString(output, chat(team.getPrefix()), StandardCharsets.UTF_8);
        DataTypeIO.writeString(output, chat(team.getSuffix()), StandardCharsets.UTF_8);
    }

    private byte getFriendlyFlags() {
        byte b = 0;

        if (team.isAllowFriendlyFire())
            b |= 0x01;

        if (team.isCanSeeInvisiblePlayers())
            b |= 0x02;

        return b;
    }

    private String chat(String input) {
        TextComponentSerializer serializer = new TextComponentSerializer();

        TextComponent component = new TextComponent();

        for (BaseComponent component1 : TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', input)))
            component.addExtra(component1);

        return serializer.serialize(component, String.class, null).getAsString();
    }

    public enum Mode {
        CREATE_TEAM(0),
        REMOVE_TEAM(1),
        UPDATE_INFO(2),
        ADD_PLAYER(3),
        REMOVE_PLAYER(4);

        byte id;

        Mode(int id) {
            this((byte) id);
        }

        Mode(byte id) {
            this.id = id;
        }
    }
}
