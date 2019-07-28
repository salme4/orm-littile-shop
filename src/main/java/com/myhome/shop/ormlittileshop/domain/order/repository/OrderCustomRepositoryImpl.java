package com.myhome.shop.ormlittileshop.domain.order.repository;

import com.myhome.shop.ormlittileshop.domain.member.Member;
import com.myhome.shop.ormlittileshop.domain.member.Member_;
import com.myhome.shop.ormlittileshop.domain.order.Order;
import com.myhome.shop.ormlittileshop.domain.order.OrderSearch;
import com.myhome.shop.ormlittileshop.domain.order.Order_;
import org.hibernate.jpamodelgen.util.StringUtil;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Order> findAllOrderBySearchs(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        ArrayList<Predicate> criteria = new ArrayList<>();

        //주문상태 검색
        if(orderSearch.getStatus() != null) {
            Predicate status = cb.equal(root.get(Order_.STATUS), orderSearch.getStatus());
            criteria.add(status);
        }

        //사용자 이름 검색 (어렵넹..)
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            Join<Order, Member> member = root.join("member", JoinType.INNER);
            Predicate name = cb.like(member.<String>get(Member_.USER_NAME),
                    "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        query.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> orderTypedQuery =
                em.createQuery(query).setMaxResults(1000);

        return orderTypedQuery.getResultList();
    }
}
