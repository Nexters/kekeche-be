package com.nexters.kekechebe.domain.memo.dto.response;

import com.nexters.kekechebe.domain.memo.entity.Memo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class MemoPage {
    private List<MemoDetail> memos;

    private int totalPages;

    private long totalCount;

    private int page;

    private int size;

    private boolean hasNext;

    private boolean hasPrevious;

    private Boolean isFirstPage;

    private Boolean isLastPage;

    public static MemoPage from(Page<Memo> memos) {
        List<MemoDetail> memoDetails = memos.getContent().stream()
                .map(Memo::toMemoDetail)
                .toList();

        return MemoPage.builder()
                .memos(memoDetails)
                .totalPages(memos.getTotalPages())
                .totalCount(memos.getTotalElements())
                .page(memos.getNumber())
                .size(memos.getSize())
                .hasNext(memos.hasNext())
                .hasPrevious(memos.hasPrevious())
                .isFirstPage(memos.isFirst())
                .isLastPage(memos.isLast())
                .build();
    }
}