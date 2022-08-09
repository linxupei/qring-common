package com.qring.lock.exception;

/**
 * @Author Qring
 * @Description 锁异常
 * @Date 2022/8/9 10:34
 * @Version 1.0
 */
public class LockException extends RuntimeException {
    public LockException() {

    }

    public LockException(String message) {
        super(message);
    }
}
