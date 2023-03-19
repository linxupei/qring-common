package com.qring.common.web.exception;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.qring.common.base.exception.BizException;
import com.qring.common.base.result.CommonResultCode;
import com.qring.common.base.result.ResultDTO;
import com.qring.common.web.i18n.MessageSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @Author Qring
 * @Description 统一异常处理器
 * @Date 2023/3/12 22:24
 * @Version 1.0
 */
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private MessageSourceUtil messageSourceUtil;

    @ExceptionHandler({Exception.class})
    public ResultDTO<?> exceptionHandler(HttpServletRequest request, Exception ex) {
        log.error("sys error, message={}", ex.getMessage(), ex);
        return ResultDTO.fail(CommonResultCode.SYSTEM_ERROR.getCode(),
                getI18nMessage(request, CommonResultCode.SYSTEM_ERROR.getMsg()));
    }

    @ExceptionHandler({BizException.class})
    public ResultDTO<?> bizExceptionHandler(HttpServletRequest request, BizException ex) {
        return ResultDTO.fail(ex.getCode(), getI18nMessage(request, ex.getMessage(), ex.getArgs()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultDTO<?> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> msgList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return getMessageList(request, msgList);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResultDTO<?> constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException ex) {
        List<String> msgList = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return getMessageList(request, msgList);
    }

    @ExceptionHandler({BindException.class})
    public ResultDTO<?> bindExceptionHandler(HttpServletRequest request, BindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> msgList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return getMessageList(request, msgList);
    }

    private ResultDTO<?> getMessageList(HttpServletRequest request, List<String> msgList) {
        List<String> messageList = new ArrayList<>();
        for (String key : msgList) {
            messageList.add(getI18nMessage(request, key));
        }
        return ResultDTO.fail(JSONUtil.toJsonStr(messageList));
    }

    @ExceptionHandler({TypeMismatchException.class})
    public ResultDTO<?> typeMismatchExceptionHandler(HttpServletRequest request, TypeMismatchException ex) {
        log.error("param type error , message={}", ex.getMessage(), ex);
        return ResultDTO.fail(CommonResultCode.ERROR_PARAM.getCode(),
                getI18nMessage(request, CommonResultCode.ERROR_PARAM.getMsg()));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResultDTO<?> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        return ResultDTO.fail(CommonResultCode.REQUEST_METHOD_ERROR.getCode(),
                getI18nMessage(request, CommonResultCode.REQUEST_METHOD_ERROR.getMsg()));
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResultDTO<?> httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        return ResultDTO.fail(CommonResultCode.REQUEST_METHOD_ERROR.getCode(),
                getI18nMessage(request, CommonResultCode.REQUEST_METHOD_ERROR.getMsg()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResultDTO<?> httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException ex) {
        return ResultDTO.fail(CommonResultCode.ILLEGAL_PARAM.getCode(),
                getI18nMessage(request, CommonResultCode.ILLEGAL_PARAM.getMsg()));
    }

    public String getI18nMessage(HttpServletRequest request, String messageResourceKey) {
        if (StrUtil.isBlank(messageResourceKey)) {
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return messageSourceUtil.getMessageByCurrentLocale(messageResourceKey, messageResourceKey, locale);
    }

    public String getI18nMessage(HttpServletRequest request, String messageResourceKey, String[] args) {
        if (args == null || args.length == 0) {
            return getI18nMessage(request, messageResourceKey);
        }
        if (StrUtil.isBlank(messageResourceKey)) {
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        String[] params = Arrays.stream(args).map(param -> {
            if (param != null && param.contains(".")) {
                param = messageSourceUtil.getMessageByCurrentLocale(param, param, locale);
            }
            return param;
        }).toArray(String[]::new);
        return messageSourceUtil.getMessageByCurrentLocale(messageResourceKey, messageResourceKey, locale, params);
    }
}
