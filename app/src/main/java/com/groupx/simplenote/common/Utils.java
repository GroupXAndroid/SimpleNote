package com.groupx.simplenote.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Utils {
    public static String ColorIntToString(int colorInt) {
        return "#" + Integer.toHexString(colorInt);
    }

    public static String DateTimeToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault());
        return formatter.format(date);
    }
    /**
     * @return JSONObject App standard.
     */
    public JSONObject getStandard() {
        try {
            File myObj = new File("com/groupx/simplenote/common/standard/standard.json");
            Scanner myReader = new Scanner(myObj);
            String data = "";
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
            JSONObject standard = new JSONObject(data.toString());
            return standard;
        } catch (FileNotFoundException | JSONException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getLanguageList(){
        JSONObject standard = this.getStandard();
        try {
            JSONObject llanguages= standard.getJSONObject("languages");
            Iterator x = llanguages.keys();
            JSONArray jsonArray = new JSONArray();

            while (x.hasNext()){
                String key = (String) x.next();
                jsonArray.put(llanguages.get(key));
            }

            return jsonArray;
        } catch (JSONException e) {

        }
        return null;
    }
    /**
     * Check supported language in the system.
     *
     * @param key
     * @return true/false
     */
    public boolean checkSupportedLanguage(String key) {
        JSONObject standard = this.getStandard();
        try {
            if (standard != null && !standard.getJSONObject("languages").has(key)) {
                return false;
            }
        } catch (JSONException e) {

        }
        return true;
    }

    /**
     * Get standard achievement list in the system.
     *
     * @return
     */
    public HashMap<String, String> getStandardAchievements() {
        HashMap<String, String> result = new HashMap<>();
        JSONObject standard = getStandard();
        try {
            JSONObject achis = standard.getJSONObject("achievements");
            Iterator<String> keys = achis.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    if (achis.has(key)) {
                        result.put(key, achis.get(key).toString());
                    }
                } catch (JSONException f) {
                    continue;
                }
            }
        } catch (JSONException e) {
        }
        return result;
    }

    /**
     * Convert to good achievement array
     *
     * @param achievements
     * @return List<String> Achievement array.
     */
    public List<String> makeGoodAchievement(JSONArray achievements) {
        List<String> result = new ArrayList<>();
        HashMap<String, String> standardAchievements = getStandardAchievements();
        for (int i = 0; i < achievements.length(); i++) {
            try {
                if (standardAchievements.get(achievements.get(i).toString()) != null) {
                    result.add(achievements.get(i).toString());
                }
            } catch (JSONException e) {
                continue;
            }
        }
        return result;
    }
}
