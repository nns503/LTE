# LikeApi

All URIs are relative to *http://localhost:8080*

| Method                              | HTTP request                      | Description |
|-------------------------------------|-----------------------------------|-------------|
| [**likePost**](LikeApi.md#likePost) | **POST** /api/posts/{postId}/like | 게시글 좋아요 요청  |

<a name="likePost"></a>

# **likePost**

> likePost(postId)

게시글 좋아요 요청

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

