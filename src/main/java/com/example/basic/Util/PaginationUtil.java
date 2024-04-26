package com.example.basic.Util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaginationUtil {
    //페이지 정보를 가져와서 세부 페이지 값을 생성
    public static Map<String, Integer> Pagination(Page<?> page) {
        Map<String, Integer> paginationMap = new HashMap<>();

        int currentPage = page.getNumber()+1; //현재 페이지
        int totalPages = page.getTotalPages(); //전체 페이지 수
        int blockLimit = Math.min(5, totalPages); //한 화면에 출력되는 페이지 번호 개수

        int startPage = Math.max(1, currentPage-blockLimit/2);
        System.out.println(startPage);
        int endPage = Math.min(startPage + blockLimit-1, totalPages);
        System.out.println(endPage);
        //이전페이지, 다음페이지의 번호
        int prevPage = Math.max(1, currentPage-1);
        int nextPage = Math.min(totalPages, currentPage+1);
        //마지막페이지
        int lastPage = totalPages;
        //Map(키값(변수명), 값)
        paginationMap.put("startPage", startPage);
        paginationMap.put("endPage", endPage);
        paginationMap.put("prevPage", prevPage);
        paginationMap.put("nextPage", nextPage);
        paginationMap.put("lastPage", lastPage);
        paginationMap.put("currentPage", currentPage);

        return paginationMap;
    }
}
