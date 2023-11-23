package com.infyfacebook.friendrequest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infyfacebook.friendrequest.dto.FriendRequestDTO;
import com.infyfacebook.friendrequest.dto.RequestStatus;
import com.infyfacebook.friendrequest.dto.UserPrivacySetting;
import com.infyfacebook.friendrequest.entity.FriendRequest;
import com.infyfacebook.friendrequest.exception.InfyFacebookException;
import com.infyfacebook.friendrequest.repository.FriendRequestRepository;


@Service(value="friendRequestService")
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService{
	
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	private static final String NO_USER_FOUND = "Service.REQUEST_NOT_FOUND";
	
	@Override
	public void addFriend(FriendRequestDTO friendRequestDTO) throws InfyFacebookException {
		if(friendRequestDTO.getSentTo().equals(friendRequestDTO.getSentFrom())) {
			throw new InfyFacebookException("Service.INVALID_FRIEND_REQUEST");
		}
		Optional<FriendRequest> optional1 = friendRequestRepository.findBySentToAndSentFrom(friendRequestDTO.getSentTo(), friendRequestDTO.getSentFrom());
		Optional<FriendRequest> optional2 = friendRequestRepository.findBySentToAndSentFrom(friendRequestDTO.getSentFrom(), friendRequestDTO.getSentTo());	
		if(optional1.isPresent() || optional2.isPresent()) {			
			throw new InfyFacebookException("Service.REQUEST_ALREADY_PRESENT");
		}
		FriendRequest friendRequest = friendRequestDTO.toEntity();
		friendRequest.setRequestStatus(RequestStatus.PENDING);
		friendRequestRepository.save(friendRequest);
	}

	@Override
	public void acceptFriendRequest(FriendRequestDTO friendRequestDTO) throws InfyFacebookException {
		Optional<FriendRequest> optional = friendRequestRepository.findBySentToAndSentFrom(friendRequestDTO.getSentTo(), friendRequestDTO.getSentFrom());
		if(!optional.isPresent()) {
			throw new InfyFacebookException(NO_USER_FOUND);
		}
		optional.get().setRequestStatus(RequestStatus.ACCEPTED);
	}
	
	@Override
	public void declineFriendRequest(FriendRequestDTO friendRequestDTO) throws InfyFacebookException {
		Optional<FriendRequest> optional = friendRequestRepository.findBySentToAndSentFrom(friendRequestDTO.getSentTo(), friendRequestDTO.getSentFrom());
		if(!optional.isPresent()) {
			throw new InfyFacebookException(NO_USER_FOUND);
		}
		if(optional.get().getRequestStatus().toString().equals("PENDING")) {			
			friendRequestRepository.deleteBySentToAndSentFrom(friendRequestDTO.getSentTo(), friendRequestDTO.getSentFrom());
		}
		else {
			throw new InfyFacebookException("Service.CANNOT_REJECT");
		}
	}

	@Override
	public List<Long> getFriendList(Long userId) throws InfyFacebookException {
		List<FriendRequest> friendRequestList = friendRequestRepository.findBySentToOrSentFromAndRequestStatus(userId, userId, RequestStatus.ACCEPTED);
		List<Long> friendList = new ArrayList<>();
		for(FriendRequest friendRequest : friendRequestList) {
			if(friendRequest.getSentTo().equals(userId)) {
				friendList.add(friendRequest.getSentFrom());
			}
			if(friendRequest.getSentFrom().equals(userId)) {
				friendList.add(friendRequest.getSentTo());
			}
		}
		return friendList;
	}

	@Override
	public Long getFriendCount(Long userId) throws InfyFacebookException {
		return friendRequestRepository.countBySentToOrSentFromAndRequestStatus(userId, userId, RequestStatus.ACCEPTED);
	}

	@Override
	public List<FriendRequestDTO> getAllRequestSentTo(Long userId) throws InfyFacebookException {
		List<FriendRequest> friendRequestList = friendRequestRepository.findBySentTo(userId);
		List<FriendRequestDTO> friendRequestDTOList = new ArrayList<>();
		for(FriendRequest friendRequest : friendRequestList) {
			FriendRequestDTO friendRequestDTO = friendRequest.toDTO();
			friendRequestDTOList.add(friendRequestDTO);
		}
		return friendRequestDTOList;
	}
	
	@Override
	public List<FriendRequestDTO> getAllRequestSentFrom(Long userId) throws InfyFacebookException {
		List<FriendRequest> friendRequestList = friendRequestRepository.findBySentFrom(userId);
		List<FriendRequestDTO> friendRequestDTOList = new ArrayList<>();
		for(FriendRequest friendRequest : friendRequestList) {
			FriendRequestDTO friendRequestDTO = friendRequest.toDTO();
			friendRequestDTOList.add(friendRequestDTO);
		}
		return friendRequestDTOList;
	}

	@Override
	public Boolean isFriend(Long userId, Long friendUserId){
		Optional<FriendRequest> optional1 = friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId);
		Optional<FriendRequest> optional2 = friendRequestRepository.findBySentToAndSentFrom(friendUserId, userId);
		if(!optional1.isPresent() && !optional2.isPresent()) {
			return false;
		}
		FriendRequest friendRequest = new FriendRequest();
		if(optional1.isPresent()) {
			friendRequest = optional1.get();
		}
		if(optional2.isPresent()) {
			friendRequest = optional2.get();
		}
		return friendRequest.getRequestStatus().toString().equals("ACCEPTED");
	}

	@Override
	public void removeFriend(Long userId, Long friendUserId) throws InfyFacebookException {
		Optional<FriendRequest> optional1 = friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId);
		Optional<FriendRequest> optional2 = friendRequestRepository.findBySentToAndSentFrom(friendUserId, userId);
		if(!optional1.isPresent() && !optional2.isPresent()) {
			throw new InfyFacebookException(NO_USER_FOUND);
		}
		FriendRequest friendRequest = new FriendRequest();
		if(optional1.isPresent()) {
			friendRequest = optional1.get();
		}
		if(optional2.isPresent()) {
			friendRequest = optional2.get();
		}
		if(friendRequest.getRequestStatus().toString().equals("ACCEPTED")) {			
			friendRequestRepository.delete(friendRequest);
		}
	}

	@Override
	public String getStatus(Long userId, Long friendUserId) throws InfyFacebookException {
		Optional<FriendRequest> optional1 = friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId);
		Optional<FriendRequest> optional2 = friendRequestRepository.findBySentToAndSentFrom(friendUserId, userId);
		if(!optional1.isPresent() && !optional2.isPresent()) {
			return "NOT_FRIEND";
		}
		if(optional1.isPresent()) {
			return optional1.get().getRequestStatus().toString();
		}
		return optional2.get().getRequestStatus().toString();
	}

	@Override
	public List<Long> getFriendFriendListList(Long friendUserId, Long userId, UserPrivacySetting friendPrivacySetting) throws InfyFacebookException {
		List<Long> friendFriendList;
		if(friendPrivacySetting==null) {
			friendPrivacySetting = UserPrivacySetting.valueOf("PUBLIC");
		}
		Boolean isFriend = isFriend(userId, friendUserId);
		if(isFriend.booleanValue()) {
			if(friendPrivacySetting.toString().equals("PRIVATE")) {
				throw new InfyFacebookException("Service.ACCESS_DENIED");
			}
			friendFriendList = getFriendList(friendUserId);
			return friendFriendList;
		}
		else {
			if(friendPrivacySetting.toString().equals("PUBLIC")) {
				friendFriendList = getFriendList(friendUserId);
				return friendFriendList;
			}
			else {
				throw new InfyFacebookException("Service.ACCESS_DENIED");
			}
		}
	}
}
