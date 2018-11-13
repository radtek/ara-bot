package com.zhuiyi.common.util;

import com.zhuiyi.common.constant.GlobaSystemConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 10:31
 * description: 自定义的对象处理工具类
 * own:
 */
@SuppressWarnings({"RedundantArrayCreation", "unchecked"})
public class CustomObjectUtil {

    /**
     * 设置渠道筛选条件对象
     *
     * @param itemFilter 渠道筛选条件对象
     * @param cid        渠道条件
     * @param eid        渠道条件
     * @param client     渠道条件
     * @param labels     渠道条件
     * @param im         渠道条件
     */
    public static void getObjectFilter(Object itemFilter, String cid, String eid, String client, String labels, String im) {
        Class classItem = itemFilter.getClass();
        Field[] fields = classItem.getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String setMethodName = "set" + firstLetter + fieldName.substring(1);
                Method setMethod = classItem.getMethod(setMethodName, new Class[]{field.getType()});
                switch (fieldName) {
                    case "isTotal":
                        if (null == cid && null == eid && null == client && null == labels && null == im) {
                            setMethod.invoke(itemFilter, new Object[]{1});
                        }
                        break;
                    case "cid":
                        setMethod.invoke(itemFilter, new Object[]{cid});
                        break;
                    case "eid":
                        setMethod.invoke(itemFilter, new Object[]{eid});
                        break;
                    case "client":
                        setMethod.invoke(itemFilter, new Object[]{client});
                        break;
                    case "labels":
                        setMethod.invoke(itemFilter, new Object[]{labels});
                        break;
                    case "im":
                        setMethod.invoke(itemFilter, new Object[]{im});
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置渠道筛选条件对象
     *
     * @param itemFilter 渠道筛选条件对象
     * @param channels        渠道条件 {"cid":["user","test01"], "eid":["app","weixin"], "client":[], "labels":[], "im":[]}
     */
    public static void getMapObjectFilter(Object itemFilter, Map channels) {
        Class classItem = itemFilter.getClass();
        Field[] fields = classItem.getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String setMethodName = "set" + firstLetter + fieldName.substring(1);
                Method setMethod = classItem.getMethod(setMethodName, new Class[]{field.getType()});
                switch (fieldName) {
                    case "isTotal":
                        if (channels.get("cid").toString().length() == 2 && channels.get("eid").toString().length() == 2
                                && channels.get("client").toString().length() == 2 && channels.get("labels").toString().length() == 2
                                && channels.get("im").toString().length() == 2 ) {
                            setMethod.invoke(itemFilter, new Object[]{1});
                        }
                        break;
                    case "cid":
                        if(channels.get("cid").toString().length() > 2){
                            setMethod.invoke(itemFilter, new Object[]{channels.get("cid").toString()});
                        }else{
                            setMethod.invoke(itemFilter, new Object[]{null});
                        }
                        break;
                    case "eid":
                        if(channels.get("eid").toString().length() > 2){
                            setMethod.invoke(itemFilter, new Object[]{channels.get("eid").toString()});
                        }else{
                            setMethod.invoke(itemFilter, new Object[]{null});
                        }
                        break;
                    case "client":
                        if(channels.get("client").toString().length() > 2){
                            setMethod.invoke(itemFilter, new Object[]{channels.get("client").toString()});
                        }else{
                            setMethod.invoke(itemFilter, new Object[]{null});
                        }
                        break;
                    case "labels":
                        if(channels.get("labels").toString().length() > 2){
                            setMethod.invoke(itemFilter, new Object[]{channels.get("labels").toString()});
                        }else{
                            setMethod.invoke(itemFilter, new Object[]{null});
                        }
                        break;
                    case "im":
                        if(channels.get("im").toString().length() > 2){
                            setMethod.invoke(itemFilter, new Object[]{channels.get("im").toString()});
                        }else{
                            setMethod.invoke(itemFilter, new Object[]{null});
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据渠道条件从指定数据集中筛选对应数据对象
     *
     * @param itemList   指定数据集
     * @param itemFilter 包含渠道条件的筛选对象 此对象必须严格控制
     * @return object 匹配筛选条件的数据对象
     */
    public static List<?> getObjectByChanel(List<?> itemList, Object itemFilter) {
        return itemList.parallelStream().filter(obj -> {
            boolean flag = false;
            Class classItem = obj.getClass();
            Field[] fields = classItem.getDeclaredFields();
            try {
                for (Field field : fields) {
                    Type t = field.getGenericType();
                    String fieldName = field.getName();
                    String firstLetter = fieldName.substring(0, 1).toUpperCase();
                    String getMethodName = "get" + firstLetter + fieldName.substring(1);
                    Method getMethodTemp = classItem.getMethod(getMethodName, new Class[]{});
                    Optional<Object> value = Optional.ofNullable(getMethodTemp.invoke(obj, new Object[]{}));
                    Optional<Object> valueFilter = Optional.ofNullable(getMethodTemp.invoke(itemFilter, new Object[]{}));
                    if (valueFilter.isPresent()) {
                        if (value.isPresent()) {
                            if ("class java.lang.String".equals(t.toString())) {
                                flag = String.valueOf(valueFilter.get()).equals(String.valueOf(value.get()));
                            } else if ("int".equals(t.toString()) || "class java.lang.Integer".equals(t.toString())) {
                                flag = (int) valueFilter.get() == (int) value.get();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }).collect(Collectors.toList());
    }

    /**
     * 组装redis的Key串
     *
     * @param appID       业务标识
     * @param monthAndDay 日期和时间标识 MMdd
     * @param tenMinute   十分钟标识 HHmm
     * @param name        具体的项名
     * @return String redis的Key串
     */
    public static String buildKeyString(String appID, String monthAndDay, String tenMinute, String name) {
        StringBuilder keyString = new StringBuilder();
        keyString.append(GlobaSystemConstant.KEY_PROJECT_NAME_STR);
        if (null != appID) {
            keyString.append(":");
            keyString.append(appID);
        }
        if (null != monthAndDay) {
            keyString.append(":");
            keyString.append(monthAndDay);
        }
        keyString.append(":");
        if (tenMinute != null) {
            keyString.append(tenMinute);
            keyString.append(":");
        }
        keyString.append(name);
        return keyString.toString();
    }

    /**
     * 用于Faqtype转换
     * 0 正常 2 意图 10 寒暄 20 资料知识
     *
     * @param srcFaqtype 原faqtype
     * @return String 目标faqtype
     */
    public static String changeFaqtype(String srcFaqtype) {
        String desFaqtype;
        switch (srcFaqtype) {
            case "1":
                desFaqtype = "10";
                break;
            case "2":
                desFaqtype = "20";
                break;
            case "3":
                desFaqtype = "2";
                break;
            default:
                desFaqtype = "0";
                break;
        }
        return desFaqtype;
    }

    /**
     * 用于Faqtype转换
     * 0 正常 2 意图 10 寒暄 20 资料知识
     *
     * @param bid 前端传过来的原bid
     * @return String 解析过后的真实Bid
     */
    public static String solveBid(String bid) {
        int bidTemp = Integer.parseInt(bid);
        int pid = bidTemp >> 16;
        return String.valueOf(pid) + String.valueOf(bidTemp - (pid << 16));
    }
}
