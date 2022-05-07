package example.schedule.ConfigurerSchedule;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class ConfigurerSchedulingTest implements InitializingBean {
    /**
     * 目的：初始化bean，且立即执行某个方法。
     * 方式：implement InitializingBean，重写afterPropertiesSet方法。
     */
    @Autowired
    ConfigurerScheduling configurerScheduling;

    // 创建两个线程 定死的 ，一个线程，另一个线程定时查询任务是否更新？

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread() {
            /**
             * 一个线程 正常执行定时任务
             */
            public void run() {

                try {
                    // 等待任务调度初始化完成
                    while (!configurerScheduling.inited()) {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务调度初始化完成，添加任务");
                configurerScheduling.addTriggerTask("task", new TriggerTask(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("run job1..." + Calendar.getInstance().get(Calendar.SECOND));
                    }
                }
                        , new CronTrigger("0/20 * * * * ? ")));
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                }
                System.out.println("添加监视任务............");
                configurerScheduling.addTriggerTask("task1", new TriggerTask(new Runnable() {
                    @Override
                    public void run() {
                        // 数据库cron是否更新，更新的话，reset
                        System.out.println("run watch-job..." + Calendar.getInstance().get(Calendar.SECOND));

                        configurerScheduling.resetTriggerTask("task", new TriggerTask(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("run reset-job1..." + Calendar.getInstance().get(Calendar.SECOND));

                            }
                        }, new CronTrigger("0/2 * * * * ? ")));
                    }
                }, new CronTrigger("0/40 * * * * ? ")));
            }

            ;
        }.start();
    }
}


