package com.zhuiyi.repository;

import com.zhuiyi.model.IpAreaMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */

public interface IpAreaMatchRepository extends JpaRepository<IpAreaMatch, String>, JpaSpecificationExecutor<IpAreaMatch>, Serializable{

}

