package com.shopmall.modules.system.service;

import com.shopmall.modules.system.domain.UserAvatar;
import com.shopmall.modules.system.dto.UserAvatarDto;
import com.shopmall.modules.system.dto.UserAvatarQueryCriteria;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author zhuxiying
* @date 2023-12-01
*/
public interface UserAvatarService extends BaseService <UserAvatar> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserAvatarQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<UserAvatarDto>
    */
    List<UserAvatar> queryAll(UserAvatarQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <UserAvatarDto> all, HttpServletResponse response) throws IOException;

    UserAvatar saveFile(UserAvatar userAvatar);
}
