Voici un guide clair pour **créer une procédure stockée en MySQL** avec des exemples de :

* ✅ déclaration
* 🔁 **boucle**
* ❓ **condition**

---

### ✅ 1. Syntaxe de base d'une procédure stockée

```sql
DELIMITER $$

CREATE PROCEDURE nom_procedure(IN param1 INT, OUT result INT)
BEGIN
    -- Instructions ici
    SET result = param1 * 2;
END $$

DELIMITER ;
```

---

### 🔁 2. Exemple avec **boucle WHILE**

```sql
DELIMITER $$

CREATE PROCEDURE somme_de_1_a_n(IN n INT, OUT resultat INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    SET resultat = 0;

    WHILE i <= n DO
        SET resultat = resultat + i;
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;
```

📌 Appel :

```sql
CALL somme_de_1_a_n(10, @res);
SELECT @res; -- => 55
```

---

### ❓ 3. Exemple avec **IF/ELSE**

```sql
DELIMITER $$

CREATE PROCEDURE test_condition(IN age INT, OUT categorie VARCHAR(20))
BEGIN
    IF age < 18 THEN
        SET categorie = 'Mineur';
    ELSEIF age <= 60 THEN
        SET categorie = 'Adulte';
    ELSE
        SET categorie = 'Senior';
    END IF;
END $$

DELIMITER ;
```

---

### 🔁 4. Exemple avec **boucle FOR** (simulée via WHILE)

```sql
DELIMITER $$

CREATE PROCEDURE afficher_nombres(IN max INT)
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= max DO
        SELECT i;
        SET i = i + 1;
    END WHILE;
END $$

DELIMITER ;
```

---

### 🛠 Bonnes pratiques

* Utiliser `DELIMITER $$` pour éviter les erreurs dues aux `;`
* Toujours finir par `END $$`
* Pour les erreurs : tester avec `SHOW ERRORS`

---

Souhaites-tu un exemple plus avancé lié aux **salaires, absences ou employés** pour ton application ?
