package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.enumm.Status;
import com.ekolodey.spring_attestation.models.*;
import com.ekolodey.spring_attestation.repositories.CartRepository;
import com.ekolodey.spring_attestation.repositories.OrderRepository;
import com.ekolodey.spring_attestation.security.PersonDetails;
import com.ekolodey.spring_attestation.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Controller
public class CartController {

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public CartController(CartRepository cartRepository, OrderRepository orderRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    private Person getPerson(){
        // Извлекаем объект аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    private int getPersonId(){
        // Извлекаем id пользователя из объекта
        return getPerson().getId();
    }


    @GetMapping("/cart")
    public String cart(Model model){
        List<Cart> cartList = cartRepository.findByPersonId(getPersonId());
        float total = 0;

        for(Cart item: cartList){
            total += item.getTotalPrice();
        }
        model.addAttribute("cart", cartList);
        model.addAttribute("total", total);

        return "/user/cart";
    }


    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int productId){
        int id_person = getPersonId();
        Cart cart = cartRepository.findByPersonIdAndProductId(id_person, productId);
        if (cart == null)
        {
            Product product = productService.getProductId(productId);
            cart = new Cart(id_person,product,0);
        }
        cart.setCount(cart.getCount() + 1);
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProductFromCart(@PathVariable("id") int productId){
        Cart cart = cartRepository.findByPersonIdAndProductId(getPersonId(), productId);

        if (cart != null){
            cart.setCount(cart.getCount()-1);
            if(cart.getCount() == 0){
                cartRepository.delete(cart);
            } else {
                cartRepository.save(cart);
            }
        }

        return "redirect:/cart";
    }


    @GetMapping("/order/create")
    public String order(){
        Person person = getPerson();
        List<Cart> cartList = cartRepository.findByPersonId(person.getId());

        String uuid = UUID.randomUUID().toString();

        Order order = new Order(uuid, person, Status.Ожидает);

        HashSet<OrderItem> items = new HashSet<>();
        for(Cart cart: cartList){
            Product product = cart.getProduct();
            OrderItem item = new OrderItem(product, order, cart.getCount(), product.getPrice());
            items.add(item);
        }

        order.setItems(items);
        orderRepository.save(order);

        cartRepository.deleteByPersonId(person.getId());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderUser(Model model){
        List<Order> orders = orderRepository.findByPerson(getPerson());

        model.addAttribute("orders", orders);
        return "/user/orders";
    }
}
