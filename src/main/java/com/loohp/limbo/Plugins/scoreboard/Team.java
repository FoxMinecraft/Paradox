package com.loohp.limbo.plugins.scoreboard;

import com.loohp.limbo.entity.Entity;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team {

    private final Scoreboard board;
    private final String name;
    private final List<Entity> entities = new ArrayList<>();
    private String displayName = "";
    private String prefix = "";
    private String suffix = "";
    private boolean allowFriendlyFire = true;
    private boolean canSeeInvisiblePlayers = false;
    private NameTagVisibilityOption nameTagVisibilityOption = NameTagVisibilityOption.ALWAYS;
    private CollisionRuleOption collisionRuleOption = CollisionRuleOption.ALWAYS;
    private TeamColor color = TeamColor.WHITE;

    public Team(Scoreboard board, String name) {
        this.board = board;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isAllowFriendlyFire() {
        return allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean allowFriendlyFire) {
        this.allowFriendlyFire = allowFriendlyFire;
    }

    public boolean isCanSeeInvisiblePlayers() {
        return canSeeInvisiblePlayers;
    }

    public void setCanSeeInvisiblePlayers(boolean canSeeInvisiblePlayers) {
        this.canSeeInvisiblePlayers = canSeeInvisiblePlayers;
    }

    public NameTagVisibilityOption getNameTagVisibilityOption() {
        return nameTagVisibilityOption;
    }

    public void setNameTagVisibilityOption(NameTagVisibilityOption nameTagVisibilityOption) {
        this.nameTagVisibilityOption = nameTagVisibilityOption;
    }

    public CollisionRuleOption getCollisionRuleOption() {
        return collisionRuleOption;
    }

    public void setCollisionRuleOption(CollisionRuleOption collisionRuleOption) {
        this.collisionRuleOption = collisionRuleOption;
    }

    public TeamColor getColor() {
        return color;
    }

    public void setColor(TeamColor color) {
        this.color = color;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity... entity) {
        List<Entity> list = Arrays.asList(entity);

        entities.addAll(list);
        board.addEntities(this, list);
    }

    public void removeEntity(Entity... entity) {
        for (Entity e : entity)
            entities.remove(e);

        board.removeEntities(this, Arrays.asList(entity));
    }

    public enum NameTagVisibilityOption {
        ALWAYS("always"),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam"),
        NEVER("never");

        String value;

        NameTagVisibilityOption(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CollisionRuleOption {
        ALWAYS("always"),
        PUSH_OTHER_TEAMS("pushOtherTeams"),
        PUSH_OWN_TEAM("pushOwnTeam"),
        NEVER("never");

        String value;

        CollisionRuleOption(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum TeamColor {
        BLACK(0),
        DARK_BLUE(1),
        DARK_GREEN(2),
        DARK_CYAN(3),
        DARK_RED(4),
        PURPLE(5),
        GOLD(6),
        GRAY(7),
        DARK_GRAY(8),
        BLUE(9),
        GREEN(10),
        CYAN(11),
        RED(12),
        PINK(13),
        YELLOW(14),
        WHITE(15),

        OBFUSCATED(16),
        BOLD(17),
        STRIKETHROUGH(18),
        UNDERLINE(19),
        ITALIC(20),

        RESET(21);

        int value;

        TeamColor(int value) {
            this.value = value;
        }

        public static TeamColor fromChatColor(ChatColor color) {
            return valueOf(color.getName());
        }

        public int getValue() {
            return value;
        }
    }


}
