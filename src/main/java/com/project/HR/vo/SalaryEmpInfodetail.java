package com.project.HR.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="salary_emp_infodetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryEmpInfodetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "empno")
    Integer empNo;
    @Column(name = "info_id")
    Integer infoId;
    @Column(name = "leave_type")
    String leaveType;
    @Column(name = "leave_hours")
    Float leaveHours;
    @Column(name = "total")
    Float total;
}
