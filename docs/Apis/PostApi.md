# PostApi

All URIs are relative to *http://localhost:8080*

| Method                              | HTTP request                   | Description |
|-------------------------------------|--------------------------------|-------------|
| [**create**](PostApi.md#create)     | **POST** /api/posts            | 게시글 작성 요청   |
| [**delete**](PostApi.md#delete)     | **DELETE** /api/posts/{postId} | 게시글 삭제 요청   |
| [**getPost**](PostApi.md#getPost)   | **GET** /api/posts/{postId}    | 게시글 단건 조회   |
| [**getPosts**](PostApi.md#getPosts) | **GET** /api/posts             | 게시글 목록 조회   |
| [**update**](PostApi.md#update)     | **PUT** /api/posts/{postId}    | 게시글 수정 요청   |

<a name="create"></a>

# **create**

> CreatePostResponse create(CreatePostRequest)

게시글 작성 요청

### Parameters

| Name                  | Type                                                    | Description | Notes |
|-----------------------|---------------------------------------------------------|-------------|-------|
| **CreatePostRequest** | [**CreatePostRequest**](../Models/CreatePostRequest.md) |             |       |

### Return type

[**CreatePostResponse**](../Models/CreatePostResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: application/json;charset=UTF-8

<a name="delete"></a>

# **delete**

> delete(postId)

게시글 삭제 요청

### Parameters

| Name       | Type     | Description | Notes             |
|------------|----------|-------------|-------------------|
| **postId** | **Long** | 게시글 ID      | [default to null] |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="getPost"></a>

# **getPost**

> GetPostResponse getPost(postId)

게시글 단건 조회

### Parameters

| Name       | Type     | Description | Notes             |
|------------|----------|-------------|-------------------|
| **postId** | **Long** | 게시글 ID      | [default to null] |

### Return type

[**GetPostResponse**](../Models/GetPostResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="getPosts"></a>

# **getPosts**

> GetPostsResponse getPosts(request, page, size)

게시글 목록 조회

### Parameters

| Name        | Type                                 | Description | Notes                      |
|-------------|--------------------------------------|-------------|----------------------------|
| **request** | [**GetPostsRequest**](../Models/.md) |             | [default to null]          |
| **page**    | **Integer**                          |             | [optional] [default to 1]  |
| **size**    | **Integer**                          |             | [optional] [default to 10] |

### Return type

[**GetPostsResponse**](../Models/GetPostsResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="update"></a>

# **update**

> UpdatePostResponse update(postId, UpdatePostRequest)

게시글 수정 요청

### Parameters

| Name                  | Type                                                    | Description | Notes             |
|-----------------------|---------------------------------------------------------|-------------|-------------------|
| **postId**            | **Long**                                                | 게시글 ID      | [default to null] |
| **UpdatePostRequest** | [**UpdatePostRequest**](../Models/UpdatePostRequest.md) |             |                   |

### Return type

[**UpdatePostResponse**](../Models/UpdatePostResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: application/json;charset=UTF-8

