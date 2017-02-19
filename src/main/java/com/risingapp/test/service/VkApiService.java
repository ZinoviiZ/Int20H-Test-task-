package com.risingapp.test.service;

import com.risingapp.test.connector.VkAuthorizeConnector;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zinoviyzubko on 18.02.17.
 */
@Service
public class VkApiService {

    @Autowired private VkAuthorizeConnector vkAuthorize;

    protected UserActor getVkUser(VkApiClient vk, String code) throws ClientException, ApiException {

        return vkAuthorize.getVkUser(vk, code);
    }
}
