package com.zhuiyi.api;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ApiApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.CustomProperties;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.model.Dialog;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/27
 * description:
 * own: zhuiyi
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
@Slf4j
public class DialogApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResCodeProperties resCodeProperties;
    @Autowired
    private CustomProperties customProperties;

    private Dialog dialogTemp = new Dialog();

    @Before
    public void runBeforeTestMethod() {

        dialogTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        dialogTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(CustomTimeUtil.getNowTimeForDate()));
        dialogTemp.setDateSign("test");
        dialogTemp.setSessionId("test");
        dialogTemp.setSearchId("test");
        dialogTemp.setAppid("11");
        dialogTemp.setAnswerType("test");
        dialogTemp.setFaqNum(0);
        dialogTemp.setRetcode(0);
        dialogTemp.setTenMin("test");
        dialogTemp.setSource("test");
        dialogTemp.setAccount("test");
        dialogTemp.setCountry("test");
        dialogTemp.setProvince("test");
        dialogTemp.setCity("test");
        dialogTemp.setIsReject(0);
        dialogTemp.setIsClickGood(0);
        dialogTemp.setIsClickBad(0);
        dialogTemp.setIsZrg("test");
        dialogTemp.setAdaptorCost(0);
        dialogTemp.setClientip("test");
        dialogTemp.setBizType(0);
        dialogTemp.setBusinessName("test");
        dialogTemp.setIsAssistant(0);
        dialogTemp.setChannel("test");
        dialogTemp.setCid("test");
        dialogTemp.setEid("test");
        dialogTemp.setLables("test");
        dialogTemp.setIm("test");
        dialogTemp.setClient("test");
        dialogTemp.setTags("test");
        dialogTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        dialogTemp.setReserved1("test");
        dialogTemp.setReserved2("test");
        dialogTemp.setReserved3("test");
        dialogTemp.setReserved4("test");
        dialogTemp.setReserved5("test");
    }

    /**
     * 测试查询实体列表
     */
    @Test
    public void queryList() throws Exception {
        mockMvc.perform(get("/yibot/v1/api/dialog")).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getSuccessCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getSuccessMsg()))
        .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试按id查询实体
     */
    @Test
    public void query() throws Exception {

        String id = "id1234";
        mockMvc.perform(get("/yibot/v1/api/dialog/" + id)).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getReqContentNotExistCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getReqContentNotExistMsg()))
        .andDo(MockMvcResultHandlers.print());
     }

    /**
     * 测试新增实体
     */
    @Test
    @Transactional
    @Rollback
    public void create() throws Exception {

        mockMvc.perform(post("/yibot/v1/api/dialog").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(dialogTemp))).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getSuccessCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getSuccessMsg()))
        .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试更新实体
     */
    @Test
    @Transactional
    @Rollback
    public void update() throws Exception {

        String id = "id1234";

        mockMvc.perform(put("/yibot/v1/api/dialog/" + id).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(dialogTemp))).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getSuccessCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getSuccessMsg()))
        .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试删除实体
     */
    @Test
    @Transactional
    @Rollback
    public void deleteDialog() throws Exception {

        String id = "id1234";
        mockMvc.perform(delete("/yibot/v1/api/dialog/" + id)).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getOperateFailErrorCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getOperateFailErrorMsg()))
        .andDo(MockMvcResultHandlers.print());
    }
}

