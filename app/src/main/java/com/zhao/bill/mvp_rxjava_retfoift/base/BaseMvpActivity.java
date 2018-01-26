
package com.zhao.bill.mvp_rxjava_retfoift.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhao.bill.mvp_rxjava_retfoift.R;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.CustomProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * activity 基类
 * Created by Bill on 2017/9/5.
 */
public abstract class BaseMvpActivity<P extends BaseContract.BasePresenter<V>,
        V extends BaseContract.BaseView> extends RxAppCompatActivity {


    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 线程调度
     */
    protected <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {

                    // 可添加网络连接判断等
                    if (!isNetworkAvailable(BaseMvpActivity.this)) {
                        Toast.makeText(BaseMvpActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycle);
    }

    public Context context;
    protected Activity mActivity;
    protected P mPresenter;

    private static final int FAST_CLICK_INTERVAL = 400;// 快速点击间隔，单位为毫秒
    private long mLastClickTime;    // 记录最后一次点击的时间
    private boolean isFirstStart = true;
    private Unbinder mBind;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onCreate(savedInstanceState);

        context = this;
        mActivity = this;

        int contentViewID = getContentViewID();
        if (contentViewID != 0) {
            this.setContentView(contentViewID);
        }

        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_title_bg), 0);

        mBind = ButterKnife.bind(this);

        initPresenter();
        initView();
        initData();
        initListeners();
    }

    protected abstract void initView();

    protected void initListeners() {
    }

    protected void initData() {
    }

    private void initPresenter() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.onViewAttached((V) this);
        }
    }

    protected P getPresenter() {
        return null;
    }

    /**
     * bind layout resource file
     */
    protected abstract int getContentViewID();

    @Override
    protected void onStart() {
        super.onStart();

        if (mPresenter != null) {
            mPresenter.onStart(isFirstStart);
            isFirstStart = false;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            onBack();
            return true;//系统不会自己销毁页面了
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始进度
     *
     * @param tip
     */
    public void startProgressDialog(String tip) {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(context, tip);
        }

        progressDialog.show();
    }

    /**
     * 结束进度
     */
    public void stopProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 将返回事件统一处理，如果在返回事件时需要一些自定义的事件就重写这个方法
     */
    protected void onBack() {
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onViewDetached();
            mPresenter.onPresenterDestroyed();
        }

        if (mBind != null) {
            mBind.unbind();
            mBind = null;
        }

        // 防止窗口泄露
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /****************************  activity  跳转  *********************************/

    public void startActivity(Class<?> clazz) {
        Intent i = new Intent();
        i.setClass(this, clazz);
        super.startActivity(i);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void startActivity(Class<?> clazz, Intent intent) {
        if (intent != null) {
            intent.setClass(this, clazz);
            super.startActivity(intent);
            // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public void startActivityForResult(final Class<?> className, final Intent intent) {
        intent.setClass(this, className);
        this.startActivityForResult(intent, 0);// 请求code默认 0
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void startActivityForResult(final Class<?> className, final int requestCode) {
        final Intent i = new Intent();
        i.setClass(this, className);
        this.startActivityForResult(i, requestCode);
        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void startActivityForResult(Class<?> clazz, Intent intent, int requestCode) {
        if (intent != null) {
            intent.setClass(this, clazz);
            intent.putExtra("RequestCode", requestCode);
        }
        super.startActivityForResult(intent, requestCode);
        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 判断是否是快速点击
     */
    public synchronized boolean isFastClick() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - mLastClickTime > FAST_CLICK_INTERVAL) {
            mLastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 初始化标题栏1(返回TextView)
     *
     * @param isShowBackBtn 左上角的返回键是否显示
     * @param titleText     中间的页面标题
     * @param rightBtText   右上角显示的文字控件
     * @return 右上角按钮，便于添加事件
     */
    protected TextView loadTitleBar(boolean isShowBackBtn, String titleText, String rightBtText) {
        // 左上角返回按钮
        ImageView leftBtn = (ImageView) findViewById(R.id.left_btn);
        leftBtn.setVisibility(isShowBackBtn ? View.VISIBLE : View.GONE);
        leftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        findViewById(R.id.left_tv).setVisibility(View.GONE);

        // 标题文字
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        if (TextUtils.isEmpty(titleText)) {
            titleTv.setVisibility(View.INVISIBLE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(titleText);
        }

        // 右上角按钮
        TextView rightBtn = (TextView) findViewById(R.id.right_tv);

        if (TextUtils.isEmpty(rightBtText)) {
            rightBtn.setVisibility(View.GONE);
        } else {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText(rightBtText);
        }

        return rightBtn;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
