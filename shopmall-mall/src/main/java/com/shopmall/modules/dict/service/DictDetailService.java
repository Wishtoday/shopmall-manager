package com.shopmall.modules.dict.service;

import com.shopmall.modules.dict.domain.DictDetail;
import com.shopmall.modules.dict.dto.DictDetailDto;
import com.shopmall.modules.dict.dto.DictDetailQueryCriteria;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiying
 * @date 2023-12-12
 */
public interface DictDetailService  extends BaseService <DictDetail> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<DictDetailDto>
    */
    List<DictDetail> queryAll(DictDetailQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <DictDetailDto> all, HttpServletResponse response) throws IOException;


    /**
     * 按名称查询字典值返回label
     *
     * @param dictName dict类型名称
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> queryDetailsByName(String dictName);

    /**
     * 按名称查询字典值返回key
     *
     * @param dictName dict类型名称
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> queryDetailsByKey(String dictName);

}
