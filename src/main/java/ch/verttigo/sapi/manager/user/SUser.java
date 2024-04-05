package ch.verttigo.sapi.manager.user;

import ch.verttigo.sapi.cache.lCache;
import ch.verttigo.sapi.rest.ReqUser;
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
    private int coins;
    private final int totalPlayTime;
    private final Date lastLogon;

    public SUser(UUID uuid) {
        JSONObject data = lCache.getUser(uuid);
        this.uuid = uuid;
        this.Nickname = data.getString("Nickname");
        this.paidAccount = data.getBoolean("paidAccount");
        this.firstLogon = null;
        this.lastLogon = null;
        this.totalPlayTime = 0;
        this.coins = data.getInt("Coins");
        this.token = data.getInt("Tokens");
        this.exp = data.getInt("EXP");
        this.group = data.getInt("Group");
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

    public int getCoins() {
        return coins;
    }

    public Boolean setCoins(int amount) {
        JSONObject obj = ReqUser.updateUser(uuid, new JSONObject().put("Coins", amount));
        if (obj != null) {
            coins = obj.getInt("Coins");
            return true;
        }
        return false;
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    public Date getLastLogon() {
        return lastLogon;
    }

}