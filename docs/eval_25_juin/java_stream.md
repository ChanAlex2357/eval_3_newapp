# JAVA Stream

En Java, les **streams** permettent de manipuler des collections de manière **fonctionnelle** et **déclarative**, en enchaînant des opérations comme `filter`, `map`, `collect`, etc., au lieu d’écrire des boucles classiques.

---

## 🎯 À quoi sert un `Stream` ?

Un `Stream` sert à :

* parcourir des collections (List, Set, etc.) sans boucle explicite,
* effectuer des transformations (`map`, `filter`, etc.),
* effectuer des agrégations (`sum`, `count`, `collect`, etc.),
* créer des pipelines de traitement **lisibles** et **concats**.

---

## 🔧 Exemple simple

```java
List<String> names = Arrays.asList("Alex", "Boby", "Alice", "Anita");

List<String> filteredNames = names.stream()
    .filter(name -> name.startsWith("A"))
    .map(String::toUpperCase)
    .collect(Collectors.toList());

System.out.println(filteredNames); // [ALEX, ALICE, ANITA]
```

---

## 🧩 Les opérations courantes sur Stream

| Opération    | Rôle                                                          |
| ------------ | ------------------------------------------------------------- |
| `filter()`   | Garder uniquement les éléments qui remplissent une condition  |
| `map()`      | Transformer chaque élément                                    |
| `collect()`  | Convertir le résultat final (en liste, map, set, etc.)        |
| `sorted()`   | Trier les éléments                                            |
| `limit(n)`   | Garder les `n` premiers                                       |
| `distinct()` | Supprimer les doublons                                        |
| `count()`    | Compter les éléments                                          |
| `reduce()`   | Agréger manuellement une collection (comme une somme cumulée) |
| `forEach()`  | Exécuter une action pour chaque élément                       |

---

## 💡 Exemples concrets

### ✅ Filtrer une liste de personnes adultes

```java
List<Person> people = getPeople();

List<Person> adults = people.stream()
    .filter(p -> p.getAge() >= 18)
    .collect(Collectors.toList());
```

### ✅ Compter les éléments

```java
long count = people.stream()
    .filter(p -> p.getAge() >= 18)
    .count();
```

### ✅ Trouver le premier élément

```java
Optional<String> result = names.stream()
    .filter(n -> n.startsWith("B"))
    .findFirst();
```

---

## 🧠 À retenir

* `stream()` est **non-destructif** : il ne modifie pas la collection d’origine.
* Chaque opération intermédiaire retourne un **nouveau Stream**.
* La pipeline **ne s’exécute pas tant qu’une opération terminale** (`collect`, `count`, etc.) n’est pas appelée.

---

Souhaites-tu un exemple orienté projet ? (comme `SalarySlip`, `Employee`, etc.)
