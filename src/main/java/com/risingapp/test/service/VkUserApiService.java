package com.risingapp.test.service;

import com.risingapp.test.cache.FileCacheManager;
import com.risingapp.test.cache.GroupCacheManager;
import com.risingapp.test.cache.UrlCacheManager;
import com.risingapp.test.connector.VkAuthorizeConnector;
import com.risingapp.test.enums.OvvaChannel;
import com.risingapp.test.enums.VkGroup;
import com.risingapp.test.response.GetImageUrlResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.responses.SearchResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zinoviyzubko on 18.02.17.
 */
@Service
@PropertySource("classpath:vk.properties")
public class VkUserApiService {

    private VkApiClient vk;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired private VkAuthorizeConnector vkAuthorize;
    @Autowired private FileCacheManager fileCacheManager;
    @Autowired private UrlCacheManager urlCacheManager;
    @Autowired private GroupCacheManager groupCacheManager;

    @PostConstruct
    private void init() {

        vk = new VkApiClient(HttpTransportClient.getInstance());
    }

    public void save(String code) throws ClientException, ApiException {

        UserActor actor = vkAuthorize.getVkUser(vk, code);
        File file = fileCacheManager.getTvProgram(sdf.format(new Date()), OvvaChannel.CHANNEL_1PLUS1.getValue());

        SearchResponse searchResponse = vk.groups().search(actor, VkGroup.RISING_APP.getGroupName()).execute();
        List<Group> groups = searchResponse.getItems();
        Integer groupId = Integer.valueOf(groups.get(0).getId());

        PhotoUpload serverResponse = vk.photos().getWallUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vk.upload().photoWall(serverResponse.getUploadUrl(), file).execute();

        List<Photo> photoList = vk.photos().saveWallPhoto(actor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

        Photo photo = photoList.get(0);

        groupCacheManager.addGroupId(VkGroup.RISING_APP, groupId);
        urlCacheManager.addUrl("photo" + actor.getId() + "_" + photo.getId(), sdf.format(new Date()), OvvaChannel.CHANNEL_1PLUS1);
    }

    public GetImageUrlResponse getImageUrl() {

        GetImageUrlResponse imageUrlResponse = new GetImageUrlResponse();
        String imageId = urlCacheManager.getUrl(sdf.format(new Date()), OvvaChannel.CHANNEL_1PLUS1);
        Integer groupId = groupCacheManager.getGroupId(VkGroup.RISING_APP);
        imageUrlResponse.setImageId(imageId);
        imageUrlResponse.setGroupId(groupId);
        return imageUrlResponse;
    }
}
