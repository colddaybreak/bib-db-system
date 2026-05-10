# API Design Document

## 1. Authentication API

| Endpoint | Method | Path | Request Body / Params | Response | Description |
|----------|--------|------|-----------------------|----------|-------------|
| Register | POST | /api/auth/register | RegisterRequest { username, password, email } | ApiResponse\<String\> | Register a new user |
| Login | POST | /api/auth/login | LoginRequest { username, password } | ApiResponse\<String\> | Login and return JWT token |

## 2. Publication API

| Endpoint | Method | Path | Request Body / Params | Response | Description |
|----------|--------|------|-----------------------|----------|-------------|
| Create | POST | /api/publications | PublicationDTO { title*, authors*, year*, venue, abstractText, bibtex, tagIds } | ApiResponse\<PublicationDTO\> | Create a publication |
| List (paged) | GET | /api/publications | ?page=1&size=10&keyword=xxx&tagId=1 | ApiResponse\<List\<PublicationDTO\>\> | Search with keyword and tag filter |
| Get by ID | GET | /api/publications/{id} | Path param: id | ApiResponse\<PublicationDTO\> | Get single publication |
| Update | PUT | /api/publications/{id} | PublicationDTO { title*, authors*, year*, venue, abstractText, bibtex, tagIds } | ApiResponse\<PublicationDTO\> | Update publication by ID |
| Delete | DELETE | /api/publications/{id} | Path param: id | ApiResponse\<Void\> | Delete publication by ID |
| Import BibTeX | POST | /api/publications/import | Multipart file (.bib) | ApiResponse\<String\> | Import publications from BibTeX file |

> Fields marked with * are required (validated with @NotBlank / @NotNull).

## 3. Tag API

| Endpoint | Method | Path | Request Body / Params | Response | Description |
|----------|--------|------|-----------------------|----------|-------------|
| Create | POST | /api/tags | TagDTO { name* } | ApiResponse\<TagDTO\> | Create a tag |
| List all | GET | /api/tags | None | ApiResponse\<List\<TagDTO\>\> | Get all tags |
| Update | PUT | /api/tags/{id} | TagDTO { name* } | ApiResponse\<TagDTO\> | Update tag by ID |
| Delete | DELETE | /api/tags/{id} | Path param: id | ApiResponse\<Void\> | Delete tag by ID |

## 4. Bookmark API

| Endpoint | Method | Path | Request Body / Params | Response | Description |
|----------|--------|------|-----------------------|----------|-------------|
| Toggle | POST | /api/bookmarks/{publicationId} | Path param: publicationId | ApiResponse\<String\> | Bookmark or unbookmark |
| My list | GET | /api/bookmarks | None | ApiResponse\<List\<PublicationDTO\>\> | Get current user's bookmarks |

## 5. Uniform Response Format (ApiResponse)

```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

| Field | Type | Description |
|-------|------|-------------|
| code | int | 200 = success, non-200 = failure |
| message | String | Response message |
| data | T | Response data (generic) |

## 6. Error Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 400 | Bad Request (validation error, invalid parameter) |
| 404 | Resource Not Found |
| 500 | Internal Server Error |

## 7. DTO / Request Summary

| Class | Package | Purpose | Fields |
|-------|---------|---------|--------|
| LoginRequest | request | Login request | username*, password* |
| RegisterRequest | request | Registration request | username*, password*, email |
| PublicationDTO | DTO | Publication transfer object | id, title*, authors*, year*, venue, abstractText, bibtex, tagIds |
| TagDTO | DTO | Tag transfer object | id, name* |
| ApiResponse\<T\> | common | Uniform response format | code, message, data |

## 8. Swagger UI Access

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
