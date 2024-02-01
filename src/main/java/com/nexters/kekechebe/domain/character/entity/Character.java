package com.nexters.kekechebe.domain.character.entity;

import com.nexters.kekechebe.domain.character.enums.CharacterAsset;
import com.nexters.kekechebe.domain.character.enums.Item;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.entity.Memo;
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
@Table(name = "\"character\"")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE `character` SET deleted_at = current_timestamp WHERE id = ?")
public class Character extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "exp", nullable = false)
    private int exp;

    @Column(name = "variation", nullable = false)
    @Enumerated
    private CharacterAsset.Variation variation;

    @Column(name = "shape", nullable = false)
    @Enumerated
    private CharacterAsset.Shape shape;

    @Column(name = "color", nullable = false)
    @Enumerated
    private CharacterAsset.Color color;

    @Column(name = "item", nullable = false)
    @Enumerated
    private Item item;

    @Column(name = "keyword")
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "character", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Memo> memos = new ArrayList<>();

    @Builder
    public Character(
            String name,
            int level,
            int exp,
            CharacterAsset.Variation variation,
            CharacterAsset.Shape shape,
            CharacterAsset.Color color,
            Item item,
            String keyword,
            Member member
    ) {
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.variation = variation;
        this.shape = shape;
        this.color = color;
        this.item = item;
        this.keyword = keyword;
        this.member = member;
    }

    public void updateName(String name) {
        this.name = name;
    }
}