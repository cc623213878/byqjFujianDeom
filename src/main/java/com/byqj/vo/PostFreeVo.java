package com.byqj.vo;

import lombok.Data;

import java.util.Objects;

@Data
public class PostFreeVo {
    private String postId;
    private String postName;
    private Double postFree = 0.0D;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostFreeVo that = (PostFreeVo) o;
        return Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }
}
