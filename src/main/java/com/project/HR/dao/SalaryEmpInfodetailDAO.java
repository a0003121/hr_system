package com.project.HR.dao;

import com.project.HR.vo.SalaryEmpInfodetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryEmpInfodetailDAO extends JpaRepository<SalaryEmpInfodetail, Integer> {
    public void deleteByInfoId(int infoId);
}
