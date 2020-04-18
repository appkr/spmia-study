package dev.appkr.apigateway;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TraceId {

    public static String create() {
        return new BigInteger(1, SecureRandom.getSeed(8)).toString(16);
    }
}
