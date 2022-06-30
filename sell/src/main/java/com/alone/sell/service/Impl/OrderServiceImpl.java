package com.alone.sell.service.Impl;

import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.enums.OrderStatusEnum;
import com.alone.sell.common.enums.PayStatusEnum;
import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.common.utils.KeyUtil;
import com.alone.sell.converter.OrderMasterToOrderDTOConverter;
import com.alone.sell.dataobject.OrderDetail;
import com.alone.sell.dataobject.OrderMaster;
import com.alone.sell.dataobject.ProductInfo;
import com.alone.sell.dto.CartDTO;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.repository.OrderDetailRepository;
import com.alone.sell.repository.OrderMasterRepository;
import com.alone.sell.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Chri61
 * @Date 2021/12/30 0030
 */
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IProductInfoService iProductInfoService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private IPayService iPayService;
    @Autowired
    private IPushMessageService iPushMessageService;
    @Autowired
    private WebSocket webSocket;


    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //生成订单id
        String orderId = KeyUtil.getUniqueKey();
        orderDTO.setOrderId(orderId);
        //总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1.查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            //获取商品详情
            ProductInfo productInfo = iProductInfoService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价。
            orderAmount = productInfo.getProductPrice()
                    .multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //复制属性
            BeanUtils.copyProperties(productInfo, orderDetail);
            //主键id
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            //订单id
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        //3.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        //复制属性
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        iProductInfoService.decreaseStock(cartDTOList);

        //发送websocket消息
        webSocket.sendMessage("有新的订单");

        return orderDTO;
    }

    @Override
    public OrderDTO findById(String orderId) {
        Optional<OrderMaster> byId = orderMasterRepository.findById(orderId);
        OrderMaster orderMaster = byId.orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAAL_NOT_EXIST);
        }
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, dto);
        //设置订单详情列表
        dto.setOrderDetailList(orderDetailList);
        return dto;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> OrderDTOList = OrderMasterToOrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<>(OrderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单的状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态错误，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROE);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        //修改完，再复制属性
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中没有商品");
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> collect = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        iProductInfoService.increaseStock(collect);
        //如果已支付，需要退款
        if (orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            iPayService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态错误，orderId={}，orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROE);
        }
        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【完结订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送消息
        iPushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO OrderDTO) {
        //1.判断订单状态
        if (!OrderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态错误，orderId={}，orderStatus={}", OrderDTO.getOrderId(), OrderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROE);
        }
        //2.判断支付状态
        if (!OrderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态错误");
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //3.修改支付状态
        OrderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(OrderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【订单支付完成】更新失败");
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return OrderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> OrderDTOList = OrderMasterToOrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<>(OrderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
