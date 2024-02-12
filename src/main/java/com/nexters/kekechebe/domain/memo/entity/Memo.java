package com.nexters.kekechebe.domain.memo.entity;

import com.nexters.kekechebe.domain.character.dto.response.SpecialtyDetail;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.dto.CharacterDetail;
import com.nexters.kekechebe.domain.memo.dto.response.MemoDetail;
import com.nexters.kekechebe.domain.memo_specialty.entity.MemoSpecialty;
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
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

    @Column(name = "is_modified")
    private Boolean isModified = false;

    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MemoSpecialty> memoSpecialties = new ArrayList<>();

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

    public void updateContent(String content) {
        this.content = content;
        this.isModified = true;
    }

    public MemoDetail toMemoDetail() {
        return MemoDetail.builder()
                .id(id)
                .content(content)
                .character(CharacterDetail.builder()
                        .id(character.getId())
                        .name(character.getName())
                        .build())
                .specialties(toSpecialtyDetail())
                .isModified(isModified)
                .createdAt(getCreatedAt())
                .build();
    }

    private List<SpecialtyDetail> toSpecialtyDetail() {
        return memoSpecialties.stream()
                .map(MemoSpecialty::getSpecialty)
                .map(Specialty::toSpecialtyDetail)
                .toList();
    }
}
