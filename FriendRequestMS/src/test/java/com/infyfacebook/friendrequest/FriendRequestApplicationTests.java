package com.infyfacebook.friendrequest;

import static org.mockito.ArgumentMatchers.anyLong;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infyfacebook.friendrequest.dto.FriendRequestDTO;
import com.infyfacebook.friendrequest.dto.RequestStatus;
import com.infyfacebook.friendrequest.dto.UserPrivacySetting;
import com.infyfacebook.friendrequest.entity.FriendRequest;
import com.infyfacebook.friendrequest.exception.InfyFacebookException;
import com.infyfacebook.friendrequest.repository.FriendRequestRepository;
import com.infyfacebook.friendrequest.service.FriendRequestService;
import com.infyfacebook.friendrequest.service.FriendRequestServiceImpl;

@SpringBootTest
class FriendRequestApplicationTests {

	@Mock
	FriendRequestRepository friendRequestRepository;
	
	@Mock
	FriendRequestService friendRequestServiceMock;
	
	@InjectMocks
	FriendRequestService friendRequestService = new FriendRequestServiceImpl();
	
	@Test
	void addFriendTestValid() throws InfyFacebookException{
		
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.empty());
		Mockito.when(friendRequestRepository.save(new FriendRequest())).thenReturn(new FriendRequest());
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		
		Assertions.assertDoesNotThrow(() -> friendRequestService.addFriend(friendRequestDTO));
	}
	
	@Test
	void addFriendTestInvalid() throws InfyFacebookException{
		
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.of(new FriendRequest()));
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.addFriend(friendRequestDTO));
		
		Assertions.assertEquals("Service.REQUEST_ALREADY_PRESENT", exception.getMessage());
	}
	
	@Test
	void addFriendTestInvalid2() throws InfyFacebookException{
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(100L);
		friendRequestDTO.setSentTo(100L);
		Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.addFriend(friendRequestDTO));
		
		Assertions.assertEquals("Service.INVALID_FRIEND_REQUEST", exception.getMessage());
	}
	
	@Test
	void acceptFriendRequestTestValid() throws InfyFacebookException{
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.of(new FriendRequest()));
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		
		Assertions.assertDoesNotThrow(() -> friendRequestService.acceptFriendRequest(friendRequestDTO));
	}
	
	@Test
	void acceptFriendRequestTestInvalid() throws InfyFacebookException{
		
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.empty());
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		
		Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.acceptFriendRequest(friendRequestDTO));
		
		Assertions.assertEquals("Service.REQUEST_NOT_FOUND", exception.getMessage());
	}
	
	@Test
	void declineFriendRequestTestValid() throws InfyFacebookException{
		FriendRequest friendRequest = new FriendRequest();
		friendRequest.setRequestStatus(RequestStatus.valueOf("PENDING"));
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.of(friendRequest));
		
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		
		Assertions.assertDoesNotThrow(() -> friendRequestService.declineFriendRequest(friendRequestDTO));
		
	}
	
	@Test
	void declineFriendRequestTestInvalid() throws InfyFacebookException{
		
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.empty());
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		
		Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.declineFriendRequest(friendRequestDTO));
		
		Assertions.assertEquals("Service.REQUEST_NOT_FOUND", exception.getMessage());
		
	}
	
	@Test
	void declineFriendRequestTestInvalid2() throws InfyFacebookException{
		FriendRequest friendRequest = new FriendRequest();
		friendRequest.setRequestStatus(RequestStatus.valueOf("ACCEPTED"));
		
		Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.of(friendRequest));
		FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
		friendRequestDTO.setSentFrom(110L);
		friendRequestDTO.setSentTo(100L);
		
		Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.declineFriendRequest(friendRequestDTO));
		
		Assertions.assertEquals("Service.CANNOT_REJECT", exception.getMessage());
		
	}
	
	@Test
	void getFriendListTestValid() throws InfyFacebookException{
		Long userId = 1L;

        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setSentTo(userId);
        friendRequest1.setSentFrom(2L);
        friendRequest1.setRequestStatus(RequestStatus.ACCEPTED);

        FriendRequest friendRequest2 = new FriendRequest();
        friendRequest2.setSentTo(3L);
        friendRequest2.setSentFrom(userId);
        friendRequest2.setRequestStatus(RequestStatus.ACCEPTED);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(friendRequest1);
        friendRequestList.add(friendRequest2);

        Mockito.when(friendRequestRepository.findBySentToOrSentFromAndRequestStatus(userId, userId, RequestStatus.ACCEPTED)).thenReturn(friendRequestList);
        List<Long> friendList = friendRequestService.getFriendList(userId);

        List<Long> expectedFriendList = new ArrayList<>();
        expectedFriendList.add(2L);
        expectedFriendList.add(3L);

        Assertions.assertEquals(expectedFriendList, friendList);
	}
	
	@Test
	void getFriendCountTestValid() throws InfyFacebookException{
		Long userId = 1L;

        Mockito.when(friendRequestRepository.countBySentToOrSentFromAndRequestStatus(userId, userId, RequestStatus.ACCEPTED)).thenReturn(2L);

        Assertions.assertEquals(2L, friendRequestService.getFriendCount(userId));
	}
	
	@Test 
	void getAllRequestSentToTestValid() throws InfyFacebookException{
		Long userId = 1L;

        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setSentTo(userId);
        friendRequest1.setSentFrom(2L);
        friendRequest1.setRequestStatus(RequestStatus.PENDING);

        FriendRequest friendRequest2 = new FriendRequest();
        friendRequest2.setSentTo(userId);
        friendRequest2.setSentFrom(3L);
        friendRequest2.setRequestStatus(RequestStatus.PENDING);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(friendRequest1);
        friendRequestList.add(friendRequest2);

        Mockito.when(friendRequestRepository.findBySentTo(userId)).thenReturn(friendRequestList);

        List<FriendRequestDTO> friendRequestDTOList = friendRequestService.getAllRequestSentTo(userId);

        List<FriendRequestDTO> expectedFriendRequestDTOList = new ArrayList<>();
        expectedFriendRequestDTOList.add(friendRequest1.toDTO());
        expectedFriendRequestDTOList.add(friendRequest2.toDTO());

        Assertions.assertEquals(expectedFriendRequestDTOList, friendRequestDTOList);
	}
	
	@Test
	void getAllRequestSentFromTestValid() throws InfyFacebookException{
		Long userId = 1L;

        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setSentFrom(userId);
        friendRequest1.setSentTo(2L);
        friendRequest1.setRequestStatus(RequestStatus.PENDING);

        FriendRequest friendRequest2 = new FriendRequest();
        friendRequest2.setSentFrom(userId);
        friendRequest2.setSentTo(3L);
        friendRequest2.setRequestStatus(RequestStatus.PENDING);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(friendRequest1);
        friendRequestList.add(friendRequest2);

        Mockito.when(friendRequestRepository.findBySentFrom(userId)).thenReturn(friendRequestList);

 
        List<FriendRequestDTO> friendRequestDTOList = friendRequestService.getAllRequestSentFrom(userId);
        List<FriendRequestDTO> expectedFriendRequestDTOList = new ArrayList<>();
        expectedFriendRequestDTOList.add(friendRequest1.toDTO());
        expectedFriendRequestDTOList.add(friendRequest2.toDTO());

        Assertions.assertEquals(expectedFriendRequestDTOList, friendRequestDTOList);
	}
	
	@Test
	void isFriendTestValid() throws InfyFacebookException{
		Long userId = 1L;
        Long friendUserId = 2L;

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSentTo(userId);
        friendRequest.setSentFrom(friendUserId);
        friendRequest.setRequestStatus(RequestStatus.ACCEPTED);

        Mockito.when(friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId)).thenReturn(Optional.of(friendRequest));

        Boolean isFriend = friendRequestService.isFriend(userId, friendUserId);

        Assertions.assertTrue(isFriend);
	}

	@Test
	void removeFriendTestValid() throws InfyFacebookException{
		Long userId = 1L;
        Long friendUserId = 2L;

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSentTo(userId);
        friendRequest.setSentFrom(friendUserId);
        friendRequest.setRequestStatus(RequestStatus.ACCEPTED);

        Mockito.when(friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId)).thenReturn(Optional.of(friendRequest));

        Assertions.assertDoesNotThrow(() -> friendRequestService.removeFriend(userId,friendUserId));
	}
	
	@Test
	void removeFriendTestInvalid() throws InfyFacebookException{
		Long userId = 1L;
        Long friendUserId = 2L;

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSentTo(userId);
        friendRequest.setSentFrom(friendUserId);
        friendRequest.setRequestStatus(RequestStatus.ACCEPTED);

        Mockito.when(friendRequestRepository.findBySentToAndSentFrom(anyLong(), anyLong())).thenReturn(Optional.empty());
        
        Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.removeFriend(userId, friendUserId));
		
		Assertions.assertEquals("Service.REQUEST_NOT_FOUND", exception.getMessage());
	}
	
	@Test
	void getStatusTestValid() throws InfyFacebookException{
		Long userId = 1L;
        Long friendUserId = 2L;

        Mockito.when(friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId)).thenReturn(Optional.empty());
        Mockito.when(friendRequestRepository.findBySentToAndSentFrom(friendUserId, friendUserId)).thenReturn(Optional.empty());
		
        String actual = friendRequestService.getStatus(userId, friendUserId);
        
		Assertions.assertEquals("NOT_FRIEND", actual);
	}
	
	@Test
	void getStatusTestValid2() throws InfyFacebookException{
		Long userId = 1L;
        Long friendUserId = 2L;

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSentTo(userId);
        friendRequest.setSentFrom(friendUserId);
        friendRequest.setRequestStatus(RequestStatus.ACCEPTED);

        Mockito.when(friendRequestRepository.findBySentToAndSentFrom(userId, friendUserId)).thenReturn(Optional.of(friendRequest));
		
        String actual = friendRequestService.getStatus(userId, friendUserId);
        
		Assertions.assertEquals("ACCEPTED", actual);
	}
	
	@Test 
	void getFriendFriendListTestInvalid() throws InfyFacebookException{
		Long friendUserId = 1L;
        Long userId = 2L;
        UserPrivacySetting friendPrivacySetting = UserPrivacySetting.PRIVATE;

        Mockito.when(friendRequestServiceMock.isFriend(anyLong(), anyLong())).thenReturn(true);
        Exception exception = Assertions.assertThrows(InfyFacebookException.class, () -> friendRequestService.getFriendFriendListList(friendUserId, userId, friendPrivacySetting));
		
		Assertions.assertEquals("Service.ACCESS_DENIED", exception.getMessage());
	}
}
