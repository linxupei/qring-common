package com.qring.common.test.common.util;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

/**
 * @Author Qring
 * @Description Totp
 * @Date 2023/3/21 22:31
 * @Version 1.0
 */
@Slf4j
@Component
public class TotpUtil {

    private static final long TIME_STEP = 60 * 5L;
    private static final int PASSWORD_LENGTH = 6;
    private KeyGenerator keyGenerator;
    private TimeBasedOneTimePasswordGenerator totp;

    {
        totp = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(TIME_STEP), PASSWORD_LENGTH);
        try {
            keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
            // SHA-1 and SHA-256 需要 64 字节 (512 位) 的 key; SHA512 需要 128 字节 (1024 位) 的 key
            keyGenerator.init(512);
        } catch (NoSuchAlgorithmException e) {
            log.error("not exist algorithm: {}", e.getLocalizedMessage());
        }
    }

    public String createTotp(Key key, Instant time) throws InvalidKeyException {
        String format = "%0" + PASSWORD_LENGTH + "d";
        return String.format(format, totp.generateOneTimePassword(key, time));
    }

    public Optional<String> createTotp(String keyStr) {
        try {
            return Optional.of(createTotp(decodeKeyFromString(keyStr), Instant.now()));
        } catch (InvalidKeyException e) {
            return Optional.empty();
        }
    }

    public boolean validateTotp(Key key, String code) throws InvalidKeyException {
        return createTotp(key, Instant.now()).equals(code);
    }

    public Key generateKey() {
        return keyGenerator.generateKey();
    }

    public String encodeKey2String(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String encodeKey2String() {
        return encodeKey2String(generateKey());
    }

    public Key decodeKeyFromString(String keyStr) {
        return new SecretKeySpec(Base64.getDecoder().decode(keyStr), totp.getAlgorithm());
    }

    public Duration getTimeStep() {
        return totp.getTimeStep();
    }
}














