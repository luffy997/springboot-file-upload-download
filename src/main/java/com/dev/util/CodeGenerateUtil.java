package com.dev.util;

import java.security.SecureRandom;
import java.util.Random;

public class CodeGenerateUtil {
    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();


    /**
     * 生成随机数
     * @param n 生成几位随机数
     * @returnn
     */
    public static String generateVerCode(int n) {
        char[] nonceChars = new char[n];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}
