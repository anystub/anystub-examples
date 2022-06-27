package org.anystub.examples;

import org.anystub.examples.ps.StoreApi;
import org.anystub.examples.quotes.QuoteOfTheDayApi;
import org.openapitools.client.model.QuoteResponse;
import org.springframework.stereotype.Component;

;

@Component
public class Worker {

    private final StoreApi storeApi;
    private final QuoteOfTheDayApi quoteOfTheDayApi;

    public Worker(StoreApi storeApi, QuoteOfTheDayApi quoteOfTheDayApi) {
        this.storeApi = storeApi;
        this.quoteOfTheDayApi = quoteOfTheDayApi;
        storeApi.getApiClient().setBasePath("https://petstore3.swagger.io/api/v3");
        quoteOfTheDayApi.getApiClient().setBasePath("https://quotes.rest");
    }

    /**
     * (!) silly worker
     * requests pet-store and quotes service
     * @return a number from petShop inventory + number of quotes of the day
     */
    public int numberOfItems() {
        QuoteResponse quoteResponse = quoteOfTheDayApi.qodGet("", "en");
        return storeApi.getInventory().values().stream().findFirst()
                .map(i->i+quoteResponse.getContents().getQuotes().size())
                .orElse(-1);
    }
}
