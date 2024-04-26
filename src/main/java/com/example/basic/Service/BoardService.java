package com.example.basic.Service;

import com.example.basic.DTO.BoardDTO;
import com.example.basic.Entity.BoardEntity;
import com.example.basic.Entity.BoardcmtEntity;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Entity.ReviewEntity;
import com.example.basic.Repository.BoardRepository;
import com.example.basic.Repository.BoardcmtRepository;
import com.example.basic.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardcmtRepository boardcmtRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    //삽입
    public void boardInsert(BoardDTO boardDTO, Integer memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow();

        BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);

        boardEntity.setMemberEntity(memberEntity);
        boardRepository.save(boardEntity);
    }

    //수정
    public void boardUpdate(BoardDTO boardDTO, Integer memberId) {
        Integer id = boardDTO.getBoardId();

        MemberEntity member = memberRepository.findById(memberId).orElseThrow();

        BoardEntity board = boardRepository.findById(id).orElseThrow();

        if (board.getMemberEntity().getMemberId().equals(memberId)) {
            BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);

            boardEntity.setMemberEntity(member);
            boardRepository.save(boardEntity);
        }
    }

    //삭제
    public void boardDelete(Integer boardId) {

        List<BoardcmtEntity> boardcmtEntity = boardcmtRepository.findByBoardId(boardId);

        for (BoardcmtEntity boardcmt : boardcmtEntity) {
            boardcmtRepository.deleteById(boardcmt.getBoardcmtId());
        }

        boardRepository.deleteById(boardId);

    }

    //전체 조회
    public Page<BoardDTO> boardlist(String type, String keyword, Pageable pageable) {
        int cntPage = pageable.getPageNumber() - 1; //현재페이지
        int pageLimit = 10;
        Pageable page = PageRequest.of(cntPage, pageLimit,
                Sort.by(Sort.Direction.DESC, "boardId"));

        Page<BoardEntity> boardEntities;

        if (type.equals("s") && keyword != null) {
            //분류가 제목에 검색어가 존재하면
            boardEntities = boardRepository.findByBoardSubjectContaining(keyword, page);
        } else if (type.equals("c") && keyword != null) {
            //분류가 내용에 검색어가 존재하면
            boardEntities = boardRepository.findByBoardContentContaining(keyword, page);
        } else if (type.equals("w") && keyword != null) {
            //분류가 작성자에 검색어가 존재하면
            boardEntities = boardRepository.findByBoardWriterContaining(keyword, page);
        } else if (type.equals("sc") && keyword != null) {
            //분류가 제목과 내용에 검색어가 존재하면
            boardEntities = boardRepository.findByBoardSCContaining(keyword, page);
        } else {
            //분류 및 검색어가 없는 경우
            boardEntities = boardRepository.findAll(page);
        }

        Page<BoardDTO> boardDTOS = boardEntities.map(
                data -> modelMapper.map(data, BoardDTO.class));

        return boardDTOS;
    }

    //개별 조회
    public BoardDTO boardDetail(Integer boardId, Integer memberId) {

        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow();

        BoardDTO boardDTO = null;
        if (boardEntity.getMemberEntity().getMemberId().equals(memberId)) {
            boardDTO = modelMapper.map(boardEntity, BoardDTO.class);
        }

        return boardDTO;
    }

    //회원의 게시글 조회
    public List<BoardDTO> memberBoard(Integer memberId) {
        List<BoardEntity> boardEntities = boardRepository.findByMemberId(memberId);
        List<BoardDTO> boardDTOS = Arrays.asList(modelMapper.map(boardEntities, BoardDTO[].class));

        return boardDTOS;
    }

}