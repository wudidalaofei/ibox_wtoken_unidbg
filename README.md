
# unidbg-server
springboot运行unidbg

### 技术支持
**接单,逆向,爬虫,算法接单, 合作联系 -> Vx:cjh-18888  QQ:2625112940**
### 使用方式:
1.加载到idea

2.add 个maven工程

3.运行 `UnidbgServerApplication.java` 即可启动服务

### 修改配置和代码
通过 `application.properties` 自行修改服务的地址(默认`0.0.0.0`就行)和端口(默认是`9090`)

Controller示例文件文件参考`SignController.py`

### 打jar包
配置好maven环境的前提下，项目主目录执行
````java
 mvn clean package -Dmaven.test.skip=true  
````
使用maven的package即可，之后会发现生成一个target目录其中里面就有jar包了。
```
mvn package
```

### 使用jar包
```
java -jar target\unidbg-server-0.0.2.jar 
```


### 感谢
https://github.com/zhkl0228/unidbg
各种项目实战笔记,逆向学习笔记.干活满满
![](http://img.zhuoyue360.com//image-20220510150153371.png)
学unidbg强烈推荐龙哥的星球，更新频率高
![](https://img2020.cnblogs.com/blog/736399/202111/736399-20211108182436999-806188411.jpg)
