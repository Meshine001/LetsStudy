package com.meshine.letsstudyclient.net;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Field;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 * Created by Ming on 2016/5/15.
 */
@Rest(rootUrl = NetInfo.ROOT_URL,converters = {FormHttpMessageConverter.class,MappingJackson2HttpMessageConverter.class,StringHttpMessageConverter.class})
public interface MyRestClient extends RestClientHeaders{

    @Get("?contacts.get&username={username}")
    String getContacts(@Path String username) throws RestClientException;

    @Post("?contacts.add")
    String addContacts(@Field String username,@Field String contacts) throws RestClientException;

    @Post("?event.add")
    String addEvent(@Field String data) throws RestClientException;

    @Post("?file.upload")
    @RequiresHeader(HttpHeaders.CONTENT_TYPE)
    String uploadFile(@Body MultiValueMap<String, Object> data) throws RestClientException;
}
