package com.fanok.mdpu24;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.fanok.mdpu24.Adapter.CastomAdapter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class DowlandNews extends DowladParent {

    private ArrayList<Article> articleList;

    public DowlandNews(View view, String url) {
        super(view, url);
    }


    @Override
    protected void parce(Document data) {
        Elements articles = data.getElementsByTag("article");
        articleList = new ArrayList<>();
        for (int i = 1; i < articles.size(); i++) {
            Article article1Item = new Article();
            article1Item.setUrlArticle(articles.get(i).getElementsByTag("a").get(0).attr("href"));
            article1Item.setTitle(articles.get(i).getElementsByTag("a").get(0).attr("title"));
            article1Item.setDate(articles.get(i).getElementsByTag("time").get(0).attr("datetime"));
            String bg = articles.get(i).getElementsByClass("eael-timeline-post-image").get(0).attr("style");
            bg = bg.substring(bg.indexOf("http"), bg.indexOf("')"));
            article1Item.setImage(bg);
            articleList.add(article1Item);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (articleList == null)
            Toast.makeText(getView().getContext(), getView().getResources().getString(R.string.error_no_internet_conection), Toast.LENGTH_SHORT).show();

        CastomAdapter adapter = new CastomAdapter(getView().getContext(), articleList);
        ListView listView = getView().findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Article article = (Article) adapter.getItem(i);
            String url = article.getUrlArticle();
            Intent intent = new Intent(view.getContext(), NewsActivity.class);
            intent.putExtra("url", url);
            view.getContext().startActivity(intent);
        });

        super.onPostExecute(aVoid);
    }


}
