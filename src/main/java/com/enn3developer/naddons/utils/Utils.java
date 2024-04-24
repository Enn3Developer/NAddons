package com.enn3developer.naddons.utils;

public class Utils {
    public static int parsePort(String port) {
        try {
            return Integer.parseInt(port.trim());
        } catch (Exception e) {
            return 25565;
        }
    }
}
