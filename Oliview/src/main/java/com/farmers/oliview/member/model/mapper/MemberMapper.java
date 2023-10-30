package com.farmers.oliview.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.farmers.oliview.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(Member inputMember);




}
