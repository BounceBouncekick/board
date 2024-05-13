package com.example.board.dto;


import com.example.board.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@ToString
public class BoardDTO {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;


    private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    @Builder
    public BoardDTO(Long id,String boardWriter, String boardPass, String boardTitle, String boardContents, int boardHits) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
    }

    public static BoardDTO toEntity(BoardEntity boardEntity){
            return BoardDTO.builder()
                    .id(boardEntity.getId())
                    .boardWriter(boardEntity.getBoardWriter())
                    .boardPass(boardEntity.getBoardPass())
                    .boardContents(boardEntity.getBoardContents())
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardHits(boardEntity.getBoardHits())
                    .build();
    }


}
