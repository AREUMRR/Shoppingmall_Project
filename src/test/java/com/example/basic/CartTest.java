package com.example.basic;

import com.example.basic.Constant.RoleType;
import com.example.basic.Entity.*;
import com.example.basic.Repository.CartItemRepository;
import com.example.basic.Repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartTest {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    public void setCart() {
        CartEntity cart = CartEntity.builder()
                .memberEntity(MemberEntity.builder()
                        .memberId(1)
                        .memberEmail("rkrk@gmail.com")
                        .memberPassword("1234")
                        .memberName("김다다")
                        .memberAddress("경기도 부천시")
                        .memberPhone("01000000000")
                        .build())
                .build();
        cartRepository.save(cart);
    }

    //Test 시 주의!! 테이블에 생성된 인스턴트를 꼭 참조해라.
    @Test
    public void setCartTest() {
        CartItemEntity cartItem = CartItemEntity.builder()
                .cartEntity(CartEntity.builder()
                        .cartId(1)
                        .build())
                .productEntity(ProductEntity.builder()
                        .productId(31)
                        .productName("상품Test")
                        .productContent("Test##")
                        .productPrice(10000)
                        .build())
                .quantity(5)
                .build();
        cartItemRepository.save(cartItem);

    }

}
