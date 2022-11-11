package com.wafflestudio.seminar.core.seminar.service

import com.wafflestudio.seminar.core.UserSeminar.domain.UserSeminarEntity
import com.wafflestudio.seminar.core.UserSeminar.repository.UserSeminarRepository
import com.wafflestudio.seminar.core.seminar.domain.SeminarEntity
import com.wafflestudio.seminar.core.seminar.repository.SeminarRepository
import com.wafflestudio.seminar.core.user.domain.UserEntity
import com.wafflestudio.seminar.core.user.domain.enums.RoleType
import com.wafflestudio.seminar.core.user.domain.profile.InstructorProfile
import com.wafflestudio.seminar.core.user.domain.profile.ParticipantProfile
import com.wafflestudio.seminar.core.user.repository.InstructorProfileRepository
import com.wafflestudio.seminar.core.user.repository.ParticipantProfileRepository
import com.wafflestudio.seminar.core.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SeminarTestHelper @Autowired constructor(
        private val userRepository: UserRepository,
        private val instructorProfileRepository: InstructorProfileRepository,
        private val participantProfileRepository: ParticipantProfileRepository,
        private val seminarRepository: SeminarRepository,
        private val userSeminarRepository: UserSeminarRepository,
) {
    fun createInstructor(
            email: String,
            username: String,
            password: String,
            company: String,
            year: Int,
    ): UserEntity {
        val user = createUser(email, username, password, RoleType.INSTRUCTOR)
        val instructorProfile = createInstructorProfile(company, year, user)
        user.instructorProfile = instructorProfile
        userRepository.save(user)
        return user
    }
    
    fun createParticipant(
            email: String,
            username: String,
            password: String,
            university: String,
            isRegistered: Boolean,
    ): UserEntity {
        val user = createUser(email, username, password, RoleType.PARTICIPANT)
        val participantProfile = createParticipantProfile(university, isRegistered, user)
        user.participantProfile = participantProfile
        userRepository.save(user)
        return user
    }
    
    fun createUser(
            email: String,
            username: String,
            password: String,
            role: RoleType,
    ) = UserEntity(
            email, username, password, role
    ).also { userRepository.save(it) }
    
    fun createInstructorProfile(
            company: String,
            year: Int,
            user: UserEntity,
    ) = InstructorProfile(company, year, user)
            .also { instructorProfileRepository.save(it) }
    
    fun createParticipantProfile(
            university: String,
            isRegistered: Boolean,
            user: UserEntity,
    ) = ParticipantProfile(university, isRegistered, user)
            .also { participantProfileRepository.save(it) }

    fun createSeminar(
            name: String,
            instructor: String,
            capacity: Long,
            count: Long,
            time: String,
            online: Boolean,
    ) = SeminarEntity(
            name, instructor, capacity, count, time, online
    ).also { seminarRepository.save(it) }

    fun createUserSeminarEntity(
            user: UserEntity,
            seminar: SeminarEntity,
    ) = UserSeminarEntity(user, seminar)
            .also { userSeminarRepository.save(it) }
}