package com.shopmall.modules.system.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.system.domain.Job;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author zhuxiying
* @date 2020-05-14
*/
@Repository
@Mapper
public interface JobMapper extends CoreMapper <Job> {

}
