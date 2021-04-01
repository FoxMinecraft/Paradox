package com.loohp.limbo.plugins.scoreboard;

import com.loohp.limbo.entity.Entity;
import com.loohp.limbo.player.Player;
import com.loohp.limbo.server.packets.PacketOut;
import com.loohp.limbo.server.packets.PacketPlayOutDisplayScoreboard;
import com.loohp.limbo.server.packets.PacketPlayOutScoreboardObjective;
import com.loohp.limbo.server.packets.PacketPlayOutTeam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scoreboard {

    private final List<Player> watchers = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();
    private final String name;
    private final ScoreboardPosition position;
    private Objective objective;

    public Scoreboard(String name, ScoreboardPosition position) {
        this.name = name;
        this.position = position;
    }

    public Team getTeam(String name) {
        for (Team team : teams)
            if (team.getName().equalsIgnoreCase(name))
                return team;

        return null;
    }

    public void removeTeam(Team team) {
        teams.remove(team);

        PacketPlayOutTeam teamPacket = new PacketPlayOutTeam(team, PacketPlayOutTeam.Mode.REMOVE_TEAM);

        sendPacket(teamPacket);
    }

    public Team createNewTeam(String name) {
        Team team = new Team(this, name);

        PacketPlayOutTeam teamPacket = new PacketPlayOutTeam(team, PacketPlayOutTeam.Mode.CREATE_TEAM);

        sendPacket(teamPacket);

        teams.add(team);
        return team;
    }

    void update(Team team) {
        PacketPlayOutTeam packet = new PacketPlayOutTeam(team, PacketPlayOutTeam.Mode.UPDATE_INFO);

        sendPacket(packet);
    }

    void addEntities(Team team, List<Entity> toAdd) {
        PacketPlayOutTeam packet = new PacketPlayOutTeam(team, PacketPlayOutTeam.Mode.ADD_PLAYER, toAdd);

        sendPacket(packet);
    }

    void removeEntities(Team team, List<Entity> toRemove) {
        PacketPlayOutTeam packet = new PacketPlayOutTeam(team, PacketPlayOutTeam.Mode.REMOVE_PLAYER, toRemove);

        sendPacket(packet);
    }

    public void display(Player... players) {
        watchers.addAll(Arrays.asList(players));

        PacketPlayOutDisplayScoreboard packet = new PacketPlayOutDisplayScoreboard(this);
        PacketPlayOutScoreboardObjective objective = new PacketPlayOutScoreboardObjective(this.objective, PacketPlayOutScoreboardObjective.Mode.CREATE_SCOREBOARD);

        sendPacket(packet);
        sendPacket(objective);

        for (Team team : teams) {
            PacketPlayOutTeam teamPacket = new PacketPlayOutTeam(team, PacketPlayOutTeam.Mode.CREATE_TEAM);

            sendPacket(teamPacket, players);
        }
    }

    public void removeDisplay(Player... players) {
        for (Player p : players)
            watchers.remove(p);

        PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective(objective, PacketPlayOutScoreboardObjective.Mode.REMOVE_SCOREBOARD);

        sendPacket(packet, players);
    }

    private void sendPacket(PacketOut packet, Player... players) {
        for (Player p : players) {
            try {
                p.clientConnection.sendPacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPacket(PacketOut packet) {
        sendPacket(packet, watchers.toArray(new Player[0]));
    }

    public String getName() {
        return name;
    }

    public ScoreboardPosition getPosition() {
        return position;
    }

    public enum ScoreboardPosition {
        LIST(0),
        SIDEBAR(1),
        BELOW_NAME(2),
        TEAM(3);

        private final int value;

        ScoreboardPosition(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
