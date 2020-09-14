package pers.shawn.rbac.module.rbac.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.shawn.rbac.bean.ExcelResultInfo;
import pers.shawn.rbac.bean.FailureInfo;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.service.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * @create 2020/8/21 16:56
 * @desc EasyExcel批量录入谷歌账号使用的监听器
 **/
public class UserListener extends AnalysisEventListener<User> {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    /**
     * 每次提交到数据库的条数
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 批量录入的最终结果
     */
    private ExcelResultInfo resultInfo = new ExcelResultInfo();
    /**
     * 临时存储待入库的对象集合
     */
    List<User> list = new ArrayList<>();

    private IUserService iUserService;

    public UserListener(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        logger.info("解析到数据:{}", user.toString());
        Integer i = analysisContext.readRowHolder().getRowIndex();
        if (verifyData(user, i)) {
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            list.add(user);
            /**
             * 达到一定数量便提交到数据库
             * 避免集合占用内存过大
             */
            if (list.size() >= BATCH_COUNT) {
                /**
                 * 2020/8/24 16:28 shawn
                 * TODO: 发生解析异常时，这里的下标就不准了
                 * 记录解析异常的行数，这里相减时清零重新记录
                 */
                i -= BATCH_COUNT;
                saveData(i);
                list.clear();
            }
        }

    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        logger.error("解析失败, 继续解析下一行:{}", exception.getMessage());
        Integer i = context.readRowHolder().getRowIndex();
        FailureInfo info = new FailureInfo(i, FailureInfo.REASON_ANALYSIS_EXCEPTION);
        resultInfo.setTotalFailure(1);
        resultInfo.getFailureInfoList().add(info);
//        logger.error("第{}行解析异常", i);
    }

    /**
     * @Author shawn
     * @Description 数据全部读取完成后，list集合中的数据可能没有达到一定数量，需要在这里提交到数据库
     * @Date 16:29 2020/8/24
     * @Param [analysisContext]
     * @return void
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        Integer i = analysisContext.readRowHolder().getRowIndex() - list.size();
        saveData(i);
        logger.info("数据解析完成");
    }

    /**
     * @Author shawn
     * @Description 批量保存数据到数据库，并对异常进行捕获
     * @Date 16:34 2020/8/24
     * @Param [i]
     * @return void
     */
    private void saveData(Integer i) {
        logger.info("{}条数据，开始存储到数据库！", list.size());
        try {
            iUserService.saveBatch(list);
            resultInfo.setTotalSuccess(list.size());
        } catch (Exception e) {
            logger.error("批量保存数据错误", e);
            FailureInfo info = new FailureInfo(i, FailureInfo.REASON_SQL_BATCH);
            resultInfo.setTotalFailure(list.size());
            resultInfo.getFailureInfoList().add(info);
        }
        logger.info("存储数据库成功！");
    }

    /**
     * 校验数据合法性且账户未被记录
     * @param user
     * @return
     */
    private boolean verifyData(User user, Integer i) {
//        if (StringUtils.isBlank(user.getAccount())) {
//            FailureInfo info = new FailureInfo(i, FailureInfo.REASON_BLANK);
//            resultInfo.setTotalFailure(1);
//            resultInfo.getFailureInfoList().add(info);
//            return false;
//        }
//        if (StringUtils.isBlank(googleAccount.getPassword())) {
//            FailureInfo info = new FailureInfo(i, FailureInfo.REASON_BLANK);
//            resultInfo.setTotalFailure(1);
//            resultInfo.getFailureInfoList().add(info);
//            return false;
//        }
//        if (StringUtils.isBlank(googleAccount.getEmail())) {
//            FailureInfo info = new FailureInfo(i, FailureInfo.REASON_BLANK);
//            resultInfo.setTotalFailure(1);
//            resultInfo.getFailureInfoList().add(info);
//            return false;
//        }
//        if (googleAccount.getStatus() == null) {
//            FailureInfo info = new FailureInfo(i, FailureInfo.REASON_BLANK);
//            resultInfo.setTotalFailure(1);
//            resultInfo.getFailureInfoList().add(info);
//            return false;
//        }
//        boolean b = iAccountGoogleService.existAccountGoogle(googleAccount.getAccount());
//        if (b) {
//            FailureInfo info = new FailureInfo(i, FailureInfo.REASON_EXIST);
//            resultInfo.setTotalFailure(1);
//            resultInfo.getFailureInfoList().add(info);
//            return false;
//        }
        return true;
    }

    public ExcelResultInfo getInfo() {
        return resultInfo;
    }
}
