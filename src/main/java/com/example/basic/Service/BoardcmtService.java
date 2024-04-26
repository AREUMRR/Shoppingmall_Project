
/*
    작성자 : 정아름
    작성일 : 24.02.21
    수정사항 : 리뷰 댓글이랑 게시판 댓글 repository & service 다시 확인해라!!!!!!!!!!
 */

package com.example.basic.Service;

import com.example.basic.DTO.BoardDTO;
import com.example.basic.DTO.BoardcmtDTO;
import com.example.basic.Entity.BoardEntity;
import com.example.basic.Entity.BoardcmtEntity;
import com.example.basic.Entity.MemberEntity;
import com.example.basic.Repository.BoardRepository;
import com.example.basic.Repository.BoardcmtRepository;
import com.example.basic.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardcmtService {
    private final BoardcmtRepository boardcmtRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    //삽입
    public void boardcmtInsert(BoardcmtDTO boardcmtDTO, Integer boardId, Integer memberId) {

        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow();

        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow();

        BoardcmtEntity boardcmtEntity = modelMapper.map(boardcmtDTO, BoardcmtEntity.class);

        boardcmtEntity.setBoardEntity(boardEntity);
        boardcmtEntity.setMemberEntity(memberEntity);
        boardcmtRepository.save(boardcmtEntity);
    }

    //수정
    public void boardcmtUpdate(BoardcmtDTO boardcmtDTO) {

        MemberEntity member = memberRepository.findById(boardcmtDTO.getMemberId()).orElseThrow();

        BoardEntity boardDTO = boardRepository.findById(boardcmtDTO.getBoardId()).orElseThrow();

        BoardcmtEntity boardcmtEntity = boardcmtRepository.findById(boardcmtDTO.getBoardcmtId()).orElseThrow();

        if (boardcmtEntity != null) {

            BoardcmtEntity boardcmt = modelMapper.map(boardcmtDTO, BoardcmtEntity.class);
            boardcmt.setMemberEntity(member);
            boardcmt.setBoardEntity(boardDTO);
            boardcmtRepository.save(boardcmt);
        }
    }

    //삭제
    public void boardcmtDelete(Integer boardcmtId) {
        boardcmtRepository.deleteById(boardcmtId);
    }

    //전체 조회
    public List<BoardcmtDTO> boardcmtlist(Integer boardId) {
        List<BoardcmtEntity> boardcmtEntities = boardcmtRepository.findByBoardId(boardId);

        List<BoardcmtDTO> boardcmtDTOS = Arrays.asList(modelMapper.
                map(boardcmtEntities, BoardcmtDTO[].class));

        return boardcmtDTOS;
    }

    //개별조회
    public BoardcmtDTO boardcmtDetail(Integer boardcmtId, Integer boardId, Integer memberId) {

        BoardcmtEntity boardcmtEntity = boardcmtRepository.findById(boardcmtId).orElseThrow();

        BoardcmtDTO boardcmtDTO = null;
        if (boardcmtEntity.getMemberEntity().getMemberId().equals(memberId) &&
                boardcmtEntity.getBoardEntity().getBoardId().equals(boardId)) {
            boardcmtDTO = modelMapper.map(boardcmtEntity, BoardcmtDTO.class);
        }

        return boardcmtDTO;
    }
}
