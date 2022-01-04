package com.project.HR.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.HR.vo.Insurance;

public interface InsuranceDAO extends JpaRepository<Insurance, Integer> {
	List<Insurance> findByInsuranceType(String insuranceType);
}
