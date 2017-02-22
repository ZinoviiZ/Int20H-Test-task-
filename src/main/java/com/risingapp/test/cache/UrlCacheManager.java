package com.risingapp.test.cache;

import com.risingapp.test.enums.OvvaChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zinoviyzubko on 22.02.17.
 */
@Component
public class UrlCacheManager {

    private Map<UrlId, String> cachedMap;

    @PostConstruct
    private void init() {
        cachedMap = new HashMap<>();
    }

    public void addUrl(String imageName, String date, OvvaChannel channel) {
        UrlId urlId = new UrlId(date, channel);
        cachedMap.put(urlId, imageName);
    }

    public String getUrl(String date, OvvaChannel channel) {
        UrlId urlId = new UrlId(date, channel);
        return cachedMap.get(urlId);
    }

    @Data
    @AllArgsConstructor
    private class UrlId {

        private String date;
        private OvvaChannel channel;
    }

}
