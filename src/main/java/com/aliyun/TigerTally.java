package com.aliyun;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Module;
import com.github.unidbg.arm.backend.DynarmicFactory;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.api.ApplicationInfo;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import com.github.unidbg.memory.Memory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class TigerTally extends AbstractJni {
    private final AndroidEmulator emulator;
    private final VM vm;
    private final Module module;
    private final DvmClass TigerTallyAPI;

    public void destroy() throws IOException {
        emulator.close();
    }


    public TigerTally(){
        String soPath = "src/main/java/com/aliyun/libtiger_tally.so";
        String appPath = "src/main/java/com/aliyun/ma.apk";
        emulator = AndroidEmulatorBuilder.for64Bit().setProcessName("com.xiaochuankeji.tieba").build(); // 创建模拟器实例
        final Memory memory = emulator.getMemory(); // 模拟器的内存操作接口
        memory.setLibraryResolver(new AndroidResolver(23)); // 设置系统类库解析
        vm = emulator.createDalvikVM(new File(appPath)); // 创建Android虚拟机
        DalvikModule dm = vm.loadLibrary(new File(soPath), true); // 加载so到虚拟内存
        module = dm.getModule(); //获取本SO模块的句柄

        vm.setJni(this);
        vm.setVerbose(false);
        dm.callJNI_OnLoad(emulator);
        TigerTallyAPI = vm.resolveClass("com/aliyun/TigerTally/TigerTallyAPI");


    }
    public static String getPhoneNum() {

//给予真实的初始号段，号段是在百度上面查找的真实号段

        String[] start = {"133", "149", "153", "173", "177",

                "180", "181", "189", "199", "130", "131", "132",

                "145", "155", "156", "166", "171", "175", "176", "185", "186", "166", "134", "135",

                "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172",

                "178", "182", "183", "184", "187", "188", "198", "170", "171"};

//随机出真实号段 使用数组的length属性，获得数组长度，

//通过Math.random()*数组长度获得数组下标，从而随机出前三位的号段

        String phoneFirstNum = start[(int) (Math.random() * start.length)];

//随机出剩下的8位数

        String phoneLastNum = "";

//定义尾号，尾号是8位

        final int LENPHONE = 8;

//循环剩下的位数

        for (int i = 0; i < LENPHONE; i++) {

//每次循环都从0~9挑选一个随机数

            phoneLastNum += (int) (Math.random() * 10);

        }
        String phoneNum = phoneFirstNum + phoneLastNum;

        return phoneNum;
    }

    public static void main(String[] args) {

        String phone = getPhoneNum();
        TigerTally tiger = new TigerTally();


    }
    void callFunc1() {
        DvmObject<?> dvmObject = TigerTallyAPI.callStaticJniMethodObject(emulator, "_genericNt1(I)I", 2);

    }

    void callFunc2() {
        DvmObject<?> dvmObject = TigerTallyAPI.callStaticJniMethodObject(emulator, "_genericNt2(ILjava/lang/String;)I", 2, "EWA40T3eMNVkLmj8Ur9CuQExbcOti8c3yd-I8xDkLhvphNMuRujkY7V6lKbvAtE2qXa4kTWSnXmo0HXfuUXRgyFNXYwhwvvf7yUYQ-DjWjAa34fjA9yJCam4Llddmcu3D8BQKw4gR-nkYzzOx0uGj9OkfgUHoFxF00akZNyeMrs=");

    }
    public Map<String, String> callFunc3(String arg,Boolean hasInit) {
        if (hasInit){
            this.callFunc2();
            this.callFunc1();
        }

        DvmObject<?> dvmObject = TigerTallyAPI.callStaticJniMethodObject(emulator, "_genericNt3(I[B)Ljava/lang/String;", 1, new ByteArray(vm, arg.getBytes(StandardCharsets.UTF_8)));
        String wtoken = dvmObject.getValue().toString();

        if(wtoken.equals("you must init first")){

            dvmObject = TigerTallyAPI.callStaticJniMethodObject(emulator, "_genericNt3(I[B)Ljava/lang/String;", 1, new ByteArray(vm, arg.getBytes(StandardCharsets.UTF_8)));
            wtoken = dvmObject.getValue().toString();
            System.out.println(wtoken);
        }
        Map<String, String> result = new HashMap<>();
        result.put("wtoken",wtoken );
        return result;

    }


    @Override
    public DvmObject<?> callObjectMethodV(BaseVM vm, DvmObject<?> dvmObject, String signature, VaList vaList) {
        switch (signature){
            case "android/content/pm/PackageManager->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;":
                String appName = vaList.getObjectArg(0).getValue().toString();
                System.out.println("check app:"+appName);
                switch (appName){
                    case "com.box.art":{
                        return new ApplicationInfo(vm);
                    }
                }
            case "android/content/pm/PackageManager->getApplicationLabel(Landroid/content/pm/ApplicationInfo;)Ljava/lang/CharSequence;":
                return new StringObject(vm,"java/lang/CharSequence");
            case "android/app/Application->getFilesDir()Ljava/io/File;":
                return vm.resolveClass("java/io/File");
            case "java/lang/Class->getAbsolutePath()Ljava/lang/String;":
                return new StringObject(vm, "/sdcard");
            case "android/app/Application->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;":
                return vm.resolveClass("android/content/SharedPreferences").newObject(vaList.getObjectArg(0));
            case "android/content/SharedPreferences->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;":
                String str = vaList.getObjectArg(0).getValue().toString();
                String str2 = vaList.getObjectArg(1).getValue().toString();
                if (str.equals("tt_ak")){
                    System.out.println("tt_ak");
                    return new StringObject(vm,"^1652122636405^86400");
                }
                System.out.println(str + "|" + str2);


        }
        return super.callObjectMethodV(vm, dvmObject, signature, vaList);
    }

    public static String randomDeviceModule(){
        String[] arrayList = {"cheeseburger/dumpling", "enchilada", "enchilada", "fajita", "fajita", "guacamoleb", "guacamoleb", "guacamole", "guacamole", "hotdogb", "hotdogb", "hotdog", "hotdog", "instantnoodle & instantnoodlep", "kebab", "kebab", "lemonade", "lemonade", "lemonadep", "lemonadep", "bacon", "oneplus3", "oneplus2", "onyx", "molly", "sprout", "gm9pro_sprout", "GM6_s_sprout", "seed", "seedmtk", "shamrock", "sailfish", "walleye", "taimen", "blueline", "sargo", "bonito", "crosshatch", "flame", "coral", "redfin", "dragon", "marlin", "f1f", "find5", "find7", "N1", "n3", "R11", "R11s", "r5", "r7f", "r7plusf", "r7sf", "R819", "RMX1801", "RMX1851"};
        Random random = new Random();
        String module = arrayList[random.nextInt(arrayList.length)];
        return module;

    }

    public static String randomVersion(){
        Random random = new Random();
        String a = String.valueOf(random.nextInt(5) + 7);
        return a;
    }
    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    @Override
    public DvmObject<?> getStaticObjectField(BaseVM vm, DvmClass dvmClass, String signature) {
        switch (signature){
            case "android/os/Build->BRAND:Ljava/lang/String;":
                return new StringObject(vm,"Android");
            case "android/os/Build->MODEL:Ljava/lang/String;":

                return new StringObject(vm,randomDeviceModule());
            case "android/os/Build$VERSION->RELEASE:Ljava/lang/String;":
                return new StringObject(vm,randomVersion());
            case "android/os/Build->DEVICE:Ljava/lang/String;":
                return new StringObject(vm, getRandomString(7));

        }
        return super.getStaticObjectField(vm, dvmClass, signature);
    }

    @Override
    public DvmObject<?> callStaticObjectMethodV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        if ("com/aliyun/TigerTally/A->ct()Landroid/content/Context;".equals(signature)){

            return vm.resolveClass("android/app/Application", vm.resolveClass("android/content/ContextWrapper", vm.resolveClass("android/content/Context"))).newObject(signature);
        }
        switch (signature){
            case "com/aliyun/TigerTally/A->pb(Ljava/lang/String;[B)Ljava/lang/String;":
                return new StringObject(vm,"");
        }
        return super.callStaticObjectMethodV(vm, dvmClass, signature, vaList);
    }
}
