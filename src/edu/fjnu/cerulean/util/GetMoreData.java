/**
 * 
 */
package edu.fjnu.cerulean.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * 获取更多数据
 * @author Cerulean
 *
 */
public class GetMoreData {

	public static String More_DataStr;
	
	public static boolean isGetMoreDataSuccess(String auto_id){
		long timestamp;
		timestamp = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(
				"http://www.codoon.com/gps_sports/routes_feed?auto_id="
						+ auto_id
						+ "&_=" + timestamp);
		System.out
				.println("http://www.codoon.com/gps_sports/routes_feed?auto_id=&_="
						+ timestamp);
		// 设置字符
		httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
		// 尝试登录
		HttpResponse response;
		try {
			response = ClientCodoonSystem.httpClient.execute(httpGet);
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
			More_DataStr = EntityUtils.toString(entity, "UTF-8");
			System.out.println("response content " + More_DataStr);

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
	
}
