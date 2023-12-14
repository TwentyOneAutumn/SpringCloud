package com.executor.config;

import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Job执行处理器
 * 负责执行Job并处理日志信息
 */
public class JobHandler {

    /**
     * 执行Job
     * @param jobName 任务名称
     * @param jobEntity 任务对象
     */
    public void execute(String jobName, JobEntity jobEntity){
        // 初始化
        JobExecuteLog jobExecuteLog = JobExecuteLog.init(jobName,JobExecuteLog.initClassName(jobEntity.getClass().getName()));
        try {
            jobEntity.outWork(jobExecuteLog.getJobParam());
        } catch (Exception exception){
            jobExecuteLog.error(exception);
        }
        jobExecuteLog.end();
    }

    /**
     * Job日志
     */
    @Data
    private static class JobExecuteLog{

        /**
         * 任务名称
         */
        private String jobName;

        /**
         * 任务参数
         */
        private String jobParam;

        /**
         * 任务开始时间
         */
        private LocalDateTime startTime;

        /**
         * 任务结束时间
         */
        private LocalDateTime endTime;

        /**
         * 任务用时
         */
        private String takeTime;

        /**
         * 是否异常
         */
        private boolean isError = false;

        /**
         * 异常信息
         */
        private String errorMsg;

        /**
         * 作业类名称
         */
        private String jobClassName;

        /**
         * 私有化构造方法
         */
        private JobExecuteLog(){}

        private JobExecuteLog(String jobName, String jobParam, LocalDateTime startTime, String jobClassName){
            this.jobName = jobName;
            this.jobParam = jobParam;
            this.startTime = startTime;
            this.jobClassName = jobClassName;
        }

        /**
         * 初始化
         * @param jobName 任务名称
         */
        public static JobExecuteLog init(String jobName,String jobClassName){
            return new JobExecuteLog(jobName, XxlJobHelper.getJobParam(),LocalDateTime.now(),jobClassName);
        }

        /**
         * 结束
         */
        public void end(){
            if(!this.isError){
                initTakeTime();
                StringBuilder builder = new StringBuilder();
                builder.append("任务名称:").append(this.jobName).append("<br/>");
                if(StrUtil.isEmpty(this.jobParam)){
                    this.jobParam = "无";
                }
                builder.append("任务参数:").append(this.jobParam).append("<br/>");
                builder.append("任务开始时间:").append(this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("<br/>");
                builder.append("任务结束时间:").append(this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("<br/>");
                builder.append("任务用时:").append(this.takeTime).append("<br/>");
                builder.append("任务状态:成功");
                String log = builder.toString();
                XxlJobHelper.log(log);
                XxlJobHelper.handleSuccess(log);
            }
        }

        /**
         * 异常
         * @param exception 异常对象
         */
        public void error(Exception exception){
            this.isError = true;
            this.errorMsg = exception.getMessage();
            StringBuilder builder = new StringBuilder();
            builder.append("任务名称:").append(this.jobName).append("<br/>");
            if(StrUtil.isEmpty(this.jobParam)){
                this.jobParam = "无";
            }
            builder.append("任务参数:").append(this.jobParam).append("<br/>");
            builder.append("任务开始时间:").append(this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("<br/>");
            builder.append("任务状态:异常").append("<br/>");
            // 获取异常的堆栈信息
            StackTraceElement[] stackTraceElements = exception.getStackTrace();
            // 输出堆栈信息
            for (StackTraceElement element : stackTraceElements) {
                String className = initClassName(element.getClassName());
                if(className.equals(this.jobClassName)){
                    String errorPosition = className + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
                    builder.append("异常位置:").append(errorPosition).append("<br/>");
                    break;
                }
            }
            builder.append("异常类型:").append(exception.getClass().getName()).append("<br/>");
            builder.append("异常信息:").append(this.errorMsg);
            String log = builder.toString();
            XxlJobHelper.log(log);
            XxlJobHelper.handleFail(log);
        }

        /**
         * 初始化TakeTime
         */
        private void initTakeTime(){
            this.endTime = LocalDateTime.now();
            int seconds = (int) Duration.between(this.startTime, this.endTime).getSeconds();
            if(seconds < 1){
                seconds = 1;
            }
            if(seconds < 60){
                this.takeTime = seconds + "秒";
                return;
            }
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if(minutes < 60){
                this.takeTime = minutes + "分钟" + seconds + "秒";
                return;
            }
            int hours = minutes / 60;
            minutes = minutes % 60;
            this.takeTime = hours + "小时" + minutes + "分钟" + seconds + "秒";
        }

        /**
         * 初始化类名
         * @param className 类名
         * @return 处理后的类名
         */
        private static String initClassName(String className){
            int indexOf = className.indexOf("$");
            if(indexOf != -1){
                className = className.substring(0, indexOf);
            }
            return className;
        }
    }
}