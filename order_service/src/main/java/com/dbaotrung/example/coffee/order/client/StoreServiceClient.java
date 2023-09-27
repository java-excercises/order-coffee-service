package com.dbaotrung.example.coffee.order.client;

import com.dbaotrung.example.coffee.order.config.GenericFeignClientConfig;
import com.dbaotrung.example.coffee.order.dto.CoffeeStoreDto;
import com.dbaotrung.example.coffee.order.dto.GeneralApiResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.store.name}", url = "${feign.store.url}", configuration = GenericFeignClientConfig.class)
@ConditionalOnProperty(prefix = "feign.store", name = "name")
public interface StoreServiceClient {

    @GetMapping(value = "/{storeId}")
    GeneralApiResponse<CoffeeStoreDto> viewDetails(@PathVariable("storeId") long entityId);
}
