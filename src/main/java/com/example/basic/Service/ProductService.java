package com.example.basic.Service;

import com.example.basic.DTO.ProductDTO;
import com.example.basic.Entity.ProductEntity;
import com.example.basic.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    @Value("${imgUploadLocation}")
    private String imgUploadLocation;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    //삽입
    public void productInsert(ProductDTO productDTO, MultipartFile imgFile) {
        //출고량=0
        productDTO.setQuantity(0);
        //재고량=입고량
        productDTO.setQuantityCount(productDTO.getQuantityIn());

        String originalFileName = imgFile.getOriginalFilename(); //파일명 추출
        String newFileName = ""; //파일저장후 난수로 만든 파일명
        try {
            if (originalFileName != null) { //파일이 존재하면
                //파일을 해당위치에 난수로 이름을 지정해서 저장
                newFileName = fileService.uploadFile(imgUploadLocation,
                        originalFileName, imgFile.getBytes());
            }
        } catch (Exception e) {
            ;
        }

        productDTO.setProductImage(newFileName); //새로운 파일명을 등록

        //변환
        ProductEntity productEntity = modelMapper.map(productDTO,
                ProductEntity.class);

        productRepository.save(productEntity);
    }

    //수정
    public void productUpdate(ProductDTO productDTO, MultipartFile imgFile) {

        Optional<ProductEntity> productEntity = productRepository
                .findById(productDTO.getProductId()); //수정할 레코드를 조회

        String deleteFile = productEntity.get().getProductImage(); //저장된 파일명을 읽기

        String originalFileName = imgFile.getOriginalFilename();
        String newFileName;
        try {
            if (originalFileName.length() != 0) { //null, 새로운 파일이 추가되었으면
                if (deleteFile.length() != 0) { //기존에 저장된 파일이 존재하면
                    fileService.deleteFile(imgUploadLocation, deleteFile); //기존파일삭제
                }
                //새로운 파일 추가
                newFileName = fileService.uploadFile(imgUploadLocation, originalFileName,
                        imgFile.getBytes());
                productDTO.setProductImage(newFileName);
            }
        } catch (Exception e) {
            ;
        }

        productDTO.setQuantity(0);
        //재고 수량 업데이트
        Integer count = productEntity.get().getQuantityCount();
        //재고량 + 추가 입고량
        productDTO.setQuantityCount(productDTO.getQuantityIn() + count);
        //추가 입고량이 없을 경우
        Integer quantity = productEntity.get().getQuantityIn();
        productDTO.setQuantityIn(quantity);

        //데이터베이스 저장
        ProductEntity product = modelMapper.map(productDTO,
                ProductEntity.class);
        productRepository.save(product);
    }

    //삭제
    public void productDelete(Integer productId) {
        //상품 조회(하디드스크에 저장된 이미지 삭제)
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();
        //읽어온 레코드에서 파일명을 읽는다.
        String delFile = productEntity.getProductImage();
        //저장된 경로와 파일명을 전달하여 파일을 삭제
        fileService.deleteFile(imgUploadLocation, delFile);

        productRepository.deleteById(productId);
    }

    //전체 조회
    public List<ProductDTO> productList(String keyword, String type) {

        List<ProductEntity> productEntities;

        if (keyword != null) {
            //검색어가 존재하면 상품명에서 검색
            productEntities = productRepository.findByProductNameContaining(keyword);
            //검색어도 없으면
        } else {
            productEntities = productRepository.findAll();
        }

        if (type.equals("n")) {
            productEntities = productRepository.findProductEntitiesBy();
        }

        List<ProductDTO> productDTOS = Arrays.
                asList(modelMapper.map(productEntities, ProductDTO[].class));

        return productDTOS;
    }

    //개별 조회
    public ProductDTO productDetail(Integer productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        ProductDTO productDTO = modelMapper.map(productEntity, ProductDTO.class);
        return productDTO;
    }
}
