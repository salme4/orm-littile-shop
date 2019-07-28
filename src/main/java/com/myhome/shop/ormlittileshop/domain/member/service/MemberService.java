package com.myhome.shop.ormlittileshop.domain.member.service;


import com.myhome.shop.ormlittileshop.domain.member.Member;
import com.myhome.shop.ormlittileshop.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberService")
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) throws Exception {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateMember(Member member) {
        List<Member> byUserName = this.findByUserName(member.getUserName());
        if(!byUserName.isEmpty()) {
            throw new IllegalStateException("같은 이름의 User가 존재합니다.");
        }
    }

    @Transactional(readOnly = true)
    Member findMemberById(Long id) throws Exception {
        Member member = memberRepository.findById(id)
                .orElse(new Member());
        return member;
    }

    @Transactional(readOnly = true)
    List<Member> findByUserName(String name) {
        List<Member> members = memberRepository.findAllByUserName(name);
        return members;
    }


}
