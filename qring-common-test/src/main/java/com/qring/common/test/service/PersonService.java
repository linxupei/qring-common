package com.qring.common.test.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qring.common.test.repository.mapper.PersonMapper;
import com.qring.common.test.repository.model.entity.PersonDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scala.util.control.Exception;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/21 17:00
 * @Version 1.0
 */
@Service
public class PersonService extends ServiceImpl<PersonMapper, PersonDO> {

    @Transactional(rollbackFor = Exception.class)
    public void saveTest() {
        PersonDO personDO = new PersonDO();
        personDO.setAge(1);
        this.save(personDO);
    }
}
