package com.core.ssh;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.AuthFuture;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;

import java.io.ByteArrayOutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SSHClient {

    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * 远程主机地址
     */
    private final String host;

    /**
     * 端口号
     */
    private final int port;

    private SSHClient(String username, String password, String host, int port){
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public static SSHClient builder(String username, String password, String host, int port) {
        return new SSHClient(username,password,host,port);
    }

    public static SSHClient builder(String username, String password, String host) {
        return new SSHClient(username,password,host,22);
    }

    /**
     * 执行脚本
     * @param shellPath 远程主机上要执行的脚本路径
     */
    public void channelCmd(String command){
        SshClient client = null;
        ClientSession session = null;
        try {
            // 创建客户端
            client = SshClient.setUpDefaultClient();
            // 开始创建会话
            client.start();
            // 连接
            ConnectFuture connectFuture = client.connect(username, host, 22).verify(10, TimeUnit.SECONDS);
            // 判断是否连接成功
            if (connectFuture.isConnected()) {
                // 获取客户端Session对象
                session = connectFuture.getSession();
                // 添加密码
                session.addPasswordIdentity(password);
                // 验证身份认证
                AuthFuture auth = session.auth().verify(10, TimeUnit.SECONDS);
                // 判断是否认证成功
                if (auth.isSuccess()) {
                    // 创建通道对象
                    ChannelExec channel = session.createExecChannel(command);
                    // 设置输入输出流
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    ByteArrayOutputStream outputError = new ByteArrayOutputStream();
                    channel.setOut(output);
                    channel.setErr(outputError);
                    // 打开通道
                    channel.open();
                    // 等待命令执行完成
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0);
                    String outputStr = output.toString();
                    String outputErrorStr = outputError.toString();
                    if(StrUtil.isNotEmpty(outputStr)){
                        log.info("命令输出:{}", outputStr);
                    }
                    if(StrUtil.isNotEmpty(outputErrorStr)){
                        log.info("命令错误输出:{}", outputErrorStr);
                    }
                } else{
                    Throwable exception = auth.getException();
                    log.error("IP[{}], 用户名[{}], 密码[{}], 身份验证失败:{}",host,username,password,BeanUtil.isNotEmpty(exception) ? exception.getMessage() : "无");
                }
            } else {
                Throwable exception = connectFuture.getException();
                log.error("连接服务器{}失败:{}",host, BeanUtil.isNotEmpty(exception) ? exception.getMessage() : "无");
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            try {
                if(session != null){
                    session.close();
                }
                if(client != null){
                    client.close();
                }
            }catch (Exception exception){
                exception.printStackTrace();
                log.error("关闭客户端异常:{}",exception.getMessage());
            }
        }
    }
}
