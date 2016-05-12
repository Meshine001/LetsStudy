package com.meshine.letsstudyclient.net;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Field;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

/**
 * Created by Meshine on 16/5/12.
 */
@Rest(rootUrl = NetInfo.ROOT_URL,converters = {MappingJackson2HttpMessageConverter.class, StringHttpMessageConverter.class})
public interface MyRestClient {
    @Post("/contacts.php")
    List<String> getContacts(@Body long userId);
}
