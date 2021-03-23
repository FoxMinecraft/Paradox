package com.loohp.limbo.events.Player;

import com.loohp.limbo.player.Player;

public class PlayerQuitEvent extends PlayerEvent {

    public PlayerQuitEvent(Player player) {
        super(player);
    }

}
