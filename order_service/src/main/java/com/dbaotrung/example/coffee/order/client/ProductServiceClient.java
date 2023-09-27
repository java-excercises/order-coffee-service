package com.dbaotrung.example.coffee.order.client;

import com.dbaotrung.example.coffee.order.config.GenericFeignClientConfig;
import com.dbaotrung.example.coffee.order.dto.CoffeeStoreDto;
import com.dbaotrung.example.coffee.order.dto.GeneralApiResponse;
import com.dbaotrung.example.coffee.order.dto.ProductDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.product.name}", url = "${feign.product.url}", configuration = GenericFeignClientConfig.class)
@ConditionalOnProperty(prefix = "feign.product", name = "name")
public interface ProductServiceClient {

    @GetMapping(value = "/{productId}")
    GeneralApiResponse<ProductDto> viewDetails(@PathVariable("productId") long productId);
}
