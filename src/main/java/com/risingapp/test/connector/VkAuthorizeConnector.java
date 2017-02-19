package com.risingapp.test.connector;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by zinoviyzubko on 18.02.17.
 */
@Component
@PropertySource("classpath:vk.properties")
public class VkAuthorizeConnector {

    @Value("${client_id}")
    private Integer clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${redirect_url}")
    private String redirectUrl;

    public UserActor getVkUser(VkApiClient vk, String code) throws ClientException, ApiException {

        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(clientId, clientSecret, redirectUrl, code)
                .execute();

        return new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
    }
}
