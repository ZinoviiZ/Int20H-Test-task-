package com.risingapp.test.service;

import com.risingapp.test.cache.CacheManager;
import com.risingapp.test.enums.OvvaChannel;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.SearchResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetOwnerPhotoUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.SaveOwnerPhotoResponse;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class VkUserApiService extends VkApiService {

    private VkApiClient vk;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired private CacheManager cacheManager;


    @Value("${app_name}")
    private String appName;

    @PostConstruct
    private void init() {

        vk = new VkApiClient(HttpTransportClient.getInstance());
    }

    public ResponseEntity postImageInGroup(String code) throws ClientException, ApiException {

        UserActor actor = getVkUser(vk, code);
        File file = cacheManager.getTvProgram(sdf.format(new Date()), OvvaChannel.CHANNEL_1PLUS1.getValue());

//        if(!isGroupAlreadyCreated(actor, appName))
//            vk.groups().create(actor, appName).execute();

        SearchResponse response = vk.groups().search(actor, "RisingApp").execute();
        List<Group> groups = response.getItems();
        Integer groupId = Integer.valueOf(groups.get(0).getId());

        PhotoUpload serverResponse = vk.photos().getWallUploadServer(actor).execute();
        WallUploadResponse uploadResponse = vk.upload().photoWall(serverResponse.getUploadUrl(), file).execute();
        List<Photo> photoList = vk.photos().saveWallPhoto(actor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

        Photo photo = photoList.get(0);
        String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();
        PostResponse getResponse = vk.wall().post(actor)
                .attachments(attachId)
                .execute();

        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean isGroupAlreadyCreated(UserActor actor, String name) throws ClientException, ApiException {

        List<Group> groups = vk.groups().search(actor, name).execute().getItems();

        if (groups == null) return false;
        for (Group group : groups) {
            if (group.getName().equals(name)) return true;
        }
        return false;
    }
}
