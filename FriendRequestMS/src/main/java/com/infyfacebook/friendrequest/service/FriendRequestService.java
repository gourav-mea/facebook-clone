package com.infyfacebook.friendrequest.service;

import java.util.List;

import com.infyfacebook.friendrequest.dto.FriendRequestDTO;
import com.infyfacebook.friendrequest.dto.UserPrivacySetting;
import com.infyfacebook.friendrequest.exception.InfyFacebookException;

public interface FriendRequestService {
	public void addFriend(FriendRequestDTO friendRequestDTO) throws InfyFacebookException;
	public void acceptFriendRequest(FriendRequestDTO friendRequestDTO) throws InfyFacebookException;
	public void declineFriendRequest(FriendRequestDTO friendRequestDTO) throws InfyFacebookException;
	public List<Long> getFriendList(Long userId) throws InfyFacebookException;
	public Long getFriendCount(Long userId) throws InfyFacebookException;
	public List<FriendRequestDTO> getAllRequestSentTo(Long userId) throws InfyFacebookException;
	public List<FriendRequestDTO> getAllRequestSentFrom(Long userId) throws InfyFacebookException;
	public Boolean isFriend(Long userId, Long friendUserId);
	public void removeFriend(Long userId, Long friendUserId) throws InfyFacebookException;
	public String getStatus(Long userId, Long friendUserId) throws InfyFacebookException;
	public List<Long> getFriendFriendListList(Long friendUserId, Long userId, UserPrivacySetting friendPrivacySetting) throws InfyFacebookException;
}
