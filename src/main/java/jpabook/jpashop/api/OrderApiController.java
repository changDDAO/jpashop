package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.Repository.OrderRepository;
import jpabook.jpashop.domain.Repository.OrderSearch;
import jpabook.jpashop.domain.Repository.order.query.OrderQueryDto;
import jpabook.jpashop.domain.Repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return collect;

    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return collect;
    }


    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return collect;
    }

    @Getter
    static class OrderDto {
        private Long orderId;
        private String name;

        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;


        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());

        }

        @Data
        static class OrderItemDto {
            private String itemName;
            private int orderPrice;
            private int count;


            public OrderItemDto(OrderItem orderItem) {
                itemName = orderItem.getItem().getName();
                orderPrice = orderItem.getOrderPrice();
                count = orderItem.getCount();
            }
        }




    }
}
