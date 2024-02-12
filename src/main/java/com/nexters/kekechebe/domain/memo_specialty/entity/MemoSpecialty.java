package com.nexters.kekechebe.domain.memo_specialty.entity;

import com.nexters.kekechebe.domain.memo.entity.Memo;
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
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
@Table(name = "memo_specialty")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE memo_specialty SET deleted_at = current_timestamp WHERE id = ?")
public class MemoSpecialty extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id", nullable = false)
    private Memo memo;

    @Builder
    public MemoSpecialty(
            Specialty specialty,
            Memo memo
    ) {
        this.specialty = specialty;
        this.memo = memo;
    }
}