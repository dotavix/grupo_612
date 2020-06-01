/*
package com.example.becareful;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;
import java.net.URLEncoder;

public class GoogleSearch {

    String encoding = "UTF-8";

		try {
        String searchText = "donald trump wikipedia";
        Document duckduckgo = Jsoup.connect("https://duckduckgo.com/?q=" + URLEncoder.encode(searchText, encoding)).userAgent("Mozilla/5.0").get();

        Elements webSitesLinks = duckduckgo.getElementsByClass("result__url__domain");

        //Check if any results found
        if (webSitesLinks.isEmpty()) {
            System.out.println("No results found");
            return;
        }

        webSitesLinks.forEach(link->System.out.println(link.text()));

        //========If you want to get the first link ================
        //String firstWebSiteLink = webSitesLinks.get(0).text();

        //System.out.println(firstWebSiteLink);

        //Move to the next page code..

    } catch (IOException e) {
        e.printStackTrace();
    }

}
*/
