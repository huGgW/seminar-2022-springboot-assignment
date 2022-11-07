package com.wafflestudio.seminar.core.user.api

import com.wafflestudio.seminar.common.LoginUser
import com.wafflestudio.seminar.core.profile.dto.ParticipantProfileRequest
import com.wafflestudio.seminar.core.user.database.UserEntity
import com.wafflestudio.seminar.core.user.dto.UserRequest
import com.wafflestudio.seminar.core.user.dto.UserResponse
import com.wafflestudio.seminar.core.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
        private val userService: UserService
) {
    @GetMapping("/api/v1/user/{userId}")
    fun getUserInformation(
            @PathVariable userId: Long
    ) = userService.constructUserInformationById(userId)
    
    @PutMapping("/api/v1/user/me")
    fun putUserInformation(
            @RequestBody userRequest: UserRequest,
            @LoginUser meUser: UserEntity?,
    ) = meUser?.let {
        userService.modifyUserInformation(userRequest, meUser)
        ResponseEntity<String>("Modified", HttpStatus.OK)
    }
            ?: ResponseEntity<String>("Failed to get user information.", HttpStatus.UNAUTHORIZED)
    
    @PostMapping("/api/v1/user/participant")
    fun postParticipantPost(
            @RequestBody participantProfileRequest: ParticipantProfileRequest,
            @LoginUser meUser: UserEntity?,
    ): Any = meUser?.let{
        userService.addToParticipantAndReturnUserInfo(participantProfileRequest, meUser)
    } ?: ResponseEntity<String>("Failed to get user information.", HttpStatus.UNAUTHORIZED)
}