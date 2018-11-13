package com.zhuiyi.api;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ApiApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.CustomProperties;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.model.Session;
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
public class SessionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResCodeProperties resCodeProperties;
    @Autowired
    private CustomProperties customProperties;

    private Session sessionTemp = new Session();

    @Before
    public void runBeforeTestMethod() {

        sessionTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        sessionTemp.setStartTime(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setEndTime(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(CustomTimeUtil.getNowTimeForDate()));
        sessionTemp.setDateSign("test");
        sessionTemp.setRoundNum(0);
        sessionTemp.setAppid("11");
        sessionTemp.setAccount("test");
        sessionTemp.setUserIp("test");
        sessionTemp.setCountry("test");
        sessionTemp.setProvince("test");
        sessionTemp.setCity("test");
        sessionTemp.setSenLevel(0);
        sessionTemp.setJudgeType(0);
        sessionTemp.setClickGoodNum(0);
        sessionTemp.setClickBadNum(0);
        sessionTemp.setSource("test");
        sessionTemp.setZrgType("test");
        sessionTemp.setZrgAt(0);
        sessionTemp.setBusinessName("test");
        sessionTemp.setCheckTag("test");
        sessionTemp.setHasHanxuan(0);
        sessionTemp.setHasReject(0);
        sessionTemp.setHasAns1(0);
        sessionTemp.setHasAns3(0);
        sessionTemp.setHasAssistant(0);
        sessionTemp.setHasAdopted(0);
        sessionTemp.setHasSecondedit(0);
        sessionTemp.setHasAbandoned(0);
        sessionTemp.setChannel("test");
        sessionTemp.setCid("test");
        sessionTemp.setEid("test");
        sessionTemp.setLables("test");
        sessionTemp.setIm("test");
        sessionTemp.setClient("test");
        sessionTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
        sessionTemp.setReserved1("test");
        sessionTemp.setReserved2("test");
        sessionTemp.setReserved3("test");
        sessionTemp.setReserved4("test");
        sessionTemp.setReserved5("test");
    }

    /**
     * 测试查询实体列表
     */
    @Test
    public void queryList() throws Exception {
        mockMvc.perform(get("/yibot/v1/api/session")).andExpect(status().isOk())
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
        mockMvc.perform(get("/yibot/v1/api/session/" + id)).andExpect(status().isOk())
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

        mockMvc.perform(post("/yibot/v1/api/session").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sessionTemp))).andExpect(status().isOk())
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

        mockMvc.perform(put("/yibot/v1/api/session/" + id).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(sessionTemp))).andExpect(status().isOk())
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
    public void deleteSession() throws Exception {

        String id = "id1234";
        mockMvc.perform(delete("/yibot/v1/api/session/" + id)).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getOperateFailErrorCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getOperateFailErrorMsg()))
        .andDo(MockMvcResultHandlers.print());
    }
}

