package com.shopmall.utils;


import com.shopmall.category.dto.CateDto;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeUtil
 * @Author zhuxiying
 * @Date 2023/12/12
 **/
public class TreeUtil {
    /**
     * 获得指定节点下所有归档
     * @param list
     * @param parentId
     * @return
     */
    public static List<CateDto> list2TreeConverter(List<CateDto> list, int parentId) {
        List<CateDto> returnList = new ArrayList<>();

        for (CateDto res : list) {
            //判断对象是否为根节点

            if (res.getPid() == parentId) {
                //该节点为根节点,开始递归

                recursionFn(list, res); //通过递归为节点设置childList

                returnList.add(res);
            }
        }

        return returnList;
    }

    /**
     * 递归列表
     * 通过递归,给指定t节点设置childList
     * @param list
     * @param t
     */
    public static void recursionFn(List<CateDto> list, CateDto t) {
        //只能获取当前t节点的子节点集,并不是所有子节点集
        List<CateDto> childsList = getChildList(list, t);
        //设置他的子集对象集
        t.setChildren(childsList);

        //迭代子集对象集
        for (CateDto nextChild : childsList) { //遍历完,则退出递归

            //判断子集对象是否还有子节点
            if (!CollectionUtils.isEmpty(childsList)) {
                //有下一个子节点,继续递归
                recursionFn(list, nextChild);
            }
        }
    }

    /**
     * 获得指定节点下的所有子节点
     * @param list
     * @param t
     * @return
     */
    public static List<CateDto> getChildList(List<CateDto> list, CateDto t) {
        List<CateDto> childsList = new ArrayList<CateDto>();
        //遍历集合元素,如果元素的Parentid==指定元素的id,则说明是该元素的子节点
        for (CateDto t1 : list) {
            if (t1.getPid().equals(t.getId())) {
                childsList.add(t1);
            }
        }

        return childsList;
    }

    /**
     * 判断是否还有下一个子节点
     * @param list
     * @param t
     */
    public static boolean hasChild(List<CateDto> list, CateDto t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
