package com.eshel.ourvisa.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import com.bumptech.glide.util.LogTime;
import com.eshel.ourvisa.BaseApplication;
import com.eshel.ourvisa.Constant;
import com.eshel.ourvisa.VisaApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Log {

    //进行耗时统计使用
    public static final String timeTag = "耗时统计(Time-consuming statistics)";
    private volatile static long time;
    private volatile static SparseArray<Long> times;
    private static SparseArray<Long> getTimes(){
        if(times == null){
            synchronized (Log.class){
                if(times == null){
                    times = new SparseArray<>();
                }
            }
        }
        return times;
    }

    public static String[] filters;// 过滤掉包含某字段的log
    private static BufferedWriter sWriter;

    public static void initLog(String[] filters) {
        Log.filters = filters;
    }

    /**
     * 进行耗时统计
     * 单位毫秒
     * preparePrintTime();
     * your code...
     * printTime();
     *
     * LogCat: time:xxx
     */
    public static long preparePrintTime(){
        time = LogTime.getLogTime();
        return time;
    }
    public static double printTime(){
        return printTime(null);
    }
    public static double printTime(String TAG){
        double longTIme = LogTime.getElapsedMillis(time);
        if(TAG == null)
            TAG = timeTag+" time: ";
        else
            TAG = timeTag+": " + TAG+" time: ";
        Log.d(TAG, Double.toString(longTIme));
        return longTIme;
    }

    /**
     * 进行耗时统计
     * @param id 用来标记不同的起始时间
     * preparePrintTime(1);
     * your code...
     * printTime(1, "code1 time");
     * your code...
     * preparePrintTime(2);
     * your code...
     * printTime(2, "code2 time");
     * printTime(1,"all time");
     */
    public static long preparePrintTime(int id){
        long logTime = LogTime.getLogTime();
        getTimes().put(id, logTime);
        return logTime;
    }
    public static double printTime(int id){
        return printTime(id,null);
    }
    public static double printTime(int id, String TAG){
        double longTIme = LogTime.getElapsedMillis(getTimes().get(id));
        if(TAG == null)
            TAG = timeTag+" time: ";
        else
            TAG = timeTag+": " + TAG + " id: "+id+" time: ";
        Log.d(TAG, Double.toString(longTIme));
        return longTIme;
    }

    /**
     * 打印日志至文件
     * @return
     */
    public static boolean nativeLogIsOpen() {
        return true;
    }

    /**
     * 仅打日志至logcat, 不写入文件
     * @param msg
     */
    public static void onlyLog(String msg){
        onlyLog(null,msg);
    }

    public static void onlyLog(String TAG, String msg){
        if(!VisaApp.isDebug())
            return;
        if(TAG != null)
            msg = TAG + ": " + msg;
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - Constant.TAG.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            android.util.Log.i(Constant.TAG, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        android.util.Log.i(Constant.TAG, msg);
    }

    /**
     * debug 模式下才打印
     * @param TAG
     * @param msg
     */
    public static void onlyLogDebug(String TAG, String msg){
        if (VisaApp.isDebug() && !filter(msg)) {
            onlyLog(TAG,msg);
        }
    }
    public static void onlyLogDebug(String msg){
        if (VisaApp.isDebug() && !filter(msg)) {
            onlyLog(msg);
        }
    }

    /**
     * 打印 方法堆栈 类堆栈, 可在方法中调用 静态代码块中调用 代码块中调用, 打印被调用时堆栈信息
     */
    public static void logMethodStack() {
        if (VisaApp.isDebug())
            new Exception(Constant.TAG).printStackTrace(System.out);
    }

    public static void log(Object msg) {
        String msgS = objToString(msg);
        if (VisaApp.isDebug() && !filter(msg)) {
            android.util.Log.d(Constant.TAG, msgS);
        }
        saveLogToNative("D", Constant.TAG, msgS);
    }

    public static void logD(Object msg) {
        log(msg);
    }

    public static void logI(Object msg) {
        String msgS = objToString(msg);
        if (VisaApp.isDebug() && !filter(msg)) {
            android.util.Log.i(Constant.TAG, msgS);
        }
        saveLogToNative("I", Constant.TAG, msgS);
    }

    public static void logW(Object msg) {
        String msgS = objToString(msg);
        if (VisaApp.isDebug() && !filter(msg)) {
            android.util.Log.w(Constant.TAG, msgS);
        }
        saveLogToNative("W", Constant.TAG, msgS);
    }

    public static void logE(Object msg) {
        String msgS = objToString(msg);
        if (VisaApp.isDebug() && !filter(msg)) {
            android.util.Log.e(Constant.TAG, msgS);
        }
        saveLogToNative("E", Constant.TAG, msgS);
    }

    public static void d(String tag, String msg) {
        logD(tag + " : " + msg);
    }

    public static void i(String tag, String msg) {
        logI(tag + " : " + msg);
    }

    /**
     * 打印日志 并拼接堆栈信息
     */
    public static void logAndPrintMethodStack(String tag, String msg){
        logI(tag + " : " + msg);
        logMethodStack();
    }

    public static void w(String tag, String msg) {
        logW(tag + " : " + msg);
    }

    public static void e(String tag, String msg) {
        logE(tag + " : " + msg);
    }

    public static void logE(String msg, Throwable throwable) {
        if (VisaApp.isDebug() && !filter(msg)) {
            android.util.Log.e(Constant.TAG, msg, throwable);
        }
        saveLogToNative("E", Constant.TAG, msg);
        printError(throwable);
    }

    public static String objToString(Object msg) {
        try {
            String msg2 = "";
            if (msg == null)
                msg2 = "null";
            else if (msg instanceof String)
                msg2 = (String) msg;
            else if (msg instanceof Integer)
                msg2 = "" + ((int) msg);
            else if (msg instanceof Float)
                msg2 = "" + ((float) msg);
            else {
                msg2 = msg.toString();
            }
            return msg2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printStackTrace(Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
            saveException("W", "System.err", throwable);
        }
    }

    public static void printError(Throwable throwable) {
        if (throwable != null) {
            android.util.Log.e(Constant.TAG, "AndroidRuntime: "+throwable.getMessage(), throwable);
            saveException("E", "AndroidRuntime", throwable);
        }
    }

    static SimpleDateFormat format = new SimpleDateFormat("MM-dd hh:mm:ss.SSS", Locale.getDefault());

    public static void saveException(String level, String TAG, Throwable throwable) {
        if (!nativeLogIsOpen())
            return;

        if (throwable != null) {
            /*StringBuilder sb = new StringBuilder();
            sb.append("\r\n");
            sb.append("\t");
            sb.append(TAG);
            sb.append(": ");
            sb.append(throwable.getClass().getName());
            sb.append(": ");
            sb.append(throwable.getMessage());
            sb.append("\r\n");

            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                sb.append("\t\t");
                sb.append("at ");
                sb.append(stackTraceElement.toString());
                sb.append("\r\n");
            }*/
            saveLogToNative(level, TAG,/*sb.toString()*/android.util.Log.getStackTraceString(throwable));
        }
    }

    public static void saveLogToNative(String level, String TAG, String msg) {
        try {
            if (nativeLogIsOpen() && !filter(msg)) {
                String content = String.format(Locale.getDefault(), "%s %d-%d/%s %s/%s: %s",
                        format.format(new Date()),
                        ProcessUtil.getPid(),
                        ProcessUtil.getTid(),
                        BaseApplication.getContext() != null ? BaseApplication.getContext().getPackageName() : "com.yiju.bluelake",
                        level == null ? "NULL" : level,
                        TAG == null ? "null" : TAG,
                        msg == null ? "null" : msg
                );
                if (sWriter == null) {
                    File appDir = FileUtils.getAppDir();
                    boolean needWritePhoneInfo = false;
                    if (appDir != null && appDir.exists()) {
                        File logFile = new File(appDir, "instance.log");
                        if (!logFile.exists() || !logFile.isFile()) {
                            try {
                                logFile.createNewFile();
                                needWritePhoneInfo = true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            sWriter = new BufferedWriter(new FileWriter(logFile, true));
                            if(needWritePhoneInfo)
                                dumpError(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    sWriter.write(content);
                    sWriter.newLine();
                    sWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * true需要过滤, 不打印log
     * @param msg
     * @return
     */
    private static boolean filter(Object msg) {
        if (msg == null)
            msg = "null";
        if (filters != null) {
            for (String filter : filters) {
                if (Constant.TAG.contains(filter)) {
                    return true;
                }
                if (msg.toString().contains(filter)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void deInit() {
        if (sWriter != null) {
            Closeable write = sWriter;
            sWriter = null;
            StreamUtils.closeStream(write);
        }
    }

    /**
     * 设置全局异常捕获并保存至文件
     */
    public static void setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.logE(t.toString());
                printError(e);
//                dumpError(false);
                if(t == Looper.getMainLooper().getThread())
                    System.exit(0);
                else {
                    Log.logE("ERROR!!! child Thread Carsh");
                }
            }
        });
    }

    /**
     * @param appendInstallAppList 是否打印已安装app信息
     */
    public static void dumpError(boolean appendInstallAppList) {
        //Android special log text
        String logText = "Phone Info:.................................\n";
        try {
            logText += "Product: " + android.os.Build.PRODUCT + "\n";
            logText += "CPU_ABI: " + android.os.Build.CPU_ABI + "\n";
            logText += "TAGS: " + android.os.Build.TAGS + "\n";
            logText += "VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE + "\n";
            logText += "MODEL: " + android.os.Build.MODEL + "\n";
            logText += "VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE + "\n";
            logText += "DEVICE: " + android.os.Build.DEVICE + "\n";
            logText += "DISPLAY: " + android.os.Build.DISPLAY + "\n";
            logText += "BRAND: " + android.os.Build.BRAND + "\n";
            logText += "BOARD: " + android.os.Build.BOARD + "\n";
            logText += "FINGERPRINT: " + android.os.Build.FINGERPRINT + "\n";
            logText += "ID: " + android.os.Build.ID + "\n";
            logText += "MANUFACTURER: " + android.os.Build.MANUFACTURER + "\n";
            logText += "USER: " + android.os.Build.USER + "\n";
        } catch (Exception e) {
            logText += e.toString() + "\n";
        } catch (Error e) {
            logText += e.toString() + "\n";
        }

        //Installed App
        if (appendInstallAppList)
            logText += dumpInstalledApp() + "\n";

        //Network info
        try {
            TelephonyManager tm = (TelephonyManager) BaseApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);

            if (tm != null) {
                if (ActivityCompat.checkSelfPermission(BaseApplication.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                   ;//do nothing
                }
                logText += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";//忽略权限错误
                logText += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
            }
            int mcc = BaseApplication.getContext().getResources().getConfiguration().mcc;
            int mnc = BaseApplication.getContext().getResources().getConfiguration().mnc;
            logText +="IMSI MCC (Mobile Country Code): " + String.valueOf(mcc) + "\n";
            logText +="IMSI MNC (Mobile Network Code): " + String.valueOf(mnc) + "\n";
        }
        catch ( Exception e ){
            logText += e.toString() + "\n";
        }
        catch ( Error e ){
            logText += e.toString() + "\n";
        }
        //CPUInfo
        {
            CMDExecute cmdExc = new CMDExecute( );
            try {
                String[ ] args = {"/system/bin/cat", "/proc/cpuinfo"};
                logText   += "CPUInfo:..............................\n" +
                        cmdExc.run(args, "/system/bin/");
            }
            catch (IOException ex) {
                logText += ex.toString( ) + "\n";
            }
            catch ( Error e ){
                logText += e.toString() + "\n";
            }

            logText +="\n";
        }
        //LogStat
/*        {
            try{
                CMDExecute cmdExc = new CMDExecute( );
                String[ ] args = {"logcat","-d","-t","5000","-v","threadtime"};
                logText       += "Logcat:.....................................\n" +
                        cmdExc.run(args, "/system/bin/");
            }
            catch ( Exception e ){
                logText += e.toString() + "\n";
            }
            catch ( Error e ){
                logText += e.toString() + "\n";
            }
        }*/

        //MemInfo
        {
//            CMDExecute cmdExc = new CMDExecute( );
            try {
                final android.app.ActivityManager activityManager =( android.app.ActivityManager ) BaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
                android.app.ActivityManager.MemoryInfo outInfo = new android.app.ActivityManager.MemoryInfo();
                if(activityManager != null) {
                    activityManager.getMemoryInfo(outInfo);
                    logText += "Total Available Memory :" + getTotalMemory() + "k\n";
                    logText += "In low memory situation:" + String.valueOf(outInfo.lowMemory);
                    logText += "\n";
                }
            }
            catch ( Exception ex ) {
                logText += ex.toString( ) + "\n";
            }
            catch ( Error e ){
                logText += e.toString( ) + "\n";
            }
        }

        //Graphic
        try
        {
            logText          += "Display:......................................\n";
            DisplayMetrics dm = BaseApplication.getContext().getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;
            float density = dm.density;
            float xdpi = dm.xdpi;
            float ydpi = dm.ydpi;
            logText += "The absolute width: " + String.valueOf(screenWidth) + "pixels\n";
            logText += "The absolute heightin: " + String.valueOf(screenHeight) + "pixels\n";
            logText += "The logical density of the display. : " + String.valueOf(density) + "\n";
            logText += "X dimension : " + String.valueOf(xdpi) +"pixels per inch\n";
            logText += "Y dimension : " + String.valueOf(ydpi) +"pixels per inch\n";
        }
        catch ( Exception e ){
            logText += e.toString( ) + "\n";
        }
        catch ( Error e ){
            logText += e.toString() + "\n";
        }
        //Packge info
        logText += "PakageVersion:" + getVersion(BaseApplication.getContext()) + "\n";
        Log.e("SystemInfo",logText);
    }

    /**
     * 获取手机安装app包名信息
     */
    public static String dumpInstalledApp(){
        String res = "Install apps:\n";
        try{
            List<String> ls = listInstalledApp(BaseApplication.getContext());
            if ( ls != null ){
                Iterator<String> it = ls.iterator();
                while ( it.hasNext())
                {
                    String lCurModule = it.next();
                    res = res + lCurModule + "\n";
                }
            }
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        catch ( Error e ){
            e.printStackTrace();
        }
        return res;
    }

    //Dump tool
    static public class CMDExecute {
        public synchronized String run(String[] cmd, String workdirectory) throws IOException {
            String result = "";
            try {
                ProcessBuilder builder = new ProcessBuilder(cmd);
                InputStream in = null;
                if (workdirectory != null) {
                    builder.directory(new File(workdirectory));
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                    in = process.getInputStream();
                    byte[] re = new byte[1024];
                    int nLen  = in.read(re,0,1024);
                    while ( nLen != -1){
                        result = result + new String(re,0,nLen);
                        nLen   = in.read(re,0,1024);
                    }
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            catch ( Error e ){
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * 获取手机总内存
     * @return
     */
    public static long getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2="";
        String[] arrayOfString;
        long initial_memory = 0, free_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            for (int i = 0; i < 2; i++) {
                str2 =str2+" "+ localBufferedReader.readLine();// meminfo  //THIS WILL READ meminfo AND GET BOTH TOT MEMORY AND FREE MEMORY eg-: Totalmemory 12345 KB //FREEMEMRY: 1234 KB
            }
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                android.util.Log.i(str2, num + "\t");
            }
            // total Memory
            initial_memory = Integer.valueOf(arrayOfString[2]).intValue();
            free_memory = Integer.valueOf(arrayOfString[5]).intValue();

            localBufferedReader.close();
        } catch (IOException e) {
        }
        catch ( Error e ){
        }
        return (initial_memory-free_memory);
    }

    /**
     * 获取手机版本号(version name)
     * @param c
     * @return
     */
    public static String getVersion(Context c)
    {
        try {
            return c.getPackageManager().getPackageInfo(c.getPackageName(),0).versionName;
        } catch(Exception e) {
            return "?";
        }
        catch ( Error e ){
            return "?";
        }
    }

    /**
     * 获取手机上安装的app列表
     */
    public static List<String> listInstalledApp(Context context){
        List<String> res = new LinkedList<String>( );
        try{
            List<android.content.pm.PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
            for(android.content.pm.PackageInfo pack : packages)
            {
                android.content.pm.ActivityInfo[] activityInfo = context.getPackageManager().getPackageInfo(pack.packageName, android.content.pm.PackageManager.GET_ACTIVITIES).activities;
                if(activityInfo!=null)
                {
                    for(int i=0; i<activityInfo.length; i++)
                    {
                        if ( !res.contains( activityInfo[i].packageName.toLowerCase() ) )
                            res.add(activityInfo[i].packageName.toLowerCase());
                    }
                }
            }
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        catch ( Error e ){
            e.printStackTrace();
        }

        return res;
    }
}
