package com.byqj.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by willim on 2019/4/4.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddPostVo {
    private String postId;
    private Integer num;

    @Override
    public boolean equals(Object o) {
        if (o instanceof AddPostVo) {
            AddPostVo temp = (AddPostVo) o;
            return this.postId.equals(temp.getPostId());
        }
        return false;
    }
}
