package com.example.basic.Service;

import com.example.basic.DTO.CartDTO;
import com.example.basic.DTO.CartItemDTO;
import com.example.basic.Entity.*;
import com.example.basic.Repository.CartItemRepository;
import com.example.basic.Repository.CartRepository;
import com.example.basic.Repository.MemberRepository;
import com.example.basic.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    //삽입
    public void cartInsert(Integer memberId, Integer productId, Integer quantity) {

        //회원 조회
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 회원의 장바구니 조회
        CartEntity cart = cartRepository.findByMemberId(memberEntity.getMemberId());
        if (cart == null) {
            cart = CartEntity.builder()
                    .memberEntity(memberEntity)
                    .build();
            cartRepository.save(cart);
        }

        ProductEntity product = productRepository.findById(productId).orElseThrow();

        // 장바구니에 상품이 이미 존재하는지 확인
        CartItemEntity cartItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(), product.getProductId());

        // 장바구니에 상품이 존재하지 않는다면 카트상품 생성 후 추가
        if (cartItem == null) {
            cartItem = CartItemEntity.builder()
                    .cartEntity(cart)
                    .productEntity(product)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(cartItem);
        } else {
            // 장바구니에 상품이 존재하면 수량만 변경
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        }
    }


    //수정
    public void cartUpdate(Integer memberId, Integer quantity) {

        //회원 조회
        Optional<MemberEntity> member = memberRepository.findById(memberId);
        MemberEntity memberEntity = member.orElseThrow();

        //회원의 장바구니 조회
        CartEntity cart = cartRepository.findByMemberId(memberEntity.getMemberId());

        //장바구니 상품 조회
        CartItemEntity cartItem = cartItemRepository.findByCartId(
                cart.getCartId());

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    //삭제
    public void cartDelete(Integer cartItemId, Integer cartId) {

        cartItemRepository.deleteById(cartItemId, cartId);
    }

    //전체 조회(장바구니 상품)
    public List<CartItemDTO> cartList(Integer memberId) {
        //회원의 장바구니 조회
        CartEntity cart = cartRepository.findByMemberId(memberId);

        //장바구니 상품 찾기
        List<CartItemEntity> cartEntities = cartItemRepository.findAllByCartId(cart.getCartId());

        //장바구니에 상품이 존재하면
        if (cartEntities != null) {
            //상품 정보 담기
            List<CartItemDTO> cartItemDTOS = new ArrayList<>();
            for (CartItemEntity cartItemEntity : cartEntities) {
                //상품 리스트에 상품이 존재하면
                if (cartItemEntity != null) {

                    CartItemDTO cartItemDTO = modelMapper.map(cartItemEntity, CartItemDTO.class);
                    cartItemDTOS.add(cartItemDTO);
                }
            }
            return cartItemDTOS;
        } else {
            //장바구니에 상품이 존재하지 않으면
            return Collections.emptyList();
        }
    }

    //장바구니 아이템 개별조회
    public CartItemDTO cartItemDetail(Integer cartItemId, Integer cartId) {
        CartItemEntity cartItemEntity = cartItemRepository.findByCartItemIdAndCartId(cartItemId, cartId);

        CartItemDTO cartItemDTO = modelMapper.map(cartItemEntity, CartItemDTO.class);

        return cartItemDTO;
    }

    //회원의 장바구니 개별조회
    public CartDTO cartDetail(Integer memberId) {

        //회원의 장바구니 생성
        CartEntity cart = cartRepository.findByMemberId(memberId);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        return cartDTO;
    }
}
