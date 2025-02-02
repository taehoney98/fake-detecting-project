package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseBoardPage {
    private Long id;
    private String title;
    private String user;
    private String category;
    private boolean lock;
    private LocalDateTime createdDate;
    private boolean answer;

    public static ResponseBoardPage toDto(Board board){
        return ResponseBoardPage.builder()
                .id(board.getId())
                .title(board.getTitle())
                .user(board.getMember().getNickName())
                .category(board.getCategory().getName())
                .lock(board.getPassword() != null)
                .createdDate(board.getCreateDate())
                .answer(board.getComment() != null)
                .build();
    }
}
