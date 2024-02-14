package com.nexters.kekechebe.domain.specialty.entity;

import com.nexters.kekechebe.domain.character.dto.response.SpecialtyDetail;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.memo_specialty.entity.MemoSpecialty;
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
@Table(name = "specialty")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE specialty SET deleted_at = current_timestamp WHERE id = ?")
public class Specialty extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "memo_cnt", nullable = false)
    private Integer memoCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @OneToMany(mappedBy = "specialty", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MemoSpecialty> memoSpecialties = new ArrayList<>();

    @Builder
    public Specialty(
            String content,
            Integer memoCnt,
            Character character
    ) {
        this.content = content;
        this.memoCnt = memoCnt;
        this.character = character;
    }

    public void apply() {
        memoCnt += 1;
    }

    public void remove() {
        memoCnt -= 1;
    }

    public SpecialtyDetail toSpecialtyDetail() {
        return SpecialtyDetail.builder()
                .id(id)
                .content(content)
                .memoCnt(memoCnt)
                .build();
    }
}