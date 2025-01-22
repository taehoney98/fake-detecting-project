package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Board;
import lombok.*;

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

    public static ResponseBoardPage toDto(Board board){
        return ResponseBoardPage.builder()
                .id(board.getId())
                .title(board.getTitle())
                .user(board.getMember().getNickName())
                .category(board.getCategory().getName())
                .lock(board.getPassword() != null)
                .build();
    }
}
