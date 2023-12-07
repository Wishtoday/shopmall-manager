package com.shopmall.modules.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.shopmall.dozer.service.IGenerator;
import com.shopmall.modules.system.domain.Job;
import com.shopmall.modules.system.dto.JobDto;
import com.shopmall.modules.system.dto.JobQueryCriteria;
import com.shopmall.modules.system.mapper.JobMapper;
import com.shopmall.modules.system.service.DeptService;
import com.shopmall.modules.system.service.JobService;
import com.shopmall.pagehandle.common.service.impl.BaseServiceImpl;
import com.shopmall.pagehandle.utils.QueryHelpPlus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhuxiying
 * @date 2023-11-30
 */
@SuppressWarnings("unchecked")
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseServiceImpl <JobMapper, Job> implements JobService {

    private final IGenerator generator;

    private final DeptService deptService;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo <Job> page = new PageInfo <>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), JobDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<Job> queryAll(JobQueryCriteria criteria){
        List<Job> jobList = baseMapper.selectList(QueryHelpPlus.getPredicate(Job.class, criteria));
        if(criteria.getDeptIds().size()==0){
            for (Job job : jobList) {
                    job.setDept(deptService.getById(job.getDeptId()));
            }
        }else {
            //断权限范围
            for (Long deptId : criteria.getDeptIds()) {
                for (Job job : jobList) {
                    if(deptId.equals(job.getDeptId())){
                        job.setDept(deptService.getById(job.getDeptId()));
                    }
                }
            }
        }
        return jobList;
    }


    @Override
    public void download(List<JobDto> all, HttpServletResponse response) throws IOException {
        /*List<Map<String, Object>> list = new ArrayList<>();
        for (JobDto job : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("岗位名称", job.getName());
            map.put("岗位状态", job.getEnabled());
            map.put("岗位排序", job.getSort());
            map.put("创建日期", job.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);*/
    }
}
