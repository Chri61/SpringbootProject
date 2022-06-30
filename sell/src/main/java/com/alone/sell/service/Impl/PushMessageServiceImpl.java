package com.alone.sell.service.Impl;

import com.alone.sell.common.config.WechatAcountConfig;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IPushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chri61
 * @Date 2022/3/31 0031
 */
@Slf4j
@Service
public class PushMessageServiceImpl implements IPushMessageService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAcountConfig wechatAcountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {

        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(wechatAcountConfig.getTemplateId().getOrderStatus());
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> dataList = Arrays.asList(
                new WxMpTemplateData("firs", "亲，快他么的收货"),
                new WxMpTemplateData("keyword1", "微信点菜的顶顶顶顶顶的"),
                new WxMpTemplateData("keyword2", "188275145678"),
                new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临")
        );
        wxMpTemplateMessage.setData(dataList);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("【微信模板消息】发送失败");
        }
    }
}
