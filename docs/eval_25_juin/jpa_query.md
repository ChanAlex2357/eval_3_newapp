Dans un **service JPA (Spring Data JPA)**, tu peux :

---

## ✅ 1. Exécuter une requête personnalisée (`@Query`)

### 🔹 Exemple JPQL

```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<Employee> findEmployeesWithSalaryGreaterThan(@Param("minSalary") BigDecimal minSalary);
}
```

---

## ✅ 2. Appeler une procédure stockée (`@Procedure`)

Tu peux déclarer une procédure existante MySQL directement dans ton repository :

### 🔹 Méthode 1 : `@Procedure`

```java
@Repository
public interface PayrollRepository extends JpaRepository<PayrollEntry, String> {

    @Procedure(procedureName = "calculate_salary")
    void calculateSalary(@Param("employeeId") String empId);
}
```

> ⚠️ Ta procédure doit déjà être définie dans MySQL :
> `CREATE PROCEDURE calculate_salary(IN employeeId VARCHAR(255))`

---

## ✅ 3. Exécuter une requête native SQL (`nativeQuery=true`)

### 🔹 Exemple sur une **vue SQL**

Supposons que tu aies une **vue** SQL nommée `v_salary_summary`.

```java
@Entity
@Table(name = "v_salary_summary")
public class SalarySummary {
    @Id
    private String employee;

    private BigDecimal totalSalary;
}
```

```java
@Repository
public interface SalarySummaryRepository extends JpaRepository<SalarySummary, String> {

    @Query(value = "SELECT * FROM v_salary_summary WHERE employee = :employee", nativeQuery = true)
    SalarySummary findByEmployee(@Param("employee") String employee);
}
```

---

## ✅ 4. Avec `EntityManager` pour des requêtes dynamiques plus complexes

### 🔹 Exemple en utilisant `EntityManager`

```java
@Service
public class SalaryService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> findCustomSalaryData(Date start, Date end) {
        String sql = "SELECT employee, SUM(amount) FROM salary_detail WHERE date BETWEEN :start AND :end GROUP BY employee";
        return entityManager.createNativeQuery(sql)
            .setParameter("start", start)
            .setParameter("end", end)
            .getResultList();
    }
}
```

---

## 🧠 Résumé

| Objectif                   | Solution                     |
| -------------------------- | ---------------------------- |
| Requête JPQL               | `@Query(...)`                |
| Requête SQL native         | `@Query(nativeQuery = true)` |
| Vue SQL                    | Mapper `@Entity` sur la vue  |
| Procédure stockée          | `@Procedure`                 |
| Requête dynamique complexe | `EntityManager`              |

Souhaites-tu un exemple pour une procédure stockée réelle ou une vue dans ton contexte ERPNext (salaire, présence) ?
