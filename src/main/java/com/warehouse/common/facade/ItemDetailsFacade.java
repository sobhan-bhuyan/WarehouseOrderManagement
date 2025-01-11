package com.warehouse.common.facade;

import com.fasterxml.jackson.core.type.TypeReference;
import com.warehouse.common.facade.models.ims.ItemDetailsResponse;
import com.warehouse.common.utilities.Constants;
import com.warehouse.services.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ItemDetailsFacade {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public Map<String, ItemDetailsResponse.ItemLocation> fetchItemDetails(Set<String> itemIds) {
        //redisService.delete(Constants.REDIS_KEY_IMS_ITEM_DETAILS);
        Optional<List<ItemDetailsResponse>> optionalItemDetailsResponses = redisService.get(Constants.REDIS_KEY_IMS_ITEM_DETAILS, new TypeReference<>() {
        });

        if (optionalItemDetailsResponses.isPresent()) {
            return filterItemDetails(optionalItemDetailsResponses.get(), itemIds);
        }
        String url = "http://demo2086836.mockable.io/ims/get-products";

        ResponseEntity<List<ItemDetailsResponse>> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        List<ItemDetailsResponse> itemDetailsResponseList = responseEntity.getBody();
        redisService.set(Constants.REDIS_KEY_IMS_ITEM_DETAILS, itemDetailsResponseList, 30L);

        assert itemDetailsResponseList != null;
        return filterItemDetails(itemDetailsResponseList, itemIds);
    }

    private Map<String, ItemDetailsResponse.ItemLocation> filterItemDetails(List<ItemDetailsResponse> itemDetailsResponseList, Set<String> itemIds) {
        return itemDetailsResponseList.stream()
                .filter(itemDetailsResponse -> itemIds.contains(itemDetailsResponse.getItemId()))
                .collect(Collectors.toMap(
                        ItemDetailsResponse::getItemId,
                        ItemDetailsResponse::getItemLocation
                ));
    }
}
