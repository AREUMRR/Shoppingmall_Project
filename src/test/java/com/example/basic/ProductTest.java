package com.example.basic;

import com.example.basic.Entity.ProductEntity;
import com.example.basic.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {

    @Autowired
    ProductRepository productRepository;

    //테스트 성공!!!!!!!!!!!!!!!
    @Test
    public void productInsertTest() {
        for (int i = 1; i <= 30; i++) {
            ProductEntity productEntity = ProductEntity.builder()
                    .productName("상품Test" + i)
                    .productContent("상품 테스트 입니다." + i)
                    .quantityIn(100)
                    .quantity(10)
                    .productPrice(i + 1000)
                    .build();
            productRepository.save(productEntity);
        }
    }

    @Test
    public void deleteTest() {
        for (int i = 1; i <= 30; i++) {
            ProductEntity productEntity = ProductEntity.builder()
                    .productId(i)
                    .build();
            productRepository.deleteById(productEntity.getProductId());
        }
    }
}
