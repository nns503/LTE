# AuthApi

All URIs are relative to *http://localhost:8080*

| Method                                      | HTTP request            | Description   |
|---------------------------------------------|-------------------------|---------------|
| [**deleteMember**](AuthApi.md#deleteMember) | **DELETE** /api/members | 회원 계정 삭제 요청   |
| [**join**](AuthApi.md#join)                 | **POST** /api/join      | 회원 회원가입 요청    |
| [**reissueToken**](AuthApi.md#reissueToken) | **POST** /api/refresh   | 리프레쉬 토큰 갱신 요청 |

<a name="deleteMember"></a>

# **deleteMember**

> deleteMember()

회원 계정 삭제 요청

### Parameters

This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="join"></a>

# **join**

> join(JoinRequest)

회원 회원가입 요청

### Parameters

| Name            | Type                                        | Description | Notes |
|-----------------|---------------------------------------------|-------------|-------|
| **JoinRequest** | [**JoinRequest**](../Models/JoinRequest.md) |             |       |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: Not defined

<a name="reissueToken"></a>

# **reissueToken**

> reissueToken()

리프레쉬 토큰 갱신 요청

### Parameters

This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

