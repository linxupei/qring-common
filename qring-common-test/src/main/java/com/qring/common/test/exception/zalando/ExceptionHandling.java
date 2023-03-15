package com.qring.common.test.exception.zalando;

import com.qring.common.base.exception.BizException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * @Author Qring
 * @Description 业务异常处理
 * @Date 2023/3/8 22:50
 * @Version 1.0
 */
@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return true;
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Problem> handleBusinessErrorException(BizException ex, NativeWebRequest request) {
        return ResponseEntity.status(Status.OK.getStatusCode()).body(Problem.builder()
                .withDetail(ex.getMessage())
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build());
    }
}
