package com.oreilly.restclient.services;

import com.oreilly.restclient.entities.Site;
import com.oreilly.restclient.json.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class GeocoderService {
    private static final String BASE = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String KEY = "AIzaSyDw_d6dfxDEI7MAvqfGXEIsEMwjC1PWRno";

    private RestTemplate restTemplate;

    @Autowired
    public GeocoderService(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public Site getLatLng(String... address) throws UnsupportedEncodingException {
        String joinedAddress = String.join(",", address);
        String encodedAddress = "";
        encodedAddress = URLEncoder.encode(joinedAddress,"UTF-8");

        Response response = restTemplate.getForObject(
                String.format("%s?address=%s&key=%s", BASE, encodedAddress, KEY), Response.class);

        return new Site(response.getFormattedAddress(),
                response.getLocation().getLat(),
                response.getLocation().getLng());
    }
}