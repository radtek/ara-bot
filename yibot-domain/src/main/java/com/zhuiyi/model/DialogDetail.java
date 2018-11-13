package com.zhuiyi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(DialogDetailId.class)
@Table(name = "t_dialog_detail")
public class DialogDetail implements Serializable {
    /**
     * id  db_column: id
     */
    @Id
    @Column(name = "id", length = 100, nullable = false)
    private String id;
    /**
     * exactTime  db_column: exact_time
     */
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exact_time", length = 19, nullable = false)
    private Date exactTime;
    /**
     * appid  db_column: appid
     */
    @NotBlank @Length(max=30)
    @Column(name = "appid", length = 30, nullable = false)
    private String appid;
    /**
     * dateMonth  db_column: date_month
     */
    @NotBlank @Length(max=10)
    @Column(name = "date_month", length = 10, nullable = false)
    private String dateMonth;
    /**
     * recvTime  db_column: recv_time
     */
    @NotNull
    @Column(name = "recv_time", length = 19, nullable = false)
    private Date recvTime;
    /**
     * retmsg  db_column: retmsg
     */
    @Length(max=250)
    @Column(name = "retmsg", length = 250, nullable = false)
    private String retmsg;
    /**
     * directFaqId  db_column: direct_faq_id
     */
    @NotBlank @Length(max=10)
    @Column(name = "direct_faq_id", length = 10, nullable = false)
    private String directFaqId;
    /**
     * indirectFaqId  db_column: indirect_faq_id
     */
    @NotBlank @Length(max=100)
    @Column(name = "indirect_faq_id", length = 100, nullable = false)
    private String indirectFaqId;
    /**
     * directFaq  db_column: direct_faq
     */
    @Length(max=400)
    @Column(name = "direct_faq", length = 400)
    private String directFaq;
    /**
     * indirectFaq  db_column: indirect_faq
     */
    @Length(max=4000)
    @Column(name = "indirect_faq", length = 4000)
    private String indirectFaq;
    /**
     * userIp  db_column: user_ip
     */
    @Length(max=50)
    @Column(name = "user_ip", length = 50)
    private String userIp;
    /**
     * query  db_column: query
     */
    @Length(max=3000)
    @Column(name = "query", length = 3000)
    private String query;
    /**
     * bizPortal  db_column: biz_portal
     */
    @Length(max=30)
    @Column(name = "biz_portal", length = 30)
    private String bizPortal;
    /**
     * bizRetCode  db_column: biz_ret_code
     */
    @Max(127)
    @Column(name = "biz_ret_code", length = 3)
    private Integer bizRetCode;
    /**
     * bizDocid  db_column: biz_docid
     */
    @Length(max=800)
    @Column(name = "biz_docid", length = 800)
    private String bizDocid;
    /**
     * bizRetStr  db_column: biz_ret_str
     */
    @Length(max=50)
    @Column(name = "biz_ret_str", length = 50)
    private String bizRetStr;
    /**
     * ccdCost  db_column: ccd_cost
     */
    @Max(9999999999L)
    @Column(name = "ccd_cost", length = 10)
    private Integer ccdCost;
    /**
     * xforward  db_column: x_forward
     */
    @Length(max=20)
    @Column(name = "x_forward", length = 20)
    private String xforward;
    /**
     * bizCost  db_column: biz_cost
     */
    @Max(9999999999L)
    @Column(name = "biz_cost", length = 10)
    private Integer bizCost;
    /**
     * answerIndex  db_column: answer_index
     */
    @Length(max=10)
    @Column(name = "answer_index", length = 10)
    private String answerIndex;
    /**
     * place  db_column: place
     */
    @Length(max=50)
    @Column(name = "place", length = 50)
    private String place;
    /**
     * other  db_column: other
     */
    @Length(max=50)
    @Column(name = "other", length = 50)
    private String other;
    /**
     * senLevel  db_column: sen_level
     */
    @Max(127)
    @Column(name = "sen_level", length = 3)
    private Integer senLevel;
    /**
     * senWords  db_column: sen_words
     */
    @Length(max=100)
    @Column(name = "sen_words", length = 100)
    private String senWords;
    /**
     * confidence  db_column: confidence
     */
    @Length(max=100)
    @Column(name = "confidence", length = 100)
    private String confidence;
}


