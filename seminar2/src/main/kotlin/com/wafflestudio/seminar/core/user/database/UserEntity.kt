package com.wafflestudio.seminar.core.user.database

import com.wafflestudio.seminar.core.jointable.UserSeminarEntity
import com.wafflestudio.seminar.core.profile.database.InstructorProfileEntity
import com.wafflestudio.seminar.core.profile.database.ParticipantProfileEntity
import com.wafflestudio.seminar.core.seminar.database.SeminarEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "user")
class UserEntity (
        @Size(min = 1) @Column(nullable = false)
        val username: String,
        
        @Size(min = 1) @Column(nullable = false, unique = true)
        val email: String,

        @Size(min = 1) @Column(nullable = false)
        val password: String,
        
        @CreationTimestamp @Column(nullable = false)
        var lastLogin: LocalDateTime? = null,

        @OneToMany(mappedBy = "user")
        val seminars: MutableSet<UserSeminarEntity> = mutableSetOf(),

        @OneToOne(optional = true, mappedBy = "user")
        var participantProfile: ParticipantProfileEntity? = null,
        
        @OneToOne(optional = true, mappedBy = "user")
        var instructorProfile: InstructorProfileEntity? = null,
) {
    @CreationTimestamp @Column(nullable = false, updatable = false)
    val dataJoined: LocalDateTime? = null

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}