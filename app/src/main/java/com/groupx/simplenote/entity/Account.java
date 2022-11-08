package com.groupx.simplenote.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.groupx.simplenote.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Account implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accountId")
    private int id;
    private String username;
    private String password;
    private String avatarImagePath;
    private Date dob;
    private String fullName;
    private String settingJson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarImagePath() {
        return avatarImagePath;
    }

    public void setAvatarImagePath(String avatarImagePath) {
        this.avatarImagePath = avatarImagePath;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSettingJson() {
        if (this.settingJson == null || this.settingJson.isEmpty()){
            setSetting(getSetting());
        }
        return this.settingJson;
    }

    public void setSettingJson(String settingJson) {
        this.settingJson = settingJson;
    }

    /**
     * @return JSONObject App settings.
     * @desc Get app settings.
     */
    public JSONObject getSetting() {
        JSONObject settingJson;
        try {
            if (this.settingJson != null){
                settingJson =  new JSONObject(this.settingJson);
                return settingJson;
            }

        } catch (JSONException e) {

        }
        List<String> achievement = new ArrayList<>();
        settingJson = new JSONObject();
        try {
            settingJson.put("notification", "0");
            settingJson.put("lock_key", "");
            settingJson.put("language", "1");
            settingJson.put("background", "0");
            settingJson.put("achievement", achievement);
            settingJson.put("premium", false);
        } catch (JSONException f) {
        }
        return settingJson;
    }

    /**
     * Set new app settings for current user.
     * Good structure:
     * {
     * "notification" : "",
     * "lock_key": "",
     * "language": "",
     * "background": "",
     * "achievement": []
     * }
     * Not good structure will be rejected.
     *
     * @param settingJson new setting
     */
    public void setSetting(@NonNull JSONObject settingJson) {
        Utils utils = new Utils();
        JSONObject setting = this.getSetting();

        if (settingJson.has("notification")) {
            try {
                setting.put("notification", settingJson.get("notification").toString());
            } catch (JSONException e) {
            }
        }

        if (settingJson.has("lock_key")) {
            try {
                setting.put("lock_key", settingJson.get("lock_key").toString());
            } catch (JSONException e) {
            }
        }

        try {
            if (settingJson.has("language") && utils.checkSupportedLanguage(settingJson.get("language").toString())) {
                setting.put("language", settingJson.get("language").toString());
            }
        } catch (JSONException e) {
        }


        if (settingJson.has("background")) {
            try {
                setting.put("background", settingJson.get("background").toString());
            } catch (JSONException e) {
            }
        }

        if (settingJson.has("achievement")) {
            try {
                setting.put("achievement", utils.makeGoodAchievement(settingJson.getJSONArray("achievement")));

            } catch (JSONException e) {
            }
        }
        if (settingJson.has("premium")) {
            try {
                setting.put("premium", settingJson.get("premium"));

            } catch (JSONException e) {
            }
        }

        this.settingJson = settingJson.toString();
    }

    /**
     * Set new system language.
     * @param s
     */
    public void setLanguage(int s) {
        JSONObject setting = getSetting();
        try {
            setting.put("language", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setSetting(setting);
    }

    /**
     *
     * @param key sub key of setting
     * @return
     */
    public String getSetting(String key){
        try {
            String result = getSetting().getString(key).toString();
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Setup sub setting for account.
     * @param key
     * @param value
     */
    public void setSetting(String key, String value){
        JSONObject setting = getSetting();
        if (setting.has(key)){
            try {
                setting.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
