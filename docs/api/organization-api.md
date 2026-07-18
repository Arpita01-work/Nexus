# Organization API

## Module

Organization Management

---

# Overview

This module is responsible for managing organizations (companies) inside Nexus.

Each organization acts as an isolated workspace (tenant).

All future modules such as:

- Users
- Projects
- Workflows
- Tasks
- Assets
- Analytics
- Notifications

will belong to an organization.

---

# Base URL

/api/v1/organizations

---

# Organization Object

| Field | Type | Required | Description |
|--------|------|----------|-------------|
| id | UUID | System Generated | Organization Identifier |
| name | String | Yes | Organization Name |
| code | String | Yes | Unique Organization Code |
| email | String | Yes | Official Email |
| phone | String | No | Contact Number |
| website | String | No | Company Website |
| description | String | No | About Organization |
| createdAt | Timestamp | System Generated | Creation Time |
| updatedAt | Timestamp | System Generated | Last Updated Time |

---

# APIs

---

## 1. Create Organization

POST

/api/v1/organizations

### Request

```json
{
  "name": "OpenAI",
  "code": "OPENAI",
  "email": "contact@openai.com",
  "phone": "+919876543210",
  "website": "https://openai.com",
  "description": "Artificial Intelligence Research"
}
```

### Success

201 Created

```json
{
  "success": true,
  "message": "Organization created successfully",
  "data": {
      ...
  }
}
```

---

Validation

- name cannot be blank
- code cannot be blank
- code must be unique
- email must be valid
- website must be valid URL

---

Errors

400 Validation Failed

409 Organization Already Exists

500 Internal Server Error

---

## 2. Get Organization

GET

/api/v1/organizations/{id}

Response

200 OK

---

404 Organization Not Found

---

## 3. Update Organization

PUT

/api/v1/organizations/{id}

Response

200 OK

---

404 Not Found

409 Duplicate Organization Code

---

## 4. Delete Organization

DELETE

/api/v1/organizations/{id}

Soft Delete

Response

204 No Content

---

404 Not Found

---

Future APIs

- Search Organizations
- Pagination
- Filtering
- Sorting
- Organization Logo Upload
- Organization Settings
- Organization Statistics