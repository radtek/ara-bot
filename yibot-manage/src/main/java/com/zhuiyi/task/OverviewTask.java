package com.zhuiyi.task;

import com.zhuiyi.common.constant.GlobaSystemConstant;
import com.zhuiyi.common.util.CustomObjectUtil;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/24 12:20
 * description: xxx
 * own:
 */
@Component
@Slf4j
public class OverviewTask {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private RedisService redisService;

    //todo 发布时注意修改定时时间

    /**
     * 每小时的10分执行该方法
     */
//    @Scheduled(cron = "0/20 * * * * *")
    @Scheduled(cron = "0 0/10 * * * *")
    public void cronByTenMinute() {
        log.info("[SYS][TASK][Content: Start to run every ten minute task][Time: {}]", CustomTimeUtil.getFormatCurrentDateTime());

        //开始获取redis中的统计数据到计算存储中
        String monthAndToday = CustomTimeUtil.getNowMonthAndDay();
        //从业务池中获取业务列表
        String appPoolKey = CustomObjectUtil.buildKeyString(null, null, null, GlobaSystemConstant.KEY_APP_NAME_STRING);
        Set<String> appidSet = redisService.findSetMembers(appPoolKey);
        log.debug("[SYS][TASK][Appidpool: {}]", appidSet.toString());
        if (appidSet != null) {
            appidSet.stream().forEach(x -> {
                //构造对应的redisKey
                String dialogNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:dialog:num");
                String costNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:cost:num");
                String userNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:user:num");
                String directNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:direct:num");
                String respondNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:respond:num");
                String likeNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:like:num");
                String dislikeNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:dislike:num");
                String sessionNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:session:num");
                String zrgNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:zrg:num");
                String qpsNumKey = CustomObjectUtil.buildKeyString(x, monthAndToday, null, "ov:qps:num");
                String tenDialogKey = CustomObjectUtil.buildKeyString(x, monthAndToday, CustomTimeUtil.getNowTenMinuteBefore(), "dialog");

                //读取并缓存
                //查询当天的提问量数据
                GlobaSystemConstant.INIT_HASHMAP.put("dialogNumKey", ((Integer) Optional.ofNullable(redisService.find(dialogNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("costNumKey", ((Integer) Optional.ofNullable(redisService.find(costNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("userNumKey", ((Integer) Optional.ofNullable(redisService.find(userNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("directNumKey", ((Integer) Optional.ofNullable(redisService.find(directNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("respondNumKey", ((Integer) Optional.ofNullable(redisService.find(respondNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("likeNumKey", ((Integer) Optional.ofNullable(redisService.find(likeNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("dislikeNumKey", ((Integer) Optional.ofNullable(redisService.find(dislikeNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("sessionNumKey", ((Integer) Optional.ofNullable(redisService.find(sessionNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("zrgNumKey", ((Integer) Optional.ofNullable(redisService.find(zrgNumKey)).orElse(0)).longValue());
                GlobaSystemConstant.INIT_HASHMAP.put("qpsNumKey", ((Integer) Optional.ofNullable(redisService.find(qpsNumKey)).orElse(0)).longValue());
                //从Redis中取前10分钟的dialog日志
                GlobaSystemConstant.INIT_HASHMAP.put("tenDialogKey", redisService.findHashAll(tenDialogKey));

                //构建旧版整体数据统计定时任务
//                buildWholeTrendOverviewTask(x);
                //构建新版整体数据统计定时任务
                buildWholeServiceOverviewTask(x);
                //构建今日趋势统计定时任务
                buildPartTrendOverviewTask(x);
                //全国热点FAQ
                buildCityOverviewTask(x);
                //各省份热点FAQ
                buildProvinceOverviewTask(x);
                // 热门知识点
                buildTouchOverviewTask(x);
                //满意度分析
                buildJudgeOverviewTask(x);
                //转人工分析
                buildZrgOverviewTask(x);
            });
        } else {
            log.warn("[SYS][TASK][Content: There is no appid now]");
        }
    }

    /**
     * 每天的0点30分执行该方法
     */
//    @Scheduled(cron = "0 0/1 * * * *")
    @Scheduled(cron = "0 30 0 * * ?")
    public void cronByDay(){
        log.info("[SYS][TASK][Content: Start to run every day task][Time: {}]", CustomTimeUtil.getFormatCurrentDateTime());
        //从业务池中获取业务列表
        String appPoolKey = CustomObjectUtil.buildKeyString(null, null, null, GlobaSystemConstant.KEY_APP_NAME_STRING);
        Set<String> appidSet = redisService.findSetMembers(appPoolKey);
        log.info("[SYS][TASK][Appidpool: {}]", appidSet.toString());
        if (appidSet != null) {
            appidSet.stream().forEach(x -> {
                //构建热点分析数据统计定时任务
                buildHotOverviewTask(x);
                //构建资料库分析数据统计定时任务
                buildFaqTouchOverviewTask(x);
            });
        } else {
            log.warn("[SYS][TASK][Content: There is no appid now]");
        }
    }

    /**
     * 构建整体趋势统计定时任务
     */
    public void buildWholeTrendOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = WholeTrendOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建新版整体趋势统计定时任务
     */
    public void buildWholeServiceOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = WholeServiceOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建每十分钟耗时和访问量趋势统计定时任务
     */
    public void buildPartTrendOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = PartTrendOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建Faq提问数据统计定时任务
     */
    public void buildCityOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = CityOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建Faq提问数据统计定时任务
     */
    public void buildTouchOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = TouchOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建Faq提问数据统计定时任务
     */
    public void buildProvinceOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = ProvinceOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建资料库分析数据统计定时任务
     */
    public void buildFaqTouchOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = FaqTouchOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建热点分析数据统计定时任务
     */
    public void buildHotOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = HotOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建点赞点踩分析数据统计定时任务
     */
    public void buildJudgeOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = JudgeOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建转人工分析数据统计定时任务
     */
    public void buildZrgOverviewTask(String appid) {
        //设置任务类型
        Class taskClass = ZrgOverviewTask.class;
        //设置任务开始时间
        long taskTime = System.currentTimeMillis();
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = taskClass.getName();
        startTask(name, group, taskClass, taskTime, appid);
    }

    /**
     * 构建定时任务
     */
    public void startTask(String name, String group, Class task, long taskTime, String appid) {
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(task).withIdentity(name, group).build();
        jobDetail.getJobDataMap().put("appid", appid);
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).startAt(new Date(taskTime)).build();
        //将触发器与任务绑定到调度器内
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
