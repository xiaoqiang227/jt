package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
@Service
public class DubboOrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	/**
	 * 1.利用mybatis-plus  效率低
	 * 2.手写sql
	 * 订单号：登录用户id+当前时间戳'
	 */
	@Override
	@Transactional //控制事务
	public String saveOrder(Order order) {
		String orderId = ""+order.getUserId() + 
						 System.currentTimeMillis();
		Date date = new Date();
		order.setOrderId(orderId)
			 .setStatus(1)
			 .setCreated(date)
			 .setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功!!!!");
		
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功!!!!");
		
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
			orderItemMapper.insert(orderItem);
			
		}
		System.out.println("订单商品入库成功!!!!");
		return orderId;
	}

	/**
	 * 1.利用mybatis-plus方式查询数据
	 * 2.利用sql语句关联查询 3张表 137行
	 * 	  order对象
	 */
	@Override
	public Order findOrderById(String id) {
		/*
		 * Order order = orderMapper.selectById(id); OrderShipping orderShipping =
		 * orderShippingMapper.selectById(id); QueryWrapper<OrderItem> queryWrapper =
		 * new QueryWrapper<>(); queryWrapper.eq("order_id", id); List<OrderItem>
		 * orderItems = orderItemMapper.selectList(queryWrapper);
		 * order.setOrderShipping(orderShipping) .setOrderItems(orderItems);
		 */
		
		return orderMapper.findOrderById(id);
	}
}
