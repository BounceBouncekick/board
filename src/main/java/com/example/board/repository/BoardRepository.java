package com.example.board.repository;


import com.example.board.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findByUuid(String uuid);

    @Modifying
    @Query("UPDATE Board b SET b.body = :body, b.title = :title, b.imageUrls = :imageUrls, b.updatedTime = current_timestamp WHERE b.uuid = :uuid")
    void updateBoardByUUID(@Param("uuid") String uuid, @Param("body") String body,
                           @Param("title") String title, @Param("imageUrls") String imageUrls);
}

//    @Modifying
//    @Transactional
//    @Query("UPDATE BoardEntity b SET b.boardWriter = :boardWriter, b.boardPass = :boardPass, b.boardTitle = :boardTitle, b.boardContents = :boardContents WHERE b.id = :id")
//    void updateBoard(@Param("id") Long id,
//                     @Param("boardWriter") String boardWriter,
//                     @Param("boardPass") String boardPass,
//                     @Param("boardTitle") String boardTitle,
//                     @Param("boardContents") String boardContents);
//
//    @Modifying
//    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
//    void updateHits(@Param("id") Long id);
