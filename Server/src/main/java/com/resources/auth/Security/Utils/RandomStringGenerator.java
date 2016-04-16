package com.resources.auth.Security.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Util class for password generation
 */
public class RandomStringGenerator {
    private RandomStringGenerator() {
    }

    private static final Logger logger = LoggerFactory.getLogger(RandomStringGenerator.class);

    private static  Random random = new Random();

    public static String generateString(int lengthOfString) {
        StringBuilder sb = new StringBuilder();
        String randAlph = "qwertyuiop[]asdfghjklzxcvbnm/QWERTYUIOP[]ASDFGHJKLZXCVBNM1234567890-!@#$%^&*()";

        for(int i = 0; i < lengthOfString; i++) {
            char c = randAlph.charAt(random.nextInt(randAlph.length()));
            sb.append(c);
        }
        logger.trace("New random string was created {}", sb.toString());
        return sb.toString();

    }
}
