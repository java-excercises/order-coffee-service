package com.dbaotrung.example.coffee.store.controller;

import com.dbaotrung.example.coffee.store.dto.GeneralApiResponse;
import com.dbaotrung.example.coffee.store.dto.MenuItemDto;
import com.dbaotrung.example.coffee.store.dto.PageDto;
import com.dbaotrung.example.coffee.store.entity.CoffeeStore;
import com.dbaotrung.example.coffee.store.entity.MenuItem;
import com.dbaotrung.example.coffee.store.service.CoffeeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private CoffeeStoreService coffeeStoreService;

    @PostMapping(value = "/")
    public PageDto<CoffeeStore> loadAllStores(@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                              @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                              @RequestParam(value = "orders", required = false, defaultValue = "[]")
                              List<Sort.Order> orders) {
        return PageDto.createSuccessResponse(coffeeStoreService.loadAllStores(pageSize, pageIndex, orders));
    }

    @PostMapping(value = "/create-or-update")
    public GeneralApiResponse<CoffeeStore> createOrUpdateStore(@RequestBody CoffeeStore coffeeStore) {
        return new GeneralApiResponse<>(coffeeStoreService.createOrUpdateStore(coffeeStore));
    }

    @DeleteMapping(value = "/{storeId}")
    public GeneralApiResponse<Boolean> deleteStore(@PathVariable("storeId") long entityId) {
        return new GeneralApiResponse<>(coffeeStoreService.deleteStore(entityId));
    }

    @GetMapping(value = "/{storeId}")
    public GeneralApiResponse<CoffeeStore> viewDetails(@PathVariable("storeId") long entityId) {
        return new GeneralApiResponse<>(coffeeStoreService.loadStoreDetails(entityId));
    }

    @PatchMapping(value = "/update-store-menu")
    public GeneralApiResponse<CoffeeStore> updateStoreMenu(@RequestParam long storeId, @RequestBody List<MenuItemDto> menuItemDtos) {
        return new GeneralApiResponse<>(coffeeStoreService.updateStoreMenu(storeId, menuItemDtos));
    }
}
