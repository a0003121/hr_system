package com.project.HR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer> {
}
