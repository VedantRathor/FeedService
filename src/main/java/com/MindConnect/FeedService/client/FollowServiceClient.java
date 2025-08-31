package com.MindConnect.FeedService.client;

import com.MindConnect.FeedService.dto.FollowerCountResponseDTO;
import com.MindConnect.FeedService.dto.FollowerResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FollowServiceClient {
    private final RestTemplate restTemplate;
    private final String followServiceUrl = "http://localhost:8082/api/follows";

    public FollowServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FollowerCountResponseDTO getFollowerCount(String userId) {
        String url = followServiceUrl + "/" + userId + "/" + "count";
        return restTemplate.getForObject(url, FollowerCountResponseDTO.class);
    }

    public FollowerResponseDTO getFollowers(String userId, String cursor, Long batchSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(followServiceUrl + "/" + userId + "/" + "followers")
                .queryParam("batchSize", batchSize);

        if (cursor != null && !cursor.isEmpty()) {
            builder.queryParam("cursor", cursor);
        }

        return restTemplate.getForObject(builder.toUriString(), FollowerResponseDTO.class);
    }
}
