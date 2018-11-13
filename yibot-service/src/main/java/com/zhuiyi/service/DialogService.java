package com.zhuiyi.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.zhuiyi.model.Dialog;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/24
 * description:
 * own: zhuiyi
 */

public interface DialogService extends CommonService<Dialog,String>{
	
	/**
	* 此方法描述的是：
	* @author: klauszhou@wezhuiyi.com
	* @version: 2018年8月22日 下午2:33:26
	*/
	public List<Dialog> getDialogLogs(String appid, String dateMongth, String id,PageRequest pageRequest);
	
	/**
	* 此方法描述的是：
	* @author: klauszhou@wezhuiyi.com
	* @version: 2018年8月22日 下午2:33:28
	*/
	public Dialog getDialogLogs(String appid, String dateMongth, String id);
	
	/**
	* 此方法描述的是：
	* @author: klauszhou@wezhuiyi.com
	* @version: 2018年8月22日 下午2:33:30
	*/
	public Dialog getDialogLogs(
    		Long bid,
    		Long pid,
    		int pageno,
    		int pagesize,
    		Date stadate,
    		Date enddate,
    		String faqid,
    		String faqin,
    		String faqtype,
    		String usereval,
    		String answertype,
    		String haszrg,
    		String hashanxuan,
    		String hassenword,
    		String hasreject,
    		String address,
    		String query,
    		String userid) ;

}

