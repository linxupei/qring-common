package com.qring.common.base.lock.aop;

import lombok.AllArgsConstructor;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/9 13:51
 * @Version 1.0
 */
@AllArgsConstructor
public class AnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
    private final LockAnnotationAdvisor lockAnnotationAdvisor;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.advisor = lockAnnotationAdvisor;
    }
}
