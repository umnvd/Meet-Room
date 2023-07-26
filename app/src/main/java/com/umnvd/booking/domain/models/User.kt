package com.umnvd.booking.domain.models

data class User(
    val uid: String,
    val nickname: String,
    val fullName: String,
    val photoUrl: String,
)

val mockUser = User(
    uid = "mock_user_1",
    nickname = "felafelq1",
    fullName = "Дмитрий Умнов",
    photoUrl = "https://i.pravatar.cc/300?img=11",
)

val mockUserList = listOf(
    mockUser,
    User(
        uid = "mock_user_2",
        nickname = "elsagate2022",
        fullName = "Елизавета Гаврилова",
        photoUrl = "https://i.pravatar.cc/300?img=22",
    ),
    User(
        uid = "mock_user_3",
        nickname = "VladIsLove3",
        fullName = "Владислав Карасёв",
        photoUrl = "https://i.pravatar.cc/300?img=3",
    ),
    User(
        uid = "mock_user_33",
        nickname = "Test",
        fullName = "Тестов Тест",
        photoUrl = "https://i.pravatar.cc/300?img=33",
    ),
)
