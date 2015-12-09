/**
 * 
 */
package edu.fjnu.cerulean.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 登录到咕咚并且跳转到运动->手机咕咚页面
 * @author Cerulean
 * 
 */
public class ClientCodoonSystem {

	public static String my_routes_htmlStr;
	private static String _flash_id;
	private static String token;
	public static int flag_getToday = 0;

	public static HttpClient httpClient = new DefaultHttpClient(
			new ThreadSafeClientConnManager());

	private static boolean isClientCodoonSysSuccess1(String login_id,
			String password) {
		HttpPost httpPost = new HttpPost("http://sso.codoon.com/login");

		// 填充表单
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("login_id", login_id));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("code", ""));
		nvps.add(new BasicNameValuePair("forever", "on"));
		nvps.add(new BasicNameValuePair("app_id", "www"));
		nvps.add(new BasicNameValuePair("next", "/"));
		// nvps.add(new BasicNameValuePair("", "登录"));
		// 设置字符
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		System.out.println(nvps.toString());
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			// 如果登录成功 会返回302
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpPost.getURI());

			HttpEntity entity = response.getEntity();

			System.out.println("response content "
					+ EntityUtils.toString(entity, "UTF-8"));

			if (result.equals("HTTP/1.1 302 Found")) {
				String temp = response.getFirstHeader("Location").toString();
				System.out.println("temp:" + temp);
				String[] strarray = temp.split(" ", 2);
				_flash_id = strarray[1];
				System.out.println("_flash_id:" + _flash_id);
				return true;
			} else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isClientCodoonSysSuccess2() {
		HttpGet httpGet = new HttpGet("http://sso.codoon.com" + _flash_id);
		System.out.println("http://sso.codoon.com" + _flash_id);

		// 填充表单
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String[] strarray = _flash_id.split("=", 2);
		_flash_id = strarray[1];
		nvps.add(new BasicNameValuePair("_flash_id", _flash_id));
		// 停止自动重定向
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects", false);
		httpGet.setParams(params);
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		System.out.println(nvps.toString());
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			// 如果登录成功 会返回302
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpGet.getURI());

			HttpEntity entity = response.getEntity();

			System.out.println("response content "
					+ EntityUtils.toString(entity, "UTF-8"));

			if (result.equals("HTTP/1.1 302 Found")) {
				String temp = response.getFirstHeader("Location").toString();
				System.out.println("temp:" + temp);
				String[] strarray1 = temp.split(" ", 2);
				token = strarray1[1];
				System.out.println("token:" + token);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isClientCodoonSysSuccess3() {
		HttpGet httpGet = new HttpGet(token);
		System.out.println(token);

		// 填充表单
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String[] strarray = token.split("=", 2);
		token = strarray[1];
		nvps.add(new BasicNameValuePair("token", token));
		nvps.add(new BasicNameValuePair("next", "/"));
		// 停止自动重定向
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects", false);
		httpGet.setParams(params);
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		System.out.println(nvps.toString());
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			// 如果登录成功 会返回302
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpGet.getURI());

			HttpEntity entity = response.getEntity();

			System.out.println("response content "
					+ EntityUtils.toString(entity, "UTF-8"));

			if (result.equals("HTTP/1.1 302 FOUND")) {
				System.out.println("token:" + token);
				return true;
			} else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean istodatavSuccess1() {
		HttpGet httpGet = new HttpGet("http://www.codoon.com/data_v");
		System.out.println("http://www.codoon.com/data_v");
		// 停止自动重定向
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.protocol.handle-redirects", false);
		httpGet.setParams(params);
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			// 如果成功 会返回301
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpGet.getURI());

			HttpEntity entity = response.getEntity();

			System.out.println("response content "
					+ EntityUtils.toString(entity, "UTF-8"));

			if (result.equals("HTTP/1.1 301 MOVED PERMANENTLY")) {
				return true;
			} else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean istodatavSuccess2() {
		HttpGet httpGet = new HttpGet("http://www.codoon.com/data_v/");
		System.out.println("http://www.codoon.com/data_v/");
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			// 如果成功 会返回200
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpGet.getURI());

			HttpEntity entity = response.getEntity();

			if (result.equals("HTTP/1.1 200 OK")) {
				return true;
			} else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean istomyroutesSuccess() {
		HttpGet httpGet = new HttpGet(
				"http://www.codoon.com/gps_sports/my_routes");
		System.out.println("http://www.codoon.com/gps_sports/my_routes");
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			// 如果成功 会返回200
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpGet.getURI());

			HttpEntity entity = response.getEntity();
			my_routes_htmlStr = EntityUtils.toString(entity, "UTF-8");

			if (result.equals("HTTP/1.1 200 OK")) {
				return true;
			} else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isAutoGetmyroutesDateSuccess() {
		long timestamp;
		timestamp = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(
				"http://www.codoon.com/gps_sports/routes_feed?auto_id=&_="
						+ timestamp);
		System.out
				.println("http://www.codoon.com/gps_sports/routes_feed?auto_id=&_="
						+ timestamp);
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		// 尝试登录
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			// 如果成功 会返回200
			String result = response.getStatusLine().toString();
			System.out.println("resultstring:" + result);
			Header[] resHeaders = response.getAllHeaders();

			System.out.println("=============================================");
			for (int i = 0; i < resHeaders.length; i++) {
				System.out.println("" + resHeaders[i].toString());
			}
			System.out.println("=============================================");

			System.out.println(response.getFirstHeader("Location"));

			System.out.println("executing request " + httpGet.getURI());

			HttpEntity entity = response.getEntity();
			my_routes_htmlStr = EntityUtils.toString(entity, "UTF-8");
			System.out.println("response content " + my_routes_htmlStr);

			if (result.equals("HTTP/1.1 200 OK")) {
				return true;
			} else {
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 模拟点击 加载更多 来获取更多的数据
	 * 
	 * @return
	 */
	private static boolean isGetMoremyroutesDateSuccess() {

		return false;
	}

	public static String getThisauto_id(String jsStr) {
		String auto_id = null;
		Pattern p = Pattern.compile("var auto_id = '(.*)';");
		Matcher m = p.matcher(jsStr);
		if (m.find()) {
			auto_id = m.group(1);
		}
		return auto_id;
	}

	public static int Client(String login_id, String password) {
		int flag_is_success = 0;
		Boolean resultBoolean1 = isClientCodoonSysSuccess1(login_id, password);
		System.out.println("result1:" + resultBoolean1.toString());
		if (resultBoolean1 == true) {
			Boolean resultBoolean2 = isClientCodoonSysSuccess2();
			System.out.println("result2:" + resultBoolean2.toString());
			if (resultBoolean2 == true) {
				Boolean resultBoolean3 = isClientCodoonSysSuccess3();
				System.out.println("result3:" + resultBoolean3.toString());
				if (resultBoolean3 == true) {
					Boolean resultBoolean4 = istodatavSuccess1();
					System.out.println("result4:" + resultBoolean4.toString());
					if (resultBoolean4 == true) {
						Boolean resultBoolean5 = istodatavSuccess2();
						System.out.println("result5:"
								+ resultBoolean5.toString());
						if (resultBoolean5 == true) {
							Boolean resultBoolean6 = istomyroutesSuccess();
							System.out.println("result6:"
									+ resultBoolean6.toString());
							if (resultBoolean6 == true) {
								Boolean resultbBoolean7 = isAutoGetmyroutesDateSuccess();
								System.out.println("result7:"
										+ resultbBoolean7.toString());
								{
									flag_is_success = 1;
								}
							} else {
								flag_is_success = 0;
								return flag_is_success;
							}
						} else {
							flag_is_success = 0;
							return flag_is_success;
						}
					} else {
						flag_is_success = 0;
						return flag_is_success;
					}
				} else {
					flag_is_success = 0;
					return flag_is_success;
				}
			} else {
				flag_is_success = 0;
				return flag_is_success;
			}
		} else {
			flag_is_success = 0;
			return flag_is_success;
		}
		return flag_is_success;
	}

}
