package com.alone.sell.controller.manage;

import com.alone.sell.common.enums.ResultEnum;
import com.alone.sell.dto.OrderDTO;
import com.alone.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家订单页
 *
 * @author Chri61
 * @Date 2022/3/24 0024
 */
@Slf4j
@Controller
@RequestMapping("seller/order")
public class SellerOrderController {

    @Autowired
    private IOrderService iOrderServicel;

    /**
     * 订单列表
     *
     * @param page 第几页
     * @param size 每页显示的个数
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(defaultValue = "1", value = "page") Integer page,
                             @RequestParam(defaultValue = "10", value = "size") Integer size,
                             Map<String, Object> map) {

        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = iOrderServicel.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        //当前页
        map.put("currentPate", page);
        map.put("size", size);
        //总页数
        map.put("totalPages", orderDTOPage.getTotalPages());
        return new ModelAndView("/order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("cancel")
    public ModelAndView cancel(String orderId, Map<String, Object> map) {
        try {
            OrderDTO orderDTO = iOrderServicel.findById(orderId);
            iOrderServicel.cancel(orderDTO);
            log.info("【卖家端取消订单】订单取消成功 orderId={}", orderId);
        } catch (Exception e) {
            log.error("【卖家端取消订单】发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 完结订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("finish")
    public ModelAndView finish(String orderId, Map<String, Object> map) {
        try {
            OrderDTO orderDTO = iOrderServicel.findById(orderId);
            iOrderServicel.finish(orderDTO);
            log.info("【卖家端完结订单】完结订单，操作成功 orderId={}", orderId);
        } catch (Exception e) {
            log.error("【卖家端完结订单】发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/detail?orderId=" + orderId);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 查看订单详情
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("detail")
    public ModelAndView detail(String orderId, Map<String, Object> map) {
        try {
            OrderDTO orderDTO = iOrderServicel.findById(orderId);
            map.put("orderDTO", orderDTO);
        } catch (Exception e) {
            log.error("【卖家端查看订单详情】发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        return new ModelAndView("order/detail", map);
    }

}

