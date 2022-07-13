package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

        @Autowired
        MemberRepository memberRepository;


        @Test
        @Transactional
        @Rollback(false)
        public void testMember() throws Exception{
            Member member = new Member();
            member.setUserName("memberA");
            Long savedId = memberRepository.save(member);
            Member findMember = memberRepository.find(savedId);

            Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
            Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
            Assertions.assertThat(findMember).isEqualTo(member);
            System.out.println("findMember ==member " + (findMember==member));

        }
    }


