Créer une **fonction** MySQL est similaire à une procédure, mais avec quelques différences importantes :

| Caractéristique     | Procédure (`PROCEDURE`)   | Fonction (`FUNCTION`)             |
| ------------------- | ------------------------- | --------------------------------- |
| Retourne une valeur | ❌ Non (via paramètre OUT) | ✅ Oui (avec `RETURNS`)            |
| Appel dans requête  | ❌ Non                     | ✅ Oui (`SELECT ma_fonction(...)`) |
| Usage               | Exécute des actions       | Calcule et retourne une valeur    |

---

### ✅ 1. Syntaxe d’une fonction MySQL

```sql
DELIMITER $$

CREATE FUNCTION nom_fonction(param1 INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE result INT;
    SET result = param1 * 2;
    RETURN result;
END $$

DELIMITER ;
```

📌 Appel :

```sql
SELECT nom_fonction(5); -- retourne 10
```

---

### ❓ Exemple avec **condition**

```sql
DELIMITER $$

CREATE FUNCTION is_major(age INT)
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
    RETURN IF(age >= 18, 'Majeur', 'Mineur');
END $$

DELIMITER ;
```

---

### 🔁 Exemple avec **boucle WHILE**

```sql
DELIMITER $$

CREATE FUNCTION somme_1_a_n(n INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE total INT DEFAULT 0;

    WHILE i <= n DO
        SET total = total + i;
        SET i = i + 1;
    END WHILE;

    RETURN total;
END $$

DELIMITER ;
```

---

### 🔒 Précautions

* `DETERMINISTIC` indique que pour les mêmes entrées, le résultat est toujours le même.
* Les fonctions **ne peuvent pas** modifier des tables (pas de `INSERT`, `UPDATE`, `DELETE`).

---

### ❌ Erreurs fréquentes

1. Oublier `DETERMINISTIC` ou `RETURNS`
2. Utiliser `;` sans avoir changé le `DELIMITER`
3. Essayer de faire un `SELECT ... INTO` dans une fonction (non autorisé)

---

Souhaites-tu que je t’aide à créer une fonction pour un **besoin métier concret** (comme calculer un **salaire net** ou une **présence cumulée**) ?
