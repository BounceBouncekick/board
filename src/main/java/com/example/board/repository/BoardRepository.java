package com.example.board.repository;


import com.example.board.entity.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.boardWriter = :boardWriter, b.boardPass = :boardPass, b.boardTitle = :boardTitle, b.boardContents = :boardContents WHERE b.id = :id")
    void updateBoard(@Param("id") Long id,
                     @Param("boardWriter") String boardWriter,
                     @Param("boardPass") String boardPass,
                     @Param("boardTitle") String boardTitle,
                     @Param("boardContents") String boardContents);

    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
