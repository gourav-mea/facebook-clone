package com.infyfacebook.friendrequest.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.infyfacebook.friendrequest.dto.FriendRequestDTO;
import com.infyfacebook.friendrequest.dto.RequestStatus;


@Entity
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long sentTo;
	private Long sentFrom;
	@Enumerated(EnumType.STRING)
	private RequestStatus requestStatus;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSentTo() {
		return sentTo;
	}
	public void setSentTo(Long sentBy) {
		this.sentTo = sentBy;
	}
	public Long getSentFrom() {
		return sentFrom;
	}
	public void setSentFrom(Long sentFrom) {
		this.sentFrom = sentFrom;
	}
	public RequestStatus getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	public FriendRequestDTO toDTO() {
		FriendRequestDTO friendRequestDto = new FriendRequestDTO();
		friendRequestDto.setId(id);
		friendRequestDto.setSentTo(sentTo);
		friendRequestDto.setSentFrom(sentFrom);
		friendRequestDto.setRequestStatus(requestStatus);
		
		return friendRequestDto;
	}
	
}
