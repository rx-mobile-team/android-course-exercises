package com.rxmobileteam.lecture1.service;

import com.rxmobileteam.lecture1.data.ProductDao;
import com.rxmobileteam.lecture1.data.ProductInterface;
import com.rxmobileteam.utils.ExerciseNotCompletedException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@link ProductService} provides an API that allows to manage {@link Product}s.
 * <p>
 * TODO: 1. Using {@link ProductDao} implement method {@link ProductService#addProduct(Product)}}
 * TODO: 2. Using {@link ProductDao} implement method {@link ProductService#searchProducts(String)}
 */
public class ProductService {
    private final ProductInterface productDao;

    public ProductService(ProductInterface productDao) {
        this.productDao = productDao;
    }

    /**
     * Adds a new product to the system.
     *
     * @param product a product to add
     * @return {@code true} if a product was added, {@code false} otherwise.
     */
    public boolean addProduct(@NotNull Product product) {
        // TODO: implement this method
        return this.productDao.add(product);
    }

    /**
     * Returns all products that contains the given query in the name or description.
     *
     * @param query a search query
     * @return a list of found products
     */
    @NotNull
    public List<Product> searchProducts(@NotNull String query) {
        // TODO: implement this method
        String queryLower = query.toLowerCase();
        return productDao.findAll().stream().filter(product ->
            product.getName().contains(queryLower)
                || product.getDescription().contains(queryLower)).toList();
    }
}
