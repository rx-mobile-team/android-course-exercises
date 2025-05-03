package com.rxmobileteam.lecture1.service;

import com.rxmobileteam.lecture1.data.ProductDao;
import com.rxmobileteam.utils.ExerciseNotCompletedException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link ProductService} provides an API that allows to manage {@link Product}s.
 * <p>
 * TODO: 1. Using {@link ProductDao} implement method {@link ProductService#addProduct(Product)}}
 * TODO: 2. Using {@link ProductDao} implement method {@link ProductService#searchProducts(String)}
 */
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Adds a new product to the system.
     *
     * @param product a product to add
     * @return {@code true} if a product was added, {@code false} otherwise.
     */
    public boolean addProduct(@NotNull Product product) {
        return productDao.add(product);
    }

    /**
     * Returns all products that contains the given query in the name or description.
     *
     * @param query a search query
     * @return a list of found products
     */
    @NotNull
    public List<Product> searchProducts(@NotNull String query) {
        return productDao.findAll()
            .stream()
            .filter(product ->
                product.getName().toLowerCase().contains(query.toLowerCase())
                    || product.getDescription().toLowerCase().contains(query.toLowerCase()))
            .toList();
    }
}
