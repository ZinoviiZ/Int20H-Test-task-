package com.risingapp.test.cache;

import com.risingapp.test.enums.VkGroup;
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
public class GroupCacheManager {

    private Map<GroupId, Integer> cachedMap;

    @PostConstruct
    private void init() {
        cachedMap = new HashMap<>();
    }

    public void addGroupId(VkGroup vkGroup, Integer grId) {
        GroupId groupId = new GroupId(vkGroup);
        cachedMap.put(groupId, grId);
    }

    public Integer getGroupId(VkGroup vkGroup) {
        GroupId groupId = new GroupId(vkGroup);
        return cachedMap.get(groupId);
    }

    @Data
    @AllArgsConstructor
    private class GroupId {

        private VkGroup vkGroup;
    }

}
