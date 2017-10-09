# weather

### 一个简单的天气预报应用

> 这其实是本人在刚工作那段时间(14年)所写的一个小app，当然现在看来功能很简单，界面很丑，代码也比较简陋。前几天看到了这个项目。在AS下进行了重新编译。（14年时开发工具还是eclipse）。入门级程序员可以参考该项目。

本项目是个 可以用作桌面小工具(`AppWidgetProvider`)的天气预报应用。界面如下：

![app_widget](https://github.com/yaowen369/weather/blob/master/docs/app_widget.jpg)

![setting_activity](https://github.com/yaowen369/weather/blob/master/docs/setting.jpg)

天气预报的数据是从[百度API](http://api.map.baidu.com/telematics/v3/weather?location=%E4%B8%8A%E6%B5%B7&output=xml&ak=9IFC7MPIPL3mLy8GUVMwYSNE)上获得的,使用 `HttpURLConnection`建立网络链接，返回的是[xml格式的数据](https://github.com/yaowen369/weather/blob/master/docs/baidu_api_return.xml)，自己手动解析数据，然后刷新界面，并存储到数据库中。

可以在设置界面 选择后台静默更新的频率(比如一天，六小时，三小时等)，以及 输入城市名来更换城市。

