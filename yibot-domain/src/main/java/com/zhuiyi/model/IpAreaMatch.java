package com.zhuiyi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_ip_area_match")
public class IpAreaMatch implements Serializable {
    /**
     * id  db_column: id
     */
    @Id
    @Column(name = "id", length = 20, nullable = false)
    private String id;
    /**
     * country  db_column: country
     */
    @NotBlank @Length(max=100)
    @Column(name = "country", length = 100, nullable = false)
    private String country;
    /**
     * province  db_column: province
     */
    @NotBlank @Length(max=100)
    @Column(name = "province", length = 100, nullable = false)
    private String province;
    /**
     * city  db_column: city
     */
    @NotBlank @Length(max=100)
    @Column(name = "city", length = 100, nullable = false)
    private String city;
    /**
     * district  db_column: district
     */
    @Length(max=100)
    @Column(name = "district", length = 100)
    private String district;
    /**
     * ispp  db_column: ispp
     */
    @Length(max=100)
    @Column(name = "ispp", length = 100)
    private String ispp;
    /**
     * isp  db_column: isp
     */
    @Length(max=100)
    @Column(name = "isp", length = 100)
    private String isp;
    /**
     * code  db_column: code
     */
    @NotNull @Max(9999999999L)
    @Column(name = "code", length = 10, nullable = false)
    private Integer code;
    /**
     * gmtCreate  db_column: gmt_create
     */
    @NotNull
    @Column(name = "gmt_create", length = 19, nullable = false)
    private Date gmtCreate;
    /**
     * gmtModified  db_column: gmt_modified
     */
    @NotNull
    @Column(name = "gmt_modified", length = 19, nullable = false)
    private Date gmtModified;
}


