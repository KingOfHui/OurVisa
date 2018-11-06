package com.eshel.ourvisa.util;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eshel.ourvisa.BaseApplication;
import com.eshel.ourvisa.BuildConfig;
import com.eshel.ourvisa.R;

import java.io.File;

/**
 * createBy Eshel
 * createTime: 2017/10/5 01:46
 * desc: UI 相关工具类
 */

@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
public class UIUtil {

	private static Toast mToast;

	/**
	 * 对 Toast 工具进行适配 , 解决华为小米设备显示异常问题
	 */
	private static void toastCompatibleHuaweiXiaomi(){
		if(
			"huawei".equalsIgnoreCase(Build.MANUFACTURER)
		 || "xiaomi".equalsIgnoreCase(Build.MANUFACTURER)
		) {
			mToast = null;
		}
	}

	/**
	 * @deprecated
	 * @see UIUtil#isDebug()
	 * 判断apk是否是debug模式
	 */
	public static boolean isApkDebugable(Context context) {
		try {
			ApplicationInfo info= context.getApplicationInfo();
			return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isDebug(){
		return BuildConfig.DEBUG;
	}
	@Deprecated
	public static void setDebug(boolean isDebug){
//		debug = isDebug;
	}

	public static String getString(int resId){
		if(resId == 0)
			return "";
		return BaseApplication.getContext().getResources().getString(resId);
	}

	/**
	 * getString 并使首字母大写
	 */
	public static String getStringFristUpperCase(int resId){
		String text = getString(resId);
		String c = String.valueOf(text.charAt(0));
		return c.toUpperCase() + text.substring(1, text.length());
	}
	public static int getColor(int resId){
		return BaseApplication.getContext().getResources().getColor(resId);
	}
	public static void debugToast(CharSequence text){
		if(isDebug())
			toast("debug: "+text);
	}
	public static void debugShortToast(CharSequence text){
		if(isDebug())
			toastShort("debug: "+text);
	}
	public static void toastShort(final CharSequence text){
		if(Looper.myLooper() == Looper.getMainLooper())
			showToast(0,text);
		else
			ThreadUtil.postMain(new Runnable() {
				@Override
				public void run() {
					showToast(0,text);
				}
			});
	}

	public static void toastShort(int textId){
		toastShort(getString(textId));
	}

	public static void toast(int textId){
		toast(getString(textId));
	}

	public static void toast(final CharSequence text){
		if(Looper.myLooper() == Looper.getMainLooper())
			showToast(1,text);
		else
			ThreadUtil.postMain(new Runnable() {
				@Override
				public void run() {
					showToast(1,text);
				}
			});
	}

	public static Toast getToast() {
		toastCompatibleHuaweiXiaomi();
		if(mToast == null){
			createToast(Toast.LENGTH_SHORT,"");
		}
		return mToast;
	}

	@SuppressLint("ShowToast")
    private static void createToast(int longorshort, CharSequence text){
		synchronized (Toast.class) {
			if(mToast == null) {
				mToast = Toast.makeText(BaseApplication.getContext(), "",
						longorshort == 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
				mToast.setText(text);
				View view = mToast.getView();
				view.setBackgroundResource(R.drawable.shap_loading_bg);
				View msg = ((ViewGroup)view).getChildAt(0);
				if(msg instanceof TextView){
					((TextView) msg).setTextColor(Color.WHITE);
					((TextView) msg).setGravity(Gravity.CENTER);
				}
				ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) msg.getLayoutParams();
				if(params != null){
					params.leftMargin = params.rightMargin = DensityUtil.dp2px(10);
					msg.setLayoutParams(params);
				}
				/*
				com.yiju.publiclib.widget.Toast.setSupport(new com.yiju.publiclib.widget.Toast.Support() {
					@Override
					public Toast getToast() {
						return UIUtil.getToast();
					}
				});*/
			}
		}
	}

	private static void showToast(int longorshort, CharSequence text){
		if(text == null)
			return;
		toastCompatibleHuaweiXiaomi();
		if(mToast == null) {
			createToast(longorshort,text);
		}else {
			mToast.setDuration(longorshort == 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
			mToast.setText(text);
		}
		mToast.show();
	}
	public static int getScreenHeight(){
		return BaseApplication.getContext().getResources().getDisplayMetrics().heightPixels;
	}
	public static int getScreenWidth(){
		return BaseApplication.getContext().getResources().getDisplayMetrics().widthPixels;
	}
	public static Context getContext(){
		return BaseApplication.getContext();
	}

	/**
	 * 获取状态栏高度
	 */
	public static int getStateBarHeight(){
		//获取状态栏高度是为沉浸式布局预留状态栏位置, api < 19 无沉浸式, 故直接返回 0
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
			return 0;
		}
		return getStateBarHeightK();
	}

	/**
	 * 返回真实状态栏高度
	 */
	public static int getStateBarHeightK(){
		int result = 0;
		int resourceId = BaseApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = BaseApplication.getContext().getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 获取底部导航栏高度
	 */
	public static int getNavigationBarHeight(Activity activity) {
	    if(!isNavigationBarShow(activity))
	        return 0;
		Resources resources = activity.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
	}

    /**
     * 底部虚拟按键是否显示
     */
    public static boolean isNavigationBarShow(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y!=size.y;
        }else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !(menu || back);
        }
    }

	/**
	 * 状态栏全透明
	 */
	public static void setStateColorNone(Activity activity){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(Color.TRANSPARENT);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
		}
	}

	/**
	 * 根据给定的宽和高进行拉伸
	 *
	 * @param origin    原图
	 * @param newWidth  新图的宽
	 * @param newHeight 新图的高
	 * @return new Bitmap
	 */
	public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
		if (origin == null) {
			return null;
		}
		int height = origin.getHeight();
		int width = origin.getWidth();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
		Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
		if (!origin.isRecycled()) {
			origin.recycle();
		}
		return newBM;
	}


	public static<T extends ViewGroup.LayoutParams> T setViewLayoutParmasByParent(View view, ViewGroup parent, int width, int height){
		if(view == null)
			return null;
		ViewGroup.LayoutParams layoutParams;
		if(parent instanceof LinearLayout){
			layoutParams = new LinearLayout.LayoutParams(width,height);
		}else if(parent instanceof RelativeLayout){
			layoutParams = new RelativeLayout.LayoutParams(width,height);
		}else if(parent instanceof FrameLayout){
			layoutParams = new FrameLayout.LayoutParams(width,height);
		}else {
			layoutParams = new ViewGroup.LayoutParams(width, height);
		}
		view.setLayoutParams(layoutParams);
		return (T) layoutParams;
	}

	public static ViewGroup.LayoutParams setViewLayoutParmas(View view, int width, int height){
        if(view == null)
            return null;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(layoutParams == null)
            layoutParams = new ViewGroup.LayoutParams(width,height);
        else {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
        return layoutParams;
    }

	public static ViewGroup.LayoutParams setViewLayoutParmasDp(View view, int widthDp, int heightDp){
		return setViewLayoutParmas(view,(widthDp>0?DensityUtil.dp2px(widthDp):widthDp),heightDp>0?DensityUtil.dp2px(heightDp):heightDp);
	}

	public static void setViewWidth(View view, int widthPx){
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if(layoutParams == null)
			layoutParams = new ViewGroup.LayoutParams(widthPx,WRAP_CONTENT);
		else {
			layoutParams.width = widthPx;
		}
		view.setLayoutParams(layoutParams);
	}

	public static void setViewHeight(View view, int heightPx){
		if(view == null)
			return;
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if(layoutParams == null)
			layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,heightPx);
		else {
			layoutParams.height = heightPx;
		}
		view.setLayoutParams(layoutParams);
	}

	public static void setViewHeightNotEnforce(View view, int heightPx){
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if(layoutParams == null)
			return;
		else {
			layoutParams.height = heightPx;
		}
		view.setLayoutParams(layoutParams);
	}

	public static float getRawX(View view){
		int[] location = new  int[2] ;
		view.getLocationInWindow(location);
		return location[0];
	}

	public static float getRawY(View view){
		int[] location = new  int[2] ;
		view.getLocationInWindow(location);
		return location[1];
	}

	/**
	 * 重新调整 (topMargin bottomMargin)margin, 即所有控件 margin 共减少 offset 像素
	 * 按照比例减少 margin
	 */
	public static void resetMargin(ViewGroup views, float offset){
		if(offset == 0)
			return;
		if(ObjUtil.notNull(views)){
			int allMargin = 0;
			//循环算出总margin
			for (int i = 0; i < views.getChildCount(); i++) {
				View temp = views.getChildAt(i);
				ViewGroup.LayoutParams lp = temp.getLayoutParams();
				if(lp instanceof ViewGroup.MarginLayoutParams){
					allMargin += ((ViewGroup.MarginLayoutParams) lp).bottomMargin;
					allMargin += ((ViewGroup.MarginLayoutParams) lp).topMargin;
				}
			}
			//排除除零异常
			if(DataUtil.isZero(allMargin))
				return;
			//根据总margin值重设margin
			for (int i = 0; i < views.getChildCount(); i++) {
				View temp = views.getChildAt(i);
				ViewGroup.LayoutParams lp = temp.getLayoutParams();
				if(lp instanceof ViewGroup.MarginLayoutParams){
					ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) lp;
					mlp.bottomMargin = (int) (mlp.bottomMargin - mlp.bottomMargin*1.0f/allMargin * offset);
					mlp.topMargin = (int) (mlp.topMargin - mlp.topMargin * 1.0f / allMargin * offset);
				}
			}
		}
	}

    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    @SuppressWarnings("SameParameterValue")
    private static boolean compareColor(int c1, int c2, int error){
    	return Math.abs(c1 - c2)<=error;
	}


	public static Bitmap changeBitmap(Bitmap bitmap, int srcColor, int destColor, boolean hasAlpha){
		int bitmap_h;
		int bitmap_w;
		int mArrayColorLengh;
		int[] mArrayColor;
		int count = 0;
		mArrayColorLengh = bitmap.getWidth() * bitmap.getHeight();
		mArrayColor = new int[mArrayColorLengh];
		bitmap_w=bitmap.getWidth();
		bitmap_h =bitmap.getHeight();
		for (int i = 0; i < bitmap.getHeight(); i++) {
			for (int j = 0; j < bitmap.getWidth(); j++) {
				int color = bitmap.getPixel(j, i);
				int r = Color.red(color);
				int g = Color.green(color);
				int b = Color.blue(color);
				int a = Color.alpha(color);

				int r1 = Color.red(srcColor);
				int g1 = Color.green(srcColor);
				int b1 = Color.blue(srcColor);
				int a1 = Color.alpha(srcColor);
				if(!hasAlpha){
					a = a1 = 0xff;
				}
				if(compareColor(r,r1,20)
						&&compareColor(g,g1,20)
						&&compareColor(b,b1,20)
						&&compareColor(a,a1,20)){
					color = destColor;
				}
				mArrayColor[count]=color;
				count++;
			}
		}
        return Bitmap.createBitmap( mArrayColor, bitmap_w, bitmap_h, Bitmap.Config.ARGB_8888);
	}

	/**
	 * 改变非透明色
	 * @param bitmap bitmap
	 * @param a1 alpha
	 * @param r1 red
	 * @param g1 green
	 * @param b1 blue
	 */
	public static Bitmap changeBitmap(Bitmap bitmap, int a1, int r1, int g1, int b1){
		int bitmap_h;
		int bitmap_w;
		int mArrayColorLengh;
		int[] mArrayColor;
		int count = 0;
		mArrayColorLengh = bitmap.getWidth() * bitmap.getHeight();
		mArrayColor = new int[mArrayColorLengh];
		bitmap_w=bitmap.getWidth();
		bitmap_h =bitmap.getHeight();
		int newcolor=-1;
		for (int i = 0; i < bitmap.getHeight(); i++) {
			for (int j = 0; j < bitmap.getWidth(); j++) {
				//获得Bitmap 图片中每一个点的color颜色值
				int color = bitmap.getPixel(j, i);
				//将颜色值存在一个数组中 方便后面修改
				// mArrayColor[count] = color;
				int r = Color.red(color);
				int g = Color.green(color);
				int b = Color.blue(color);
				int a = Color.alpha(color);
				if(a >= 10){
					a+=a1;
					r+=r1;
					g+=g1;
					b+=b1;
					if(a>255)
						a=255;
					if(a<0)
						a=0;
					if(r>255)
						r=255;
					if(r<0)
						r=0;
					if(g>255)
						g=255;
					if(g<0)
						g=0;
					if(b>255)
						b=255;
					if(b<0)
						b=0;
				}
				/*if ((90<r&&r<=200)&&(90<g&&g<=200)&&(90<b&&b<=200)){//大概得把非道路（路旁变透明）
					a=0;
				}else  if (r==255&&g==255&&b==33){//把黄色的箭头白色 因为黄色箭头rgb大部分是255 255 33(值可以用画图工具取值) 组合
					// 但是还有小部分有别的值组成（箭头所不能变成全白有黄色斑点）
					r=255;
					g=255;
					b=255;
				}*/
				color = Color.argb(a, r, g, b);
				mArrayColor[count]=color;
				count++;
			}
		}
        return Bitmap.createBitmap( mArrayColor, bitmap_w, bitmap_h, Bitmap.Config.ARGB_8888);
	}

	public static Bitmap changeBitmapColor(Bitmap bitmap, int a1, int r1, int g1, int b1){
		int bitmap_h;
		int bitmap_w;
		int mArrayColorLengh;
		int[] mArrayColor;
		int count = 0;
		mArrayColorLengh = bitmap.getWidth() * bitmap.getHeight();
		mArrayColor = new int[mArrayColorLengh];
		bitmap_w=bitmap.getWidth();
		bitmap_h =bitmap.getHeight();
		int newcolor=-1;
		for (int i = 0; i < bitmap.getHeight(); i++) {
			for (int j = 0; j < bitmap.getWidth(); j++) {
				//获得Bitmap 图片中每一个点的color颜色值
				int color = bitmap.getPixel(j, i);
				//将颜色值存在一个数组中 方便后面修改
				// mArrayColor[count] = color;
				int r = Color.red(color);
				int g = Color.green(color);
				int b = Color.blue(color);
				int a = Color.alpha(color);
				if(a >= 10){
					if(a1 != -1)
						a = a1;
					if(r1 != -1)
						r=r1;
					if(g1 != -1)
						g = g1;
					if(b1 != -1)
						b=b1;
				}
				color = Color.argb(a, r, g, b);
				mArrayColor[count]=color;
				count++;
			}
		}
        return Bitmap.createBitmap( mArrayColor, bitmap_w, bitmap_h, Bitmap.Config.ARGB_8888 );
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}
	public static long lastHideSoftTime;
	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInput(Activity activity){
		if(activity != null){
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm != null){
				if(isSoftShowing(activity)) {
					long currentTime = System.currentTimeMillis();
					if(currentTime - lastHideSoftTime <= 200)
						return;
					lastHideSoftTime = currentTime;
					imm.toggleSoftInput(0, 0);
				}
			}
		}
	}

	public static void hideSoftInput(View view){
		if(view == null)
			return;
		Activity activity = ViewUtil.getActivity(view);
		if(ObjUtil.notNull(activity)){
			UIUtil.hideSoftInput(activity);
		}
	}

	public static void showSoftInput(Activity activity){
		if(activity != null){
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm != null){
				if(!isSoftShowing(activity))
					imm.toggleSoftInput(0,0);
			}
		}
	}

	public static boolean isSoftShowing(Activity context) {
		//获取当前屏幕内容的高度
		int screenHeight = context.getWindow().getDecorView().getHeight();
		//获取View可见区域的bottom
		Rect rect = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		//考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
		//选取screenHeight*2/3进行判断
		return screenHeight*2/3 > rect.bottom;
	}

	/**
	 * 获取软键盘高度
	 */
	public static int getSoftInputY(Activity activity){
		//获取当前屏幕内容的高度
		int screenHeight = activity.getWindow().getDecorView().getHeight();
		//获取View可见区域的bottom
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		return screenHeight - rect.bottom - getNavigationBarHeight(activity);
	}

	/**
	 * 设置监听, 当editText内容为空时button不可点击
	 */
	public static void bindButtonAndEditText(final Button button, final EditText... editTexts){
		if(button != null && editTexts != null){
			boolean allNotNull = true;
			for (EditText text : editTexts) {
				if (text.getText().toString().length() == 0) {
					allNotNull = false;
					break;
				}
			}
			if(allNotNull){
				button.setClickable(true);
				button.getBackground().setAlpha(255);
			}else {
				button.setClickable(false);
				button.getBackground().setAlpha(172);
			}
			TextWatcher textWatcher = new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if (s.toString().length() == 0) {
						if (button.isClickable()) {
							button.setClickable(false);
							button.getBackground().setAlpha(172);
						}
					} else {
						for (EditText text : editTexts) {
							if (text.getText().toString().length() == 0) {
								break;
							}
						}
						if (!button.isClickable()) {
							button.setClickable(true);
							button.getBackground().setAlpha(255);
						}
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
				}
			};
			for (final EditText editText : editTexts) {
				if(editText != null){
					editText.addTextChangedListener(textWatcher);
				}
			}
		}
	}

	public static boolean checkTextViewIsEmpty(TextView textView){
		return textView == null || getStringForTextView(textView).length()==0;
	}
	public static String getStringForTextView(TextView textView){
		return textView == null ? null : textView.getText().toString().trim();
	}
/*
	public static HookPopupWindow createPopupWindow(Context context, int layoutIds, int width, int height, int style){
		View contentView = LayoutInflater.from(context).inflate(layoutIds, null, false);
		return createPopupWindow(contentView, width, height, style);
	}

	public static HookPopupWindow createPopupWindow(View contentView, int width, int height, int style){
		HookPopupWindow mCountryWindow = new HookPopupWindow(contentView, width, height, true);
		// 设置PopupWindow的背景
		mCountryWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 设置PopupWindow是否能响应外部点击事件
		mCountryWindow.setOutsideTouchable(true);
		mCountryWindow.setTouchable(true);
		// 设置PopupWindow是否能响应点击事件 两条一起使用才生效
		if(style != 0)
			mCountryWindow.setAnimationStyle(style);
		return mCountryWindow;
	}

	public static HookPopupWindow createLoadingPopupWindow(final Activity context, int layoutIds, int style){
		HookPopupWindow popupWindow = createPopupWindow(context, layoutIds, UIUtil.WRAP_CONTENT, UIUtil.WRAP_CONTENT, style);
		popupWindow.canDismiss = false;
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				UIUtil.activityToNormal(context,300);
			}
		});
		UIUtil.activityToGrayTran(context,300);
		popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER,0,0);
		return popupWindow;
	}*/



	public static void useCamera(Activity activity, int requestcode, Uri outputUri){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			int i = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
			if(i != PackageManager.PERMISSION_GRANTED){
				activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, requestcode);
				return;
			}
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		activity.startActivityForResult(intent, requestcode);
	}
	public static final float minAlpha = 0.5f;
	public static void activityToGrayTran(Activity activity){
		changeWindowAlpha(activity.getWindow(),0,1.0f,minAlpha);
	}

	public static void activityToGrayTran(View view, long time){
		if(view != null && view.getContext() instanceof Activity)
			activityToGrayTran(ViewUtil.getActivity(view), time);
		else {
			Log.logE("ERROR !!! view.getContext() not activity", new Exception());
		}
	}

	public static void activityToGrayTran(View view){
		activityToGrayTran(view, 0);
	}

	public static void activityToGrayTran(final Activity activity, long time){
		changeWindowAlpha(activity.getWindow(),time,1.0f,minAlpha);
	}
	public static void activityToNormal(final Activity activity, long time){
		changeWindowAlpha(activity.getWindow(),time,minAlpha,1.0f);
	}

	public static void changeWindowAlpha(final Window window, long time, float fromAlpha, float toAlpha){
//		if(Build.BRAND.equals())
		String test = Build.BOARD;//universal7885
//		UIUtil.debugShortToast(test);
		WindowManager.LayoutParams lp = null;
		View decorView = null;
		if(Build.BOARD.equals("universal7885"))
			decorView = window.getDecorView();
		else
			lp = window.getAttributes();
		ValueAnimator va = ValueAnimator.ofFloat(fromAlpha,toAlpha);
		va.setDuration(time);
		final WindowManager.LayoutParams finalLp = lp;
		final View finalDecorView = decorView;
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (float) animation.getAnimatedValue();
				if(finalLp != null) {
					finalLp.alpha = value;
					window.setAttributes(finalLp);
				}

				if(finalDecorView != null)
					finalDecorView.setAlpha(value);
			}
		});
		va.start();
	}
	public static void activityToNormal(Activity activity){
		changeWindowAlpha(activity.getWindow(),0,0.3f,1.0f);
//		WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
//		lp.alpha=1.0f;
//		activity.getWindow().setAttributes(lp);
	}
	public static void viewsToGrayTran(View... views){
		for (View view : views) {
			view.setAlpha(0.3f);
		}
	}
	public static void viewsToGrayTran(long time, View... views){
		AlphaAnimation animation = new AlphaAnimation(1.0f,0.3f);
		animation.setDuration(time);
		animation.setFillAfter(true);
		for (View view : views) {
			if(view.getVisibility() == View.VISIBLE)
				view.startAnimation(animation);
		}
	}
	public static void viewsToNormal(long time, View... views){
		AlphaAnimation animation = new AlphaAnimation(0.3f,1.0f);
		animation.setDuration(time);
		animation.setFillAfter(true);
		for (View view : views) {
			if(view.getVisibility() == View.VISIBLE)
				view.startAnimation(animation);
		}
	}
	public static void viewsToNormal(View... views){
		for (View view : views) {
			view.setAlpha(1.0f);
		}
	}

	public static TextView getDefaultFragmentLayout(Object object){
		Context context;
		if(object instanceof Fragment)
			context = ((Fragment) object).getContext();
		else if(object instanceof Context)
			context = (Context) object;
		else {
			return null;
		}
		TextView root = new TextView(context);
		root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		root.setText(object.getClass().getSimpleName());
		root.setGravity(Gravity.CENTER);
		return root;
	}

/*	public static View getEmptyView(Context context, CharSequence title, int imageResId){
		View view = View.inflate(context, R.layout.layout_empty, null);
		ImageView image = view.findViewById(R.id.image);
		TextView text = view.findViewById(R.id.text);
		text.setText(title);
		image.setImageResource(imageResId);
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		return view;
	}*/

	public static boolean isViewCovered(final View view){
		View currentView = view;

		Rect currentViewRect = new Rect();
		boolean partVisible =currentView.getGlobalVisibleRect(currentViewRect);
		boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >=view.getMeasuredHeight();
		boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= view.getMeasuredWidth();
		boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
		if (!totalViewVisible)//if any part of the view is clipped by any of its parents,return true
			return true;

		while (currentView.getParent() instanceof ViewGroup) {
			ViewGroup currentParent = (ViewGroup) currentView.getParent();
			if (currentParent.getVisibility() != View.VISIBLE)//if the parent of view is not visible,return true
				return true;

			int start = indexOfViewInParent(currentView, currentParent);
			for (int i = start + 1; i < currentParent.getChildCount(); i++) {
				Rect viewRect = new Rect();
				view.getGlobalVisibleRect(viewRect);
				View otherView = currentParent.getChildAt(i);
				Rect otherViewRect = new Rect();
				otherView.getGlobalVisibleRect(otherViewRect);
				if (Rect.intersects(viewRect, otherViewRect))//if view intersects its older brother(covered),return true
					return true;
			}
			currentView = currentParent;
		}
		return false;
	}


	private static int indexOfViewInParent(View view, ViewGroup parent){
		int index;
		for (index = 0; index < parent.getChildCount(); index++) {
			if (parent.getChildAt(index) == view)
				break;
		}
		return index;
	}

	/*public static void compressImage(Activity activity, File image, CompressImage.CompressListener listener){
		compressImage(activity,listener,image);
	}

	public static void compressImage(Activity activity, CompressImage.CompressListener listener, File... images){
		Log.log("compressImage invoke");
		Log.log(images);
		if(images == null)
			return;
		CompressConfig config;
		config = new CompressConfig.Builder().setMaxSize(1024*1024/2)
				.setMaxPixel(800)
				.enableReserveRaw(true)
				.create();
		ArrayList<TImage> mTImages;
		mTImages = new ArrayList<>();
		for (File image : images) {
			if(image == null) {
				listener.onCompressFailed(null,null);
				return;
			}
			mTImages.add(TImage.of(image.getAbsolutePath(),
					image.getParentFile().getName().equals(Constant.DIR_ICON_CACHE) ? TImage.FromType.CAMERA : TImage.FromType.OTHER));
		}
		CompressImageImpl.of(activity, config, mTImages,listener).compress();
	}*/

	public static String getBitmapType(File image){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(image.getAbsolutePath(), options);
        return options.outMimeType;
	}

	/**
	 * 获取图片宽高
	 * @param path 图片路径
	 */
	public static int[] getImageWidthHeight(String path){
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
		return new int[]{options.outWidth,options.outHeight};
	}

	public static int[] getViewLocation(View view){
		if(view != null){
			int[] location = new int[2];
			view.getLocationInWindow(location);
			Log.log("y: --->  "+location[1]);
			return location;
		}
		return null;
	}

    public static void setMarginTopDp(View itemView, int top) {
		setMargin(itemView,-1,DensityUtil.dp2px(top),-1,-1);
    }

    public static void setMargin(View view, int left, int top, int right, int bottom){
		if(view != null){
			ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
			if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams){
				ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
				if(left != -1)
					params.leftMargin = left;
				if(top != -1)
					params.topMargin = top;
				if(right != -1)
					params.rightMargin = right;
				if(bottom != -1)
					params.bottomMargin = bottom;
			}
		}
	}

	public static int getDpForRes(int resId){
		return (int) BaseApplication.getContext().getResources().getDimension(resId);
	}

	/**
	 * 截取 View 为Bitmap图片
     * 注意: 必须是特定 View
	 */
    @SuppressWarnings("deprecation")
	public static Bitmap shotScreen(View dView, float bitmap_scale){
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bitmap = dView.getDrawingCache();
//		Bitmap bitmap = Bitmap.createBitmap(drawBitmap);
		Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(bitmap.getWidth() * bitmap_scale),
				Math.round(bitmap.getHeight() * bitmap_scale), false);
		bitmap.recycle();
		dView.setDrawingCacheEnabled(false);

		return inputBitmap;
	}

	/**
	 * 截取整个Activity图像
	 * (包括底部虚拟按键)
	 */
	@SuppressWarnings("deprecation")
    public static Bitmap shotScreen(Activity act){
		View dView = act.getWindow().getDecorView();
		dView.setDrawingCacheEnabled(true);
		dView.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        dView.setDrawingCacheEnabled(false);
		return bitmap;
//		return shotScreen(act.getWindow().getDecorView().getRootView());
	}
}
