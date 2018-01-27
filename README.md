# MVP_RxJava_Retfoift
Mvp模式 + RxJava调度 + Retrofit网络请求

一、MVP模式
    
    MVP模式的作用
    
        分离了视图逻辑和业务逻辑，降低了耦合，Activity只处理生命周期的任务，代码变得更加简洁。 
        视图逻辑和业务逻辑分别抽象到了View和Presenter的接口中去，提高代码的可阅读性。 
        Presenter被抽象成接口，可以有多种具体的实现，所以方便进行单元测试。把业务逻辑抽到Presenter中去，
        避免后台线程引用着Activity导致Activity的资源无法被系统回收从而引起内存泄露和OOM。
        
        其中最重要的有三点：
   
        Activity：代码变得更加简洁。
                  相信很多人阅读代码的时候，都是从Activity开始的，对着一个1000+行代码的Activity，看了都觉得难受。
                  使用MVP之后，Activity就能瘦身许多了，基本上只有FindView、SetListener以及Init的代码。
                  其他的就是对Presenter的调用，还有对View接口的实现。
                  这种情形下阅读代码就容易多了，而且你只要看Presenter的接口，就能明白这个模块都有哪些业务，很快就能定位到具体代码。
                  Activity变得容易看懂，容易维护，以后要调整业务、删减功能也就变得简单许多，方便进行单元测试。
                 
        MVP中，由于业务逻辑都在Presenter里，我们完全可以写一个PresenterTest的实现类继承Presenter的接口，
                  现在只要在Activity里把Presenter的创建换成PresenterTest，就能进行单元测试了，测试完再换回来即可。
                  万一发现还得进行测试，那就再换成PresenterTest吧。
    
        避免 Activity 的内存泄露 Android APP 发生OOM的最大原因就是出现内存泄露造成APP的内存不够用，而造成内存泄露的
                  两大原因之一就是Activity泄露（Activity Leak）（另一个原因是Bitmap泄露（Bitmap Leak））。
                  Java一个强大的功能就是其虚拟机的内存回收机制，这个功能使得Java用户在设计代码的时候，不用像C++用户那样考虑对象的回收问题。
                  然而，Java用户总是喜欢随便写一大堆对象，然后幻想着虚拟机能帮他们处理好内存的回收工作。可是虚拟机在回收内存的时候，
                  只会回收那些没有被引用的对象，被引用着的对象因为还可能会被调用，所以不能回收。 
                  Activity是有生命周期的，用户随时可能切换Activity，当APP的内存不够用的时候，系统会回收处于后台的Activity的资源以避免OOM。
                  采用传统的MV模式，一大堆异步任务和对UI的操作都放在Activity里面，比如你可能从网络下载一张图片，在下载成功的回调里把图片加载到 
                  Activity 的 ImageView 里面，所以异步任务保留着对Activity的引用。这样一来，即使Activity已经被切换到后台（onDestroy已经执行），
                  这些异步任务仍然保留着对Activity实例的引用，所以系统就无法回收这个Activity实例了，结果就是Activity Leak。Android的组件中，
                  Activity对象往往是在堆（Java Heap）里占最多内存的，所以系统会优先回收Activity对象，如果有Activity Leak，APP很容易因为内存不够而OOM。
    
        采用MVP模式，只要在当前的Activity的onDestroy里，分离异步任务对Activity的引用，就能避免 Activity Leak。
    
    MVP和MVC的区别：
        作为一种新的模式，MVP与MVC有着一个重大的区别：在MVP中View并不直接使用Model，它们之间的通信是通过Presenter (MVC中的Controller)来进行的，
        所有的交互都发生在Presenter内部，而在MVC中View会直接从Model中读取数据而不是通过 Controller。
        
        在MVC里，View是可以直接访问Model的！从而，View里会包含Model信息，不可避免的还要包括一些业务逻辑。 
        在MVC模型里，更关注的Model的不变，而同时有多个对Model的不同显示，即View。所以，在MVC模型里，Model不依赖于View，
        但是View是依赖于Model的。不仅如此，因为有一些业务逻辑在View里实现了，导致要更改View也是比较困难的，至少那些业务逻辑是无法重用的。
        虽然 MVC 中的 View 的确“可以”访问 Model，但是我们不建议在 View 中依赖 Model，而是要求尽可能把所有业务逻辑都放在 Controller 中处理，
        而 View 只和 Controller 交互。

    MVP如何解决MVC的问题编辑
            在MVP里，Presenter完全把Model和View进行了分离，主要的程序逻辑在Presenter里实现。而且，Presenter与具体的View是没有直接关联的，
        而是通过定义好的接口进行交互，从而使得在变更View时候可以保持Presenter的不变，即重用！不仅如此，我们还可以编写测试用的View，模拟用户的各种操作，
        从而实现对Presenter的测试--而不需要使用自动化的测试工具。我们甚至可以在Model和View都没有完成时候，就可以通过编写MockObject
       （即实现了Model和View的接口，但没有具体的内容的）来测试Presenter的逻辑。
            在MVP里，应用程序的逻辑主要在Presenter里实现，其中的View是很薄的一层。因此就有人提出了Presenter First的设计模式，
        就是根据UserStory来首先设计和开发Presenter。在这个过程中，View是很简单的，能够把信息显示清楚就可以了。在后面，根据需要再随便更改View，
        而对Presenter没有任何的影响了。 如果要实现的UI比较复杂，而且相关的显示逻辑还跟Model有关系，就可以在View和Presenter之间放置一个Adapter。
        由这个 Adapter来访问Model和View，避免两者之间的关联。而同时，因为Adapter实现了View的接口，从而可以保证与Presenter之间接口的不变。
        这样就可以保证View和Presenter之间接口的简洁，又不失去UI的灵活性。 
            在MVP模式里，View只应该有简单的Set/Get的方法，用户输入和设置界面显示的内容，除此就不应该有更多的内容，绝不容许直接访问Model--这就是与MVC很大的不同之处。

    MVP的优点
        1、模型与视图完全分离，我们可以修改视图而不影响模型
        2、可以更高效地使用模型，因为所有的交互都发生在一个地方——Presenter内部
        3、我们可以将一个Presenter用于多个视图，而不需要改变Presenter的逻辑。这个特性非常的有用，因为视图的变化总是比模型的变化频繁。
        4、如果我们把逻辑放在Presenter中，那么我们就可以脱离用户接口来测试这些逻辑（单元测试）

    MVP的缺点编辑
        由于对视图的渲染放在了Presenter中，所以视图和Presenter的交互会过于频繁。
        还有一点需要明白，如果Presenter过多地渲染了视图，往往会使得它与特定的视图的联系过于紧密。
        一旦视图需要变更，那么Presenter也需要变更了。
        比如说，原本用来呈现Html的Presenter现在也需要用于呈现Pdf了，那么视图很有可能也需要变更。


二、Retrofit请求
    
    1、Retrofit的优点
     Q1.什么是网络框架？
        业务层-->网络调度层-->网络执行层
    
        这里：
            Retrofit是网络调度层
            类似volley, retrofit, android-async-http
            做具体业务请求、线程切换、数据转换
    
    OkHttp是网络执行层
        类似HttpClient, HttpUrlConnection
        做底层网络请求
    
    Q2.为什么用Retrofit?
        因为Retrofit有：
        CallAdapter-请求适配器：
        可以实现多种请求响应形式：同步方式、异步回调方式、RxJava方式
        Converter-数据转换器：
        可以自己定义responseBodyConverter和requestBodyConverter,实现加密功能和各种奇葩格式的解析
    
    
    2、使用 Retrofit 的步骤共有5个：
    
        步骤1：添加Retrofit库的依赖
            build.gradle:
                    compile 'com.squareup.okhttp3:logging-interceptor:3.8.1'
                    compile 'com.squareup.retrofit2:retrofit:2.3.0'
                    compile 'com.squareup.retrofit2:converter-gson:2.3.0'
                    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
                    
        步骤2：创建接收服务器返回数据的类
          /**
           * 服务器通用返回数据格式  返回的data是object
           * Created by Bill on 2016/11/8.
           */
          public class BaseEntity<E> implements Serializable {
          
              private int code;
              private String message;
              private String result;
              private E data;
          
              public int getCode() {
                  return code;
              }
          
              public void setCode(int code) {
                  this.code = code;
              }
          
              public String getMessage() {
                  return message;
              }
          
              public void setMessage(String message) {
                  this.message = message;
              }
          
              public E getData() {
                  return data;
              }
          
              public void setData(E data) {
                  this.data = data;
              }
          
              public String getResult() {
                  return result;
              }
          
              public void setResult(String result) {
                  this.result = result;
              }
          }
          
        步骤3：创建用于描述网络请求的接口 
    
              @FormUrlEncoded
              @POST(URLs.DB_LIST)
              Observable<BaseEntityList<ChooseDbBean.DataBean>> getList(@FieldMap Map<String, String> map);
              
        步骤4：创建 Retrofit 实例 
              /**
               * 获取实例
               */
              private static RequestManager requestManager = null;
          
              private RequestManager() {
          
              }
          
              public static RequestManager getInstance() {
                  if (requestManager == null) {
          
                      return new RequestManager();
                  }
                  return requestManager;
              }
              
                  /**
                   * 设置观察者
                   *
                   * @param observable
                   * @param subscriber
                   * @param <T>
                   */
                  public <T> void toSubscribe(Observable<T> observable, Observer<T> subscriber) {
              
                      observable
                              .subscribeOn(Schedulers.io()) // 指定Observable(被观察者)所在的线程，或者叫做事件产生的线程
                              .unsubscribeOn(Schedulers.io()) //在io线程中处理网络请求
                              .observeOn(AndroidSchedulers.mainThread()) //在主线程中处理数据   指定 Observer(观察者)所运行在的线程，或者叫做事件消费的线程。
                              .subscribe(subscriber);
                  }
              
        步骤5：创建网络请求接口实例并配置网络请求参数，并对数据进行处理
            
           Map<String, String> map = new HashMap<>();
            
           RequestManager.getInstance().toSubscribe(RequestManager.createMainApi().getList(map),
                          new BaseObserverList<ChooseDbBean.DataBean>(context) {
                              @Override
                              protected void onHandleSuccess(List<ChooseDbBean.DataBean> t) {
                                  iDataToPresenter.onSuccess(t);
                              }
          
                              @Override
                              protected void onHandleError(String msg) {
                                  iDataToPresenter.onFailed(msg);
                              }
          
                              @Override
                              protected void onHandleComplete() {
          
                              }
                          });
                          

三、RxJava