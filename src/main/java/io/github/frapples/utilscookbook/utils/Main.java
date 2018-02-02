package io.github.frapples.utilscookbook.utils;


import java.util.Date;

public class Main {

    public static void main(String[] args) {
        String salt = SecureUtils.randomSalt();
        System.out.println(salt);

        System.out.println(DatetimeUtils.timestamp());

    }
}
