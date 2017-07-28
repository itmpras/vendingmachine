package com.prasanna.vending.state.inventory;

import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.exception.ProductNotAvailableException;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by pgopal on 27/07/2017.
 */
public class MapBasedVendingMachineInventoryTest {

    InventoryStateHandler inventory;


    @Test(expected = ProductNotAvailableException.class)
    public void shouldThrowAnExceptionIfWeDontHaveEnoughProduct() throws Exception {
        inventory = new InventoryStateHandler(new HashMap<VendableProduct, Integer>());
        inventory.updateInventory(new SellProductEvent(VendableProduct.A));

    }


    @Test
    public void shouldReduceProductCount() throws Exception {
        HashMap<VendableProduct, Integer> map = new HashMap<VendableProduct, Integer>();
        map.put(VendableProduct.A, 2);
        inventory = new InventoryStateHandler(map);

        InventoryStateUpdate inventoryStateUpdate = inventory.updateInventory(new SellProductEvent(VendableProduct.A));
        Integer available = inventoryStateUpdate.getCurrentInventory().get(VendableProduct.A);

        MatcherAssert.assertThat("Product Count should get reduced", available == 1);
    }

}