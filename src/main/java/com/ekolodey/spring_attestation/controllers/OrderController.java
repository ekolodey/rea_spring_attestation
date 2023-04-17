package com.ekolodey.spring_attestation.controllers;

import com.ekolodey.spring_attestation.models.*;
import com.ekolodey.spring_attestation.repositories.CartRepository;
import com.ekolodey.spring_attestation.repositories.OrderRepository;
import com.ekolodey.spring_attestation.repositories.OrderStatusRepository;
import com.ekolodey.spring_attestation.security.PersonDetails;
import com.ekolodey.spring_attestation.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductService productService;

    public OrderController(CartRepository cartRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
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
    public String create(){
        Person person = getPerson();
        List<Cart> cartList = cartRepository.findByPersonId(person.getId());

        String uuid = UUID.randomUUID().toString();

        Order order = new Order(uuid, person, orderStatusRepository.findByName("Ожидает"));

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
    public String list(Model model, HttpServletRequest request){
        List<Order> orders;

        if(request.isUserInRole("ADMIN"))
            orders = orderRepository.findAll();
        else
            orders = orderRepository.findByPerson(getPerson());

        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/order/{id}")
    public String info(@PathVariable("id") String orderId, Model model){
        Order order = orderRepository.findByNumber(orderId);

        model.addAttribute("order", order);
        model.addAttribute("statuses", orderStatusRepository.findAll());
        return "order/info";
    }

    @PostMapping("/order/{id}")
    public String changeStatus(@PathVariable("id") String orderId, @ModelAttribute("status") Integer statusId, Model model){
        Order order = orderRepository.findByNumber(orderId);
        order.setStatus(orderStatusRepository.findById(statusId).get());
        orderRepository.save(order);

        model.addAttribute("order", order);
        model.addAttribute("statuses", orderStatusRepository.findAll());
        return "order/info";
    }
}
