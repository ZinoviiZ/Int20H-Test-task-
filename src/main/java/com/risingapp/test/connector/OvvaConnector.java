package com.risingapp.test.connector;

import com.risingapp.test.enums.OvvaAction;
import com.risingapp.test.response.OvvaResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Component
public class OvvaConnector {

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials("username", "password"));
        HttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        List<HttpMessageConverter<?>> messageConverters = new LinkedList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }

    public <T extends OvvaResponse> T send(String query, Class<T> responseType) throws URISyntaxException {

        URI uri = new URI(query);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);

        HttpEntity<T> responseData = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, responseType);

        return responseData.getBody();
    }
}
