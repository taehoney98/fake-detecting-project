package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseBoard {
    private Long id;
    private String title;
    private String content;
    private String categoryName;
    private String memberName;
    private LocalDateTime createAt;
    private String comment;
    public static ResponseBoard toDto(Board board){
        return ResponseBoard.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .categoryName(board.getCategory().getName())
                .memberName(board.getMember().getName()) // 닉네임, 이름 뭘로??
                .createAt(board.getCreateDate())
                .build();
    }
}
