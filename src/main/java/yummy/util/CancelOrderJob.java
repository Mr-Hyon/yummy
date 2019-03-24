package yummy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import yummy.model.MyOrder;
import yummy.service.OrderService;

@DisallowConcurrentExecution
public class CancelOrderJob {

    @Autowired
    OrderService orderService;

    //订单有效时间15分钟
    public static final long EFFTIVE_TIME = 2 * 60 * 1000;
    private Logger logger = Logger.getLogger(CancelOrderJob.class);

    public void execute() throws JobExecutionException {
        System.out.println("失效订单检测任务开始执行!");
        Queue<MyOrder> queue = new LinkedList<>();

        // 在每次启动Job时去数据库查找失效订单,并加入到队列中(从数据库中查询，此处使用假数据)
        List<MyOrder> list = orderService.getUnpaidOrder();

        if (!list.isEmpty()) {
            for (MyOrder o : list) {
                queue.offer(o);
            }
        }
        // 获取队列的头元素,开始检测头订单是否失效
        MyOrder element = queue.peek();
        while (element != null) {
            //时间差值
            Long diff = this.checkOrder(element);
            if (diff != null && diff >= EFFTIVE_TIME) {
                System.out.println("开始关闭订单" + element.getId() + "下单时间" + element.getStartTime());
                orderService.cancelOrder(element);
                // 弹出队列
                queue.poll();
                // 取下一个元素
                element = queue.peek();
            } else if (diff < EFFTIVE_TIME) {
                try {
                    System.out.println("等待检测订单" + element.getId() + "下单时间" + element.getStartTime() + "已下单"
                            + diff / 1000 + "秒");
                    //线程等待
                    Thread.sleep(EFFTIVE_TIME - diff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.info("CancelOrderJob.checkOrder定时任务出现问题");
                }
            }
        }
    }

    /**
     * 获取订单的下单时间和现在的时间差
     */
    public Long checkOrder(MyOrder myOrder) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long diff = null;
        if (myOrder != null) {
            try {
                Date createTime = sdf.parse(myOrder.getStartTime());
                diff = sdf.parse(sdf.format(date)).getTime() - sdf.parse(sdf.format(createTime)).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 返回值为毫秒
        return diff;
    }

}
