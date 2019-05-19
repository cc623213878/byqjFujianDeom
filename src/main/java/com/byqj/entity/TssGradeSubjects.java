package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @ClassName:TssGradeSubjects
 * @Description:
 * @Author:lwn
 * @Date:2019/3/25 16:55
 **/
@Getter
@Setter
public class TssGradeSubjects {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String kmmc;
    private String kssjhcs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TssGradeSubjects that = (TssGradeSubjects) o;
        return
                Objects.equals(kmmc, that.kmmc) &&
                        Objects.equals(kssjhcs, that.kssjhcs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(kmmc, kssjhcs);
    }
}
