package com.gb.market.market.services;

import com.gb.market.market.dtos.CartItem;
import com.gb.market.market.entities.BDCartItem;
import com.gb.market.market.entities.Cart;
import com.gb.market.market.entities.Order;
import com.gb.market.market.entities.Product;
import com.gb.market.market.exceptions.ResourceNotFoundException;
import com.gb.market.market.repositories.BDCartItemRepo;
import com.gb.market.market.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final BDCartItemRepo bdCartItemRepo;
    private Cart cart;
    private long orderNumber;

    @PostConstruct
    public void init() {
        cart = new Cart();
        orderNumber = 1;
    }

    // TODO пересмотреть логику этого метода
    public Cart getCurrentCart() {
        return cart;
    }

    //Добавляет в корзину пользователя продукт из базы данных (формируя DTO)
    public void addToCartFromBD(long productId) {
        Product product = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Продукт невозможно добавить id: " + productId));
        cart.addToCart(new CartItem().createCartItemDTO(product));
    }

    public void addToBdCartFromBD(long productId) {
        Order order;
        Optional<Order> currentOrder = orderRepository.findByOrderNumber(orderNumber);
        if (currentOrder.isEmpty()) {
            order = new Order();
            order.setOrderNumber(orderNumber);
        } else {
            order = currentOrder.get();
        }
        Product product = productService.findProductById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Продукт невозможно добавить id: " + productId));

        BDCartItem bdCartItem = new BDCartItem();
        bdCartItem.setTitle(product.getTitle());
        bdCartItem.setPrice(product.getPrice());
        bdCartItem.setItem_id(product.getId());
        orderRepository.save(order);
        bdCartItem.setOrder(order);
        bdCartItemRepo.save(bdCartItem);
    }

    public Cart getBDCart() {
        Cart currentBDCart = new Cart();
        Optional<Order> currentOrder = orderRepository.findByOrderNumber(orderNumber);
        currentOrder.ifPresent(order -> order.getItems()
                .forEach(bdCartItem -> currentBDCart.addToCart(new CartItem().createCartItemDTO(bdCartItem))));
        return currentBDCart;
    }

    public void clearBDCart(){
        Optional<Order> currentOrder = orderRepository.findByOrderNumber(orderNumber);
        currentOrder.ifPresent(orderRepository::delete);
    }
}
