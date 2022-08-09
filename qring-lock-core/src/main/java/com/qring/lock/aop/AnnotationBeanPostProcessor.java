package com.qring.lock.aop;

import com.qring.lock.LockFailureStrategy;
import com.qring.lock.LockTemplate;
import lombok.AllArgsConstructor;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.Ordered;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/9 13:51
 * @Version 1.0
 */
@AllArgsConstructor
public class AnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
    private LockTemplate lockTemplate;
    private LockFailureStrategy lockFailureStrategy;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        LockInterceptor lockInterceptor = new LockInterceptor(lockTemplate, lockFailureStrategy);
        LockAnnotationAdvisor advisor = new LockAnnotationAdvisor(lockInterceptor, Ordered.HIGHEST_PRECEDENCE);
        this.advisor = advisor;
    }
}
