package sample;

import java.util.prefs.Preferences;

public class OptionsData {
    public static boolean mdateTime = false, mshowString= false, mitalics= false, mbold= false;
    public static String mcustomString = "Temp String";
    public  static int mfontSize = 12;

    public static void storePreferences(Class c){
        Preferences.userNodeForPackage(c).putBoolean("mdateTime", mdateTime);
        Preferences.userNodeForPackage(c).putBoolean("mshowString", mshowString);
        Preferences.userNodeForPackage(c).putBoolean("mitalics", mitalics);
        Preferences.userNodeForPackage(c).putBoolean("mbold", mbold);
        Preferences.userNodeForPackage(c).putByteArray("mcustomString", mcustomString.getBytes());
        Preferences.userNodeForPackage(c).putInt("mfontSize", mfontSize);
    }
    public static void getPreferences(Class c){
        mdateTime = Preferences.userNodeForPackage(c).getBoolean("mdateTime", false);
        mshowString = Preferences.userNodeForPackage(c).getBoolean("mshowString", false);
        mitalics = Preferences.userNodeForPackage(c).getBoolean("mitalics", false);
        mbold = Preferences.userNodeForPackage(c).getBoolean("mbold", false);
        mcustomString = new String(Preferences.userNodeForPackage(c).getByteArray("mcustomString", "Temp String".getBytes()));
        mfontSize = Preferences.userNodeForPackage(c).getInt("mfontSize", 12);
    }
}
