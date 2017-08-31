package com.iqes.repository.queue;

import com.iqes.entity.QueueInfo;
import org.springframework.data.repository.CrudRepository;
/**
 *  排队信息管理Dao层
 * Created by jiangdongdong.tp on 2017/2/6.
 */
public interface QueueQueryDao extends CrudRepository<QueueInfo,Long> {
    /**
     * 服务名称:虚拟排队<br>
     * 执行逻辑：<br>
     * 1、添加顾客虚拟排队的记录<br>
     * @since 1.0 2017-08-30<br>
     * @author jaingdongdong<br>
     * @param queueInfo 查询指令<br>
     */
    public QueueInfo save(QueueInfo queueInfo);


}
