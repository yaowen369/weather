package com.yaoxiaowen.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import com.yaoxiaowen.weather.debug.DebugTools;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.MyApp;
import com.yaoxiaowen.weather.tool.WeatherStringRes;

public class BaiduWeather {
	
	private static final String TAG = "BaiduWeather";
	
	private SharedPreferences sp = null;
	private SharedPreferences.Editor editor;
	private Context mContext = MyApp.getContext();
	private SetDataToDB mSetDataToDB = new SetDataToDB();
	
	private static final String STATUS = "status";
	public static final String SUCCESS = "success";
	private static final String RESULTS = "results";
	private static final String CURRENT_CITY = "currentCity";
	private static final String WEATHER_DATA = "weather_data";
	
	private static final String DATE = "date";
	private static final String DAY_PICTURE_URL = "dayPictureUrl";
	private static final String NIGHT_PICTURE_URL = "nightPictureUrl";
	private static final String WEATHER = "weather";
	private static final String WIND = "wind";
	private static final String TEMPERATURE = "temperature";
	
	//关于标点符号的特殊处理
	private static final char colon = '：';
	private static final String waveSymbol = " ~ ";
	
	//错误和正确到标记
	public static String ERROR = "error";
	
	
//	private DebugTools mDebugTools;

	public BaiduWeather(Context context) {
		// TODO Auto-generated constructor stub
//		mDebugTools = new DebugTools(context);
	}
	
	public BaiduWeather(){
		sp = mContext.getSharedPreferences(WeatherStringRes.SP_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	public String getWeather(String city){
		String state = null;
		try{
			String xml = GetXmlCode(URLEncoder.encode(city, "utf-8"));
			
			MyLog.v(TAG, "getWeather -> xml=" + xml);
			
			state = readStringXml(xml, city);
		}catch (Exception e){
			MyLog.v(TAG, "getWeather -> exception");
			e.printStackTrace();
		}
		
		return state;
	}

	public String GetXmlCode(String city) throws UnsupportedEncodingException{
		//String requestURL = "http://api.map.baidu.com/telematics/v3/weather?location="+city+"&output=xml&ak=A72e372de05e63c8740b2622d0ed8ab1";
		String requestURL = "http://api.map.baidu.com/telematics/v3/weather?location="+city+"&output=xml&ak=9IFC7MPIPL3mLy8GUVMwYSNE"; 
		StringBuffer buffer = null;
		
		MyLog.v(TAG, "requestURL = " + requestURL);
		
		try{
			//建立连接
			URL url = new URL(requestURL);
			HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			
			//获取输入流
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader intputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(intputStreamReader);
			
			//读取返回结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null){
				buffer.append(str);
			}
			
			//释放资源
			bufferedReader.close();
			inputStream.close();
			inputStream.close();
			httpUrlConn.disconnect();
			
		}catch (Exception e){
			MyLog.v(TAG, "GetXmlCode -> Exception");
			e.printStackTrace();
		}
		
//		MyLog.v(TAG, "buffer.toString() = " 
//					+ buffer.toString());
		return buffer.toString();
	}
	
	public String readStringXml(String xml, String ifcify){
		StringBuffer buff = new StringBuffer();  // 用于拼接天气信息
		Document doc = null;
		List listDate = null;  //用来存放日期
		List listDayPic = null;     //用来存放白天图片路径信息
		List ListNightPic = null;
		List listWeather = null;
		List listWind = null;
		List listTemper = null;
		
		try {
			//读取并解析xml文档
			//下面是通过解析xml字符串的
			doc = DocumentHelper.parseText(xml);  //将字符串转移为xml
			Element rootElt = doc.getRootElement();  //获取根节点
			Iterator iter = rootElt.elementIterator(RESULTS); //获取根节点下的字节点result
			String status = rootElt.elementText(STATUS);  //获取数据，如果等于Success。表示有数据
			if (!status.equals(SUCCESS)){
				return ERROR;  // 如果不存在数据，直接返回 
			}
			
			String date = rootElt.elementText("date");  //获取根节点下的，当天日期
			editor.putString(WeatherStringRes.SP_DATE, date);
//			buff.append(date + "\n");
			
			//遍历result节点
			while (iter.hasNext()){
				MyLog.v(TAG, "readStringXml() -> iter.hasNext() --1");
				Element recordEle = (Element)iter.next();
				String city = recordEle.elementText(CURRENT_CITY);   //得到城市名字
				MyLog.v(TAG, "readStringXml() -> city=" + city);
				editor.putString(WeatherStringRes.SP_CURRENT_CITY, city);  
				Iterator iters = recordEle.elementIterator("weather_data");
				
				//遍历results节点下的weather_data 节点
				while (iters.hasNext()){
					MyLog.v(TAG, "readStringXml() -> iters.hasNext()() --2");
					
					Element itemEle = (Element)iters.next();
					listDate = itemEle.elements(DATE);
					listDayPic = itemEle.elements(DAY_PICTURE_URL);
					ListNightPic = itemEle.elements(NIGHT_PICTURE_URL);
					listWeather =  itemEle.elements(WEATHER);
					listWind = itemEle.elements(WIND);
					listTemper = itemEle.elements(TEMPERATURE);
					
				}  // end of "while (iters.hasNext())"
				
				for (int i=0; i<listDate.size(); i++){
					
					Element eleDate = (Element)listDate.get(i);
					Element eleDay = (Element)listDayPic.get(i);
					Element eleNight = (Element)ListNightPic.get(i);
					Element eleWeather = (Element)listWeather.get(i);
					Element eleWind = (Element)listWind.get(i);
					Element eleTemper = (Element)listTemper.get(i);
					
					
					ContentValues values = new ContentValues();
					
					String tmpDate = eleDate.getText();
					
					if (i == 0){             //针对第一天的特殊字符进行处理
						String[] tmpArr = dealDateStr(tmpDate);
						tmpDate = tmpArr[0];
					}
					
					values.put(WeatherStringRes.FIELD_DATE,  tmpDate);
					values.put(WeatherStringRes.FIELD_DAY_PICTURE_URL, eleDay.getText());
					values.put(WeatherStringRes.FIELD_NIGHT_PICTURE_URL, eleNight.getText());
					values.put(WeatherStringRes.FIELD_WEATHER, eleWeather.getText());
					values.put(WeatherStringRes.FIELD_WIND, eleWind.getText());
//					values.put(WeatherStringRes.FIELD_TEMPERATURE, eleTemper.getText());
					values.put(WeatherStringRes.FIELD_TEMPERATURE, dealTempStr(eleTemper.getText()));
					
					mSetDataToDB.updateDateToDB(values, i);
					buff.append(eleDate.getText() + "===" 
									+ eleWeather.getText() + "==="
									+ eleWind.getText() + "===" 
									+ eleTemper.getText() + "\n");
					MyLog.v(TAG, "readStringXml() ->for循环--i=" + i);
				}   // end of "for (int i=0; i<listDate.size(); i++)"
			}  // end of "while (iter.hasNext())"
			
			editor.commit();
		}catch (DocumentException e){
			MyLog.v(TAG, "readStringXml -> DoDocumentException");
			e.printStackTrace();
		}catch (Exception e){
			MyLog.v(TAG, "readStringXml -> Exception");
			e.printStackTrace();
		}
		
		MyLog.v(TAG, "readStringXml()-> 最后的结果 buff=" + buff);
//		return buff.toString();
		return SUCCESS;
	}
	
	
	private String[] dealDateStr(String strDate){
		
		String[] strArr = {null, null};
		strArr[0] = strDate.substring(0, 2);
		int indexUnit = strDate.indexOf(WeatherStringRes.celsiusUnit);
		int indexColon = strDate.indexOf(colon);
		strArr[1] = strDate.substring(indexColon + 1, indexUnit + 1);
		
		editor.putString(WeatherStringRes.SP_REAL_TEMP, strArr[1]);
		return strArr;
	}
	
	private String dealTempStr(String strTemperature){
		
		String str = strTemperature.replace(waveSymbol, WeatherStringRes.virgule);
		
		return str;
	}
}
