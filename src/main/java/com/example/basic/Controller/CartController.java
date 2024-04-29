
/*
    설명 : 상품관리의 목록, 수정, 삭제, 조회로 이동하는 페이지 영역
    입력값 : /cart/list, /cart/insert, /cart/update, /cart/delete, /cart/detail
    출력값 : cart/list, cart/insert, cart/update, cart/detail
    작성일 : 24.02.22
    작성자 : 정아름
    수정사항 : 상품관리의 전체 목록 페이지는 list 처리 하기로 함
 */

package com.example.basic.Controller;

import com.example.basic.DTO.CartDTO;
import com.example.basic.DTO.CartItemDTO;
import com.example.basic.DTO.MemberDTO;
import com.example.basic.DTO.ProductDTO;
import com.example.basic.Service.CartService;
import com.example.basic.Service.MemberService;
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
public class CartController {
    //주입
    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    //전체 조회
    @GetMapping("/cart/list")
    public String listForm(@AuthenticationPrincipal User user, Model model,
                           RedirectAttributes redirectAttributes) throws NullPointerException {
        //회원 정보가 있으면
        //로그인 정보로 회원 조회
        MemberDTO memberDTO = memberService.detail(user.getUsername());
        if (memberDTO != null) {

            //회원의 장바구니 목록 조회
            List<CartItemDTO> cartItemDTO = cartService.cartList(memberDTO.getMemberId());

            //장바구니에 들어있는 상품의 총 가격
            int totalPrice = 0;
            for (CartItemDTO cartItem : cartItemDTO) {
                totalPrice += cartItem.getQuantity() * cartItem.getProductPrice();
            }
            if (cartItemDTO.isEmpty()) {

                redirectAttributes.addFlashAttribute("successMessage",
                        "장바구니가 비어있습니다.");
            }

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", cartItemDTO);
            model.addAttribute("mid", memberDTO.getMemberId());
            model.addAttribute("list", cartItemDTO);

            return "cart/list";
            //회원 정보가 없으면 상품 목록 페이지로 이동
        } else {
            return "redirect:/product/list";
        }
    }

    //삽입
    @PostMapping("/cart/insert/{id}")
    public String insertProc(@PathVariable Integer id,
                             @RequestParam Integer quantity,
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

        //선택한 수량이 상품의 재고량보다 많을 때 목록 페이지로 이동
        if (productDTO == null || quantity > productDTO.getQuantityCount()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "재고량이 부족합니다.");
            return "redirect:/product/detail/" + id;
        }

        cartService.cartInsert(memberId, productDTO.getProductId(), quantity);

        redirectAttributes.addFlashAttribute("successMessage",
                "장바구니에 저장되었습니다.");

        return "redirect:/cart/list";
    }

    //수정
    @GetMapping("/cart/update")
    public String updateForm(Integer mid, Integer id, Model model) {

        CartDTO cartDTO = cartService.cartDetail(mid);
        CartItemDTO cartItemDTO = cartService.cartItemDetail(id, cartDTO.getCartId());

        model.addAttribute("mid", mid);
        model.addAttribute("cartItem", cartItemDTO);

        return "cart/update";
    }

    @PostMapping("/cart/update")
    public String updateProc(Integer mid, CartItemDTO cartItemDTO,
                             RedirectAttributes redirectAttributes) {
        cartService.cartUpdate(mid, cartItemDTO.getQuantity());

        redirectAttributes.addFlashAttribute("successMessage",
                "장바구니가 수정되었습니다.");

        return "redirect:/cart/list";
    }

    //삭제
    @GetMapping("/cart/delete")
    public String deleteProc(Integer mid, Integer id, RedirectAttributes redirectAttributes) {

        CartDTO cartDTO = cartService.cartDetail(mid);

        cartService.cartDelete(id, cartDTO.getCartId());

        redirectAttributes.addFlashAttribute("successMessage",
                "장바구니 상품이 삭제되었습니다.");

        return "redirect:/cart/list";
    }
}
