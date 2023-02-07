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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);
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
//            List<MemberDTO> result = em.createQuery("select new com.jpql.MemberDTO(m.username, m.age) From Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = result.get(0);
//            System.out.println("memberDTO = " + memberDTO.getUsername());
//            System.out.println("memberDTO = " + memberDTO.getAge());

            em.flush();
            em.clear();

            //페이징
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(20)
//                    .getResultList();
//
//            System.out.println("result.size = " + resultList.size());
//            for(Member member1 : resultList){
//                System.out.println("member1 = " + member1);
//            }

            // 내부조인
            //String query = "select m from Member m inner join m.team t";
            //외부조인
            //String query = "select m from Member m left join m.team t";
            //세타조인
            //String query = "select m from Member m, Team t where m.username = t.name";
            //List<Member> resultList = em.createQuery(query, Member.class)
            //        .getResultList();

            // 조인 필터링
//            String query = "select m from Member m left join m.team t on t.name  = 'teamA'";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();

            // 연관관계 없는 엔티티 외부조인
//            String query = "select m from Member m left join Team t  on m.username = t.name";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();


            // 조건식
//            String query = "select " +
//                                "case when m.age <= 10 then '학생요금' " +
//                                "     when m.age >= 60 then '경로요금' " +
//                                "     else '일반요금' " +
//                                "end " +
//                            "from Member m";

            // coalesce : 하나씩 조회해서 null 이 아니면 반환
            //String query = "select coalesce(m.username, '이름 없는 회원') from Member m";

            // nullif : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            String query = "select nullif(m.username, 'member1') from Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for(String s : resultList){
                System.out.println("s = " + s);
            }

            tx.commit();;
        } catch(Exception e){
            tx.rollback();
        } finally{
            em.close();
        }
        emf.close();
    }
}
