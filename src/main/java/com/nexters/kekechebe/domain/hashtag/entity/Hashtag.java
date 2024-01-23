package com.nexters.kekechebe.domain.hashtag.entity;

import com.nexters.kekechebe.util.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hashtag")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE hashtag SET deleted_at = current_timestamp WHERE id = ?")
public class Hashtag extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Hashtag(
            String content
    ) {
        this.content = content;
    }
}