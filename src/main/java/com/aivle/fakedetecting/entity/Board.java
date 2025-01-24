package com.aivle.fakedetecting.entity;


import com.aivle.fakedetecting.dto.RequestBoard;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bd_seq")
    private Long id;
    @Column(name = "bd_title")
    private String title;
    @Column(name = "bd_ct")
    private String content;
    @Column(name = "bd_pwd")
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_id")
    @JsonManagedReference
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mb_seq")
    @JsonManagedReference
    private Member member;
    @OneToOne(mappedBy = "board")
    @JsonManagedReference
    private Comment comment;
    @OneToOne(mappedBy = "board")
    @JsonManagedReference
    private Image image;
    public static Board toEntity(RequestBoard requestBoard){
        return Board.builder()
                .title(requestBoard.getTitle())
                .content(requestBoard.getContent())
                .password(requestBoard.getPassword())
                .build();
    }

}
