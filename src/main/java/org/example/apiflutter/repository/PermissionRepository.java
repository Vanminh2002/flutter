package org.example.apiflutter.repository;

import org.example.apiflutter.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PermissionRepository extends JpaRepository<Permission, String> {
}