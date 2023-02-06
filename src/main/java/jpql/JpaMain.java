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

            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                        .setParameter("username", "test")
                        .getSingleResult();
            System.out.println("singleResult = " + result.getUsername());

            tx.commit();;
        } catch(Exception e){
            tx.rollback();
        } finally{
            em.close();
        }
        emf.close();
    }
}
