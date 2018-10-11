package com.protein.proteincore.enums;

import org.bukkit.ChatColor;

public enum Messages {

    NOT_ONLINE("&c{player} is not online!"),
    GENBUCKET_NO_MONEY("&cYou don't have enough money to {action} this genbucket."),
    GENBUCKET_CANCELLED_PROCESS("&eYou've &ccancelled &ea genbucket process @ {loc}"),
    GENBUCKET_PURCHASED("&aYou have purchased a {genbucket} GenBucket!"),
    GENBUCKET_PLACED("&c&l-{money}"),

    MUST_BE_POSITVE("&c&l(!) &c{num} must be positive number."),
    CANT_EDIT("&c&l(!) &cYou can't edit terrain here."),

    GRACE_PERIOD_DENY("&c&l(!) &cYou can't do this while the Grace Period is active.");

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
