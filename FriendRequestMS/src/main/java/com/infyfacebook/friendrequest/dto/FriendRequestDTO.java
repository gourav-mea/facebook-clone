package com.infyfacebook.friendrequest.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.infyfacebook.friendrequest.entity.FriendRequest;

public class FriendRequestDTO {
	
	private Long id;
	
	@NotNull(message = "{friendrequest.sentto.notpresent}")
	private Long sentTo;
	
	@NotNull(message = "{friendrequest.sentfrom.notpresent}")
	private Long sentFrom;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(id, sentFrom, sentTo, requestStatus);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendRequestDTO other = (FriendRequestDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(sentFrom, other.sentFrom)
				&& Objects.equals(sentTo, other.sentTo) && Objects.equals(requestStatus, other.requestStatus);
	}
	public FriendRequest toEntity() {
		FriendRequest friendRequest = new FriendRequest();
		friendRequest.setId(id);
		friendRequest.setSentTo(sentTo);
		friendRequest.setSentFrom(sentFrom);
		friendRequest.setRequestStatus(requestStatus);
		return friendRequest;
	}
	
}
