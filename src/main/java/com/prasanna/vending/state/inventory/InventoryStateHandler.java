package com.prasanna.vending.state.inventory;

import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.ServiceEvent;
import com.prasanna.vending.exception.ProductNotAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class InventoryStateHandler implements InventoryState, InventoryStateUpdate {

    private Map<VendableProduct, Integer> map;
    static final Logger LOG = LoggerFactory.getLogger(InventoryStateHandler.class);

    public InventoryStateHandler(Map<VendableProduct, Integer> map) {
        this.map = new HashMap<VendableProduct, Integer>(map);
    }

    public Map<VendableProduct, Integer> getCurrentInventory() {
        return Collections.unmodifiableMap(map);
    }

    private Map<VendableProduct, Integer> getModifiableInventory() {
        return map;
    }

    public InventoryStateUpdate updateInventory(SellProductEvent event) {
        VendableProduct vendableProduct = event.getVendableProduct();
        Integer integer = map.get(vendableProduct);

        if (integer == null || integer == 0) {
            throw new ProductNotAvailableException("Product Not Available for Vending :" + vendableProduct);
        }

        InventoryStateHandler inventory = new InventoryStateHandler(map);
        inventory.getModifiableInventory().put(vendableProduct, --integer);

        return inventory;
    }


    public InventoryStateUpdate performService(ServiceEvent serviceEvent) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        LOG.info("Performing Inventory Update Service");
        InventoryStateHandler inventory = new InventoryStateHandler(map);

        VendableProduct[] values = VendableProduct.values();
        for (VendableProduct p : values) {
            Integer integer = inventory.map.get(p);
            System.out.println("Please Enter quantity to update  for:" + p + " Available :" + integer);
            String input = null;
            try {
                input = br.readLine();
                inventory.map.put(p, Integer.parseInt(input));
            } catch (IOException e) {
                LOG.error("System error: Ignoring update for " + p);
            } catch (NumberFormatException e) {
                LOG.error("System error: Ignoring update for " + p);
            }

        }

        return inventory;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
