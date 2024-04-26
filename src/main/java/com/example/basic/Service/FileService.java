package com.example.basic.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
@Service
public class FileService {
    //파일 업로드
    //uploadFile(저장위치, 파일명(a.jpg), 파일데이터)--->새로운파일명(1236546.jpg)
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData){
        //파일 저장 경로 설정
        File uploadPathDirectory = new File(uploadPath);
        if (!uploadPathDirectory.exists()) {
            uploadPathDirectory.mkdirs();
        }

        UUID uuid = UUID.randomUUID(); //난수로 이름을 생성
        //abc.jpg===>jpg 분리(이미지파일의 확장자 분리)
        String extension = originalFileName.substring(
                originalFileName.lastIndexOf("."));
        //45651233.jpg =>난수파일명에 확장자를 결합해서 새로운 파일명을 만들기(전달값)
        String saveFileName = uuid + extension;
        //c:salad/product/45651233.jpg 저장할 위치와 파일명을 결합(작업처리)
        String uploadFullUrl = uploadPath + saveFileName;

        //하드디스크에 파일 저장
        try {
            //FileOutputStream 객체를 이용하여
            //c:salad/product/45651233.jpg 경로 지정 후 파일을 저장
            FileOutputStream fos = new FileOutputStream(uploadFullUrl);
            //파일을 저장
            fos.write(fileData);
            fos.close();
        } catch(Exception e) {
        }

        return saveFileName;
    }

    //파일 삭제처리
    public void deleteFile(String uploadPath, String fileName) {
        //경로+파일명
        String deleteFileName = uploadPath + fileName;

        File deleteFile = new File(deleteFileName);
        if(deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
