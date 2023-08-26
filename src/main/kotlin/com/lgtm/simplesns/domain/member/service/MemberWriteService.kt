package com.lgtm.simplesns.domain.member.service

import com.lgtm.simplesns.domain.member.dto.MemberCreateCommand
import com.lgtm.simplesns.domain.member.dto.MemberDto
import com.lgtm.simplesns.domain.member.dto.MemberUpdateCommand
import com.lgtm.simplesns.domain.member.entity.MemberUpdatedHistory
import com.lgtm.simplesns.domain.member.repository.MemberRepository
import com.lgtm.simplesns.domain.member.repository.MemberUpdatedHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberWriteService(
    private val memberRepository: MemberRepository,
    private val memberUpdatedHistoryRepository: MemberUpdatedHistoryRepository
) {

    fun create(command: MemberCreateCommand): MemberDto {
        val member = command.toEntity()
        val savedMember = memberRepository.save(member)

        val memberUpdatedHistory = MemberUpdatedHistory(nickname = savedMember.nickname, email = savedMember.nickname)
        memberUpdatedHistoryRepository.save(memberUpdatedHistory)

        return MemberDto.toDto(member)
    }

    fun update(memberId: Long, command: MemberUpdateCommand) {
        val member = memberRepository.findById(memberId).orElseThrow()
        member.update(command.toEntity())

        val memberUpdatedHistory = MemberUpdatedHistory(nickname = member.nickname, email = member.nickname)
        memberUpdatedHistoryRepository.save(memberUpdatedHistory)
    }

}