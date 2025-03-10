package com.aivle.fakedetecting.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board_image")
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_image_seq")
    private Long id;
    @Column(name = "board_image_name")
    private String name;
    @Column(name = "board_image_url")
    private String url;
    @OneToOne
    @JoinColumn(name = "bd_seq")
    @JsonBackReference
    private Board board;
}
