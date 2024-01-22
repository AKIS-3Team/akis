package com.example.akis.boundedContext.member.entity;

import com.example.akis.base.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString // 디버그를 위한
@Entity
@Getter
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String nickname;
    private String password;

    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // 모든 멤버는 member 권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        // username이 admin인 회원은 추가로 admin 권한도 가진다.
        if ("admin".equals(username)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }

}
