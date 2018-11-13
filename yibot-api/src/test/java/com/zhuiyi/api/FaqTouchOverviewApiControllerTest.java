package com.zhuiyi.api;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ApiApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.config.param.CustomProperties;
import com.zhuiyi.config.param.ResCodeProperties;
import com.zhuiyi.model.FaqTouchOverview;
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
public class FaqTouchOverviewApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResCodeProperties resCodeProperties;
    @Autowired
    private CustomProperties customProperties;

    private FaqTouchOverview faqTouchOverviewTemp = new FaqTouchOverview();

    @Before
    public void runBeforeTestMethod() {

        faqTouchOverviewTemp.setDateSign("test");
        faqTouchOverviewTemp.setAppid("11");
        faqTouchOverviewTemp.setFaqId(0);
        faqTouchOverviewTemp.setFaq("test");
        faqTouchOverviewTemp.setCategoryId("test");
        faqTouchOverviewTemp.setTouchNum(0);
        faqTouchOverviewTemp.setDirectNum(0);
        faqTouchOverviewTemp.setRecommendNum(0);
        faqTouchOverviewTemp.setRecommendAnsNum(0);
        faqTouchOverviewTemp.setClickGood(0);
        faqTouchOverviewTemp.setClickBad(0);
        faqTouchOverviewTemp.setTouchRate("test");
        faqTouchOverviewTemp.setZdZrg(0);
        faqTouchOverviewTemp.setBdZrg(0);
        faqTouchOverviewTemp.setTotalZrg(0);
        faqTouchOverviewTemp.setClickGoodRate("test");
        faqTouchOverviewTemp.setClickBadRate("test");
        faqTouchOverviewTemp.setBizType("test");
        faqTouchOverviewTemp.setTotalClick(0);
        faqTouchOverviewTemp.setZrgRate("test");
        faqTouchOverviewTemp.setLabels("test");
        faqTouchOverviewTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        faqTouchOverviewTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    /**
     * 测试查询实体列表
     */
    @Test
    public void queryList() throws Exception {
        mockMvc.perform(get("/yibot/v1/api/faqtouchoverview")).andExpect(status().isOk())
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
        mockMvc.perform(get("/yibot/v1/api/faqtouchoverview/" + id)).andExpect(status().isOk())
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

        mockMvc.perform(post("/yibot/v1/api/faqtouchoverview").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(faqTouchOverviewTemp))).andExpect(status().isOk())
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

        mockMvc.perform(put("/yibot/v1/api/faqtouchoverview/" + id).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(faqTouchOverviewTemp))).andExpect(status().isOk())
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
    public void deleteFaqTouchOverview() throws Exception {

        Long id = 1L;
        mockMvc.perform(delete("/yibot/v1/api/faqtouchoverview/" + id)).andExpect(status().isOk())
        .andExpect(jsonPath("code").value(resCodeProperties.getOperateFailErrorCode()))
        .andExpect(jsonPath("message").value(resCodeProperties.getOperateFailErrorMsg()))
        .andDo(MockMvcResultHandlers.print());
    }
}

