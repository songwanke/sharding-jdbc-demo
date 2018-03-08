package com.jd.b2b.shardingjdbc.web;

import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.service.OrderService;
import com.jd.ecc.commons.db.hintmanager.HintManagerUtil;
import io.shardingjdbc.core.api.HintManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songwanke
 * @date 2018/1/30
 */
@RestController
@RequestMapping("/order/test")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/insert",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public void insert(Order order){
        log.info("插入order信息：{}",order);
        String va ="confirm=regression360\n" +
                "  &refereeId=18679&\n" +
                "  mobileCode=921241&\n" +
                "  password=regression360&\n" +
                "  agreement=1&\n" +
                "  code=DFHZ&\n" +
                "  mobile=14316020083&\n" +
                "  businessToken=8d5adc1ba5754783baee569409aed2d4&\n" +
                "  platformId=3&\n" +
                "  uuid=650fb84ef2ba48e8832d3d2be8c067a4&\n" +
                "  username=shared_8a83ka9L";
        orderService.insert(order);
    }

    @RequestMapping(value = "/selectAll",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    public List<Order> selctAll() throws Exception {

        HintManager hintManager = HintManagerUtil.hintManagerStart();
        List<Order> orders = orderService.selectAll();
        HintManagerUtil.hintManagerClose(hintManager);
        log.info("orders：{},orders.size:{}",orders,orders.size());
        return orders;
    }

        @RequestMapping(value = "/test",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
        public String test() throws Exception {


        return "";
    }
}
