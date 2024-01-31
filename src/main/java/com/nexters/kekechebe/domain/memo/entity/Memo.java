package com.nexters.kekechebe.domain.memo.entity;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.hashtag.entity.Hashtag;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.dto.request.MemoUpdateRequest;
import com.nexters.kekechebe.domain.memo.dto.response.MemoDetail;
import com.nexters.kekechebe.util.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memo")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE memo SET deleted_at = current_timestamp WHERE id = ?")
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Hashtag> hashtags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @Builder
    public Memo(
            String content,
            Member member,
            Character character
    ) {
        this.content = content;
        this.member = member;
        this.character = character;
    }

    public void update(MemoUpdateRequest request) {
        this.content = request.getContent();
    }

    public MemoDetail toMemoDetail() {
        return MemoDetail.builder()
                .id(id)
                .content(content)
                .characterId(character.getId())
                .build();
    }
}
