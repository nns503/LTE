# COMMENTApi

All URIs are relative to *http://localhost:8080*

| Method                                       | HTTP request                                        | Description |
|----------------------------------------------|-----------------------------------------------------|-------------|
| [**create**](COMMENTApi.md#create1)          | **POST** /api/posts/{postId}/comments               | 댓글 작성       |
| [**delete**](COMMENTApi.md#delete1)          | **DELETE** /api/posts/{postId}/comments/{commentId} | 댓글 삭제       |
| [**getComments**](COMMENTApi.md#getComments) | **GET** /api/posts/{postId}/comments                | 댓글 목록 조회    |
| [**update**](COMMENTApi.md#update1)          | **PUT** /api/posts/{postId}/comments/{commentId}    | 댓글 수정       |

<a name="create1"></a>

# **create**

> create(postId, CreateCommentRequest)

댓글 작성

### Parameters

| Name                     | Type                                                          | Description | Notes             |
|--------------------------|---------------------------------------------------------------|-------------|-------------------|
| **postId**               | **Long**                                                      | 게시글 ID      | [default to null] |
| **CreateCommentRequest** | [**CreateCommentRequest**](../Models/CreateCommentRequest.md) |             |                   |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: Not defined

<a name="delete1"></a>

# **delete**

> delete(UNKNOWN_PARAMETER_NAME, commentId)

댓글 삭제

### Parameters

| Name                       | Type                  | Description | Notes             |
|----------------------------|-----------------------|-------------|-------------------|
| **UNKNOWN_PARAMETER_NAME** | [****](../Models/.md) | 게시글 ID      |                   |
| **commentId**              | **Long**              | 댓글 ID       | [default to null] |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="getComments"></a>

# **getComments**

> GetCommentsResponse getComments(postId, page, size)

댓글 목록 조회

### Parameters

| Name       | Type        | Description | Notes                      |
|------------|-------------|-------------|----------------------------|
| **postId** | **Long**    | 게시글 ID      | [default to null]          |
| **page**   | **Integer** |             | [optional] [default to 1]  |
| **size**   | **Integer** |             | [optional] [default to 10] |

### Return type

[**GetCommentsResponse**](../Models/GetCommentsResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="update1"></a>

# **update**

> update(UNKNOWN_PARAMETER_NAME, commentId, UpdateCommentRequest)

댓글 수정

### Parameters

| Name                       | Type                                                          | Description | Notes             |
|----------------------------|---------------------------------------------------------------|-------------|-------------------|
| **UNKNOWN_PARAMETER_NAME** | [****](../Models/.md)                                         | 게시글 ID      |                   |
| **commentId**              | **Long**                                                      |             | [default to null] |
| **UpdateCommentRequest**   | [**UpdateCommentRequest**](../Models/UpdateCommentRequest.md) |             |                   |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: Not defined

