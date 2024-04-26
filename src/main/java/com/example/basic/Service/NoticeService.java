package com.example.basic.Service;

import com.example.basic.DTO.NoticeDTO;
import com.example.basic.Entity.NoticeEntity;
import com.example.basic.Repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    //삽입
    public void noticeInsert(NoticeDTO noticeDTO) {
        noticeDTO.setNoticeCount(0);
        NoticeEntity noticeEntity = modelMapper.map(noticeDTO, NoticeEntity.class);

        noticeRepository.save(noticeEntity);
    }

    //수정
    public void noticeUpdate(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = modelMapper.map(noticeDTO, NoticeEntity.class);

        noticeRepository.save(noticeEntity);
    }

    //삭제
    public void noticeDelete(Integer noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    //전체 조회
    public Page<NoticeDTO> noticeList(Pageable pageable) {
        int cntPage = pageable.getPageNumber()-1; //현재페이지
        int pageLimit = 10;
        Pageable page = PageRequest.of(cntPage, pageLimit,
                Sort.by(Sort.Direction.DESC,"noticeId"));

        Page<NoticeEntity> noticeEntities= noticeRepository.findAll(page);

        Page<NoticeDTO> noticeDTOS = noticeEntities.map(
                data->modelMapper.map(data, NoticeDTO.class));

        return noticeDTOS;
    }

    //개별 조회
    @Transactional
    public NoticeDTO noticeDetail(Integer noticeId, String pandan) {
        //조회수 증가
        if (pandan.equals("R")) {
            noticeRepository.noticeCount(noticeId);
        }

        Optional<NoticeEntity> noticeEntity = noticeRepository.findById(noticeId);
        NoticeDTO noticeDTO = modelMapper.map(noticeEntity, NoticeDTO.class);

        return noticeDTO;
    }
}
