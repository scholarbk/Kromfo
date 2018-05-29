package scholar.kromfo.Helpers;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;


    public static SessionManager helper;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Kromfo";

    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_OTP = "otp";


    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_IS_IN_CHARGE_SENSE = "isInChargeSense";
    private static final String KEY_IS_NOTIFY_ME_CHARGING = "isInNotifyMeSense";
    private static final String KEY_IS_KIOSK_MODE_ACTIVE = "isKioskModeActive";
    private static final String KEY_IS_BOOT_RECOVERY = "isBootModeActive";
    private static final String KEY_IS_FLAT_SURFACE = "isFlat";
    private static final String KEY_IS_INPOCKET = "inPocket";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public static SessionManager newInstance(Context context) {
        if (helper == null)
        {
            helper = new SessionManager(context);
        }
        return helper;
    }
    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();


    }
    public void setKioskSense(boolean isInKioskMode) {

        editor.putBoolean(KEY_IS_KIOSK_MODE_ACTIVE, isInKioskMode);

        // commit changes
        editor.commit();


    }

    public void setBootSense(boolean isInBootMode) {

        editor.putBoolean(KEY_IS_BOOT_RECOVERY, isInBootMode);

        // commit changes
        editor.commit();


    }
    public void setFlatSense(boolean isFlatMode) {

        editor.putBoolean(KEY_IS_FLAT_SURFACE, isFlatMode);

        // commit changes
        editor.commit();


    }
    public void setChargeSense(boolean isInChargeSense) {

        editor.putBoolean(KEY_IS_IN_CHARGE_SENSE, isInChargeSense);

        // commit changes
        editor.commit();


    }
    public void setPocketSense(boolean isInPocketSense) {

        editor.putBoolean(KEY_IS_INPOCKET, isInPocketSense);

        // commit changes
        editor.commit();


    }
    public void setNotifyMeCharging(boolean isNotifyMeCharge) {

        editor.putBoolean(KEY_IS_NOTIFY_ME_CHARGING, isNotifyMeCharge);

        // commit changes
        editor.commit();


    }
    public boolean isLoggedIn(){ return pref.getBoolean(KEY_IS_LOGGEDIN, false); }
    public boolean isChargnSense(){
        return pref.getBoolean(KEY_IS_IN_CHARGE_SENSE, false);
    }
    public boolean isNotifyMeSense(){
        return pref.getBoolean(KEY_IS_NOTIFY_ME_CHARGING, true);
    }
    public boolean isKioskSense(){
        return pref.getBoolean(KEY_IS_KIOSK_MODE_ACTIVE, false);
    }
    public boolean isBootSense(){
        return pref.getBoolean(KEY_IS_BOOT_RECOVERY, false);
    }
    public boolean isFlatSense(){
        return pref.getBoolean(KEY_IS_FLAT_SURFACE, false);
    }
    public boolean isInPocketSense(){
        return pref.getBoolean(KEY_IS_INPOCKET, false);
    }
    public void createOTP(String otp) {
        editor.putString(KEY_OTP, otp);
        editor.commit();
    }
    public HashMap<String, String> getOTP() {
        HashMap<String, String> profileNo = new HashMap<>();
        profileNo.put("OTP", pref.getString(KEY_OTP, null));
        return profileNo;
    }
    public void createMobileNumber(String mobilenumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobilenumber);
        editor.commit();
    }
    public HashMap<String, String> getMobileNumber() {
        HashMap<String, String> profileNo = new HashMap<>();
        profileNo.put("phone", pref.getString(KEY_MOBILE_NUMBER, null));
        return profileNo;
    }
}
