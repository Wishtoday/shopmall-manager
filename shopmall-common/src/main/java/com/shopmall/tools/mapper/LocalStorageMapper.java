package com.shopmall.tools.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.tools.domain.LocalStorage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Repository
@Mapper
public interface LocalStorageMapper extends CoreMapper <LocalStorage> {

}
