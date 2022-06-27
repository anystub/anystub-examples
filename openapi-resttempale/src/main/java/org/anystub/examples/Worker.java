package org.anystub.examples;

import org.anystub.examples.gen.StoreApi;
import org.springframework.stereotype.Component;

;

@Component
public class Worker {

    private final StoreApi storeApi;

    public Worker(StoreApi storeApi) {
        this.storeApi = storeApi;
        storeApi.getApiClient().setBasePath("https://petstore3.swagger.io/api/v3");
    }

    public int numberOfItems() {
        return storeApi.getInventory().values().stream().findFirst().orElse(-1);
    }
}
