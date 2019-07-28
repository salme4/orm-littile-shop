package com.myhome.shop.ormlittileshop.domain.member.service;


import com.myhome.shop.ormlittileshop.domain.member.Member;
import com.myhome.shop.ormlittileshop.domain.member.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testJoin() throws Exception {
        //given
        Member member = new Member();
        member.setUserName("dave");

        //when
        Long Id = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberService.findMemberById(Id));
    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicateUser() {
        //given
        Member member1 = new Member();
        member1.setUserName("dave");

        Member member2 = new Member();
        member2.setUserName("dave");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("Duplicate User");
    }
}