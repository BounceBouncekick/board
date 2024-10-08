package com.example.board.service;

import com.example.board.enumclass.BoardCategory;
import com.example.board.dto.BoardCreateRequest;
import com.example.board.dto.BoardDetailResponse;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final AwsS3Service awsS3Service;

    public BoardService(BoardRepository boardRepository,UserRepository userRepository,AwsS3Service awsS3Service) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.awsS3Service = awsS3Service;
    }

    @Transactional
    public Board writeBoard(BoardCreateRequest req, BoardCategory category)throws IOException{

        Board board = req.toEntity(category);

        // 이미지 업로드
        List<MultipartFile> imageFiles = new ArrayList<>();
        if (req.getUploadImage() != null) {
            imageFiles.addAll(Arrays.asList(req.getUploadImage()));
        }

        List<String> uploadedImageUrls = awsS3Service.uploadFile(imageFiles);

        // 업로드된 이미지 URL을 Board 엔티티에 설정
        for (String imageUrl : uploadedImageUrls) {
            // 이미지가 여러 개일 경우를 대비해 반복문을 사용하여 각 이미지 URL을 Board 엔티티에 추가합니다.
            board.addImageUrl(imageUrl);
        }

        // 게시글 저장
        return boardRepository.save(board);


//      Board board = req.toEntity(category);
//      Board savedBoard = boardRepository.save(board);

    }

    @Transactional
    public void delete(String uuid) {
        Optional<Board> optionaluuid = boardRepository.findByUuid(uuid);

        if (optionaluuid.isPresent()) {
            Board boardDelete = optionaluuid.get();
            boardRepository.delete(boardDelete);
        } else {
            throw new IllegalArgumentException("해당 UUID의 게시글을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void update(String uuid,BoardCreateRequest req,MultipartFile imageFile)throws IOException{
        Optional<Board> optionaluuid = boardRepository.findByUuid(uuid);

        log.info("이미지파일 수정서비스 :{}",imageFile);
        if(optionaluuid.isPresent()){
            Board boardUpdate = optionaluuid.get();
            String imageUrls = awsS3Service.uploadFile(imageFile);
            boardRepository.updateBoardByUUID(uuid, req.getBody(), req.getTitle(),imageUrls);
        }
    }

    public List<BoardCreateRequest> findAll() {
        List<Board> boardEntityList = boardRepository.findAll();
        List<BoardCreateRequest> boardDTOList = boardEntityList.stream()
                .map(board -> BoardCreateRequest.builder()
                        .title(board.getTitle())
                        .body(board.getBody())
                        .imageUrls(board.getImageUrls()) // 이미지 URL 리스트 추가
                        .uuid(board.getUuid()) // uuid 추가
                        .build())
                .collect(Collectors.toList());

        return boardDTOList;
    }

    public BoardDetailResponse getBoardDetail(String uuid) {
        Optional<Board> boardOptional = boardRepository.findByUuid(uuid);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            return BoardDetailResponse.fromEntity(board);
        } else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다. UUID: " + uuid);
        }
    }
}
