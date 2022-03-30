package com.github.mini.common.result;

import com.github.mini.common.code.ResultCode;
import com.github.mini.common.enums.CommonCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @className: PageResult
 * @description: 分页结果类
 * @author: Hanson
 * @version: V1.0
 * @create: 2020-01-28 15:31
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PageResult extends BaseResult {
    private Long total;
    private List<?> rows;

    public PageResult(ResultCode codeEnum) {
        super(codeEnum);
    }

    public PageResult(ResultCode codeEnum, Long total, List<?> rows) {
        super(codeEnum);
        this.total = total;
        this.rows = rows;
    }

    public static PageResult ok(Long total, List<?> rows) {
        return new PageResult(CommonCodeEnum.SUCCESS, total, rows);
    }


}
