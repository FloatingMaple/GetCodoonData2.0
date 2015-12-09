/**
 * 
 */
package edu.fjnu.cerulean.main;

import java.util.Scanner;

import javafx.beans.binding.When;
import edu.fjnu.cerulean.util.ClientCodoonSystem;
import edu.fjnu.cerulean.util.GetMoreData;
import edu.fjnu.cerulean.util.GetTodayData;

/**
 * @author Cerulean
 * 
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("input your username:");
		String login_id = scanner.nextLine();
		System.out.println("input your password:");
		String password = scanner.nextLine();

		int flag = ClientCodoonSystem.Client(login_id, password);

		if (flag == 1) {
			System.out.println("登录成功");
			//初始化
			GetTodayData.todayCodoonData.setAvg_speed(0.0);
			GetTodayData.todayCodoonData.setDistance(0.0);
			GetTodayData.todayCodoonData.setDuration("0:0");
			GetTodayData.todayCodoonData.setUsePower(0.0);
			GetTodayData.getdata(ClientCodoonSystem.my_routes_htmlStr);
			while(GetTodayData.flag_time[2]==1) {
				Boolean resultGetMoreBoolean = 
						GetMoreData.isGetMoreDataSuccess(GetTodayData.thisauto_id);
				if (resultGetMoreBoolean == true) {
					//获取成功
					System.out.println("MoreData:" + GetMoreData.More_DataStr);
					GetTodayData.getdata(GetMoreData.More_DataStr);
				}else {
				}
			}
			System.out.println("今天的运动情况：\n" + GetTodayData.todayCodoonData.toString());
		} else {
			System.out.println("登录失败");
		}

		ClientCodoonSystem.httpClient.getConnectionManager().shutdown();
		scanner.close();
	}

}
