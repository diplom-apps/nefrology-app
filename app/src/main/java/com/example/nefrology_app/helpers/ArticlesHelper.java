package com.example.nefrology_app.helpers;

import android.util.Log;

import com.example.nefrology_app.ArticlesActivity;
import com.example.nefrology_app.entity.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticlesHelper {
    public ArrayList<Article> getArticles(String jsonArticles){

        ArrayList<Article> articles = new ArrayList<>();

        try {
            JSONArray jsonObject = new JSONArray(jsonArticles);

            if (jsonObject.length() == 0) return null;

            for (int i = 0; i < jsonObject.length(); i++){
                JSONObject object = new JSONObject(jsonObject.get(i).toString());
                Article article = new Article();
                article.setId(Integer.parseInt(object.getString("id")));
                article.setTitle(object.getString("title"));
                article.setText(object.getString("text"));
                articles.add(article);
            }

        } catch (JSONException e) {
            return null;
        }

        return articles;
    }

    // TODO:: остальные методыдля фильтации статей, если будет нужно
}
