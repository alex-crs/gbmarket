package com.gb.market.market.dtos;

import com.gb.market.market.dtos.order.OrderDTOFullInfo;
import com.gb.market.market.dtos.order.OrderItemDTO;
import com.gb.market.market.entities.Order;
import com.gb.market.market.entities.OrderItem;

public class DTOConverter {
    public static OrderItemDTO createOrderItemDTOFromOrderItem(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setTitle(orderItem.getProduct().getTitle());
        orderItemDTO.setAmount(orderItem.getAmount());
        orderItemDTO.setPricePerProduct(orderItem.getPricePerProduct());
        orderItemDTO.setTotalPrice(orderItem.getTotalPrice());
        return orderItemDTO;
    }


    public static OrderDTOFullInfo createOrderDTOFullInfoFromOrder(Order order) {
        OrderDTOFullInfo orderDTOFullInfo = new OrderDTOFullInfo();
        orderDTOFullInfo.setOrderId(order.getId());
        orderDTOFullInfo.setCreatedAt(order.getCreatedAt());
        orderDTOFullInfo.setTotalPrice(order.getTotalPrice());
        orderDTOFullInfo.setAddress(order.getAddress());
        orderDTOFullInfo.setPhone(order.getPhone());
        order.getItems().forEach(orderItem -> orderDTOFullInfo.getItems().add(createOrderItemDTOFromOrderItem(orderItem)));
        return orderDTOFullInfo;
    }
}
