package com.shopmall.modules.system.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.system.domain.UserAvatar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author zhuxiying
* @date 2020-05-14
*/
@Repository
@Mapper
public interface UserAvatarMapper extends CoreMapper <UserAvatar> {

}
