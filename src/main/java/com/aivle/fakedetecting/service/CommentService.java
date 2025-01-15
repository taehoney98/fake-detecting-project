package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.dto.RequestComment;
import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.entity.Comment;
import com.aivle.fakedetecting.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    @Transactional
    public Comment postComment(RequestComment requestComment) throws Exception {
        Board board = boardService.findBoardById(requestComment.getBoard_id());
        Comment comment = Comment.toEntity(requestComment);
        comment.setBoard(board);
        return commentRepository.save(comment);
    }
}
