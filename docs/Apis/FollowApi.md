# FollowApi

All URIs are relative to *http://localhost:8080*

| Method                                                | HTTP request                                 | Description    |
|-------------------------------------------------------|----------------------------------------------|----------------|
| [**followMember**](FollowApi.md#followMember)         | **POST** /api/members/{memberId}/follow      | 팔로우 요청         |
| [**getFollowCount**](FollowApi.md#getFollowCount)     | **GET** /api/members/{memberId}/follow/count | 팔로위 및 팔로워 수 조회 |
| [**getFolloweeList**](FollowApi.md#getFolloweeList)   | **GET** /api/members/{memberId}/followee     | 팔로위 목록 조회      |
| [**getFolloweePosts**](FollowApi.md#getFolloweePosts) | **GET** /api/members/followee/posts          | 팔로위 최신 게시글 조회  |
| [**getFollowerList**](FollowApi.md#getFollowerList)   | **GET** /api/members/{memberId}/follower     | 팔로워 목록 조회      |
| [**unfollowMember**](FollowApi.md#unfollowMember)     | **DELETE** /api/members/{memberId}/follow    | 언팔로우 요청        |

<a name="followMember"></a>

# **followMember**

> followMember(memberId)

팔로우 요청

### Parameters

| Name         | Type     | Description | Notes             |
|--------------|----------|-------------|-------------------|
| **memberId** | **Long** | 멤버 ID       | [default to null] |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="getFollowCount"></a>

# **getFollowCount**

> GetFollowCountResponse getFollowCount(memberId)

팔로위 및 팔로워 수 조회

### Parameters

| Name         | Type     | Description | Notes             |
|--------------|----------|-------------|-------------------|
| **memberId** | **Long** | 멤버 ID       | [default to null] |

### Return type

[**GetFollowCountResponse**](../Models/GetFollowCountResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="getFolloweeList"></a>

# **getFolloweeList**

> GetFolloweeListResponse getFolloweeList(memberId, page, size)

팔로위 목록 조회

### Parameters

| Name         | Type        | Description | Notes                      |
|--------------|-------------|-------------|----------------------------|
| **memberId** | **Long**    | 멤버 ID       | [default to null]          |
| **page**     | **Integer** |             | [optional] [default to 1]  |
| **size**     | **Integer** |             | [optional] [default to 10] |

### Return type

[**GetFolloweeListResponse**](../Models/GetFolloweeListResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="getFolloweePosts"></a>

# **getFolloweePosts**

> GetFolloweePostsResponse getFolloweePosts()

팔로위 최신 게시글 조회

### Parameters

This endpoint does not need any parameter.

### Return type

[**GetFolloweePostsResponse**](../Models/GetFolloweePostsResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="getFollowerList"></a>

# **getFollowerList**

> GetFollowerListResponse getFollowerList(memberId, page, size)

팔로워 목록 조회

### Parameters

| Name         | Type        | Description | Notes                      |
|--------------|-------------|-------------|----------------------------|
| **memberId** | **Long**    | 멤버 ID       | [default to null]          |
| **page**     | **Integer** |             | [optional] [default to 1]  |
| **size**     | **Integer** |             | [optional] [default to 10] |

### Return type

[**GetFollowerListResponse**](../Models/GetFollowerListResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="unfollowMember"></a>

# **unfollowMember**

> unfollowMember(memberId)

언팔로우 요청

### Parameters

| Name         | Type     | Description | Notes             |
|--------------|----------|-------------|-------------------|
| **memberId** | **Long** | 멤버 ID       | [default to null] |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

