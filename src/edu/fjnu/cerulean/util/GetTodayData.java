/**
 * 
 */
package edu.fjnu.cerulean.util;

import edu.fjnu.cerulean.util.ClientCodoonSystem;
import edu.fjnu.cerulean.util.CodoonData;
import edu.fjnu.cerulean.util.GetMoreData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.cookie.ClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 获取运动数据
 * @author Cerulean
 * 
 */
public class GetTodayData {

	public static CodoonData todayCodoonData = new CodoonData();
	public static int flag_time[] = { 0, 0, 0 };
	public static String thisauto_id;
	
	public static void getdata(String urlTest) {
		// 初始化今天的运动数据
		List<String> datalist = new ArrayList<String>();
		flag_time[0] = 0;
		flag_time[1] = 0;
		flag_time[2] = 0;

		Document document = Jsoup.parse(urlTest);

		thisauto_id = ClientCodoonSystem.getThisauto_id(urlTest);
		System.out.println("auto_id:" + thisauto_id);
		
		Elements ele_time = document.select(".tl").select(".f12");
		Elements ele2 = document.getElementsByTag("table");
		int flag_i = 0;
		// 判断是否是今天,并在flag_time里面做标记
		for (Element element : ele_time) {
			String datadate = element.text().substring(0,
					element.text().length() - 15);
			System.out.println("datadate:" + datadate);
			// 获取当前日期
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todaydate = sdf.format(date);
			System.out.println("todaydate:" + todaydate);
//			 if (datadate.equals("2015-12-08")){
			if (datadate.equals(todaydate)) {
				flag_time[flag_i] = 1;
			}
			flag_i++;
		}

		for (int i = 0; i < flag_time.length; i++) {
			System.out.println("flag_time:" + flag_time[i]);
		}


		int j = 0;
		for (int i = 0; i < flag_time.length; i++) {
			if (flag_time[i] == 1) {
				// 是今天
				datalist.add(ele2.get(i).toString());
				System.out.println("datalist["+j+"]" + datalist.get(j));
				j++;
			}
		}

		System.out
				.println("**********************************************************");		
		
		//传入今天的数据
		for (int i = 0; i < datalist.size(); i++) {
			if (datalist.get(i) != null) {
				getCoreCodoonData(datalist.get(i));
			}
		}

	}

	private static CodoonData getCoreCodoonData(String dataStr) {

		double[] tempdata = new double[] { 0, 0, 0, 0 };

		Document document = Jsoup.parse(dataStr);
		Elements todaydataElements = document.select(".f18.co14.fb.f24");
		int i = 0;
		for (Element element2 : todaydataElements) {
			System.out.println(element2.text());
			if (i == 1 || i == 5 || i == 9) {
				// 跳过运动时长
				i++;
			} else {
				tempdata[i] = Double.valueOf(element2.text());
				i++;
			}
		}
		todayCodoonData
				.setDistance(tempdata[0] + todayCodoonData.getDistance());
		todayCodoonData
				.setUsePower(tempdata[3] + todayCodoonData.getUsePower());
		return todayCodoonData;
	}
}
