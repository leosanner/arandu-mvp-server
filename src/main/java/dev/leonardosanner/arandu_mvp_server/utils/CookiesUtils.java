package dev.leonardosanner.arandu_mvp_server.utils;

import java.util.HashMap;
import java.util.Map;

public class CookiesUtils {

    public static Map<String, Object> turnCookieInObject(String cookie) {
        HashMap<String, Object> hash = new HashMap<>();

        for (String s : cookie.split(";")) {
            String[] strings = s.split("=");

            if (strings.length > 1) {
                hash.put(strings[0], strings[1]);
            } else {
                hash.put(strings[0], null);
            }
        }

        return hash;
    }
}
