package com.risingapp.test.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zinoviyzubko on 22.02.17.
 */
@Component
public class UrlCacheManager {

    private Map<UrlId, String> cachedMap;

    public void addUrl(String url, String date) {

        UrlId urlId = new UrlId(date);
        cachedMap.put(urlId, url);
    }

    public String getUrl(String date) {

        UrlId urlId = new UrlId(date);
        return cachedMap.get(urlId);
    }

    @Data
    @AllArgsConstructor
    private class UrlId {

        private String date;
    }
}
