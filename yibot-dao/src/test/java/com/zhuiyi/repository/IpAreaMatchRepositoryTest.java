package com.zhuiyi.repository;

import com.zhuiyi.DaoApplication;
import com.zhuiyi.common.util.CustomTimeUtil;
import com.zhuiyi.model.IpAreaMatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DaoApplication.class)
@Slf4j
public class IpAreaMatchRepositoryTest {

    @Autowired
    private IpAreaMatchRepository ipAreaMatchRepository;

    private IpAreaMatch ipAreaMatchTemp = new IpAreaMatch();

    @Before
    public void runBeforeTestMethod() {

        ipAreaMatchTemp.setId("test");
        ipAreaMatchTemp.setCountry("test");
        ipAreaMatchTemp.setProvince("test");
        ipAreaMatchTemp.setCity("test");
        ipAreaMatchTemp.setDistrict("test");
        ipAreaMatchTemp.setIspp("test");
        ipAreaMatchTemp.setIsp("test");
        ipAreaMatchTemp.setCode(0);
        ipAreaMatchTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
        ipAreaMatchTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void save() {
//        ipAreaMatchTemp = ipAreaMatchRepository.save(ipAreaMatchTemp);
        String fileName = "D://ip_file";

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(x -> {
                String[] arr = x.split(",");
                ipAreaMatchTemp.setId(arr[0]);
                ipAreaMatchTemp.setCountry(arr[1]);
                ipAreaMatchTemp.setProvince(arr[2]);
                ipAreaMatchTemp.setCity(arr[3]);
                ipAreaMatchTemp.setDistrict(arr[4]);
                ipAreaMatchTemp.setIspp("");
                ipAreaMatchTemp.setIsp("");
                ipAreaMatchTemp.setCode(0);
                ipAreaMatchTemp.setGmtCreate(CustomTimeUtil.getNowTimeForDate());
                ipAreaMatchTemp.setGmtModified(CustomTimeUtil.getNowTimeForDate());
                ipAreaMatchRepository.save(ipAreaMatchTemp);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback
    public void update() {

        ipAreaMatchRepository.save(ipAreaMatchTemp);
        log.info(ipAreaMatchRepository.findById(ipAreaMatchTemp.getId()).toString());
    }


    @Test
    @Transactional
    @Rollback
    public void query() {
//        save();
        log.info(ipAreaMatchRepository.findById(ipAreaMatchTemp.getId()).toString());
    }

    @Test
    public void count() {
        log.info("" + ipAreaMatchRepository.count());
    }

    @Test
    @Transactional
    @Rollback
    public void delete() {
        save();
        ipAreaMatchRepository.deleteById(ipAreaMatchTemp.getId());
    }
}