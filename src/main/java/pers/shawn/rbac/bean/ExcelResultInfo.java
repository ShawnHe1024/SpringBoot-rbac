package pers.shawn.rbac.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * @create 2020/8/24 11:07
 * @desc Excel批量数据录入处理结果
 **/
@Data
public class ExcelResultInfo {

    public ExcelResultInfo() {
        this.totalSuccess = 0;
        this.totalFailure = 0;
        this.failureInfoList = new ArrayList<>();
    }

    private Integer totalSuccess;

    private Integer totalFailure;

    private List<FailureInfo> failureInfoList;

    /**
     * 在原数据基础上增加而不是替换
     * @param totalSuccess
     */
    public void setTotalSuccess(Integer totalSuccess) {
        this.totalSuccess += totalSuccess;
    }

    /**
     * 在原数据基础上增加而不是替换
     * @param totalFailure
     */
    public void setTotalFailure(Integer totalFailure) {
        this.totalFailure += totalFailure;
    }
}
