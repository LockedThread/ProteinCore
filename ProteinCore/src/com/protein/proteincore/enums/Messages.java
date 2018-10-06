package com.protein.proteincore.enums;

import org.bukkit.ChatColor;

public enum Messages {

    NOT_ONLINE("&c{player} is not online!"),
    GENBUCKET_NO_MONEY("&cYou don't have enough money to {action} this genbucket."),
    GENBUCKET_CANCELLED_PROCESS("&eYou've &ccancelled &ea genbucket process @ {loc}"),
    GENBUCKET_PURCHASED("&aYou have purchased a {genbucket} GenBucket!"),
    GENBUCKET_PLACED("&c&l-{money}"),
    ;

    public String val;

    Messages(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', val);
    }

    public String getKey() {
        return name().toLowerCase().replace("_", "-");
    }

}
