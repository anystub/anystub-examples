package org.anystub.examples;

import org.springframework.stereotype.Component;

;

@Component
public class Worker {

//    private final StoreApi storeApi;
//
//    public Worker(StoreApi storeApi) {
//        this.storeApi = storeApi;
//    }

    public int numberOfItems() {
        return 1;
//        return storeApi.getInventory().values().stream().findFirst().orElse(-1);
    }
}
