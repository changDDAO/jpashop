package jpabook.jpashop.domain.Repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
       return  em.createQuery(
                "select new jpabook.jpashop.Repository.order.query.OrderQueryDto Order o"+
                " join o.member m"+
                " join o.delivery d",OrderQueryDto.class)
                .getResultList();

    }
}
