package com.rmf.imagecollection.data.parser

import com.rmf.imagecollection.data.remote.dto.UserDto
import com.rmf.imagecollection.domain.model.User

fun UserDto.toUser() =
    User(
        name = name,
        username = username
    )