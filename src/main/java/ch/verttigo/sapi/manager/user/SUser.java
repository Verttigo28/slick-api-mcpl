package ch.verttigo.sapi.manager.user;

import ch.verttigo.sapi.cache.lCache;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class SUser {
    private final UUID uuid;
    private final String Nickname;
    private final boolean paidAccount;
    private final Date firstLogon;
    private final Object friends;
    private final Object specialPerms;
    private final int group;
    private final int exp;
    private final int token;
    private final int coins;
    private final int totalPlayTime;
    private final Date lastLogon;

    public SUser(UUID uuid) {
        JSONObject data = lCache.getUser(uuid);
        this.uuid = uuid;
        this.Nickname = data.get("Nickname").toString();
        this.paidAccount = (boolean) data.get("paidAccount");
        this.firstLogon = (Date) data.get("firstLogon");
        this.lastLogon = (Date) data.get("lastLogon");
        this.totalPlayTime = (int) data.get("totalPlayTime");
        this.coins = (int) data.get("totalPlayTime");
        this.token = (int) data.get("totalPlayTime");
        this.exp = (int) data.get("totalPlayTime");
        this.group = (int) data.get("totalPlayTime");
        this.specialPerms = null;
        this.friends = null;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {

    }

    public boolean isPaidAccount() {
        return paidAccount;
    }

    public Date getFirstLogon() {
        return firstLogon;
    }

    public Object getFriends() {
        return friends;
    }

    public Object getSpecialPerms() {
        return specialPerms;
    }

    public int getGroup() {
        return group;
    }

    public int getExp() {
        return exp;
    }

    public int getToken() {
        return token;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(Double amount) {
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    public Date getLastLogon() {
        return lastLogon;
    }

}