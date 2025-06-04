Ton erreur vient d'une **incohérence entre le `Content-Type` du header** et le **type de corps (`body`) que tu envoies**.

---

## ❌ Erreur principale :

```
No serializer found for class sun.nio.ch.ChannelInputStream ...
through reference chain: java.util.HashMap["emp_file"]->MultipartFile["inputStream"]
```

---

## 🔍 Pourquoi ?

Tu fais :

```java
headers.setContentType(MediaType.APPLICATION_JSON);
```

Mais tu envoies un corps de requête de type :

```java
Map<String, MultipartFile>
```

→ **ça doit être du `multipart/form-data`**, pas du `application/json` !
Sinon, **Spring essaie de sérialiser les `MultipartFile` en JSON**, ce qui échoue, car ils contiennent un `InputStream` non sérialisable.

---

## ✅ Solution rapide

### 🔧 1. Corrige les headers

Dans `createHeaders` :

```java
headers.setContentType(MediaType.MULTIPART_FORM_DATA);
```

---

### 🛠️ 2. Corrige l’appel à `callMethod(...)` dans ton `FrappeWebService`

Tu dois envoyer un corps **multipart** correctement formé.
Selon ce que fait `callMethod(...)`, assure-toi que tu utilises un `HttpEntity<MultiValueMap<String, Object>>` comme ceci :

```java
MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
parts.add("emp_file", new MultipartInputStreamFileResource(empFile.getInputStream(), empFile.getOriginalFilename()));
parts.add("structure_file", new MultipartInputStreamFileResource(structureFile.getInputStream(), structureFile.getOriginalFilename()));
parts.add("salary_file", new MultipartInputStreamFileResource(salaryFile.getInputStream(), salaryFile.getOriginalFilename()));

HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

ResponseEntity<String> response = restTemplate.exchange(
    url,
    HttpMethod.POST,
    requestEntity,
    String.class
);
```

### 👉 Et pour cela, tu auras besoin de la classe `MultipartInputStreamFileResource` :

```java
public class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // unknown
    }
}
```

---

## ✅ Résumé des modifications

| Étape                                                              | Action                                      |
| ------------------------------------------------------------------ | ------------------------------------------- |
| ❌ Tu envoies `MultipartFile` avec `application/json`               | Spring ne sait pas comment sérialiser       |
| ✅ Mets `Content-Type: multipart/form-data`                         | Indique que tu envoies un fichier           |
| ✅ Utilise `MultiValueMap<String, Object>` dans `HttpEntity`        | Pour faire un vrai `multipart POST`         |
| ✅ Remplace `MultipartFile` par `InputStreamResource` dans le corps | Spring peut alors créer le `multipart` réel |

---

Souhaites-tu que je t’envoie une version complète et propre du `callMethod()` ajusté à `multipart/form-data` ?
