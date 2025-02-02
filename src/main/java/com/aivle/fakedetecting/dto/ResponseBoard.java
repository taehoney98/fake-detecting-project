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
    private Long memberId;
    private String memberName;
    private LocalDateTime createAt;
    private String comment;
    private String imageUrl;
    public static ResponseBoard toDto(Board board){
        return ResponseBoard.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .categoryName(board.getCategory().getName())
                .memberName(board.getMember().getNickName())
                .createAt(board.getCreateDate())
                .memberId(board.getMember().getSeq())
                .imageUrl(board.getImage() != null ? board.getImage().getName() : null)
                .build();
    }
}
