package com.cl1199;

import com.cl1199.service.Api;

/**
 * 自动投注请求入口
 * @author wdj
 */
public class BetController {
	
	private static String loginName = "lin2732903";//账号
	
	private static String pwd = "a123456";//密码
	
	private static String betMultiple = "1";//倍数
	
	private static String moneyUnit = "7";//金额
	
	private static String betBonusGroup = "1998";//奖金组
	
	public static void main(String[] args) {
        new Api(loginName , pwd , betMultiple, moneyUnit, betBonusGroup).doIt();
	}
}
