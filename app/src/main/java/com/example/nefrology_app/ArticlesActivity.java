package com.example.nefrology_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nefrology_app.entity.Article;
import com.example.nefrology_app.helpers.ArticlesHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ArticlesActivity extends AppCompatActivity {

    private ArticlesHelper articlesHelper = new ArticlesHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(getResources().getString(R.string.connection_url) + "/articles")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("TEST", e.getMessage());
                ArticlesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        makeToast("Нет возможности отобразить статьи.");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String result = response.body().string();

                final ArrayList<Article> articles = articlesHelper.getArticles(result);

                if (articles == null) {
                    ArticlesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            makeToast("Нет возможности отобразить статьи");
                        }
                    });
                } else {
                    ArticlesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            printArticles(articles);
                        }
                    });
                }

            }
        });
    }

    private void printArticles(ArrayList<Article> articles) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.articles);

        final ArrayList<TextView> textViews = new ArrayList<>();
        for (Article article: articles) {
            final Button button = new Button(this);
            button.setGravity(Gravity.CENTER);
            button.setText(article.getTitle());
            button.setBackgroundColor(getResources().getColor(R.color.colorBlue));
            button.setBottom(2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (TextView tv : textViews) {
                        tv.setVisibility(View.GONE);
                    }
                    LinearLayout p = (LinearLayout) view.getParent();
                    p.getChildAt(1).setVisibility(View.VISIBLE);
                }
            });

            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(article.getText());
            textViews.add(textView);
            textView.setVisibility(View.GONE);

            final LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(button);
            layout.addView(textView);

            linearLayout.addView(layout);
            LinearLayout p = (LinearLayout) linearLayout.getChildAt(0);
            p.getChildAt(1).setVisibility(View.VISIBLE);
        }
    }

    private void makeToast(String message) {
        Toast.makeText(ArticlesActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}