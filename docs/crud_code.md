Voici la suite logique de ton `FrappeCrudService` avec des méthodes génériques supplémentaires pour :

* ✅ `deleteDocument`
* ✅ `updateDocument`
* ✅ `submitDocument`
* ✅ `cancelDocument`

---

### ✅ Méthodes à ajouter à `FrappeCrudService`

```java
// Delete un document par son ID
public void deleteDocument(UserErpNext user, D document, String id) throws ERPNexException {
    if (id == null) {
        throw new ERPNexException("Document ID must be provided for deletion.");
    }

    frappeWebService.callResource(
        user,
        document,
        id,
        null,
        HeadersUtils.buildJsonHeader(user),
        HttpMethod.DELETE,
        null,
        null
    );
}

// Mise à jour d’un document
public D updateDocument(UserErpNext user, D document, Class<D> modelClass) throws ERPNexException, Exception {
    FrappeResponseParser<D> parser = new FrappeResponseParser<>();
    document.update_cotnrole();

    ResponseEntity<String> response = frappeWebService.callResource(
        user,
        document,
        document.getName(),
        document.as_dict(),
        HeadersUtils.buildJsonHeader(user),
        HttpMethod.PUT,
        null,
        null
    );

    ResourceSingleResponse<D> singleResponse = parser.parseSingleResourceResponse(response, modelClass);
    return singleResponse.getData();
}

// Soumettre un document
public D submitDocument(UserErpNext user, D document, Class<D> modelClass) throws ERPNexException {
    FrappeResponseParser<D> parser = new FrappeResponseParser<>();

    ResponseEntity<String> response = frappeWebService.callSubmitOrCancel(
        user,
        document,
        "submit"
    );

    ResourceSingleResponse<D> singleResponse = parser.parseSingleResourceResponse(response, modelClass);
    return singleResponse.getData();
}

// Annuler un document
public D cancelDocument(UserErpNext user, D document, Class<D> modelClass) throws ERPNexException {
    FrappeResponseParser<D> parser = new FrappeResponseParser<>();

    ResponseEntity<String> response = frappeWebService.callSubmitOrCancel(
        user,
        document,
        "cancel"
    );

    ResourceSingleResponse<D> singleResponse = parser.parseSingleResourceResponse(response, modelClass);
    return singleResponse.getData();
}
```

---

### ✅ Prérequis dans `FrappeWebService`

Tu dois ajouter cette méthode dans `FrappeWebService` :

```java
public ResponseEntity<String> callSubmitOrCancel(UserErpNext user, FrappeDocument doc, String action) {
    String url = String.format("/api/resource/%s/%s?action=%s", doc.getDoctype(), doc.getName(), action);

    return restTemplate.exchange(
        user.getUrl() + url,
        HttpMethod.PUT,
        new HttpEntity<>(null, HeadersUtils.buildJsonHeader(user)),
        String.class
    );
}
```

---

Souhaites-tu aussi une méthode générique `setDocStatus(int)` ou `transitionDocument(String action)` pour pouvoir appeler `submit`, `cancel`, `amend` plus dynamiquement ?
