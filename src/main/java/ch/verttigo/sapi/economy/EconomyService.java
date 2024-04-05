package ch.verttigo.sapi.economy;

import ch.verttigo.sapi.SAPI;
import ch.verttigo.sapi.manager.user.SUser;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class EconomyService implements Economy {
    public boolean isEnabled() {
        return SAPI.getInstance().isEnabled();
    }

    public String getName() {
        return "SAPI-Economy";
    }

    public boolean hasBankSupport() {
        return false;
    }

    public int fractionalDigits() {
        return 0;
    }

    public String format(double amount) {
        return String.valueOf(amount);
    }

    public String currencyNamePlural() {
        return "Coins";
    }

    public String currencyNameSingular() {
        return "Coin";
    }

    public double getBalance(UUID uuid) {
        return new SUser(uuid).getCoins();
    }

    public boolean has(UUID uuid, double amount) {
        return getBalance(uuid) > amount;
    }


    public EconomyResponse withdrawPlayer(UUID uuid, double amount) {
        SUser su = new SUser(uuid);
        double deduct = su.getCoins() - amount;
        if (deduct >= 0.0) {
            return new EconomyResponse(0.0D, su.getCoins(), ResponseType.FAILURE, "The value is more than the player's balance!");
        } else {
            su.setCoins((int) deduct);
            return new EconomyResponse(amount, su.getCoins(), ResponseType.SUCCESS, "");
        }
    }


    public EconomyResponse depositPlayer(UUID uuid, double amount) {
        SUser su = new SUser(uuid);
        su.setCoins((int) (su.getCoins() + amount));
        return new EconomyResponse(amount, 0.0D, ResponseType.SUCCESS, "");
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public double getBalance(String s) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return 0;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public double getBalance(String s, String s1) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return 0;
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public boolean has(String s, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }


}