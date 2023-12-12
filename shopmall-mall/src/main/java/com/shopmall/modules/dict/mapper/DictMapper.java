package com.shopmall.modules.dict.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.dict.domain.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Repository
@Mapper
public interface DictMapper extends CoreMapper <Dict> {

}
