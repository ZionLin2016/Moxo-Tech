Moxo-Tech(默索云课堂)
android课堂辅助软件
---------
Moxo-Tech采用轻量级急速开发框架，基于Fragment+RxJava+Retrofit 2.0+Glide+GreenDao构建，采用Material Design设计风格，是本人的毕设作品。

云课堂是一款教学互动的软件，在学习借鉴其他成熟应用的同时，尝试地进行一些创新以及功能整合，下面是对系统主要功能地简要概述：
系统从整体上分为教师以及学生两端，教师端以及学生端都有登录以及注册功能，教师端登录后，可以根据课程或者活动需要填写课程相关信息(班级名称、课程名称、课程类型、数字教材、课程要求、课程进度、考试安排等)创建智慧云班课，创建完云班课之后会生成一个随机的邀请码，学生可以通过教师告知的邀请码加入该教师的课程。创建课程后教师可以通过pc机或者手机上传课件及课程参考资料，查看参加课程成员的联系方式，同时还可以发公共，创建答疑聊天室进行答疑，答疑聊天室是以TCP协议为基础搭建的，通过自定义通讯规则，实现文本传输，语音传输，图片传输。
学生端注册登录之后则可以通过老师告知的邀请码，加入该老师所创建的班级，了解课程信息，可以查看老师上传的资源，发布的公告，可以通过答疑聊天室向老师提出问题，同时在主界面上能添加查看课程表。
两个分系统都有系统设置的功能，实现退出登录，清除缓存，查看个人信息等。
同时还需搭建一个后台服务器来响应app的请求，响应app的注册、登录及添加、查看课程信息、保存个人信息，答疑聊天室的分组，信息的转发等


当然，由于本项目是为了完成毕业设计，部分借鉴了蓝墨云课堂的功能设计，目前的功能大概完成了60%。
这个app集成了基于Java Socket写的IM, 目前这个功能还有待完善.

本项目基于Material Design+RxJava+Retrofit 2.0+Glide+GreenDao构建了一个良好MVC架构的客户端模型，对新手友好度更高。


本项目仍在不断的完善阶段，欢迎及时向我反馈[email](lsd2015y@Outlook.com)。如果对你有帮助欢迎点个star、fork.

---------------

Preview
-------------

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/1.png?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/2.png?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/3.png?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/4.png?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/5.jpg?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/6.jpg?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/7.jpg?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/8.jpg?raw=true)

![此处输入图片的描述](https://github.com/ZionLin2016/Moxo-Tech/blob/master/screenshots/9.png?raw=true)

---------------

Point
--------------------


### 为什么采用多Fragment构建应用？
相比Activity，Fragment稍显复杂。谈起Fragment的时候，很多开发者直接摆手，然后告诉你这玩意坑太多,比如说调试比较困难，无法有效的实现业务逻辑和View的解耦，偶尔的NullPointerException问题等等。但这些问题都不是阻止我们使用Fragment的原因。其实深究下来，你会发现这并不是Fragment自身的问题，而更多的是由于Fragment稍显复杂，导致很多开发者没有耐心去深究它，再加上一些开发者喜欢直接copy网上的代码，不加以思考的就应用在实际项目中，后果可想而知。有很多人也提出过通过自定义View的方式取代Fragment，当然这也是可行的，但是对于大部分开发者来说，这好像没有必要（你不是在加班，就是在吃饭...你懂的）

那么这里我为什么要采用多Fragment构建基础框架呢？主要有有两方面的原因：
>1.相比创建Actvity，Fragment要显得更加轻量级。尤其是在同一个Activity中实现多种布局的时候，无需新建Activity，另外，由于Fragment可以被缓存，因此在某些场景下会有更流畅的体验。
2. Fragment能让你更容易的适配手机和平板。如果你的应用需要支持这两种类型的设备，那么使用Fragment会帮你减少很多适配问题。
3. 最大程度上的实现布局复用，更好的实现模块化。很多情况下，你会发现：你在用玩“积木”的方法拼装应用。



### 为什么采用RxJava+Retrofit？
在11年左右的时候，没有太多网络库供你选择，所以在一些比较老的项目当中还会看到我们使用HttpUrlConnection或者HttpClient来自行封装成的请求库。5年的时间，让Android整个生态环境更加完善，越来越多人参与到Android的开发工作，也诞生了很多非常优秀的网络库，如async-http-client,volley，OkHttpClient,Retrofit等等，这些库各有利弊，但是就目前看来相对比较通用又受大家欢迎的莫过于volley，OkHttpclent和Retrofit这三者了。而Retrofit是通过包装OKHttpClient而来，所以说两者在底层并无多大区别，而volley则是google前几年推出的。在我看来，在你的应用无特殊要求的情况下，三者之一都能满足需求，而且在出现问题的时候能快速的从周围开发者得到反馈。

这里我选择了Retrofit来做出基础网络请求，另外也应时的结合RxJava来帮助大家更好的学习。

>补：目前国内很多大神也在这些项目之上二次封装了很多使用的库，比如NoHttp，xUtils，OkHttpUtils，OkGo等，也有很多一些从头开发的，如HttpLite等。但无论你选择哪一个，本质并无变化。不过，如果你想深入底层学习，开始时请尽可能的不要使用，封装程度太高，学到的越少。后期有经验之后，自己尝试造轮子呗。


### 为什么采用Glide？
目前图片加载框架也是繁多，目前常用的有以下几种：ImageLoader（2011年），Picasso（2013年），Glide（2012年），Fresco（2015年）四种。其中ImageLoader出现的最早也应用的最为广泛。早期出现的ImageLoader首要关注的是如何尽快的加载图片，然后需要自己动手处理图片防止内存溢出。后面，大家觉得很烦啊，于是一些即注重加载速度，又减少内存溢出的网络加载框架就出现了，就像后三种。（我在一些群里曾听到一些开发者说“ImageLoader不行啊，经常造成内存溢出啊，Fresco就不会，所以ImageLoader已经过时了”之类的话，虽然我个人能力也比较一般，但是当遇到问题的时候我首先想的是“我是不是忘记处理什么？是不是我的能力有问题，而不是质疑框架，毕竟框架是死的，人是活的”）



这些加载库各有优缺点，需要自行调研之后根据业务选择，这里我选择了加载速度较快，体积更小的Glide作为网络加载库。



### greenDAO 数据库ORM
greenDao是一个将对象映射到SQLite数据库中的轻量且快速的ORM解决方案，其减轻开发人员处理低级别的数据库的需求，同时节省开发时间。SQLite是一个很好的嵌入式关系数据库。但写SQL以及解析查询结果是相当繁琐以及耗时的任务。greendao使你远离Java对象映射到数据库表（称为ORM，对象/关系映射”）的过程，并使用一个简单的面向对象的API对java对象进行查询，存储，更新，删除。

### ButterKnife 注解
Butter Knife，专门为Android View设计的绑定注解，其强大的View绑定以及Click事件处理功能，简化了代码，提高了开发效率

### Java Socket 实现即时通讯
Java Socket(套接字)，是Java中基于TCP协议实现网络通信的类，是支持TCP/IP协议的网络通信的基本单元，是网络通信过程中端点的抽象表示，包括进行网络通信所必须的5种信息：连接使用的协议，本机主机的IP地址，本机进程的协议端口，远程主机的IP地址，远端进程的协议端口。一个完整的Socket通信程序一般包括以下几个步骤：
* 创建Socket
* 打开连接到Socket的输入输出流。
* 按照一定的协议对Socket进行读/写操作。
* 关闭Socket。
 

--------------

Version
---------

###V 0.0.1

1.提交第一版

###备注
由于本人学习方向是JavaEE方向，这个项目可能没剩余的精力去完善它，就先这样了,服务器代码在另外一个仓库[IMSDK](https://github.com/ZionLin2016/IMSDK.git) ，如果对你有帮助请star一下，谢谢！

--------------------
 
