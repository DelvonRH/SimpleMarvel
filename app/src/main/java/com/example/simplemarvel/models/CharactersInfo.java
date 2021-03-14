package com.example.simplemarvel.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CharactersInfo
{
    public static final String TAG = "CharactersInfo";
    private String name;
    private String description;
    private int id;
    private int comicCount;
    private String image;
    private String imageExtension;

    public CharactersInfo(JSONObject jsonObject) throws JSONException
    {
        name = jsonObject.getString("name");
        description = jsonObject.getString("description");
        id = jsonObject.getInt("id");
        JSONObject thumbnail = jsonObject.getJSONObject("thumbnail");
        image = thumbnail.getString("path");
        imageExtension = thumbnail.getString("extension");
        JSONObject comics = jsonObject.getJSONObject("comics");
        comicCount = comics.getInt("available");
        Log.d(TAG, toString());
    }

    public static List<CharactersInfo> charactersInfoList(JSONArray jsonArray) throws JSONException
    {
        List<CharactersInfo> characters = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++)
        {
            characters.add(new CharactersInfo(jsonArray.getJSONObject(i)));
        }
        return characters;
    }

    @Override
    public String toString()
    {
        return "CharactersInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", comicCount=" + comicCount +
                ", image='" + image + '\'' +
                '}';
    }

    public static String listToString(List<CharactersInfo> characters)
    {
        String result = "\n";
        for (CharactersInfo  charactersInfo : characters)
            result+= charactersInfo.toString() + "\n";
        return result;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getId()
    {
        return id;
    }

    public int getComicCount()
    {
        return comicCount;
    }

    public String getImage()
    {
        return image + "/standard_medium." + imageExtension;
    }
}
