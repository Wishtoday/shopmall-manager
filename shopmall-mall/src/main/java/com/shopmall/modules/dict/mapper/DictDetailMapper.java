package com.shopmall.modules.dict.mapper;

import com.shopmall.common.mapper.CoreMapper;
import com.shopmall.modules.dict.domain.DictDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author zhuxiying
* @date 2023-12-12
*/
@Repository
public interface DictDetailMapper extends CoreMapper <DictDetail> {

    @Select("<script>SELECT d.* from dict_detail d LEFT JOIN dict t on d.dict_id = t.id where d.is_del=0 <if test = \"label !=null\" > and d.label LIKE concat('%', #{label}, '%') </if> <if test = \"dictName != ''||dictName !=null\" > AND t.name = #{dictName} order by d.sort asc</if></script>")
    List<DictDetail> selectDictDetailList(@Param("label") String label, @Param("dictName") String dictName);
}
