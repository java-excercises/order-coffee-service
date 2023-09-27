package com.dbaotrung.example.coffee.store.service;

import com.dbaotrung.example.coffee.store.entity.CoffeeStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CoffeeStoreService {

    Page<CoffeeStore> loadAllStores(int pageSize, int pageIndex, List<Sort.Order> orders);

    CoffeeStore createOrUpdateStore(CoffeeStore coffeeStore);

    boolean deleteStore(long itemId);

    CoffeeStore loadStoreDetails(long itemId);
}
