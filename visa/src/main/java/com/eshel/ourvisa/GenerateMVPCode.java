package com.eshel.ourvisa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateMVPCode {

    private static final int TYPE_ACTIVITY = 947;
    private static final String JAVA_PATH = "F:\\OurVisa\\visa\\src\\main\\java";
    private static final String PACKAGE = "com.eshel.ourvisa";
    private static final String PACKAGE_PATH = JAVA_PATH + "\\" + PACKAGE.replace(".", "\\") + "\\";
    private static final String MVP_PATH = PACKAGE_PATH + "mvp\\";
    private static final String _java = ".java";

    public static void main(String args[]){
        String generateName = "home";//register
        String path = "ui";
        int type = TYPE_ACTIVITY;
        try {
            if(generateName.equals(""))
                return;
            generateCode(generateName, path, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateCode(String generateName, String packageS, int type) throws Exception {
        String generatePackagePath = PACKAGE_PATH + packageS.replace(".", "\\") + "\\" + generateName;
        File packageF = new File(generatePackagePath);
        checkDir(packageF);
        packageF.mkdir();

        createViewImpl(packageF, packageS, type);
        createIView(generateName);
        createIPresenter(generateName);
        createIModle(generateName);
        createIModleCallback(generateName);
        createModleImpl(packageF, packageS);
        createPresenterImpl(packageF, packageS);
    }

    private static void createPresenterImpl(File packageF, String packageS) throws IOException {
        String packageName = packageF.getName();
        String presenterName = getPresenterName(packageName);
        File presenterF = new File(PACKAGE_PATH + packageS.replace(".","\\") + "\\" + packageName + "\\" + presenterName + _java);
        checkFile(presenterF);

        BufferedWriter bw = new BufferedWriter(new FileWriter(presenterF));
        writeLine(bw, "package " + PACKAGE + "."+ packageS + "." + packageName + ";");
        writeLine(bw, "");
        writeLine(bw, "import com.eshel.ourvisa.mvp.base.Presenter;");
        writeLine(bw, "import com.eshel.ourvisa.mvp.presenters."+getIPersenterName(packageName)+";");
        writeLine(bw, "import com.eshel.ourvisa.mvp.view."+getIViewName(packageName)+";");
        writeLine(bw, "");
        writeLine(bw, "public class " + presenterName + " extends Presenter<" + getIViewName(packageName) + "," + getModleName(packageName) + "> implements "+getIPersenterName(packageName)+" {");
        writeLine(bw, "");
        writeLine(bw, "    @Override");
        writeLine(bw, "    protected void onClose() {");
        writeLine(bw, "");
        writeLine(bw, "    }");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static void createModleImpl(File packageF, String packageS) throws IOException {
        String packageName = packageF.getName();
        String modleName = getModleName(packageName);
        File modleF = new File(PACKAGE_PATH + packageS.replace(".","\\") + "\\" + packageName + "\\" + modleName + _java);
        checkFile(modleF);

        BufferedWriter bw = new BufferedWriter(new FileWriter(modleF));
        writeLine(bw, "package " + PACKAGE + "."+ packageS + "." + packageName + ";");
        writeLine(bw, "");
        writeLine(bw, "import com.eshel.ourvisa.mvp.base.Modle;");
        writeLine(bw, "import com.eshel.ourvisa.mvp.modles.modlecallback."+getIModleCallbackName(packageName)+";");
        writeLine(bw, "");
        writeLine(bw, "public class " + modleName + " extends Modle<" + getIModleCallbackName(packageName) + "> {");
        writeLine(bw, "");
        writeLine(bw, "    public "+modleName+"(" + getIModleCallbackName(packageName) + " callback) {");
        writeLine(bw, "        super(callback);");
        writeLine(bw, "    }");
        writeLine(bw, "");
        writeLine(bw, "    @Override");
        writeLine(bw, "    protected void onClose() {");
        writeLine(bw, "");
        writeLine(bw, "    }");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static void createIModleCallback(String generateName) throws IOException {
        String IModleCallbackName = getIModleCallbackName(generateName);
        File IPresenterF = new File(MVP_PATH + "modles\\modlecallback\\" + IModleCallbackName + _java);
        checkFile(IPresenterF);

        BufferedWriter bw = new BufferedWriter(new FileWriter(IPresenterF));
        writeLine(bw, "package " + PACKAGE + ".mvp.modles.modlecallback;");
        writeLine(bw, "");
        writeLine(bw, "import com.eshel.ourvisa.mvp.base.ModleCallback;");
        writeLine(bw, "");
        writeLine(bw, "public interface " + IModleCallbackName + " extends ModleCallback {");
        writeLine(bw, "    ");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static void createIModle(String generateName) throws IOException {
        String IModleName = getIModleName(generateName);
        File IPresenterF = new File(MVP_PATH + "modles\\" + IModleName + _java);
        checkFile(IPresenterF);

        BufferedWriter bw = new BufferedWriter(new FileWriter(IPresenterF));
        writeLine(bw, "package " + PACKAGE + ".mvp.modles;");
        writeLine(bw, "");
        writeLine(bw, "public interface " + IModleName + " {");
        writeLine(bw, "    ");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static void createIPresenter(String generateName) throws IOException {
        String IPresenterName = getIPersenterName(generateName);
        File IPresenterF = new File(MVP_PATH + "presenters\\" + IPresenterName + _java);
        checkFile(IPresenterF);

        BufferedWriter bw = new BufferedWriter(new FileWriter(IPresenterF));
        writeLine(bw, "package " + PACKAGE + ".mvp.presenters;");
        writeLine(bw, "");
        writeLine(bw, "public interface " + IPresenterName + " {");
        writeLine(bw, "    ");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static void createIView(String packageName) throws IOException {
        String IViewName = getIViewName(packageName);
        File IViewF = new File(MVP_PATH + "view\\" + IViewName + _java);
        checkFile(IViewF);

        BufferedWriter bw = new BufferedWriter(new FileWriter(IViewF));
        writeLine(bw, "package " + PACKAGE + ".mvp.view;");
        writeLine(bw, "");
        writeLine(bw, "import com.eshel.ourvisa.mvp.base.IView;");
        writeLine(bw, "public interface " + IViewName + " extends IView {");
        writeLine(bw, "    ");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static void createViewImpl(File packageF, String packageS, int type) throws Exception{
        switch (type){
            case TYPE_ACTIVITY:
                createActivityViewImpl(packageF, packageS);
                break;
        }
    }

    private static void createActivityViewImpl(File packageF, String packageS) throws IOException {
        String packageName = packageF.getName();

        String activityName = String.valueOf(packageName.charAt(0)).toUpperCase()+packageName.substring(1, packageName.length()) + "Activity";
        File activity = new File(packageF, activityName + _java);
        checkFile(activity);

        BufferedWriter bw = new BufferedWriter(new FileWriter(activity));
        writeLine(bw, "package " + PACKAGE + "." + packageS + "." + packageName + ";");
        writeLine(bw, "import com.eshel.ourvisa.mvp.base.MVPActivity;");
        writeLine(bw, "import android.os.Bundle;");
        writeLine(bw, "import android.support.annotation.Nullable;");
        writeLine(bw, "");
        writeLine(bw, "import com.eshel.ourvisa.titles.BackTitleHolder;");
        writeLine(bw, "import com.eshel.ourvisa.mvp.view."+getIViewName(packageName)+";");
        bw.newLine();
        writeLine(bw, "public class "+activityName+" extends MVPActivity<BackTitleHolder, " + getPresenterName(packageName) + "> implements " + getIViewName(packageName) +" {");
        bw.newLine();
        writeLine(bw, "    @Override");
        writeLine(bw, "    protected void onCreate(@Nullable Bundle savedInstanceState) {");
        writeLine(bw, "        super.onCreate(savedInstanceState);");
        writeLine(bw, "    }");
        writeLine(bw, "}");
        writeLine(bw, "");

        bw.flush();
        bw.close();
    }

    private static String getPresenterName(String packageName){
        return fristToUpper(packageName) + "Presenter";
    }

    private static String getIPersenterName(String packageName){
        return "I" + getPresenterName(packageName);
    }

    private static String getIModleName(String generateName) {
        return "I" + getModleName(generateName);
    }

    private static String getModleName(String packageName){
        return fristToUpper(packageName) + "Modle";
    }

    private static String getIModleCallbackName(String generateName) {
        return "I" + fristToUpper(generateName) + "ModleCallback";
    }

    private static String getIViewName(String packageName){
        return "I" + fristToUpper(packageName) + "View";
    }








    private static String fristToUpper(String text){
        return text.substring(0,1).toUpperCase() + text.substring(1, text.length());
    }

    private static void writeLine(BufferedWriter bw, String line) throws IOException {
        bw.write(line);
        bw.newLine();
    }

    private static void checkDir(File dir){
        if(dirExists(dir)){
            throw new Error("文件夹已存在, 路径: "+dir.getAbsolutePath());
        }
    }

    private static void checkFile(File file){
        if(fileExists(file)){
            throw new Error("文件已存在, 路径: "+file.getAbsolutePath());
        }
    }

    private static boolean dirExists(File dir){
        return dir.exists() && dir.isDirectory();
    }

    private static boolean fileExists(File file){
        return file.exists() && file.isFile();
    }
}
