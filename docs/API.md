# Documentation for LTE

<a name="documentation-for-api-endpoints"></a>

## Documentation for API Endpoints

All URIs are relative to *http://localhost:8080*

| Class              | Method                                                                               | HTTP request                                        | Description    |
|--------------------|--------------------------------------------------------------------------------------|-----------------------------------------------------|----------------|
| *AuthApi*          | [**deleteMember**](Apis/AuthApi.md#deletemember)                                     | **DELETE** /api/members                             | 회원 계정 삭제 요청    |
 *AuthApi*          | [**join**](Apis/AuthApi.md#join)                                                     | **POST** /api/join                                  | 회원 회원가입 요청     |
 *AuthApi*          | [**reissueToken**](Apis/AuthApi.md#reissuetoken)                                     | **POST** /api/refresh                               | 리프레쉬 토큰 갱신 요청  |
| *COMMENTApi*       | [**create**](Apis/COMMENTApi.md#create1)                                             | **POST** /api/posts/{postId}/comments               | 댓글 작성          |
 *COMMENTApi*       | [**delete**](Apis/COMMENTApi.md#delete1)                                             | **DELETE** /api/posts/{postId}/comments/{commentId} | 댓글 삭제          |
 *COMMENTApi*       | [**getComments**](Apis/COMMENTApi.md#getcomments)                                    | **GET** /api/posts/{postId}/comments                | 댓글 목록 조회       |
 *COMMENTApi*       | [**update**](Apis/COMMENTApi.md#update1)                                             | **PUT** /api/posts/{postId}/comments/{commentId}    | 댓글 수정          |
| *FollowApi*        | [**followMember**](Apis/FollowApi.md#followmember)                                   | **POST** /api/members/{memberId}/follow             | 팔로우 요청         |
 *FollowApi*        | [**getFollowCount**](Apis/FollowApi.md#getfollowcount)                               | **GET** /api/members/{memberId}/follow/count        | 팔로위 및 팔로워 수 조회 |
 *FollowApi*        | [**getFolloweeList**](Apis/FollowApi.md#getfolloweelist)                             | **GET** /api/members/{memberId}/followee            | 팔로위 목록 조회      |
 *FollowApi*        | [**getFolloweePosts**](Apis/FollowApi.md#getfolloweeposts)                           | **GET** /api/members/followee/posts                 | 팔로위 최신 게시글 조회  |
 *FollowApi*        | [**getFollowerList**](Apis/FollowApi.md#getfollowerlist)                             | **GET** /api/members/{memberId}/follower            | 팔로워 목록 조회      |
 *FollowApi*        | [**unfollowMember**](Apis/FollowApi.md#unfollowmember)                               | **DELETE** /api/members/{memberId}/follow           | 언팔로우 요청        |
| *LikeApi*          | [**likePost**](Apis/LikeApi.md#likepost)                                             | **POST** /api/posts/{postId}/like                   | 게시글 좋아요 요청     |
| *LoginEndpointApi* | [**apiLoginPost**](Apis/LoginEndpointApi.md#apiloginpost)                            | **POST** /api/login                                 |                |
| *MemberApi*        | [**getMemberInfo**](Apis/MemberApi.md#getmemberinfo)                                 | **GET** /api/members/{memberId}/info                | 회원 정보 요청       |
 *MemberApi*        | [**updateNickname**](Apis/MemberApi.md#updatenickname)                               | **PUT** /api/members/nickname                       | 닉네임 수정 요청      |
 *MemberApi*        | [**updatePassword**](Apis/MemberApi.md#updatepassword)                               | **PUT** /api/members/password                       | 비밀번호 수정 요청     |
| *NOTIFICATIONApi*  | [**getNotifications**](Apis/NOTIFICATIONApi.md#getnotifications)                     | **GET** /api/notification                           | 알림 목록 조회       |
 *NOTIFICATIONApi*  | [**readNotification**](Apis/NOTIFICATIONApi.md#readnotification)                     | **PUT** /api/notification/{notificationId}          | 단건 알림 읽음 표시    |
 *NOTIFICATIONApi*  | [**readNotifications**](Apis/NOTIFICATIONApi.md#readnotifications)                   | **PUT** /api/notification                           | 전체 알림 읽음 표시    |
 *NOTIFICATIONApi*  | [**readNotificationsDelete**](Apis/NOTIFICATIONApi.md#readnotificationsdelete)       | **DELETE** /api/notification/read                   | 읽은 알림 전체 삭제    |
 *NOTIFICATIONApi*  | [**selectedNotificationDelete**](Apis/NOTIFICATIONApi.md#selectednotificationdelete) | **DELETE** /api/notification                        | 선택된 알림 삭제      |
| *PostApi*          | [**create**](Apis/PostApi.md#create)                                                 | **POST** /api/posts                                 | 게시글 작성 요청      |
 *PostApi*          | [**delete**](Apis/PostApi.md#delete)                                                 | **DELETE** /api/posts/{postId}                      | 게시글 삭제 요청      |
 *PostApi*          | [**getPost**](Apis/PostApi.md#getpost)                                               | **GET** /api/posts/{postId}                         | 게시글 단건 조회      |
 *PostApi*          | [**getPosts**](Apis/PostApi.md#getposts)                                             | **GET** /api/posts                                  | 게시글 목록 조회      |
 *PostApi*          | [**update**](Apis/PostApi.md#update)                                                 | **PUT** /api/posts/{postId}                         | 게시글 수정 요청      |

<a name="documentation-for-models"></a>

## Documentation for Models

- [CommentDTO](./Models/CommentDTO.md)
- [CreateCommentRequest](./Models/CreateCommentRequest.md)
- [CreatePostRequest](./Models/CreatePostRequest.md)
- [CreatePostResponse](./Models/CreatePostResponse.md)
- [FollowDTO](./Models/FollowDTO.md)
- [FollowPostDTO](./Models/FollowPostDTO.md)
- [GetCommentsResponse](./Models/GetCommentsResponse.md)
- [GetFollowCountResponse](./Models/GetFollowCountResponse.md)
- [GetFolloweeListResponse](./Models/GetFolloweeListResponse.md)
- [GetFolloweePostsResponse](./Models/GetFolloweePostsResponse.md)
- [GetFollowerListResponse](./Models/GetFollowerListResponse.md)
- [GetMemberInfoResponse](./Models/GetMemberInfoResponse.md)
- [GetNotificationDTO](./Models/GetNotificationDTO.md)
- [GetNotificationResponse](./Models/GetNotificationResponse.md)
- [GetPostResponse](./Models/GetPostResponse.md)
- [GetPostsRequest](./Models/GetPostsRequest.md)
- [GetPostsResponse](./Models/GetPostsResponse.md)
- [JoinRequest](./Models/JoinRequest.md)
- [PostDTO](./Models/PostDTO.md)
- [SelectedNotificationDeleteRequest](./Models/SelectedNotificationDeleteRequest.md)
- [SliceInfo](./Models/SliceInfo.md)
- [UpdateCommentRequest](./Models/UpdateCommentRequest.md)
- [UpdateNicknameRequest](./Models/UpdateNicknameRequest.md)
- [UpdatePasswordRequest](./Models/UpdatePasswordRequest.md)
- [UpdatePostRequest](./Models/UpdatePostRequest.md)
- [UpdatePostResponse](./Models/UpdatePostResponse.md)
- [_api_login_post_request](./Models/_api_login_post_request.md)

<a name="documentation-for-authorization"></a>

## Documentation for Authorization

<a name="JWT"></a>

### JWT

- **Type**: HTTP Bearer Token authentication (JWT)

