
/*
    설명 : 상품관리의 목록, 수정, 삭제, 조회로 이동하는 페이지 영역
    입력값 : /product/list, /product/insert, /product/update, /product/delete, /product/detail
    출력값 : product/list, product/insert, product/update, product/detail
    작성일 : 24.02.22
    작성자 : 정아름
    수정사항 : 상품관리의 전체 목록 페이지는 list 처리 하기로 함
             상품 등록 시 C:/salad에 사진 들어가는 거 확인함.
 */

package com.example.basic.Controller;

import com.example.basic.DTO.ProductDTO;
import com.example.basic.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    //주입
    private final ProductService productService;

    //카테고리 별 정렬 추가
    //전체 조회
    @GetMapping(value = {"/product/list", "/salad/list", "/product/admin"})
    public String listForm(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                           @RequestParam(value = "type", defaultValue = "") String type,
                           Model model) {

        List<ProductDTO> productDTO = productService.productList(keyword, type);

        model.addAttribute("list", productDTO);

        return "product/list";
    }

    //삽입
    @GetMapping("/product/insert")
    public String insertForm(Model model) {

        //검증처리시 빈 DTO를 전달
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("data", productDTO);

        return "product/insert";
    }

    @PostMapping("/product/insert")
    public String insertProc(@Valid ProductDTO productDTO, @RequestParam MultipartFile imgFile,
                             BindingResult bindingResult) {

        //검증시 오류가 발생하면
        if (bindingResult.hasErrors()) {
            //등록창으로 다시 이동
            return "product/insert";
        }

        productService.productInsert(productDTO, imgFile);

        return "redirect:/product/list";
    }

    //수정
    @GetMapping("/product/update")
    public String updateForm(Integer id, Model model) {
        ProductDTO productDTO = productService.productDetail(id);
        model.addAttribute("data", productDTO);

        return "product/update";
    }

    @PostMapping("/product/update")
    public String updateProc(ProductDTO productDTO, MultipartFile imgFile,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product/update";
        }

        if (productDTO.getQuantityIn() == null) {
            productDTO.setQuantityIn(0);
        }

        productService.productUpdate(productDTO, imgFile);

        return "redirect:/product/list";
    }

    //삭제
    @GetMapping("/product/delete")
    public String deleteProc(Integer id) {
        productService.productDelete(id);

        return "redirect:/product/list";
    }

    //개별 조회
    @GetMapping("/product/detail")
    public String readProc(Integer id, Model model) {
        ProductDTO productDTO = productService.productDetail(id);

        model.addAttribute("data", productDTO);

        return "product/detail";
    }
}
