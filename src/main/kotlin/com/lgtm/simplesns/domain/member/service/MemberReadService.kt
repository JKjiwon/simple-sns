package com.lgtm.simplesns.domain.member.service

import com.lgtm.simplesns.application.common.ApplicationException
import com.lgtm.simplesns.application.common.ErrorCode
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberReadService(
    private val memberRepository: MemberRepository
) {

    fun getMember(memberId: Long): MemberDto {
        val member = memberRepository.findById(memberId)
            .orElseThrow { ApplicationException(ErrorCode.MEMBER_NOT_FOUND) }
        return MemberDto.of(member)
    }

    fun getMembers(ids: List<Long>): List<MemberDto> {
        return memberRepository.findAllByIdIn(ids).map { MemberDto.of(it) }
    }

}