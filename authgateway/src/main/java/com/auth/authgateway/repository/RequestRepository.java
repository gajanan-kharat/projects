package com.auth.authgateway.repository;

import com.auth.authgateway.entities.Group;
import com.auth.authgateway.entities.Request;
import com.auth.authgateway.entities.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByStatus(RequestStatus status);

    @Query(value = "select * from REQUEST req where req.status=:status and req.groups IN :groups", nativeQuery = true)
    List<Request> findByStatusAndGroups(@Param("status") String status, @Param("groups") Set<Group> groups);
    // TODO: Implement the same query using methods only instead of native query
}
