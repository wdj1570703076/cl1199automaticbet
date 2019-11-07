package com.cl1199.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.cl1199.dao.BetDao;
import com.cl1199.dao.HistoryDao;
import com.cl1199.dao.LoginDao;
import com.cl1199.dao.SaleissueDao;
import com.cl1199.util.AESUtil;


public class Api {
	// Cookie自动维护对象
	private CookieStore cookieStore = new BasicCookieStore();
	private HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();	
	private static String identity = null;	
	private static String betMultiple = "1";//倍数	
	private static String moneyUnit = "7";//金额
	/**
	 * 下注请求参数
	 */
	private String loginName;
	private String pwd;
	private String betBonusGroup;	
	private JTextArea logArea;
	private boolean stop;
	private String roadBox;
	/**
	 * 请求地址
	 */
	private static String doLoginUrl = "https://cl1199.com/user/login";//登陆地址	
	private static String doBetUrl = "https://cl1199.com/lottery/bet";//下注地址
	private static String doHistoryUrl = "https://cl1199.com/lottery/lotterycode";//开奖历史接口
	private static String doSaleissueUrl = "https://cl1199.com/lottery/saleissue";//当前信息

	public Api(String betBonusGroup) {
		this.betBonusGroup = betBonusGroup;
	}

	public void doIt() {
		doLogin();		
	}
	
	/**
	 * 登陆
	 */
	public void doLogin() {		
		try {
			HttpResponse responseLogin = this.client.execute(getHttpPost(doLoginUrl, 1, null, null));
			String loginStr = EntityUtils.toString(responseLogin.getEntity(), Charset.defaultCharset());
			LoginDao loginDao = JSONObject.parseObject(AESUtil.aesDecodeStr(loginStr), LoginDao.class);
			if (loginDao.getStatus() == 1) {
				identity = loginDao.getData().getKey();
				logArea.append("登陆成功》》》》》》》》开始下注\n");
				Thread.sleep(3000);
				doBet();	
			}else {
				logArea.append("登陆失败》》》》》》》》原因："+loginDao.getMsg()+"\n");
			}
		} catch (Exception e) {
			logArea.append("登陆异常》》》》》》》》"+e+"\n");
		}
		logArea.setCaretPosition( logArea.getDocument().getLength());		
	}
	
	/**
	 * 下注
	 */
	public void doBet() {
		while (!stop) {
			try {
				HttpResponse responseHistory = this.client.execute(getHttpPost(doHistoryUrl, 2, null, null));	
				HistoryDao historyDao = JSONObject.parseObject(AESUtil.aesDecodeStr(EntityUtils.toString(responseHistory.getEntity(), Charset.defaultCharset())), HistoryDao.class);
				
				HttpResponse responseSaleissue = this.client.execute(getHttpPost(doSaleissueUrl, 4, null, null));	
				SaleissueDao saleissueDao = JSONObject.parseObject(AESUtil.aesDecodeStr(EntityUtils.toString(responseSaleissue.getEntity(), Charset.defaultCharset())), SaleissueDao.class);
				
				if (historyDao.getStatus() == 1 && saleissueDao.getStatus() == 1) {
					String betCodes = "";
					HttpResponse responseBet = this.client.execute(getHttpPost(doBetUrl, 3, saleissueDao.getData().getIssue(), betCodes));
					String respStr = EntityUtils.toString(responseBet.getEntity(), Charset.defaultCharset());
					BetDao betDao = JSONObject.parseObject(AESUtil.aesDecodeStr(respStr), BetDao.class);				
					if (betDao.getStatus() == 1) {
						System.out.println();
						logArea.append("下注成功!!!》》》剩余金额： "+Double.parseDouble(saleissueDao.getUser().getAvaiableAmount())+"》》》》订单号："+betDao.getData()[0]+"\n");
					}else {
						logArea.append("下注失败!!!》》》》》》》》》原因："+betDao.getMsg()+"\n");
					}
				}else {
					logArea.append("查询失败》》》》》》》》原因："+historyDao.getMsg()+"\n");
				}
			} catch (Exception e) {
				logArea.append("异常》》》》》》》》"+e+"\n");
			}
			logArea.setCaretPosition( logArea.getDocument().getLength());
		}	
		logArea.append("==============任务结束================\n");
	}
	
	private HttpPost getHttpPost(String url, int flag, String Issue, String betCodes) {
		HttpPost httpPost = new HttpPost(url);
		Map<String, String> headerParams = new HashMap<String, String>();
		if (flag!=1 && StringUtils.isBlank(identity)) {
			return null;
		}
		headerParams.put("identity", identity);
		if (headerParams != null && headerParams.size() > 0) {
			for (Map.Entry<String, String> entry : headerParams.entrySet()) {
				httpPost.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
			}
		}	
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		switch (flag) {
		case 1:
			nvps.add(new BasicNameValuePair("loginName", loginName));
			nvps.add(new BasicNameValuePair("pwd", pwd));
			break;
		case 2:
			nvps.add(new BasicNameValuePair("type", "430"));
			nvps.add(new BasicNameValuePair("top", "1"));
			break;
		case 3:
			nvps.add(new BasicNameValuePair("LotteryType", "430"));
			nvps.add(new BasicNameValuePair("IsChase", "false"));
			nvps.add(new BasicNameValuePair("IsStopAfterWin", "true"));
			nvps.add(new BasicNameValuePair("IsAllIn", "false"));
			nvps.add(new BasicNameValuePair("LstOrder[0][PlayType]", "836"));
			nvps.add(new BasicNameValuePair("LstOrder[0][BetPlan]", ""));
			nvps.add(new BasicNameValuePair("LstOrder[0][IsZip]", "false"));
			nvps.add(new BasicNameValuePair("LstOrder[0][BetBonusGroup]", betBonusGroup));
			nvps.add(new BasicNameValuePair("LstOrder[0][MoneyUnit]", moneyUnit));
			nvps.add(new BasicNameValuePair("LstOrder[0][BetMultiple]", betMultiple));
			nvps.add(new BasicNameValuePair("Issue", Issue));
			nvps.add(new BasicNameValuePair("LstOrder[0][BetCodes]", betCodes));
			break;
		case 4:
			nvps.add(new BasicNameValuePair("type", "430"));
			break;
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
		return httpPost;
	}
	
	public void stopByMark() {		
        stop = true;
    }
	
	public void startByMark(String loginName, String pwd, JTextArea logArea, String roadBox) {
		this.loginName = loginName;
		this.pwd = pwd;
		this.logArea = logArea;
		this.roadBox = roadBox;
        stop = false;
    }
}
