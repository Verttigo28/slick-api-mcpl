package ch.verttigo.sapi.utils;

import ch.verttigo.sapi.SAPI;

public class Logger {

    public enum LogType {
        info,
        warning,
        severe
    }

    public static void log(LogType lt, String msg) {
        switch (lt) {
            case info -> {
                SAPI.getInstance().getLogger().info(msg);
            }
            case warning -> {
                SAPI.getInstance().getLogger().warning(msg);
            }
            case severe -> {
                SAPI.getInstance().getLogger().severe(msg);
            }
        }
    }


}
