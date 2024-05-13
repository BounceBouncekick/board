package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        BoardEntity boardEntity = BoardEntity.toDTO(boardDTO);
        boardRepository.save(boardEntity);
    }


    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toEntity(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO updateBoard(BoardDTO boardDTO) {
        boardRepository.updateBoard(boardDTO.getId(),
                                    boardDTO.getBoardWriter(),
                                    boardDTO.getBoardPass(),
                                    boardDTO.getBoardTitle(),
                                    boardDTO.getBoardContents());
        return findById(boardDTO.getId());
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toEntity(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
