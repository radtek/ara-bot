package com.zhuiyi.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

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

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ApiApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.CustomProperties;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.model.DialogDetail;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;

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
public class DialogDetailApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResCodeProperties resCodeProperties;
    @Autowired
    private CustomProperties customProperties;

    private DialogDetail dialogDetailTemp = new DialogDetail();

    @Before
    public void runBeforeTestMethod() {

        dialogDetailTemp.setId("" + new DefaultKeyGenerator().generateKey().longValue());
        dialogDetailTemp.setAppid("11");
        dialogDetailTemp.setDateMonth(CustomTimeUtil.getFormateSimMonth(CustomTimeUtil.getNowTimeForDate()));
        dialogDetailTemp.setExactTime(CustomTimeUtil.getNowTimeForDate());
        dialogDetailTemp.setRecvTime(CustomTimeUtil.getNowTimeForDate());
        dialogDetailTemp.setRetmsg("test");
        dialogDetailTemp.setDirectFaqId("test");
        dialogDetailTemp.setIndirectFaqId("test");
        dialogDetailTemp.setDirectFaq("test");
        dialogDetailTemp.setIndirectFaq("test");
        dialogDetailTemp.setUserIp("test");
        dialogDetailTemp.setQuery("test");
        dialogDetailTemp.setBizPortal("test");
        dialogDetailTemp.setBizRetCode(0);
        dialogDetailTemp.setBizDocid("test");
        dialogDetailTemp.setBizRetStr("test");
        dialogDetailTemp.setCcdCost(0);
        dialogDetailTemp.setXforward("test");
        dialogDetailTemp.setBizCost(0);
        dialogDetailTemp.setAnswerIndex("test");
        dialogDetailTemp.setPlace("test");
        dialogDetailTemp.setOther("test");
        dialogDetailTemp.setSenLevel(0);
        dialogDetailTemp.setSenWords("test");
        dialogDetailTemp.setConfidence("test");
    }

    /**
     * 测试查询实体列表
     */
    @Test
    public void queryList() throws Exception {
        mockMvc.perform(get("/yibot/v1/api/dialogdetail")).andExpect(status().isOk())
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
        mockMvc.perform(get("/yibot/v1/api/dialogdetail/" + id)).andExpect(status().isOk())
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

        mockMvc.perform(post("/yibot/v1/api/dialogdetail").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(dialogDetailTemp))).andExpect(status().isOk())
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

        mockMvc.perform(put("/yibot/v1/api/dialogdetail/" + id).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(dialogDetailTemp))).andExpect(status().isOk())
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
    public void deleteDialogDetail() throws Exception {

        String id = "id1234";
        mockMvc.perform(delete("/yibot/v1/api/dialogdetail/" + id)).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getOperateFailErrorCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getOperateFailErrorMsg()))
        .andDo(MockMvcResultHandlers.print());
    }
}

