package com.zhuiyi.api;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ApiApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.CustomProperties;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.model.HotAreaFqOverview;
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
public class HotAreaFqOverviewApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResCodeProperties resCodeProperties;
    @Autowired
    private CustomProperties customProperties;

    private HotAreaFqOverview hotAreaFqOverviewTemp = new HotAreaFqOverview();

    @Before
    public void runBeforeTestMethod() {

        hotAreaFqOverviewTemp.setDateSign("test");
        hotAreaFqOverviewTemp.setAppid("11");
        hotAreaFqOverviewTemp.setAreaType(0);
        hotAreaFqOverviewTemp.setAreaName("test");
        hotAreaFqOverviewTemp.setFaqId(0);
        hotAreaFqOverviewTemp.setFaq("test");
        hotAreaFqOverviewTemp.setVisitNum(0);
        hotAreaFqOverviewTemp.setVisitTrend("test");
        hotAreaFqOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        hotAreaFqOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    /**
     * 测试查询实体列表
     */
    @Test
    public void queryList() throws Exception {
        mockMvc.perform(get("/yibot/v1/api/hotareafqoverview")).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getSuccessCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getSuccessMsg()))
        .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试按id查询实体
     */
    @Test
    public void query() throws Exception {

        Long id = 1L;
        mockMvc.perform(get("/yibot/v1/api/hotareafqoverview/" + id)).andExpect(status().isOk())
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

        mockMvc.perform(post("/yibot/v1/api/hotareafqoverview").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(hotAreaFqOverviewTemp))).andExpect(status().isOk())
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

        Long id = 1L;

        mockMvc.perform(put("/yibot/v1/api/hotareafqoverview/" + id).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(hotAreaFqOverviewTemp))).andExpect(status().isOk())
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
    public void deleteHotAreaFqOverview() throws Exception {

        Long id = 1L;
        mockMvc.perform(delete("/yibot/v1/api/hotareafqoverview/" + id)).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getOperateFailErrorCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getOperateFailErrorMsg()))
        .andDo(MockMvcResultHandlers.print());
    }
}

