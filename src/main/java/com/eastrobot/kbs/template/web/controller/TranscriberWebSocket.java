package com.eastrobot.kbs.template.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@ServerEndpoint("/native-ws/{uid}")
public class TranscriberWebSocket {
    private static final String APP_KEY = "default";
    private static final String ACCESS_TOKEN = "default";
    private static final NlsClient client = new NlsClient("ws://172.16.7.57:8101/ws/v1", ACCESS_TOKEN);

    private SpeechTranscriber transcriber;
    /**
     * 语句buffer
     */
    private StringBuilder sentenceBuffer;
    private Session sessionHolder;

    @OnOpen
    public void onOpen(Session session) {
        session.setMaxIdleTimeout(0);
        session.setMaxBinaryMessageBufferSize(10 * 1024 * 1024);
        sessionHolder = session;

        log.info("ws: session: [{}], connected success: [{}]", session.getId(), session.getUserPrincipal().getName());
    }

    @OnMessage
    public void actionMsg(String action) throws Exception {
        switch (action) {
            case "start":
                log.info("init transcriber env");
                transcriber = initTranscriber();
                transcriber.start();
                sentenceBuffer = new StringBuilder(1024);
                break;
            case "stop":
                log.info("clean transcriber env");
                transcriber.stop();
                transcriber.close();
                break;
            default:
                break;
        }
        log.info("action:{} -> {}", action, transcriber.getState().toString());
    }

    @OnMessage
    public void onMessage(InputStream is, Session session) throws Exception {
        log.info("ws: receive data length: {}", is.available());
        transcriber.send(is);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.warn("ws: session: [{}], connection close: [{}]", session.getId(), session.getUserPrincipal().toString());
        session.close();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("ws: session: [{}], connection error: [{}]", session.getId(), session.getUserPrincipal().toString());
        error.printStackTrace();
    }

    private void sendResponseToClient(SpeechTranscriberResponse response) {
        String transSentenceText = response.getTransSentenceText();

        if (StringUtils.isNotBlank(transSentenceText)) {
            try {
                String sendText;
                if ("SentenceEnd".equals(response.getName())) {
                    sentenceBuffer.append(transSentenceText);
                    sendText = sentenceBuffer.toString();
                } else {
                    sendText = sentenceBuffer.toString() + transSentenceText;
                }
                sessionHolder.getBasicRemote().sendText(
                        new JSONObject()
                                .fluentPut("msg", sendText)
                                .fluentPut("time", response.getTransSentenceTime())
                                .toString()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SpeechTranscriber initTranscriber() throws Exception {
        transcriber = new SpeechTranscriber(client, getTranscriberListener());
        transcriber.setAppKey(APP_KEY);
        // 输入音频编码方式
        transcriber.setFormat(InputFormatEnum.PCM);
        // 输入音频采样率
        transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
        // 是否返回中间识别结果
        transcriber.setEnableIntermediateResult(true);
        // 是否生成并返回标点符号
        transcriber.setEnablePunctuation(true);
        // 是否将返回结果规整化,比如将一百返回为100
        transcriber.setEnableITN(true);
        return transcriber;
    }

    private SpeechTranscriberListener getTranscriberListener() {
        return new SpeechTranscriberListener() {
            //识别出中间结果.服务端识别出一个字或词时会返回此消息.仅当setEnableIntermediateResult(true)时,才会有此类消息返回
            @Override
            public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
                log.info("onTranscriptionResultChange >> task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        //状态码 20000000 表示正常识别
                        ", status: " + response.getStatus() +
                        //句子编号，从1开始递增
                        ", index: " + response.getTransSentenceIndex() +
                        //当前的识别结果
                        ", result: " + response.getTransSentenceText() +
                        //当前已处理的音频时长，单位是毫秒
                        ", time: " + response.getTransSentenceTime());

                sendResponseToClient(response);
            }

            @Override
            public void onTranscriberStart(SpeechTranscriberResponse response) {
                log.debug("onTranscriberStart >> task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        ", status: " + response.getStatus());
            }

            @Override
            public void onSentenceBegin(SpeechTranscriberResponse response) {
                log.debug("onSentenceBegin >> task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        ", status: " + response.getStatus());

            }

            //识别出一句话.服务端会智能断句,当识别到一句话结束时会返回此消息
            @Override
            public void onSentenceEnd(SpeechTranscriberResponse response) {
                log.error("onSentenceEnd >> task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        //状态码 20000000 表示正常识别
                        ", status: " + response.getStatus() +
                        //句子编号，从1开始递增
                        ", index: " + response.getTransSentenceIndex() +
                        //当前的识别结果
                        ", result: " + response.getTransSentenceText() +
                        //置信度
                        ", confidence: " + response.getConfidence() +
                        //开始时间
                        ", begin_time: " + response.getSentenceBeginTime() +
                        //当前已处理的音频时长，单位是毫秒
                        ", time: " + response.getTransSentenceTime());
                sendResponseToClient(response);
            }

            //识别完毕
            @Override
            public void onTranscriptionComplete(SpeechTranscriberResponse response) {
                log.debug("onTranscriptionComplete >> task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        ", status: " + response.getStatus() + " ==> " + response.getTransSentenceText());
            }

            @Override
            public void onFail(SpeechTranscriberResponse response) {
                log.error(
                        "onFail >> task_id: " + response.getTaskId() +
                                //状态码 20000000 表示识别成功
                                ", status: " + response.getStatus() +
                                //错误信息
                                ", status_text: " + response.getStatusText());
            }

        };
    }
}
