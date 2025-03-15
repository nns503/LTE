# MemberApi

All URIs are relative to *http://localhost:8080*

| Method                                            | HTTP request                         | Description |
|---------------------------------------------------|--------------------------------------|-------------|
| [**getMemberInfo**](MemberApi.md#getMemberInfo)   | **GET** /api/members/{memberId}/info | 회원 정보 요청    |
| [**updateNickname**](MemberApi.md#updateNickname) | **PUT** /api/members/nickname        | 닉네임 수정 요청   |
| [**updatePassword**](MemberApi.md#updatePassword) | **PUT** /api/members/password        | 비밀번호 수정 요청  |

<a name="getMemberInfo"></a>

# **getMemberInfo**

> GetMemberInfoResponse getMemberInfo(memberId)

회원 정보 요청

### Parameters

| Name         | Type     | Description | Notes             |
|--------------|----------|-------------|-------------------|
| **memberId** | **Long** | 멤버 ID       | [default to null] |

### Return type

[**GetMemberInfoResponse**](../Models/GetMemberInfoResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="updateNickname"></a>

# **updateNickname**

> updateNickname(UpdateNicknameRequest)

닉네임 수정 요청

### Parameters

| Name                      | Type                                                            | Description | Notes |
|---------------------------|-----------------------------------------------------------------|-------------|-------|
| **UpdateNicknameRequest** | [**UpdateNicknameRequest**](../Models/UpdateNicknameRequest.md) |             |       |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: Not defined

<a name="updatePassword"></a>

# **updatePassword**

> updatePassword(UpdatePasswordRequest)

비밀번호 수정 요청

### Parameters

| Name                      | Type                                                            | Description | Notes |
|---------------------------|-----------------------------------------------------------------|-------------|-------|
| **UpdatePasswordRequest** | [**UpdatePasswordRequest**](../Models/UpdatePasswordRequest.md) |             |       |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: Not defined

