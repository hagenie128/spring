package com.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false,length = 50)
    @NotBlank(message = "이름을 반드시 입력하세요")
    private String name;

    @NonNull
    @Column(nullable = false,length = 100,unique = true)
    @NotBlank(message = "이메일을 반드시 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NonNull
    @Column(nullable = false,length = 100)
    private String phone;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    /**
     * 회원은 여러 주문을 할 수 있습니다.
     * order 테이블에 member_id 컬럼이 있습니다.
     */
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders = new ArrayList<Order>();
}
