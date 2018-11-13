/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/8/25 17:19:33                           */
/*==============================================================*/


drop table if exists t_city_overview;

drop table if exists t_dialog_201807;

drop table if exists t_dialog_201808;

drop table if exists t_dialog_201809;

drop table if exists t_dialog_answer;

drop table if exists t_dialog_back;

drop table if exists t_dialog_detail_201807;

drop table if exists t_dialog_detail_201808;

drop table if exists t_dialog_detail_201809;

drop table if exists t_faq_touch_overview;

drop table if exists t_feedback_back;

drop table if exists t_hot_area_fq_overview;

drop table if exists t_hot_area_overview;

drop table if exists t_hot_faq_overview;

drop table if exists t_ip_area_match;

drop table if exists t_judge_overview;

drop table if exists t_part_trend_overview;

drop table if exists t_province_overview;

drop table if exists t_session_201807;

drop table if exists t_session_201808;

drop table if exists t_session_201809;

drop table if exists t_touch_overview;

drop table if exists t_whole_service_overview;

drop table if exists t_whole_trend_overview;

drop table if exists t_zrg_overview;

/*==============================================================*/
/* Table: t_city_overview                                       */
/*==============================================================*/
create table t_city_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_city_overview comment '各城市faq数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_city_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_dialog_201807                                       */
/*==============================================================*/
create table t_dialog_201807
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   exact_time           datetime not null,
   date_sign            char(10) not null,
   session_id           varchar(100),
   search_id            varchar(50),
   answer_type          varchar(10),
   faq_num              tinyint unsigned,
   retcode              tinyint,
   ten_min              char(10) not null,
   source               varchar(30),
   account              varchar(100),
   country              varchar(50),
   province             varchar(50),
   city                 varchar(50),
   is_reject            tinyint unsigned,
   is_click_good        tinyint unsigned,
   is_click_bad         tinyint unsigned,
   is_zrg               char(6),
   adaptor_cost         int unsigned,
   clientip             varchar(30),
   biz_type             tinyint unsigned not null,
   business_name        varchar(30),
   is_assistant         tinyint unsigned,
   channel              varchar(100),
   cid                  varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   client               varchar(30),
   tags                 varchar(30),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   reserved1            varchar(100),
   reserved2            varchar(100),
   reserved3            varchar(100),
   reserved4            varchar(100),
   reserved5            varchar(100),
   primary key (id, exact_time, appid, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(exact_time))(
PARTITION PT20180702 VALUES LESS THAN (TO_DAYS('2018-07-02')),
PARTITION PT20180703 VALUES LESS THAN (TO_DAYS('2018-07-03')),
PARTITION PT20180704 VALUES LESS THAN (TO_DAYS('2018-07-04')),
PARTITION PT20180705 VALUES LESS THAN (TO_DAYS('2018-07-05')),
PARTITION PT20180706 VALUES LESS THAN (TO_DAYS('2018-07-06')),
PARTITION PT20180707 VALUES LESS THAN (TO_DAYS('2018-07-07')),
PARTITION PT20180708 VALUES LESS THAN (TO_DAYS('2018-07-08')),
PARTITION PT20180709 VALUES LESS THAN (TO_DAYS('2018-07-09')),
PARTITION PT20180710 VALUES LESS THAN (TO_DAYS('2018-07-10')),
PARTITION PT20180711 VALUES LESS THAN (TO_DAYS('2018-07-11')),
PARTITION PT20180712 VALUES LESS THAN (TO_DAYS('2018-07-12')),
PARTITION PT20180713 VALUES LESS THAN (TO_DAYS('2018-07-13')),
PARTITION PT20180714 VALUES LESS THAN (TO_DAYS('2018-07-14')),
PARTITION PT20180715 VALUES LESS THAN (TO_DAYS('2018-07-15')),
PARTITION PT20180716 VALUES LESS THAN (TO_DAYS('2018-07-16')),
PARTITION PT20180717 VALUES LESS THAN (TO_DAYS('2018-07-17')),
PARTITION PT20180718 VALUES LESS THAN (TO_DAYS('2018-07-18')),
PARTITION PT20180719 VALUES LESS THAN (TO_DAYS('2018-07-19')),
PARTITION PT20180720 VALUES LESS THAN (TO_DAYS('2018-07-20')),
PARTITION PT20180721 VALUES LESS THAN (TO_DAYS('2018-07-21')),
PARTITION PT20180722 VALUES LESS THAN (TO_DAYS('2018-07-22')),
PARTITION PT20180723 VALUES LESS THAN (TO_DAYS('2018-07-23')),
PARTITION PT20180724 VALUES LESS THAN (TO_DAYS('2018-07-24')),
PARTITION PT20180725 VALUES LESS THAN (TO_DAYS('2018-07-25')),
PARTITION PT20180726 VALUES LESS THAN (TO_DAYS('2018-07-26')),
PARTITION PT20180727 VALUES LESS THAN (TO_DAYS('2018-07-27')),
PARTITION PT20180728 VALUES LESS THAN (TO_DAYS('2018-07-28')),
PARTITION PT20180729 VALUES LESS THAN (TO_DAYS('2018-07-29')),
PARTITION PT20180730 VALUES LESS THAN (TO_DAYS('2018-07-30')),
PARTITION PT20180731 VALUES LESS THAN (TO_DAYS('2018-07-31')),
PARTITION PT20180801 VALUES LESS THAN (TO_DAYS('2018-08-01'))
);

alter table t_dialog_201807 comment '机器对话流水表';

/*==============================================================*/
/* Index: idx_date_sign                                         */
/*==============================================================*/
create index idx_date_sign on t_dialog_201807
(
   date_sign
);

/*==============================================================*/
/* Table: t_dialog_201808                                       */
/*==============================================================*/
create table t_dialog_201808
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   exact_time           datetime not null,
   date_sign            char(10) not null,
   session_id           varchar(100),
   search_id            varchar(50),
   answer_type          varchar(10),
   faq_num              tinyint unsigned,
   retcode              tinyint,
   ten_min              char(10) not null,
   source               varchar(30),
   account              varchar(100),
   country              varchar(50),
   province             varchar(50),
   city                 varchar(50),
   is_reject            tinyint unsigned,
   is_click_good        tinyint unsigned,
   is_click_bad         tinyint unsigned,
   is_zrg               char(6),
   adaptor_cost         int unsigned,
   clientip             varchar(30),
   biz_type             tinyint unsigned not null,
   business_name        varchar(30),
   is_assistant         tinyint unsigned,
   channel              varchar(100),
   cid                  varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   client               varchar(30),
   tags                 varchar(30),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   reserved1            varchar(100),
   reserved2            varchar(100),
   reserved3            varchar(100),
   reserved4            varchar(100),
   reserved5            varchar(100),
   primary key (id, exact_time, appid, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(exact_time))(
PARTITION PT20180802 VALUES LESS THAN (TO_DAYS('2018-08-02')),
PARTITION PT20180803 VALUES LESS THAN (TO_DAYS('2018-08-03')),
PARTITION PT20180804 VALUES LESS THAN (TO_DAYS('2018-08-04')),
PARTITION PT20180805 VALUES LESS THAN (TO_DAYS('2018-08-05')),
PARTITION PT20180806 VALUES LESS THAN (TO_DAYS('2018-08-06')),
PARTITION PT20180807 VALUES LESS THAN (TO_DAYS('2018-08-07')),
PARTITION PT20180808 VALUES LESS THAN (TO_DAYS('2018-08-08')),
PARTITION PT20180809 VALUES LESS THAN (TO_DAYS('2018-08-09')),
PARTITION PT20180810 VALUES LESS THAN (TO_DAYS('2018-08-10')),
PARTITION PT20180811 VALUES LESS THAN (TO_DAYS('2018-08-11')),
PARTITION PT20180812 VALUES LESS THAN (TO_DAYS('2018-08-12')),
PARTITION PT20180813 VALUES LESS THAN (TO_DAYS('2018-08-13')),
PARTITION PT20180814 VALUES LESS THAN (TO_DAYS('2018-08-14')),
PARTITION PT20180815 VALUES LESS THAN (TO_DAYS('2018-08-15')),
PARTITION PT20180816 VALUES LESS THAN (TO_DAYS('2018-08-16')),
PARTITION PT20180817 VALUES LESS THAN (TO_DAYS('2018-08-17')),
PARTITION PT20180818 VALUES LESS THAN (TO_DAYS('2018-08-18')),
PARTITION PT20180819 VALUES LESS THAN (TO_DAYS('2018-08-19')),
PARTITION PT20180820 VALUES LESS THAN (TO_DAYS('2018-08-20')),
PARTITION PT20180821 VALUES LESS THAN (TO_DAYS('2018-08-21')),
PARTITION PT20180822 VALUES LESS THAN (TO_DAYS('2018-08-22')),
PARTITION PT20180823 VALUES LESS THAN (TO_DAYS('2018-08-23')),
PARTITION PT20180824 VALUES LESS THAN (TO_DAYS('2018-08-24')),
PARTITION PT20180825 VALUES LESS THAN (TO_DAYS('2018-08-25')),
PARTITION PT20180826 VALUES LESS THAN (TO_DAYS('2018-08-26')),
PARTITION PT20180827 VALUES LESS THAN (TO_DAYS('2018-08-27')),
PARTITION PT20180828 VALUES LESS THAN (TO_DAYS('2018-08-28')),
PARTITION PT20180829 VALUES LESS THAN (TO_DAYS('2018-08-29')),
PARTITION PT20180830 VALUES LESS THAN (TO_DAYS('2018-08-30')),
PARTITION PT20180831 VALUES LESS THAN (TO_DAYS('2018-08-31')),
PARTITION PT20180901 VALUES LESS THAN (TO_DAYS('2018-09-01'))
);

alter table t_dialog_201808 comment '机器对话流水表';

/*==============================================================*/
/* Index: idx_date_sign                                         */
/*==============================================================*/
create index idx_date_sign on t_dialog_201808
(
   date_sign
);

/*==============================================================*/
/* Table: t_dialog_201809                                       */
/*==============================================================*/
create table t_dialog_201809
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   exact_time           datetime not null,
   date_sign            char(10) not null,
   session_id           varchar(100),
   search_id            varchar(50),
   answer_type          varchar(10),
   faq_num              tinyint unsigned,
   retcode              tinyint,
   ten_min              char(10) not null,
   source               varchar(30),
   account              varchar(100),
   country              varchar(50),
   province             varchar(50),
   city                 varchar(50),
   is_reject            tinyint unsigned,
   is_click_good        tinyint unsigned,
   is_click_bad         tinyint unsigned,
   is_zrg               char(6),
   adaptor_cost         int unsigned,
   clientip             varchar(30),
   biz_type             tinyint unsigned not null,
   business_name        varchar(30),
   is_assistant         tinyint unsigned,
   channel              varchar(100),
   cid                  varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   client               varchar(30),
   tags                 varchar(30),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   reserved1            varchar(100),
   reserved2            varchar(100),
   reserved3            varchar(100),
   reserved4            varchar(100),
   reserved5            varchar(100),
   primary key (id, exact_time, appid, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(exact_time))(
PARTITION PT20180902 VALUES LESS THAN (TO_DAYS('2018-09-02')),
PARTITION PT20180903 VALUES LESS THAN (TO_DAYS('2018-09-03')),
PARTITION PT20180904 VALUES LESS THAN (TO_DAYS('2018-09-04')),
PARTITION PT20180905 VALUES LESS THAN (TO_DAYS('2018-09-05')),
PARTITION PT20180906 VALUES LESS THAN (TO_DAYS('2018-09-06')),
PARTITION PT20180907 VALUES LESS THAN (TO_DAYS('2018-09-07')),
PARTITION PT20180908 VALUES LESS THAN (TO_DAYS('2018-09-08')),
PARTITION PT20180909 VALUES LESS THAN (TO_DAYS('2018-09-09')),
PARTITION PT20180910 VALUES LESS THAN (TO_DAYS('2018-09-10')),
PARTITION PT20180911 VALUES LESS THAN (TO_DAYS('2018-09-11')),
PARTITION PT20180912 VALUES LESS THAN (TO_DAYS('2018-09-12')),
PARTITION PT20180913 VALUES LESS THAN (TO_DAYS('2018-09-13')),
PARTITION PT20180914 VALUES LESS THAN (TO_DAYS('2018-09-14')),
PARTITION PT20180915 VALUES LESS THAN (TO_DAYS('2018-09-15')),
PARTITION PT20180916 VALUES LESS THAN (TO_DAYS('2018-09-16')),
PARTITION PT20180917 VALUES LESS THAN (TO_DAYS('2018-09-17')),
PARTITION PT20180918 VALUES LESS THAN (TO_DAYS('2018-09-18')),
PARTITION PT20180919 VALUES LESS THAN (TO_DAYS('2018-09-19')),
PARTITION PT20180920 VALUES LESS THAN (TO_DAYS('2018-09-20')),
PARTITION PT20180921 VALUES LESS THAN (TO_DAYS('2018-09-21')),
PARTITION PT20180922 VALUES LESS THAN (TO_DAYS('2018-09-22')),
PARTITION PT20180923 VALUES LESS THAN (TO_DAYS('2018-09-23')),
PARTITION PT20180924 VALUES LESS THAN (TO_DAYS('2018-09-24')),
PARTITION PT20180925 VALUES LESS THAN (TO_DAYS('2018-09-25')),
PARTITION PT20180926 VALUES LESS THAN (TO_DAYS('2018-09-26')),
PARTITION PT20180927 VALUES LESS THAN (TO_DAYS('2018-09-27')),
PARTITION PT20180928 VALUES LESS THAN (TO_DAYS('2018-09-28')),
PARTITION PT20180929 VALUES LESS THAN (TO_DAYS('2018-09-29')),
PARTITION PT20180930 VALUES LESS THAN (TO_DAYS('2018-09-30')),
PARTITION PT20181001 VALUES LESS THAN (TO_DAYS('2018-10-01'))
);

alter table t_dialog_201809 comment '机器对话流水表';

/*==============================================================*/
/* Index: idx_date_sign                                         */
/*==============================================================*/
create index idx_date_sign on t_dialog_201809
(
   date_sign
);

/*==============================================================*/
/* Table: t_dialog_answer                                       */
/*==============================================================*/
create table t_dialog_answer
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   answer               text,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   primary key (id, appid)
);

alter table t_dialog_answer comment '机器对话流水答案表';

/*==============================================================*/
/* Table: t_dialog_back                                         */
/*==============================================================*/
create table t_dialog_back
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   exact_time           datetime not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_dialog_back comment '原始dialog数据记录表';

/*==============================================================*/
/* Index: idx_exact_time                                        */
/*==============================================================*/
create index idx_exact_time on t_dialog_back
(
   exact_time
);

/*==============================================================*/
/* Table: t_dialog_detail_201807                                */
/*==============================================================*/
create table t_dialog_detail_201807
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   exact_time           datetime not null,
   recv_time            datetime not null,
   retmsg               varchar(250),
   direct_faq_id        varchar(10) not null,
   indirect_faq_id      varchar(100) not null,
   direct_faq           varchar(400),
   indirect_faq         varchar(4000),
   user_ip              varchar(50),
   query                varchar(3000),
   biz_portal           varchar(30),
   biz_ret_code         tinyint,
   biz_docid            varchar(800),
   biz_ret_str          varchar(50),
   ccd_cost             int,
   x_forward            varchar(20),
   biz_cost             int,
   answer_index         varchar(10),
   place                varchar(50),
   other                varchar(50),
   sen_level            tinyint unsigned,
   sen_words            varchar(100),
   confidence           varchar(100),
   primary key (id, appid, exact_time, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(exact_time))(
PARTITION PT20180702 VALUES LESS THAN (TO_DAYS('2018-07-02')),
PARTITION PT20180703 VALUES LESS THAN (TO_DAYS('2018-07-03')),
PARTITION PT20180704 VALUES LESS THAN (TO_DAYS('2018-07-04')),
PARTITION PT20180705 VALUES LESS THAN (TO_DAYS('2018-07-05')),
PARTITION PT20180706 VALUES LESS THAN (TO_DAYS('2018-07-06')),
PARTITION PT20180707 VALUES LESS THAN (TO_DAYS('2018-07-07')),
PARTITION PT20180708 VALUES LESS THAN (TO_DAYS('2018-07-08')),
PARTITION PT20180709 VALUES LESS THAN (TO_DAYS('2018-07-09')),
PARTITION PT20180710 VALUES LESS THAN (TO_DAYS('2018-07-10')),
PARTITION PT20180711 VALUES LESS THAN (TO_DAYS('2018-07-11')),
PARTITION PT20180712 VALUES LESS THAN (TO_DAYS('2018-07-12')),
PARTITION PT20180713 VALUES LESS THAN (TO_DAYS('2018-07-13')),
PARTITION PT20180714 VALUES LESS THAN (TO_DAYS('2018-07-14')),
PARTITION PT20180715 VALUES LESS THAN (TO_DAYS('2018-07-15')),
PARTITION PT20180716 VALUES LESS THAN (TO_DAYS('2018-07-16')),
PARTITION PT20180717 VALUES LESS THAN (TO_DAYS('2018-07-17')),
PARTITION PT20180718 VALUES LESS THAN (TO_DAYS('2018-07-18')),
PARTITION PT20180719 VALUES LESS THAN (TO_DAYS('2018-07-19')),
PARTITION PT20180720 VALUES LESS THAN (TO_DAYS('2018-07-20')),
PARTITION PT20180721 VALUES LESS THAN (TO_DAYS('2018-07-21')),
PARTITION PT20180722 VALUES LESS THAN (TO_DAYS('2018-07-22')),
PARTITION PT20180723 VALUES LESS THAN (TO_DAYS('2018-07-23')),
PARTITION PT20180724 VALUES LESS THAN (TO_DAYS('2018-07-24')),
PARTITION PT20180725 VALUES LESS THAN (TO_DAYS('2018-07-25')),
PARTITION PT20180726 VALUES LESS THAN (TO_DAYS('2018-07-26')),
PARTITION PT20180727 VALUES LESS THAN (TO_DAYS('2018-07-27')),
PARTITION PT20180728 VALUES LESS THAN (TO_DAYS('2018-07-28')),
PARTITION PT20180729 VALUES LESS THAN (TO_DAYS('2018-07-29')),
PARTITION PT20180730 VALUES LESS THAN (TO_DAYS('2018-07-30')),
PARTITION PT20180731 VALUES LESS THAN (TO_DAYS('2018-07-31')),
PARTITION PT20180801 VALUES LESS THAN (TO_DAYS('2018-08-01'))
);

alter table t_dialog_detail_201807 comment '机器对话流水明细表';

/*==============================================================*/
/* Table: t_dialog_detail_201808                                */
/*==============================================================*/
create table t_dialog_detail_201808
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   exact_time           datetime not null,
   recv_time            datetime not null,
   retmsg               varchar(250),
   direct_faq_id        varchar(10) not null,
   indirect_faq_id      varchar(100) not null,
   direct_faq           varchar(400),
   indirect_faq         varchar(4000),
   user_ip              varchar(50),
   query                varchar(3000),
   biz_portal           varchar(30),
   biz_ret_code         tinyint,
   biz_docid            varchar(800),
   biz_ret_str          varchar(50),
   ccd_cost             int,
   x_forward            varchar(20),
   biz_cost             int,
   answer_index         varchar(10),
   place                varchar(50),
   other                varchar(50),
   sen_level            tinyint unsigned,
   sen_words            varchar(100),
   confidence           varchar(100),
   primary key (id, appid, exact_time, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(exact_time))(
PARTITION PT20180802 VALUES LESS THAN (TO_DAYS('2018-08-02')),
PARTITION PT20180803 VALUES LESS THAN (TO_DAYS('2018-08-03')),
PARTITION PT20180804 VALUES LESS THAN (TO_DAYS('2018-08-04')),
PARTITION PT20180805 VALUES LESS THAN (TO_DAYS('2018-08-05')),
PARTITION PT20180806 VALUES LESS THAN (TO_DAYS('2018-08-06')),
PARTITION PT20180807 VALUES LESS THAN (TO_DAYS('2018-08-07')),
PARTITION PT20180808 VALUES LESS THAN (TO_DAYS('2018-08-08')),
PARTITION PT20180809 VALUES LESS THAN (TO_DAYS('2018-08-09')),
PARTITION PT20180810 VALUES LESS THAN (TO_DAYS('2018-08-10')),
PARTITION PT20180811 VALUES LESS THAN (TO_DAYS('2018-08-11')),
PARTITION PT20180812 VALUES LESS THAN (TO_DAYS('2018-08-12')),
PARTITION PT20180813 VALUES LESS THAN (TO_DAYS('2018-08-13')),
PARTITION PT20180814 VALUES LESS THAN (TO_DAYS('2018-08-14')),
PARTITION PT20180815 VALUES LESS THAN (TO_DAYS('2018-08-15')),
PARTITION PT20180816 VALUES LESS THAN (TO_DAYS('2018-08-16')),
PARTITION PT20180817 VALUES LESS THAN (TO_DAYS('2018-08-17')),
PARTITION PT20180818 VALUES LESS THAN (TO_DAYS('2018-08-18')),
PARTITION PT20180819 VALUES LESS THAN (TO_DAYS('2018-08-19')),
PARTITION PT20180820 VALUES LESS THAN (TO_DAYS('2018-08-20')),
PARTITION PT20180821 VALUES LESS THAN (TO_DAYS('2018-08-21')),
PARTITION PT20180822 VALUES LESS THAN (TO_DAYS('2018-08-22')),
PARTITION PT20180823 VALUES LESS THAN (TO_DAYS('2018-08-23')),
PARTITION PT20180824 VALUES LESS THAN (TO_DAYS('2018-08-24')),
PARTITION PT20180825 VALUES LESS THAN (TO_DAYS('2018-08-25')),
PARTITION PT20180826 VALUES LESS THAN (TO_DAYS('2018-08-26')),
PARTITION PT20180827 VALUES LESS THAN (TO_DAYS('2018-08-27')),
PARTITION PT20180828 VALUES LESS THAN (TO_DAYS('2018-08-28')),
PARTITION PT20180829 VALUES LESS THAN (TO_DAYS('2018-08-29')),
PARTITION PT20180830 VALUES LESS THAN (TO_DAYS('2018-08-30')),
PARTITION PT20180831 VALUES LESS THAN (TO_DAYS('2018-08-31')),
PARTITION PT20180901 VALUES LESS THAN (TO_DAYS('2018-09-01'))
);

alter table t_dialog_detail_201808 comment '机器对话流水明细表';

/*==============================================================*/
/* Table: t_dialog_detail_201809                                */
/*==============================================================*/
create table t_dialog_detail_201809
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   exact_time           datetime not null,
   recv_time            datetime not null,
   retmsg               varchar(250),
   direct_faq_id        varchar(10) not null,
   indirect_faq_id      varchar(100) not null,
   direct_faq           varchar(400),
   indirect_faq         varchar(4000),
   user_ip              varchar(50),
   query                varchar(3000),
   biz_portal           varchar(30),
   biz_ret_code         tinyint,
   biz_docid            varchar(800),
   biz_ret_str          varchar(50),
   ccd_cost             int,
   x_forward            varchar(20),
   biz_cost             int,
   answer_index         varchar(10),
   place                varchar(50),
   other                varchar(50),
   sen_level            tinyint unsigned,
   sen_words            varchar(100),
   confidence           varchar(100),
   primary key (id, appid, exact_time, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(exact_time))(
PARTITION PT20180902 VALUES LESS THAN (TO_DAYS('2018-09-02')),
PARTITION PT20180903 VALUES LESS THAN (TO_DAYS('2018-09-03')),
PARTITION PT20180904 VALUES LESS THAN (TO_DAYS('2018-09-04')),
PARTITION PT20180905 VALUES LESS THAN (TO_DAYS('2018-09-05')),
PARTITION PT20180906 VALUES LESS THAN (TO_DAYS('2018-09-06')),
PARTITION PT20180907 VALUES LESS THAN (TO_DAYS('2018-09-07')),
PARTITION PT20180908 VALUES LESS THAN (TO_DAYS('2018-09-08')),
PARTITION PT20180909 VALUES LESS THAN (TO_DAYS('2018-09-09')),
PARTITION PT20180910 VALUES LESS THAN (TO_DAYS('2018-09-10')),
PARTITION PT20180911 VALUES LESS THAN (TO_DAYS('2018-09-11')),
PARTITION PT20180912 VALUES LESS THAN (TO_DAYS('2018-09-12')),
PARTITION PT20180913 VALUES LESS THAN (TO_DAYS('2018-09-13')),
PARTITION PT20180914 VALUES LESS THAN (TO_DAYS('2018-09-14')),
PARTITION PT20180915 VALUES LESS THAN (TO_DAYS('2018-09-15')),
PARTITION PT20180916 VALUES LESS THAN (TO_DAYS('2018-09-16')),
PARTITION PT20180917 VALUES LESS THAN (TO_DAYS('2018-09-17')),
PARTITION PT20180918 VALUES LESS THAN (TO_DAYS('2018-09-18')),
PARTITION PT20180919 VALUES LESS THAN (TO_DAYS('2018-09-19')),
PARTITION PT20180920 VALUES LESS THAN (TO_DAYS('2018-09-20')),
PARTITION PT20180921 VALUES LESS THAN (TO_DAYS('2018-09-21')),
PARTITION PT20180922 VALUES LESS THAN (TO_DAYS('2018-09-22')),
PARTITION PT20180923 VALUES LESS THAN (TO_DAYS('2018-09-23')),
PARTITION PT20180924 VALUES LESS THAN (TO_DAYS('2018-09-24')),
PARTITION PT20180925 VALUES LESS THAN (TO_DAYS('2018-09-25')),
PARTITION PT20180926 VALUES LESS THAN (TO_DAYS('2018-09-26')),
PARTITION PT20180927 VALUES LESS THAN (TO_DAYS('2018-09-27')),
PARTITION PT20180928 VALUES LESS THAN (TO_DAYS('2018-09-28')),
PARTITION PT20180929 VALUES LESS THAN (TO_DAYS('2018-09-29')),
PARTITION PT20180930 VALUES LESS THAN (TO_DAYS('2018-09-30')),
PARTITION PT20181001 VALUES LESS THAN (TO_DAYS('2018-10-01'))
);

alter table t_dialog_detail_201809 comment '机器对话流水明细表';

/*==============================================================*/
/* Table: t_faq_touch_overview                                  */
/*==============================================================*/
create table t_faq_touch_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   faq_id               int not null,
   faq                  varchar(500),
   category_id          varchar(20),
   touch_num            int,
   direct_num           int,
   recommend_num        int,
   recommend_ans_num    int,
   click_good           int,
   click_bad            int,
   touch_rate           varchar(30),
   zd_zrg               int,
   bd_zrg               int,
   total_zrg            int,
   click_good_rate      varchar(30),
   click_bad_rate       varchar(30),
   biz_type             varchar(10),
   total_click          int,
   zrg_rate             varchar(30),
   labels               varchar(100),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   primary key (id, appid)
);

alter table t_faq_touch_overview comment 'faq触达数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds_fi                                          */
/*==============================================================*/
create index idx_ad_ds_fi on t_faq_touch_overview
(
   appid,
   date_sign,
   faq_id
);

/*==============================================================*/
/* Table: t_feedback_back                                       */
/*==============================================================*/
create table t_feedback_back
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   exact_time           datetime not null,
   is_redo              tinyint not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_feedback_back comment '原始feedback数据记录表';

/*==============================================================*/
/* Index: idx_exact_time                                        */
/*==============================================================*/
create index idx_exact_time on t_feedback_back
(
   exact_time
);

/*==============================================================*/
/* Table: t_hot_area_fq_overview                                */
/*==============================================================*/
create table t_hot_area_fq_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   area_type            tinyint,
   area_name            char(10),
   faq_id               int,
   faq                  varchar(200),
   visit_num            int not null,
   visit_trend          char(20) not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   primary key (id, appid)
);

alter table t_hot_area_fq_overview comment '热点区域FAQ数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds_an                                          */
/*==============================================================*/
create index idx_ad_ds_an on t_hot_area_fq_overview
(
   appid,
   date_sign,
   area_name
);

/*==============================================================*/
/* Table: t_hot_area_overview                                   */
/*==============================================================*/
create table t_hot_area_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   area_type            tinyint,
   area_name            char(10),
   visit_num            int not null,
   visit_trend          char(20) not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   primary key (id, appid)
);

alter table t_hot_area_overview comment '热点区域数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds_at                                          */
/*==============================================================*/
create index idx_ad_ds_at on t_hot_area_overview
(
   appid,
   date_sign,
   area_name
);

/*==============================================================*/
/* Table: t_hot_faq_overview                                    */
/*==============================================================*/
create table t_hot_faq_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   faq_id               int unsigned,
   faq                  varchar(200) not null,
   visit_num            int not null,
   visit_trend          char(20) not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   primary key (id, appid)
);

alter table t_hot_faq_overview comment '热点FAQ数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds_fi                                          */
/*==============================================================*/
create index idx_ad_ds_fi on t_hot_faq_overview
(
   appid,
   date_sign,
   faq_id
);

/*==============================================================*/
/* Table: t_ip_area_match                                       */
/*==============================================================*/
create table t_ip_area_match
(
   id                   varchar(20) not null,
   country              varchar(100) not null,
   province             varchar(100) not null,
   city                 varchar(100) not null,
   district             varchar(100),
   ispp                 varchar(100),
   isp                  varchar(100),
   code                 int(11) not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   primary key (id)
);

alter table t_ip_area_match comment 'ip与地域映射表';

/*==============================================================*/
/* Table: t_judge_overview                                      */
/*==============================================================*/
create table t_judge_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_judge_overview comment '用户评价统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_judge_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_part_trend_overview                                 */
/*==============================================================*/
create table t_part_trend_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_part_trend_overview comment '耗时和访问量趋势统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_part_trend_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_province_overview                                   */
/*==============================================================*/
create table t_province_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_province_overview comment '各省faq数据热点分析统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_province_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_session_201807                                      */
/*==============================================================*/
create table t_session_201807
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   start_time           datetime not null,
   end_time             datetime not null,
   date_sign            char(10) not null,
   round_num            int(11) unsigned not null,
   account              varchar(100),
   user_ip              varchar(50),
   country              varchar(50),
   province             varchar(50),
   city                 varchar(50),
   sen_level            tinyint,
   judge_type           tinyint,
   click_good_num       int unsigned,
   click_bad_num        int unsigned,
   source               varchar(30),
   zrg_type             char(6),
   zrg_at               tinyint,
   business_name        varchar(30),
   check_tag            varchar(10),
   has_hanxuan          tinyint unsigned,
   has_reject           tinyint unsigned,
   has_ans1             tinyint unsigned,
   has_ans3             tinyint unsigned,
   has_assistant        tinyint unsigned,
   has_adopted          tinyint unsigned,
   has_secondedit       tinyint unsigned,
   has_abandoned        tinyint unsigned,
   channel              varchar(100),
   cid                  varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   client               varchar(30),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   reserved1            varchar(100),
   reserved2            varchar(100),
   reserved3            varchar(100),
   reserved4            varchar(100),
   reserved5            varchar(100),
   primary key (id, start_time, appid, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(start_time))(
PARTITION PT20180702 VALUES LESS THAN (TO_DAYS('2018-07-02')),
PARTITION PT20180703 VALUES LESS THAN (TO_DAYS('2018-07-03')),
PARTITION PT20180704 VALUES LESS THAN (TO_DAYS('2018-07-04')),
PARTITION PT20180705 VALUES LESS THAN (TO_DAYS('2018-07-05')),
PARTITION PT20180706 VALUES LESS THAN (TO_DAYS('2018-07-06')),
PARTITION PT20180707 VALUES LESS THAN (TO_DAYS('2018-07-07')),
PARTITION PT20180708 VALUES LESS THAN (TO_DAYS('2018-07-08')),
PARTITION PT20180709 VALUES LESS THAN (TO_DAYS('2018-07-09')),
PARTITION PT20180710 VALUES LESS THAN (TO_DAYS('2018-07-10')),
PARTITION PT20180711 VALUES LESS THAN (TO_DAYS('2018-07-11')),
PARTITION PT20180712 VALUES LESS THAN (TO_DAYS('2018-07-12')),
PARTITION PT20180713 VALUES LESS THAN (TO_DAYS('2018-07-13')),
PARTITION PT20180714 VALUES LESS THAN (TO_DAYS('2018-07-14')),
PARTITION PT20180715 VALUES LESS THAN (TO_DAYS('2018-07-15')),
PARTITION PT20180716 VALUES LESS THAN (TO_DAYS('2018-07-16')),
PARTITION PT20180717 VALUES LESS THAN (TO_DAYS('2018-07-17')),
PARTITION PT20180718 VALUES LESS THAN (TO_DAYS('2018-07-18')),
PARTITION PT20180719 VALUES LESS THAN (TO_DAYS('2018-07-19')),
PARTITION PT20180720 VALUES LESS THAN (TO_DAYS('2018-07-20')),
PARTITION PT20180721 VALUES LESS THAN (TO_DAYS('2018-07-21')),
PARTITION PT20180722 VALUES LESS THAN (TO_DAYS('2018-07-22')),
PARTITION PT20180723 VALUES LESS THAN (TO_DAYS('2018-07-23')),
PARTITION PT20180724 VALUES LESS THAN (TO_DAYS('2018-07-24')),
PARTITION PT20180725 VALUES LESS THAN (TO_DAYS('2018-07-25')),
PARTITION PT20180726 VALUES LESS THAN (TO_DAYS('2018-07-26')),
PARTITION PT20180727 VALUES LESS THAN (TO_DAYS('2018-07-27')),
PARTITION PT20180728 VALUES LESS THAN (TO_DAYS('2018-07-28')),
PARTITION PT20180729 VALUES LESS THAN (TO_DAYS('2018-07-29')),
PARTITION PT20180730 VALUES LESS THAN (TO_DAYS('2018-07-30')),
PARTITION PT20180731 VALUES LESS THAN (TO_DAYS('2018-07-31')),
PARTITION PT20180801 VALUES LESS THAN (TO_DAYS('2018-08-01'))
);

alter table t_session_201807 comment '会话表';

/*==============================================================*/
/* Index: idx_date_sign                                         */
/*==============================================================*/
create index idx_date_sign on t_session_201807
(
   date_sign
);

/*==============================================================*/
/* Index: idx_account                                           */
/*==============================================================*/
create index idx_account on t_session_201807
(
   account
);

/*==============================================================*/
/* Index: idx_st_so_rn                                          */
/*==============================================================*/
create index idx_st_so_rn on t_session_201807
(
   start_time,
   round_num,
   source
);

/*==============================================================*/
/* Table: t_session_201808                                      */
/*==============================================================*/
create table t_session_201808
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   start_time           datetime not null,
   end_time             datetime not null,
   date_sign            char(10) not null,
   round_num            int(11) unsigned not null,
   account              varchar(100),
   user_ip              varchar(50),
   country              varchar(50),
   province             varchar(50),
   city                 varchar(50),
   sen_level            tinyint,
   judge_type           tinyint,
   click_good_num       int unsigned,
   click_bad_num        int unsigned,
   source               varchar(30),
   zrg_type             char(6),
   zrg_at               tinyint,
   business_name        varchar(30),
   check_tag            varchar(10),
   has_hanxuan          tinyint unsigned,
   has_reject           tinyint unsigned,
   has_ans1             tinyint unsigned,
   has_ans3             tinyint unsigned,
   has_assistant        tinyint unsigned,
   has_adopted          tinyint unsigned,
   has_secondedit       tinyint unsigned,
   has_abandoned        tinyint unsigned,
   channel              varchar(100),
   cid                  varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   client               varchar(30),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   reserved1            varchar(100),
   reserved2            varchar(100),
   reserved3            varchar(100),
   reserved4            varchar(100),
   reserved5            varchar(100),
   primary key (id, start_time, appid, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(start_time))(
PARTITION PT20180802 VALUES LESS THAN (TO_DAYS('2018-08-02')),
PARTITION PT20180803 VALUES LESS THAN (TO_DAYS('2018-08-03')),
PARTITION PT20180804 VALUES LESS THAN (TO_DAYS('2018-08-04')),
PARTITION PT20180805 VALUES LESS THAN (TO_DAYS('2018-08-05')),
PARTITION PT20180806 VALUES LESS THAN (TO_DAYS('2018-08-06')),
PARTITION PT20180807 VALUES LESS THAN (TO_DAYS('2018-08-07')),
PARTITION PT20180808 VALUES LESS THAN (TO_DAYS('2018-08-08')),
PARTITION PT20180809 VALUES LESS THAN (TO_DAYS('2018-08-09')),
PARTITION PT20180810 VALUES LESS THAN (TO_DAYS('2018-08-10')),
PARTITION PT20180811 VALUES LESS THAN (TO_DAYS('2018-08-11')),
PARTITION PT20180812 VALUES LESS THAN (TO_DAYS('2018-08-12')),
PARTITION PT20180813 VALUES LESS THAN (TO_DAYS('2018-08-13')),
PARTITION PT20180814 VALUES LESS THAN (TO_DAYS('2018-08-14')),
PARTITION PT20180815 VALUES LESS THAN (TO_DAYS('2018-08-15')),
PARTITION PT20180816 VALUES LESS THAN (TO_DAYS('2018-08-16')),
PARTITION PT20180817 VALUES LESS THAN (TO_DAYS('2018-08-17')),
PARTITION PT20180818 VALUES LESS THAN (TO_DAYS('2018-08-18')),
PARTITION PT20180819 VALUES LESS THAN (TO_DAYS('2018-08-19')),
PARTITION PT20180820 VALUES LESS THAN (TO_DAYS('2018-08-20')),
PARTITION PT20180821 VALUES LESS THAN (TO_DAYS('2018-08-21')),
PARTITION PT20180822 VALUES LESS THAN (TO_DAYS('2018-08-22')),
PARTITION PT20180823 VALUES LESS THAN (TO_DAYS('2018-08-23')),
PARTITION PT20180824 VALUES LESS THAN (TO_DAYS('2018-08-24')),
PARTITION PT20180825 VALUES LESS THAN (TO_DAYS('2018-08-25')),
PARTITION PT20180826 VALUES LESS THAN (TO_DAYS('2018-08-26')),
PARTITION PT20180827 VALUES LESS THAN (TO_DAYS('2018-08-27')),
PARTITION PT20180828 VALUES LESS THAN (TO_DAYS('2018-08-28')),
PARTITION PT20180829 VALUES LESS THAN (TO_DAYS('2018-08-29')),
PARTITION PT20180830 VALUES LESS THAN (TO_DAYS('2018-08-30')),
PARTITION PT20180831 VALUES LESS THAN (TO_DAYS('2018-08-31')),
PARTITION PT20180901 VALUES LESS THAN (TO_DAYS('2018-09-01'))
);

alter table t_session_201808 comment '会话表';

/*==============================================================*/
/* Index: idx_date_sign                                         */
/*==============================================================*/
create index idx_date_sign on t_session_201808
(
   date_sign
);

/*==============================================================*/
/* Index: idx_account                                           */
/*==============================================================*/
create index idx_account on t_session_201808
(
   account
);

/*==============================================================*/
/* Index: idx_st_so_rn                                          */
/*==============================================================*/
create index idx_st_so_rn on t_session_201808
(
   start_time,
   round_num,
   source
);

/*==============================================================*/
/* Table: t_session_201809                                      */
/*==============================================================*/
create table t_session_201809
(
   id                   varchar(100) not null,
   appid                varchar(30) not null,
   date_month           char(10) not null,
   start_time           datetime not null,
   end_time             datetime not null,
   date_sign            char(10) not null,
   round_num            int(11) unsigned not null,
   account              varchar(100),
   user_ip              varchar(50),
   country              varchar(50),
   province             varchar(50),
   city                 varchar(50),
   sen_level            tinyint,
   judge_type           tinyint,
   click_good_num       int unsigned,
   click_bad_num        int unsigned,
   source               varchar(30),
   zrg_type             char(6),
   zrg_at               tinyint,
   business_name        varchar(30),
   check_tag            varchar(10),
   has_hanxuan          tinyint unsigned,
   has_reject           tinyint unsigned,
   has_ans1             tinyint unsigned,
   has_ans3             tinyint unsigned,
   has_assistant        tinyint unsigned,
   has_adopted          tinyint unsigned,
   has_secondedit       tinyint unsigned,
   has_abandoned        tinyint unsigned,
   channel              varchar(100),
   cid                  varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   client               varchar(30),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   reserved1            varchar(100),
   reserved2            varchar(100),
   reserved3            varchar(100),
   reserved4            varchar(100),
   reserved5            varchar(100),
   primary key (id, start_time, appid, date_month)
)
ENGINE = INNODB PARTITION BY RANGE (TO_DAYS(start_time))(
PARTITION PT20180902 VALUES LESS THAN (TO_DAYS('2018-09-02')),
PARTITION PT20180903 VALUES LESS THAN (TO_DAYS('2018-09-03')),
PARTITION PT20180904 VALUES LESS THAN (TO_DAYS('2018-09-04')),
PARTITION PT20180905 VALUES LESS THAN (TO_DAYS('2018-09-05')),
PARTITION PT20180906 VALUES LESS THAN (TO_DAYS('2018-09-06')),
PARTITION PT20180907 VALUES LESS THAN (TO_DAYS('2018-09-07')),
PARTITION PT20180908 VALUES LESS THAN (TO_DAYS('2018-09-08')),
PARTITION PT20180909 VALUES LESS THAN (TO_DAYS('2018-09-09')),
PARTITION PT20180910 VALUES LESS THAN (TO_DAYS('2018-09-10')),
PARTITION PT20180911 VALUES LESS THAN (TO_DAYS('2018-09-11')),
PARTITION PT20180912 VALUES LESS THAN (TO_DAYS('2018-09-12')),
PARTITION PT20180913 VALUES LESS THAN (TO_DAYS('2018-09-13')),
PARTITION PT20180914 VALUES LESS THAN (TO_DAYS('2018-09-14')),
PARTITION PT20180915 VALUES LESS THAN (TO_DAYS('2018-09-15')),
PARTITION PT20180916 VALUES LESS THAN (TO_DAYS('2018-09-16')),
PARTITION PT20180917 VALUES LESS THAN (TO_DAYS('2018-09-17')),
PARTITION PT20180918 VALUES LESS THAN (TO_DAYS('2018-09-18')),
PARTITION PT20180919 VALUES LESS THAN (TO_DAYS('2018-09-19')),
PARTITION PT20180920 VALUES LESS THAN (TO_DAYS('2018-09-20')),
PARTITION PT20180921 VALUES LESS THAN (TO_DAYS('2018-09-21')),
PARTITION PT20180922 VALUES LESS THAN (TO_DAYS('2018-09-22')),
PARTITION PT20180923 VALUES LESS THAN (TO_DAYS('2018-09-23')),
PARTITION PT20180924 VALUES LESS THAN (TO_DAYS('2018-09-24')),
PARTITION PT20180925 VALUES LESS THAN (TO_DAYS('2018-09-25')),
PARTITION PT20180926 VALUES LESS THAN (TO_DAYS('2018-09-26')),
PARTITION PT20180927 VALUES LESS THAN (TO_DAYS('2018-09-27')),
PARTITION PT20180928 VALUES LESS THAN (TO_DAYS('2018-09-28')),
PARTITION PT20180929 VALUES LESS THAN (TO_DAYS('2018-09-29')),
PARTITION PT20180930 VALUES LESS THAN (TO_DAYS('2018-09-30')),
PARTITION PT20181001 VALUES LESS THAN (TO_DAYS('2018-10-01'))
);

alter table t_session_201809 comment '会话表';

/*==============================================================*/
/* Index: idx_date_sign                                         */
/*==============================================================*/
create index idx_date_sign on t_session_201809
(
   date_sign
);

/*==============================================================*/
/* Index: idx_account                                           */
/*==============================================================*/
create index idx_account on t_session_201809
(
   account
);

/*==============================================================*/
/* Index: idx_st_so_rn                                          */
/*==============================================================*/
create index idx_st_so_rn on t_session_201809
(
   start_time,
   round_num,
   source
);

/*==============================================================*/
/* Table: t_touch_overview                                      */
/*==============================================================*/
create table t_touch_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_touch_overview comment 'FAQ触达率数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_touch_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_whole_service_overview                              */
/*==============================================================*/
create table t_whole_service_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_whole_service_overview comment '新的整体趋势统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_whole_service_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_whole_trend_overview                                */
/*==============================================================*/
create table t_whole_trend_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_whole_trend_overview comment '旧的整体趋势统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_whole_trend_overview
(
   appid,
   date_sign
);

/*==============================================================*/
/* Table: t_zrg_overview                                        */
/*==============================================================*/
create table t_zrg_overview
(
   id                   bigint unsigned not null,
   appid                varchar(30) not null,
   date_sign            char(10) not null,
   is_total             tinyint unsigned not null,
   cid                  varchar(200),
   client               varchar(200),
   eid                  varchar(200),
   lables               varchar(200),
   im                   varchar(200),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   data                 text not null,
   primary key (id, appid)
);

alter table t_zrg_overview comment '转人工数据统计表';

/*==============================================================*/
/* Index: idx_ad_ds                                             */
/*==============================================================*/
create index idx_ad_ds on t_zrg_overview
(
   appid,
   date_sign
);

