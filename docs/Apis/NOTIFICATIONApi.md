# NOTIFICATIONApi

All URIs are relative to *http://localhost:8080*

| Method                                                                          | HTTP request                               | Description |
|---------------------------------------------------------------------------------|--------------------------------------------|-------------|
| [**getNotifications**](NOTIFICATIONApi.md#getNotifications)                     | **GET** /api/notification                  | 알림 목록 조회    |
| [**readNotification**](NOTIFICATIONApi.md#readNotification)                     | **PUT** /api/notification/{notificationId} | 단건 알림 읽음 표시 |
| [**readNotifications**](NOTIFICATIONApi.md#readNotifications)                   | **PUT** /api/notification                  | 전체 알림 읽음 표시 |
| [**readNotificationsDelete**](NOTIFICATIONApi.md#readNotificationsDelete)       | **DELETE** /api/notification/read          | 읽은 알림 전체 삭제 |
| [**selectedNotificationDelete**](NOTIFICATIONApi.md#selectedNotificationDelete) | **DELETE** /api/notification               | 선택된 알림 삭제   |

<a name="getNotifications"></a>

# **getNotifications**

> GetNotificationResponse getNotifications()

알림 목록 조회

### Parameters

This endpoint does not need any parameter.

### Return type

[**GetNotificationResponse**](../Models/GetNotificationResponse.md)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json;charset=UTF-8

<a name="readNotification"></a>

# **readNotification**

> readNotification(notificationId)

단건 알림 읽음 표시

### Parameters

| Name               | Type     | Description | Notes             |
|--------------------|----------|-------------|-------------------|
| **notificationId** | **Long** |             | [default to null] |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="readNotifications"></a>

# **readNotifications**

> readNotifications()

전체 알림 읽음 표시

### Parameters

This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="readNotificationsDelete"></a>

# **readNotificationsDelete**

> readNotificationsDelete()

읽은 알림 전체 삭제

### Parameters

This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="selectedNotificationDelete"></a>

# **selectedNotificationDelete**

> selectedNotificationDelete(SelectedNotificationDeleteRequest)

선택된 알림 삭제

### Parameters

| Name                                  | Type                                                                                    | Description | Notes |
|---------------------------------------|-----------------------------------------------------------------------------------------|-------------|-------|
| **SelectedNotificationDeleteRequest** | [**SelectedNotificationDeleteRequest**](../Models/SelectedNotificationDeleteRequest.md) |             |       |

### Return type

null (empty response body)

### Authorization

[JWT](../API#JWT)

### HTTP request headers

- **Content-Type**: application/json;charset=UTF-8
- **Accept**: Not defined

