package pers.shawn.rbac.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shawn
 * @create 2020/8/24 11:20
 * @desc
 **/

@Data
@AllArgsConstructor
public class FailureInfo {

    private Integer rowIndex;
    private String reason;

    public static final String REASON_BLANK = "必填字段为空！";
    public static final String REASON_ANALYSIS_EXCEPTION = "解析异常";
    public static final String REASON_EXIST = "账户已存在！";
    public static final String REASON_SQL_BATCH = "当前行开始100条数据批量保存错误！(下标仅供参考)";

}
