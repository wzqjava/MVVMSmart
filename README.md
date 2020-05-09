# MVVMSmart
> 目前，android基于MVVM模式开发框架比较少。**MVVMSmart-kotlin是以谷歌Jetpack架构组件ViewModel+Lifecycles+Navigation+DataBinding+LiveData+Okhttp+Retrofit+RxJava+Glide等，加上各种原生控件自定义的BindingAdapter，让事件与数据源完美绑定的一款容易上瘾的实用性MVVM快速开发框架**。告别findViewById()，告别setText()，告别setOnClickListener()...
## 技术讨QQ群：531944409
## 最新日志 **v2.0：2020年4月28日**
1. 上线kotlin稳定版
2. 增加recyclerview无数据的默认页,同时支持其他任何布局层次无数据的默认页,一行代码显示默认页
## 中文文档
建议大家用clone的方式下载开源框架,方便及时更新。
1. MVVMsmart-kotlin地址: https://github.com/wzqjava/MVVMSmart(分支名称:MVVMSmart-kotlin)
2. MVVMsmart-java地址:   https://github.com/wzqjava/MVVMSmart(分支名称:MVVMSmart-java)
3. AndroidStudio 从github下载代码的正确姿势:https://juejin.im/post/5e09dd306fb9a01648718430	
4. MVVMSmart系列解读文章: https://juejin.im/user/574e36b179bc440062693484/posts

## 框架特点
- **快速开发**
只需要写项目的业务逻辑，不用去关心网络请求、权限申请、View的生命周期等问题，撸起袖子就是干，高效开发。

- **维护方便**
MVVM开发模式，低耦合，逻辑分明。Model层负责将请求的数据交给ViewModel；ViewModel层负责将请求到的数据做业务逻辑处理，最后交给View层去展示，与View一一对应；View层只负责界面绘制刷新，不处理业务逻辑，非常适合分配独立模块开发。

- **本项目使用流行框架**
google AAC(Android Architecture Components:安卓架构组件):
[ViewModel](https://developer.android.google.cn/jetpack):负责管理UI的逻辑好数据,
[Lifecycles](https://developer.android.google.cn/jetpack):负责处理数据和UI生命周期的自动管理
[Navigation](https://developer.android.google.cn/jetpack):google推荐的轻Activity方案,一个大的功能只用一个Activity,内部导航多个Fragment.
[DataBinding](https://developer.android.google.cn/jetpack):负责ViewModel中数据和UI控件的自动绑定
[LiveData]:(https://developer.android.google.cn/jetpack)负责ViewModel中数据的管理和与UI层的通信
[retrofit](https://github.com/square/retrofit)+[okhttp](https://github.com/square/okhttp)
[rxJava](https://github.com/ReactiveX/RxJava)负责网络请求
[gson](https://github.com/google/gson)负责解析json数据
[glide](https://github.com/bumptech/glide)负责加载图片；
[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)负责管理RecyclerView的适配器
[rxpermissions](https://github.com/tbruyelle/RxPermissions)负责Android 6.0权限申请
[material-dialogs](https://github.com/afollestad/material-dialogs)一个漂亮的、流畅的、可定制的material design风格的对话框。

- **数据绑定**

	满足google目前控件支持的databinding双向绑定，并扩展原控件一些不支持的数据绑定。例如将图片的url路径绑定到ImageView控件中，在BindingAdapter方法里面则使用Glide加载图片；View的OnClick事件在BindingAdapter中方法使用RxView防重复点击，再把事件回调到ViewModel层，实现xml与ViewModel之间数据和事件的绑定(框架里面部分扩展控件和回调命令使用的是@kelin原创的)。UI的事件儿绑定请在UI中处理,否则不好维护,可以在UI中触发,UI中持有viewmodel,调用viewmodel中的业务即可.

- **基类封装**
  	专门针对MVVM模式打造的BaseActivityMVVM、BaseFragmentMVVM、BaseViewModelMVVM，在View层中不再需要定义ViewDataBinding和ViewModel，直接在BaseActivityMVVM、BaseFragmentMVVM上限定泛型即可使用.支持navigation导航Fragment的管理,导航返回时候回调用OnCreateView,BaseFragmentMVVM已经封装,标题使用include导入布局, Base层预留的有 initToolbar(),标题的返回、文字设置、右侧更多等在这个方法初始化即可,普通界面只需要编写Fragment，然后使用navigation导航,不用在manifest注册,性能也更好.

- **全局操作**
1. google的AAC架构，ViewModel+Lifecycles+Navigation+DataBinding+LiveData。
2. LoggingInterceptor全局拦截网络请求日志，打印Request和Response，格式化json、xml数据显示，方便与后台调试接口。
3. 全局Cookie，支持SharedPreferences和内存两种管理模式。
4. 通用的网络请求异常监听，根据不同的状态码或异常设置相应的message。
5. 全局的异常捕获，程序发生异常时不会崩溃，可跳入异常界面重启应用。
6. 全局唯一可信事件源处理，提供LiveEventBus回调方式。
7. 全局任意位置一行代码实现文件下载进度监听（暂不支持多文件进度监听）。
8. 任何布局层次无数据时候的默认页(主要用来: 列表无数据的默认页,接口error的默认页,无网络的默认页等,动态传入文字和图片的id即可)
9. app崩溃重启功能(任意指定重启Activity即可,一般是欢迎页),debug模式崩溃后测试人员可以直接截屏崩溃日志给开发,再也不会听到测试说"又崩啦.."
10. AppManagerMVVM管理类用两个栈管理Activity和Fragment,能动态获取栈顶UI,任何页面一行代码安全退出程序.
  

## 1、准备工作
> 网上有很多MVVM入门资料，在此就不再阐述什么是MVVM了，不清楚的朋友可以先去了解一下。
1. [architecture-components-samples](https://github.com/android/architecture-components-samples)
2. [todo-mvvm-live](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-live)

### 1.1、启用databinding
在主工程app的build.gradle的android {}中加入：
```gradle
dataBinding {
    enabled true
}
```
### 1.2、依赖Library
从远程依赖：

在根目录的build.gradle中加入
```gradle
allprojects {
    repositories {
		...
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
在主项目app的build.gradle中依赖

下载例子程序，在主项目app的build.gradle中依赖例子程序中的**mvvmsmart**：
```gradle
dependencies {	
    ...
    api project(':mvvmsmart')
}
```

### 1.3、配置config.gradle
下载例子程序，例子程序中的config.gradle放入你的主项目根目录中，然后在根目录build.gradle的第一行加入：

```gradle
apply from: "config.gradle"
```

**注意：** config.gradle中的 

android = [] 是你的开发相关版本配置，可自行修改，比如compileSdkVersion，buildToolsVersion，versionCode等等。

dependencies = [] 是依赖第三方库的配置，可以加新库，用户也可以自己修改版本号,目前都是androidx依赖.

### 1.4、配置AndroidManifest
添加权限：
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
配置Application：

继承**mvvmsmart**中的BaseApplicationMVVM，或者调用

```java
BaseApplication.setApplication(this);
```
来初始化你的Application

可以在你的自己AppApplication中配置

```java
//是否开启日志打印
KLog.init(true);
//配置全局异常崩溃操作
CaocConfig.Builder.create()
    .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
    .enabled(true) //是否启动全局异常捕获
    .showErrorDetails(true) //是否显示错误详细信息
    .showRestartButton(true) //是否显示重启按钮
    .trackActivities(true) //是否跟踪Activity
    .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
    .errorDrawable(R.mipmap.ic_launcher) //错误图标
    .restartActivity(LoginActivity.class) //重新启动后的activity
    //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
    //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
    .apply();
```

## 2、快速上手

### 2.1、第一个Fragment
为啥是第一Fragment,因为目前google IO 大会已经推荐在Activity中使用navigation来导航Fargment。目前sample中用Activity导航了所有功能Fragment,一个功能是一个Fargment.同时你想继续用一个个Activity也是可以的,ActivityBase层都已经处理好了.
以大家都熟悉的Recyclerview加载多条目为例：
- 三个文件**MultiRecycleViewFragment.java**、**MultiRecycleViewModel.java**、**fragment_multi_rv.xml.xml**

##### 2.1.1、关联ViewModel
fragment_multi_rv.xml中关联LinearLayoutManager和MyMultiAdapter。
```xml
<layout>
    <data>
        <variable
            name="layoutManager"
            type="androidx.recyclerview.widget.LinearLayoutManager" />
        <variable
            name="adapter"
            type="com.wzq.sample.ui.recycler_multi.MyMultiAdapter" />
    </data>
    .....

</layout>
```

> variable - type：类的全路径 <br>variable - name：变量名

##### 2.1.2、继承BaseFragmentMVVM

MultiRecycleViewFragment继承BaseFragmentMVVM
```java

public class MultiRecycleViewFragment extends BaseFragmentMVVM<FragmentMultiRvBinding, MultiRecycleViewModel> {


    private MyMultiAdapter mAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_multi_rv;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getData();
        initRecyclerView();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemsEntityLiveData.observe(this, itemsEntities -> {
            mAdapter.setNewData(itemsEntities);
        });
    }
    ....
```
> fragment_multi_rv.xml后databinding会生成一个FragmentMultiRvBinding类。（如果没有生成，试着点击Build->Clean Project）

BaseFragmentMVVM是一个抽象类(专门在Sample中独立与框架处理,方便大家使用自己项目中的Base,不用修改自己项目中的base名称,框架中的Base都有MVVM后缀), base中有两个泛型参数，一个是ViewDataBinding，另一个是BaseViewModelMVVM，上面的ActivityLoginBinding则是继承的ViewDataBinding作为第一个泛型约束，MultiRecycleViewModel继承BaseFragmentMVVM作为第二个泛型约束。

重写BaseFragmentMVVM的两个抽象方法

initContentView() 返回界面layout的id<br>
initVariableId() 返回viewModel变量的id，就像一个控件的id，可以使用R.id.xxx，这里的BR跟R文件一样，由系统生成，使用BR.xxx找到这个ViewModel的id。<br>

大家可以选择性重写initViewModel()方法，比如LoginViewModel中的重写.
```java
@Override
public LoginViewModel initViewModel() {
    //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
    return ViewModelProviders.of(this).get(LoginViewModel.class);
}
```

**注意：** 不重写initViewModel()，默认会创建MultiRecycleViewFragment中第二个泛型约束的MultiRecycleViewModel，如果没有指定第二个泛型，则会创建BaseViewModelMVVM

##### 2.1.3、继承BaseViewModelMVVM

MultiRecycleViewModel继承BaseViewModelMVVM
```java
public class MultiRecycleViewModel extends BaseViewModelMVVM {

    //给RecyclerView添加ObservableList
    public MutableLiveData<ArrayList<DemoBean.ItemsEntity>> itemsEntityLiveData;

    public MultiRecycleViewModel(@NonNull Application application) {
        super(application);
        itemsEntityLiveData = new MutableLiveData<>();
    }
    .....
}
```
BaseViewModelMVVM与BaseFragmentMVVM通过StateLiveData来处理常用UI逻辑，即可在ViewModel中使用父类的showDialog()、startActivity()等方法。在这个MultiRecycleViewModel中就可以尽情的写你的逻辑了！
> BaseActivityMVVM的使用和BaseFragmentMVVM几乎一样(BaseFragmentMVVM中单独处理的配合navigation)，详情参考Sample。

### 2.2、数据绑定
> 拥有databinding框架自带的双向绑定，也有扩展
##### 2.2.1、传统绑定
绑定用户数据：
在FormViewModel中定义
```kotlin
//对象中的数据赋值(例子中来自bundle)
entity = mBundle.getSerializable("entity") as FormEntity
```
在姓名EditText标签中绑定,直接使用ViewMode中的LiveData,方便高效.
```fragment_form_temp.xml
 android:text="@={viewModel.entityLiveData.name}"
```
这样一来，输入框中输入了什么，userName.get()的内容就是什么，userName.set("")设置什么，输入框中就显示什么。
**注意：** @符号后面需要加=号才能达到双向绑定效果；userName需要是public的，不然viewModel无法找到它。

点击事件绑定：

在FormFragment中定义
```java
//按钮的点击事件
fun commitClick() {
            Toast.makeText(activity, "触发提交按钮", Toast.LENGTH_SHORT).show()
            val submitJson = Gson().toJson(viewModel.entityLiveData.value)
//            MaterialDialogUtils.Companion.showBasicDialog(context, "提交的json实体数据：\r\n$submitJson").show()
        }
```
在登录按钮标签中绑定
```xml
  android:onClick="@{() ->presenter.commitClick()}" // 多个点击事件的话,可以参考我的放到Presenter中,几种管理,结构清晰
```
这样一来，用户的点击事件直接被回调到ViewModel层了，更好的维护了业务逻辑

这就是强大的databinding框架双向绑定的特性，不用再给控件定义id，setText()，setOnClickListener()。

**去除晦涩难懂的自定义命令绑定,特别是在viewMode中绑定事件儿,处理跳转等,非常不建议,应该交给UI层,viewmodel层也不应持有View层的东西**

##### 2.2.2、UI控件点击事件儿的绑定
还拿点击事件说吧，建议不用使用太多网上的自定义指令,因为一般都是团队开发,不好维护,所有UI事件儿的触发都要放在UI层触发, 高级命令在后边讲解

在LoginFragment.java中定义
```java
//登录按钮的点击事件
 binding.btnLogin.setOnClickListener(view ->{
            viewModel.login();
        });
```
简单粗暴,一眼明了,好维护.如果有多个点击事件怎么封装请参考HomeFragment.java中,用一个Presenter来封装;

是不是觉得有点意思，好戏还在后头呢！
##### 2.2.3、自定义ImageView图片加载
绑定图片路径：

在ViewModel中定义
```java
public String imgUrl = "http://img0.imgtn.bdimg.com/it/u=2183314203,562241301&fm=26&gp=0.jpg";
```
在ImageView标签中
```xml
binding:url="@{viewModel.imgUrl}"
```
url是图片路径，这样绑定后，这个ImageView就会去显示这张图片，不限网络图片还是本地图片。

如果需要给一个默认加载中的图片，可以加这一句
```xml
binding:placeholderRes="@{R.mipmap.ic_launcher_round}"
```
> R文件需要在data标签中导入使用，如：`<import type=com.wzq.mvvmsmart.R" />`

BindingAdapter中的实现
```java
@BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
    if (!TextUtils.isEmpty(url)) {
        //使用Glide框架加载图片
        Glide.with(imageView.getContext())
            .load(url)
            .placeholder(placeholderRes)
            .into(imageView);
    }
}
```
很简单就自定义了一个ImageView图片加载的绑定，学会这种方式，可自定义扩展。
> 如果你对这些感兴趣，可以下载源码，在binding包中可以看到各类控件的绑定实现方式

##### 2.2.4、RecyclerView绑定
> RecyclerView是很常用的控件，传统的方式需要针对各种业务要写各种Adapter，如果你使用了mvvmsmart，则可大大简化这种工作量，从此告别setAdapter()。
mvvmsmart中的recyclerview进行了三次大改动,后期又改为了BaseRecyclerViewAdapterHelper,主要考虑到方便使用和维护,之前用Databinding和ItemViewModel都太难维护,学习成本高,与高质快速开发思想相违背,这就类似于部队的技术更重视成功,稳定,而不是一味立马上新技术.
[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)负责管理RecyclerView的适配器；


在ViewModel中定义：

在xml中绑定
```xml
<androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rv"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:adapter="@{adapter}"
            app:layoutManager="@{layoutManager}"/>
```
layoutManager控制是线性(包含水平和垂直)排列还是网格排列，lineManager是设置分割线

网格布局的写法：`binding:layoutManager="@{LayoutManagers.grid(3)}`</br>
水平布局的写法：`binding:layoutManager="@{LayoutManagers.linear(LinearLayoutManager.HORIZONTAL,Boolean.FALSE)}"`</br>

使用到相关类，则需要导入该类才能使用，和导入Java类相似

> `  <variable
            name="layoutManager"
            type="androidx.recyclerview.widget.LinearLayoutManager" />`</br>
> ` <variable
            name="adapter"
            type="com.wzq.sample.ui.recycler_multi.MyMultiAdapter" />`</br>
绑定后,界面自动出现recyclerView;

更多RecyclerView、ListView、ViewPager等绑定方式，请参考 [https://github.com/evant/binding-collection-adapter](https://github.com/evant/binding-collection-adapter)

### 2.3、网络请求
> 网络请求一直都是一个项目的核心，现在的项目基本都离不开网络，一个好用网络请求框架可以让开发事半功倍。
#### 2.3.1、Retrofit+Okhttp+RxJava
> 现今，这三个组合基本是网络请求的标配，如果你对这三个框架不了解，建议先去查阅相关资料。

square出品的框架，用起来确实非常方便。**MVVMSmart**中引入了
```gradle
api "com.squareup.okhttp3:okhttp:3.10.0"
api "com.squareup.retrofit2:retrofit:2.4.0"
api "com.squareup.retrofit2:converter-gson:2.4.0"
api "com.squareup.retrofit2:adapter-rxjava2:2.4.0"
```
构建Retrofit时加入
```java
Retrofit retrofit = new Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    .build();
```
或者直接使用例子程序中封装好的RetrofitClient
#### 2.3.2、网络拦截器
**LoggingInterceptor：** 全局拦截请求信息，格式化打印Request、Response，可以清晰的看到与后台接口对接的数据，
```java
LoggingInterceptor mLoggingInterceptor = new LoggingInterceptor
    .Builder()//构建者模式
    .loggable(true) //是否开启日志打印
    .setLevel(Level.BODY) //打印的等级
    .log(Platform.INFO) // 打印类型
    .request("Request") // request的Tag
    .response("Response")// Response的Tag
    .addHeader("version", BuildConfig.VERSION_NAME)//打印版本
    .build()
```
构建okhttp时加入
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .addInterceptor(mLoggingInterceptor)
    .build();
```
**CacheInterceptor：** 缓存拦截器，当没有网络连接的时候自动读取缓存中的数据，缓存存放时间默认为3天。</br>
创建缓存对象
```java
//缓存时间
int CACHE_TIMEOUT = 10 * 1024 * 1024
//缓存存放的文件
File httpCacheDirectory = new File(mContext.getCacheDir(), "wzq_cache");
//缓存对象
Cache cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
```
构建okhttp时加入
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .cache(cache)
    .addInterceptor(new CacheInterceptor(mContext))
    .build();
```
#### 2.3.3、Cookie管理
**MVVMSmart**提供两种CookieStore：**PersistentCookieStore** (SharedPreferences管理)和**MemoryCookieStore** (内存管理)，可以根据自己的业务需求，在构建okhttp时加入相应的cookieJar
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
    .build();
```
或者
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
    .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
    .build();
```
#### 2.3.4、绑定生命周期
请求在ViewModel层。默认在BaseActivityMVVM和BaseFragmentMVVM中加入了Lifecycle，用于感知View的生命周期。
```java
RetrofitClient.getInstance().create(DemoApiService.class)
    .demoGet()
    .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) // 请求与View周期同步
    .compose(RxUtils.schedulersTransformer())  // 线程调度
    .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
    .subscribe(new Consumer<BaseResponse<DemoEntity>>() {
        @Override
        public void accept(BaseResponse<DemoEntity> response) throws Exception {
                       
        }
    }, new Consumer<ResponseThrowable>() {
        @Override
        public void accept(ResponseThrowable throwable) throws Exception {
                        
        }
    });

```
在请求时关键需要加入组合操作符`.compose(RxUtils.bindToLifecycle(getLifecycleProvider()))`<br>
**注意：** 由于BaseActivityMVVM/BaseFragmentMVVM都实现了LifecycleProvider接口，并且默认注入到ViewModel中，所以在调用请求方法时可以直接调用getLifecycleProvider()拿到生命周期接口。如果你没有使用 **mvvmabit** 里面的BaseActivityMVVM或BaseFragmentMVVM，使用自己定义的Base，那么需要让你自己的Activity继承RxAppCompatActivity、Fragment继承RxFragment才能用`RxUtils.bindToLifecycle(lifecycle)`方法。
#### 2.3.5、网络异常处理
网络异常在网络请求中非常常见，比如请求超时、解析错误、资源不存在、服务器内部错误等，在客户端则需要做相应的处理(当然，你可以把一部分异常甩锅给网络，比如当出现code 500时，提示：请求超时，请检查网络连接，此时偷偷将异常信息发送至后台(手动滑稽))。<br>

在使用Retrofit请求时，加入组合操作符`.compose(RxUtils.exceptionTransformer())`，当发生网络异常时，回调onError(ResponseThrowable)方法，可以拿到异常的code和message，做相应处理。<br>

> mvvmsmart中自定义了一个[ExceptionHandle](./mvvmsmart/src/main/java/me/wzq/mvvmsmart/http/ExceptionHandle.java)，已为你完成了大部分网络异常的判断，也可自行根据项目的具体需求调整逻辑。<br>

**注意：** 这里的网络异常code，并非是与服务端协议约定的code。网络异常可以分为两部分，一部分是协议异常，即出现code = 404、500等，属于HttpException，另一部分为请求异常，即出现：连接超时、解析错误、证书验证失等。而与服务端约定的code规则，它不属于网络异常，它是属于一种业务异常。在请求中可以使用RxJava的filter(过滤器)，也可以自定义BaseSubscriber统一处理网络请求的业务逻辑异常。由于每个公司的业务协议不一样，所以具体需要你自己来处理该类异常。
## 3、辅助功能
> 一个完整的快速开发框架，当然也少不了常用的辅助类。下面来介绍一下**MVVMSmart**中有哪些辅助功能。
### 3.1、事件总线
> 事件总线存在的优点想必大家都很清楚了，android自带的广播机制对于组件间的通信而言，使用非常繁琐，通信组件彼此之间的订阅和发布的耦合也比较严重，特别是对于事件的定义，广播机制局限于序列化的类（通过Intent传递），不够灵活。

#### 3.3.2、LiveEventBus
LiveEventBus是一个轻量级全局的消息通信工具，在我们的复杂业务中，难免会出现一些交叉的业务，比如ViewModel与ViewModel之间需要有数据交换，这时候可以轻松地使用LiveEventBusr发送一个实体或一个空消息，将事件从一个ViewModel回调到另一个ViewModel中。

使用方法：

已经介入,文档待续

### 3.2、文件下载
文件下载几乎是每个app必备的功能，图文的下载，软件的升级等都要用到，mvvmsmart使用Retrofit+Okhttp+RxJava+RxBus实现一行代码监听带进度的文件下载。

下载文件
```java
String loadUrl = "你的文件下载路径";
String destFileDir = context.getCacheDir().getPath();  //文件存放的路径
String destFileName = System.currentTimeMillis() + ".apk";//文件存放的名称
DownLoadManager.getInstance().load(loadUrl, new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
    @Override
    public void onStart() {
        //RxJava的onStart()
    }

    @Override
    public void onCompleted() {
        //RxJava的onCompleted()
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        //下载成功的回调
    }

    @Override
    public void progress(final long progress, final long total) {
        //下载中的回调 progress：当前进度 ，total：文件总大小
    }

    @Override
    public void onError(Throwable e) {
        //下载错误回调
    }
});
```
> 在ProgressResponseBody中使用了RxBus，发送下载进度信息到ProgressCallBack中，继承ProgressCallBack就可以监听到下载状态。回调方法全部执行在主线程，方便UI的更新，详情请参考例子程序。
### 3.3、ContainerActivity
一个盛装Fragment的一个容器(代理)Activity，普通界面只需要编写Fragment，使用此Activity盛装，这样就不需要每个界面都在AndroidManifest中注册一遍

使用方法：

在ViewModel中调用BaseViewModelMVVM的方法开一个Fragment
```java
startContainerActivity(你的Fragment类名.class.getCanonicalName())
```
在ViewModel中调用BaseViewModelMVVM的方法，携带一个序列化实体打开一个Fragment
```java
Bundle mBundle = new Bundle();
mBundle.putParcelable("entity", entity);
startContainerActivity(你的Fragment类名.class.getCanonicalName(), mBundle);
```
在你的Fragment中取出实体
```java
Bundle mBundle = getArguments();
if (mBundle != null) {
    entity = mBundle.getParcelable("entity");
}
```
### 3.4、6.0权限申请
> 对RxPermissions已经熟悉的朋友可以跳过。

使用方法：

例如请求相机权限，在ViewModel中调用
```java
//请求打开相机权限
RxPermissions rxPermissions = new RxPermissions((Activity) context);
rxPermissions.request(Manifest.permission.CAMERA)
    .subscribe(new Consumer<Boolean>() {
        @Override
        public void accept(Boolean aBoolean) throws Exception {
            if (aBoolean) {
                ToastUtils.showShort("权限已经打开，直接跳入相机");
            } else {
                ToastUtils.showShort("权限被拒绝");
            }
        }
    });
```
更多权限申请方式请参考[RxPermissions原项目地址](https://github.com/tbruyelle/RxPermissions)
### 3.5、图片压缩
> 为了节约用户流量和加快图片上传的速度，某些场景将图片在本地压缩后再传给后台，所以特此提供一个图片压缩的辅助功能。

使用方法：

RxJava的方式压缩单张图片，得到一个压缩后的图片文件对象
```java
String filePath = "mnt/sdcard/1.png";
ImageUtils.compressWithRx(filePath, new Consumer<File>() {
    @Override
    public void accept(File file) throws Exception {
        //将文件放入RequestBody
        ...
    }
});
```
RxJava的方式压缩多张图片，按集合顺序每压缩成功一张，都将在onNext方法中得到一个压缩后的图片文件对象
```java
List<String> filePaths = new ArrayList<>();
filePaths.add("mnt/sdcard/1.png");
filePaths.add("mnt/sdcard/2.png");
ImageUtils.compressWithRx(filePaths, new Subscriber() {
    @Override
    public void onCompleted() {
	
    }
	
    @Override
    public void onError(Throwable e) {
	
    }
	
    @Override
    public void onNext(File file) {

    }
});
```
### 3.6、其他辅助类
**ToastUtils：** 吐司工具类

**MaterialDialogUtils：** Material风格对话框工具类

**SPUtils：** SharedPreferences工具类

**SDCardUtils：** SD卡相关工具类

**ConvertUtils：** 转换相关工具类

**StringUtils：** 字符串相关工具类

**RegexUtils：** 正则相关工具类

**KLog：** 日志打印，含json格式打印

## 4、附加

### 4.1、编译错误解决方法
> 使用databinding其实有个缺点，就是会遇到一些编译错误，而AS不能很好的定位到错误的位置，这对于刚开始使用databinding的开发者来说是一个比较郁闷的事。那么我在此把我自己在开发中遇到的各种编译问题的解决方法分享给大家，希望这对你会有所帮助。

##### 4.1.1、绑定错误
绑定错误是一个很常见的错误，基本都会犯。比如TextView的 `android:text=""` ，本来要绑定的是一个String类型，结果你不小心，可能绑了一个Boolean上去，或者变量名写错了，这时候编辑器不会报红错，而是在点编译运行的时候，在AS的Messages中会出现错误提示.

解决方法：把错误提示拉到最下面 (上面的提示找不到BR类这个不要管它)，看最后一个错误 ，这里会提示是哪个xml出了错，并且会定位到行数，按照提示找到对应位置，即可解决该编译错误的问题。

**注意：** 行数要+1，意思是上面报出第33行错误，实际是第34行错误，AS定位的不准确 (这可能是它的一个bug)

##### 4.1.2、xml导包错误
在xml中需要导入ViewModel或者一些业务相关的类，假如在xml中导错了类，那一行则会报红，但是res/layout却没有错误提示，有一种场景，非常特殊，不容易找出错误位置。就是你写了一个xml，导入了一个类，比如XXXUtils，后来因为业务需求，把那个XXXUtils删了，这时候res/layout下不会出现任何错误，而你在编译运行的时候，才会出现错误日志。苦逼的是，不会像上面那样提示哪一个xml文件，哪一行出错了，最后一个错误只是一大片的报错报告

解决方法：同样找到最后一个错误提示，找到Cannot resolve type for **xxx**这一句 (xxx是类名)，然后使用全局搜索 (Ctrl+H) ，搜索哪个xml引用了这个类，跟踪点击进去，在xml就会出现一个红错，看到错误你就会明白了，这样就可解决该编译错误的问题。

##### 4.1.3、build错误
构建多module工程时，如出现【4.1.1、绑定错误】，且你能确定这个绑定是没有问题的，经过修改后出现下图错误：


解决方法：
这种是databinding比较大的坑，清理、重构和删build都不起作用，网上很难找到方法。经过试验，解决办法是手动创建异常中提到的文件夹，或者拷贝上一个没有报错的版本中对应的文件夹，可以解决这个异常

##### 4.1.4、自动生成类错误
有时候在写完xml时，databinding没有自动生成对应的Binding类及属性。比如新建了一个activity_login.xml，按照databinding的写法加入```<layout> <variable>```后，理论上会自动对应生成ActivityLoginBinding.java类和variable的属性，可能是as对databding的支持还不够吧，有时候偏偏就不生成，导致BR.xxx报红等一些莫名的错误。

解决方法：其实确保自己的写法没有问题，是可以直接运行的，报红不一定是你写的有问题，也有可能是编译器抽风了。或者使用下面的办法</br>
第一招：Build->Clean Project；</br>第二招：Build->Rebuild Project；</br>第三招：重启大法。

## 混淆
例子程序中给出了最新的【MVVMSmart混淆规则】，包含MVVMSmart中依赖的所有第三方library，可以将规则直接拷贝到自己app的混淆规则中。在此基础上你只需要关注自己业务代码以及自己引入第三方的混淆，【MVVMSmart混淆规则】请参考app目录下的[proguard-rules.pro](./app/proguard-rules.pro)文件。

## About
**wzqjava：** 一直开发了这么多app,也经历了不少公司,每个公司在基础框架上功能都大同小异,但技术选型和代码质量参差不齐不好维护,对我个人和大部分中高级Android开发者来说如果有一套质量稳定方便维护的框架(不一定是Android黑科技,但稳定好维护)能节省许多时间和精力,所以我和我们网易项目组耗费许多精力多次改版才有MVVMSmart,目前已知有6个公司的商用项目使用这个框架,我们项目组会一直维护MVVMSmart，谢谢各位朋友的支持。如果觉得这套框架不错的话，麻烦点个 **star**，你的支持则是我们前进的动力！

**QQ群**：531944409

## License

    Copyright 2019 (王志强)
 
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
 
        http://www.apache.org/licenses/LICENSE-2.0
 
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


