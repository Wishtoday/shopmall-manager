package com.shopmall.modules.system.service;

import com.shopmall.modules.system.domain.Job;
import com.shopmall.modules.system.dto.JobDto;
import com.shopmall.modules.system.dto.JobQueryCriteria;
import com.shopmall.pagehandle.common.service.BaseService;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiying
 * @date 2023-11-30
 */
public interface JobService  extends BaseService <Job> {

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<JobDto>
    */
    List<Job> queryAll(JobQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List <JobDto> all, HttpServletResponse response) throws IOException;
}
