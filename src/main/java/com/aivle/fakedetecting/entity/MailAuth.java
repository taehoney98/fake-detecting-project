package com.aivle.fakedetecting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_mail_auth")
public class MailAuth extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_seq")
    private Long id;
    @Column(name = "mail_code")
    private Integer mailCode;

//    @OneToOne
//    @JoinColumn(name = "mb_seq")
//    private Member member;
}
