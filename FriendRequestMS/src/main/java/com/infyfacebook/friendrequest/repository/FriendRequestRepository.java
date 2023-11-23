package com.infyfacebook.friendrequest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.infyfacebook.friendrequest.dto.RequestStatus;
import com.infyfacebook.friendrequest.entity.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{
	Optional<FriendRequest> findBySentToAndSentFrom(Long sentTo, Long sentFrom);
	List<FriendRequest> findBySentTo(Long sentTo);
	List<FriendRequest> findBySentFrom(Long sentFrom);
	@Query("Select c from FriendRequest c where(c.sentTo = :sentTo OR c.sentFrom = :sentFrom) AND c.requestStatus = :requestStatus")
	List<FriendRequest> findBySentToOrSentFromAndRequestStatus(@Param("sentTo") Long sentTo, @Param("sentFrom") Long sentFrom, @Param("requestStatus") RequestStatus requestStatus);
	@Query("Select COUNT(c) from FriendRequest c where(c.sentTo = :sentTo OR c.sentFrom = :sentFrom) AND c.requestStatus = :requestStatus")
	Long countBySentToOrSentFromAndRequestStatus(@Param("sentTo") Long sentTo, @Param("sentFrom") Long sentFrom, @Param("requestStatus") RequestStatus requestStatus);
	void deleteBySentToAndSentFrom(Long sentTo, Long sentFrom);
}
