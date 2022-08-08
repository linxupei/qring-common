package com.qring.common.base.annotation.qring;

import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/8 17:00
 * @Version 1.0
 */
public class QringAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private final Advice advice;

    private final Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(Qring.class);

    public QringAnnotationAdvisor(@NonNull QringInterceptor interceptor, int order) {
        this.advice = interceptor;
        setOrder(order);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}
