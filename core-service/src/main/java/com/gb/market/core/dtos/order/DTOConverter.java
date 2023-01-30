package com.gb.market.core.dtos.order;

import com.gb.market.core.entities.Order;
import com.gb.market.core.entities.OrderItem;

import java.time.format.DateTimeFormatter;

public class DTOConverter {
    public static OrderItemDTO createOrderItemDTOFromOrderItem(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getProduct().getId());
        orderItemDTO.setTitle(orderItem.getProduct().getTitle());
        orderItemDTO.setAmount(orderItem.getAmount());
        orderItemDTO.setPricePerProduct(orderItem.getPricePerProduct());
        orderItemDTO.setTotalPrice(orderItem.getTotalPrice());
        return orderItemDTO;
    }


    public static OrderDTOFullInfo createOrderDTOFullInfoFromOrder(Order order) {
        OrderDTOFullInfo orderDTOFullInfo = new OrderDTOFullInfo();
        orderDTOFullInfo.setOrderId(order.getId());
        orderDTOFullInfo.setCreatedAt(order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        orderDTOFullInfo.setTotalPrice(order.getTotalPrice());
        orderDTOFullInfo.setAddress(order.getAddress());
        orderDTOFullInfo.setPhone(order.getPhone());
        order.getItems().forEach(orderItem -> orderDTOFullInfo.getItems().add(createOrderItemDTOFromOrderItem(orderItem)));
        return orderDTOFullInfo;
    }
}
