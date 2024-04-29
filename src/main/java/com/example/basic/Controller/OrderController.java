
/*
    설명 : 상품관리의 목록, 수정, 삭제, 조회로 이동하는 페이지 영역
    입력값 : /order/list, /order/insert, /order/delete, /order/detail
    출력값 : order/list, order/insert, order/detail
    작성일 : 24.04.12
    작성자 : 정아름
    수정사항 :
 */

package com.example.basic.Controller;

import com.example.basic.DTO.*;
import com.example.basic.Service.MemberService;
import com.example.basic.Service.OrderService;
import com.example.basic.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    //주입
    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;

    //전체 조회
    @GetMapping("/order/list")
    public String listForm(@AuthenticationPrincipal User user, Model model) throws NullPointerException {
        //회원 정보가 있으면
        //로그인 정보로 회원 조회
        MemberDTO memberDTO = memberService.detail(user.getUsername());
        if (memberDTO != null) {

            //회원의 주문 목록 조회
            List<OrderItemDTO> orderItemDTO = null;
            if (orderItemDTO.get(1).isOrderStatus()) {
                orderItemDTO = orderService.orderList(memberDTO.getMemberId());
            }

            model.addAttribute("totalCount", orderItemDTO);
            model.addAttribute("mid", memberDTO.getMemberId());
            model.addAttribute("list", orderItemDTO);

            return "order/list";
            //회원 정보가 없으면 상품 목록 페이지로 이동
        } else {
            return "redirect:/product/list";
        }
    }

    //삽입(주문)
    @PostMapping("/order/insert/{id}")
    public String insertProc(@PathVariable Integer id,
                             @RequestParam Integer count,
                             @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {

        //회원 정보가 없으면 상품 목록 페이지로 이동
        if (user == null) {
            return "redirect:/product/list";
        }

        //유저 정보 가져오기
        String memberEmail = user.getUsername();
        Integer memberId = memberService.detail(memberEmail).getMemberId();

        ProductDTO productDTO = productService.productDetail(id);

        //상품의 재고량이 0 보다 작다면 재고 부족 발생
        if (productDTO == null || productDTO.getQuantityCount() < 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "재고량이 부족합니다.");
            return "redirect:/product/list";
        }

        Integer orderPrice = productDTO.getProductPrice() * productDTO.getQuantity();

        orderService.orderInsert(memberId, productDTO.getProductId(), count,
                orderPrice);

        redirectAttributes.addFlashAttribute("successMessage",
                "주문 페이지로 이동");

        System.out.println(productDTO);
        System.out.println(memberId);
        System.out.println(count);

        redirectAttributes.addAttribute("id", id);

        return "redirect:/order/detail";
    }

    //주문 페이지
    @GetMapping("/order/detail")
    public String detailForm(@AuthenticationPrincipal User user, Model model,
                             Integer id) throws NullPointerException {
        //회원 정보가 있으면
        //로그인 정보로 회원 조회
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        if (memberDTO != null) {

            OrderDTO orderDTO = orderService.orderDetail(memberDTO.getMemberId());
            //회원의 주문 목록 조회
            List<OrderItemDTO> orderItemDTO = orderService.orderList(memberDTO.getMemberId());
            OrderItemDTO order = null;
            for (OrderItemDTO order1 : orderItemDTO) {
                if (order1.getProductId().equals(id)) {
                    order = orderService.itemDTO(order1.getOrderItemId(), orderDTO.getOrderId());
                }
            }

            model.addAttribute("order", orderDTO);
            model.addAttribute("mid", memberDTO.getMemberId());
            model.addAttribute("data", order);

            return "order/detail";
            //회원 정보가 없으면 상품 목록 페이지로 이동
        } else {
            return "redirect:/product/list";
        }
    }

    //주문 페이지(주문 상태 체크)
    @PostMapping("/order/detail")
    public String detailProc(Integer mid, Integer id, Integer oid,
                             @AuthenticationPrincipal User user, Model model,
                             RedirectAttributes redirectAttributes) throws NullPointerException {
        //로그인 정보로 회원 조회
        MemberDTO memberDTO = memberService.detail(user.getUsername());

        OrderDTO orderDTO = orderService.orderDetail(mid);

        //회원 정보가 있으면
        if (memberDTO.getMemberId().equals(mid) & orderDTO.getOrderId().equals(oid)) {

            //회원의 주문 상태 변경
            orderService.orderCheckout(memberDTO.getMemberId(), id);

            model.addAttribute("id", id);
            return "order/list";
            //회원 정보가 없으면 상품 목록 페이지로 이동
        } else {
            return "redirect:/product/list";
        }
    }

    //삭제
    @GetMapping("/order/delete")
    public String deleteProc(Integer mid, Integer id, RedirectAttributes redirectAttributes) {

        OrderDTO orderDTO = orderService.orderDetail(mid);

        orderService.orderDelete(id, orderDTO.getOrderId());

        redirectAttributes.addFlashAttribute("successMessage",
                "주문이 취소되었습니다.");

        return "redirect:/order/list";
    }
}
