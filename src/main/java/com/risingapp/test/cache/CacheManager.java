package com.risingapp.test.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Component
public class CacheManager {

    private Map<TvProgramId, File> cachedMap;

    @PostConstruct
    public void init() {
        cachedMap = new HashMap<TvProgramId, File>();
    }

    public void addTvProgram(String date, String channel, File file) {
        TvProgramId id = new TvProgramId(date, channel);
        cachedMap.put(id, file);
    }

    public File getTvProgram(String date, String channel) {
        TvProgramId id = new TvProgramId(date, channel);
        File file = cachedMap.get(id);
        return file;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    static class TvProgramId {

        private String date;
        private String channel;
    }
}
