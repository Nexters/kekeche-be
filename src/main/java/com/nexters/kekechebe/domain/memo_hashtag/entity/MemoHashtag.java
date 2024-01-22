package com.nexters.kekechebe.domain.memo_hashtag.entity;

import com.nexters.kekechebe.domain.hashtag.entity.Hashtag;
import com.nexters.kekechebe.domain.memo.entity.Memo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memo_hashtag")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE memo_hashtag SET deleted_at = current_timestamp WHERE id = ?")
public class MemoHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id", nullable = false)
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;
}