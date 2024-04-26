package com.example.basic.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    //등록일
    @Column(name = "regDate", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime regDate;

    //수정일
    @Column(name = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate;
}
