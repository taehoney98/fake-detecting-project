package com.aivle.fakedetecting.entity;

import com.aivle.fakedetecting.dto.RequestComment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_comment")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "comment_content")
    private String content;
    @OneToOne
    @JoinColumn(name = "bd_seq")
    @JsonBackReference
    private Board board;

    public static Comment toEntity(RequestComment requestComment){
        return Comment.builder()
                .content(requestComment.getContent())
                .build();
    }
}
