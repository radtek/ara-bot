package com.zhuiyi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/28 16:44
 * description: Faq转人工统计数据 DTO类
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZrgOverviewDTO implements Serializable {
    /**
     *
     */
    private String date;
    private String zrgRate;
    private String zrgSession;
    private String zrgActive;
    private String zrgPassive;
    private String zeroTurn;
    private String oneTurn;
    private String twoTurn;
    private String threeTurn;
    private List<FaqZrgDTO> zrgTopFaq;
    private List<FaqZrgDTO> compareTop;
    private List<FaqZrgDTO> unSolveTop;
}
