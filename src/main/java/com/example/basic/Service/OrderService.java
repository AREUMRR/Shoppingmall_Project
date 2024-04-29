package com.example.basic.Service;

import com.example.basic.DTO.OrderDTO;
import com.example.basic.DTO.OrderItemDTO;
import com.example.basic.Entity.*;
import com.example.basic.Repository.OrderItemRepository;
import com.example.basic.Repository.OrderRepository;
import com.example.basic.Repository.MemberRepository;
import com.example.basic.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    //삽입
    public void orderInsert(Integer memberId, Integer productId, Integer quantity
            , Integer orderPrice) {

        //회원 조회
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 회원의 주문 테이블 조회
        OrderEntity order = orderRepository.findByMemberId(memberEntity.getMemberId());
        if (order == null) {
            order = OrderEntity.builder()
                    .memberEntity(memberEntity)
                    .build();
            orderRepository.save(order);
        }

        ProductEntity product = productRepository.findById(productId).orElseThrow();

        // 주문 테이블에 상품이 이미 존재하는지 확인
        OrderItemEntity orderItem = orderItemRepository.findByOrderIdAndProductId(order.getOrderId(), product.getProductId());

        // 주문 테이블에 상품이 존재하지 않는다면 주문 테이블 생성 후 추가
        if (orderItem == null) {
            orderItem = OrderItemEntity.builder()
                    .orderEntity(order)
                    .productEntity(product)
                    .quantity(quantity)
                    .orderPrice(product.getProductPrice() * quantity)
                    .build();
            orderItemRepository.save(orderItem);
        } else {
            // 주문 테이블에 상품이 존재하면 수량과 금액 변경
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            orderItem.setOrderPrice(orderItem.getOrderPrice() + orderPrice);
            orderItemRepository.save(orderItem);
        }
    }

    //주문 상태 확인
    public void orderCheckout(Integer memberId, Integer orderItemId) {
        //회원 조회
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 회원의 주문 테이블 조회
        OrderEntity order = orderRepository.findByMemberId(memberEntity.getMemberId());
        if (order == null) {
            order = OrderEntity.builder()
                    .memberEntity(memberEntity)
                    .build();
            orderRepository.save(order);
        }

        //주문 테이블 상품 찾기
        OrderItemEntity orderEntity = orderItemRepository.findById(orderItemId).orElseThrow();

        //주문 테이블에 상품이 존재하면
        if (orderEntity != null) {
            //상품 정보 수정
            orderEntity.setOrderStatus(true);
            //주문상태 체크
        }

        orderItemRepository.save(orderEntity);
    }


    //삭제
    public void orderDelete(Integer orderItemId, Integer orderId) {

        orderItemRepository.deleteById(orderItemId, orderId);
    }

    //전체 조회(주문 상품)
    public List<OrderItemDTO> orderList(Integer memberId) {
        //회원의 주문 테이블 조회
        OrderEntity order = orderRepository.findByMemberId(memberId);

        //주문 테이블 상품 찾기
        List<OrderItemEntity> orderEntities = orderItemRepository.findAllByOrderId(order.getOrderId());

        //주문 테이블에 상품이 존재하면
        if (orderEntities != null) {
            //상품 정보 담기
            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
            for (OrderItemEntity orderItemEntity : orderEntities) {
                //상품 리스트에 상품이 존재하면
                if (orderItemEntity != null) {

                    OrderItemDTO orderItemDTO = modelMapper.map(orderItemEntity, OrderItemDTO.class);
                    orderItemDTOS.add(orderItemDTO);
                }
            }
            return orderItemDTOS;
        } else {
            //주문 테이블에 상품이 존재하지 않으면
            return Collections.emptyList();
        }
    }

    //개별 주문
    public OrderItemDTO itemDTO(Integer orderItemId, Integer orderId) {

        //주문테이블 id와 주문상품테이블id로 조회
        OrderItemEntity orderItemEntity = orderItemRepository.findByOrderItemIdAndOrderId(orderItemId, orderId);

        OrderItemDTO orderItemDTO = modelMapper.map(orderItemEntity, OrderItemDTO.class);

        return orderItemDTO;
    }


    //회원의 주문 테이블 개별조회
    public OrderDTO orderDetail(Integer memberId) {

        //회원의 장바구니 생성
        OrderEntity order = orderRepository.findByMemberId(memberId);

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        return orderDTO;
    }
}
