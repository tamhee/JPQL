package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em =emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{


            Member member = new Member();
            member.setUsername("test");
            member.setAge(10);
            em.persist(member);

            // 반환타입이 명확할 때 TypedQuery
            // 반환타입이 명확하지 않을때 Query
//          TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//          TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//          Query query3 = em.createQuery("select m.username, m.age from Member m");

//          TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            // 결과가 하나 이상일 때, 리스트 반환
            // 결과가 없어도 빈 리스트를 반환
//          List<Member> resultList = query1.getResultList();
//
            // 결과가 정확히 하나일 때, 단일 객체 반환
            // 결과가 없거나 둘 이상일 때는 exception 발생.
            // Spring Data JPA -> null이나 Optional 반환하도록 되어 있음.
//          Member singleResult = query1.getSingleResult();

//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                        .setParameter("username", "test")
//                        .getSingleResult();
//            System.out.println("singleResult = " + result.getUsername());

            // 프로젝션 - 여러값 조회
            //1.  Object -> Object [] 다운 캐스팅
//            List resultList = em.createQuery("select distinct m.username, m.age From Member m")
//                    .getResultList();
//
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);
            //2 . 제네릭 타입을 Object [] 로 지정
//            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age From Member m")
//                    .getResultList();
//
//            Object [] result = resultList.get(0);
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);

            // qlString이 문자기 때문에 패키지명을 모두 적어줘야 함.
//          QueryDSL에서 패키지 명을 포함한 전체 클래스명 입력하는 문제 극복 가능
            List<MemberDTO> result = em.createQuery("select new com.jpql.MemberDTO(m.username, m.age) From Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

            tx.commit();;
        } catch(Exception e){
            tx.rollback();
        } finally{
            em.close();
        }
        emf.close();
    }
}
